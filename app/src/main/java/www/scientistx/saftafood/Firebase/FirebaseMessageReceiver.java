package www.scientistx.saftafood.Firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import www.scientistx.saftafood.MainActivity;
import www.scientistx.saftafood.R;

public class FirebaseMessageReceiver extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size()>0){
            ShowNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
        }
        if(remoteMessage.getNotification()!=null){
            ShowNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }
    }
    private RemoteViews Design(String title,String message){
        RemoteViews remoteViews= new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.id,"SAFTA Food");
        remoteViews.setTextViewText(R.id.title,title);
        remoteViews.setTextViewText(R.id.message,message);
        //remoteViews.setImageViewResource(R.id.image,R.drawable.ic_stat_3);
        return remoteViews;
    }
    public void ShowNotification(String title,String message){
        Intent intent= new Intent(this, MainActivity.class);
        String channel_id="Events & Management";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(getApplicationContext(),channel_id)
                .setSmallIcon(R.drawable.ic_stat_2)
                .setSound(uri)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000,1000,1000,1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
            builder=builder.setContent(Design(title,message));
        }else{
            builder=builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_stat_2);
        }
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(channel_id,"Event & Management",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(uri,null);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0,builder.build());
    }
}
