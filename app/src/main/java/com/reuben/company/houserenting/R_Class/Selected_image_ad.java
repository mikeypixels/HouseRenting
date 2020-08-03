package com.reuben.company.houserenting.R_Class;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.reuben.company.houserenting.R;

import java.util.List;

public class Selected_image_ad extends RecyclerView.Adapter<Selected_image_ad.Slidehold> {
public List<String> imgname;
public List<Bitmap> img;

    public Selected_image_ad(List<String> imgname, List<Bitmap> img) {
        this.imgname = imgname;
        this.img = img;
    }


    @NonNull
    @Override
    public Slidehold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Slidehold(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Slidehold holder, int position) {
        holder.imagename.setText(imgname.get(position));
        holder.imageView.setImageBitmap(img.get(position));
    }

    @Override
    public int getItemCount() {
        return imgname.size();
    }

    class Slidehold extends RecyclerView.ViewHolder {
        View view;
        public TextView imagename;
        private ImageView imageView;

        public Slidehold(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageView = view.findViewById(R.id.img);
            imagename = view.findViewById(R.id.imgname);

        }
        void single_image(Bitmap bitmap){
            imageView.setImageBitmap(bitmap);
        }


    }

}
