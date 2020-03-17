package ir.hajj.virtualhajj_app.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.List;

import ir.hajj.virtualhajj_app.Models.Post;
import ir.hajj.virtualhajj_app.R;
import ir.hajj.virtualhajj_app.ShowPost;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.Holder> {

    Activity activity;
    List<Post> items;
    String type;

    public PostAdapter(Activity activity, List<Post> items,String type) {
        this.activity = activity;
        this.items = items;
        this.type=type;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(activity).inflate(R.layout.news_item,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        holder.Title.setText(items.get(i).getTitle());
        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.displayImage("http://virtual.amoozeshbeseh.ir/Files/"+items.get(i).getPic(), holder.Pic);
        holder.Group.setText(items.get(i).getGroupName());

        ////colors
        GradientDrawable g = null;
        switch (i%6){
            case 0:
                g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { activity.getResources().getColor(R.color.c1_1), activity.getResources().getColor(R.color.c1_2), activity.getResources().getColor(R.color.c1_2) });
                holder.d3.setBackgroundColor(activity.getResources().getColor(R.color.c1_1));
                holder.d4.setColorFilter(ContextCompat.getColor(activity, R.color.c1_1), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case 1:
                g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { activity.getResources().getColor(R.color.c2_1), activity.getResources().getColor(R.color.c2_2), activity.getResources().getColor(R.color.c2_2) });
                holder.d3.setBackgroundColor(activity.getResources().getColor(R.color.c2_1));
                holder.d4.setColorFilter(ContextCompat.getColor(activity, R.color.c2_1), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case 2:
                g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { activity.getResources().getColor(R.color.c3_1), activity.getResources().getColor(R.color.c3_2), activity.getResources().getColor(R.color.c3_2) });
                holder.d3.setBackgroundColor(activity.getResources().getColor(R.color.c3_1));
                holder.d4.setColorFilter(ContextCompat.getColor(activity, R.color.c3_1), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case 3:
                g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { activity.getResources().getColor(R.color.c4_1), activity.getResources().getColor(R.color.c4_2), activity.getResources().getColor(R.color.c4_2) });
                holder.d3.setBackgroundColor(activity.getResources().getColor(R.color.c4_1));
                holder.d4.setColorFilter(ContextCompat.getColor(activity, R.color.c4_1), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case 4:
                g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { activity.getResources().getColor(R.color.c5_1), activity.getResources().getColor(R.color.c5_2), activity.getResources().getColor(R.color.c5_2) });
                holder.d3.setBackgroundColor(activity.getResources().getColor(R.color.c5_1));
                holder.d4.setColorFilter(ContextCompat.getColor(activity, R.color.c5_1), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case 5:
                g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] { activity.getResources().getColor(R.color.c6_1), activity.getResources().getColor(R.color.c6_2), activity.getResources().getColor(R.color.c6_2) });
                holder.d3.setBackgroundColor(activity.getResources().getColor(R.color.c6_1));
                holder.d4.setColorFilter(ContextCompat.getColor(activity, R.color.c6_1), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
        }
        g.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        g.setGradientRadius(45.0f);
        g.setGradientCenter(0.0f, 0.45f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.d1.setBackground(g);
            holder.d2.setBackground(g);
        }
        //end colors

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, ShowPost.class);
                intent.putExtra("id", items.get(i).getItemID());
                intent.putExtra("type",type);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        TextView Title,Group;
        ImageView Pic,d4;
        LinearLayout d1,d2,d3;

        public Holder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.Title);
            Pic=itemView.findViewById(R.id.Pic);
            Group=itemView.findViewById(R.id.Group);
            d1=itemView.findViewById(R.id.d1);
            d2=itemView.findViewById(R.id.d2);
            d3=itemView.findViewById(R.id.d3);
            d4=itemView.findViewById(R.id.d4);
        }
    }
}
