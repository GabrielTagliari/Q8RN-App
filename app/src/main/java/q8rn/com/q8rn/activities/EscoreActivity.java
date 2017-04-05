package q8rn.com.q8rn.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
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
import q8rn.com.q8rn.entities.Questao;

public class EscoreActivity extends AppCompatActivity {

    public static final String LISTA_MELHORAR = "listaMelhorar";
    public static final String NÃO_É_PERMITIDO_VOLTAR_AO_QUESTIONÁRIO = "Não é permitido voltar ao questionário";
    public static final String RESULTADO_QUESTIONARIO = "Resultado do questionário dos oito remédios naturais";

    private TextView escore;
    private TextView escoreText;
    private Button voltarMenu;
    private TextView resultado;
    private ListView listaMelhorar;

    private List<Questao> listaRecebida;

    private HashMap<Integer, Integer> pontos;

    private AlertDialog alertDialog;

    private EntrevistadoController entrevistadoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escore);

        this.getSupportActionBar().setTitle("Escore Final");

        escore = (TextView) findViewById(R.id.totalEscoreId);
        voltarMenu = (Button) findViewById(R.id.voltarMenuId);
        resultado = (TextView) findViewById(R.id.textResultadoId);
        listaMelhorar = (ListView) findViewById(R.id.listaMelhorarId);

        Intent intent = getIntent();
        pontos = (HashMap<Integer, Integer>) intent.getExtras().getSerializable("pontos");

        listaRecebida = intent.getParcelableArrayListExtra(LISTA_MELHORAR);

        if (listaRecebida != null) {
            criaDialog();

            String[] itensListView = new String[listaRecebida.size()];

            int contador = 0;

            for (Questao item : listaRecebida) {
                itensListView[contador] = item.getDominio() + " | Questão "
                        + item.getCodQuestao() + " | Pontos: "
                        + pontos.get((int) item.getCodQuestao());
                contador++;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, itensListView);

            listaMelhorar.setAdapter(adapter);

            listaMelhorar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Questao questao = listaRecebida.get(i);
                    alertDialog.setTitle(questao.getDominio());
                    String msg = montaDetalhesQuestao(questao);
                    alertDialog.setMessage(msg);
                    alertDialog.show();
                }
            });
        }

        Entrevistado entrevistado = recuperaEntrevistadoShared();
        salvarOfflineEntrevistado(entrevistado);
        int idEntrevistado = recuperaLastId();

        int escoreTotal = 0;

        QuestaoEntrevistadoController qeController =
                new QuestaoEntrevistadoController(getBaseContext());

        HashMap<Integer, Integer> inserirPontos = new HashMap<>(pontos);

        Iterator it = inserirPontos.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry item = (Map.Entry)it.next();
            qeController.insereQuestaoEntrevistado(idEntrevistado, (int) item.getKey(),
                    (int) item.getValue());
            escoreTotal += (int) item.getValue();
            it.remove();
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

    @NonNull
    private String montaDetalhesQuestao(Questao questao) {
        Integer pontos = this.pontos.get((int) questao.getCodQuestao());

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
        Integer pontos = this.pontos.get((int) questao.getCodQuestao());

        StringBuffer msg = new StringBuffer();
        msg.append(questao.getDominio());
        msg.append("<br><br>").append(questao.getTitulo());
        msg.append("<br><br>1) " + questao.getAlternativa1());
        if (questao.getAlternativa2() != null){
            msg.append("<br>2) " + questao.getAlternativa2());
        }
        if (questao.getAlternativa2() != null) {
            msg.append("<br>3) " + questao.getAlternativa3());
        }
        if (questao.getAlternativa2() != null) {
            msg.append("<br>4) " + questao.getAlternativa4());
        }
        if (questao.getAlternativa2() != null) {
            msg.append("<br>5) " + questao.getAlternativa5());
        } else {
            msg.append("<br>2) " + questao.getAlternativa5());
        }
        msg.append("<br><br>Resposta: " + questao.retornaAlternativaByNumero(pontos));
        msg.append("<br><br>Pontos: " + pontos);
        return String.valueOf(msg);
    }

    private void criaDialog() {
        alertDialog = new AlertDialog.Builder(EscoreActivity.this).create();
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
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

    public String montaBodyEmail() {
        StringBuffer body = new StringBuffer();
        body.append("<!DOCTYPE html>");
        body.append("<html>");
        body.append("<head>");
        body.append("<meta charset='utf-8'>");
        body.append("</head>");
        body.append("<body>");
        body.append("<h3><b>Escore Total: </b>" + escore.getText() + "</h3>");
        body.append("<h3><b>Resultado: " + resultado.getText() + "</b></h3>");
        body.append("<h3><b>Pontos a melhorar</b></h3>");

        for (Questao questao : listaRecebida) {
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
        emailIntent.setType("text/html");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, RESULTADO_QUESTIONARIO);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(montaBodyEmail()));
        startActivity(emailIntent);
    }
}
