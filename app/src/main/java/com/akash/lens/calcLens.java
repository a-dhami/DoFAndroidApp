package com.akash.lens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class calcLens extends AppCompatActivity {

    public static Intent makeLaunchIntent(Context c) {
        Intent intent = new Intent(c, calcLens.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_lens);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button cancel = findViewById(R.id.button_Cancel);
        cancel.setOnClickListener(v -> finish());

        Button calculate = findViewById(R.id.button_Calculate);
        calculate.setOnClickListener(v -> {
            EditText COCValue = findViewById(R.id.editText_COC);
            EditText DOSValue = findViewById(R.id.editText_DOS);
            EditText SAValue = findViewById(R.id.editText_SAValue);
            COCValue.getText();
            DOSValue.getText();
            SAValue.getText();

        });

    }

}
