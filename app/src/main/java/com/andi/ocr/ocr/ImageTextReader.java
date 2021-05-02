package com.andi.ocr.ocr;

import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

/**
 * This class convert the image to text and return the text on image
 */
public class ImageTextReader {

    public static final String TAG = "ImageTextReader";
    public  boolean success;

    private static volatile TessBaseAPI api;

    public static ImageTextReader geInstance(String path, String language, TessBaseAPI.ProgressNotifier progressNotifier) {
        try {
            ImageTextReader imageTextReader=new ImageTextReader();
            api = new TessBaseAPI(progressNotifier);
            imageTextReader.success = api.init(path, language);
            api.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);
            return imageTextReader;
        } catch (Exception e) {
            return null;
        }

    }

    public String getTextFromBitmap(Bitmap bitmap) {
        api.setImage(bitmap);
        String textOnImage;
        try {
            //textOnImage = api.getUTF8Text();
            textOnImage = api.getHOCRText(1);
        } catch (Exception e) {
            return "Scan Failed: WTF: Must be reported to developer!";
        }
        if (textOnImage.isEmpty()) {
            return "Scan Failed: Couldn't read the image\nProblem may be related to Tesseract or no Text on Image!";
        } else return textOnImage;

    }


    public void stop() {
        api.stop();
    }

    public int getAccuracy() {
        return api.meanConfidence();
    }

    public void tearDownEverything() {
        api.end();
    }

    public void clearPreviousImage() {
        api.clear();
    }

}
