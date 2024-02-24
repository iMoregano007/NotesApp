package com.im_oregano007.notesapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    SignInFragment signInFragment;
    FrameLayout frameLayout;
    NotesFragment notesFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInFragment = new SignInFragment();
        notesFragment = new NotesFragment();
        frameLayout = findViewById(R.id.frameLayout);

        if(isLoggedIn()){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,notesFragment).commit();
        } else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,signInFragment).commit();
        }

    }

    boolean isLoggedIn(){
        SharedPreferences preferences = getSharedPreferences("user_pref",MODE_PRIVATE);
        return preferences.getBoolean("isLoggedIn",false);
    }
}