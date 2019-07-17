package com.example.currency;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);
        TextView textView = (TextView)findViewById(R.id.textView);

        Intent intent = getIntent();

        String currency = intent.getStringExtra("currency");
        String strEarlier = intent.getStringExtra("strEarlier");
        String strNow= intent.getStringExtra("strNow");

        System.out.println(strEarlier);
        System.out.println(strNow);

        setTitle(currency+" Analizi");
        //textView.setText(currency);

        try{
            Uri.Builder newBuilder = new Uri.Builder();
            newBuilder.scheme("https")
                    .authority("api.exchangeratesapi.io")
                    .appendPath("history")
                    .appendQueryParameter("start_at",strEarlier)
                    .appendQueryParameter("end_at",strNow)
                    .appendQueryParameter("base","TRY");
            String newUrl = newBuilder.build().toString();
            //System.out.println("URL= " + newUrl);
            String status="analyze";
            DownloadData downloadBigData = new DownloadData(status, currency);
            downloadBigData.execute(newUrl);
        } catch (Exception e){
        }
    }
}
