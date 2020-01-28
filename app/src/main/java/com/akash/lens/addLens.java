package com.akash.lens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.akash.lens.model.Lens;
import com.akash.lens.model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addLens extends AppCompatActivity {

    public static Intent makeLaunchIntent(Context c) {
        Intent intent = new Intent(c, addLens.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lens);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LensManager manager = LensManager.getInstance();

        Button cancel = findViewById(R.id.button_Cancel);
        cancel.setOnClickListener(v -> finish());

        Button save = findViewById(R.id.button_Save);
        save.setOnClickListener(v -> {
            EditText inMakeValue = findViewById(R.id.editText_Make);
            EditText inFocalValue = findViewById(R.id.editText_FocalLength);
            EditText inApertureValue = findViewById(R.id.editText_Aperture);

            String make = (String) inMakeValue.getText().toString();
            double aperture = Double.parseDouble(inApertureValue.getText().toString());
            int focal = Integer.parseInt(inFocalValue.getText().toString());
            manager.add(new Lens(make,aperture,focal));

            finish();


                });
            }
        }


