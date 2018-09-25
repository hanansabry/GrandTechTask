package com.hanan.mstg.grand.grandtechtask.network;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.util.IOUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpPostAsyncTask extends AsyncTask<String, Void, String> {
    private String title;
    private String content;

    @Override
    protected String doInBackground(String... strings) {
        String response = null;
        try {
            URL url = new URL(strings[0]);

            //create the url connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");

            int statusCode = connection.getResponseCode();
            if (statusCode ==  200) {

                InputStream inputStream = new BufferedInputStream(connection.getInputStream());

                response = convertInputStreamToString(inputStream, Charset.forName("utf-8"));
                Log.d("aboutus", response);

                // From here you can convert the string to JSON with whatever JSON parser you like to use
                // After converting the string to JSON, I call my custom callback. You can follow this process too, or you can implement the onPostExecute(Result) method
            } else {
                // Status code is not 200
                // Do something to handle the error
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        Document document = Jsoup.parse(response);
        Element titleElement = document.select("title").get(0);
        Log.d("Element", titleElement.html());
        setTitle(titleElement.html());
        Log.d("ElementContent", getTitle());
        Element contentElement = document.select("p").get(0);
        setContent(contentElement.html());
    }

    public String convertInputStreamToString(InputStream inputStream, Charset charset) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
