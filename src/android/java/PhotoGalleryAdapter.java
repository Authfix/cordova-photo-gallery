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
    private final Context _context;

    /**
     * The layout inflate
     */
    private final LayoutInflater layoutInflater;

    /**
     * The photo list
     */
    private final Photos _photos;

    /**
     * Initialize a default adapter
     * @param context The context of the adapter
     * @param photos The photo list
     */
    public PhotoGalleryAdapter(Context context, Photos photos) {
        _context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _photos = photos;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = layoutInflater.inflate(R.layout.item_photo, container, false);

        PhotoView imageView = itemView.findViewById(R.id.item_photo_view);
        List<String> urls = _photos.getPhotos();
        Picasso.with(container.getContext()).load(urls.get(position)).into(imageView);

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
        return _photos.getNumberOfPhotos();
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
}
