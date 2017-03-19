package q8rn.com.q8rn.controllers;
/* Created by Gabriel on 14/03/2017. */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import q8rn.com.q8rn.entities.Entrevistado;
import q8rn.com.q8rn.model.CriaBanco;

public class EntrevistadoController {

    private static final String ERRO_AO_INSERIR_ENTREVISTADO = "Erro ao inserir entrevistado";
    private static final String ENTREVISTADO_INSERIDO_SUCESSO = "Entrevistado inserido com sucesso";
    private CriaBanco banco;

    public EntrevistadoController(Context context) {
        this.banco = new CriaBanco(context);
    }

    public String insereEntrevistado(Entrevistado entrevistado) {
        ContentValues valores;
        long resultado;

        SQLiteDatabase db = banco.getWritableDatabase();
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

    public List<Entrevistado> findAllEntrevistados() {
        List<Entrevistado> entrevistados = new ArrayList<>();

        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CriaBanco.TABELA_ENTREVISTADO, null);
        if(c.moveToFirst()){
            do{
                Entrevistado entrevistado = new Entrevistado();

                entrevistado.setId(c.getLong(0));
                entrevistado.setAltura(c.getDouble(1));
                entrevistado.setCinturaQuadril(c.getDouble(2));
                entrevistado.setCodIdentificacao(c.getString(3));
                entrevistado.setCorPele(c.getString(4));
                entrevistado.setDoencas(c.getString(5));
                entrevistado.setEscolaridade(c.getString(6));
                entrevistado.setEspirometria(c.getInt(7));
                entrevistado.setGlicemiaCapilar(c.getDouble(9));
                entrevistado.setIdade(c.getInt(10));
                entrevistado.setImc(c.getDouble(11));
                entrevistado.setPa(c.getDouble(12));
                entrevistado.setPeso(c.getDouble(13));
                entrevistado.setProfissao(c.getString(14));
                entrevistado.setReligiao(c.getString(15));
                entrevistado.setSaudeFisica(c.getString(16));
                entrevistado.setSaudeMental(c.getString(17));
                entrevistado.setSexo(c.getString(18));
                entrevistado.setTempoReligiao(c.getString(19));

                entrevistados.add(entrevistado);

                Gson gson = new Gson();
                String textEntrevistado = gson.toJson(entrevistado);
                Log.i("entrevistado", textEntrevistado);
            }while(c.moveToNext());
        }
        c.close();
        db.close();

        if (!entrevistados.isEmpty()) {
            return entrevistados;
        }
        return null;
    }

    public int recuperaLastId() {
        int lastId = 0;

        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CriaBanco.TABELA_ENTREVISTADO +
                " ORDER BY " + CriaBanco.ID_ENTREVISTADO + " DESC LIMIT 1" , null);
        if(c.moveToFirst()){
            do{
                lastId = c.getInt(0);
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        
        return lastId;
    }
}
