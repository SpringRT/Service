package com.a11.mvp.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.a11.mvp.R;
import com.a11.mvp.adapter.MyAdapter;
import com.a11.mvp.model.User;
import com.a11.mvp.presenter.Presenter;
import com.a11.mvp.services.LoadService;
import com.a11.mvp.view.MainView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainView {

    public static final String RESULT_REFRESH = "com.a11.mvp.activities.MainActivity.RESULT_REFRESH";
    public static final String RESULT_ERROR = "com.a11.mvp.activities.MainActivity.RESULT_ERROR";
    public static final String PARAM_RESULT = "com.a11.mvp.activities.MainActivity.PARAM_RESULT";
    public static final String PENDING_INTENT_EXTRA = "com.a11.mvp.activities.MainActivity.pi";

    Presenter presenter = Presenter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PendingIntent pi = createPendingResult(0, new Intent(), 0);
                startService(new Intent(getBaseContext(), LoadService.class).
                        setAction(LoadService.ACTION_LOAD_USER).putExtra(PENDING_INTENT_EXTRA, pi));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String result = data.getStringExtra(PARAM_RESULT);
        if (RESULT_REFRESH.equals(result)) {
            Log.e("MyTag", "HERE");
            String name, surname, email;
            name = data.getStringExtra("name");
            surname = data.getStringExtra("surname");
            email = data.getStringExtra("email");

            presenter.addUser(new User(name, surname, email));
        } else if (RESULT_ERROR.equals(result)) {
            Toast.makeText(this, R.string.load_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        presenter.notifyCreatedMain(this, getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_key), MODE_PRIVATE));
    }

    public void setData(ArrayList<User> data){
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(new MyAdapter(data, recycler, this));
    }

    public void onItemClick(int position){
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void onClickDelete(int position){
        presenter.deleteUser(position);
    }

    @Override
    public void sendError(){
        Toast.makeText(this, "Load data error!", Toast.LENGTH_LONG).show();
    }
}

