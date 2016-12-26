package com.ilp.tcs.sitesafety.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.listeners.JsonListener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * Created by 1241575 on 6/17/2016.
 * JsonTask which fetches json from server.
 */
public class JsonTask extends AsyncTask<URL, Void, String> {

    /**
     * urlConnection to create connection with server.
     */
    private HttpURLConnection urlConnection;
    /**
     * Dialog to show the progress of json fetching from server.
     */
    private ProgressDialog mDialog;
    /**
     * Reference of json listener to handle callbacks.
     */
    private JsonListener mListener;

    public JsonTask(Context context, JsonListener listener) {
        mDialog = new ProgressDialog(context);
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog.setTitle(R.string.app_name);
        mDialog.setMessage("Validating...");
        mDialog.show();
    }

    @Override
    protected String doInBackground(URL... params) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = params[0];
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.26.49.43", 8080));
            urlConnection = (HttpURLConnection) url.openConnection(proxy);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(20 * 1000);
            urlConnection.setReadTimeout(20 * 1000);

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mDialog.dismiss();
        mListener.onTaskComplete(s);
    }
}