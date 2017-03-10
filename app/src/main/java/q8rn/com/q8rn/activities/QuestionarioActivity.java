package q8rn.com.q8rn.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.constants.Constants;
import q8rn.com.q8rn.controllers.QuestaoController;
import q8rn.com.q8rn.to.Questao;

public class QuestionarioActivity extends AppCompatActivity {

    private TextView titulo;
    private RadioGroup radioGroupAlternativas;
    private Button botaoVoltar;
    private Button botaoProximo;
    private int codQuestao;
    private HashMap<Integer, Integer> pontos;

    private QuestaoController questaoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);

        titulo = (TextView) findViewById(R.id.tituloId);
        radioGroupAlternativas = (RadioGroup) findViewById(R.id.radioGroupAlternativasId);
        botaoVoltar = (Button) findViewById(R.id.botaoVoltarId);
        botaoProximo = (Button) findViewById(R.id.botaoProximoId);

        Intent intent = getIntent();
        codQuestao = intent.getExtras().getInt("codQuestao");
        pontos = new HashMap<>();
        pontos = (HashMap<Integer, Integer>) intent.getExtras().getSerializable("pontos");

        botaoProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codQuestao++;
                Intent intentProximo = new Intent(QuestionarioActivity.this, QuestionarioActivity.class);
                intentProximo.putExtra("codQuestao", codQuestao);
                startActivity(intentProximo);
            }
        });

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        questaoController = new QuestaoController(getBaseContext());

        Questao questao = questaoController.findQuestaoByCod(codQuestao);

        if (questao != null) {
            titulo.setText(questao.getTitulo());

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

    @Override
    protected void onRestart() {
        super.onRestart();
        codQuestao--;
    }
}
