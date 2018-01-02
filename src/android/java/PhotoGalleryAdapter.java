package tech.authfix.cordova.plugins.photogallery;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PhotoGalleryAdapter extends PagerAdapter {

    /**
     * The context of the adapter
     */
    private final Context context;

    /**
     * The layout inflate
     */
    private final LayoutInflater layoutInflater;

    /**
     * The photo list
     */
    private final Photos photos;

    /**
     * Initialize a default adapter
     * @param context The context of the adapter
     * @param photos The photo list
     */
    PhotoGalleryAdapter(Context context, Photos photos) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.photos = photos;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        int itemLayoutIdentifier = getResourceIdentifier("item_photo", "layout");
        int photoViewIdentifier = getResourceIdentifier("item_photo_view", "id");

        View itemView = layoutInflater.inflate(itemLayoutIdentifier, container, false);
        itemView.setTag(position);
        PhotoView imageView = itemView.findViewById(photoViewIdentifier);

        List<String> urls = photos.getPhotos();

        Picasso.with(container.getContext()).load(urls.get(position)).fit().centerInside().into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    /**
     * Get number of items inside the adapter
     * @return The number of items
     */
    @Override
    public int getCount() {
        return photos.getNumberOfPhotos();
    }

    /**
     * Check if the view is for the object
     * @param view The view
     * @param object The object
     * @return True if the view is for the object, else false
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * Gets resource identifier function of a name and a type
     * @param resourceName the resource name
     * @param resourceType the resource type (layout, drawable, values...)
     * @return The resource identifier
     */
    private int getResourceIdentifier(String resourceName, String resourceType){
        return context.getApplicationContext().getResources().getIdentifier(resourceName, resourceType, context.getApplicationContext().getPackageName());
    }
}
