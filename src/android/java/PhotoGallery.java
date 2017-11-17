package tech.authfix.cordova.plugins;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.Context;
import android.Manifest;
import android.content.pm.PackageManager;


public class PhotoGallery extends CordovaPlugin {
        
        public static final int PERMISSION_DENIED_ERROR = 20;
    
        public static final String WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        public static final String READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    
        public static final int REQ_CODE = 0;
    
        protected JSONArray args;
        protected CallbackContext callbackContext;
    
        @Override
        public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
            
            if(!action.equals("show")){
                return false;
            }
            
            this.args = args;
            this.callbackContext = callbackContext;

            try {
                JSONObject options = this.args.optJSONObject(2);
            } catch(JSONException exception) { }

            if ((cordova.hasPermission(READ) && cordova.hasPermission(WRITE))) {
                this.launchActivity();
            } else {
                this.getPermission();
            }
            return true;
            
        }
    
        protected void getPermission() {
            cordova.requestPermissions(this, REQ_CODE, new String[]{WRITE, READ});
        }
    
        protected void launchActivity() throws JSONException {
            Intent i = new Intent(this.cordova.getActivity(), tech.authfix.cordova.plugins.photogallery.PhotoGalleryActivity.class);
    
            i.putExtra("options", this.args.optJSONObject(0).toString());
    
            this.cordova.getActivity().startActivity(i);
            this.callbackContext.success("");
        }
    
        @Override
        public void onRequestPermissionResult(int requestCode, String[] permissions,
                                                int[] grantResults) throws JSONException {
            for(int r:grantResults) {
                if(r == PackageManager.PERMISSION_DENIED) {
                    this.callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, PERMISSION_DENIED_ERROR));
                    return;
                }
            }
    
            switch(requestCode) {
                case REQ_CODE:
                    launchActivity();
                    break;
            }
    
        }
}