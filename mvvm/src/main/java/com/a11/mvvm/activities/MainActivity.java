package com.a11.mvvm.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.a11.mvvm.R;
import com.a11.mvvm.adapter.MyAdapter;
import com.a11.mvvm.model.User;
import com.a11.mvvm.services.LoadService;
import com.a11.mvvm.viewmodel.IViewModel;
import com.a11.mvvm.viewmodel.MainViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String RESULT_REFRESH = "com.a11.mvvm.activities.MainActivity.RESULT_REFRESH";
    public static final String RESULT_ERROR = "com.a11.mvvm.activities.MainActivity.RESULT_ERROR";
    public static final String PARAM_RESULT = "com.a11.mvvm.activities.MainActivity.PARAM_RESULT";
    public static final String PENDING_INTENT_EXTRA = "com.a11.mvvm.activities.MainActivity..pi";

    private IViewModel viewModel;
    private ArrayList<User> users;

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
        if(RESULT_REFRESH.equals(result)) {
            onResume();
        }
        else if(RESULT_ERROR.equals(result)){
            Toast.makeText(this, R.string.load_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        users = new ArrayList<>();
        viewModel = new MainViewModel(users, getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_key), MODE_PRIVATE));
        setData();
    }

    private void setData() {
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(new MyAdapter(users, recycler, this));
    }

    public void onItemClick(int position){
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void onClickDelete(int position){
        users.remove(position);
        viewModel.notifyDataSetChanged();
    }
}
