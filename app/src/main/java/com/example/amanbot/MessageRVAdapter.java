package com.example.amanbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageRVAdapter extends RecyclerView.Adapter {

    private ArrayList<Message> messageArrayList;

    public MessageRVAdapter(ArrayList<Message> messageArrayList) {
        this.messageArrayList = messageArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case 0:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg, parent, false);
                return new UserViewHolder(view);
            case 1:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg, parent, false);
                return new BotViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //this method is use to set data to our layout file.
        Message modal = messageArrayList.get(position);
        switch (modal.getSender()) {
            case "user":

                ((UserViewHolder) holder).userTV.setText(modal.getMessage());
                break;
            case "bot":

                ((BotViewHolder) holder).botTV.setText(modal.getMessage());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //below line of code is to set position.
        switch (messageArrayList.get(position).getSender()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }

    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        //creating a variable for our text view.
        TextView userTV;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            //initializing with id.
            userTV = itemView.findViewById(R.id.idTVUser);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder {
        //creating a variable for our text view.
        TextView botTV;

        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            //initializing with id.
            botTV = itemView.findViewById(R.id.idTVBot);
        }
    }


}
