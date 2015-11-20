package com.example.daniel.hospitalapp;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Daniel on 19/11/2015.
 */
public class ParseInit extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "WNQj3BBKaR68gtueAkSfzHylUWCkF6wPa3APX8CC", "5TxZXEIMKClhclUgRnOdgipaiq0KCQ9ECcitOJAz");
    }
}
