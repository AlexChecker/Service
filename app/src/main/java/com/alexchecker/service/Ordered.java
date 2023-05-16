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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Ordered extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ordered, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rec = view.findViewById(R.id.ordered_rec);
        APICalls api = RequestBuilder.buildRequest().create(APICalls.class);
        Call<List<OrderModel>> ored = api.getOrders(Utils.login,"Bearer "+Utils.tokens.getAccess(),0);
        ored.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                ArrayList<OrderModel> sortedType = new ArrayList<>();
                for (OrderModel o:
                        response.body()) {
                    if(o.getStatus() == 2) sortedType.add(o);
                }

                rec.setLayoutManager(new LinearLayoutManager(getContext()));
                rec.setHasFixedSize(true);
                OrdersAdapter ada = new OrdersAdapter(sortedType);
                rec.setAdapter(ada);

            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {

            }
        });
    }
}