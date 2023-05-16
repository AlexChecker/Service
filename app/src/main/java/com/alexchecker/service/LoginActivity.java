package com.alexchecker.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alexchecker.service.API.APICalls;
import com.alexchecker.service.API.Models.LoginModel;
import com.alexchecker.service.API.RequestBuilder;
import com.alexchecker.service.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText login;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.editLogin);
        password = findViewById(R.id.editPassword);
    }

    public void button_login(View view) {
        APICalls api = RequestBuilder.buildRequest().create(APICalls.class);

       Call<LoginModel> logIn = api.LogIn(login.getText().toString(),password.getText().toString());
        Intent intent = new Intent(this, MainScreen.class);

       logIn.enqueue(new Callback<LoginModel>() {
           @Override
           public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
               if(response.isSuccessful())
               {
                   Utils.tokens = response.body();
                   startActivity(intent);
                   Utils.login=login.getText().toString();
               }
           }

           @Override
           public void onFailure(Call<LoginModel> call, Throwable t) {
               Toast.makeText(LoginActivity.this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
           }
       });
    }
}