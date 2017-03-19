package com.a11.mvvm.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.a11.mvvm.R;
import com.a11.mvvm.model.User;
import com.a11.mvvm.viewmodel.EditViewModel;
import com.a11.mvvm.viewmodel.IViewModel;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    private IViewModel viewModel;
    private ArrayList<User> users = new ArrayList<>();

    private EditText nameET;
    private EditText surnameET;
    private EditText emailET;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        nameET = (EditText) findViewById(R.id.nameET);
        surnameET = (EditText) findViewById(R.id.surnameET);
        emailET = (EditText) findViewById(R.id.emailET);

        viewModel = new EditViewModel(users, getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_key), MODE_PRIVATE));
        position = getIntent().getIntExtra("position", -1);
        if (position != -1) {
            setData(users.get(position));
        }

        findViewById(R.id.saveBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(getName(), getSurname(), getEmail());
                if (position != -1) {
                    users.set(position, user);
                }
                viewModel.notifyDataSetChanged();
                finish();
            }
        });
    }

    public void setData(User user) {
        String name = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();

        nameET.setText(name);
        surnameET.setText(surname);
        emailET.setText(email);
    }

    public String getName() {
        return nameET.getText().toString();
    }

    public String getSurname() {
        return surnameET.getText().toString();
    }

    public String getEmail() {
        return emailET.getText().toString();
    }
}
