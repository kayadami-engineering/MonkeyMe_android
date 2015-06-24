package com.example.basak.monkeyme;

import android.graphics.Bitmap;

/**
 * Created by basak on 2015-04-15.
 */
public class GridItem {
    String Name;
    String Picture;
    String Keyword;
    String Hint;
    String Date;
    String g_no;
    Bitmap ProfileImage;


    GridItem(String Picture, String Name)
    {
        this.Picture = Picture;
        this.Name = Name;
        this.ProfileImage = null;
    }
    GridItem(String g_no, String Picture, String Keyword, String Hint, String Date){
        this.Picture = Picture;
        this.g_no = g_no;
        this.Keyword = Keyword;
        this.Hint = Hint;
        this.Date = Date;
    }
}