package ir.hajj.virtualhajj_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShowVideo extends AppCompatActivity {
    VideoView vid;
    MediaController mediaController;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_video);

        Bundle bundle=getIntent().getExtras();

        vid=findViewById(R.id.video);
        playvideo("http://virtual.amoozeshbeseh.ir/Daily/"+bundle.getString("video"));
    }

    public void playvideo(String videopath) {
        try {
            progressDialog = ProgressDialog.show(ShowVideo.this, "",
                    "درحال دریافت...", false);
            progressDialog.setCancelable(true);
            getWindow().setFormat(PixelFormat.TRANSLUCENT);

            mediaController = new MediaController(ShowVideo.this);

            Uri video = Uri.parse(videopath);
            vid.setMediaController(mediaController);
            vid.setVideoURI(video);

            vid.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mp) {
                    progressDialog.dismiss();
                    vid.start();
                }
            });

        } catch (Exception e) {
            progressDialog.dismiss();
            System.out.println("خطا در نمایش :" + e.getMessage());
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
