package com.example.currency;

public interface WebServiceListener {
    void OnTaskCompleted(float usd, float euro, float gbp, float chf, float cad, float nok, String strNow,String strEarlier);
}
