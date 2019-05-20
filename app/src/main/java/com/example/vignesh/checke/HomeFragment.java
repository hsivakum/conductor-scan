package com.example.vignesh.checke;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Vignesh on 21-03-2018.
 */

public class HomeFragment extends Fragment{

    TextView busno,trip,heads,amount;
    private Button button;
    private IntentIntegrator qrScan;
    public static JSONObject jsonObject;
    public String strDate;
    SwipeRefreshLayout swipeRefreshLayout;

    public static HomeFragment newInstance(int instance)
    {
        Bundle args = new Bundle();
        args.putInt("argsInstance",instance);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_scan,container,false);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.simpleSwipeRefreshLayout);
        checkval(HomeMain.busno,HomeMain.trip);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh

                swipeRefreshLayout.setRefreshing(false);
                checkval(HomeMain.busno,HomeMain.trip);
            }
        });
        busno = view.findViewById(R.id.busno);
        trip = view.findViewById(R.id.trip);
        amount = view.findViewById(R.id.amount);
        heads = view.findViewById(R.id.heads);
        busno.setText(HomeMain.busno);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        strDate = mdformat.format(calendar.getTime());
        trip.setText(HomeMain.trip);
        qrScan = new IntentIntegrator(getActivity());
        button = view.findViewById(R.id.scan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getScan();
            }
        });

        return view;
    }

    private void checkval(final String busno,final String trip) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_HEADS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonObject = new JSONObject(response);
                    if(!jsonObject.getBoolean("error"))
                    {
                        amount.setText(jsonObject.getString("amount"));
                        heads.setText(jsonObject.getString("heads"));
                    }
                    else
                    {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("busno",busno);
                params.put("trip",trip);
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    public void getScan()
    {
        qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        qrScan.setPrompt("Scan");
        qrScan.setCameraId(0);
        qrScan.setBeepEnabled(false);
        qrScan.setBarcodeImageEnabled(false);
        qrScan.forSupportFragment(this).initiateScan();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(getActivity(), "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                validTicket(result.getContents());
            }
        }
        else {
            Toast.makeText(getActivity(),"Nothing",Toast.LENGTH_LONG).show();
        }
    }

    private void validTicket(final String contents) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity()  ,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_TICKET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    jsonObject = new JSONObject(response);
                    if(!jsonObject.getBoolean("error"))
                    {
                        Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("token",contents);
                params.put("busno",HomeMain.busno);
                params.put("trip",HomeMain.trip);
                params.put("dates",strDate);
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }
}
