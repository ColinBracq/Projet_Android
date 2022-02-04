package com.bracq.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;


import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ClickConnect(View view){

        EditText champMail = (EditText) findViewById(R.id.email1);
        EditText champMdp = (EditText) findViewById(R.id.password1);

        String mail = champMail.getText().toString();
        String mdp = champMdp.getText().toString();

        AndroidNetworking.post("https://myhouse.lesmoulinsdudev.com/auth")
                .addBodyParameter( "login", mail)
                .addBodyParameter( "password", mdp)

                .build()

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String token = null;
                        try {
                            token = response.getString("token");
                            Connexion(token);
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Compte inconnu", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public void Connexion(String token){
        Intent intent = new Intent(this, ListePiece.class);
        intent.putExtra(Manager.TOKEN, token);
        startActivity(intent);
    }


    public void ClickInscription(View view){

        Intent intent = new Intent(this, CreationCompte.class);
        startActivity(intent);
    }
}