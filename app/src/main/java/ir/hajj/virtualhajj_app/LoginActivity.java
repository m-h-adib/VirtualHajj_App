package ir.hajj.virtualhajj_app;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ir.hajj.virtualhajj_app.Utility.ApiProvider;
import ir.hajj.virtualhajj_app.Utility.ApiServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;

    public void init(){
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_login);

        init();
    }

    public void login(View v){
        if(!username.getText().toString().equals("") && !password.getText().toString().equals("")){
            if(username.getText().toString().length()==11){
                if(password.getText().toString().length()>=6) {
                    ApiProvider apiProvider = new ApiProvider();
                    final ApiServices apiServices = apiProvider.GetApiServices();

                    Call<ResponseBody> call = apiServices.Login("http://virtual.amoozeshbeseh.ir/token", "password", username.getText().toString(), password.getText().toString());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(final Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if (response.isSuccess()) {
                                    JSONObject jsonObject = new JSONObject(response.body().string());

                                    Prefs.putString("Token", jsonObject.getString("access_token"));
                                    finish();
                                    //Toast.makeText(LoginActivity.this, "" + jsonObject.getString("access_token"), Toast.LENGTH_SHORT).show();

                                } else {
                                    //Toast.makeText(LoginActivity.this, "ورود نا موفق" , Toast.LENGTH_SHORT).show();
                                    final Dialog dialog = new Dialog(LoginActivity.this);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setContentView(R.layout.confirm_pass);
                                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                    lp.copyFrom(dialog.getWindow().getAttributes());
                                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                    lp.gravity = Gravity.CENTER;
                                    dialog.getWindow().setAttributes(lp);
                                    dialog.setCancelable(true);
                                    dialog.show();

                                    final EditText re_pass = dialog.findViewById(R.id.re_password);
                                    final EditText firstname = dialog.findViewById(R.id.firstname);
                                    final EditText lastname = dialog.findViewById(R.id.lastname);
                                    Button register_btn = dialog.findViewById(R.id.register_btn);
                                    register_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(final View v) {
                                            if (password.getText().toString().equals(re_pass.getText().toString())) {
                                                Call<ResponseBody> call1 = apiServices.Register(username.getText().toString(), password.getText().toString(), re_pass.getText().toString(), username.getText().toString(), firstname.getText().toString(), lastname.getText().toString());
                                                call1.enqueue(new Callback<ResponseBody>() {
                                                    @Override
                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                        if (response.isSuccess()) {
                                                            login(v);
                                                            dialog.dismiss();
                                                        } else {
                                                            Toast.makeText(LoginActivity.this, "با این شماره تماس قبلا ثبت نامی صورت گرفته است", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                                    }
                                                });
                                            } else {
                                                Toast.makeText(LoginActivity.this, "رمز عبور با تکرار آن مطابقط ندارد", Toast.LENGTH_SHORT).show();
                                                re_pass.setText("");
                                                password.setText("");
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
                else {
                    Toast.makeText(this, "رمز عبور باید حداقل 6 نویسه باشد", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "لطفا شماره همراه 11 رقمی خود را به درستی وارد نمایید", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(this, "لطفا شماره همراه و رمز عبور را وارد نمایید", Toast.LENGTH_SHORT).show();
        }
       
    }
}
