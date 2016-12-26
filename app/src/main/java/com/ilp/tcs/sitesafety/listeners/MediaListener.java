package com.ilp.tcs.sitesafety.listeners;

import com.ilp.tcs.sitesafety.modals.ChildItem;

/**
 * Created by 1241575 on 5/31/2016.
 * MediaListener interface to listen to various media received by camera and gallery.
 */
public interface MediaListener {
    /**
     * @param childItem Child item that is trying to open gallery
     */
    void onOpenGallery(ChildItem childItem);

    /**
     * @param childItem Child item that is trying to open camera
     */
    void onOpenCamera(ChildItem childItem);

    /**
     * @param path    path of the image that is updated
     * @param caption caption of image
     */
    void onUpdateImage(String path, String caption);
}
