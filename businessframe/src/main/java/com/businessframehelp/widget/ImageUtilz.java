package com.businessframehelp.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import java.io.InputStream;

/**
 * Created by King6rf on 2017/7/7.
 */

public class ImageUtilz {
    public static Drawable loadImageFromAsserts(Context context, String filename, int originalDensity) {
        Drawable drawable = null;
        InputStream is = null;

        // set options to resize the image
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDensity = originalDensity;

        try {
            is = context.getResources().getAssets().open(filename);
            drawable = Drawable.createFromResourceStream(context.getResources(), null, is, filename, opts);
        } catch (Throwable e) {
            // handle
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Throwable e1) {
                    // ingore
                }
            }
        }
        return drawable;
    }
    public static Drawable loadImageFromAsserts(Context context, String filename) {
        return loadImageFromAsserts(context,filename, DisplayMetrics.DENSITY_DEFAULT);
    }
}
