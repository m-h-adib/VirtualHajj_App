package ir.hajj.virtualhajj_app.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pixplicity.easyprefs.library.Prefs;

import ir.hajj.virtualhajj_app.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by m-h-adib on 6/22/2018.
 */

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        @SuppressLint("WrongConstant") SharedPreferences shared = getSharedPreferences("MyShared", MODE_APPEND);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("sans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        initImageLoader(getApplicationContext());

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

    }

    public static void initImageLoader(Context context) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.ic_launcher_background)
                .showImageOnFail(R.drawable.ic_launcher_background).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).defaultDisplayImageOptions(options)
                .discCacheSize(100 * 1024 * 1024)
                .memoryCacheSize(100 * 1024 * 1024).build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }


    public static void initShareIntent(String _url, String _text, Activity activity) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, _text);
        share.putExtra(Intent.EXTRA_TEXT, _url);

        activity.startActivity(Intent.createChooser(share, "اشتراک گذاری مطلب"));
    }

    public static void font(TextView tt, Context context) {
        SharedPreferences shared = context.getSharedPreferences("MyShared", MODE_APPEND);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), shared.getString("font_name", "sans") + ".ttf");
        tt.setTypeface(typeface);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            tt.setLineSpacing((float) 1 + (float) shared.getInt("font_space", 0) / (float) 10, (float) 1 + (float) shared.getInt("font_space", 0) / (float) 10);
        }
        float sp = tt.getTextSize() / context.getResources().getDisplayMetrics().scaledDensity;
        tt.setTextSize(TypedValue.COMPLEX_UNIT_SP, shared.getInt("font_size", 0) + sp);
    }

    public static boolean IsOnline(Activity activity) {
        ConnectivityManager manager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }

    public static String GetEnglishNumber(String persianNumber) {
        String[][] mChars = new String[][]{
                {"۰", "0"},
                {"۱", "1"},
                {"۲", "2"},
                {"۳", "3"},
                {"۴", "4"},
                {"۵", "5"},
                {"۶", "6"},
                {"۷", "7"},
                {"۸", "8"},
                {"۹", "9"}
        };

        for (String[] num : mChars) {

            persianNumber = persianNumber.replace(num[0], num[1]);

        }

        return persianNumber;
    }
}
