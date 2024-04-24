package com.example.assignment1;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.assignment1.R;

public class MainActivity extends AppCompatActivity {
    EditText editTextListPrice , editTextDiscount, editTextFinalPrice;
    RadioGroup radioGroupSale;
    SeekBar seekBarCustomSale ;
    TextView textViewCurrentSale;
    Button buttonCalculate , buttonReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        editTextListPrice  = findViewById(R.id.TextEnterListPrice);
        editTextDiscount = findViewById(R.id.editTextDiscount);
        editTextFinalPrice = findViewById(R.id.editTextFinalPrice);
        radioGroupSale = findViewById(R.id.radioGroup);
        seekBarCustomSale = findViewById(R.id.seekBar2);
        textViewCurrentSale = findViewById(R.id.CustomText);
        buttonCalculate = findViewById(R.id.buttonCal);
        buttonReset = findViewById(R.id.buttonReset);

      setupSeekBar();
        setupCalculateButton();
        setupResetButton();
        resetFields();




    }
    public void setupSeekBar(){
        seekBarCustomSale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              textViewCurrentSale.setText(progress+"%");
              if(radioGroupSale.getCheckedRadioButtonId() == R.id.radioButtonCustom){
                  if(!editTextListPrice.getText().toString().isEmpty()){
                      calculateDiscount();

                  }
              }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void setupCalculateButton(){
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextListPrice.getText().toString().isEmpty()){
                    editTextDiscount.setText("0.0");
                    editTextFinalPrice.setText("0.0");
                    Toast.makeText(MainActivity.this,"Enter Item Price",Toast.LENGTH_SHORT).show();

                }else {
                    calculateDiscount();
                }
            }
        });
    }

    public void calculateDiscount(){
        float listPrice = Float.parseFloat(editTextListPrice.getText().toString());
        int selectedID = radioGroupSale.getCheckedRadioButtonId();
        float discountRate = getDiscountRate(selectedID);
        float discount = listPrice* discountRate;
        float finalPrice = listPrice-discount;

        editTextDiscount.setText(String.format("%2f",discount));
        editTextFinalPrice.setText(String.format("%2f",finalPrice));
    }
    public float getDiscountRate(int selectedId) {
        if (selectedId == R.id.radioButton10) {
            return 0.10f;
        } else if (selectedId == R.id.radioButton15) {
            return 0.15f;
        } else if (selectedId == R.id.radioButton18) {
            return 0.18f;
        } else if (selectedId == R.id.radioButtonCustom) {
            String currentSaleText = textViewCurrentSale.getText().toString().replace("%", "");
            return Float.parseFloat(currentSaleText) / 100.0f;
        } else {
            return 0.0f;
        }
    }

    private void setupResetButton() {
        buttonReset.setOnClickListener(v -> resetFields());
    }
    private void resetFields() {
        editTextListPrice.setText("");
        editTextDiscount.setText("0.00");
        editTextFinalPrice.setText("0.00");
        radioGroupSale.check(R.id.radioButton10);
        seekBarCustomSale.setProgress(25);
        textViewCurrentSale.setText("25%");
    }
}