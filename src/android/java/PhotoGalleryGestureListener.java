package tech.authfix.cordova.plugins.photogallery;

import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class PhotoGalleryGestureListener extends SimpleOnGestureListener {

    private final ActionBar toolbar;

    public PhotoGalleryGestureListener(ActionBar toolbar) {
        this.toolbar = toolbar;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d("ITEM", "Double tap");
        invertToolbarVisibility();
        return super.onDoubleTap(e);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d("ITEM", "Single tap");
        invertToolbarVisibility();
        return super.onSingleTapConfirmed(e);
    }

    private void invertToolbarVisibility(){

        if(toolbar.isShowing()){
            toolbar.hide();
        }
        else {
            toolbar.show();
        }
    }
}