package com.bracq.projet_android;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Manager {

    public static final String TOKEN = "com.bracq.projet_android.TOKEN";

    public ArrayList<Image> list(String type) {
        ArrayList<Image> imageList = new ArrayList<>();

        AndroidNetworking.get("https://myhouse.lesmoulinsdudev.com/pictures")
                .addPathParameter("type", type)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray images = response.getJSONArray("pictures");

                            for (int i = 0; i < images.length(); ++i) {
                                // On récupère les données
                                final JSONObject image = images.getJSONObject(i);
                                // On ajoute les données à la liste
                                imageList.add(new Image(
                                        image.getInt("id"),
                                        image.getString("url")
                                ));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
        return imageList;
    }
}
