package com.a11.mvvm.services;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.a11.mvvm.R;
import com.a11.mvvm.activities.MainActivity;
import com.a11.mvvm.model.JSONModel;
import com.a11.mvvm.model.User;
import com.a11.mvvm.viewmodel.MainViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class LoadService extends IntentService {

    public static final String URL = "https://randomuser.me/api/";
    public static final String NAME = "com.a11.service.services.LoadService";
    public static final String ACTION_LOAD_USER = "com.a11.service.services.LoadService.ACTION_LOAD_USER";

    public LoadService() {
        super(NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        if(ACTION_LOAD_USER.equals(intent.getAction())) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent result = new Intent();
                    PendingIntent pi = intent.getParcelableExtra(MainActivity.PENDING_INTENT_EXTRA);
                    try {
                        User u = getRandomUser();
                        ArrayList<User> users = new ArrayList<>();
                        users.add(u);
                        MainViewModel mvm = new MainViewModel(users, prefs());
                        mvm.notifyDataSetChanged();
                        result = new Intent().putExtra(MainActivity.PARAM_RESULT, MainActivity.RESULT_REFRESH);
                    } catch (Exception e) {
                        Log.e("MyTag", e.toString());
                        result = new Intent().putExtra(MainActivity.PARAM_RESULT, MainActivity.RESULT_ERROR);
                    } finally {
                        try {
                            pi.send(LoadService.this, 0, result);
                        } catch (PendingIntent.CanceledException ce) {
                            Log.e("MVVM", "Error trans data");
                        }
                    }
                }
            }).start();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private SharedPreferences prefs() {
        return getSharedPreferences(getString(R.string.shared_preferences_key), MODE_PRIVATE);
    }

    private User getRandomUser() throws Exception {
        URL url = new URL(URL);
        URLConnection c = url.openConnection();
        c.connect();
        ObjectMapper mapper = new ObjectMapper();
        JSONModel model = mapper.readValue(c.getInputStream(), JSONModel.class);
        if (model.getResults() == 1) {
            User user = JSONModel.castUser(model.getUsers().get(0));
            return user;
        } else {
            throw new Exception();
        }
    }
}







