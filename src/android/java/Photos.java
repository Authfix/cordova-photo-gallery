package tech.authfix.cordova.plugins.photogallery;

import java.util.List;

public class Photos {

    /**
     * The photos url
     */
    private final List<String> photos;

    /**
     * The selected photo
     */
    private int selectedPhoto;

    /**
     * Initialize a default photos with no pre selected index
     * @param photos List of urls
     */
    public Photos(List<String> photos){
        this(photos, 0);
    }

    /**
     * Initialize a default photo with preselected index
     * @param photos The photos url
     * @param selectedPhoto The selected index
     */
    public Photos(List<String> photos, int selectedPhoto) {
        this.photos = photos;
        this.selectedPhoto = selectedPhoto;
    }

    /**
     * Gets photos
     * @return
     */
    public List<String> getPhotos() {
        return photos;
    }

    /**
     * Gets the number of photos
     * @return The number of photos
     */
    public int getNumberOfPhotos(){
        return photos.size();
    }

    /**
     * Gets the selected photo
     * @return The position of the selected photo
     */
    public int getSelectedPhoto() {
        return selectedPhoto;
    }

    /**
     * Set the selected photo
     * @param selectedPhoto The position of the selected photo
     */
    public void setSelectedPhoto(int selectedPhoto) {
        this.selectedPhoto = selectedPhoto;
    }
}