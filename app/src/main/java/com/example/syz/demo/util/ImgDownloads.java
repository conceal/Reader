package com.example.syz.demo.util;
 
/**
 * 参考于network：https://blog.csdn.net/android_yck/article/details/79395267
 */
 
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


 
public class ImgDownloads {

    private static String filePath;
    private static Bitmap mBitmap;
    private static String mFileName="sshs";
    private static String mSaveMessage;
    private final static String TAG = "GifShowActivity";
    private static Context context;

    private static ProgressDialog mSaveDialog = null;

    public static void downloadImg(Context contexts,String filePaths){
        context = contexts;
        filePath = filePaths;
        mSaveDialog = ProgressDialog.show(context, "保存图片", "图片正在保存中，请稍等...", true);
        new Thread(saveFileRunnable).start();
    }

    private static Runnable saveFileRunnable = new Runnable(){
        @Override
        public void run() {
            try {
                File file = Glide.with(context)
                    .load(filePath)
                    .downloadOnly(300, 300)
                    .get();
//                mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));

                if (!TextUtils.isEmpty(filePath)) {//网络图片

                    //对资源链接
                    URL url=new URL(filePath);
                    //打开输入流
                    InputStream inputStream=url.openStream();
                    //对网上资源进行下载转换位图图片
                    mBitmap= BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                }
                saveFile(mBitmap, file);
                mSaveMessage = "图片保存成功！";
            } catch (IOException e) {
                mSaveMessage = "图片保存失败！";
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            messageHandler.sendMessage(messageHandler.obtainMessage());
        }
    };
    private static Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSaveDialog.dismiss();
            Log.d(TAG, mSaveMessage);
            Toast.makeText(context, mSaveMessage, Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 保存文件
     * @param bm
     * @param file
     * @throws IOException
     */
    public static void saveFile(Bitmap bm, File file) throws IOException {
        File dirFile = new File(Environment.getExternalStorageDirectory().getPath());
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".gif";
        File myCaptureFile = new File(Environment.getExternalStorageDirectory().getPath() +"/DCIM/Camera/"+ fileName);
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileOutputStream = new FileOutputStream(myCaptureFile);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
//        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
//        bos.flush();
//        bos.close();
//
        //把图片保存后声明这个广播事件通知系统相册有新图片到来
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }
    /**
     * 保存图片到图库
     * @param bmp
     */
    public static void saveFile( Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                mFileName);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".gif";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
 
            e.printStackTrace();
        }
 
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(file.getPath()))));
    }
}