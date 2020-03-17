package ir.hajj.virtualhajj_app.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ir.hajj.virtualhajj_app.Models.Question;
import ir.hajj.virtualhajj_app.R;
import ir.hajj.virtualhajj_app.Utility.JalaliCalendar;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.Holder> {
    List<Question> items;
    Activity activity;

    public QuestionAdapter(List<Question> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_item,viewGroup,false);
        return new Holder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        holder.code.setText("کد: #"+String.valueOf(items.get(i).getQuestionID()+1000));
        GregorianCalendar calendar = new GregorianCalendar(Integer.valueOf(items.get(i).getDateD().substring(0,4)),Integer.valueOf(items.get(i).getDateD().substring(5,7))-1,Integer.valueOf(items.get(i).getDateD().substring(8,10)),00,00,00);
        JalaliCalendar jj=new JalaliCalendar(calendar);
        holder.date.setText(jj.getDayOfWeekDayMonthString()+" "+jj.getYear());
        if(items.get(i).getReply().equals("")){
            holder.reply.setBackgroundResource(R.color.red);
            holder.reply.setText("بدون پاسخ");
        }
        else {
            holder.reply.setBackgroundResource(R.color.colorPrimary);
            holder.reply.setText("پاسخ داده شده");
        }
        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(items.get(i).getReply().equals("")){
                    Toast.makeText(activity, "هنوز پاسخی درج نشده است", Toast.LENGTH_SHORT).show();
                }
                else {
                    final Dialog dialog=new Dialog(activity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custom_dialog_text);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    lp.gravity = Gravity.CENTER;
                    dialog.getWindow().setAttributes(lp);
                    dialog.setCancelable(true);

                    TextView title=dialog.findViewById(R.id.title);
                    TextView text=dialog.findViewById(R.id.text);
                    Button close=dialog.findViewById(R.id.close);

                    title.setText(items.get(i).getQues());
                    text.setText(items.get(i).getReply());
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
            }
        });
        //Toast.makeText(activity, ""+items.get(i).getDateD(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        TextView code,date;
        Button reply;

        public Holder(@NonNull View itemView) {
            super(itemView);
            code=itemView.findViewById(R.id.code);
            reply=itemView.findViewById(R.id.reply);
            date=itemView.findViewById(R.id.date);
        }
    }
}
