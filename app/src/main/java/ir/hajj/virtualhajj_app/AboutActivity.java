package ir.hajj.virtualhajj_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("درباره ما");

        TextView txt1=findViewById(R.id.txt1);
        txt1.setText(Html.fromHtml("<p dir=\"RTL\" style=\"text-align: center;\"><strong>به نام خدا</strong></p>\n" +
                "\n" +
                "<hr />\n" +
                "<p dir=\"RTL\">معرفی نرم افزار کاروان مجازی</p>\n" +
                "\n" +
                "<p dir=\"RTL\">اپلیکیشن کاروان مجازی در قالب موبایل منتشر شده که دارای 5 بخش اصلی آرشیو مطالب، برنامه روزانه، اخبار حج، پرسش و پاسخ پیرانون مسایل حج و مسابقات می باشد</p>\n" +
                "\n" +
                "<p dir=\"RTL\">در بخش آرشیو، مطالب با قالب های مختلف از جمله: متن، تصاویر، صوت و ویدئو در 10 دسته اصلی ارائه شده است.</p>\n" +
                "\n" +
                "<p dir=\"RTL\">تمامی مطالب موجود در این بخش قالبیت آفلاین شدن را نیز دارا می باشند به این صورت که مثلا کاربر ویدئویی را در برنامه برای بار اول دانلود نموده و در دفعات بعدی آن را از حافظه دستگاه خود می خواند و احتیاجی به اتصال مجدد به اینترنت ندارد.</p>\n" +
                "\n" +
                "<p dir=\"RTL\">در بخش برنامه روزانه، برنامه های متنوعی برای هر روز قرار داده شده که 24 ساعته فعال بوده و در سه نوبت 8 ساعته در روز فعالیت دارد و کاربر می تواند مطالب روز را در ساعت مقرر شده در نرم افزار به صورت آنلاین دریافت نماید.</p>\n" +
                "\n" +
                "<p dir=\"RTL\">اخبار مرتبط با حج نیز در بخش اخبار قرار خواهد گرفت تا کاربران عزیز بتوانند جدید ترین اخبار را دریافت نمایند</p>\n" +
                "\n" +
                "<p dir=\"RTL\">دربخش پرسش و پاسخ کاربران سوالاتشان را در مورد موضوع حج ثبت نموده و نرم افزار یک کد رهگیری به آنها می دهد. سپس کارشناسان برنامه سوالات را مشاهده کرده و به ترتیب به آنها پاسخ خواهند داد و کاربران از همین بخش برنامه پاسخ خود را دریافت خواهند کرد. در واقع این بخش از برنامه به صورت تعاملی بوده و کاربر با برنامه در ارتباط است</p>\n" +
                "\n" +
                "<p dir=\"RTL\">همچنین مسابقات مختلفی هنگام موسم، قبل از موسم و بعد از آن در بخش مسابقات برنامه فعال خواهد شد و جوایزی نیز برای ان در نظر گرفته خواهد شد و کاربران از همین صفحه می توانند در مسابقات شرکت کرده و نتایج را دریافت نمایند.</p>\n"+
                "<p>منابع: http://zaer.hajj.ir و http://amoozesh.hajj.ir</p>"));
    }
}
