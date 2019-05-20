package com.example.vignesh.checke;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vignesh on 21-03-2018.
 */

public class HistoryFragment extends Fragment{

    static List<Hist> GetDataAdapter1;
    String JSON_ID = "from";
    String JSON_NAME = "to";
    String JSON_SUBJECT = "heads";
    String JSON_PHONE_NUMBER = "bookdate";
    TextView filter;
    RecyclerView.Adapter recyclerViewadapter;
    SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView rv;

    public static HistoryFragment newInstance(int instance)
    {
        Bundle args = new Bundle();
        args.putInt("argsInstance",instance);
        HistoryFragment homeFragment = new HistoryFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_history,container,false);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.simpleSwipeRefreshLayout);
        ScrollView scrollable_contents = (ScrollView) view.findViewById(R.id.scrollableContents);
        getLayoutInflater().inflate(R.layout.scrollable_content, scrollable_contents);
        filter = (TextView)view.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),FilterActivity.class);
                startActivityForResult(i, 2);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh
                swipeRefreshLayout.setRefreshing(false);
                setAdap(view);
            }
        });

        return view;
    }

    public void setAdap(View view)
    {
        rv = (RecyclerView) view.findViewById(R.id.quiz_list);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        GetDataAdapter1 = FilterActivity.check();
        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, getActivity());

        rv.setAdapter(recyclerViewadapter);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            setAdap(getView());
        }
    }
}
