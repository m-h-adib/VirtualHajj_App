package ir.hajj.virtualhajj_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.pixplicity.easyprefs.library.Prefs;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean doubleBackToExitPressedOnce = false;
    private FirebaseAnalytics mFirebaseAnalytics;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setTitle("کاروان مجازی حج");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                final TextView login=drawer.findViewById(R.id.login);
                if(!Prefs.getString("Token","").equals("")){
                    login.setText("خروج از سیستم");
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("خروج از سیستم")
                                    .setMessage("مطمئنید خارج می شوید؟")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton("بله", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            Prefs.putString("Token","");
                                            login.setText("ورود / عضویت");
                                            drawer.closeDrawers();
                                            openFragment(new ArchiveFragment());
                                        }})
                                    .setNegativeButton("خیر", null).show();
                            drawer.closeDrawers();
                        }
                    });
                }
                else {
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            drawer.closeDrawers();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    });
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        openFragment(new ArchiveFragment());

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_favorites:
                                openFragment(new ArchiveFragment());
                                break;
                            case R.id.action_schedules:
                                openFragment(new DailyFragment());
                                Bundle bundle = new Bundle();
                                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "123");
                                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                                break;
                            case R.id.action_music:
                                openFragment(new NewsFragment());
                                break;
                            case R.id.Questions:
                                openFragment(new QuestionFragment());
                                break;
                            case R.id.Competition:
                                openFragment(new CompetitionFragment());
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void openFragment(final Fragment fragment)   {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.your_placeholder, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openFragmentstack(final Fragment fragment)   {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.your_placeholder, fragment);
        transaction.addToBackStack("tag");
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(getSupportFragmentManager().findFragmentById(R.id.your_placeholder) instanceof PostFragment){
                openFragment(new ArchiveFragment());
            }
            else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "لطفا برای خروج کلید بازگشت را دوبار بزنید ", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 2000);
            }

        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),AboutActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }  else if (id == R.id.nav_share) {
            String shareBody = "نرم افزار کاروان مجازی حج را از لینک زیر دانلود نمایید" + "\n"+"http://virtual.amoozeshbeseh.ir/virtual.apk";
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "اشتراک لینک کاروان مجازی حج");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "اشتراک کتاب"));

        } else if (id == R.id.nav_contact) {
            startActivity(new Intent(getApplicationContext(),Contact.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
