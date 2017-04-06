package q8rn.com.q8rn.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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

    public static final String ENTREVISTADO = "entrevistado";
    public static final String COD_QUESTAO = "codQuestao";

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
    private EditText pas;
    private EditText pad;
    private EditText glicemiaCapilar;
    private EditText espirometria;
    private Spinner saudeFisicaSpinner;
    private Spinner saudeMentalSpinner;
    private EditText doencas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        instanciaElementosTela();
        populaTodosSpinners();

        clearRadioErrorOnChange();

        ActionBar supportActionBar = this.getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.titulo_form);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        //botaoLoadData.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        lista.add(doencas);
        return lista;
    }

    private void salvarIdSharedPreferences(Entrevistado entrevistado) {
        SharedPreferences.Editor editor;
        editor = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(entrevistado);
        editor.putString(ENTREVISTADO, json);
        editor.apply();
    }

    private Entrevistado retornaEntrevistado() {

        RadioButton rb = (RadioButton) findViewById(radioGroupSexo.getCheckedRadioButtonId());
        int codEscolaridade = Entrevistado
                .getCodEscolaridade(escolaridadeSpinner.getSelectedItem().toString());

        String cinturaTexto = cinturaQuadril.getText().toString();
        String espirometriaTexto = espirometria.getText().toString();
        String glicemiaTexto = glicemiaCapilar.getText().toString();

        boolean imcVazio = isVazio(imc);
        boolean cinturaVazio = isVazio(cinturaQuadril);
        boolean pasVazio = isVazio(pas);
        boolean padVazio = isVazio(pad);
        boolean glicemiaVazio = isVazio(glicemiaCapilar);
        boolean espirometriaVazio = isVazio(espirometria);

        Entrevistado entrevistado = new Entrevistado();
        entrevistado.setCodIdentificacao(iniciaisNome.getText().toString());
        entrevistado.setIdade(Integer.parseInt(idade.getText().toString()));
        entrevistado.setSexo(rb.getText().toString());
        entrevistado.setCorPele(corPeleSpinner.getSelectedItem().toString());
        entrevistado.setReligiao(religiao.getText().toString());
        entrevistado.setTempoReligiao(Integer.parseInt(tempoReligiao.getText().toString()));
        entrevistado.setProfissao(profissao.getText().toString());
        entrevistado.setEscolaridade(codEscolaridade);
        entrevistado.setPeso(Double.parseDouble(peso.getText().toString()));
        entrevistado.setAltura(Double.parseDouble(altura.getText().toString()));
        entrevistado.setImc(imcVazio ? 0 : Double.parseDouble(imc.getText().toString()));
        entrevistado.setCinturaQuadril(cinturaVazio ? 0 : Double.parseDouble(cinturaTexto));
        entrevistado.setPas(pasVazio ? 0 : Double.parseDouble(pas.getText().toString()));
        entrevistado.setPad(padVazio ? 0 : Double.parseDouble(pad.getText().toString()));
        entrevistado.setGlicemiaCapilar(glicemiaVazio ? 0 : Double.parseDouble(glicemiaTexto));
        entrevistado.setEspirometria(espirometriaVazio ? 0 : Integer.parseInt(espirometriaTexto));
        entrevistado.setSaudeFisica(saudeFisicaSpinner.getSelectedItem().toString());
        entrevistado.setSaudeMental(saudeMentalSpinner.getSelectedItem().toString());
        entrevistado.setDoencas(doencas.getText().toString());

        return entrevistado;
    }

    private boolean isVazio(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
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
        pas = (EditText) findViewById(R.id.pasId);
        pad = (EditText) findViewById(R.id.padId);
        glicemiaCapilar = (EditText) findViewById(R.id.glicemiaId);
        espirometria = (EditText) findViewById(R.id.espirometriaId);
        doencas = (EditText) findViewById(R.id.doencasId);

        radioGroupSexo = (RadioGroup) findViewById(R.id.radioGroupSexoId);
        femininoRadio = (RadioButton) findViewById(R.id.femininoId);

        corPeleSpinner = (Spinner) findViewById(R.id.corPeleIdSpinner);
        escolaridadeSpinner = (Spinner) findViewById(R.id.escolaridadeIdSpinner);
        saudeFisicaSpinner = (Spinner) findViewById(R.id.saudeFisicaIdSpinner);
        saudeMentalSpinner = (Spinner) findViewById(R.id.saudeMentalIdSpinner);
    }

    @SuppressLint("SetTextI18n")
    public void loadData(View view) {
        iniciaisNome.setText("GGG");
        idade.setText("30");
        religiao.setText("Evangélico");
        tempoReligiao.setText("5");
        profissao.setText("Padeiro");
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
        escolaridadeSpinner.setSelection(3);
        saudeFisicaSpinner.setSelection(1);
        saudeMentalSpinner.setSelection(2);
    }

    public void iniciarQuestionario(View view) {
        List<EditText> listaEditText = montaListaEditText();
        List<Spinner> listaSpinners = montaListaSpinners();

        EntrevistadoValidator validator = new EntrevistadoValidator();

        boolean permiteSalvar = validator.validate(listaEditText, radioGroupSexo,
                femininoRadio, listaSpinners);

        if (permiteSalvar) {
            Entrevistado entrevistado = retornaEntrevistado();
            salvarIdSharedPreferences(entrevistado);
            Intent intent = new Intent(FormActivity.this, QuestionarioActivity.class);
            intent.putExtra(COD_QUESTAO, 1);
            startActivity(intent);

        } else {
            Toast.makeText(FormActivity.this,
                    Constants.PREENCHA_CAMPOS, Toast.LENGTH_SHORT).show();
        }
    }
}
