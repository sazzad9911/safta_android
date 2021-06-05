package www.scientistx.saftafood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutUs extends AppCompatActivity {

    WebView Web;
    SwipeRefreshLayout Swip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Swip=findViewById(R.id.refresh1);
        Swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Webview();
            }
        });
        Webview();
    }
    public void Webview(){
        Web=findViewById(R.id.about_us);
        Web.getSettings().setJavaScriptEnabled(true);
        Web.getSettings().setAppCacheEnabled(true);
        Web.getSettings().setLoadWithOverviewMode(true);
        Web.getSettings().setUseWideViewPort(true);
        Web.getSettings().setBuiltInZoomControls(true);
        Web.loadUrl("https://safta-food-6c65a.web.app/home.html");
        Swip.setRefreshing(true);
        Web.setWebViewClient(new WebViewClient(){
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                Web.loadUrl("file:///android_safta/error.html");
            }
            public void onPageFinished(WebView view, String url){
                Swip.setRefreshing(false);
            }

        });
    }

    @Override
    public void onBackPressed() {
        if(Web.canGoBack()){
            Web.canGoBack();
        }else{
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
}

