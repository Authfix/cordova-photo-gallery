package tech.authfix.cordova.plugins.photogallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PhotoGalleryActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    /**
     * Get the tag of this activity
     */
    private static final String TAG = PhotoGalleryActivity.class.getName();

    /**
     * The code used for sharing activity
     */
    private static final int ShareRequestCode = 375;

    /**
     * The current image position
     */
    private TextView currentPageTextView;

    /**
     * The total images
     */
    private TextView totalImagesTextView;

    /**
     * Gets the photos
     */
    private Photos photos;

    /**
     * The view pager
     */
    private PhotoGalleryViewPager viewPager;

    /**
     * Occurs when the activity is being created
     * @param savedInstanceState The save parameters
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int photoLayoutIdentifier = getResourceIdentifier("activity_photo_gallery", "layout");
        int pagerLayoutIdentifier = getResourceIdentifier("photo_gallery.pager", "id");
        int photoNumberToolbarIdentifier = getResourceIdentifier("photo_gallery.picture_number", "id");
        int appBarLayoutIdentifier = getResourceIdentifier("photo_gallery.appbar_layout", "id");
        int totalImagesNumberIdentifier = getResourceIdentifier("photo_gallery.total_images", "id");
        int currentImagePositionIdentifier = getResourceIdentifier("photo_gallery.current_image", "id");

        setContentView(photoLayoutIdentifier);

        this.currentPageTextView = findViewById(currentImagePositionIdentifier);
        this.totalImagesTextView = findViewById(totalImagesNumberIdentifier);

        ActionBar actionBar = setupActionBar();
        Toolbar photoNumberToolbar = findViewById(photoNumberToolbarIdentifier);

        AppBarLayout appBarLayout = findViewById(appBarLayoutIdentifier);
        appBarLayout.bringToFront();

        String serializePluginOptions = this.getIntent().getStringExtra("options");

        ArrayList<String> photoUrls = new ArrayList<String>();

        try {

            JSONObject options = new JSONObject(serializePluginOptions);

            JSONArray urlsArray = options.getJSONArray("urls");
            int selectedPicture = options.optInt("selectedPicture");

            for (int i = 0; i < urlsArray.length(); i++) {
                String url = urlsArray.getString(i);
                photoUrls.add(url);
            }

            this.totalImagesTextView.setText(String.valueOf(urlsArray.length()));

            photos = new Photos(photoUrls);

            PhotoGalleryAdapter photoGalleryAdapter = new PhotoGalleryAdapter(this, photos);

            viewPager = findViewById(pagerLayoutIdentifier);
            viewPager.addOnPageChangeListener(this);
            viewPager.setGestureListener(new PhotoGalleryGestureListener(actionBar, photoNumberToolbar));
            viewPager.setAdapter(photoGalleryAdapter);
            viewPager.setCurrentItem(selectedPicture);


        } catch (JSONException e) {

            // TODO : Manage missing options issue.
            Log.e(TAG, "Issue trying to get options");
        }
    }

    /**
     * Occurs when the activity create options menu
     * @param menu The options menu to create
     * @return true if menu create, else false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        int menuIdentifier = getResourceIdentifier("menu_photo_gallery", "menu");
        getMenuInflater().inflate(menuIdentifier, menu);

        return true;
    }

    /**
     * Occurs when options item selected
     * @param item The selected item
     * @return True if handled, else false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Integer shareMenuIdentifier = getResourceIdentifier("action_share", "id");
        Integer selectedMenuItemIdentifier = item.getItemId();

        if(selectedMenuItemIdentifier.intValue() == shareMenuIdentifier.intValue()){
            shareImage();
            return true;
        }

        if(android.R.id.home == selectedMenuItemIdentifier.intValue()){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Occurs when activity launched has a result
     * @param requestCode The request code
     * @param resultCode The result code
     * @param data The data from activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Gets resource identifier function of a name and a type
     *
     * @param resourceName the resource name
     * @param resourceType the resource type (layout, drawable, values...)
     * @return The resource identifier
     */
    private int getResourceIdentifier(String resourceName, String resourceType) {
        return getApplication().getResources().getIdentifier(resourceName, resourceType, getApplication().getPackageName());
    }

    /**
     * Share the image
     */
    private void shareImage(){

        LinearLayout currentView = this.viewPager.findViewWithTag(this.photos.getSelectedPhoto());
        PhotoView p = currentView.findViewById(this.getResourceIdentifier("item_photo_view", "id"));

        Uri uri = this.getLocalBitmapUri(p);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/*");

        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        Intent chooser = Intent.createChooser(sharingIntent, "Share");
        startActivityForResult(chooser, ShareRequestCode);
    }

    /**
     * Create Local Image due to Restrictions
     *
     * @param imageView
     *
     * @return
     */
    public Uri getLocalBitmapUri(PhotoView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;

        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS
                    ), "share_image_" + System.currentTimeMillis() + ".jpeg");

            file.getParentFile().mkdirs();

            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();

            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    /**
     * Setup the action bar
     * @return The setup action bar
     */
    private ActionBar setupActionBar(){
        int toolbarIdentifier = getResourceIdentifier("photo_gallery_toolbar", "id");

        Toolbar toolbar = findViewById(toolbarIdentifier);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        return actionBar;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.photos.setSelectedPhoto(position);
        this.currentPageTextView.setText(String.valueOf(position + 1));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}