package tech.authfix.cordova.plugins.photogallery;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotoGalleryActivity extends Activity {

    /**
     * Get the tag of this activity
     */
    private static final String TAG = PhotoGalleryActivity.class.getName();

    /**
     * The view pager
     */
    private ViewPager viewPager;

    /**
     * The options
     */
    private JSONObject options;

    /**
     * Occurs when the activity is being created
     * @param savedInstanceState The save parameters
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int photoLayoutIdentifier = getResourceIdentifier("activity_photo_gallery", "layout");
        int pagerLayoutIdentifier = getResourceIdentifier("photo_gallery.pager", "id");

        setContentView(photoLayoutIdentifier);

        String serializePluginOptions = this.getIntent().getStringExtra("options");

        ArrayList<String> photoUrls = new ArrayList<String>();

        try {

            options = new JSONObject(serializePluginOptions);

            JSONArray urlsArray = options.getJSONArray("urls");
            int selectedPicture = options.getInt("selectedPicture");

            for (int i = 0; i < urlsArray.length(); i++) {
                String url = urlsArray.getString(i);
                photoUrls.add(url);
            }

            Photos photos = new Photos(photoUrls);
            PhotoGalleryAdapter photoGalleryAdapter = new PhotoGalleryAdapter(this, photos);

            viewPager = findViewById(pagerLayoutIdentifier);
            viewPager.setAdapter(photoGalleryAdapter);
            viewPager.setCurrentItem(selectedPicture);

        } catch (JSONException e) {

            // TODO : Manage missing options issue.
            Log.e(TAG, "Issue trying to get options");
            return;
        }
    }

    /**
     * Gets resource identifier function of a name and a type
     * @param resourceName the resource name
     * @param resourceType the resource type (layout, drawable, values...)
     * @return The resource identifier
     */
    private int getResourceIdentifier(String resourceName, String resourceType){
        return getApplication().getResources().getIdentifier(resourceName, resourceType, getApplication().getPackageName());
    }
}