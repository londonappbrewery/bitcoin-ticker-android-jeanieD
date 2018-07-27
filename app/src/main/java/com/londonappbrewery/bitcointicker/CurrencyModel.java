package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyModel {
    private Double mLastPrice;
    private String mPriceError;
    private Double mPercentChange;

    // TODO: Create a PriceDataModel from a JSON:
    public static CurrencyModel fromJson(JSONObject jsonObject) {

        try {

            CurrencyModel currencyData = new CurrencyModel();
            currencyData.mLastPrice = jsonObject.getDouble("last");
            currencyData.mPercentChange = jsonObject.getJSONObject("changes").getJSONObject("percent").getDouble("day");

            return currencyData;


        } catch (JSONException e) {
            e.printStackTrace();
            CurrencyModel currencyData = new CurrencyModel();
            currencyData.mPriceError = "ERROR";
            return currencyData;
        }

    }

    //getter method for price


    public Double getLastPrice() {
        return mLastPrice;
    }

    public String getPriceError() {
        return mPriceError;
    }

    public Double getPercentChange() {
        return mPercentChange;
    }
}
