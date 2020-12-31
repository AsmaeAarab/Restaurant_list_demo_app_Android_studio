package com.example.tprestaurant.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tprestaurant.Activities.AcceuilActivity;
import com.example.tprestaurant.Activities.LoginActivity;
import com.example.tprestaurant.R;
import com.example.tprestaurant.SharedPrefs.Authentification_Shared_Preferences;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menubar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menubar extends Fragment {

    private Context contextDuFragement;

    public Menubar() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Menubar newInstance(String param1, String param2) {
        Menubar fragment = new Menubar();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_menubar, container, false);
        ButterKnife.bind(this,view);
        contextDuFragement=view.getContext();
        setHasOptionsMenu(true);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater in) {
        in.inflate(R.menu.menu_bar,menu);
        super.onCreateOptionsMenu(menu,in);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.home_icon){
            Intent acceuilpage = new Intent(contextDuFragement, AcceuilActivity.class);
            acceuilpage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(acceuilpage);
            getActivity().finish();
        }
        if (id == R.id.logOut){
            Authentification_Shared_Preferences.DestroyPrefs(contextDuFragement);
            Intent loginpage = new Intent(contextDuFragement, LoginActivity.class);

            startActivity(loginpage);
        }
        return super.onOptionsItemSelected(item);
    }
}