package com.google.cloudservices;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

class Utils
{
    /**
     * Convert bitmap to string with maximum image size.
     *
     * @param  bitmap            Bitmap that you want to convert to string.
     * @param  IMAGE_MAX_SIZE    Maximum image size.
     * @return Encoded String Image .
     */
    public  String getStringImage(Bitmap bitmap, final int IMAGE_MAX_SIZE)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1)
        {
            width = IMAGE_MAX_SIZE;
            height = (int) (width / bitmapRatio);
        }
        else
        {
            height = IMAGE_MAX_SIZE;
            width = (int) (height * bitmapRatio);
        }

        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}