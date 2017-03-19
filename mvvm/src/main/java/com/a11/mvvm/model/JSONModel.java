package com.a11.mvvm.model;

import java.util.ArrayList;

public class JSONModel {
    public ArrayList<JUser> results;
    public Info info;

    public static User castUser(JUser jUser) {
        return new User(jUser.name.first,
                jUser.name.last, jUser.email);
    }

    public int getResults() {
        return info.results;
    }

    public ArrayList<JUser> getUsers() {
        return results;
    }

    public class Info {
        public String seed;
        public int results;
        public int page;
        public String version;
    }
}
