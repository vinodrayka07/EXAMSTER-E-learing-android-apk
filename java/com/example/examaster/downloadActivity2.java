package com.example.examaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.trusted.ScreenOrientation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.github.barteksc.pdfviewer.PDFView;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import com.github.barteksc.pdfviewer.PDFView;


public class downloadActivity2 extends AppCompatActivity {


    private PDFView pdfView;
    Button btn;
    private static final int REQUEST_CODE = 100;
    ProgressBar progressBar;


    @SuppressLint("SetJavaScriptEnabled")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download2);
        pdfView = findViewById(R.id.pdfView);
        btn = findViewById(R.id.bt);
        progressBar = findViewById(R.id.progressBar);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);



            }



        }

        getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();

        String url = bundle.getString("otherurl");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager.Request request =new DownloadManager.Request(Uri.parse(url));
                String title = URLUtil.guessFileName(url,null,null);
                request.setTitle(title);
                request.setDescription("Dowloading....");
                String cookie = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie",cookie);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);

                DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);

                Toast.makeText(downloadActivity2.this, "Downloding....", Toast.LENGTH_SHORT).show();
            }
        });











                new pdfdownload().execute(url);


            }




        private class pdfdownload extends AsyncTask<String, Void, InputStream>{


            @Override
            protected InputStream doInBackground(String... strings) {
                InputStream inputStream = null;

                try {
                    URL url = new URL(strings[0]);

                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                    if (urlConnection.getResponseCode() == 200){

                        inputStream = new BufferedInputStream(urlConnection.getInputStream());



                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return inputStream;

            }





            @Override
            protected void onPostExecute(InputStream inputStream) {
                pdfView.fromStream(inputStream).load();
                progressBar.setVisibility(View.GONE);
                //btn.setVisibility(View.VISIBLE);

            }



        }




    }
