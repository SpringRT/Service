package com.a11.mvvm.model;


import android.content.SharedPreferences;

import java.util.ArrayList;

public class Model {
    private SharedPreferences prefs;

    public Model(SharedPreferences prefs){
        this.prefs = prefs;
    }

    public ArrayList<User> getData(){
        int size = prefs.getInt("size", 0);
        ArrayList<User> data = new ArrayList<>();
        for (int i = 0; i < size; i++){
            String name = prefs.getString("name" + i, "");
            String surname = prefs.getString("surname" + i, "");
            String email = prefs.getString("email" + i, "");
            data.add(new User(name, surname, email));
        }

        return data;
    }

    public void setData(ArrayList<User> data){
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        for (User user: data) {
            addUser(user);
        }
    }

    private void addUser(User user){
        int size = prefs.getInt("size", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name" + size, user.getName());
        editor.putString("surname" + size, user.getSurname());
        editor.putString("email" + size, user.getEmail());
        editor.putInt("size", size+1);
        editor.apply();
    }
}
