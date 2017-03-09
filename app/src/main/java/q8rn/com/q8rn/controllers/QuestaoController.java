package q8rn.com.q8rn.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import q8rn.com.q8rn.activities.QuestionarioActivity;
import q8rn.com.q8rn.model.CriaBanco;
import q8rn.com.q8rn.to.Questao;

/* Created by Gabriel on 08/03/2017. */

public class QuestaoController {

    public static final String ERRO_AO_INSERIR_QUESTÃO = "Erro ao inserir questão";
    public static final String QUESTÃO_INSERIDA_COM_SUCESSO = "Questão inserida com sucesso";

    private SQLiteDatabase db;
    private CriaBanco banco;

    public QuestaoController(Context context) {
        this.banco = new CriaBanco(context);
    }

    public String insereDados(long codQuestao, String titulo, String alternativa1,
                              String alternativa2, String alternativa3, String alternativa4,
                              String alternativa5, int pkDominio) {
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.COD_QUESTAO, codQuestao);
        valores.put(CriaBanco.TITULO_QUESTAO, titulo);
        valores.put(CriaBanco.ALTERNATIVA_QUESTAO + 1, alternativa1);
        valores.put(CriaBanco.ALTERNATIVA_QUESTAO + 2, alternativa2);
        valores.put(CriaBanco.ALTERNATIVA_QUESTAO + 3, alternativa3);
        valores.put(CriaBanco.ALTERNATIVA_QUESTAO + 4, alternativa4);
        valores.put(CriaBanco.ALTERNATIVA_QUESTAO + 5, alternativa5);
        valores.put(CriaBanco.PK_DOMINIO_QUESTAO, String.valueOf(pkDominio));

        resultado = db.insert(CriaBanco.TABELA_QUESTAO, null, valores);
        db.close();

        if (resultado ==-1)
            return ERRO_AO_INSERIR_QUESTÃO;
        else
            return QUESTÃO_INSERIDA_COM_SUCESSO;
    }
    
    public void findAllQuestao() {
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CriaBanco.TABELA_QUESTAO, null);
        if(c.moveToFirst()){
            do{
                String id = c.getString(0);
                String titulo = c.getString(1);
                String alternativa1 = c.getString(2);
                String alternativa2 = c.getString(3);
                String alternativa3 = c.getString(4);
                String alternativa4 = c.getString(5);
                String alternativa5 = c.getString(6);

                Log.i("questao", id + titulo + alternativa1 + alternativa2 + alternativa3 +
                        alternativa4 + alternativa5);
            }while(c.moveToNext());
        }
        c.close();
        db.close();
    }

    public Questao findQuestaoByCod(long codQuestao) {
        List<Questao> questoes = new ArrayList<>();

        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CriaBanco.TABELA_QUESTAO + " WHERE "
                + CriaBanco.COD_QUESTAO + " = " + codQuestao , null);
        if(c.moveToFirst()){
            do{
                Questao questao = new Questao();
                questao.setId(Long.parseLong(c.getString(0)));
                questao.setCodQuestao(Long.parseLong(c.getString(1)));
                questao.setTitulo(c.getString(8));
                questao.setAlternativa1(c.getString(2));
                questao.setAlternativa2(c.getString(3));
                questao.setAlternativa3(c.getString(4));
                questao.setAlternativa4(c.getString(5));
                questao.setAlternativa5(c.getString(6));

                questoes.add(questao);

                Log.i("questao", questao.getCodQuestao() + questao.getTitulo());
            }while(c.moveToNext());
        }
        c.close();
        db.close();

        if (!questoes.isEmpty()) {
            return questoes.get(0);
        }
        return null;
    }

    public void deletaAllQuestoes() {
        SQLiteDatabase db = banco.getReadableDatabase();
        db.delete(CriaBanco.TABELA_QUESTAO, null, null);
        db.close();
    }
}
