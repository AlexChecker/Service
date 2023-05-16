package com.alexchecker.service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.alexchecker.service.API.APICalls;
import com.alexchecker.service.API.Models.OrderStatusModel;
import com.alexchecker.service.API.RequestBuilder;
import com.alexchecker.service.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainScreen extends AppCompatActivity {

    public void setFrag(Fragment frag)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.spaceForFragments,frag,null).commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        APICalls api = RequestBuilder.buildRequest().create(APICalls.class);

        Call<List<OrderStatusModel>> oreds = api.getOrderStatuses("Bearer "+Utils.tokens.getAccess());

        oreds.enqueue(new Callback<List<OrderStatusModel>>() {
            @Override
            public void onResponse(Call<List<OrderStatusModel>> call, Response<List<OrderStatusModel>> response) {
                if(response.isSuccessful())
                {
                    Utils.OrderStatuses = response.body();
                }

            }

            @Override
            public void onFailure(Call<List<OrderStatusModel>> call, Throwable t) {

            }
        });

        BottomNavigationView bottom = findViewById(R.id.bottomNavigationView);
        bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.OrderHistory) {
                    setFrag(new OrderHistory());
                    return true;
                }
                if(item.getItemId() == R.id.filterOrder)
                {
                    setFrag(new OrderFilter());
                    return true;
                }
                if(item.getItemId() == R.id.sortOrder)
                {
                    setFrag(new SortedOrders());
                    return true;
                }
                if(item.getItemId() == R.id.FindOrder)
                {
                    setFrag(new SearchOrders());
                    return true;
                }
                if(item.getItemId() == R.id.korzina)
                {
                    setFrag(new Ordered());
                    return true;
                }
                return false;
            }
        });
    }
}