package com.example.syz.demo.screenPage.mineScreen;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syz.demo.util.HttpUtil;
import com.example.syz.demo.util.Text;
import com.example.syz.demo.R;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PersonInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView head_photo;
    private TextView nickname;
    private ImageView boy_img;
    private ImageView girl_img;
    private TextView birthday;
    private TextView motto;
    private ImageView back;
    private TextView post;
    private View bindingView;
    private String phone;
    private String password;
    private String Sex = "男";
    private String backMsg;
    private File headPhotoFile;
    private String TAG = "PersonInfoActivity";
    private Uri imageUri;
    private String list[] = {"打开相机", "从相册中选择"};
    private String mFileName;
    private String headImg;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private boolean haveHeadPhoto = false;
    private String register_uri = "https://www.apiopen.top/createUser?key=00d91e8e0cca2b76f515926a36db68f5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info_acitivy);

        Intent registerIntent = getIntent();
        phone = registerIntent.getStringExtra("phone");
        password = registerIntent.getStringExtra("password");

        initView();

    }

    private void initView() {
        head_photo = (ImageView) findViewById(R.id.head_photo);
        nickname = (TextView) findViewById(R.id.nickname);
        boy_img = (ImageView) findViewById(R.id.boy_img);
        girl_img = (ImageView) findViewById(R.id.girl_img);
        birthday = (TextView) findViewById(R.id.birthday);
        back = (ImageView) findViewById(R.id.back);
        post = (TextView) findViewById(R.id.post);
        motto = (EditText) findViewById(R.id.motto);
        bindingView = (View) findViewById(R.id.bindingView);

        boy_img.setImageResource(R.drawable.boy);

        boy_img.setOnClickListener(this);
        girl_img.setOnClickListener(this);
        head_photo.setOnClickListener(this);
        birthday.setOnClickListener(this);
        back.setOnClickListener(this);
        post.setOnClickListener(this);

        LightStatusbar();
    }

    /**
     * 创建Menu
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.success, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.post:
                register();
                break;
            case R.id.boy_img:
                boy_img.setImageResource(R.drawable.boy);
                girl_img.setImageDrawable(null);
                Sex = "男";
                break;
            case R.id.girl_img:
                boy_img.setImageDrawable(null);
                girl_img.setImageResource(R.drawable.girl);
                Sex = "女";
                break;
            case R.id.head_photo:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("打开方式");
                alertDialog.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                openCamera();
                                break;
                            case 1:
                                if (ContextCompat.checkSelfPermission(PersonInfoActivity.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(PersonInfoActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                                } else {
                                    openAlbum();
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
                Dialog dialog = alertDialog.create();
                dialog.show();
                break;
            case R.id.birthday:
                selectTime();
                break;
            default:
                break;
        }
    }

    /**
     * PersonInfoActivity页面选择生日日期，通过一个日期选择器选择
     */
    private void selectTime() {
        final Calendar calendar = Calendar.getInstance();
        final StringBuffer time = new StringBuffer();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(PersonInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                time.append(year+"-"+(month+1)+"-"+dayOfMonth);
                birthday.setText(time);
                time.setLength(0);
            }
        }, year, month, day);

        datePickerDialog.show();
    }


    /**
     * 打开相机
     */
    private void openCamera() {
        mFileName = "WYK" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        File outputImage = new File(getExternalCacheDir(), mFileName);
        try{
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            Log.d(TAG, Log.getStackTraceString(e));
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(PersonInfoActivity.this, "com.example.cameraalbumtest.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent1 = new Intent("android.media.action.IMAGE_CAPTURE");
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent1, TAKE_PHOTO);
    }

    /**
     * 打开相册，用于从相册选择图片
     */
    private void openAlbum() {
        Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
        intent2.setType("image/*");
        startActivityForResult(intent2, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        displayImage(bitmap);
                    } catch (Exception e) {
                        Log.d(TAG, Log.getStackTraceString(e));
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4及以上系统
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
    *4.4及以上的系统的图片处理方式,4.4以上的版本选取相册中的图片返回的是一个封装过的Uri，需要对这个Uri进行解析
    * @param data 图片
     */
    private void handleImageOnKitKat(Intent data) {
        String impagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是documen类型的Uri，就通过document id进行处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];   //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                impagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                impagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            impagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            impagePath = uri.getPath();
        }

        if (impagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(impagePath);
            displayImage(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 4.4及以下的系统的图片处理方式
     * @param data
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            displayImage(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取图片的路径
     * @param uri 从data中获取的经过处理的图片uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null , selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }

        return path;
    }


    /**
     *在head_photo中展示经过RoundedBitmapDrawable处理的图片
     * @param bitmap 图片的bitmap
     */
    private void displayImage(Bitmap bitmap) {
        //压缩图片
        Bitmap bitmap1 = compressImage(bitmap);
        //将图片保存成文件
        saveBitmapFile(bitmap1);
        //将图片设置成圆形图片
        RoundedBitmapDrawable circleImage = RoundedBitmapDrawableFactory.create(getResources(), bitmap1);
        circleImage.setCircular(true);
        head_photo.setImageDrawable(circleImage);
    }


    /**
     * 图片质量压缩
     * @param image
     * @return
     */
    private static Bitmap compressPicture(Bitmap image)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100)
        { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     *  图片按比例大小压缩
     * @param image
     * @return
     */
    private Bitmap compressImage (Bitmap image)
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024)
        {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww)
        {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        }
        else if (w < h && h > hh)
        {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);

        return compressPicture(bitmap);// 压缩好比例大小后再进行质量压缩
    }


    private void saveBitmapFile(Bitmap bitmap){
        headPhotoFile = new File(getExternalCacheDir(), mFileName);
        try{
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(headPhotoFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            Log.d(TAG, Log.getStackTraceString(e));
        }
        haveHeadPhoto = true;
    }


    private void register() {
        if (haveHeadPhoto) {
            headImg = headPhotoFile.getPath();
        } else {
            headImg = "http://ww1.sinaimg.cn/large/0077HGE3ly1g0d9el5x7qj30go0aft93.jpg";
        }
        RequestBody requestBody = new FormBody.Builder()
                .add("phone", phone)
                .add("passwd", password)
                .add("img", headImg)
                .add("name", nickname.getText().toString())
                .add("other", Sex)
                .add("other2", birthday.getText().toString())
                .add("text", motto.getText().toString())
                .build();

        HttpUtil.postOkHttpRequest(register_uri, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    backMsg = jsonObject.getString("msg");
                } catch (Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (backMsg.equals("成功!")) {
                            Intent backIntent = new Intent(PersonInfoActivity.this, Loginactivity.class);
                            startActivity(backIntent);
                        } else if (backMsg.equals("用户已注册！")) {
                            Toast.makeText(getApplicationContext(), backMsg, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "请填写正确信息", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void LightStatusbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option =  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        int statusBarHeight = getStatusBarHeight(this);
        ViewGroup.LayoutParams params = bindingView.getLayoutParams();
        params.height = statusBarHeight;
        bindingView.setVisibility(View.VISIBLE);
        bindingView.setLayoutParams(params);
    }

    private int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight;
    }

}
