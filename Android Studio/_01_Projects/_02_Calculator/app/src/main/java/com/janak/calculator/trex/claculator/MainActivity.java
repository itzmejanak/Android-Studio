package com.janak.calculator.trex.claculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView workingDisplay, resultDisplay;
    double in1 = 0, in2 = 0;
    boolean Add, Sub, Multiply, Divide, Remainder, deci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        workingDisplay = findViewById(R.id.working_display);
        resultDisplay = findViewById(R.id.result_display);

        // Initialize buttons
        Button btnClear = findViewById(R.id.btn_clear);
        Button btnBackspace = findViewById(R.id.btn_backspace);
        Button btnModulus = findViewById(R.id.btn_modulus);
        Button btnSquare = findViewById(R.id.btn_square);
        Button btnSquareRoot = findViewById(R.id.btn_square_root);
        Button btnExponent = findViewById(R.id.btn_exponent);
        Button btnDivide = findViewById(R.id.btn_divide);
        Button btnMultiply = findViewById(R.id.btn_multiply);
        Button btnMinus = findViewById(R.id.btn_minus);
        Button btnSeven = findViewById(R.id.btn_seven);
        Button btnEight = findViewById(R.id.btn_eight);
        Button btnNine = findViewById(R.id.btn_nine);
        Button btnPlus = findViewById(R.id.btn_plus);
        Button btnFour = findViewById(R.id.btn_four);
        Button btnFive = findViewById(R.id.btn_five);
        Button btnSix = findViewById(R.id.btn_six);
        Button btnOne = findViewById(R.id.btn_one);
        Button btnTwo = findViewById(R.id.btn_two);
        Button btnThree = findViewById(R.id.btn_three);
        Button btnEquals = findViewById(R.id.btn_equals);
        Button btnZero = findViewById(R.id.btn_zero);
        Button btnDecimal = findViewById(R.id.btn_decimal);

        // Set onClick listeners
        btnClear.setOnClickListener(this);
        btnBackspace.setOnClickListener(this);
        btnModulus.setOnClickListener(this);
        btnSquare.setOnClickListener(this);
        btnSquareRoot.setOnClickListener(this);
        btnExponent.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnEquals.setOnClickListener(this);
        btnZero.setOnClickListener(this);
        btnDecimal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_clear) {
            clearDisplay();
        } else if (viewId == R.id.btn_backspace) {
            backspace();
        } else if (viewId == R.id.btn_modulus) {
            setOperationFlagAndStoreNumber(true, false, false, false, false);
        } else if (viewId == R.id.btn_square) {
            // Square functionality
            if (!workingDisplay.getText().toString().isEmpty()) {
                double number = Double.parseDouble(workingDisplay.getText().toString());
                resultDisplay.setText(Math.pow(number, 2) + "");
            }
        } else if (viewId == R.id.btn_square_root) {
            // Square root functionality
            if (!workingDisplay.getText().toString().isEmpty()) {
                double number = Double.parseDouble(workingDisplay.getText().toString());
                resultDisplay.setText(Math.sqrt(number) + "");
            }
        } else if (viewId == R.id.btn_exponent) {
            // Exponent functionality
            if (!workingDisplay.getText().toString().isEmpty()) {
                double number = Double.parseDouble(workingDisplay.getText().toString());
                resultDisplay.setText(Math.exp(number) + "");
            }
    } else if (viewId == R.id.btn_divide) {
            setOperationFlagAndStoreNumber(false, false, false, true, false);
        } else if (viewId == R.id.btn_multiply) {
            setOperationFlagAndStoreNumber(false, false, true, false, false);
        } else if (viewId == R.id.btn_minus) {
            setOperationFlagAndStoreNumber(false, true, false, false, false);
        } else if (viewId == R.id.btn_plus) {
            setOperationFlagAndStoreNumber(true, false, false, false, false);
        } else if (viewId == R.id.btn_equals) {
            performOperation();
        } else if (viewId == R.id.btn_zero) {
            appendToDisplay("0");
        } else if (viewId == R.id.btn_decimal) {
            appendDecimal();
        } else {
            appendToDisplay(((Button) v).getText().toString());
        }
    }
    private void clearDisplay() {
        workingDisplay.setText("");
        resultDisplay.setText("");
        in1 = 0;
        in2 = 0;
    }

    private void backspace() {
        String currentText = workingDisplay.getText().toString();
        if (!currentText.isEmpty()) {
            workingDisplay.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    private void setOperationFlagAndStoreNumber(boolean add, boolean sub, boolean multiply, boolean divide, boolean remainder) {
        if (!workingDisplay.getText().toString().isEmpty()) {
            in1 = Double.parseDouble(workingDisplay.getText().toString());
            Add = add;
            Sub = sub;
            Multiply = multiply;
            Divide = divide;
            Remainder = remainder;
            deci = false;
            workingDisplay.setText(null);
        }
    }

    private void performOperation() {
        if (Add || Sub || Multiply || Divide || Remainder) {
            in2 = Double.parseDouble(workingDisplay.getText().toString());
        }
        if (Add) {
            resultDisplay.setText(in1 + in2 + "");
            Add = false;
        } else if (Sub) {
            resultDisplay.setText(in1 - in2 + "");
            Sub = false;
        } else if (Multiply) {
            resultDisplay.setText(in1 * in2 + "");
            Multiply = false;
        } else if (Divide) {
            resultDisplay.setText(in1 / in2 + "");
            Divide = false;
        } else if (Remainder) {
            resultDisplay.setText(in1 % in2 + "");
            Remainder = false;
        }
    }

    private void appendToDisplay(String str) {
        workingDisplay.append(str);
    }

    private void appendDecimal() {
        if (!deci) {
            workingDisplay.append(".");
            deci = true;
        }
    }
}
