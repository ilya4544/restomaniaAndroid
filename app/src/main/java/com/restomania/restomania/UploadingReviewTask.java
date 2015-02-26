package com.restomania.restomania;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Freemahn on 18.10.2014.
 */
/**/
public class UploadingReviewTask extends AsyncTask<String, Void, Void> {
    String url = "http://104.131.184.188:8080/restoserver/";

    @Override
    protected Void doInBackground(String... strings) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost http = new HttpPost(url + "vote");
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("token", strings[0]));
        nameValuePairs.add(new BasicNameValuePair("waiterId", strings[1]));
        nameValuePairs.add(new BasicNameValuePair("review", strings[2]));
        nameValuePairs.add(new BasicNameValuePair("rating", strings[3]));

        try {
            http.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(http);
            String line = "";
            StringBuilder total = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while ((line = rd.readLine()) != null) {
                total.append(line);

            }
            Log.e("Responce", total.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("UPLOADING", "Success??");
        return null;
    }


}
