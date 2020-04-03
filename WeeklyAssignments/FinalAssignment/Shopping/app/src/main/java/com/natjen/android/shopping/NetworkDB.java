package com.natjen.android.shopping;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkDB {
    private static final String TAG = "NetworkFetchr";
    public static final String Shopping_URL = "http://realm.itu.dk:8080/?who=nhoj";

    public ArrayList<Item> getItemListFromServer(String ShoppingURL) {
        ArrayList<Item> result = new ArrayList<Item>();
        try {
            String url = Uri.parse(ShoppingURL).buildUpon().build().toString();
            String jsonString = getUrlString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            JSONArray thingsArray = jsonBody.getJSONArray("shopping");
            JSONObject itemJObject;
            for (int i = 0; i < thingsArray.length(); i++) {
                itemJObject = thingsArray.getJSONObject(i);
                result.add(new Item(itemJObject.getString("what"), itemJObject.getString("where")));
            }
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        return result;
    }
    //To get all items from server

    public void send(String ShoppingURL) {
        try {
            String url = Uri.parse(ShoppingURL).buildUpon().build().toString();
            getUrlString(url);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
    }
    //To add and delete items on server

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }
}
