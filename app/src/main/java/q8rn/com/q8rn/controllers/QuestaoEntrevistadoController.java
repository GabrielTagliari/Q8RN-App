package q8rn.com.q8rn.controllers;
/* Created by Gabriel on 15/03/2017. */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
import q8rn.com.q8rn.activities.MainActivity;
import q8rn.com.q8rn.entities.QuestaoEntrevistado;
import q8rn.com.q8rn.model.CriaBanco;

public class QuestaoEntrevistadoController {

    public static final String ERRO_AO_INSERIR_QE = "Erro ao inserir resultado";
    public static final String QE_INSERIDA_COM_SUCESSO = "Resultado inserido com sucesso";

    private SQLiteDatabase db;
    private CriaBanco banco;

    public QuestaoEntrevistadoController(Context context) {
        this.banco = new CriaBanco(context);
    }

    public String insereQuestaoEntrevistado(int idEntrevistado, int idQuestao, int escore) {
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
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

    public File gerarExcel() {
        SQLiteDatabase db = banco.getReadableDatabase();

        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "q8rn.csv");
        try {

            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

            //Headers
            String arrStr1[] = {"nutricao1", "nutricao2", "nutricao3", "nutricao4", "exercicio5",
                    "exercicio6", "exercicio7", "agua8", "agua9", "sol10", "sol11", "temp12",
                    "temp13", "temp14", "temp15", "temp16", "ar17", "ar18","descanso19",
                    "descanso20", "descanso21", "conf22", "conf23", "conf24", "conf25",
                    "Q8RNtotal"};

            csvWrite.writeNext(arrStr1);

            //TODO adicionar a parte do entrevistado

            ArrayList<Integer> pontos = recuperaDadosPontosRelatorio(db);

            String arrStr[] = new String[pontos.size() + 1];
            int total = 0;

            for (int i = 0;i < pontos.size();i++){
                arrStr[i] = String.valueOf(pontos.get(i));
                total += pontos.get(i);
            }

            arrStr[pontos.size()] = String.valueOf(total);

            csvWrite.writeNext(arrStr);

            csvWrite.close();
            return file;
        } catch (IOException e) {
            Log.e("MainActivity", e.getMessage(), e);
            return file;
        }
    }

    @NonNull
    private ArrayList<Integer> recuperaDadosPontosRelatorio(SQLiteDatabase db) {
        Cursor cq = null;

        cq = db.rawQuery("SELECT * FROM " + CriaBanco.TABELA_QUESTAO_ENTREVISTADO
                + " WHERE " + CriaBanco.ENTREVISTADO_ID + " = " + 1
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