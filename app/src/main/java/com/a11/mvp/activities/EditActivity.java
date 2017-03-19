package com.a11.mvp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.a11.mvp.R;
import com.a11.mvp.model.User;
import com.a11.mvp.presenter.Presenter;
import com.a11.mvp.view.EditView;

public class EditActivity extends AppCompatActivity implements EditView {

    Presenter presenter = Presenter.getInstance();

    private EditText nameET;
    private EditText surnameET;
    private EditText emailET;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        nameET = (EditText) findViewById(R.id.nameET);
        surnameET = (EditText) findViewById(R.id.surnameET);
        emailET = (EditText) findViewById(R.id.emailET);
        position = getIntent().getIntExtra("position", -1);

        findViewById(R.id.saveBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(getName(), getSurname(), getEmail());
                presenter.replaceUser(position, user);
                finish();
            }
        });

       if (position != -1)
            presenter.notifyCreatedEdit(this, getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_key),MODE_PRIVATE), position);
    }

    @Override
    public void setData(User user){
        String name = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();

        nameET.setText(name);
        surnameET.setText(surname);
        emailET.setText(email);
    }

    public String getName(){
        return nameET.getText().toString();
    }

    public String getSurname(){
        return surnameET.getText().toString();
    }

    public String getEmail(){
        return emailET.getText().toString();
    }

}
