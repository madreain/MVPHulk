package com.madreain.hulk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */

public class BitmapUtils {
    /**
     * @param srcPath 图片地址
     * @param KB      压缩到多少 KB 级别
     * @return
     */
    public static byte[] getImage(Context context, String srcPath, int KB) {
        return getImage(context, srcPath, KB, "");
    }

    /**
     * 二次采样图片压缩
     *
     * @param filepath
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap compressImageFromFile(String filepath, int newWidth, int newHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 第一次采样:仅仅得到原图的宽度和高度，从而获取缩放比例
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);
        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;
        int ratioWidth = originalWidth / newWidth;
        int ratioHeight = originalHeight / newHeight;
        // 缩放比例(取值大的)
        options.inSampleSize = (ratioWidth > ratioHeight) ? ratioWidth : ratioHeight;
        // 第二次采样：为了获取缩放后图片的所有像素点
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filepath, options);
    }

    /**
     * @param context
     * @param srcPath  图片地址
     * @param KB       压缩到多少 KB 级别
     * @param waterStr 水印
     *                 ---水印---
     */
    public static byte[] getImage(Context context, String srcPath, int KB, String waterStr) {
        int degree = readPictureDegree(srcPath);
        File file = new File(srcPath);
        Bitmap bitmap;
        if (file.length() > KB * 1024) {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            //开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            double YS = Math.sqrt(file.length() / 1024 / KB);//根据原图跟需要压缩的要求进行计算压缩比例，最后除以2,留一部分给大小给质量压缩
            double hh = h / YS;//这里设置高度为原图的一半
            double ww = w / YS;//这里设置宽度为原图的一半
            //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;//be=1表示不缩放
            if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0) be = 1;
            newOpts.inSampleSize = be;//设置缩放比例
            newOpts.inPreferredConfig=Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

            //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            newOpts.inJustDecodeBounds = false;
            bitmap = toturn(bitmap, degree);
        } else {
            bitmap = BitmapFactory.decodeFile(srcPath);
            bitmap = toturn(bitmap, degree);
        }
        return compressImage(context, bitmap, KB, TextUtils.isEmpty(waterStr) ? "" : waterStr + "\r\n" + DUtils.dateFormatAll(System.currentTimeMillis() + ""));//压缩好比例大小后再进行质量压缩
    }

    /**
     * @param image
     * @param KB    压缩到多少 KB 级别
     * @return
     */
    /**
     * @param context
     * @param image
     * @param KB
     * @param waterStr 水印字符串   自定义换行位置添加"\r\n"
     * @return
     */
    private static byte[] compressImage(Context context, Bitmap image, int KB, String waterStr) {
        if (!TextUtils.isEmpty(waterStr))
            image = drawTextToLeftBottom(context, image, waterStr);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length > KB * 1024 && options > 0) {  //循环判断如果压缩后图片是否大于300kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                options -= 5;//每次都减少10
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            }
            //ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            //Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
            return baos.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap drawTextToLeftBottom(Context context, Bitmap bitmap, String text) {
        int imgWidth = bitmap.getWidth();
        int screenWidth = SizeUtils.getScreenWidth(context);
        int size = SizeUtils.dp2px(16) * imgWidth / screenWidth;
        int padd = SizeUtils.dp2px(10) * imgWidth / screenWidth;

        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(size);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint, bounds, padd, bitmap.getHeight() - padd);
    }

    //图片上绘制文字
    private static Bitmap drawTextToBitmap(Bitmap bitmap, String text, TextPaint paint, Rect bounds, int paddingLeft, int paddingTop) {
        Bitmap.Config bitmapConfig = bitmap.getConfig();
        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        paint.setAntiAlias(true);
        StaticLayout layout = new StaticLayout(text, paint, bitmap.getWidth() * 4 / 5, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
        canvas.translate(paddingLeft, paddingTop - layout.getHeight());
        layout.draw(canvas);
        return bitmap;
    }

    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转角度
     *
     * @param img
     * @param degree 角度
     * @return
     */
    public static Bitmap toturn(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree); /*翻转90度*/
        int width = img.getWidth();
        int height = img.getHeight();
        img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        return img;
    }
}
