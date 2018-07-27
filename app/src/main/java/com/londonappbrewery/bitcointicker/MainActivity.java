package com.londonappbrewery.bitcointicker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

    // Member Variables:
    TextView mPriceTextView;
    TextView mPercentChange;
    String mURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        mPercentChange = (TextView) findViewById(R.id.dailyChange);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Am I Rich", "item selected " + parent.getItemAtPosition(position));
                mURL = BASE_URL + parent.getItemAtPosition(position);
                Log.d("Am I Rich", mURL);
                networkRequest(mURL);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Am I Rich", "nothing selected");
            }
        });





    }

    // TODO: complete the networkRequest() method
    //here's where we call Volley to process async HTTP requests

    public void networkRequest(String url) {
        //Instantiate the Volley RequestQueue
        RequestQueue myQueue = Volley.newRequestQueue(this);


        //request a json response from the provided URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //code to do something with the JSON response
                Log.d("Am I Rich", "no error received, received JSON" + response.toString());
                //pass the JSON response to the CurrencyModel
                CurrencyModel CurrencyData = CurrencyModel.fromJson(response);
                mPriceTextView.setText(String.valueOf(CurrencyData.getLastPrice()));
                mPercentChange.setText(String.valueOf(CurrencyData.getPercentChange()) + '%');

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //code to do something with the error
                Log.d("Am I Rich", "received error, code is: " + error);
                Toast.makeText(MainActivity.this, "Request failed", Toast.LENGTH_SHORT).show();

            }
        });

        myQueue.add(jsonObjectRequest);



    }





}
