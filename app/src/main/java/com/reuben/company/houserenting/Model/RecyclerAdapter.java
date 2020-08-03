package com.reuben.company.houserenting.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.core.Context;
import com.reuben.company.houserenting.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHollder> {
    private static final String Tag = "RecyclerView";
    public Context mContext;
    private ArrayList<RecyclerViewModel> messagesList;

    public RecyclerAdapter(Context mContext, ArrayList<RecyclerViewModel> messagesList) {
        this.mContext = mContext;
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHollder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        return new ViewHollder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHollder holder, int position) {
        //TextViews
        holder.textView.setText(messagesList.get(position).getImageName());
        holder.price.setText(messagesList.get(position).getImagePrice());
        holder.location.setText(messagesList.get(position).getImageLoc());

        holder.room.setText(messagesList.get(position).getHouseRoom());
        holder.kitchen.setText(messagesList.get(position).getHouseKitchen());
        holder.parking.setText(messagesList.get(position).getHouseParking());

//        holder.bedroom.setText(messagesList.get(position).getBedroom());
//        holder.dinningroom.setText(messagesList.get(position).getDinningroom());
//        holder.livingroom.setText(messagesList.get(position).getLivingroom());
//        Glide.with(holder.itemView.getContext())
//                .load(messagesList.get(position).getImageURL())
//                .fitCenter()
//                .into(holder.imageView);
        //Picasso.get().load(messagesList.get(position).getImageURL()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class ViewHollder extends RecyclerView.ViewHolder {
        //Widgets
        public ImageView imageView;
        public TextView textView;
        public TextView price;
        public TextView location;

        public TextView room;
        public TextView kitchen;
        public TextView parking;

        public TextView bedroom;
        public TextView dinningroom;
        public TextView livingroom;

        public ViewHollder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rImgV);
            textView = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.PriceTv);
            location = itemView.findViewById(R.id.rTv);
            //Footer of Card
            room = itemView.findViewById(R.id.rum);
            kitchen = itemView.findViewById(R.id.kicheni);
            parking = itemView.findViewById(R.id.paking);
            //layer below
//            bedroom = itemView.findViewById(R.id.bedroom);
//            dinningroom = itemView.findViewById(R.id.dinningRoom);
//            livingroom = itemView.findViewById(R.id.sitting);
        }
    }
}