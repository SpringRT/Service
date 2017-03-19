package com.a11.mvp.presenter;

import android.content.SharedPreferences;

import com.a11.mvp.model.Model;
import com.a11.mvp.model.User;
import com.a11.mvp.view.EditView;
import com.a11.mvp.view.MainView;

import java.util.ArrayList;

public class Presenter {

    private ArrayList<User> data;
    private static Presenter instance = new Presenter();
    private SharedPreferences prefs;
    private Model model;
    private MainView main;

    public static Presenter getInstance(){
        return instance;
    }

    private Presenter() {}

    private void load(){
        createModel(prefs);
        data = model.getData();
    }

    public void createModel(SharedPreferences prefs){
        this.prefs = prefs;
        model = new Model(prefs);
    }

    public void addUser(User user){
        data.add(user);
        model.addUser(user);
        notifyModifiedMain();
    }

    public void replaceUser(int position, User user){
        data.set(position, user);
        model.replaceUser(user, position);
    }

    public void deleteUser(int position){
        data.remove(position);
        model.setData(data);
    }

    public void notifyCreatedMain(MainView main, SharedPreferences prefs){
        this.prefs = prefs;
        if (data == null)
            load();
        this.main = main;
        this.main.setData(data);
    }

    public void notifyModifiedMain(){
        if (main != null){
            main.setData(data);
        }
    }

    public void notifyCreatedEdit(EditView edit, SharedPreferences prefs, int position){
        this.prefs = prefs;
        if (data == null) load();
        edit.setData(data.get(position));
    }

    public void notifyErrorAddingUser(){
        if (main != null){
            main.sendError();
        }
    }
}
