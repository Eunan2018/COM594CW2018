package com.eunan.tracey.com594cw2018;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetDataTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetDataTask";
    private final OnDownloadComplete mCallback;
    public static String error = "error";

    interface OnDownloadComplete {
        void onDownloadComplete(String data);
    }

    public GetDataTask(OnDownloadComplete callback) {
        this.mCallback = callback;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d(TAG, "onPostExecute: parameter = " + s);
        if (s != null) {
            if (mCallback != null) {
                mCallback.onDownloadComplete(s);
            }
        }
        Log.d(TAG, "onPostExecute: ends");
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: starts" + strings);

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer();
                String tmp = "";
                while ((tmp = reader.readLine()) != null)
                    json.append(tmp).append("\n");
                reader.close();

                return json.toString();
            } else {

                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


