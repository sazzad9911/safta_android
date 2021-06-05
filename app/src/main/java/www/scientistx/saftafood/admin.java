package www.scientistx.saftafood;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
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
import static www.scientistx.saftafood.App.CHANNEL_3_ID;

public class admin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth fAuth1;
    FirebaseFirestore fstore1;
    FirebaseDatabase database;
    FirebaseStorage storage1 = FirebaseStorage.getInstance();
    ConstraintLayout Dashboard1,Profile1,Event1,EditLine1,Fragment,Fragment1;
    ProgressBar prg1;
    TextView Name1,Position1,Date1,Degree1,Phone1,HeadLine1,Content1,MealDue1,MealRate1,Txt;
    TextView HomeDue1,HomeCredit1,MealCredit1,TotalMeal1,Home1,Wifi1,Gass1,Electricity1,Cooking1,Previous1,MealPrevious1,Fine1,Extra1,Month1;
    ImageView Image1;
    WebView WebEvent1;
    EditText txt11,txt21,txt31;
    Button BTN1,Btn1,Buy1;
    ImageButton btr1;
    public double EventCost1,homedue1,expenses1,countmeal1,mealrate1,due1,homehandcash,mealhandcash;
    public static double mealdue1,expens;
    public double totalMealCredit,totalHomeCredit;
    public String months1;
    public int i;
    public double hand;
    public boolean a=false,b=false;
    private FirebaseListAdapter<vp_message> adapter;
    NotificationManagerCompat managerCompat1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fAuth1=FirebaseAuth.getInstance();
        fstore1=FirebaseFirestore.getInstance();
        String Email=fAuth1.getInstance().getCurrentUser().getEmail();
        if(fAuth1.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),login.class));
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab1);
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
        Dashboard1=findViewById(R.id.dashboard1);
        HomeDue1=findViewById(R.id.homedue1);
        HomeCredit1=findViewById(R.id.homecredit1);
        MealCredit1=findViewById(R.id.mealcredit1);
        TotalMeal1=findViewById(R.id.totalmeal1);
        Home1=findViewById(R.id.home1);
        Wifi1=findViewById(R.id.wifi1);
        Gass1=findViewById(R.id.gass1);
        Electricity1=findViewById(R.id.electricity1);
        Cooking1=findViewById(R.id.cooking1);
        Previous1=findViewById(R.id.previous1);
        MealPrevious1=findViewById(R.id.pmealdue1);
        Fine1=findViewById(R.id.fine1);
        Extra1=findViewById(R.id.extra1);
        Month1=findViewById(R.id.month1);
        MealDue1=findViewById(R.id.mealdue1);
        MealRate1=findViewById(R.id.mealrate1);
        //Profile view..........................................
        Profile1=findViewById(R.id.profilee1);
        Name1=findViewById(R.id.name1);
        Position1=findViewById(R.id.position1);
        Date1=findViewById(R.id.datee1);
        Degree1=findViewById(R.id.degree1);
        Phone1=findViewById(R.id.phonee1);
        Image1=findViewById(R.id.image1);
        EditLine1=findViewById(R.id.table1);
        txt11=findViewById(R.id.editfield11);
        txt21=findViewById(R.id.editfield21);
        txt31=findViewById(R.id.editfield31);
        Btn1=findViewById(R.id.save1);
        BTN1=findViewById(R.id.SaveInfo1);
        btr1=findViewById(R.id.closerrrr1);
        WebEvent1=findViewById(R.id.webevent1);
        //Event view................................................
        Event1=findViewById(R.id.eventt1);
        HeadLine1=findViewById(R.id.headline1);
        Content1=findViewById(R.id.content1);
        prg1=findViewById(R.id.progressBar_main1);
        //Messanger1=findViewById(R.id.messanger1);
       // Agree1=findViewById(R.id.button31);
        //Messagess1=findViewById(R.id.messagesss1);
        Buy1=findViewById(R.id.buy1);
        Fragment=findViewById(R.id.fragment);
        Fragment1=findViewById(R.id.fragment1);
        Txt=findViewById(R.id.Txt);
        managerCompat1= NotificationManagerCompat.from(this);

        if(fAuth1.getCurrentUser()!=null){
           if(Email.equals("mksa.sazzad@gmail.com")||Email.equals("jayanta15-2641@diu.edu.bd")){
               LoadData1();
               FirebaseNotify();
           }
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
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings1) {
            String Email=fAuth1.getInstance().getCurrentUser().getEmail();
            if(Email.equals("jayanta15-2641@diu.edu.bd")){
                //ResetMeal();
                confirmation cf=new confirmation();
                cf.show(getSupportFragmentManager(),"reset");
            }else{
                Toast.makeText(admin.this,"This option is not for you",Toast.LENGTH_LONG).show();
            }
            return true;
        }if(id==R.id.action_settings2){
            String Email=fAuth1.getInstance().getCurrentUser().getEmail();
            if(Email.equals("mksa.sazzad@gmail.com")){
                //ResetHome();
                confirmation df=new confirmation();
                df.show(getSupportFragmentManager(),"homereset");
            }else{
                Toast.makeText(admin.this,"This option is not for you",Toast.LENGTH_LONG).show();
            }
            return true;
        }if(id==R.id.action_settings3){
            String Email=fAuth1.getInstance().getCurrentUser().getEmail();
            if(Email.equals("jayanta15-2641@diu.edu.bd")){
                Fragment1.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(admin.this,"This option is not for you",Toast.LENGTH_LONG).show();
            }
            return true;
        }if(id==R.id.action_settings4){
            String Email=fAuth1.getInstance().getCurrentUser().getEmail();
            if(Email.equals("mksa.sazzad@gmail.com")){
                Fragment1.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(admin.this,"This option is not for you",Toast.LENGTH_LONG).show();
            }
            return true;
        }if(id==R.id.action_settings5){
            String Email=fAuth1.getInstance().getCurrentUser().getEmail();
            if(Email.equals("mksa.sazzad@gmail.com")){
               b=true;
               LoadData1();
            }else if(Email.equals("jayanta15-2641@diu.edu.bd")){
                a=true;
                LoadData1();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home1) {
            // Handle the camera action
            Dashboard1.setVisibility(View.VISIBLE);
            Profile1.setVisibility(View.INVISIBLE);
            Event1.setVisibility(View.INVISIBLE);
            Fragment.setVisibility(View.INVISIBLE);
        } else if (id == R.id.Profile11) {
            Dashboard1.setVisibility(View.INVISIBLE);
            Profile1.setVisibility(View.VISIBLE);
            Event1.setVisibility(View.INVISIBLE);
            Fragment.setVisibility(View.INVISIBLE);

        } else if (id == R.id.Event11) {
            Dashboard1.setVisibility(View.INVISIBLE);
            Profile1.setVisibility(View.INVISIBLE);
            Event1.setVisibility(View.VISIBLE);
            Fragment.setVisibility(View.INVISIBLE);
            WebView();
            Buy1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BuyTicket();
                }
            });

        } else if (id == R.id.nav_tools1) {
            startActivity(new Intent(getApplicationContext(),AboutUs.class));
        } else if (id == R.id.logoutt) {
            fAuth1.signOut();
            startActivity(new Intent(getApplicationContext(), login.class));
            finish();

        } else if (id == R.id.nav_share1) {
            String Email=fAuth1.getInstance().getCurrentUser().getEmail();
            if(Email.equals("jayanta15-2641@diu.edu.bd")){
                Fragment.setVisibility(View.VISIBLE);
                FragmentManager fragmentManager=getSupportFragmentManager();
                add_meal addMeal=new add_meal();
                fragmentManager.beginTransaction().replace(R.id.fragment,addMeal).commit();
                Bundle bundle=new Bundle();
                bundle.putDouble("mealrate",mealrate1);
                addMeal.setArguments(bundle);
            }else {
                Toast.makeText(admin.this,"This option is not for you",Toast.LENGTH_LONG).show();
            }
        }else if(id==R.id.nav_send1){
            String Email=fAuth1.getInstance().getCurrentUser().getEmail();
            if(Email.equals("mksa.sazzad@gmail.com")){
                Fragment.setVisibility(View.VISIBLE);
                FragmentManager fragmentManager=getSupportFragmentManager();
                add_bills add_bills=new add_bills();
                fragmentManager.beginTransaction().replace(R.id.fragment,add_bills).commit();
                Bundle bundle=new Bundle();
                bundle.putDouble("mealrate",mealrate1);
                bundle.putDouble("handcash",mealhandcash);
                add_bills.setArguments(bundle);
            }else {
                Toast.makeText(admin.this,"This option is not for you",Toast.LENGTH_LONG).show();
            }
        }else if(id==R.id.mess1){
            Toast.makeText(admin.this,"Download SAFTA Food v-2.1.0",Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void EditName(View view){
        EditLine1.setVisibility(View.VISIBLE);
        txt31.setVisibility(View.INVISIBLE);
        BTN1.setVisibility(View.INVISIBLE);
        Btn1.setVisibility(View.VISIBLE);
        txt11.setHint("Enter Name");
        txt21.setHint("Enter Position");

        btr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditLine1.setVisibility(View.INVISIBLE);
            }
        });
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prg1.setVisibility(View.VISIBLE);
                String name=txt11.getText().toString().trim();
                String position=txt21.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    txt11.setError("Fill the body");
                    prg1.setVisibility(View.INVISIBLE);
                    return;
                }if(TextUtils.isEmpty(position)){
                    txt21.setError("Fill thr body");
                    prg1.setVisibility(View.INVISIBLE);
                    return;
                }
                //Update information codding and messages
                String email= fAuth1.getInstance().getCurrentUser().getEmail();
                DocumentReference washingtonRef = fstore1.collection("users").document(email);
                washingtonRef
                        .update("Name",name,"Position",position)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(admin.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
                                EditLine1.setVisibility(View.INVISIBLE);
                                prg1.setVisibility(View.INVISIBLE);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                prg1.setVisibility(View.INVISIBLE);
                                Toast.makeText(admin.this,"Connection Problem",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
    public void EditInfo(View view){
        EditLine1.setVisibility(View.VISIBLE);
        txt31.setVisibility(View.VISIBLE);
        Btn1.setVisibility(View.INVISIBLE);
        BTN1.setVisibility(View.VISIBLE);
        txt11.setHint("Enter Degree");
        txt21.setHint("Enter Phone");
        txt31.setHint("Date of Birth(19 Dec 1997)");

        btr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditLine1.setVisibility(View.INVISIBLE);
            }
        });
        BTN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updating information and codding, messages
                prg1.setVisibility(View.VISIBLE);
                String degree=txt11.getText().toString().trim();
                String phone=txt21.getText().toString().trim();
                String date=txt31.getText().toString().trim();
                if(TextUtils.isEmpty(degree)){
                    txt11.setError("Fill the body");
                    prg1.setVisibility(View.INVISIBLE);
                    return;
                }if(TextUtils.isEmpty(phone)){
                    txt21.setError("Fill the body");
                    prg1.setVisibility(View.INVISIBLE);
                    return;
                }if(TextUtils.isEmpty(date)){
                    txt31.setError("Fill the body");
                    prg1.setVisibility(View.INVISIBLE);
                    return;
                }
                String email= fAuth1.getInstance().getCurrentUser().getEmail();
                DocumentReference washingtonRef = fstore1.collection("users").document(email);
                washingtonRef
                        .update("Degree",degree,"Phone",phone,"Date",date)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(admin.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
                                EditLine1.setVisibility(View.INVISIBLE);
                                prg1.setVisibility(View.INVISIBLE);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                prg1.setVisibility(View.INVISIBLE);
                                Toast.makeText(admin.this,"Connection Problem",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
    public void ImageClick(View view){
        Toast.makeText(admin.this,"This option is not active",Toast.LENGTH_LONG).show();
    }
    public void LoadData1(){
        prg1.setVisibility(View.VISIBLE);
        String email= fAuth1.getInstance().getCurrentUser().getEmail();
        DocumentReference docRef = fstore1.collection("users").document(email);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(admin.this,"Load Faild",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Name1.setText(snapshot.getString("Name"));
                    Phone1.setText(snapshot.getString("Phone"));
                    Position1.setText(snapshot.getString("Position"));
                    Degree1.setText(snapshot.getString("Degree"));
                    Date1.setText(snapshot.getString("Date"));
                    String image=snapshot.getString("Image");
                    double mealcredit=snapshot.getDouble("mealcredit"),homecredit=snapshot.getDouble("homecredit"),
                            home=snapshot.getDouble("home"),wifi=snapshot.getDouble("wifi"),gass=snapshot.getDouble("gass"),
                            cooking=snapshot.getDouble("cooking"),elec=snapshot.getDouble("elec"),prev=snapshot.getDouble("prev"),
                            pmeal=snapshot.getDouble("pmeal"),fine=snapshot.getDouble("fine"),extra=snapshot.getDouble("extra"),totalmeal=snapshot.getDouble("totalmeal");
                    LoadEvent1();
                    Calculation1(mealcredit,homecredit,home,wifi,gass,elec,cooking,prev,pmeal,fine,extra,totalmeal);
                    LoadImage1(image);
                } else {
                    Toast.makeText(admin.this,"No data found",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void Calculation1(final double mealcredit, double homecredit, double home, double wifi, double gass, double elec, double cooking,
                            double prev, double pmeal, double fine, final double extra, final double totalmeal){
        homedue1=(home+wifi+gass+elec+prev+pmeal+fine+extra+cooking)-homecredit;
        HomeDue1.setText(String.format("%.2f",homedue1));
        HomeCredit1.setText(String.format("%.0f",homecredit));
        MealCredit1.setText(String.format("%.0f",mealcredit));
        TotalMeal1.setText(String.format("%.0f",totalmeal));
        Home1.setText("Home rent: "+String.format("%.2f",home)+" tk");
        Wifi1.setText("Wifi Bill: "+String.format("%.2f",wifi)+" tk");
        Gass1.setText("Gass Bill: "+String.format("%.2f",gass)+" tk");
        Cooking1.setText("Cooking Bill: "+String.format("%.2f",cooking)+" tk");
        Electricity1.setText("Electricity Bill: "+String.format("%.2f",elec)+" tk");
        Previous1.setText("Previous Due: "+String.format("%.2f",prev)+" tk");
        MealPrevious1.setText("Previous meal Due: "+String.format("%.2f",pmeal)+" tk");
        Fine1.setText("Fine Fee: "+String.format("%.2f",fine)+" tk");
        Extra1.setText("Extra Cost: "+String.format("%.2f",extra)+" tk");
        if(homedue1>100){
            HomeNotification();
        }
        //get data from meal database................................................................
        java.util.Date d= new Date();
        months1 =String.format("%tb",d)+" "+String.format("%tY",d);
        //total expenses calculation................................................................
        String email= fAuth1.getInstance().getCurrentUser().getEmail();
        DocumentReference docRef = fstore1.collection("meal").document(months1);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(admin.this,"Load Faild",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    int i=1;
                    expenses1=0 ;
                    while (i<=31){
                        if(snapshot.getDouble(String.valueOf(i))!=null){
                            expenses1 = expenses1+snapshot.getDouble(String.valueOf(i));
                        }
                        i++;
                    }
                }

                //total meal calculation........................................................
                DocumentReference docReff = fstore1.collection("meal").document(months1).collection("totalmeal").document(months1);
                docReff.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(admin.this,"Load Faild",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {
                            int i=1;
                            countmeal1=0 ;
                            while (i<=31){
                                if(snapshot.getDouble(String.valueOf(i))!=null){
                                    countmeal1 = countmeal1+snapshot.getDouble(String.valueOf(i));
                                }
                                i++;
                            }
                        }
                        mealrate1=(expenses1/countmeal1);
                        due1=(mealrate1*totalmeal)-mealcredit;
                        mealdue1=due1;
                        expens=expenses1*1;
                        LoadData2(expenses1);
                        MealRate1.setText(String.format("%.2f",mealrate1));
                        MealDue1.setText(String.format("%.2f",due1));
                        if(due1>100){
                            MealNotification();
                        }
                    }
                });

            }
        });

    }
    public void LoadImage1(String name){
        StorageReference storageReference = storage1.getReferenceFromUrl("gs://safta-food-6c65a.appspot.com/").child("images").child(name);
        try {
            final File file = File.createTempFile("image", "jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    Image1.setImageBitmap(bitmap);
                    prg1.setVisibility(View.INVISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(admin.this, "Image load Faild", Toast.LENGTH_SHORT).show();
                    prg1.setVisibility(View.INVISIBLE);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void LoadEvent1(){
        DocumentReference docRef = fstore1.collection("event").document("new");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(admin.this,"Load Faild",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    HeadLine1.setText(snapshot.getString("head"));
                    Content1.setText(snapshot.getString("content"));
                    EventCost1=snapshot.getDouble("cost");

                } else {
                    Toast.makeText(admin.this,"No data found",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void WebView(){
        prg1.setVisibility(View.VISIBLE);
        WebEvent1.getSettings().setJavaScriptEnabled(true);
        WebEvent1.getSettings().setAppCacheEnabled(true);
        WebEvent1.getSettings().setLoadWithOverviewMode(true);
        WebEvent1.getSettings().setUseWideViewPort(true);
        WebEvent1.getSettings().setBuiltInZoomControls(true);
        WebEvent1.loadUrl("https://safta-food-6c65a.web.app/event.html");
        WebEvent1.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                prg1.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void BuyTicket(){
        if((-homedue1)>=EventCost1){
            String email=fAuth1.getInstance().getCurrentUser().getEmail();
            Toast.makeText(admin.this,"Event purched successfull",Toast.LENGTH_LONG).show();
            DocumentReference df=fstore1.collection("users").document(email);
            df.update("extra", FieldValue.increment(EventCost1));
            //========================================================
            DocumentReference dc=fstore1.collection("event").document("new")
                    .collection("subscribe").document(email);
            dc.update("Date",months1,"Number",FieldValue.increment(1));
        }else {
            Toast.makeText(admin.this,"You have not enough money in Home account",Toast.LENGTH_LONG).show();
        }
    }
    public void MealNotification(){
        Notification notification= new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_stat_1)
                .setContentTitle("Meal Dues")
                .setContentText("Your reminding due"+String.format("%.2f",mealdue1)+". "+"Please pay the money otherwise meal will be stop")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        managerCompat1.notify(1,notification);
    }
    public void HomeNotification(){
        Notification notification= new NotificationCompat.Builder(this,CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_stat_1)
                .setContentTitle("Home Dues")
                .setContentText("Due "+String.format("%.2f",homedue1)+"."+"Pay the money otherwise charged 50tk")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        managerCompat1.notify(1,notification);
    }
    public void ResetHome(){
        String[] emails=new String[9];
        emails[0]="mksa.sazzad@gmail.com";
        emails[1]="jayanta15-2641@diu.edu.bd";
        emails[2]="amanullah15-2447@diu.edu.bd";
        emails[3]="saiful15-2678@diu.edu.bd";
        emails[4]="nahid10-434@diu.edu.bd";
        emails[5]="rowshan47-318@diu.edu.bd";
        emails[6]="sujon15-2813@diu.edu.bd";
        emails[7]="hridoy15-3723@diu.edu.bd";
        for(i=0;i<8;i++){
            final DocumentReference sfDocRef = fstore1.collection("users").document(emails[i]);

            fstore1.runTransaction(new Transaction.Function<Void>() {
                @Override
                public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                    DocumentSnapshot snapshot = transaction.get(sfDocRef);

                    // Note: this could be done without a transaction
                    //       by updating the population using FieldValue.increment()
                    double previous= (snapshot.getDouble("home")+snapshot.getDouble("cooking")+
                            snapshot.getDouble("elec")+snapshot.getDouble("wifi")+snapshot.getDouble("gass")+
                            snapshot.getDouble("pmeal")+snapshot.getDouble("prev")+snapshot.getDouble("fine")
                    +snapshot.getDouble("extra"))-snapshot.getDouble("homecredit");
                    transaction.update(sfDocRef, "prev", previous,"homecredit",0,"extra",0,
                            "fine",0,"cooking",0,"elec",0,"gass",0,"wifi",0,"home",0);
                    // Success
                    return null;
                }
            });
        }
        Toast.makeText(admin.this,"Delete data successfull",Toast.LENGTH_LONG).show();
    }
    public void ResetMeal(){
        String[] emails=new String[9];
        emails[0]="mksa.sazzad@gmail.com";
        emails[1]="jayanta15-2641@diu.edu.bd";
        emails[2]="amanullah15-2447@diu.edu.bd";
        emails[3]="saiful15-2678@diu.edu.bd";
        emails[4]="nahid10-434@diu.edu.bd";
        emails[5]="rowshan47-318@diu.edu.bd";
        emails[6]="sujon15-2813@diu.edu.bd";
        emails[7]="hridoy15-3723@diu.edu.bd";
        for(i=0;i<8;i++){
            final DocumentReference sfDocRef = fstore1.collection("users").document(emails[i]);

            fstore1.runTransaction(new Transaction.Function<Void>() {
                @Override
                public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                    DocumentSnapshot snapshot = transaction.get(sfDocRef);

                    // Note: this could be done without a transaction
                    //       by updating the population using FieldValue.increment()
                    double pmeal = (mealrate1*snapshot.getDouble("totalmeal"))-snapshot.getDouble("mealcredit");
                    transaction.update(sfDocRef, "pmeal", pmeal,"totalmeal",0,"mealcredit",0);
                    // Success
                    return null;
                }
            });
        }
        Toast.makeText(admin.this,"Delete data successfull",Toast.LENGTH_LONG).show();
    }
    public void MealSavings(final double hand){
        fstore1.collection("savings").document("1")
                .update("meal",hand).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(admin.this,"Handcash update successfull",Toast.LENGTH_LONG).show();
            }
        });
        a=false;
    }
    public void HomeSavings(final double total){
        final DocumentReference sfDocRef = fstore1.collection("savings").document("1");

        fstore1.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(sfDocRef);

                // Note: this could be done without a transaction
                //       by updating the population using FieldValue.increment()
                double hand = total-snapshot.getDouble("expenses");
                transaction.update(sfDocRef, "home", FieldValue.increment(hand),"month",months1);
                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(admin.this,"Home handcash update successfull",Toast.LENGTH_LONG).show();
            }
        });
        b=false;
    }
    public void LoadData2(final double ex){
        String[] emails=new String[9];
        emails[0]="mksa.sazzad@gmail.com";
        emails[1]="jayanta15-2641@diu.edu.bd";
        emails[2]="amanullah15-2447@diu.edu.bd";
        emails[3]="saiful15-2678@diu.edu.bd";
        emails[4]="nahid10-434@diu.edu.bd";
        emails[5]="rowshan47-318@diu.edu.bd";
        emails[6]="sujon15-2813@diu.edu.bd";
        emails[7]="hridoy15-3723@diu.edu.bd";
        final String[] Name=new String[9];
        final double[] due=new double[9];
        final double[] homedue=new double[9];
        fstore1.collection("users")
                .whereEqualTo("switch", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            i=0;
                            totalHomeCredit=0;
                            totalMealCredit=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                totalMealCredit=totalMealCredit+document.getDouble("mealcredit");
                                totalHomeCredit=totalHomeCredit+document.getDouble("homecredit");
                                Name[i]=document.getString("Name");
                                due[i]=(mealrate1*document.getDouble("totalmeal"))-document.getDouble("mealcredit");
                                homedue[i]=document.getDouble("home")+document.getDouble("cooking")+
                                        document.getDouble("elec")+document.getDouble("wifi")+document.getDouble("gass")
                                        +document.getDouble("pmeal")+document.getDouble("prev")+document.getDouble("fine")
                                        +document.getDouble("extra")-document.getDouble("homecredit");
                                i++;
                            }
                            String mix="";
                            for(i=0;i<8;i++){
                                mix += Name[i] + "\n" + "Meal Due: " + String.format("%.2f", due[i]) + " tk" + "\n" + "Home Due: " + String.format("%.2f", homedue[i]) + " tk\n" + "\n";
                            }
                            Txt.setText(mix);
                            Txt.setMovementMethod(new ScrollingMovementMethod());
                            hand=totalMealCredit-ex;
                            //update hand cash for meal account.................................................
                            if(a==true){
                                MealSavings(hand);
                            }else if(b==true){
                                HomeSavings(totalHomeCredit);
                            }

                        } else {
                            Toast.makeText(admin.this,"not found",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        Month1.setText("For the month: "+months1);
    }
    public void close(View view){
        Fragment1.setVisibility(View.GONE);
    }
    public double getHandcash(double hands){
        return hands;
    }
    public void FirebaseNotify(){
        // Read from the database
        database=FirebaseDatabase.getInstance();
        database.getReference("users")
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               Notification();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
               // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    public void Notification(){
        Notification notification= new NotificationCompat.Builder(this,CHANNEL_3_ID)
                .setSmallIcon(R.drawable.ic_stat_5)
                .setContentTitle("New Request")
                .setContentText("View the notification on notification bar!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        managerCompat1.notify(1,notification);
    }

}
