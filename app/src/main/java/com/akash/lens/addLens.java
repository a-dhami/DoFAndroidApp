package com.akash.lens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.akash.lens.model.Lens;
import com.akash.lens.model.LensManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addLens extends AppCompatActivity {

    private static final String EXTRA_LENSPOSITION = "com.akash.lens.addLens - position";
    private static final String EXTRA_EDITBOOL = "com.akash.len.addLens - editmode boolean";

    private int lensNum;
    private boolean editMode;

    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, addLens.class);
    }

    public static Intent makeLaunchIntent(Context c, int position) {
        Intent intent = new Intent(c, addLens.class);
        intent.putExtra(EXTRA_LENSPOSITION,position);
        intent.putExtra(EXTRA_EDITBOOL,true);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lens);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LensManager manager = LensManager.getInstance();

        extractDataFromIntent();

        setEditMode(manager, editMode);

        setCancelButton();

        setSaveButton(manager);
    }

    private void setEditMode(LensManager manager, boolean editMode) {
        if(editMode)
        {
            EditText inMakeValue = findViewById(R.id.editText_Make);
            EditText inFocalValue = findViewById(R.id.editText_FocalLength);
            EditText inApertureValue = findViewById(R.id.editText_Aperture);

            String make = manager.get(lensNum).getMake();
            int focal = manager.get(lensNum).getFocalLength();
            double aperture = manager.get(lensNum).getMaxAperture();

            inMakeValue.setText(make);
            inFocalValue.setText(String.valueOf(focal));
            inApertureValue.setText(String.valueOf(aperture));
        }

    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        lensNum = intent.getIntExtra(EXTRA_LENSPOSITION,-1);
        editMode = intent.getBooleanExtra(EXTRA_EDITBOOL,false);

    }


    private void setSaveButton(LensManager manager) {
            Button save = findViewById(R.id.button_Save);
            save.setOnClickListener(v -> {
                EditText inMakeValue = findViewById(R.id.editText_Make);
                EditText inFocalValue = findViewById(R.id.editText_FocalLength);
                EditText inApertureValue = findViewById(R.id.editText_Aperture);

                String make = (String) inMakeValue.getText().toString();
                double aperture = Double.parseDouble(inApertureValue.getText().toString());
                int focal = Integer.parseInt(inFocalValue.getText().toString());

                if(make.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), R.string.txt_EmptyMake, Toast.LENGTH_LONG).show();
                }
                else if (inFocalValue.getText().toString().isEmpty() || focal == 0) {
                    Toast.makeText(getApplicationContext(), R.string.txt_FocalError, Toast.LENGTH_LONG).show();
                }
                else if (inApertureValue.getText().toString().isEmpty() || aperture < 1.4) {
                    Toast.makeText(getApplicationContext(), R.string.txt_ApertureError, Toast.LENGTH_LONG).show();
                }
                else if(!editMode)
                {
                    manager.add(new Lens(make,aperture,focal));
                }
                else {
                    manager.set(lensNum, make, aperture, focal);
                }


                finish();
            });
        }

    private void setCancelButton() {
        Button cancel = findViewById(R.id.button_Cancel);
        cancel.setOnClickListener(v -> finish());
    }


}


