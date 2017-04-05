package q8rn.com.q8rn.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.constants.Constants;
import q8rn.com.q8rn.controllers.QuestaoEntrevistadoController;
import q8rn.com.q8rn.entities.Questao;
import q8rn.com.q8rn.model.CriaBanco;
import q8rn.com.q8rn.model.PopulaBanco;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "questao";
    public static final String NÃO_EXISTEM_DADOS = "Não existem dados a serem exportados";

    private Button botaoOnline;
    private Button botaoOffline;

    private PopulaBanco populaBanco;

    private CriaBanco banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populaBanco = new PopulaBanco(getBaseContext());
        populaBanco.popularQuestoes();

        botaoOnline = (Button) findViewById(R.id.botaoOnlineId);
        botaoOffline = (Button) findViewById(R.id.botaoOfflineId);

        this.getSupportActionBar().hide();

        botaoOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        botaoOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestaoEntrevistadoController qe = new QuestaoEntrevistadoController(getBaseContext());

                if (isStoragePermissionGranted()) {

                    try {
                        File file = qe.gerarExcel(getBaseContext());
                        Uri u1;
                        u1 = Uri.fromFile(file);

                        Date dataSemformato = Calendar.getInstance().getTime();

                        SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                        String dataFormatada = spf.format(dataSemformato);

                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Q8RN - " + dataFormatada);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Banco de dados do questionário dos " +
                                "oito remédios naturais gerado em: " + dataFormatada + "\n\n");
                        sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
                        sendIntent.setType("text/html");
                        startActivity(sendIntent);

                    } catch (RuntimeException e) {
                        Log.i(TAG, e.getMessage());
                        Toast.makeText(MainActivity.this, NÃO_EXISTEM_DADOS, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void obtemQuestoesOnline() {

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = Constants.URL_QUESTAO;

        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject json = (JSONObject) response.get(i);
                        Questao questao = new Questao(json);
                        Log.i(TAG, String.valueOf(questao));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(req);
    }
}
