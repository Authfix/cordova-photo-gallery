package tech.authfix.cordova.plugins.photogallery;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class PhotoGalleryViewPager extends ViewPager{

    private GestureDetectorCompat gestureDetectorCompat;

    /**
     * Initialize a default {@link PhotoGalleryViewPager}.
     * @param context The {@link Context} the {@link ViewPager} will use.
     */
    public PhotoGalleryViewPager(Context context) {
        super(context);
    }

    /**
     * Initialize a default {@link PhotoGalleryViewPager}.
     * @param context The {@link Context} the {@link ViewPager} will use.
     * @param attrs Attributes of the component
     */
    public PhotoGalleryViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Occurs when the control intercept a touch event
     * @param ev The touch event values
     * @return True if intercept, else false
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            // Sometimes ViewPager will fire an exception with PhotoView
            // https://github.com/chrisbanes/PhotoView
            return false;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if(gestureDetectorCompat != null){
            gestureDetectorCompat.onTouchEvent(ev);
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * Setup a gesture listener
     * @param listener The listener to use
     */
    public void setGestureListener(PhotoGalleryGestureListener listener){
        gestureDetectorCompat = new GestureDetectorCompat(this.getContext(), listener);
    }
}