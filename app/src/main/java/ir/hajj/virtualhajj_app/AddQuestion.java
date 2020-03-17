package ir.hajj.virtualhajj_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import ir.hajj.virtualhajj_app.Utility.ApiProvider;
import ir.hajj.virtualhajj_app.Utility.ApiServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddQuestion extends AppCompatActivity {

    String[] grousps = { "اسرار و معارف", "اخلاق و آداب", "احکام و مناسک", "تاریخ و اماکن", "سلامت و بهداشت","سیاسی و اجتماعی","کلام و عقائد","مهارت ها و سبک زندگی","تفسیر آیات و روایات","امور اجرایی"};
    int[] ids={15,16,17,18,19,20,21,22,24,25};
    ApiProvider apiProvider=new ApiProvider();
    ApiServices apiServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        apiServices=apiProvider.GetApiServices();
        setTitle("افزودن سوال");
        final AppCompatSpinner spinner = findViewById(R.id.groups);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,grousps);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        final EditText ques=findViewById(R.id.ques);
        Button ok=findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ques.getText().toString().equals("")){
                    ir.hajj.virtualhajj_app.Models.AddQuestion question=new ir.hajj.virtualhajj_app.Models.AddQuestion();
                    question.setGroupID(ids[spinner.getSelectedItemPosition()]);
                    question.setQues(ques.getText().toString());

                    Call<ResponseBody> call=apiServices.AddQuestion("Bearer "+Prefs.getString("Token",""),question);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccess())
                                finish();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(AddQuestion.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }else {
                    Toast.makeText(AddQuestion.this, "لطفا متن سوال را بنویسید", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
