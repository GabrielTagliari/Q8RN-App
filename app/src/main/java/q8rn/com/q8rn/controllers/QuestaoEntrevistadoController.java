package q8rn.com.q8rn.controllers;
/* Created by Gabriel on 15/03/2017. */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

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
        Cursor c = null;

            try {
                c = db.rawQuery("SELECT * FROM " + CriaBanco.TABELA_QUESTAO_ENTREVISTADO
                        +" WHERE "+ CriaBanco.ENTREVISTADO_ID + " = " + 1
                        + " ORDER BY " + CriaBanco.QUESTAO_ID + " ASC", null);
                int rowcount = 0;
                int colcount = 0;
                File sdCardDir = Environment.getExternalStorageDirectory();
                String filename = "MyBackUp.csv";
                // the name of the file to export with
                File saveFile = new File(sdCardDir, filename);
                FileWriter fw = new FileWriter(saveFile);

                BufferedWriter bw = new BufferedWriter(fw);
                rowcount = c.getCount();
                colcount = c.getColumnCount();
                if (rowcount > 0) {
                    c.moveToFirst();

                    for (int i = 0; i < colcount; i++) {
                        if (i != colcount - 1) {

                            bw.write(c.getColumnName(i) + ",");

                        } else {

                            bw.write(c.getColumnName(i));

                        }
                    }
                    bw.newLine();

                    for (int i = 0; i < rowcount; i++) {
                        c.moveToPosition(i);

                        for (int j = 0; j < colcount; j++) {
                            if (j != colcount - 1)
                                bw.write(c.getString(j) + ",");
                            else
                                bw.write(c.getString(j));
                        }
                        bw.newLine();
                    }
                    bw.flush();
                    Log.i("excel","Exportado com sucesso");
                    return saveFile;
                }
            } catch (Exception ex) {
                if (db.isOpen()) {
                    db.close();
                    Log.i("excel",ex.getMessage().toString());
                }

            } finally {

            }
        return null;
    }
}

