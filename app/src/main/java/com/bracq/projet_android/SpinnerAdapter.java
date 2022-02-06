package com.bracq.projet_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<Image> {
    ArrayList<Image> images;
    Context context;
    int resources;

    public SpinnerAdapter(Context context, int resource, ArrayList<Image> objects) {
        super(context, resource, objects);
        this.context = context;
        this.images = objects;
        this.resources = resource;
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(resources, null, false);

        //getting the view elements of the list from the view
        ImageView imageView = view.findViewById(R.id.imageViewSpinner);

        //getting the hero of the specified position
        Image image = images.get(position);

        //adding values to the list item
        Picasso.get().load("https://myhouse.lesmoulinsdudev.com/" + image.getUrl()).into(imageView);
        //finally returning the view
        return view;
    }
}
