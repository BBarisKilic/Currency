package com.example.currency;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class DownloadData extends AsyncTask<String, Void, String> {

    public WebServiceListener delegate = null;

    String date;
    String currency;
    String status;
    String strNow;
    String strEarlier;
    ArrayList<String> dateList = new ArrayList<>();

    public DownloadData (WebServiceListener delegate, String status){
        this.delegate=delegate;
        this.status=status;
    }

    public DownloadData (String status,String currency){
        this.status=status;
        this.currency=currency;
    }


    @Override
    protected String doInBackground(String... strings) {

        String result = "";
        URL url;
        HttpsURLConnection httpsURLConnection;

        try {
            url = new URL(strings[0]);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int data = inputStreamReader.read();
            while (data > 0) {
                char ch = (char) data;
                result += ch;

                data = inputStreamReader.read();
            }
            inputStreamReader.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        switch (status) {
            case "currency":
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String base = jsonObject.getString("base");
                    //System.out.println("base"+base);

                    date = jsonObject.getString("date");

                    String rates = jsonObject.getString("rates");
                    //System.out.println("rates"+rates);

                    JSONObject jsonObject1 = new JSONObject(rates);
                    String euro = jsonObject1.getString("EUR");
                    String dolar = jsonObject1.getString("USD");
                    String gbpound = jsonObject1.getString("GBP");
                    String chf_string = jsonObject1.getString("CHF");
                    String cad_string = jsonObject1.getString("CAD");
                    String nok_string = jsonObject1.getString("NOK");
                    float eur = Float.valueOf(euro);
                    float reciprocal_euro = 1 / eur;
                    float usd = Float.valueOf(dolar);
                    float reciprocal_usd = 1 / usd;
                    float gbp = Float.valueOf(gbpound);
                    float reciprocal_gbp = 1 / gbp;
                    float chf = Float.valueOf(chf_string);
                    float reciprocal_chf = 1/ chf;
                    float cad = Float.valueOf(cad_string);
                    float reciprocal_cad = 1 / cad;
                    float nok = Float.valueOf(nok_string);
                    float reciprocal_nok = 1 / nok;
                    //System.out.println("1 EUR = "+ reciprocal_euro + " TRY");
                    //System.out.println("1 USD = "+ reciprocal_usd + " TRY");
                    //System.out.println("1 GBP = "+ reciprocal_gbp + " TRY");

                    try {
                        Date now = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                        Date earlier = new Date();
                        earlier.setDate(now.getDate() - 6);
                        strNow = new SimpleDateFormat("yyyy-MM-dd").format(now);
                        strEarlier = new SimpleDateFormat("yyyy-MM-dd").format(earlier);
                        //System.out.println(strNow);
                        //System.out.println(strEarlier);
                    } catch (ParseException e){
                    }

                    delegate.OnTaskCompleted(reciprocal_usd,reciprocal_euro,reciprocal_gbp,reciprocal_chf, reciprocal_cad, reciprocal_nok, strNow,strEarlier);

                } catch (Exception e) {
                }
                break;
            case "analyze":
                try {
                    JSONObject newJsonObject = new JSONObject(s);
                    String newBase = newJsonObject.getString("base");
                    //System.out.println("base"+base);

                    String newRates = newJsonObject.getString("rates");
                    //System.out.println("rates"+rates);

                    JSONObject dates = newJsonObject.getJSONObject("rates");
                    Iterator keys = dates.keys();

                    while (keys.hasNext()) {
                        String currentDynamicKey = (String) keys.next();
                        //System.out.println(currentDynamicKey);
                        dateList.add(currentDynamicKey);
                        Collections.sort(dateList);
                        //System.out.println(dateList);
                    }
                    for (int i = 0; i < dateList.size(); i++) {
                        //System.out.println(dateList.get(i));
                        JSONObject currentDynamicValue = dates.getJSONObject(dateList.get(i));
                        String euro = currentDynamicValue.getString("EUR");
                        String dolar = currentDynamicValue.getString("USD");
                        String gbpound = currentDynamicValue.getString("GBP");
                        float eur = Float.valueOf(euro);
                        float reciprocal_euro = 1 / eur;
                        System.out.println("1 EUR = "+ reciprocal_euro + " TRY");
                    }
                } catch (Exception e) {
                }
                break;
            default:
                break;
        }
        super.onPostExecute(s);
    }
}
