package com.a11.mvp.view;

import com.a11.mvp.model.User;

import java.util.ArrayList;

public interface MainView {
    void setData(ArrayList<User> data);
    void sendError();
}
