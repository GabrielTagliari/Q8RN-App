package q8rn.com.q8rn.controllers;
/* Created by Gabriel on 15/03/2017. */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
import q8rn.com.q8rn.entities.Entrevistado;
import q8rn.com.q8rn.entities.QuestaoEntrevistado;
import q8rn.com.q8rn.model.CriaBanco;

public class QuestaoEntrevistadoController {

    private static final String ERRO_AO_INSERIR_QE = "Erro ao inserir resultado";
    private static final String QE_INSERIDA_COM_SUCESSO = "Resultado inserido com sucesso";
    public static final String OCORREU_UM_ERRO = "Ocorreu um erro";

    private CriaBanco banco;

    public QuestaoEntrevistadoController(Context context) {
        this.banco = new CriaBanco(context);
    }

    public String insereQuestaoEntrevistado(int idEntrevistado, int idQuestao, int escore) {
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues valores;
        long resultado;

        valores = new ContentValues();
        valores.put(CriaBanco.ENTREVISTADO_ID, idEntrevistado);
        valores.put(CriaBanco.QUESTAO_ID, idQuestao);
        valores.put(CriaBanco.ESCORE, escore);

        resultado = db.insert(CriaBanco.TABELA_QUESTAO_ENTREVISTADO, null, valores);
        db.close();

        if (resultado ==-1)
            return ERRO_AO_INSERIR_QE;
        else
            return QE_INSERIDA_COM_SUCESSO;
    }

    public List<QuestaoEntrevistado> findAllQuestaoEntrevistadoByIdEntrevistado(long idEntrevistado) {
        List<QuestaoEntrevistado> resultados = new ArrayList<>();

        SQLiteDatabase db = banco.getReadableDatabase();
        /*Cursor c = db.rawQuery("SELECT qe.* FROM " + CriaBanco.TABELA_QUESTAO + " q " +
                " LEFT JOIN " + CriaBanco.TABELA_QUESTAO_ENTREVISTADO + " qe " +
                " ON qe." + CriaBanco.QUESTAO_ID + " = q." + CriaBanco.ID_QUESTAO +
                " WHERE qe." + CriaBanco.ENTREVISTADO_ID + " = " + idEntrevistado, null);*/
        Cursor c = db.rawQuery("SELECT * FROM " + CriaBanco.TABELA_QUESTAO_ENTREVISTADO
                +" WHERE "+ CriaBanco.ENTREVISTADO_ID + " = " + idEntrevistado
                + " ORDER BY " + CriaBanco.QUESTAO_ID + " ASC", null);
        if(c.moveToFirst()){
            do{
                QuestaoEntrevistado questaoEntrevistado = new QuestaoEntrevistado();
                questaoEntrevistado.setIdEntrevistado(c.getLong(0));
                questaoEntrevistado.setIdQuestao(c.getLong(1));
                questaoEntrevistado.setEscore(c.getInt(2));

                resultados.add(questaoEntrevistado);

                Log.i("questaoentrevistado", "idEntrevistado: " + questaoEntrevistado.getIdEntrevistado()
                + "idQuestao: " + questaoEntrevistado.getIdQuestao() + "escore: " + questaoEntrevistado.getEscore());
            }while(c.moveToNext());
        }
        c.close();
        db.close();

        return resultados;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public File gerarExcel(Context context) throws IOException {
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "q8rn.csv");
        try {

            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

            //Headers
            String header[] = {"numeração","IDADE", "sexo", "CORDAPELE", "religiao", "haqtotempo",
                    "profissao", "anosdeestudo", "peso", "altura", "IMC", "cintura", "PAS", "PAD",
                    "glicemiacap", "espirometria", "saudefisica", "saudemental", "doençarefer",
                    "nutricao1", "nutricao2", "nutricao3", "nutricao4", "exercicio5", "exercicio6",
                    "exercicio7", "agua8", "agua9", "sol10", "sol11", "temp12", "temp13", "temp14",
                    "temp15", "temp16", "ar17", "ar18","descanso19", "descanso20", "descanso21",
                    "conf22", "conf23", "conf24", "conf25", "Q8RNtotal"};

            csvWrite.writeNext(header);

            EntrevistadoController entrevistadoController = new EntrevistadoController(context);
            ArrayList<Entrevistado> allEntrevistados =
                    (ArrayList<Entrevistado>) entrevistadoController.findAllEntrevistados();

            if (allEntrevistados.isEmpty()) {
                throw new RuntimeException();
            }

            for (Entrevistado entrevistado : allEntrevistados) {
                adicionaLegendaCampos(entrevistado);

                String[] eachRow = new String[45];
                eachRow[0] = String.valueOf(entrevistado.getId());
                eachRow[1] = String.valueOf(entrevistado.getIdade());
                eachRow[2] = String.valueOf(entrevistado.getSexo());
                eachRow[3] = String.valueOf(entrevistado.getCorPele());
                eachRow[4] = String.valueOf(entrevistado.getReligiao());
                eachRow[5] = String.valueOf(entrevistado.getTempoReligiao());
                eachRow[6] = String.valueOf(entrevistado.getProfissao());
                eachRow[7] = String.valueOf(entrevistado.getEscolaridade());
                eachRow[8] = String.valueOf(entrevistado.getPeso());
                eachRow[9] = String.valueOf(entrevistado.getAltura());
                eachRow[10] = String.valueOf(entrevistado.getImc());
                eachRow[11] = String.valueOf(entrevistado.getCinturaQuadril());
                eachRow[12] = String.valueOf(entrevistado.getPas());
                eachRow[13] = String.valueOf(entrevistado.getPad());
                eachRow[14] = String.valueOf(entrevistado.getGlicemiaCapilar());
                eachRow[15] = String.valueOf(entrevistado.getEspirometria());
                eachRow[16] = String.valueOf(entrevistado.getSaudeFisica());
                eachRow[17] = String.valueOf(entrevistado.getSaudeMental());
                eachRow[18] = String.valueOf(entrevistado.getDoencas());

                ArrayList<Integer> pontos = recuperaDadosPontosRelatorio(entrevistado.getId());

                int total = 0;
                int contador = 0;

                for (int i = 19;i < 44;i++){
                    eachRow[i] = String.valueOf(pontos.get(contador));
                    total += pontos.get(contador);
                    contador++;
                }

                eachRow[44] = String.valueOf(total);

                csvWrite.writeNext(eachRow);
            }

            csvWrite.close();
            return file;
        } catch (IOException e) {
            throw new IOException(OCORREU_UM_ERRO);
        }
    }

