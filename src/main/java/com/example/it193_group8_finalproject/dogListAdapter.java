package com.example.it193_group8_finalproject;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it193_group8_finalproject.model.Dog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class dogListAdapter extends BaseAdapter {
    Context mContext;

    List<Dog> mDogList;
    LayoutInflater inflater;

    public dogListAdapter(Context context, List<Dog> dogList){
        mContext = context;
        mDogList = dogList;
    }

    @Override
    public int getCount() {
        return mDogList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDogList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.dog_item, parent, false);

            holder = new ViewHolder(convertView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Dog currentDog = mDogList.get(position);
        holder.dogNameTextView.setText(currentDog.getDog_name());
        Picasso.get().load(currentDog.getDog_img()).into(holder.dogImageView);

        return convertView;
    }

    static class ViewHolder{
        TextView dogNameTextView;
        ImageView dogImageView;

        ViewHolder(View itemView){

            dogNameTextView = itemView.findViewById(R.id.textView4);
            dogImageView = itemView.findViewById(R.id.imageView3);
        }
    }
}
