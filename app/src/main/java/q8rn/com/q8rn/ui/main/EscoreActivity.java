package q8rn.com.q8rn.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.entity.Entrevistado;
import q8rn.com.q8rn.entity.Questao;
import q8rn.com.q8rn.infrastructure.Constants;
import q8rn.com.q8rn.manager.EntrevistadoManager;
import q8rn.com.q8rn.manager.QuestaoEntrevistadoManager;

public class EscoreActivity extends AppCompatActivity {

    public static final String LISTA_MELHORAR = "listaMelhorar";
    public static final String NÃO_É_PERMITIDO_VOLTAR_AO_QUESTIONÁRIO = "Não é permitido voltar ao questionário";
    public static final String RESULTADO_QUESTIONARIO = "Resultado do questionário dos oito remédios naturais";
    public static final String ALERTA = "Alerta";
    public static final String QUER_REALMENTE_VOLTAR_AO_MENU = "Quer realmente voltar ao menu?";
    public static final String SIM = "Sim";
    public static final String NAO = "Não";
    public static final String TEXT_HTML = "text/html";
    public static final String ESCORE_FINAL = "Escore Final";

    private TextView mEscore;
    private TextView mResultado;

    private List<Questao> mListaRecebida;

    private HashMap<Integer, Integer> mPontos;

    private AlertDialog mAlertDialog;

