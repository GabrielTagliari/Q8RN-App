package q8rn.com.q8rn.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.constants.Constants;

public class EscoreActivity extends AppCompatActivity {

    private TextView escore;
    private Button voltarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escore);

        escore = (TextView) findViewById(R.id.totalEscoreId);
        voltarMenu = (Button) findViewById(R.id.voltarMenuId);

        Intent intent = getIntent();
        int escoreTotal = intent.getExtras().getInt(Constants.ESCORE);

        escore.setText(String.valueOf(escoreTotal));

        voltarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarConfirmacao();
            }
        });
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
