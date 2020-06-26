package com.example.sptransapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class ResponseActivity extends AppCompatActivity {
    String parameter;
    int typeSearch, way;
    TextView titleSearch, txtStatus;
    RecyclerView recyclerResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#CC0000"));

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        parameter = data.getString("parameter");
        typeSearch = data.getInt("typeSearch");
        way = data.getInt("way");

        titleSearch = findViewById(R.id.titleSearch);
        titleSearch.setText("Resultados da busca '" + parameter + "'");
        txtStatus = findViewById(R.id.txtLoadWarning);

        recyclerResult = findViewById(R.id.recyclerResult);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ResponseActivity.this);
        recyclerResult.setLayoutManager(layoutManager);

        fetchRequest();
    }

    private void fetchRequest() {
        Object[] parameters = {parameter, typeSearch, way};
        FetchAsync fetchAsync = new FetchAsync(parameters, recyclerResult, txtStatus);
        fetchAsync.execute();
    }

    public void startActivity(View view) {
        Intent intent = new Intent(ResponseActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