    private EntrevistadoManager mEntrevistadoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escore);

        ActionBar supportActionBar = this.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(ESCORE_FINAL);
        }

        mEscore = (TextView) findViewById(R.id.totalEscoreId);
        mResultado = (TextView) findViewById(R.id.textResultadoId);
        ListView listaMelhorar = (ListView) findViewById(R.id.listaMelhorarId);

        Intent intent = getIntent();
        mPontos = (HashMap<Integer, Integer>) intent.getExtras().getSerializable("pontos");

        mListaRecebida = intent.getParcelableArrayListExtra(LISTA_MELHORAR);

        if (mListaRecebida != null) {
            criaDialog();

            String[] itensListView = new String[mListaRecebida.size()];

            int contador = 0;

            for (Questao item : mListaRecebida) {
                itensListView[contador] = item.getDominio() + " | Questão "
                        + item.getCodQuestao() + " | Pontos: "
                        + mPontos.get((int) item.getCodQuestao());
                contador++;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, itensListView);

            listaMelhorar.setAdapter(adapter);

            listaMelhorar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Questao questao = mListaRecebida.get(i);
                    mAlertDialog.setTitle(questao.getDominio());
                    String msg = montaDetalhesQuestao(questao);
                    mAlertDialog.setMessage(msg);
                    mAlertDialog.show();
                }
            });
        }

        Entrevistado entrevistado = recuperaEntrevistadoShared();
        salvarOfflineEntrevistado(entrevistado);
        int idEntrevistado = recuperaLastId();

        int escoreTotal = 0;

        QuestaoEntrevistadoManager qeManager =
                new QuestaoEntrevistadoManager(getBaseContext());

        HashMap<Integer, Integer> inserirPontos = new HashMap<>(mPontos);

        Iterator it = inserirPontos.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry item = (Map.Entry)it.next();
            qeManager.insereQuestaoEntrevistado(idEntrevistado, (int) item.getKey(),
                    (int) item.getValue());
            escoreTotal += (int) item.getValue();
            it.remove();
        }

        mEscore.setText(String.valueOf(escoreTotal));

        mResultado.setText(calculaResultado(escoreTotal));
    }

    @NonNull
    private String montaDetalhesQuestao(Questao questao) {
        Integer pontos = this.mPontos.get((int) questao.getCodQuestao());

        StringBuffer msg = new StringBuffer();
        msg.append(questao.getTitulo());
        msg.append("\n\n1) ").append(questao.getAlternativa1());
        if (questao.getAlternativa2() != null){
            msg.append("\n2) ").append(questao.getAlternativa2());
        }
        if (questao.getAlternativa2() != null) {
            msg.append("\n3) ").append(questao.getAlternativa3());
        }
        if (questao.getAlternativa2() != null) {
            msg.append("\n4) ").append(questao.getAlternativa4());
        }
        if (questao.getAlternativa2() != null) {
            msg.append("\n5) ").append(questao.getAlternativa5());
        } else {
            msg.append("\n2) ").append(questao.getAlternativa5());
        }
        msg.append("\n\nResposta: ").append(questao.retornaAlternativaByNumero(pontos));
        msg.append("\n\nPontos: ").append(pontos);
        return String.valueOf(msg);
    }

    private String montaDetalhesQuestaoHtml(Questao questao) {
        Integer pontos = this.mPontos.get((int) questao.getCodQuestao());

        StringBuffer msg = new StringBuffer();
        msg.append(questao.getDominio());
        msg.append("<br><br>").append(questao.getTitulo());
        msg.append("<br><br>1) ").append(questao.getAlternativa1());
        if (questao.getAlternativa2() != null){
            msg.append("<br>2) ").append(questao.getAlternativa2());
        }
        if (questao.getAlternativa2() != null) {
            msg.append("<br>3) ").append(questao.getAlternativa3());
        }
        if (questao.getAlternativa2() != null) {
            msg.append("<br>4) ").append(questao.getAlternativa4());
        }
        if (questao.getAlternativa2() != null) {
            msg.append("<br>5) ").append(questao.getAlternativa5());
        } else {
            msg.append("<br>2) ").append(questao.getAlternativa5());
        }
        msg.append("<br><br>Resposta: ").append(questao.retornaAlternativaByNumero(pontos));
        msg.append("<br><br>Pontos: ").append(pontos);
        return String.valueOf(msg);
    }

    private void criaDialog() {
        mAlertDialog = new AlertDialog.Builder(EscoreActivity.this).create();
        mAlertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    private void salvarOfflineEntrevistado(Entrevistado entrevistado) {
        mEntrevistadoManager = new EntrevistadoManager(getBaseContext());
        mEntrevistadoManager.insereEntrevistado(entrevistado);
    }

    private int recuperaLastId() {
        return mEntrevistadoManager.recuperaLastId();
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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, NÃO_É_PERMITIDO_VOLTAR_AO_QUESTIONÁRIO, Toast.LENGTH_SHORT).show();
    }

    public String montaBodyEmail() {
        StringBuilder body = new StringBuilder();
        body.append("<!DOCTYPE html>");
        body.append("<html>");
        body.append("<head>");
        body.append("<meta charset='utf-8'>");
        body.append("</head>");
        body.append("<body>");
        body.append("<h3><b>Escore Total: </b>");
        body.append(mEscore.getText()).append("</h3>");
        body.append("<h3><b>Resultado: ");
        body.append(mResultado.getText());
        body.append("</b></h3>");
        body.append("<h3><b>Pontos a melhorar</b></h3>");

        for (Questao questao : mListaRecebida) {
            body.append(montaDetalhesQuestaoHtml(questao));
            body.append("<br>");
            body.append("---------------------------------------------------------------------");
            body.append("<br>");
        }

        body.append("</body>");
        body.append("</html>");

        return body.toString();
    }


    public void enviaEmail(View view) {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType(TEXT_HTML);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, RESULTADO_QUESTIONARIO);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(montaBodyEmail()));
        startActivity(emailIntent);
    }

    public void voltarMenu(View view) {
        new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_info)
            .setTitle(ALERTA)
            .setMessage(QUER_REALMENTE_VOLTAR_AO_MENU)
            .setPositiveButton(SIM, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intentMenu = new Intent(EscoreActivity.this, MainActivity.class);
                    startActivity(intentMenu);
                }

            })
            .setNegativeButton(NAO, null)
            .show();
    }
}
