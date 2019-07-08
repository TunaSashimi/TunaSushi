package com.tunasushi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tuna.Tuna;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String str = Tuna.getTuna();
        System.out.println(str);
    }
}
