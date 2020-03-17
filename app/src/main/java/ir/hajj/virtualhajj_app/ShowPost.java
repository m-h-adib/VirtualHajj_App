package ir.hajj.virtualhajj_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.nostra13.universalimageloader.core.ImageLoader;

import ir.hajj.virtualhajj_app.Models.Post;
import ir.hajj.virtualhajj_app.Utility.ApiProvider;
import ir.hajj.virtualhajj_app.Utility.ApiServices;
import ir.hajj.virtualhajj_app.Utility.MyApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShowPost extends AppCompatActivity {

    Post post = new Post();
    TextView title, group, text;
    int size1 = 16;
    float size2 = (float) 1.5;
    ImageView share, plus, mines;
    ImageButton btnPlay, btnForward, btnBackward;
    private double timeElapsed = 0, finalTime = 0;
    private int forwardTime = 10000, backwardTime = 10000;
    private Handler durationHandler = new Handler();
    private SeekBar seekbar;
    MediaPlayer mp;
    TextView duration, duration1;
    VideoView v;
    MediaController mediaController;
    ProgressDialog progressDialog;

    public void changesize(int type) {
        if (type == 1) {
            if (size1 <= 21) {
                size1 += 1;
                size2 += 0.05;
            }
        } else if (type == -1) {
            if (size1 >= 15) {
                size1 -= 1;
                size2 -= 0.05;
            }
        }
        text.setLineSpacing((float) size2, (float) size2);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, size1);
    }

    public void init() {
        text = findViewById(R.id.text);
        plus = findViewById(R.id.plus);
        mines = findViewById(R.id.mines);
        share = findViewById(R.id.share);
        seekbar = findViewById(R.id.songProgressBar);
        btnPlay = findViewById(R.id.btnPlay);
        btnForward = findViewById(R.id.btnForward);
        btnBackward = findViewById(R.id.btnBackward);
        seekbar = findViewById(R.id.songProgressBar);
        duration = findViewById(R.id.songCurrentDurationLabel);
        duration1 = findViewById(R.id.songTotalDurationLabel);
        v = findViewById(R.id.video);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();

        init();
        changesize(0);


        ApiProvider apiProvider = new ApiProvider();
        ApiServices apiServices = apiProvider.GetApiServices();

        Call<Post> call;
        if (bundle.getString("type").equals("post"))
            call = apiServices.ShowItem(bundle.getInt("id"));
        else
            call = apiServices.ShowNews(bundle.getInt("id"));
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                post = response.body();

                final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
                AppBarLayout appBarLayout = findViewById(R.id.app_bar);
                appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    boolean isShow = false;
                    int scrollRange = -1;

                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (scrollRange == -1) {
                            scrollRange = appBarLayout.getTotalScrollRange();
                        }
                        if (scrollRange + verticalOffset == 0) {
                            collapsingToolbarLayout.setTitle(post.getTitle());
                            isShow = true;
                        } else if (isShow) {
                            collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                            isShow = false;
                        }
                    }
                });
                ImageView Pic = findViewById(R.id.Pic);
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage("http://virtual.amoozeshbeseh.ir/Files/" + post.getPic(), Pic);
                if (post.getText().equals("")) {
                    CardView text_box = findViewById(R.id.text_box);
                    text_box.setVisibility(View.GONE);
                } else {
                    text.setText(Html.fromHtml(post.getText()));
                }
                title = findViewById(R.id.title);
                title.setText(post.getTitle());
                group = findViewById(R.id.group);
                group.setText(post.getGroupName());
                if (post.getAudio().equals(null) || post.getAudio().equals("")) {
                    CardView audio_box = findViewById(R.id.audio_box);
                    audio_box.setVisibility(View.GONE);
                }
                if (post.getVideo().equals(null) || post.getVideo().equals("")) {
                    CardView video_box = findViewById(R.id.video_box);
                    video_box.setVisibility(View.GONE);
                } else {
                    playvideo("http://virtual.amoozeshbeseh.ir/Files/" + post.getVideo());
                }


                //mp=new MediaPlayer();
                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mp != null) {
                            if (mp.isPlaying()) {
                                mp.pause();
                                btnPlay.setImageResource(R.drawable.play);
                            } else {
                                mp.start();
                                btnPlay.setImageResource(R.drawable.pause);
                            }
                        } else {
                            play(post.getAudio());
                        }
                    }
                });
                btnForward.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((timeElapsed + forwardTime) > 0) {
                            timeElapsed = timeElapsed - backwardTime;
                            mp.seekTo((int) timeElapsed);
                        }


                    }
                });
                btnBackward.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((timeElapsed + forwardTime) <= finalTime) {
                            timeElapsed = timeElapsed + forwardTime;
                            mp.seekTo((int) timeElapsed);
                        }

                    }
                });
                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (progress == finalTime)
                            btnPlay.setImageResource(R.drawable.play);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(ShowPost.this, "خطا در دریافت اطلاعات"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        ///share
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApp.IsOnline(ShowPost.this)) {

                    //Uri obj1=Uri.parse("http://taghzieh.daheshti.ir/Files/" + cursor.getString(cursor.getColumnIndex("Pic")));
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/html");
                    String text = post.getTitle() + "<br />متن مطلب:<br />" + post.getText();

                    text += "برای دسترسی مطالب بیشتر به نرم افزار کاروان مجازی مراجعه نمایید<br />";
                    //text+="<br />لینک تصویر غذا:<br />http://taghzieh.redbee.ir/Files/"+cursor.getString(cursor.getColumnIndex("Pic"));
                    text += "<br />لینک دانلود نرم افزار: <br />http://virtaul.amoozeshbeseh.ir/app.apk";
                    intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(text));
                    startActivity(Intent.createChooser(intent, "اشتراک مطلب"));

                } else {
                    Snackbar.make(findViewById(android.R.id.content), "لطفا اتصال اینترنت را بررسی نمایید", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        ////end share

        ////text size
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changesize(1);
            }
        });
        mines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changesize(-1);
            }
        });
        ///end size
    }

    public void playvideo(String videopath) {
        try {
            progressDialog = ProgressDialog.show(ShowPost.this, "",
                    "درحال دریافت...", false);
            progressDialog.setCancelable(true);
            getWindow().setFormat(PixelFormat.TRANSLUCENT);

            mediaController = new MediaController(ShowPost.this);

            Uri video = Uri.parse(videopath);
            v.setMediaController(mediaController);
            v.setVideoURI(video);

            v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mp) {
                    progressDialog.dismiss();
                    v.start();
                }
            });

        } catch (Exception e) {
            progressDialog.dismiss();
            System.out.println("خطا در نمایش :" + e.getMessage());
        }

    }

    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            if (mp != null) {
                timeElapsed = mp.getCurrentPosition();
                seekbar.setProgress((int) timeElapsed);
                double timeRemaining = finalTime - timeElapsed;
                int h, h1, m, m1, s, s1;
                h = (int) ((int) (timeElapsed / 1000) / 60) / 60;
                h1 = (int) ((int) (timeRemaining / 1000) / 60) / 60;
                m = (int) (timeElapsed / 1000) / 60;
                m1 = (int) (timeRemaining / 1000) / 60;
                s = (int) (timeElapsed / 1000) % 60;
                s1 = (int) (timeRemaining / 1000) % 60;

                duration.setText(String.valueOf(h) + ":" + String.valueOf(m) + ":" + String.valueOf(s));
                duration1.setText(String.valueOf(h1) + ":" + String.valueOf(m1) + ":" + String.valueOf(s1));
                durationHandler.postDelayed(this, 100);
            }
        }
    };

    public void play(String pos) {
        try {
            //if(mp==null || !mp.isPlaying()) {
            if (mp != null)
                mp.release();
            mp = new MediaPlayer();

            Uri myUri = null;

            myUri = Uri.parse("http://virtual.amoozeshbeseh.ir/Files/" + pos);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);


            mp.setDataSource(getApplicationContext(), myUri);

            mp.prepare();

            mp.start();

            finalTime = mp.getDuration();

            seekbar.setMax((int) finalTime);
            seekbar.setClickable(false);

            timeElapsed = mp.getCurrentPosition();
            seekbar.setProgress((int) timeElapsed);
            durationHandler.postDelayed(updateSeekBarTime, 100);

            btnPlay.setImageResource(R.drawable.pause);
            //}

        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mp != null) {
            mp.stop();
            //mp.release();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
