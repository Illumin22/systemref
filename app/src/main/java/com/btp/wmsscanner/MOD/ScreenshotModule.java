package com.btp.wmsscanner.MOD;

import android.graphics.Bitmap;
import android.view.View;

class ScreenshotModule {

        public static Bitmap takeSc(View v){
            v.setDrawingCacheEnabled(true);
            v.buildDrawingCache(true);
            Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
            v.setDrawingCacheEnabled(false);
            return b;
        }

        public static Bitmap takeScRootView(View v){
            return takeSc(v.getRootView());
        }
}
