package com.example.currency;

import android.content.Intent;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements WebServiceListener
{

    String date;
    String status;
    String strNow;
    String strEarlier;
    ArrayList<String> euro = new ArrayList<>();
    ArrayList<String> dolar = new ArrayList<>();
    ArrayList<String> pound = new ArrayList<>();


    ArrayList<Currency> currencyArrayList = new ArrayList<>();
    private ListView listView;
    private CurrencyAdapter currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= (ListView) findViewById(R.id.listView);
        currencyArrayList.add(new Currency(R.drawable.us,"USD", "Amerikan Doları"));
        currencyArrayList.add(new Currency(R.drawable.eu,"EURO", "Avrupa Para Birimi"));
        currencyArrayList.add(new Currency(R.drawable.uk,"GBP", "İngiliz Sterlini"));

        currencyAdapter = new CurrencyAdapter(this,currencyArrayList);
        listView.setAdapter(currencyAdapter);

        try{
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("api.exchangeratesapi.io")
                    .appendPath("latest")
                    .appendQueryParameter("base","TRY");
            String myUrl = builder.build().toString();
            //System.out.println("URL= " + myUrl);
            status="currency";
            DownloadData downloadData = new DownloadData(this,status);
            downloadData.execute(myUrl);
        } catch (Exception e){
        }

    }


    @Override
    public void OnTaskCompleted(final String strNow, final String strEarlier) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("currency", currencyArrayList.get(position).getLongName());
                intent.putExtra("strNow", strNow);
                intent.putExtra("strEarlier", strEarlier);
                startActivity(intent);
            }
        });
    }
}

