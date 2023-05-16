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


public class OrderFilter extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_filter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button filter = view.findViewById(R.id.FilterButton);
        TextView price = view.findViewById(R.id.PriceField);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pricef = price.getText().toString();
                APICalls api = RequestBuilder.buildRequest().create(APICalls.class);
                double price = Double.valueOf(pricef);
                Call<List<OrderModel>> ored = api.getFilteredOrders(Utils.login,"Bearer "+Utils.tokens.getAccess(),price);

                ored.enqueue(new Callback<List<OrderModel>>() {
                    @Override
                    public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                        if(response.isSuccessful())
                        {
                            RecyclerView v = view.findViewById(R.id.filtered_recycler);
                            v.setLayoutManager(new LinearLayoutManager(getContext()));
                            v.setHasFixedSize(true);
                            OrdersAdapter adapter = new OrdersAdapter(response.body());
                            v.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<OrderModel>> call, Throwable t) {

                    }
                });
            }
        });
    }
}