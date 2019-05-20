package com.example.vignesh.checke;

import android.content.Intent;
import android.icu.util.IndianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FilterActivity extends AppCompatActivity {

    Spinner spinner,spinner2,spinner4;
    public static JSONObject jsonObject3;
    public static String amount ="";
    public static String heads= "";
    public static String from="";
    public int i;
    static List<Hist> GetDataAdapter1;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);

        button = (Button)findViewById(R.id.btnGetMoreResults);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                amount = spinner.getSelectedItem().toString();
                heads = spinner4.getSelectedItem().toString();
                from = spinner2.getSelectedItem().toString();
                if(amount.trim().equals("No Filter"))
                {
                    amount="";
                }

                if(heads.trim().equals("No Filter"))
                {
                    heads="";
                }

                getHist(HomeMain.busno,HomeMain.trip);
                Intent intent=new Intent();
                intent.putExtra("MESSAGE","Hello");
                setResult(2,intent);
                finish();
            }
        });


        spinner = (Spinner)findViewById(R.id.spinner);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner4 = (Spinner)findViewById(R.id.spinner4);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(FilterActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.amount));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(FilterActivity.this,android.R.layout.simple_list_item_1,HomeMain.worldlist);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter1);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(FilterActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.heads));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(arrayAdapter3);

    }




    public void getHist(final String busno,final String trip)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_CONDHIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonObject3 = new JSONObject(response);
                    Log.e("msg",jsonObject3.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error",error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("busno",busno);
                params.put("trip",trip);
                params.put("heads",heads);
                params.put("amount",amount);
                params.put("from",from);
                return params;
            }
        };

        RequestHandler.getInstance(getApplication()).addToRequestQueue(stringRequest);


    }



    public static List<Hist> check() {

        JSONObject json;
        GetDataAdapter1 = new ArrayList<>();
        try {
            JSONArray cast = jsonObject3.getJSONArray("hist");
            for (int i=0; i<cast.length(); i++) {
                Hist GetDataAdapter2 = new Hist();
                json = cast.getJSONObject(i);
                String s = json.getString("from") + "---->" + json.getString("to");


                GetDataAdapter2.setStop(s);
                GetDataAdapter2.setOldamount(json.getString("oldamount"));
                GetDataAdapter2.setTotalamount(json.getString("totalamount"));
                GetDataAdapter2.setAmount(json.getString("amount"));
                GetDataAdapter2.setFrom(json.getString("from"));
                GetDataAdapter2.setTo(json.getString("to"));
                GetDataAdapter2.setTicketid(json.getString("token"));
                GetDataAdapter2.setHeads(json.getString("heads"));

                GetDataAdapter1.add(GetDataAdapter2);

            }} catch (JSONException e) {
            e.printStackTrace();
        }


        return GetDataAdapter1;
    }
}
