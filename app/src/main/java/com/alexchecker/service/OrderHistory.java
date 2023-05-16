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


public class OrderHistory extends Fragment {

    private int page =0;
    RecyclerView rec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_history, container, false);
    }

    TextView pageView;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        Button p= view.findViewById(R.id.button_previous);
        p.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                page--;
                if(page < 0) page =0;
                pageView.setText(String.valueOf(page));
                updatePage();

            }
        });

        Button n= view.findViewById(R.id.button_next);
        n.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                page++;
                pageView.setText(String.valueOf(page));
                updatePage();
            }
        });
        rec = view.findViewById(R.id.orders_hist);

        APICalls api = RequestBuilder.buildRequest().create(APICalls.class);

        Call<List<OrderModel>> ored = api.getOrders(Utils.login,"Bearer "+Utils.tokens.getAccess(),0);
        pageView = view.findViewById(R.id.pageCounter);

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

    public void updatePage()
    {
        APICalls api = RequestBuilder.buildRequest().create(APICalls.class);

        Call<List<OrderModel>> ored = api.getOrders(Utils.login,"Bearer "+Utils.tokens.getAccess(),page);
        pageView = this.getView().findViewById(R.id.pageCounter);

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


    public void button_next(View view) {
        page++;
        pageView.setText(String.valueOf(page));
    }

    public void button_previous(View view) {
        page--;
        if(page < 0) page =0;
        pageView.setText(String.valueOf(page));
    }


}