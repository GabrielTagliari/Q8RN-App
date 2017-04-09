package q8rn.com.q8rn.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.manager.QuestaoEntrevistadoManager;
import q8rn.com.q8rn.repository.PopulaBanco;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "questao";
    public static final String NÃO_EXISTEM_DADOS = "Não existem dados a serem exportados";
    public static final String PERMISSION_IS_GRANTED = "Permission is granted";
    public static final String PERMISSION_IS_REVOKED = "Permission is revoked";
    public static final String Q8RN_TRACO = "Q8RN - ";
    public static final String CORPO_EMAIL = "Banco de dados do questionário dos oito remédios naturais gerado em: ";
    public static final String TEXT_HTML = "text/html";
    public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy hh:mm:ss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PopulaBanco populaBanco = new PopulaBanco(getBaseContext());
        populaBanco.popularQuestoes();

        if (getSupportActionBar() != null){
            this.getSupportActionBar().hide();
        }
    }

    public boolean temPermissaoArmazenamento() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, PERMISSION_IS_GRANTED);
                return true;
            } else {

                Log.v(TAG, PERMISSION_IS_REVOKED);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            Log.v(TAG,PERMISSION_IS_GRANTED);
            return true;
        }
    }

    public void realizarQuestionario(View view) {
        Intent intent = new Intent(MainActivity.this, FormStepperActivity.class);
        startActivity(intent);
    }

    public void enviarDadosEmail(View view) {
        QuestaoEntrevistadoManager qeManager = new QuestaoEntrevistadoManager(getBaseContext());

        if (temPermissaoArmazenamento()) {

            try {
                File file = qeManager.gerarExcel(getBaseContext());
                Uri u1;
                u1 = Uri.fromFile(file);

                Date dataSemformato = Calendar.getInstance().getTime();

                SimpleDateFormat spf = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);
                String dataFormatada = spf.format(dataSemformato);

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, Q8RN_TRACO + dataFormatada);
                sendIntent.putExtra(Intent.EXTRA_TEXT, CORPO_EMAIL + dataFormatada + "\n\n");
                sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
                sendIntent.setType(TEXT_HTML);
                startActivity(sendIntent);

            } catch (RuntimeException e) {
                Log.i(TAG, e.getMessage());
                Toast.makeText(MainActivity.this, NÃO_EXISTEM_DADOS, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
