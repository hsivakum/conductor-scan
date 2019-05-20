package com.example.vignesh.checke;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Vignesh on 21-03-2018.
 */

public class ProfileFragment extends Fragment{


    Button logout;
    public static ProfileFragment newInstance(int instance)
    {
        Bundle args = new Bundle();
        args.putInt("argsInstance",instance);
        ProfileFragment homeFragment = new ProfileFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile,container,false);
        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
                    getActivity().finish();
                    SharedPrefManager.getInstance(getActivity()).logout();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
        return view;
    }
}
