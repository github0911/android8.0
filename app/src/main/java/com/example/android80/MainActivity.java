package com.example.android80;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android80.activity.ExampleActivity;
import com.example.android80.activity.base.BaseActivity;
import com.example.android80.activity.login.LoginActivity;
import com.example.android80.activity.movie.MovieActivity;
import com.example.android80.activity.rxjava.OperatorsActivity;
import com.google.zxing.client.android.encode.QRCodeEncoder;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static NotificationManagerCompat notificationManager;
    String channelId = "channelId";
    int notifyId = 800;
    ArrayList<Integer> ids = new ArrayList<>();
    private ImageView ivQrCode;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag(TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
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

                startActivity(LoginActivity.getIntent(MainActivity.this));
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

    public void toExampleActivity(View view) {
        startActivity(ExampleActivity.getIntent(MainActivity.this));
    }

    public void toRxJavaActivity(View view) {
        startActivity(new Intent(MainActivity.this, OperatorsActivity.class));
    }

}
