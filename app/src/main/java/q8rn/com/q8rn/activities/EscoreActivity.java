package q8rn.com.q8rn.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.constants.Constants;
import q8rn.com.q8rn.controllers.QuestaoEntrevistadoController;

public class EscoreActivity extends AppCompatActivity {

    private TextView escore;
    private Button voltarMenu;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escore);

        escore = (TextView) findViewById(R.id.totalEscoreId);
        voltarMenu = (Button) findViewById(R.id.voltarMenuId);
        resultado = (TextView) findViewById(R.id.textResultadoId);

        Intent intent = getIntent();
        HashMap<Integer, Integer> pontos;
        pontos = (HashMap<Integer, Integer>) intent.getExtras().getSerializable("pontos");

        long idEntrevistado = recuperaIdEntrevistadoShared();

        QuestaoEntrevistadoController qeController =
                new QuestaoEntrevistadoController(getBaseContext());

        Iterator it = pontos.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry item = (Map.Entry)it.next();
            qeController.insereQuestaoEntrevistado((int) idEntrevistado, (int) item.getKey(),
                    (int) item.getValue());
            it.remove();
        }

        int escoreTotal = calculaEscoreTotal(pontos);

        escore.setText(String.valueOf(escoreTotal));

        resultado.setText(calculaResultado(escoreTotal));

        voltarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarConfirmacao();
            }
        });
    }

    private long recuperaIdEntrevistadoShared() {
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
        long idEntrevistado = preferences.getLong("idEntrevistado", 0);
        Log.i("questaoentrevistado", "valor do id:" + idEntrevistado);
        return idEntrevistado;
    }

    private int calculaEscoreTotal(HashMap<Integer, Integer> pontos) {
        int escoreTotal = 0;

        for (int value : pontos.values()) {
            escoreTotal += value;
        }
        return escoreTotal;
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
}
