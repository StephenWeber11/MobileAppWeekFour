package com.uncc.mobileapp.mobileappweekfour;

import android.os.AsyncTask;

import java.util.LinkedList;

/**
 * Created by Stephen Weber on 2/11/2018.
 */

public class GetTweetsAsyncTask extends AsyncTask<String, Void, LinkedList<String>> {

    private IData iData;

    public GetTweetsAsyncTask(IData iData) {
        this.iData = iData;
    }

    @Override
    protected LinkedList<String> doInBackground(String... strings) {
        LinkedList<String> tweets = new LinkedList<>();
        for(int i = 0; i < 10; i++){
            tweets.add("Tweet " + i);
        }
        return tweets;
    }

    @Override
    protected void onPostExecute(LinkedList<String> strings) {
        iData.handleListData(strings);
    }

    public static interface IData{
        public void handleListData(LinkedList<String> data);
    }
}
