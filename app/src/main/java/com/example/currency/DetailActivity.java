package com.example.currency;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements WebServiceListener{

    HorizontalBarChart horizontalBarChart;
    String currency;
    String strEarlier;
    String strNow;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);

        horizontalBarChart = (HorizontalBarChart) findViewById(R.id.chartBar);

        Intent intent = getIntent();

        currency = intent.getStringExtra("currency");
        strEarlier = intent.getStringExtra("strEarlier");
        strNow= intent.getStringExtra("strNow");
        //System.out.println(strEarlier);
        //System.out.println(strNow);

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
            DownloadData downloadBigData = new DownloadData(this, status);
            downloadBigData.execute(newUrl);
        } catch (Exception e){
        }
    }

    @Override
    public void OnTaskCompleted(float usd, float euro, float gbp, float chf, float cad, float nok, String strNow, String strEarlier) {

    }

    @Override
    public void OnDetailTaskCompleted(final ArrayList<String> datelist, ArrayList<Float> usdList, ArrayList<Float> euroList,
                                      ArrayList<Float> gbpList, ArrayList<Float> chfList, ArrayList<Float> cadList, ArrayList<Float> nokList) {
        //System.out.println(datelist);
        //System.out.println(usdList);

        switch (currency){
        }





        
        ArrayList<BarEntry> yVals = new ArrayList<>();
        float barWidth = 1f;
        float spaceForBar = 1f;

        for(int i=0;i<datelist.size();i++){
            yVals.add(new BarEntry(i*spaceForBar,nokList.get(i)));
        }

        BarDataSet barDataSet;

        barDataSet = new BarDataSet(yVals, "Data Set Deneme");

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(barWidth);

        final XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(datelist));

        horizontalBarChart.setData(barData);
    }
}
