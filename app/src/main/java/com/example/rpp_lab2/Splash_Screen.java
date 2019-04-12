package com.example.rpp_lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.rpp_sem4_lab2.R;

import org.json.JSONArray;
import org.json.JSONException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Splash_Screen extends AppCompatActivity {
    private LoadDataTask loadTask;
    private String url = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/data/techs.ruleset.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        loadTask = new LoadDataTask(this);
        loadTask.execute(this.url);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadTask.cancel(true);
    }

    public void onEnd() {
        Intent i = new Intent(Splash_Screen.this, Game_Technologies.class);
        startActivity(i);
        finish();
    }

    private static class LoadDataTask extends AsyncTask<String, Void, String> {
        final Splash_Screen listener;
        OkHttpClient client = new OkHttpClient();

        LoadDataTask(Splash_Screen listener) {
            super();
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... params) {
            Request.Builder builder = new Request.Builder();
            builder.url(String.valueOf(params[0]));
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            Data_Holder dataHold = Data_Holder.getInstance();

            JSONArray json = null;

            try {
                json = new JSONArray(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            dataHold.setData(json);

            if (listener != null)
                listener.onEnd();
        }
    }
}
