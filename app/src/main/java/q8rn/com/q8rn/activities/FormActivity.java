package q8rn.com.q8rn.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.constants.Constants;
import q8rn.com.q8rn.to.Entrevistado;

public class FormActivity extends AppCompatActivity {

    private EditText iniciaisNome;
    private EditText idade;
    private RadioGroup radioGroupSexo;
    private Spinner corPeleSpinner;
    private EditText religiao;
    private EditText tempoReligiao;
    private EditText profissao;
    private Spinner escolaridadeSpinner;
    private EditText peso;
    private EditText altura;
    private EditText imc;
    private EditText cinturaQuadril;
    private EditText pressaoArterial;
    private EditText glicemiaCapilar;
    private EditText espirometria;
    private Spinner saudeFisicaSpinner;
    private Spinner saudeMentalSpinner;
    private EditText doencas;

    private Button botaoProximo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        instanciaElementosTela();
        populaTodosSpinners();

        radioGroupSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.masculinoId) {
                    Toast.makeText(FormActivity.this, "Masculino", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FormActivity.this, "Feminino", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarEntrevistadoEProximaActivity();
            }
        });
    }

    private void salvarEntrevistadoEProximaActivity() {
        final Entrevistado entrevistado = instanciaEntrevistado();

        RequestQueue queue = Volley.newRequestQueue(FormActivity.this);
        String url = Constants.URL_ENTREVISTADO;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("json", response);
                        Gson jsonRetorno = new Gson();
                        Entrevistado entrevistadoRetorno = jsonRetorno.fromJson(response, Entrevistado.class);
                        Intent intent = new Intent(FormActivity.this, QuestionarioActivity.class);
                        intent.putExtra("idEntrevistado", entrevistadoRetorno.getId());
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("json", error.toString());
                Toast.makeText(FormActivity.this, "[ERRO] Tente novamente.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String json = gson.toJson(entrevistado);
                Log.i("json",json);
                return json.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(stringRequest);
        queue.start();
    }

    @NonNull
    private Entrevistado instanciaEntrevistado() {
        return new Entrevistado(iniciaisNome.getText().toString(),
                Integer.parseInt(idade.getText().toString()), "Masculino",
                corPeleSpinner.getSelectedItem().toString(),
                religiao.getText().toString(), tempoReligiao.getText().toString(),
                profissao.getText().toString(),
                escolaridadeSpinner.getSelectedItem().toString(),
                Double.parseDouble(peso.getText().toString()),
                Double.parseDouble(altura.getText().toString()),
                Double.parseDouble(imc.getText().toString()),
                Double.parseDouble(cinturaQuadril.getText().toString()),
                Double.parseDouble(pressaoArterial.getText().toString()),
                Double.parseDouble(glicemiaCapilar.getText().toString()),
                Integer.parseInt(espirometria.getText().toString()),
                saudeFisicaSpinner.getSelectedItem().toString(),
                saudeMentalSpinner.getSelectedItem().toString(),
                doencas.getText().toString());
    }

    private void populaTodosSpinners() {
        populaSpinner(R.array.corpele_array, corPeleSpinner);
        populaSpinner(R.array.escolaridade_array, escolaridadeSpinner);
        populaSpinner(R.array.saude_array, saudeFisicaSpinner);
        populaSpinner(R.array.saude_array, saudeMentalSpinner);
    }

    private void populaSpinner(int array, Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private void instanciaElementosTela() {
        iniciaisNome = (EditText) findViewById(R.id.iniciaisNomeId);
        idade = (EditText) findViewById(R.id.idadeId);
        religiao = (EditText) findViewById(R.id.religiaoId);
        tempoReligiao = (EditText) findViewById(R.id.tempoReligiaoId);
        profissao = (EditText) findViewById(R.id.profissaoId);
        peso = (EditText) findViewById(R.id.pesoId);
        altura = (EditText) findViewById(R.id.alturaId);
        imc = (EditText) findViewById(R.id.imcId);
        cinturaQuadril = (EditText) findViewById(R.id.cinturaQuadrilId);
        pressaoArterial = (EditText) findViewById(R.id.pressaoId);
        glicemiaCapilar = (EditText) findViewById(R.id.glicemiaId);
        espirometria = (EditText) findViewById(R.id.espirometriaId);
        doencas = (EditText) findViewById(R.id.doencasId);

        radioGroupSexo = (RadioGroup) findViewById(R.id.radioGroupSexoId);

        corPeleSpinner = (Spinner) findViewById(R.id.corPeleIdSpinner);
        escolaridadeSpinner = (Spinner) findViewById(R.id.escolaridadeIdSpinner);
        saudeFisicaSpinner = (Spinner) findViewById(R.id.saudeFisicaIdSpinner);
        saudeMentalSpinner = (Spinner) findViewById(R.id.saudeMentalIdSpinner);

        botaoProximo = (Button) findViewById(R.id.botaoProximoId);
    }

    private void loadData() {

    }
}
