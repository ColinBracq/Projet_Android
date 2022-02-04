package com.bracq.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;


import java.util.ArrayList;

import okhttp3.Response;

public class NewPiece extends AppCompatActivity {

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_piece);

        Intent intent = getIntent();
        token = intent.getStringExtra(Manager.TOKEN);
        loadImages();
    }


    public void loadImages()
    {
        Manager manager = new Manager();
        ArrayList<Image> list = manager.list("room");
        // Création d’un adaptateur permettant d’afficher les images dans un Spinner
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.image, list);
        // Récupération du spinner
        Spinner spinner = findViewById(R.id.spinnerImage);
        // Mise ne place de l’adaptateur dans le spinner
        spinner.setAdapter(adapter);
    }


    public void ClickAddPiece(View view){
        EditText nomPiece = (EditText) findViewById(R.id.nomPiece4);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerImage);

        String nomP = nomPiece.getText().toString();
        Image selectedPicture = (Image) spinner.getSelectedItem();
        int idPicture = 1;//selectedPicture.getId();


        AndroidNetworking.post("https://myhouse.lesmoulinsdudev.com/room-create")
                .addHeaders("Authorization","Bearer "+token)
                .addBodyParameter("name", nomP )
                .addBodyParameter("idPicture", String.valueOf(idPicture)) // Erreur possible passage string au lieu de int

                .build()

                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        switch (response.code())
                        {
                            case 200:
                                retour();
                                break;
                            default:
                                Toast.makeText(NewPiece.this, "Erreur lors de l'enregistrement4", Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Erreur lors de l'enregistrement5", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void retour(){
        Intent intent = new Intent(this, ListePiece.class);
        intent.putExtra(Manager.TOKEN, token);
        startActivity(intent);
    }
}

