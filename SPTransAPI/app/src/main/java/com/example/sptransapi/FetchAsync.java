package com.example.sptransapi;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FetchAsync extends AsyncTask {
    String parameter, url, cookie = "", urlApi = "http://api.olhovivo.sptrans.com.br/v2.1";
    int typeSearch, way;
    RecyclerView recyclerResult;
    TextView txtStatus;

    FetchAsync(Object[] parameters, RecyclerView recyclerResult, TextView txtStatus) {
        this.parameter = (String) parameters[0];
        this.typeSearch = (int) parameters[1];
        this.way = (int) parameters[2];
        this.recyclerResult = recyclerResult;
        this.txtStatus = txtStatus;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        switch (typeSearch) {
            case 0: url = urlApi + "/Linha/Buscar?termosBusca=" + parameter; break;
            case 1: url = urlApi + "/Linha/BuscarLinhaSentido?termosBusca=" + parameter + "&sentido=" + way; break;
            case 2: url = urlApi + "/Parada/Buscar?termosBusca=" + parameter; break;
            case 3: url = urlApi + "/Parada/BuscarParadasPorLinha?codigoLinha=" + parameter; break;
            case 4: url = urlApi + "/Posicao/Linha?codigoLinha=" + parameter; break;
            case 5: url = urlApi + "/Previsao/Linha?codigoLinha=" + parameter; break;
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String urlAuth = urlApi + "/Login/Autenticar?token=472612875691baad9829131c018c5320494aa0cc7f7180c4eeb26318042f6d00";
        cookie = JsonHandler.getJson(urlAuth, "POST", "");
        if(typeSearch == 4) {
            ArrayList<DtoLocation> arrayList = new ArrayList<>();
            try {
                JSONObject json = new JSONObject(JsonHandler.getJson(url, "GET", cookie));
                JSONArray jsonArray = json.getJSONArray("vs");

                for (int i = 0; i < jsonArray.length(); i++) {
                    DtoLocation dtoLocation = new DtoLocation();
                    String[] xy = {jsonArray.getJSONObject(i).getString("py"), jsonArray.getJSONObject(i).getString("px")};
                    String url = "https://geocode.xyz/" + xy[0] + "," + xy[1] + "?geoit=json&auth=360711984172405571725x6406";
                    String location = JsonHandler.getJson(url, "GET", "");
                    JSONObject locationJson = new JSONObject(location);
                    dtoLocation.setPrefix(jsonArray.getJSONObject(i).getString("p"));
                    dtoLocation.setHour(jsonArray.getJSONObject(i).getString("ta"));
                    dtoLocation.setAddress(locationJson.getString("staddress") + ", " + locationJson.getString("stnumber"));
                    dtoLocation.setAccessibility(jsonArray.getJSONObject(i).getString("a"));
                    arrayList.add(dtoLocation);
                }
                LocationAdapter locationAdapter = new LocationAdapter(arrayList);
                return locationAdapter;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(typeSearch == 5) {
            ArrayList<DtoForecast> arrayList = new ArrayList<>();
            try {
                JSONObject json = new JSONObject(JsonHandler.getJson(url, "GET", cookie));
                JSONArray jsonArray = json.getJSONArray("ps");
                Log.d("6627_position", jsonArray.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    DtoForecast dtoForecast = new DtoForecast();
                    JSONObject forecast = jsonArray.getJSONObject(i);
                    JSONArray busInfo = forecast.getJSONArray("vs");
                    if(busInfo.length() == 0) continue;

                    String[] xy = {forecast.getString("py"), forecast.getString("px")};
                    String url = "https://geocode.xyz/" + xy[0] + "," + xy[1] + "?geoit=json&auth=360711984172405571725x6406";
                    String location = JsonHandler.getJson(url, "GET", "");
                    JSONObject locationJson = new JSONObject(location);
                    dtoForecast.setBusCode(busInfo.getJSONObject(0).getString("p"));
                    dtoForecast.setForecastTime(busInfo.getJSONObject(0).getString("t"));
                    dtoForecast.setStopAddress(locationJson.getString("staddress"));
                    dtoForecast.setStopCode(forecast.getString("cp"));
                    dtoForecast.setStopName(forecast.getString("np"));
                    arrayList.add(dtoForecast);
                }
                ForcastAdapter forcastAdapter = new ForcastAdapter(arrayList);
                return forcastAdapter;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return JsonHandler.getJson(url, "GET", cookie);
    }

    @Override
    protected void onPostExecute(Object response) {
        super.onPostExecute(response);

        if(typeSearch == 4 || typeSearch == 5) {
            RecyclerView.Adapter adapter = (RecyclerView.Adapter) response;
            if(adapter.getItemCount() == 0) {
                txtStatus.setText("Nenhum resultado encontrado!");
                txtStatus.setTextColor(Color.parseColor("#000000"));
            } else {
                txtStatus.setVisibility(View.INVISIBLE);
                recyclerResult.setAdapter(adapter);
            }
        } else {
            try {
                JSONArray jsonArray = new JSONArray((String) response);
                if(jsonArray.length() == 0) {
                    txtStatus.setText("Nenhum resultado encontrado!");
                    txtStatus.setTextColor(Color.parseColor("#000000"));
                } else {
                    txtStatus.setVisibility(View.INVISIBLE);
                    if(typeSearch == 0 || typeSearch == 1) {
                        ArrayList<DtoBus> arrayList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            DtoBus dtoBus = new DtoBus();
                            String tl = jsonArray.getJSONObject(i).getString("sl");
                            dtoBus.setCodLine(jsonArray.getJSONObject(i).getString("cl"));
                            dtoBus.setNumber(jsonArray.getJSONObject(i).getString("lt"));
                            dtoBus.setStopStart(jsonArray.getJSONObject(i).getString("tp"));
                            dtoBus.setStopEnd(jsonArray.getJSONObject(i).getString("ts"));
                            dtoBus.setBase(jsonArray.getJSONObject(i).getString("tl"));
                            dtoBus.setWay(tl.equals("1") ? "Ida" : "Volta");
                            arrayList.add(dtoBus);
                        }
                        BusAdapter busAdapter = new BusAdapter(arrayList);
                        recyclerResult.setAdapter(busAdapter);
                    } else if (typeSearch == 2 || typeSearch == 3) {
                        ArrayList<DtoStop> arrayList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            DtoStop dtoStop = new DtoStop();
                            dtoStop.setStopName(jsonArray.getJSONObject(i).getString("np"));
                            dtoStop.setAddressName(jsonArray.getJSONObject(i).getString("ed"));
                            dtoStop.setCodStop("Código: " + jsonArray.getJSONObject(i).getString("cp"));
                            arrayList.add(dtoStop);
                        }
                        StopAdapter stopAdapter = new StopAdapter(arrayList);
                        recyclerResult.setAdapter(stopAdapter);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}