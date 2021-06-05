package www.scientistx.saftafood;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_1_ID="meal";
    public static final String CHANNEL_2_ID="home";
    public static final String CHANNEL_3_ID="message";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotification();
    }
    public void createNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(
                    CHANNEL_1_ID,
                    "meal",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Meal Account");
            NotificationChannel channell=new NotificationChannel(
                    CHANNEL_2_ID,
                    "home",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Home Account");
            NotificationChannel channel2=new NotificationChannel(
                    CHANNEL_3_ID,
                    "Message",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel2.setDescription("User's Messages");
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            manager.createNotificationChannel(channell);
            manager.createNotificationChannel(channel2);
        }
    }
}
