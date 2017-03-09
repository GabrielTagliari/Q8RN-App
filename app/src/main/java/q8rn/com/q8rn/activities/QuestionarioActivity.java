package q8rn.com.q8rn.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    private List<Questao> questoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);

        titulo = (TextView) findViewById(R.id.tituloId);

        QuestaoController controller = new QuestaoController(getBaseContext());

        controller.deletaAllQuestoes();

        controller.insereDados(1,"Com que frequência você inclui em suas refeições diária todos " +
                "esses alimentos: feijões, castanhas, frutas, legumes e verduras?",
                "Sempre", "Com relativa frequência", "Algumas vezes",
                "Raramente", "Quase nunca", 1);

        Questao questao = controller.findQuestaoByCod(1);

        titulo.setText(questao.getTitulo());
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
