package q8rn.com.q8rn.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import q8rn.com.q8rn.entities.Questao;
import q8rn.com.q8rn.model.CriaBanco;

/* Created by Gabriel on 08/03/2017. */

public class QuestaoController {

    private static final String ERRO_AO_INSERIR_QUESTÃO = "Erro ao inserir questão";
    private static final String QUESTÃO_INSERIDA_COM_SUCESSO = "Questão inserida com sucesso";

    private CriaBanco banco;

    public QuestaoController(Context context) {
        this.banco = new CriaBanco(context);
    }

    public String insereQuestao(long codQuestao, String titulo, String alternativa1,
                                String alternativa2, String alternativa3, String alternativa4,
                                String alternativa5, String dominio) {
        ContentValues valores;
        long resultado;

        SQLiteDatabase db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.COD_QUESTAO, codQuestao);
        valores.put(CriaBanco.TITULO_QUESTAO, titulo);
        valores.put(CriaBanco.ALTERNATIVA_QUESTAO + 1, alternativa1);
        valores.put(CriaBanco.ALTERNATIVA_QUESTAO + 2, alternativa2);
        valores.put(CriaBanco.ALTERNATIVA_QUESTAO + 3, alternativa3);
        valores.put(CriaBanco.ALTERNATIVA_QUESTAO + 4, alternativa4);
        valores.put(CriaBanco.ALTERNATIVA_QUESTAO + 5, alternativa5);
        valores.put(CriaBanco.DOMINIO, dominio);

        resultado = db.insert(CriaBanco.TABELA_QUESTAO, null, valores);
        db.close();

        if (resultado ==-1)
            return ERRO_AO_INSERIR_QUESTÃO;
        else
            return QUESTÃO_INSERIDA_COM_SUCESSO;
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
                questao.setDominio(c.getString(9));

                questoes.add(questao);

                Log.i("questao", questao.getCodQuestao() + questao.getTitulo() + questao.getDominio());
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