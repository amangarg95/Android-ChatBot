package com.example.amanbot;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView chatsRV;
    private ImageButton sendMsgIB;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";

    private ArrayList<Message> messageArrayList;
    private MessageRVAdapter messageRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatsRV = findViewById(R.id.idRVChats);
        sendMsgIB = findViewById(R.id.idIBSend);
        userMsgEdt = findViewById(R.id.idEdtMessage);

        messageArrayList = new ArrayList<>();
        sendMsgIB.setOnClickListener(v -> {

            if (userMsgEdt.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
                return;
            }

            getMessage(userMsgEdt.getText().toString());
            userMsgEdt.setText("");

        });

        messageRVAdapter = new MessageRVAdapter(messageArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        chatsRV.setLayoutManager(linearLayoutManager);
        chatsRV.setAdapter(messageRVAdapter);
    }

    void getMessage(String message) {
        messageArrayList.add(new Message(message, USER_KEY));
        messageRVAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=165740&key=UaWeF06RiFtdE4IX&uid=[uid]&msg=" + message;
        String baseUrl = "https://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<Chat> call = retrofitApi.getMessage(url);
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, retrofit2.Response<Chat> response) {
                Chat chat = response.body();
                messageArrayList.add(new Message(chat.getCnt(), BOT_KEY));
                messageRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Not Hooray!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}