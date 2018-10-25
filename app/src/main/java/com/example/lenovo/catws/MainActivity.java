package com.example.lenovo.catws;

import android.graphics.drawable.Icon;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void btn_cat(View view) {
        Ion.with(this)
                .load("https://api.thecatapi.com/api/images/get?format=xml&size=med&results_per_page=5")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            GridLayout gridLayout = (GridLayout) findViewById(R.id.gridl);
                            gridLayout.removeAllViews();
                            ImageView imageView;
                            JSONObject jsonObject = XML.toJSONObject(result);
                            Log.v("result", jsonObject.toString());
                            JSONArray jsonArray = jsonObject.getJSONObject("response")
                                    .getJSONObject("data")
                                    .getJSONObject("images")
                                    .getJSONArray("image");
                            Log.v("dd11", jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String url = ((JSONObject) jsonArray.get(i)).getString("url").toString();
                                Log.v("dd2", url);
                                imageView = new ImageView(MainActivity.this);
                                Ion.with(MainActivity.this)
                                        .load(url)
                                        .intoImageView(imageView);
                                gridLayout.addView(imageView);
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }
}
