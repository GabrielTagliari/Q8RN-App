package q8rn.com.q8rn.ui;

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
import q8rn.com.q8rn.infrastructure.Constants;
import q8rn.com.q8rn.entity.Entrevistado;
import q8rn.com.q8rn.infrastructure.EntrevistadoValidator;

public class FormActivity extends AppCompatActivity {

    public static final String ENTREVISTADO = "entrevistado";
    public static final String COD_QUESTAO = "codQuestao";

    private EditText mIniciaisNome;
    private EditText mIdade;
    private RadioGroup mRadioGroupSexo;
    private RadioButton mFemininoRadio;
    private Spinner mCorPeleSpinner;
    private EditText mReligiao;
    private EditText mTempoReligiao;
    private EditText mProfissao;
    private Spinner mEscolaridadeSpinner;
    private EditText mPeso;
    private EditText mAltura;
    private EditText mImc;
    private EditText mCinturaQuadril;
    private EditText mPas;
    private EditText mPad;
    private EditText mGlicemiaCapilar;
    private EditText mEspirometria;
    private Spinner mSaudeFisicaSpinner;
    private Spinner mSaudeMentalSpinner;
    private EditText mDoencas;

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
        mRadioGroupSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mFemininoRadio.setError(null);
            }
        });
    }

    private List<Spinner> montaListaSpinners() {
        List<Spinner> lista = new ArrayList<>();
        lista.add(mCorPeleSpinner);
        lista.add(mEscolaridadeSpinner);
        lista.add(mSaudeFisicaSpinner);
        lista.add(mSaudeMentalSpinner);
        return lista;
    }

    private List<EditText> montaListaEditText() {
        List<EditText> lista = new ArrayList<>();
        lista.add(mIniciaisNome);
        lista.add(mIdade);
        lista.add(mReligiao);
        lista.add(mTempoReligiao);
        lista.add(mProfissao);
        lista.add(mPeso);
        lista.add(mAltura);
        lista.add(mDoencas);
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

        RadioButton rb = (RadioButton) findViewById(mRadioGroupSexo.getCheckedRadioButtonId());
        int codEscolaridade = Entrevistado
                .getCodEscolaridade(mEscolaridadeSpinner.getSelectedItem().toString());

        String cinturaTexto = mCinturaQuadril.getText().toString();
        String espirometriaTexto = mEspirometria.getText().toString();
        String glicemiaTexto = mGlicemiaCapilar.getText().toString();

        boolean imcVazio = isVazio(mImc);
        boolean cinturaVazio = isVazio(mCinturaQuadril);
        boolean pasVazio = isVazio(mPas);
        boolean padVazio = isVazio(mPad);
        boolean glicemiaVazio = isVazio(mGlicemiaCapilar);
        boolean espirometriaVazio = isVazio(mEspirometria);

        Entrevistado entrevistado = new Entrevistado();
        entrevistado.setCodIdentificacao(mIniciaisNome.getText().toString());
        entrevistado.setIdade(Integer.parseInt(mIdade.getText().toString()));
        entrevistado.setSexo(rb.getText().toString());
        entrevistado.setCorPele(mCorPeleSpinner.getSelectedItem().toString());
        entrevistado.setReligiao(mReligiao.getText().toString());
        entrevistado.setTempoReligiao(Integer.parseInt(mTempoReligiao.getText().toString()));
        entrevistado.setProfissao(mProfissao.getText().toString());
        entrevistado.setEscolaridade(codEscolaridade);
        entrevistado.setPeso(Double.parseDouble(mPeso.getText().toString()));
        entrevistado.setAltura(Double.parseDouble(mAltura.getText().toString()));
        entrevistado.setImc(imcVazio ? 0 : Double.parseDouble(mImc.getText().toString()));
        entrevistado.setCinturaQuadril(cinturaVazio ? 0 : Double.parseDouble(cinturaTexto));
        entrevistado.setPas(pasVazio ? 0 : Double.parseDouble(mPas.getText().toString()));
        entrevistado.setPad(padVazio ? 0 : Double.parseDouble(mPad.getText().toString()));
        entrevistado.setGlicemiaCapilar(glicemiaVazio ? 0 : Double.parseDouble(glicemiaTexto));
        entrevistado.setEspirometria(espirometriaVazio ? 0 : Integer.parseInt(espirometriaTexto));
        entrevistado.setSaudeFisica(mSaudeFisicaSpinner.getSelectedItem().toString());
        entrevistado.setSaudeMental(mSaudeMentalSpinner.getSelectedItem().toString());
        entrevistado.setDoencas(mDoencas.getText().toString());

        return entrevistado;
    }

    private boolean isVazio(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }

    private void populaTodosSpinners() {
        populaSpinner(R.array.corpele_array, mCorPeleSpinner);
        populaSpinner(R.array.escolaridade_array, mEscolaridadeSpinner);
        populaSpinner(R.array.saude_array, mSaudeFisicaSpinner);
        populaSpinner(R.array.saude_array, mSaudeMentalSpinner);
    }

    private void populaSpinner(int array, Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void instanciaElementosTela() {
        mIniciaisNome = (EditText) findViewById(R.id.iniciaisNomeId);
        mIdade = (EditText) findViewById(R.id.idadeId);
        mReligiao = (EditText) findViewById(R.id.religiaoId);
        mTempoReligiao = (EditText) findViewById(R.id.tempoReligiaoId);
        mProfissao = (EditText) findViewById(R.id.profissaoId);
        mPeso = (EditText) findViewById(R.id.pesoId);
        mAltura = (EditText) findViewById(R.id.alturaId);
        mImc = (EditText) findViewById(R.id.imcId);
        mCinturaQuadril = (EditText) findViewById(R.id.cinturaQuadrilId);
        mPas = (EditText) findViewById(R.id.pasId);
        mPad = (EditText) findViewById(R.id.padId);
        mGlicemiaCapilar = (EditText) findViewById(R.id.glicemiaId);
        mEspirometria = (EditText) findViewById(R.id.espirometriaId);
        mDoencas = (EditText) findViewById(R.id.doencasId);

        mRadioGroupSexo = (RadioGroup) findViewById(R.id.radioGroupSexoId);
        mFemininoRadio = (RadioButton) findViewById(R.id.femininoId);

        mCorPeleSpinner = (Spinner) findViewById(R.id.corPeleIdSpinner);
        mEscolaridadeSpinner = (Spinner) findViewById(R.id.escolaridadeIdSpinner);
        mSaudeFisicaSpinner = (Spinner) findViewById(R.id.saudeFisicaIdSpinner);
        mSaudeMentalSpinner = (Spinner) findViewById(R.id.saudeMentalIdSpinner);
    }

    @SuppressLint("SetTextI18n")
    public void loadData(View view) {
        mIniciaisNome.setText("GGG");
        mIdade.setText("30");
        mReligiao.setText("Evangélico");
        mTempoReligiao.setText("5");
        mProfissao.setText("Padeiro");
        mPeso.setText("76");
        mAltura.setText("1.75");
        mImc.setText("25.8");
        mCinturaQuadril.setText("85.5");
        mPas.setText("110");
        mPad.setText("70");
        mGlicemiaCapilar.setText("99");
        mEspirometria.setText("400");
        mDoencas.setText("Nenhuma");

        mFemininoRadio.setChecked(true);

        mCorPeleSpinner.setSelection(1);
        mEscolaridadeSpinner.setSelection(3);
        mSaudeFisicaSpinner.setSelection(1);
        mSaudeMentalSpinner.setSelection(2);
    }

    public void iniciarQuestionario(View view) {
        List<EditText> listaEditText = montaListaEditText();
        List<Spinner> listaSpinners = montaListaSpinners();

        EntrevistadoValidator validator = new EntrevistadoValidator();

        boolean permiteSalvar = validator.validate(listaEditText, mRadioGroupSexo,
                mFemininoRadio, listaSpinners);

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
