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
import java.util.List;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.constants.Constants;
import q8rn.com.q8rn.controllers.QuestaoController;
import q8rn.com.q8rn.to.Questao;

public class QuestionarioActivity extends AppCompatActivity {

    private static final String TAG = "json";

    private TextView titulo;
    private RadioGroup radioGroupAlternativas;
    private Button botaoVoltar;
    private Button botaoProximo;
    private int codQuestao;

    private List<Questao> questoes;

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

        botaoProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codQuestao++;
                Intent intentProximo = new Intent(QuestionarioActivity.this, QuestionarioActivity.class);
                intentProximo.putExtra("codQuestao", codQuestao);
                startActivity(intentProximo);
            }
        });


        QuestaoController controller = new QuestaoController(getBaseContext());

        if (codQuestao == 1) {
            controller.deletaAllQuestoes();

            controller.insereDados(1,"Com que frequência você inclui em suas refeições diária todos " +
                            "esses alimentos: feijões, castanhas, frutas, legumes e verduras?",
                    "Sempre", "Com relativa frequência", "Algumas vezes",
                    "Raramente", "Quase nunca", 1);

            controller.insereDados(2,"Quantas refeições você faz por dia? (Desjejum, almoço, jantar, lanches, ceia)",
                    "Sempre", "Com relativa frequência", "Algumas vezes",
                    "Raramente", "Quase nunca", 1);

            controller.insereDados(3,"Além de consumir outros alimentos do dia a dia como: feijão, " +
                    "arroz, massa, frutas, legumes e verduras, como você classifica o seu padrão " +
                    "alimentar/dietético*, de acordo com a frequência de consumo dos alimentos de " +
                    "origem animal?", "Vegetariano estrito come laticínios, ovos, carne vermelha, " +
                    "aves e peixes, menos que 1 vez por mês", "Ovo lacto, Vegetariano, " +
                    "Come Laticínios e ovos mais que 1 vez por mês e carne, " +
                    "aves e peixes, menos que 1 vez por mês", "Pesco, Vegetariano, " +
                    "Come carne vermelha e aves menos que 1 vez por mês e peixes, " +
                    "mais que 1 vez por mês", "Semi Vegetariano, Come carne vermelha, " +
                    "aves e peixes, menos de 1 vez por semana", "Não vegetariano, " +
                    "Come carne vermelha, aves e peixes 1 vez ou mais por semana", 1);
        }

        Questao questao = controller.findQuestaoByCod(codQuestao);

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

    }

    private void makeJsonArrayRequest() {

        showpDialog();

        RequestQueue queue = Volley.newRequestQueue(QuestionarioActivity.this);
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
                                titulo.setText(questao.getTitulo());
                            }
                            Log.i(TAG, "Questoes: " + String.valueOf(questoes));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        queue.add(req);
    }

    private void showpDialog() {
    }

    private void hidepDialog() {

    }
}
