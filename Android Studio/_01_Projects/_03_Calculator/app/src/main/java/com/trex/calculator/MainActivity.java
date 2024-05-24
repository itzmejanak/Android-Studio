package com.trex.calculator;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.trex.calculator.SettingsActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView result, solution;
    MaterialButton clear, open_bracket, close_bracket, ac;
    MaterialButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0, btn_dot;
    MaterialButton divide, multiply, plus, minus, btn_equal;
    ImageView settingIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // TextViews
        result = findViewById(R.id.result);
        solution = findViewById(R.id.solution);

        // Buttons
        assignId(clear, R.id.clear);
        assignId(open_bracket, R.id.open_bracket);
        assignId(close_bracket, R.id.close_bracket);
        assignId(ac, R.id.ac);
        assignId(btn_1, R.id.btn_1);
        assignId(btn_2, R.id.btn_2);
        assignId(btn_3, R.id.btn_3);
        assignId(btn_4, R.id.btn_4);
        assignId(btn_5, R.id.btn_5);
        assignId(btn_6, R.id.btn_6);
        assignId(btn_7, R.id.btn_7);
        assignId(btn_8, R.id.btn_8);
        assignId(btn_9, R.id.btn_9);
        assignId(btn_0, R.id.btn_0);
        assignId(btn_dot, R.id.btn_dot);
        assignId(divide, R.id.divide);
        assignId(multiply, R.id.multiply);
        assignId(plus, R.id.plus);
        assignId(minus, R.id.minus);
        assignId(btn_equal, R.id.btn_equal);
    }

    // Method to open the Settings activity
    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        if (btn != null) {
            btn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String data = solution.getText().toString();

        if (buttonText.equals("AC")) {
            solution.setText("");
            result.setText("");
            return;
        }
        if (buttonText.equals("=")) {
            solution.setText(result.getText());
            return;
        }

        if (buttonText.equals("×")) {
            data += "*"; // Append "*" for multiplication
            solution.setText(data);
            return;
        }

        if (buttonText.equals("⌫")) {
            if (!data.isEmpty()) {
                data = data.substring(0, data.length() - 1);
                solution.setText(data);
                return;
            }else {
                solution.setText("");
                return;
            }

        }else {
            data += buttonText;
        }
        solution.setText(data);

        String finalResult = getResult(data);
        if (!finalResult.equals("Err")) {
            double parsedResult = Double.parseDouble(finalResult);
            DecimalFormat decimalFormat = new DecimalFormat("#.####");
            String formattedResult = decimalFormat.format(parsedResult);
            result.setText(formattedResult);
        }

    }

    String getResult(String data){
        try{
            Context context  = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult =  context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }catch (Exception e){
            return "Err";
        }
    }
}