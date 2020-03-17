package ir.hajj.virtualhajj_app.Utility;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by m-h-adib on 2018/06/30.
 */
public class ApiProvider {
    private ApiServices mApiServices;

    public ApiProvider() {
        OkHttpClient okHttpClient=new OkHttpClient();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://virtual.amoozeshbeseh.ir/api/services/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mApiServices=retrofit.create(ApiServices.class);
    }

    public ApiServices GetApiServices(){
        return mApiServices;
    }
}
