package com.bracq.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListePiece extends AppCompatActivity {
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_piece);


        Intent intent = getIntent();
        token = intent.getStringExtra(Manager.TOKEN);
        loadList();
    }

    public void loadList()
    {
        Context that = this;

        AndroidNetworking.get("https://myhouse.lesmoulinsdudev.com/rooms")
                .addHeaders("Authorization","Bearer "+token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray pieces = response.getJSONArray("rooms");

                            ArrayList<Piece> ListPiece = new ArrayList<>();

                            for (int i = 0; i < pieces.length(); ++i){
                                final JSONObject piece = pieces.getJSONObject(i);

                                ListPiece.add(new Piece(
                                        piece.getInt("id"),
                                        piece.getString("name"),
                                        piece.getString("picture")
                                ));
                            }
                            PieceAdapter adapter = new PieceAdapter(that, R.layout.room_item ,ListPiece);
                            ListView listView = findViewById(R.id.ListPiece);
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }


    public void ClickNewPiece(View view){
        Intent intent = new Intent(this, NewPiece.class);
        intent.putExtra(Manager.TOKEN, token);
        startActivity(intent);
    }
}