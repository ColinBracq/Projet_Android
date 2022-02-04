package com.bracq.projet_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;

public class PieceAdapter extends ArrayAdapter<Piece> {

    ArrayList<Piece> pieces;
    Context context;
    int resources;

    public PieceAdapter(Context context, int resource, ArrayList<Piece> objects) {
        super(context, resource, objects);
        this.context = context;
        this.pieces = objects;
        this.resources = resources;
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resources, null, false);

        //getting the view elements of the list from the view
        ImageView imageView = view.findViewById(R.id.iconPiece);
        TextView textId = view.findViewById(R.id.idPiece);
        TextView textName = view.findViewById(R.id.nomPieceList);

        //getting the hero of the specified position
        Piece piece = pieces.get(position);

        //adding values to the list item
        textName.setText(piece.getName());
        textId.setText(piece.getId());
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(new java.net.URL(piece.getUrl()).openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bmp);
        //finally returning the view
        return view;
    }
}
