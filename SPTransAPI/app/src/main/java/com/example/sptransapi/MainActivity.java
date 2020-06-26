package com.example.sptransapi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText parameterSearch;
    Spinner typeSearch;
    Button btnInitFetch;
    ArrayList<String> listTypeSearch = new ArrayList<>();
    RadioGroup radioWay;
    int codItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#CC0000"));

        parameterSearch = findViewById(R.id.txtParameterSearch);
        typeSearch = findViewById(R.id.typeSearch);
        btnInitFetch = findViewById(R.id.btnInitFetch);
        radioWay = findViewById(R.id.radioWay);

        listTypeSearch.add("Buscar linha");
        listTypeSearch.add("Buscar linha com sentido");
        listTypeSearch.add("Parada de ônibus");
        listTypeSearch.add("Paradas de uma linha");
        listTypeSearch.add("Posição dos ônibus");
        listTypeSearch.add("Previsão de chegada por linha");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listTypeSearch);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSearch.setAdapter(adapter);

        typeSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                codItem = position;
                radioWay.setVisibility(View.INVISIBLE);

                if(position == 3 || position == 4 || position == 5) {
                    parameterSearch.setInputType(InputType.TYPE_CLASS_NUMBER);
                    parameterSearch.setHint("Insira o código da linha");
                } else {
                    parameterSearch.setInputType(InputType.TYPE_CLASS_TEXT);
                    if(position == 0 || position == 1) parameterSearch.setHint("Insira o número ou nome da linha");
                    else if(position == 2) parameterSearch.setHint("Insira o endereço da parada");
                }
                if(position == 1) radioWay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    public void resultScreen(View view) {
        String parameter = parameterSearch.getText().toString().trim();
        if(parameter.length() > 0) {
            Bundle bundle =  new Bundle();
            bundle.putString("parameter", parameter);
            bundle.putInt("typeSearch", codItem);
            if(codItem == 1) bundle.putInt("way", radioWay.getCheckedRadioButtonId());

            Intent intent = new Intent(MainActivity.this, ResponseActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else Toast.makeText(MainActivity.this, "Insira uma valor na caixa de texto!", Toast.LENGTH_LONG).show();
    }
}
