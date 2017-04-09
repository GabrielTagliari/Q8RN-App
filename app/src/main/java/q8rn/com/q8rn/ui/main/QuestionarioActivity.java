package q8rn.com.q8rn.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.entity.Questao;
import q8rn.com.q8rn.infrastructure.Constants;
import q8rn.com.q8rn.manager.QuestaoManager;

public class QuestionarioActivity extends AppCompatActivity {

    public static final String LISTA = "mListaMelhorar";
    public static final String PONTOS = "mPontos";
    public static final String COD_QUESTAO = "mCodQuestao";

    public static final int UM = 1;
    public static final int VINTE_CINCO = 25;

    private TextView mTitulo;
    private RadioGroup mRadioGroupAlternativas;
    private RadioButton mAlternativa1;
    private RadioButton mAlternativa2;
    private RadioButton mAlternativa3;
    private RadioButton mAlternativa4;
    private RadioButton mAlternativa5;

    private int mCodQuestao;
    private HashMap<Integer, Integer> mPontos;
    private List<Questao> mListaMelhorar;
    private Questao mQuestao;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);

        instanciaElementos();

        Intent intent = getIntent();
        mCodQuestao = intent.getExtras().getInt(Constants.COD_QUESTAO);

        if (mCodQuestao == UM) {
            mPontos = new HashMap<>();
            mListaMelhorar = new ArrayList<>();
        }

        HashMap<Integer, Integer> pontosRecebidos;
        pontosRecebidos = (HashMap<Integer, Integer>) intent.getExtras().getSerializable(PONTOS);

        ArrayList<Questao> listaRecebida = intent.getParcelableArrayListExtra(LISTA);

        if (pontosRecebidos != null) {
            mPontos = pontosRecebidos;
        }

        if (listaRecebida != null) {
            mListaMelhorar = listaRecebida;
        }

        mQuestao = findQuestaoByCod();

        populaQuestao(mQuestao);

        controleRadioButtons();

        mRadioGroupAlternativas.check(R.id.radioUmId);
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

    private Questao findQuestaoByCod() {
        mQuestao = new Questao();
        QuestaoManager questaoManager = new QuestaoManager(getBaseContext());
        return questaoManager.findQuestaoByCod(mCodQuestao);
    }

    private void populaQuestao(Questao questao) {

        if (questao != null) {
            mTitulo.setText(questao.getTitulo());

            ActionBar supportActionBar = this.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setTitle(questao.getDominio());
            }

            RadioButton alternativa1 = (RadioButton) mRadioGroupAlternativas.getChildAt(0);
            RadioButton alternativa2 = (RadioButton) mRadioGroupAlternativas.getChildAt(1);
            RadioButton alternativa3 = (RadioButton) mRadioGroupAlternativas.getChildAt(2);
            RadioButton alternativa4 = (RadioButton) mRadioGroupAlternativas.getChildAt(3);
            RadioButton alternativa5 = (RadioButton) mRadioGroupAlternativas.getChildAt(4);

            alternativa1.setText(questao.getAlternativa1());
            alternativa2.setText(questao.getAlternativa2());
            alternativa3.setText(questao.getAlternativa3());
            alternativa4.setText(questao.getAlternativa4());
            alternativa5.setText(questao.getAlternativa5());
        } else {
            Toast.makeText(QuestionarioActivity.this, Constants.ERRO_CARREGAR,
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void calculaEscoreAtual() {
        int selecionado = mRadioGroupAlternativas.getCheckedRadioButtonId();
        switch(selecionado){
            case R.id.radioUmId:
                mPontos.put(mCodQuestao, 4);
                break;
            case R.id.radioDoisId:
                mPontos.put(mCodQuestao, 3);
                break;
            case R.id.radioTresId:
                mPontos.put(mCodQuestao, 2);
                adicionaQuestaoListaDeficiencia();
                break;
            case R.id.radioQuatroId:
                mPontos.put(mCodQuestao, 1);
                adicionaQuestaoListaDeficiencia();
                break;
            case R.id.radioCincoId:
                mPontos.put(mCodQuestao, 0);
                adicionaQuestaoListaDeficiencia();
                break;
        }
    }

    private void adicionaQuestaoListaDeficiencia() {
        if (!mListaMelhorar.contains(mQuestao)) {
            mListaMelhorar.add(mQuestao);
        }
    }

    private void controleRadioButtons() {
        mostrarRadioButton(mAlternativa1);
        mostrarRadioButton(mAlternativa2);
        mostrarRadioButton(mAlternativa3);
        mostrarRadioButton(mAlternativa4);
        mostrarRadioButton(mAlternativa5);
    }

    private void instanciaElementos() {
        mTitulo = (TextView) findViewById(R.id.tituloId);
        mRadioGroupAlternativas = (RadioGroup) findViewById(R.id.radioGroupAlternativasId);

        mAlternativa1 = (RadioButton) findViewById(R.id.radioUmId);
        mAlternativa2 = (RadioButton) findViewById(R.id.radioDoisId);
        mAlternativa3 = (RadioButton) findViewById(R.id.radioTresId);
        mAlternativa4 = (RadioButton) findViewById(R.id.radioQuatroId);
        mAlternativa5 = (RadioButton) findViewById(R.id.radioCincoId);
    }

    private void mostrarRadioButton(RadioButton radioButton) {
        if (radioButton.getText().equals(Constants.VAZIO)){
            radioButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mCodQuestao--;
    }

    public void proximaQuestao(View view) {
        if (mRadioGroupAlternativas.getCheckedRadioButtonId() != -1) {
            calculaEscoreAtual();
            if (mCodQuestao == VINTE_CINCO) {
                mCodQuestao++;
                Intent intentEscore;
                intentEscore = new Intent(QuestionarioActivity.this, EscoreActivity.class);
                intentEscore.putParcelableArrayListExtra(LISTA, (ArrayList<Questao>) mListaMelhorar);
                intentEscore.putExtra(PONTOS, mPontos);
                startActivity(intentEscore);
            } else {
                mCodQuestao++;
                Intent intentProximo = new Intent(QuestionarioActivity.this, QuestionarioActivity.class);
                intentProximo.putExtra(COD_QUESTAO, mCodQuestao);
                intentProximo.putParcelableArrayListExtra(LISTA, (ArrayList<Questao>) mListaMelhorar);
                intentProximo.putExtra(PONTOS, mPontos);
                startActivity(intentProximo);
            }
        } else {
            Toast.makeText(QuestionarioActivity.this, Constants.SELECIONE_ALTERNATIVA,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
