package com.akash.lens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.akash.lens.model.LensManager;
import com.akash.lens.model.DOFCalculator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class calcLens extends AppCompatActivity {

    private static final String EXTRA_LENSPOSITION = "com.akash.lens.calcLens - position";

    private int lensNum;

    public static Intent makeLaunchIntent(Context c, int position) {
        Intent intent = new Intent(c, calcLens.class);
        intent.putExtra(EXTRA_LENSPOSITION,position);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_lens);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LensManager manager = LensManager.getInstance();

        extractDataFromIntent();

        setLensInfo(manager);

        setCalculateButton(manager);

        setCancelButton();

    }

    private void setLensInfo(LensManager manager) {
        TextView lensView = findViewById(R.id.textView_lensinfo);
        lensView.setText(manager.get(lensNum).toString());
    }

    private void setCancelButton() {
        Button cancel = findViewById(R.id.button_Cancel);
        cancel.setOnClickListener(v -> finish());
    }

    private void setCalculateButton(LensManager manager) {
        Button calculate = findViewById(R.id.button_Calculate);
        calculate.setOnClickListener(v -> {


            EditText editCOCValue = findViewById(R.id.editText_COC);
            EditText editDOSValue = findViewById(R.id.editText_DOS);
            EditText editSAValue = findViewById(R.id.editText_SAValue);

            TextView txtViewNFD = findViewById(R.id.textView_NFDValue);
            TextView txtViewFFD = findViewById(R.id.textView_FFDValue);
            TextView txtViewDOF = findViewById(R.id.textView_DoFValue);
            TextView txtViewHFD = findViewById(R.id.textView_HFDValue);

            double inCOC = Double.parseDouble(editCOCValue.getText().toString());
            double inDistance = Double.parseDouble(editDOSValue.getText().toString());
            double inSA = Double.parseDouble(editSAValue.getText().toString());

            double focal = manager.get(lensNum).getFocalLength();
            double maxAperture = manager.get(lensNum).getMaxAperture();


            if(inCOC == 0)
            {
                txtViewNFD.setText("NaN");
                txtViewFFD.setText("NaN");
                txtViewDOF.setText("NaN");
                txtViewHFD.setText("NaN");
            }
            else if(maxAperture > inSA) {
                txtViewNFD.setText("Invalid Aperture");
                txtViewFFD.setText("Invalid Aperture");
                txtViewDOF.setText("Invalid Aperture");
                txtViewHFD.setText("Invalid Aperture");
            }
            else{
                double outputVals[] = DOFCalculator.calcDOF(focal,inSA,inDistance,inCOC);
                txtViewNFD.setText(formatM(outputVals[0]) + "m");
                txtViewFFD.setText(formatM(outputVals[1]) + "m");
                txtViewDOF.setText(formatM(outputVals[2]) + "m");
                txtViewHFD.setText(formatM(outputVals[3]) + "m");
            }
        });
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        lensNum = intent.getIntExtra(EXTRA_LENSPOSITION,-1);
    }

    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        distanceInM = distanceInM / 1000;
        return df.format(distanceInM);
    }
}
