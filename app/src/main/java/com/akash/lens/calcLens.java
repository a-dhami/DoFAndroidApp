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

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

        setCalculateButton(manager);

        setCancelButton();







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


            if(maxAperture > inSA) {
                txtViewNFD.setText("NaN");
                txtViewFFD.setText("Nan");
                txtViewDOF.setText("NaN");
                txtViewHFD.setText("NaN");
            }
            else{
                double outputVals[] = DOFCalculator.calcDOF(focal,inSA,inDistance,inCOC);
                txtViewNFD.setText(Double.toString(outputVals[0]));
                txtViewFFD.setText(Double.toString(outputVals[1]));
                txtViewDOF.setText(Double.toString(outputVals[2]));
                txtViewHFD.setText(Double.toString(outputVals[3]));
            }
        });
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        int lensNum = intent.getIntExtra(EXTRA_LENSPOSITION,-1);
    }

}
