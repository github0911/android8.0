package com.example.android80;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android80.api.MovieService;
import com.example.android80.entity.MovieEntity;
import com.google.gson.Gson;
import com.google.zxing.client.android.encode.QRCodeEncoder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static NotificationManagerCompat notificationManager;
    String channelId = "channelId";
    int notifyId = 800;
    ArrayList<Integer> ids = new ArrayList<>();
    private ImageView ivQrCode;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_send);
        textView.setOnClickListener(this);
        findViewById(R.id.tv_clear).setOnClickListener(this);
        ivQrCode = findViewById(R.id.iv_qrcode);
        findViewById(R.id.btn_get).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_send: {
                notifyId++;
                ids.add(notifyId);
                sendNotification();
                break;
            }
            case R.id.tv_clear: {
                clearNotification();
                break;
            }
            case R.id.btn_get: {
                getMovie();

                break;
            }
            default:
                break;
        }
    }

    private void clearNotification() {
        if (notificationManager == null) {
            notificationManager = NotificationManagerCompat.from(this);
        }
        for (Integer id : ids) {
            notificationManager.cancel(id);
        }
        ids.clear();
    }

    private void sendNotification() {

        if (notificationManager == null) {
            notificationManager = NotificationManagerCompat.from(this);
        }
        // 设置通知的内容
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //手机系统版本 26+
            //v4 兼容包使用方式
//            builder = new NotificationCompat.Builder(this, channelId);
            builder.setChannelId(channelId);
            //高版本api中使用方式
            // Create the NotificationChannel
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);
//            mChannel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = (NotificationManager) getSystemService(
//                    NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(mChannel);
        }

        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        String title = getString(R.string.title);
        String content = getString(R.string.content);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle(title + notifyId);
        builder.setContentText(content + notifyId);
        builder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        // 显示通知
        notificationManager.notify(notifyId, builder.build());
    }

    public void encodeCode(View view) {
        try {

            Bitmap mBitmap = QRCodeEncoder.encodeAsBitmap("http://www.google.com", 400);
            ivQrCode.setImageBitmap(mBitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMovie() {

        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService service = retrofit.create(MovieService.class);

        Call call = service.getTopMovie(0, 1);
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                MovieEntity entity = response.body();
                textView.setText(new Gson().toJson(entity.getTitle()));
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });

    }

}
