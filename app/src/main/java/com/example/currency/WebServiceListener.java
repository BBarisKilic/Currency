package com.example.currency;

public interface WebServiceListener {
    void OnTaskCompleted(float usd, float euro, float gbp, String strNow,String strEarlier);
}
