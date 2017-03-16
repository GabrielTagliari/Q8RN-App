package q8rn.com.q8rn.controllers;
/* Created by Gabriel on 15/03/2017. */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
}
