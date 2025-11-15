package com.example.proteinandwbccalc;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    // objects in activity_main.xml

    private TextInputEditText rbc_input;
    private TextInputEditText protein_input;
    private TextInputEditText wbc_input;

    private TextView calculated_protein;
    private TextView calculated_wbc;

    private Button calculate_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // match the ids to the objects declared above

        rbc_input = (TextInputEditText) findViewById(R.id.rbc_input);
        protein_input = (TextInputEditText) findViewById(R.id.protein_input);
        wbc_input = (TextInputEditText) findViewById(R.id.wbc_input);

        calculated_protein = (TextView) findViewById(R.id.calculated_protein);
        calculated_wbc = (TextView) findViewById(R.id.calculated_wbc);

        calculate_button = (Button) findViewById(R.id.calculate_button);

        // calculate_button listener

        calculate_button.setOnClickListener(new listener());
    }

    class listener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            double rbc = Double.parseDouble(String.valueOf(rbc_input.getText()));

            Editable initial_protein = protein_input.getText();
            double protein = 0;
            if (initial_protein != null) {
                protein = Double.parseDouble(String.valueOf(initial_protein));
            }

            Editable initial_wbc = wbc_input.getText();
            double wbc = 0;
            if (initial_wbc != null) {
                wbc = Double.parseDouble(String.valueOf(initial_wbc));;
            }

            // print protein
            calculated_protein.setText(Double.toString(proteinCalc(rbc, protein)));

            // print white blood cell count
            calculated_wbc.setText(Double.toString(wbcCalc(rbc, wbc)));
        }
    }

    // general formula for calculations

    protected double proteinCalc(double rbc, double protein) {

        double coeff = rbc / (float) 1000;

        return protein - (coeff * 1.1);
    }

    protected double wbcCalc(double rbc, double wbc) {

        double coeff = rbc / (float) 500;

        return wbc - coeff;
    }
}
