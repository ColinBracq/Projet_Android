package com.bracq.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;

import okhttp3.Response;

public class CreationCompte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_compte);
    }

    public void register(View view){

        EditText champMail = (EditText) findViewById(R.id.email2);
        EditText champNom = (EditText) findViewById(R.id.name);
        EditText champMdp = (EditText) findViewById(R.id.password2);

        String mail = champMail.getText().toString();
        String nom = champNom.getText().toString();
        String mdp = champMdp.getText().toString();

        AndroidNetworking.post("https://myhouse.lesmoulinsdudev.com/register")
                .addBodyParameter( "login", mail)
                .addBodyParameter( "name" , nom)
                .addBodyParameter( "password", mdp)

                .build()

                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        switch (response.code())
                        {
                            case 200:
                                retour();
                                break;
                            case 400:
                                Toast.makeText(CreationCompte.this, "Erreur dans les informations", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(CreationCompte.this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    public void retour(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}