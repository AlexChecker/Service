package com.alexchecker.service;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alexchecker.service.API.APICalls;
import com.alexchecker.service.API.Models.OrderModel;
import com.alexchecker.service.API.RequestBuilder;
import com.alexchecker.service.Adapters.OrdersAdapter;
import com.alexchecker.service.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchOrders extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_orders, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rec = view.findViewById(R.id.search_recycler);

        Button b = view.findViewById(R.id.searchButton);
        TextView text = view.findViewById(R.id.searchText);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APICalls api = RequestBuilder.buildRequest().create(APICalls.class);
                Call<List<OrderModel>> ored = api.getFindOrders(Utils.login,text.getText().toString(),"Bearer "+Utils.tokens.getAccess());
                ored.enqueue(new Callback<List<OrderModel>>() {
                    @Override
                    public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                        rec.setLayoutManager(new LinearLayoutManager(getContext()));
                        rec.setHasFixedSize(true);
                        OrdersAdapter ada = new OrdersAdapter(response.body());
                        rec.setAdapter(ada);
                    }

                    @Override
                    public void onFailure(Call<List<OrderModel>> call, Throwable t) {

                    }
                });
            }
        });
    }
}