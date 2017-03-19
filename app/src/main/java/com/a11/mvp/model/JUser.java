package com.a11.mvp.model;


public class JUser {
    public String gender;
    public Name name;
    public Location location;
    public String email;
    public Login login;
    public String dob;
    public String registered;
    public String phone;
    public String cell;
    public ID id;
    public Picture picture;
    public String nat;


    public class Name {
        public String title;
        public String first;
        public String last;
    }

    public class Location {
        public String street;
        public String city;
        public String state;
        public int postcode;
    }

    public class Login {
        public String username;
        public String password;
        public String salt;
        public String md5;
        public String sha1;
        public String sha256;
    }

    public class ID{
        public String name;
        public String value;
    }

    public class Picture{
        public String large;
        public String medium;
        public String thumbnail;
    }
}
