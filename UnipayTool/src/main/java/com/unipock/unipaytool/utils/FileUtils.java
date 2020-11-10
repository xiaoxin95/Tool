package com.unipock.unipaytool.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    /**
     * bitmap 转 文件保存
     *
     * @param bitmap
     * @param newImgPath
     * @return
     */
    public static File saveBitmapFile(Bitmap bitmap, String newImgPath) {
        //复制Bitmap  因为png可以为透明，jpg不支持透明，把透明底明变成白色
        //主要是先创建一张白色图片，然后把原来的绘制至上去
        Bitmap outB = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(outB);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        File file = new File(newImgPath);

        // 判断文件是否存在
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (outB.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    public static File loadBitmapFromView(Activity activity,View v,String newImgPath) {

        try {
            int w = v.getWidth();
            int h = v.getHeight();
            Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bmp);
            c.drawColor(Color.WHITE);
            /** 如果不设置canvas画布为白色，则生成透明 */
            v.layout(0, 0, w, h);
            v.draw(c);
            // 保存文件
            return saveBitmapFile(bmp,newImgPath);
//            MediaStore.Images.Media.insertImage(activity.getContentResolver(), bmp, "title", "description");
        } catch (Exception ex) {
            ToastUtils.showShort(activity,"上傳失敗");
        }

        return null;

    }


}