    private void adicionaLegendaCampos(Entrevistado entrevistado) {
        if (entrevistado.getSexo().equals("Feminino")) {
            entrevistado.setSexo("1");
        } else {
            entrevistado.setSexo("2");
        }

        entrevistado.setSaudeFisica(adicionaLegendaSaude(entrevistado.getSaudeFisica()));
        entrevistado.setSaudeMental(adicionaLegendaSaude(entrevistado.getSaudeMental()));

        switch (entrevistado.getCorPele()) {
            case "Branca":
                entrevistado.setCorPele("1");
                break;
            case "Parda":
                entrevistado.setCorPele("1");
                break;
            case "Preta":
                entrevistado.setCorPele("1");
                break;
            case "Indígena":
                entrevistado.setCorPele("1");
                break;
            case "Amarela":
                entrevistado.setCorPele("1");
                break;
        }
    }

    private String adicionaLegendaSaude(String saude) {
        switch (saude) {
            case "Muito boa":
                saude = "1";
                break;
            case "Boa":
                saude = "2";
                break;
            case "Regular":
                saude = "3";
                break;
            case "Ruim":
                saude = "4";
                break;
            case "Muito ruim":
                saude = "5";
                break;
        }
        return saude;
    }

    @NonNull
    private ArrayList<Integer> recuperaDadosPontosRelatorio(long idEntrevistado) {
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cq;

        cq = db.rawQuery("SELECT * FROM " + CriaBanco.TABELA_QUESTAO_ENTREVISTADO
                + " WHERE " + CriaBanco.ENTREVISTADO_ID + " = " + idEntrevistado
                + " ORDER BY " + CriaBanco.QUESTAO_ID + " ASC", null);

        ArrayList<Integer> resultado = new ArrayList<>();

        if(cq.moveToFirst()){
            do{
                resultado.add(cq.getInt(2));
            }while(cq.moveToNext());
        }
        cq.close();
        db.close();
        return resultado;
    }
}