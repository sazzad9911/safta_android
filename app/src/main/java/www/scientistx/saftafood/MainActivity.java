package www.scientistx.saftafood;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static www.scientistx.saftafood.App.CHANNEL_1_ID;
import static www.scientistx.saftafood.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    ConstraintLayout Dashboard,Profile,Event,EditLine,Messanger;
    ProgressBar prg;
    TextView Name,Position,Date,Degree,Phone,HeadLine,Content,MealDue,MealRate,Messagess;
    TextView HomeDue,HomeCredit,MealCredit,TotalMeal,Home,Wifi,Gass,Electricity,Cooking,Previous,MealPrevious,Fine,Extra,Month;
    ImageView Image;
    WebView WebEvent;
    EditText txt1,txt2,txt3;
    Button BTN,Btn,Agree,Buy;
    ImageButton btr;
    public double EventCost,homedue,expenses,countmeal,mealrate,due;
    public static double mealdue;
    public String months;
    NotificationManagerCompat managerCompat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        if(fAuth.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),login.class));
            finish();
        }else if(fAuth.getCurrentUser()!=null){
            String Email=fAuth.getInstance().getCurrentUser().getEmail();
            if(Email.equals("mksa.sazzad@gmail.com")||Email.equals("jayanta15-2641@diu.edu.bd")) {
                startActivity(new Intent(getApplicationContext(), admin.class));
                finish();
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                notification_bar notificationBar=new notification_bar();
                notificationBar.show(getSupportFragmentManager(),"notification_bar");
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //Dashboare view.........................................
        Dashboard=findViewById(R.id.dashboard);
        HomeDue=findViewById(R.id.homedue);
        HomeCredit=findViewById(R.id.homecredit);
        MealCredit=findViewById(R.id.mealcredit);
        TotalMeal=findViewById(R.id.totalmeal);
        Home=findViewById(R.id.home);
        Wifi=findViewById(R.id.wifi);
        Gass=findViewById(R.id.gass);
        Electricity=findViewById(R.id.electricity);
        Cooking=findViewById(R.id.cooking);
        Previous=findViewById(R.id.previous);
        MealPrevious=findViewById(R.id.pmealdue);
        Fine=findViewById(R.id.fine);
        Extra=findViewById(R.id.extra);
        Month=findViewById(R.id.month);
        MealDue=findViewById(R.id.mealdue);
        MealRate=findViewById(R.id.mealrate111);
        //Profile view..........................................
        Profile=findViewById(R.id.profilee);
        Name=findViewById(R.id.name);
        Position=findViewById(R.id.position);
        Date=findViewById(R.id.datee);
        Degree=findViewById(R.id.degree);
        Phone=findViewById(R.id.phonee);
        Image=findViewById(R.id.image);
        EditLine=findViewById(R.id.table);
        txt1=findViewById(R.id.editfield1);
        txt2=findViewById(R.id.editfield2);
        txt3=findViewById(R.id.editfield3);
        Btn=findViewById(R.id.save);
        BTN=findViewById(R.id.SaveInfo);
        btr=findViewById(R.id.closerrrr);
        WebEvent=findViewById(R.id.webevent);
        //Event view................................................
        Event=findViewById(R.id.eventt);
        HeadLine=findViewById(R.id.headline);
        Content=findViewById(R.id.content);
        prg=findViewById(R.id.progressBar_main);
        Messanger=findViewById(R.id.messanger);
        Agree=findViewById(R.id.button3);
        Messagess=findViewById(R.id.messagesss);
        Buy=findViewById(R.id.buy);

        managerCompat= NotificationManagerCompat.from(this);

        if(fAuth.getCurrentUser()!=null){
            LoadData();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this,"Download SAFTA Food v-2.1.0",Toast.LENGTH_LONG).show();
            return true;
        }if(id==R.id.off){
            Messanger.setVisibility(View.VISIBLE);
            FragmentManager fr= getSupportFragmentManager();
            off of=new off();
            fr.beginTransaction().replace(R.id.messanger,of).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dashboard) {
            // Handle the camera action
            Dashboard.setVisibility(View.VISIBLE);
            Profile.setVisibility(View.INVISIBLE);
            Event.setVisibility(View.INVISIBLE);
        } else if (id == R.id.profile) {
            Dashboard.setVisibility(View.INVISIBLE);
            Profile.setVisibility(View.VISIBLE);
            Event.setVisibility(View.INVISIBLE);

        } else if (id == R.id.event) {
            Dashboard.setVisibility(View.INVISIBLE);
            Profile.setVisibility(View.INVISIBLE);
            Event.setVisibility(View.VISIBLE);
            WebView();
            Buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BuyTicket();
                }
            });

        } else if (id == R.id.about) {
            startActivity(new Intent(getApplicationContext(),AboutUs.class));
        } else if (id == R.id.logout) {
            fAuth.signOut();
            startActivity(new Intent(getApplicationContext(), login.class));
            finish();

        } else if (id == R.id.recharge) {
            Messanger.setVisibility(View.VISIBLE);
           FragmentManager fr=getSupportFragmentManager();
            Recharge recharge=new Recharge();
           fr.beginTransaction().replace(R.id.messanger,recharge).commit();
            Bundle bundle=new Bundle();
            bundle.putDouble("homedue",homedue);
            recharge.setArguments(bundle);
        }else if(id==R.id.message){
            Toast.makeText(MainActivity.this,"Download SAFTA Food v-2.1.0",Toast.LENGTH_LONG).show();
            //startActivity(new Intent(getApplicationContext(),Messanger.class));
        }else if(id==R.id.payement){
            Messanger.setVisibility(View.VISIBLE);
            Agree.setVisibility(View.VISIBLE);
            Messagess.setVisibility(View.VISIBLE);
            Messagess.setText("* For Bkash send money to 01761143991 set reference \"Meal\" to add money in Meal account, \n"
                    +"set \"Home\" for add money in Home Account \n"+"\n * For DBBL sent money to 017502101190 set reference \"Meal\" to add money in Meal account \n"
                    +"set \"Home\" to set money to add money in home account");
            Agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Agree.setVisibility(View.GONE);
                    Messagess.setVisibility(View.GONE);
                    FragmentManager ft= getSupportFragmentManager();
                    Payement payement=new Payement();
                    ft.beginTransaction().replace(R.id.messanger,payement).commit();
                }
            });
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void EditName(View view){
        EditLine.setVisibility(View.VISIBLE);
        txt3.setVisibility(View.INVISIBLE);
        BTN.setVisibility(View.INVISIBLE);
        Btn.setVisibility(View.VISIBLE);
        txt1.setHint("Enter Name");
        txt2.setHint("Enter Position");

        btr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditLine.setVisibility(View.INVISIBLE);
            }
        });
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prg.setVisibility(View.VISIBLE);
                String name=txt1.getText().toString().trim();
                String position=txt2.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    txt1.setError("Fill the body");
                    prg.setVisibility(View.INVISIBLE);
                    return;
                }if(TextUtils.isEmpty(position)){
                    txt2.setError("Fill thr body");
                    prg.setVisibility(View.INVISIBLE);
                    return;
                }
                //Update information codding and messages
                String email= fAuth.getInstance().getCurrentUser().getEmail();
                DocumentReference washingtonRef = fstore.collection("users").document(email);
                washingtonRef
                        .update("Name",name,"Position",position)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
                                EditLine.setVisibility(View.INVISIBLE);
                                prg.setVisibility(View.INVISIBLE);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                prg.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this,"Connection Problem",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
    public void EditInfo(View view){
        EditLine.setVisibility(View.VISIBLE);
        txt3.setVisibility(View.VISIBLE);
        Btn.setVisibility(View.INVISIBLE);
        BTN.setVisibility(View.VISIBLE);
        txt1.setHint("Enter Degree");
        txt2.setHint("Enter Phone");
        txt3.setHint("Date of Birth(19 Dec 1997)");

        btr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditLine.setVisibility(View.INVISIBLE);
            }
        });
        BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updating information and codding, messages
                prg.setVisibility(View.VISIBLE);
                String degree=txt1.getText().toString().trim();
                String phone=txt2.getText().toString().trim();
                String date=txt3.getText().toString().trim();
                if(TextUtils.isEmpty(degree)){
                    txt1.setError("Fill the body");
                    prg.setVisibility(View.INVISIBLE);
                    return;
                }if(TextUtils.isEmpty(phone)){
                    txt2.setError("Fill the body");
                    prg.setVisibility(View.INVISIBLE);
                    return;
                }if(TextUtils.isEmpty(date)){
                    txt3.setError("Fill the body");
                    prg.setVisibility(View.INVISIBLE);
                    return;
                }
                String email= fAuth.getInstance().getCurrentUser().getEmail();
                DocumentReference washingtonRef = fstore.collection("users").document(email);
                washingtonRef
                        .update("Degree",degree,"Phone",phone,"Date",date)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
                                EditLine.setVisibility(View.INVISIBLE);
                                prg.setVisibility(View.INVISIBLE);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                prg.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this,"Connection Problem",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
    public void ImageClick(View view){
        Toast.makeText(MainActivity.this,"This option is not active",Toast.LENGTH_LONG).show();
    }
    public void LoadData(){
        prg.setVisibility(View.VISIBLE);
        String email= fAuth.getInstance().getCurrentUser().getEmail();
        DocumentReference docRef = fstore.collection("users").document(email);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(MainActivity.this,"Load Faild",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Name.setText(snapshot.getString("Name"));
                    Phone.setText(snapshot.getString("Phone"));
                    Position.setText(snapshot.getString("Position"));
                    Degree.setText(snapshot.getString("Degree"));
                    Date.setText(snapshot.getString("Date"));
                    String image=snapshot.getString("Image");
                    double mealcredit=snapshot.getDouble("mealcredit"),homecredit=snapshot.getDouble("homecredit"),
                            home=snapshot.getDouble("home"),wifi=snapshot.getDouble("wifi"),gass=snapshot.getDouble("gass"),
                            cooking=snapshot.getDouble("cooking"),elec=snapshot.getDouble("elec"),prev=snapshot.getDouble("prev"),
                            pmeal=snapshot.getDouble("pmeal"),fine=snapshot.getDouble("fine"),extra=snapshot.getDouble("extra"),totalmeal=snapshot.getDouble("totalmeal");

                    LoadImage(image);
                    LoadEvent();
                    Calculation(mealcredit,homecredit,home,wifi,gass,elec,cooking,prev,pmeal,fine,extra,totalmeal);
                } else {
                    Toast.makeText(MainActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void Calculation(final double mealcredit, double homecredit, double home, double wifi, double gass, double elec, double cooking,
                            double prev, double pmeal, double fine, final double extra, final double totalmeal){
        homedue=(home+wifi+gass+elec+prev+pmeal+fine+extra+cooking)-homecredit;
        HomeDue.setText(String.format("%.2f",homedue));
        HomeCredit.setText(String.format("%.0f",homecredit));
        MealCredit.setText(String.format("%.0f",mealcredit));
        TotalMeal.setText(String.format("%.0f",totalmeal));
        Home.setText("Home rent: "+String.format("%.2f",home)+" tk");
        Wifi.setText("Wifi Bill: "+String.format("%.2f",wifi)+" tk");
        Gass.setText("Gass Bill: "+String.format("%.2f",gass)+" tk");
        Cooking.setText("Cooking Bill: "+String.format("%.2f",cooking)+" tk");
        Electricity.setText("Electricity Bill: "+String.format("%.2f",elec)+" tk");
        Previous.setText("Previous Due: "+String.format("%.2f",prev)+" tk");
        MealPrevious.setText("Previous meal Due: "+String.format("%.2f",pmeal)+" tk");
        Fine.setText("Fine Fee: "+String.format("%.2f",fine)+" tk");
        Extra.setText("Extra Cost: "+String.format("%.2f",extra)+" tk");
        if(homedue>100){
            HomeNotification();
        }
        //get data from meal database................................................................
        java.util.Date d= new Date();
        months =String.format("%tb",d)+" "+String.format("%tY",d);
        Month.setText("For the month: "+months);
        //total expenses calculation................................................................
        String email= fAuth.getInstance().getCurrentUser().getEmail();
        DocumentReference docRef = fstore.collection("meal").document(months);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(MainActivity.this,"Load Faild",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    int i=1;
                    expenses=0 ;
                    while (i<=31){
                        if(snapshot.getDouble(String.valueOf(i))!=null){
                            expenses = expenses+snapshot.getDouble(String.valueOf(i));
                        }
                        i++;
                    }
                }

        //total meal calculation........................................................
        DocumentReference docReff = fstore.collection("meal").document(months).collection("totalmeal").document(months);
        docReff.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(MainActivity.this,"Load Faild",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    int i=1;
                    countmeal=0 ;
                    while (i<=31){
                        if(snapshot.getDouble(String.valueOf(i))!=null){
                            countmeal = countmeal+snapshot.getDouble(String.valueOf(i));
                        }
                        i++;
                    }
                }
                mealrate=expenses/countmeal;
                due=(mealrate*totalmeal)-mealcredit;
                mealdue=due;
                MealRate.setText(String.format("%.2f",mealrate));
                MealDue.setText(String.format("%.2f",due));
                if(due>100){
                    MealNotification();
                }
            }
        });

            }
        });

    }
    public void LoadImage(String name){
        StorageReference storageReference = storage.getReferenceFromUrl("gs://safta-food-6c65a.appspot.com/").child("images").child(name);
        try {
            final File file = File.createTempFile("image", "jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    Image.setImageBitmap(bitmap);
                    prg.setVisibility(View.INVISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Image load Faild", Toast.LENGTH_SHORT).show();
                    prg.setVisibility(View.INVISIBLE);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void LoadEvent(){
        DocumentReference docRef = fstore.collection("event").document("new");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(MainActivity.this,"Load Faild",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    HeadLine.setText(snapshot.getString("head"));
                    Content.setText(snapshot.getString("content"));
                    EventCost=snapshot.getDouble("cost");

                } else {
                    Toast.makeText(MainActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void WebView(){
        prg.setVisibility(View.VISIBLE);
        WebEvent.getSettings().setJavaScriptEnabled(true);
        WebEvent.getSettings().setAppCacheEnabled(true);
        WebEvent.getSettings().setLoadWithOverviewMode(true);
        WebEvent.getSettings().setUseWideViewPort(true);
        WebEvent.getSettings().setBuiltInZoomControls(true);
        WebEvent.loadUrl("https://safta-food-6c65a.web.app/event.html");
        WebEvent.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                prg.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void Closer(View view){
        Messanger.setVisibility(View.INVISIBLE);
        FragmentManager frg=getSupportFragmentManager();
        empty em=new empty();
        frg.beginTransaction().replace(R.id.messanger,em).commit();
        Messagess.setVisibility(View.GONE);
        Agree.setVisibility(View.GONE);

    }
    public void BuyTicket(){
        if((-homedue)>=EventCost){
            String email=fAuth.getInstance().getCurrentUser().getEmail();
            Toast.makeText(MainActivity.this,"Event purched successfull",Toast.LENGTH_LONG).show();
            DocumentReference df=fstore.collection("users").document(email);
            df.update("extra",FieldValue.increment(EventCost));
            //========================================================
            DocumentReference dc=fstore.collection("event").document("new")
                    .collection("subscribe").document(email);
            dc.update("Date",months,"Number",FieldValue.increment(1));
        }else {
            Toast.makeText(MainActivity.this,"You have not enough money in Home account",Toast.LENGTH_LONG).show();
        }
    }
    public void MealNotification(){
        Notification notification= new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_stat_1)
                .setContentTitle("Meal Dues")
                .setContentText("Your reminding due"+String.format("%.2f",mealdue)+". "+"Please pay the money otherwise meal will be stop")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        managerCompat.notify(1,notification);
    }
    public void HomeNotification(){
        Notification notification= new NotificationCompat.Builder(this,CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_stat_1)
                .setContentTitle("Home Dues")
                .setContentText("Due "+String.format("%.2f",homedue)+"."+"Pay the money otherwise charged 50tk")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        managerCompat.notify(1,notification);
    }

}
