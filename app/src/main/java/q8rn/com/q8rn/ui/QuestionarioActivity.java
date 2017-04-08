package q8rn.com.q8rn.ui;

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

    public static final String LISTA = "listaMelhorar";
    public static final String PONTOS = "pontos";
    public static final String COD_QUESTAO = "codQuestao";

    public static final int UM = 1;
    public static final int VINTE_CINCO = 25;

    private TextView titulo;
    private RadioGroup radioGroupAlternativas;
    private RadioButton alternativa1;
    private RadioButton alternativa2;
    private RadioButton alternativa3;
    private RadioButton alternativa4;
    private RadioButton alternativa5;

    private int codQuestao;
    private HashMap<Integer, Integer> pontos;
    private List<Questao> listaMelhorar;
    private Questao questao;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);

        instanciaElementos();

        Intent intent = getIntent();
        codQuestao = intent.getExtras().getInt(Constants.COD_QUESTAO);

        if (codQuestao == UM) {
            pontos = new HashMap<>();
            listaMelhorar = new ArrayList<>();
        }

        HashMap<Integer, Integer> pontosRecebidos;
        pontosRecebidos = (HashMap<Integer, Integer>) intent.getExtras().getSerializable(PONTOS);

        ArrayList<Questao> listaRecebida = intent.getParcelableArrayListExtra(LISTA);

        if (pontosRecebidos != null) {
            pontos = pontosRecebidos;
        }

        if (listaRecebida != null) {
            listaMelhorar = listaRecebida;
        }

        questao = findQuestaoByCod();

        populaQuestao(questao);

        controleRadioButtons();

        radioGroupAlternativas.check(R.id.radioUmId);
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
        questao = new Questao();
        QuestaoManager questaoManager = new QuestaoManager(getBaseContext());
        return questaoManager.findQuestaoByCod(codQuestao);
    }

    private void populaQuestao(Questao questao) {

        if (questao != null) {
            titulo.setText(questao.getTitulo());

            ActionBar supportActionBar = this.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setTitle(questao.getDominio());
            }

            RadioButton alternativa1 = (RadioButton) radioGroupAlternativas.getChildAt(0);
            RadioButton alternativa2 = (RadioButton) radioGroupAlternativas.getChildAt(1);
            RadioButton alternativa3 = (RadioButton) radioGroupAlternativas.getChildAt(2);
            RadioButton alternativa4 = (RadioButton) radioGroupAlternativas.getChildAt(3);
            RadioButton alternativa5 = (RadioButton) radioGroupAlternativas.getChildAt(4);

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
        int selecionado = radioGroupAlternativas.getCheckedRadioButtonId();
        switch(selecionado){
            case R.id.radioUmId:
                pontos.put(codQuestao, 4);
                break;
            case R.id.radioDoisId:
                pontos.put(codQuestao, 3);
                break;
            case R.id.radioTresId:
                pontos.put(codQuestao, 2);
                adicionaQuestaoListaDeficiencia();
                break;
            case R.id.radioQuatroId:
                pontos.put(codQuestao, 1);
                adicionaQuestaoListaDeficiencia();
                break;
            case R.id.radioCincoId:
                pontos.put(codQuestao, 0);
                adicionaQuestaoListaDeficiencia();
                break;
        }
    }

    private void adicionaQuestaoListaDeficiencia() {
        if (!listaMelhorar.contains(questao)) {
            listaMelhorar.add(questao);
        }
    }

    private void controleRadioButtons() {
        mostrarRadioButton(alternativa1);
        mostrarRadioButton(alternativa2);
        mostrarRadioButton(alternativa3);
        mostrarRadioButton(alternativa4);
        mostrarRadioButton(alternativa5);
    }

    private void instanciaElementos() {
        titulo = (TextView) findViewById(R.id.tituloId);
        radioGroupAlternativas = (RadioGroup) findViewById(R.id.radioGroupAlternativasId);

        alternativa1 = (RadioButton) findViewById(R.id.radioUmId);
        alternativa2 = (RadioButton) findViewById(R.id.radioDoisId);
        alternativa3 = (RadioButton) findViewById(R.id.radioTresId);
        alternativa4 = (RadioButton) findViewById(R.id.radioQuatroId);
        alternativa5 = (RadioButton) findViewById(R.id.radioCincoId);
    }

    private void mostrarRadioButton(RadioButton radioButton) {
        if (radioButton.getText().equals(Constants.VAZIO)){
            radioButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        codQuestao--;
    }

    public void proximaQuestao(View view) {
        if (radioGroupAlternativas.getCheckedRadioButtonId() != -1) {
            calculaEscoreAtual();
            if (codQuestao == VINTE_CINCO) {
                codQuestao++;
                Intent intentEscore;
                intentEscore = new Intent(QuestionarioActivity.this, EscoreActivity.class);
                intentEscore.putParcelableArrayListExtra(LISTA, (ArrayList<Questao>) listaMelhorar);
                intentEscore.putExtra(PONTOS, pontos);
                startActivity(intentEscore);
            } else {
                codQuestao++;
                Intent intentProximo = new Intent(QuestionarioActivity.this, QuestionarioActivity.class);
                intentProximo.putExtra(COD_QUESTAO, codQuestao);
                intentProximo.putParcelableArrayListExtra(LISTA, (ArrayList<Questao>) listaMelhorar);
                intentProximo.putExtra(PONTOS, pontos);
                startActivity(intentProximo);
            }
        } else {
            Toast.makeText(QuestionarioActivity.this, Constants.SELECIONE_ALTERNATIVA,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
