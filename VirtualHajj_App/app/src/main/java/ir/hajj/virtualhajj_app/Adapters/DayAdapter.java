package ir.hajj.virtualhajj_app.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import ir.hajj.virtualhajj_app.Models.Day;
import ir.hajj.virtualhajj_app.R;
import ir.hajj.virtualhajj_app.ShowPost;
import ir.hajj.virtualhajj_app.ShowVideo;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.Holder> {
    Activity activity;
    List<Day> items;

    ImageButton btnPlay,btnForward,btnBackward;
    private double timeElapsed = 0, finalTime = 0;
    private int forwardTime = 10000, backwardTime = 10000;
    private Handler durationHandler = new Handler();
    private SeekBar seekbar;
    MediaPlayer mp;
    TextView duration,duration1;



    public DayAdapter(Activity activity, List<Day> items) {
        this.activity = activity;
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=null;
        if(i == 0)
            view= LayoutInflater.from(activity).inflate(R.layout.timeline_item_odd,viewGroup,false);
        else
            view= LayoutInflater.from(activity).inflate(R.layout.timeline_item_even,viewGroup,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        holder.title.setText(items.get(i).getTitle());
        holder.time.setText("ساعت " + items.get(i).getTime());
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
        final String currentDate = formatter1.format(calendar1.getTime());

        if(i==(items.size()-1))
            holder.line.setVisibility(View.GONE);


        GradientDrawable g = null;
        switch (i%6){
            case 0:
                g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { activity.getResources().getColor(R.color.c1_1), activity.getResources().getColor(R.color.c1_2), activity.getResources().getColor(R.color.c1_2) });
                break;
            case 1:
                g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { activity.getResources().getColor(R.color.c2_1), activity.getResources().getColor(R.color.c2_2), activity.getResources().getColor(R.color.c2_2) });
                break;
            case 2:
                g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { activity.getResources().getColor(R.color.c3_1), activity.getResources().getColor(R.color.c3_2), activity.getResources().getColor(R.color.c3_2) });
                break;
            case 3:
                g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { activity.getResources().getColor(R.color.c4_1), activity.getResources().getColor(R.color.c4_2), activity.getResources().getColor(R.color.c4_2) });
                break;
            case 4:
                g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { activity.getResources().getColor(R.color.c5_1), activity.getResources().getColor(R.color.c5_2), activity.getResources().getColor(R.color.c5_2) });
                break;
            case 5:
                g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { activity.getResources().getColor(R.color.c6_1), activity.getResources().getColor(R.color.c6_2), activity.getResources().getColor(R.color.c6_2) });
                break;
        }
        g.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        g.setGradientRadius(45.0f);
        g.setGradientCenter(0.0f, 0.45f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.box.setBackground(g);
        }

        if(currentDate.compareTo(items.get(i).getTime())>=0 || i<=2) {
            holder.b.setImageResource(R.drawable.ic_radio_button_checked_black_24dp);
        }else {
            holder.b.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
            holder.box.setBackgroundColor(Color.parseColor("#cccccc"));
            holder.time.setTextColor(Color.parseColor("#888888"));
            holder.title.setTextColor(Color.parseColor("#888888"));
            holder.line.setBackgroundColor(Color.parseColor("#cccccc"));
            holder.line2.setBackgroundColor(Color.parseColor("#cccccc"));
            holder.b.setColorFilter(Color.parseColor("#cccccc"), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        holder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentDate.compareTo(items.get(i).getTime())>=0 || i<=2) {
                    if (items.get(i).getType().equals("text")) {
                        // Create custom dialog object
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_dialog_text);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.MATCH_PARENT);

                        dialog.setCancelable(true);

                        // set values for custom dialog components - text, image and button
                        TextView title = dialog.findViewById(R.id.title);
                        title.setText(items.get(i).getTitle());
                        TextView text = dialog.findViewById(R.id.text);
                        text.setText(Html.fromHtml(items.get(i).getContent()));
                        Button close = dialog.findViewById(R.id.close);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }else if (items.get(i).getType().equals("audio")) {
                        // Create custom dialog object
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_dialog_audio);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.WRAP_CONTENT);

                        dialog.setCancelable(true);

                        timeElapsed = 0;
                        finalTime = 0;
                        forwardTime = 10000;
                        backwardTime = 10000;

                        btnPlay=dialog.findViewById(R.id.btnPlay);
                        btnForward=dialog.findViewById(R.id.btnForward);
                        btnBackward=dialog.findViewById(R.id.btnBackward);
                        seekbar=dialog.findViewById(R.id.songProgressBar);
                        duration=dialog.findViewById(R.id.songCurrentDurationLabel);
                        duration1=dialog.findViewById(R.id.songTotalDurationLabel);
                        TextView title=dialog.findViewById(R.id.title);
                        title.setText(items.get(i).getTitle());

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
                                    play(items.get(i).getContent());
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
                                if(progress==finalTime)
                                    btnPlay.setImageResource(R.drawable.play);
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {

                            }
                        });
                        Button close = dialog.findViewById(R.id.close);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(mp!=null) {
                                    durationHandler.removeCallbacks(updateSeekBarTime);
                                    mp.stop();
                                    mp.release();
                                    mp=null;
                                }
                                dialog.dismiss();
                            }
                        });


                        dialog.show();
                    }else if (items.get(i).getType().equals("video")) {
                        // Create custom dialog object
                        Intent intent=new Intent(activity, ShowVideo.class);
                        intent.putExtra("video",items.get(i).getContent());
                        activity.startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2==0) {
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView time,title;
        ImageView b;
        LinearLayout line,box,line2;
        public Holder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.time);
            title=itemView.findViewById(R.id.title);
            b=itemView.findViewById(R.id.b);
            line=itemView.findViewById(R.id.line);
            line2=itemView.findViewById(R.id.line2);
            box=itemView.findViewById(R.id.box);
        }
    }



    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            if(mp!=null) {
                timeElapsed = mp.getCurrentPosition();
                seekbar.setProgress((int) timeElapsed);
                double timeRemaining = finalTime - timeElapsed;
                int h,h1,m,m1,s,s1;
                h=(int)((int)(timeElapsed/1000)/60)/60;
                h1=(int)((int)(timeRemaining/1000)/60)/60;
                m=(int)(timeElapsed/1000)/60;
                m1=(int)(timeRemaining/1000)/60;
                s=(int)(timeElapsed/1000)%60;
                s1=(int)(timeRemaining/1000)%60;

                duration.setText(String.valueOf(h)+":"+String.valueOf(m)+":"+String.valueOf(s));
                duration1.setText(String.valueOf(h1)+":"+String.valueOf(m1)+":"+String.valueOf(s1));
                durationHandler.postDelayed(this, 100);
            }
        }
    };
    public  void play(String pos){
        try {
            //if(mp==null || !mp.isPlaying()) {
            if(mp!=null)
                mp.release();
            mp = new MediaPlayer();

            Uri myUri=null;

            myUri = Uri.parse("http://virtual.amoozeshbeseh.ir/Daily/"+pos);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);


            mp.setDataSource(activity, myUri);

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

        }catch (Exception e){

        }
    }

}
