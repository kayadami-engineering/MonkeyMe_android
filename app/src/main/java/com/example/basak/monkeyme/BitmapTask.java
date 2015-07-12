package com.example.basak.monkeyme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * Created by basak on 2015-07-01.
 */
public class BitmapTask extends AsyncTask<Void, Void ,Bitmap>{
    private final WeakReference<ImageView> imageViewReference;
    protected InputStream istream;
    protected InputStream istream2;
    private String ImgUrl;
    private int gridSize;
    public Bitmap prof;

    public BitmapTask(ImageView imageView, String ImgUrl, int gridSize, Bitmap prof){
        imageViewReference = new WeakReference<ImageView>(imageView);//가비지컬렉터를 보장하기위함
        this.ImgUrl = ImgUrl;
        this.gridSize = gridSize;
        this.prof = prof;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try{
            istream = new URL(ImgUrl).openStream();
            istream2 = new URL(ImgUrl).openStream();

        } catch(Exception e){
            Log.e("Error", "Opening Image istream Error");
        }
        Log.i("first", ImgUrl);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(istream, null, options);
        gridSize = 100;
        options.inSampleSize = calculateInSampleSize(options, gridSize, gridSize);  // 2,3번째 인자가 기준 해상도. 폰 해상도에 따라하면 이미지가 꺠지는경우가 있어서 일단 100으로
        options.inJustDecodeBounds = false;
        Log.i("val","gridSize:"+Integer.toString(this.gridSize)+" sample:"+Integer.toString(options.inSampleSize)+" height:"+Integer.toString(options.outHeight)+" width:"+Integer.toString(options.outWidth));
        return BitmapFactory.decodeStream(istream2, null, options);
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 8;
        //Log.i("val","height:"+Integer.toString(height)+" width:"+Integer.toString(width));
        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
           // Log.i("sampleSize", Integer.toString(inSampleSize));
        }

        return inSampleSize;
    }
}
