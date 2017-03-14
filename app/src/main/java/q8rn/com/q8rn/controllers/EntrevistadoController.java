package q8rn.com.q8rn.controllers;
/* Created by Gabriel on 14/03/2017. */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import q8rn.com.q8rn.entities.Entrevistado;
import q8rn.com.q8rn.model.CriaBanco;

public class EntrevistadoController {

    private static final String ERRO_AO_INSERIR_ENTREVISTADO = "Erro ao inserir entrevistado";
    private static final String ENTREVISTADO_INSERIDO_SUCESSO = "Entrevistado inserido com sucesso";
    private SQLiteDatabase db;
    private CriaBanco banco;

    public EntrevistadoController(Context context) {
        this.banco = new CriaBanco(context);;
    }

    public String insereEntrevistado(Entrevistado entrevistado) {
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.ALTURA, entrevistado.getAltura());
        valores.put(CriaBanco.CINTURA_QUADRIL, entrevistado.getCinturaQuadril());
        valores.put(CriaBanco.COD_IDENTIFICACAO, entrevistado.getCodIdentificacao());
        valores.put(CriaBanco.COR_PELE, entrevistado.getCorPele());
        valores.put(CriaBanco.DOENCAS, entrevistado.getDoencas());
        valores.put(CriaBanco.DOENCAS, entrevistado.getDoencas());
        valores.put(CriaBanco.ESCOLARIDADE, entrevistado.getEscolaridade());
        valores.put(CriaBanco.ESPIROMETRIA, entrevistado.getEspirometria());
        valores.put(CriaBanco.GLICEMIA_CAPILAR, entrevistado.getGlicemiaCapilar());
        valores.put(CriaBanco.IDADE, entrevistado.getIdade());
        valores.put(CriaBanco.IMC, entrevistado.getImc());
        valores.put(CriaBanco.PA, entrevistado.getPa());
        valores.put(CriaBanco.PESO, entrevistado.getPeso());
        valores.put(CriaBanco.PROFISSAO, entrevistado.getProfissao());
        valores.put(CriaBanco.RELIGIAO, entrevistado.getReligiao());
        valores.put(CriaBanco.SAUDE_FISICA, entrevistado.getSaudeFisica());
        valores.put(CriaBanco.SAUDE_MENTAL, entrevistado.getSaudeMental());
        valores.put(CriaBanco.SEXO, entrevistado.getSexo());
        valores.put(CriaBanco.TEMPO_RELIGIAO, entrevistado.getTempoReligiao());

        resultado = db.insert(CriaBanco.TABELA_ENTREVISTADO, null, valores);
        db.close();

        if (resultado ==-1)
            return ERRO_AO_INSERIR_ENTREVISTADO;
        else
            return ENTREVISTADO_INSERIDO_SUCESSO;
    }

    public void findAllEntrevistados() {
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CriaBanco.TABELA_ENTREVISTADO, null);
        if(c.moveToFirst()){
            do{
                String id = c.getString(0);
                String altura = c.getString(1);
                String cintura = c.getString(2);
                String codIdentificacao = c.getString(3);
                String cor_pele = c.getString(4);
                String doencas = c.getString(5);
                String escolaridade = c.getString(6);
                String espirometria = c.getString(7);
                String glicemia_capilar = c.getString(8);
                String idade = c.getString(9);
                String imc = c.getString(10);
                String pa = c.getString(11);
                String peso = c.getString(12);
                String profissao = c.getString(13);
                String religiao = c.getString(14);
                String saude_fisica = c.getString(15);
                String saude_mental = c.getString(16);
                String sexo = c.getString(17);
                String tempo_religiao = c.getString(18);

                Log.i("entrevistado", id + ", " + altura + ", " + cintura + ", " + codIdentificacao);
            }while(c.moveToNext());
        }
        c.close();
        db.close();
    }
}
