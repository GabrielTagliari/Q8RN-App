package q8rn.com.q8rn.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.constants.Constants;
import q8rn.com.q8rn.to.Entrevistado;
import q8rn.com.q8rn.validators.EntrevistadoValidator;

public class FormActivity extends AppCompatActivity {

    private EditText iniciaisNome;
    private EditText idade;
    private RadioGroup radioGroupSexo;
    private RadioButton femininoRadio;
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

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        instanciaElementosTela();
        populaTodosSpinners();

        clearRadioErrorOnChange();

        botaoProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<EditText> listaEditText = montaListaEditText();
                List<Spinner> listaSpinners = montaListaSpinners();

                EntrevistadoValidator validator = new EntrevistadoValidator();

                boolean permiteSalvar = validator.validate(listaEditText, radioGroupSexo,
                        femininoRadio, listaSpinners);

                if (permiteSalvar) {
                    Entrevistado entrevistado = instanciaEntrevistado();
                    salvarEntrevistadoEProximaActivity(entrevistado);
                } else {
                    Toast.makeText(FormActivity.this,
                            Constants.PREENCHA_CAMPOS, Toast.LENGTH_SHORT).show();
                }

                /*progressDialog.show(FormActivity.this, "", "Carregando", false);
                Intent intent = new Intent(FormActivity.this, QuestionarioActivity.class);
                startActivity(intent);*/
            }
        });
    }

    private void clearRadioErrorOnChange() {
        radioGroupSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                femininoRadio.setError(null);
            }
        });
    }

    private List<Spinner> montaListaSpinners() {
        List<Spinner> lista = new ArrayList<>();
        lista.add(corPeleSpinner);
        lista.add(escolaridadeSpinner);
        lista.add(saudeFisicaSpinner);
        lista.add(saudeMentalSpinner);
        return lista;
    }

    private List<EditText> montaListaEditText() {
        List<EditText> lista = new ArrayList<>();
        lista.add(iniciaisNome);
        lista.add(idade);
        lista.add(religiao);
        lista.add(tempoReligiao);
        lista.add(profissao);
        lista.add(peso);
        lista.add(altura);
        lista.add(imc);
        lista.add(cinturaQuadril);
        lista.add(pressaoArterial);
        lista.add(glicemiaCapilar);
        lista.add(espirometria);
        lista.add(doencas);
        return lista;
    }

    private void salvarEntrevistadoEProximaActivity(final Entrevistado entrevistado) {

        RequestQueue queue = Volley.newRequestQueue(FormActivity.this);
        String url = Constants.URL_ENTREVISTADO;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(Constants.JSON, response);

                        Gson jsonRetorno = new Gson();
                        Entrevistado entrevistadoRetorno = jsonRetorno.fromJson(response, Entrevistado.class);

                        salvarIdSharedPreferences(entrevistadoRetorno.getId());

                        progressDialog.dismiss();

                        Intent intent = new Intent(FormActivity.this, QuestionarioActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("json", error.toString());
                if (error instanceof TimeoutError) {
                    Toast.makeText(FormActivity.this,
                            Constants.FALHA_AO_SALVAR_DADOS, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(FormActivity.this,
                            Constants.ERRO_TENTE_NOVAMENTE, Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
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
                headers.put(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON_CHARSET_UTF_8);
                return headers;
            }
        };
        queue.add(stringRequest);
        queue.start();
        progressDialog = progressDialog.show(FormActivity.this,
                Constants.VAZIO, Constants.CARREGANDO, false);
    }

    private void salvarIdSharedPreferences(long id) {
        SharedPreferences.Editor editor;
        editor = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE).edit();
        editor.putLong("idEntrevistado", id);
        editor.commit();
    }

    private Entrevistado instanciaEntrevistado() {
        RadioButton rb = (RadioButton) findViewById(radioGroupSexo.getCheckedRadioButtonId());
        return new Entrevistado(iniciaisNome.getText().toString(),
                Integer.parseInt(idade.getText().toString()), rb.getText().toString(),
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
        femininoRadio = (RadioButton) findViewById(R.id.femininoId);

        corPeleSpinner = (Spinner) findViewById(R.id.corPeleIdSpinner);
        escolaridadeSpinner = (Spinner) findViewById(R.id.escolaridadeIdSpinner);
        saudeFisicaSpinner = (Spinner) findViewById(R.id.saudeFisicaIdSpinner);
        saudeMentalSpinner = (Spinner) findViewById(R.id.saudeMentalIdSpinner);

        botaoProximo = (Button) findViewById(R.id.botaoProximoId);
    }
}
