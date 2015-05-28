package com.example.basak.monkeyme;

import android.graphics.Bitmap;

/**
 * Created by basak on 2015-04-15.
 */
public class GridItem {
    String Name;
    String Picture;
    Bitmap ProfileImage;


    GridItem(String Picture, String Name)
    {
        this.Picture = Picture;
        this.Name = Name;
        this.ProfileImage = null;
    }
}