package q8rn.com.q8rn.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.constants.Constants;
import q8rn.com.q8rn.controllers.EntrevistadoController;
import q8rn.com.q8rn.controllers.QuestaoEntrevistadoController;
import q8rn.com.q8rn.entities.Entrevistado;

public class EscoreActivity extends AppCompatActivity {

    public static final String LISTA_MELHORAR = "listaMelhorar";
    public static final String NÃO_É_PERMITIDO_VOLTAR_AO_QUESTIONÁRIO = "Não é permitido voltar ao questionário";

    private TextView escore;
    private Button voltarMenu;
    private TextView resultado;
    private ListView listaMelhorar;

    private EntrevistadoController entrevistadoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escore);

        escore = (TextView) findViewById(R.id.totalEscoreId);
        voltarMenu = (Button) findViewById(R.id.voltarMenuId);
        resultado = (TextView) findViewById(R.id.textResultadoId);
        listaMelhorar = (ListView) findViewById(R.id.listaMelhorarId);

        Intent intent = getIntent();
        HashMap<Integer, Integer> pontos;
        pontos = (HashMap<Integer, Integer>) intent.getExtras().getSerializable("pontos");

        List<String> listaRecebida = intent.getStringArrayListExtra(LISTA_MELHORAR);

        if (listaRecebida != null) {
            String[] itensListView = new String[listaRecebida.size()];

            int contador = 0;

            for (String item : listaRecebida) {
                itensListView[contador] = item;
                contador++;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, itensListView);

            listaMelhorar.setAdapter(adapter);
        }

        Entrevistado entrevistado = recuperaEntrevistadoShared();
        salvarOfflineEntrevistado(entrevistado);
        int idEntrevistado = recuperaLastId();

        int escoreTotal = 0;

        QuestaoEntrevistadoController qeController =
                new QuestaoEntrevistadoController(getBaseContext());

        Iterator it = pontos != null ? pontos.entrySet().iterator() : null;

        if (it != null) {
            while (it.hasNext()) {
                Map.Entry item = (Map.Entry)it.next();
                qeController.insereQuestaoEntrevistado(idEntrevistado, (int) item.getKey(),
                        (int) item.getValue());
                escoreTotal += (int) item.getValue();
                it.remove();
            }
        }

        escore.setText(String.valueOf(escoreTotal));

        resultado.setText(calculaResultado(escoreTotal));

        voltarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarConfirmacao();
            }
        });
    }

    private void salvarOfflineEntrevistado(Entrevistado entrevistado) {
        entrevistadoController = new EntrevistadoController(getBaseContext());
        entrevistadoController.insereEntrevistado(entrevistado);
    }

    private int recuperaLastId() {
        return entrevistadoController.recuperaLastId();
    }

    private Entrevistado recuperaEntrevistadoShared() {
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("entrevistado", "");
        return gson.fromJson(json, Entrevistado.class);
    }

    private String calculaResultado(int escoreTotal) {
        if (escoreTotal >= 0 && escoreTotal <= 34) {
            return Constants.INSUFICIENTE;
        } else if (escoreTotal > 34 && escoreTotal <= 54) {
            return Constants.REGULAR;
        } else if (escoreTotal > 54 && escoreTotal <= 69) {
            return Constants.BOM;
        } else if (escoreTotal > 69 && escoreTotal <= 84) {
            return Constants.MUITO_BOM;
        } else if (escoreTotal > 84 && escoreTotal <= 100) {
            return Constants.EXCELENTE;
        } else {
            Toast.makeText(this, Constants.ERRO_AO_CARREGAR_RESULTADO, Toast.LENGTH_SHORT).show();
            return Constants.VAZIO;
        }
    }

    public void mostrarConfirmacao() {
        new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Alerta")
            .setMessage("Quer realmente voltar ao menu?")
            .setPositiveButton("Sim", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intentMenu = new Intent(EscoreActivity.this, MainActivity.class);
                    startActivity(intentMenu);
                }

            })
            .setNegativeButton("Não", null)
            .show();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, NÃO_É_PERMITIDO_VOLTAR_AO_QUESTIONÁRIO, Toast.LENGTH_SHORT).show();
    }
}
