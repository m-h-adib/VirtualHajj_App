package ir.hajj.virtualhajj_app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.text.TextUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;

import ir.hajj.virtualhajj_app.Utility.NotificationUtil;

import static android.support.constraint.Constraints.TAG;

public class NotifService extends FirebaseMessagingService {

    private static final String TAG=NotifService.class.getSimpleName();
    private final static String ACT = "act" ;
    private final static String ICON = "icon" ;
    private final static String LINK = "link" ;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Log.d(TAG, "Data Payload: " + remoteMessage.getData().size());
//        //Toast.makeText(this, ""+remoteMessage.getData().size(), Toast.LENGTH_SHORT).show();
//        if (remoteMessage.getNotification() != null) {
//            if (remoteMessage.getData().size() > 0) {
//                Log.i(TAG, "Data Payload: " + remoteMessage.getData());
//                showCustomNotification(remoteMessage);
//            }else{
//                showNotification(remoteMessage.getNotification().getBody());
//            }
//        }
    }



    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
       // sendRegistrationToServer(token);
    }
}
