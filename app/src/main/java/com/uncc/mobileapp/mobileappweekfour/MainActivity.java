package com.uncc.mobileapp.mobileappweekfour;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements GetTweetsAsyncTask.IData {

    LinkedList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(isConnected()){
//                    Toast.makeText(MainActivity.this, "Connected",Toast.LENGTH_SHORT).show();
//                    //new GetDataAsync().execute("http://api.theappsdr.com/simple.php");
//                    RequestParams requestParams = new RequestParams();
//                    requestParams.addParameter("name","Bob Smith")
//                            .addParameter("age","24")
//                            .addParameter("email","test@test.com")
//                            .addParameter("password","sakjl233k");
//
//                    //new GetDataParamsUsingGetAsync(requestParams).execute("http://api.theappsdr.com/params.php");
//                    new GetDataParamsUsingPostAsync(requestParams).execute("http://api.theappsdr.com/params.php");
//                } else {
//                    Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
//                }

                new GetTweetsAsyncTask(MainActivity.this).execute("");

            }
        });

    }

    public void handleData(LinkedList<String> data){
        this.data = data;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tweets")
                .setItems(data.toArray(new CharSequence[data.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }

        /*
        Check for wifi sometimes necessary to prompt the user if they want to continue using
        cellular data
        */
        if(networkInfo.getType() != ConnectivityManager.TYPE_WIFI && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE){}


        return false;
    }

    @Override
    public void handleListData(LinkedList<String> data) {
        this.data = data;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tweets")
                .setItems(data.toArray(new CharSequence[data.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private class GetDataAsync extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            String result = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                /*
                Lines below can be replaced with the IOUtils.toString
                However, library could not be loaded...
                Library: org.apache.commons.io:24
                */
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                /*
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    result = IOUtils.toString(connection.getInputStream(), 'UTF8');
                }
                */

                result =  sb.toString();

            } catch(MalformedURLException e){
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                if(connection != null){
                    connection.disconnect();
                }

                if(bufferedReader != null){
                    try {
                        bufferedReader.close();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

            return result;
        }

        @Override
        protected  void onPostExecute(String result){
           if(result != null){
               Log.d("Demo", result);
           } else {
               Log.d("Demo", "NO RESULT!");
           }
        }
    }

    private class GetDataParamsUsingGetAsync extends AsyncTask<String, Void, String>{
        RequestParams mParams;

        public GetDataParamsUsingGetAsync(RequestParams params){
            mParams = params;
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            String result = null;
            try {
                URL url = new URL(mParams.getEncodedURL(params[0]));
                connection = (HttpURLConnection) url.openConnection();

                /*
                Lines below can be replaced with the IOUtils.toString
                However, library could not be loaded...
                Library: org.apache.commons.io:24
                */
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line + "\n");
                }

                /*
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    result = IOUtils.toString(connection.getInputStream(), 'UTF8');
                }
                */

                result =  sb.toString();

            } catch(MalformedURLException e){
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                if(connection != null){
                    connection.disconnect();
                }

                if(bufferedReader != null){
                    try {
                        bufferedReader.close();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

            return result;
        }

        @Override
        protected  void onPostExecute(String result){
            if(result != null){
                Log.d("Demo", result);
            } else {
                Log.d("Demo", "NO RESULT!");
            }
        }
    }

    private class GetDataParamsUsingPostAsync extends AsyncTask<String, Void, String>{
        RequestParams mParams;

        public GetDataParamsUsingPostAsync(RequestParams params){
            mParams = params;
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            String result = null;
            try {
                URL url = new URL(mParams.getEncodedURL(params[0]));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST"); //Required for POST calls...
                mParams.encodePostParams(connection);
                connection.connect();

                /*
                Lines below can be replaced with the IOUtils.toString
                However, library could not be loaded...
                Library: org.apache.commons.io:24
                */
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line + "\n");
                }

                /*
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    result = IOUtils.toString(connection.getInputStream(), 'UTF8');
                }
                */

                result =  sb.toString();

            } catch(MalformedURLException e){
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                if(connection != null){
                    connection.disconnect();
                }

                if(bufferedReader != null){
                    try {
                        bufferedReader.close();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

            return result;
        }

        @Override
        protected  void onPostExecute(String result){
            if(result != null){
                Log.d("Demo", result);
            } else {
                Log.d("Demo", "NO RESULT!");
            }
        }
    }
}
