package com.example.vignesh.checke;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeMain extends AppCompatActivity {

    private BottomBar mBottomBar;
    private FragNavController fragNavController;
    public static String busno;
    public static String temp;
    public static List<String> worldlist;
    public static String trip;
    public static int i;
    public static  String cast=null;
    public static  String[] arr=null;
    public static  String[] arr1=new String[28];
    public static JSONObject jsonObject1=null;
    public static JSONObject jsonObject=null;

    //indices to fragments
    private final int TAB_FIRST = FragNavController.TAB1;
    private final int TAB_SECOND = FragNavController.TAB2;
    private final int TAB_THIRD = FragNavController.TAB3;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);

        Bundle bundle = getIntent().getExtras();
        busno = bundle.getString("busno");
        trip = bundle.getString("trip");
        getBuses();
        FilterActivity filterActivity = new FilterActivity();
        filterActivity.getHist(busno,trip);



        List<Fragment> fragments = new ArrayList<>(3);

        //add fragments to list
        fragments.add(HomeFragment.newInstance(0));
        fragments.add(HistoryFragment.newInstance(0));
        fragments.add(ProfileFragment.newInstance(0));

        fragNavController = new FragNavController(getSupportFragmentManager(),R.id.container,fragments);
        //End of FragNav

        //BottomBar menu
//        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar = findViewById(R.id.bottomBar);
        mBottomBar.setItems(R.menu.navigation);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                //switch between tabs
                switch (menuItemId) {
                    case R.id.navigation_home:
                        fragNavController.switchTab(TAB_FIRST);
                        break;
                    case R.id.navigation_dashboard:
                        fragNavController.switchTab(TAB_SECOND);
                        break;
                    case R.id.navigation_notifications:
                        fragNavController.switchTab(TAB_THIRD);
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.navigation_home) {
                    fragNavController.clearStack();
                }
            }
        });
        //End of BottomBar menu


    }

    public void getBuses()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GETBUSESSTOP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    int k=0;
                    jsonObject1 = new JSONObject(response);
                    cast = jsonObject1.getString("stops");
                    arr = cast.split(",");
                    worldlist = Arrays.asList(arr);
                    Log.e("msg",cast);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"error VOLLEY "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("busno",busno);
                return params;
            }
        };

        RequestHandler.getInstance(getApplication()).addToRequestQueue(stringRequest);
    }



}
