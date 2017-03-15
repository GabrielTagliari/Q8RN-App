package q8rn.com.q8rn.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.constants.Constants;

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
        int escoreTotal = intent.getExtras().getInt(Constants.ESCORE);

        escore.setText(String.valueOf(escoreTotal));

        resultado.setText(calculaResultado(escoreTotal));

        voltarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarConfirmacao();
            }
        });
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
