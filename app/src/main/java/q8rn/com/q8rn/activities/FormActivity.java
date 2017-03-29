package q8rn.com.q8rn.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.constants.Constants;
import q8rn.com.q8rn.entities.Entrevistado;
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
    private EditText escolaridade;
    private EditText peso;
    private EditText altura;
    private EditText imc;
    private EditText cinturaQuadril;
    private EditText pas;
    private EditText pad;
    private EditText glicemiaCapilar;
    private EditText espirometria;
    private Spinner saudeFisicaSpinner;
    private Spinner saudeMentalSpinner;
    private EditText doencas;

    private Button botaoProximo;
    private Button botaoLoadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        instanciaElementosTela();
        populaTodosSpinners();

        clearRadioErrorOnChange();

        //botaoLoadData.setVisibility(View.GONE);

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
                    salvarIdSharedPreferences(entrevistado);
                    Intent intent = new Intent(FormActivity.this, QuestionarioActivity.class);
                    intent.putExtra("codQuestao", 1);
                    startActivity(intent);

                } else {
                    Toast.makeText(FormActivity.this,
                            Constants.PREENCHA_CAMPOS, Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
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
        lista.add(escolaridade);
        lista.add(peso);
        lista.add(altura);
        lista.add(doencas);
        return lista;
    }

    private void salvarIdSharedPreferences(Entrevistado entrevistado) {
        SharedPreferences.Editor editor;
        editor = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(entrevistado);
        editor.putString("entrevistado", json);
        editor.apply();
    }

    private Entrevistado instanciaEntrevistado() {
        RadioButton rb = (RadioButton) findViewById(radioGroupSexo.getCheckedRadioButtonId());
        return new Entrevistado(iniciaisNome.getText().toString(),
                Integer.parseInt(idade.getText().toString()), rb.getText().toString(),
                corPeleSpinner.getSelectedItem().toString(),
                religiao.getText().toString(), Integer.parseInt(tempoReligiao.getText().toString()),
                profissao.getText().toString(),
                Integer.parseInt(escolaridade.getText().toString()),
                Double.parseDouble(peso.getText().toString()),
                Double.parseDouble(altura.getText().toString()),
                Double.parseDouble(imc.getText().toString()),
                Double.parseDouble(cinturaQuadril.getText().toString()),
                Double.parseDouble(pas.getText().toString()),
                Double.parseDouble(pad.getText().toString()),
                Double.parseDouble(glicemiaCapilar.getText().toString()),
                Integer.parseInt(espirometria.getText().toString()),
                saudeFisicaSpinner.getSelectedItem().toString(),
                saudeMentalSpinner.getSelectedItem().toString(),
                doencas.getText().toString());
    }

    private void populaTodosSpinners() {
        populaSpinner(R.array.corpele_array, corPeleSpinner);
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
        escolaridade = (EditText) findViewById(R.id.escolaridadeId);
        peso = (EditText) findViewById(R.id.pesoId);
        altura = (EditText) findViewById(R.id.alturaId);
        imc = (EditText) findViewById(R.id.imcId);
        cinturaQuadril = (EditText) findViewById(R.id.cinturaQuadrilId);
        pas = (EditText) findViewById(R.id.pasId);
        pad = (EditText) findViewById(R.id.padId);
        glicemiaCapilar = (EditText) findViewById(R.id.glicemiaId);
        espirometria = (EditText) findViewById(R.id.espirometriaId);
        doencas = (EditText) findViewById(R.id.doencasId);

        radioGroupSexo = (RadioGroup) findViewById(R.id.radioGroupSexoId);
        femininoRadio = (RadioButton) findViewById(R.id.femininoId);

        corPeleSpinner = (Spinner) findViewById(R.id.corPeleIdSpinner);
        saudeFisicaSpinner = (Spinner) findViewById(R.id.saudeFisicaIdSpinner);
        saudeMentalSpinner = (Spinner) findViewById(R.id.saudeMentalIdSpinner);

        botaoProximo = (Button) findViewById(R.id.botaoProximoId);

        botaoLoadData = (Button) findViewById(R.id.loadDataId);
    }

    private void loadData() {
        iniciaisNome.setText("GGG");
        idade.setText("30");
        religiao.setText("Evangélico");
        tempoReligiao.setText("5");
        profissao.setText("Padeiro");
        escolaridade.setText("22");
        peso.setText("76");
        altura.setText("1.75");
        imc.setText("25.8");
        cinturaQuadril.setText("85.5");
        pas.setText("110");
        pad.setText("70");
        glicemiaCapilar.setText("99");
        espirometria.setText("400");
        doencas.setText("Nenhuma");

        femininoRadio.setChecked(true);

        corPeleSpinner.setSelection(1);
        saudeFisicaSpinner.setSelection(1);
        saudeMentalSpinner.setSelection(2);
    }
}
