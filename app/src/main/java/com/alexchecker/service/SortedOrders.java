package com.alexchecker.service;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexchecker.service.API.APICalls;
import com.alexchecker.service.API.Models.OrderModel;
import com.alexchecker.service.API.RequestBuilder;
import com.alexchecker.service.Adapters.OrdersAdapter;
import com.alexchecker.service.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SortedOrders extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sorted_orders, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView r = view.findViewById(R.id.sorted_orders);

        APICalls api = RequestBuilder.buildRequest().create(APICalls.class);
        Call<List<OrderModel>> ored = api.getSortedOrders(Utils.login,"Bearer "+Utils.tokens.getAccess());
        ored.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                if(response.isSuccessful())
                {
                    r.setLayoutManager(new LinearLayoutManager(getContext()));
                    OrdersAdapter adap = new OrdersAdapter(response.body());
                    r.setHasFixedSize(true);
                    r.setAdapter(adap);
                }
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {

            }
        });
    }
}