package ir.hajj.virtualhajj_app.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ir.hajj.virtualhajj_app.Models.Match;
import ir.hajj.virtualhajj_app.R;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.Holder> {
    List<Match> items;
    Activity activity;

    public MatchAdapter(List<Match> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.match_item,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int i) {
        holder.question.setText(items.get(i).getQuestion());
        holder.ch1.setText(items.get(i).getCh1());
        holder.ch2.setText(items.get(i).getCh2());
        holder.ch3.setText(items.get(i).getCh3());
        holder.ch4.setText(items.get(i).getCh4());
        holder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                items.get(i).setSelect(group.indexOfChild(holder.itemView.findViewById(checkedId))+1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView question;
        RadioGroup rg;
        RadioButton ch1,ch2,ch3,ch4;

        public Holder(@NonNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.question);
            ch1=itemView.findViewById(R.id.ch1);
            ch2=itemView.findViewById(R.id.ch2);
            ch3=itemView.findViewById(R.id.ch3);
            ch4=itemView.findViewById(R.id.ch4);
            rg=itemView.findViewById(R.id.rg);
        }
    }
}
