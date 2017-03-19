package com.a11.mvvm.viewmodel;

import android.content.SharedPreferences;

import com.a11.mvvm.model.Model;
import com.a11.mvvm.model.User;

import java.util.ArrayList;

public class MainViewModel implements IViewModel{
    private Model model;
    private ArrayList<User> users;

    public MainViewModel(ArrayList<User> users, SharedPreferences prefs) {
        this.users = users;
        model = new Model(prefs);
        load();
    }

    private void load(){
        users.addAll(model.getData());
    }

    private void save(){
        model.setData(users);
    }

    @Override
    public void notifyDataSetChanged() {
        save();
    }
}
