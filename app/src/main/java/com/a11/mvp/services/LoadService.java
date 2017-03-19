package com.a11.mvp.services;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.a11.mvp.activities.MainActivity;
import com.a11.mvp.model.JSONModel;
import com.a11.mvp.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.net.URLConnection;

public class LoadService extends IntentService {

    public static final String URL = "https://randomuser.me/api/";
    public static final String NAME = "com.a11.mvp.services.LoadService";
    public static final String ACTION_LOAD_USER = "com.a11.mvp.services.LoadService.ACTION_LOAD_USER";

    public LoadService() {
        super(NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        if (ACTION_LOAD_USER.equals(intent.getAction())) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent result = new Intent();
                    PendingIntent pi = intent.getParcelableExtra(MainActivity.PENDING_INTENT_EXTRA);
                    try {
                        URL url = new URL(URL);
                        URLConnection c = url.openConnection();
                        c.connect();
                        ObjectMapper mapper = new ObjectMapper();
                        JSONModel model = mapper.readValue(c.getInputStream(), JSONModel.class);
                        if (model.getResults() == 1) {
                            User user = JSONModel.castUser(model.getUsers().get(0));
                            result = new Intent().putExtra(MainActivity.PARAM_RESULT, MainActivity.RESULT_REFRESH).
                                    putExtra("name", user.getName()).putExtra("surname", user.getSurname()).putExtra("email", user.getEmail());
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        Log.e("MyTag", e.toString());
                        result = new Intent().putExtra(MainActivity.PARAM_RESULT, MainActivity.RESULT_ERROR);
                    } finally {
                        try {
                            pi.send(LoadService.this, 0, result);
                        } catch (PendingIntent.CanceledException ce) {
                            Log.e("MVP", "Error trans data");
                        }
                    }
                }
            }).start();
        }

        return super.onStartCommand(intent, flags, startId);
    }
}



