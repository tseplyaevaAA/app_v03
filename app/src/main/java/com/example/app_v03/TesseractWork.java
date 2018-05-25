package com.example.app_v03;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.ContentValues.TAG;

public class TesseractWork {
    public TessBaseAPI api;
    public String result = "0000";
    private static final String lang = "eng";
    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/TesseractSample/";
 //   private static final String TESSDATA = "tessdata";


    public void startOCR(byte[] bytes) {
        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4; // 1 - means max size. 4 - means maxsize/4 size. Don't use value <4, because you need more memory in the heap to store your data.
            Bitmap  bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

      //      String IMGS_PATH = Environment.getExternalStorageDirectory().toString() + "/TesseractSample/imgs";
        //    prepareDirectory(IMGS_PATH);

         //   String img_path = IMGS_PATH + "/ocr.jpg";

       //   Uri outputFileUri = Uri.fromFile(new File(img_path));

       //     File newfile = savebitmap(bitmap, outputFileUri.toString());

            result = extractText(bitmap);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public String extractText(Bitmap bmp) {
        try {
            api = new TessBaseAPI();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            if (api == null) {
                Log.e(TAG, "TessBaseAPI is null. TessFactory not returning tess object.");
            }
        }


       api.init(DATA_PATH, lang);

      //EXTRA SETTINGS
//       // For example if we only want to detect numbers
      //  api.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");
//
//        //blackList Example
//        tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-qwertyuiop[]}{POIU" +
//                "YTRWQasdASDfghFGHjklJKLl;L:'\"\\|~`xcvXCVbnmBNM,./<>?");

        Log.d(TAG, "Training file loaded");

        api.setImage(bmp);
        String extractedText = "empty result";
        try {
            extractedText = api.getUTF8Text();
            Log.e(TAG, "Trying to extract text.");
        } catch (Exception e) {
            Log.e(TAG, "Error in recognizing text.");
        }
        api.end();
        return extractedText;
    }

   /* public static File savebitmap(Bitmap bmp, String path) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File f = new File(path);
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f;
    }
*/





}