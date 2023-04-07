package com.example.bookhouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ArticleShowActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private RelativeLayout relativeLayout;
    private Button btn_no_internet;
    private String geturl,url;
    public static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_show);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        geturl=getIntent().getStringExtra("url");
        url="https://docs.google.com/viewer?embedded=true&url="+geturl;
        progressBar=findViewById(R.id.progressBar);
        btn_no_internet=findViewById(R.id.retry);
        relativeLayout=findViewById(R.id.relativelayout);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        webView=findViewById(R.id.web);

        if (savedInstanceState!=null)
        {
            webView.restoreState(savedInstanceState);
        }
        else
        {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setAllowFileAccess(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setAllowFileAccessFromFileURLs(true);
            webView.getSettings().getAllowContentAccess();
            webView.getSettings().getLayoutAlgorithm();
            webView.getSettings().getAllowUniversalAccessFromFileURLs();
            webView.getSettings().setUserAgentString(USER_AGENT);
            webView.clearCache(true);
            webView.clearHistory();
            Check();
        }
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                setTitle("Just a Second...");
                progressDialog.show();
                if (newProgress==100)
                {
                    progressBar.setVisibility(View.GONE);
                    setTitle(view.getTitle());
                    progressDialog.dismiss();
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        btn_no_internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }
    public void Check()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi=connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileinternet=connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
        if(wifi.isConnected())
        {
            webView.loadUrl(url);
            webView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
        else if(mobileinternet.isConnected())
        {
            webView.loadUrl(url);
            webView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
        else
        {
            relativeLayout.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        }
    }
}