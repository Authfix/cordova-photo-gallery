package tech.authfix.cordova.plugins.photogallery;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class PhotoGalleryGestureListener extends SimpleOnGestureListener {

    private final ActionBar toolbar;

    private final Toolbar photoNumberToolbar;

    public PhotoGalleryGestureListener(ActionBar toolbar, Toolbar photoNumberToolbar) {
        this.toolbar = toolbar;
        this.photoNumberToolbar = photoNumberToolbar;
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
            photoNumberToolbar.setVisibility(View.INVISIBLE);
        }
        else {
            toolbar.show();
            photoNumberToolbar.setVisibility(View.VISIBLE);
        }
    }
}
