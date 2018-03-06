package q8rn.com.q8rn.business;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import q8rn.com.q8rn.entity.Entrevistado;
import q8rn.com.q8rn.repository.CriaBanco;

/** Created by gabriel on 07/04/17. */

public class EntrevistadoBusiness extends BaseBusiness {

    private static final String ERRO_AO_INSERIR_ENTREVISTADO = "Erro ao inserir entrevistado";
    private static final String ENTREVISTADO_INSERIDO_SUCESSO = "Entrevistado inserido com sucesso";

    private CriaBanco mBanco;

    public EntrevistadoBusiness(Context context) {
        super(context);

        this.mBanco = new CriaBanco(mContext);
    }

    public String insereEntrevistado(Entrevistado entrevistado) {
        ContentValues valores;
        long resultado;

        SQLiteDatabase db = mBanco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.ALTURA, entrevistado.getAltura());
        valores.put(CriaBanco.CINTURA_QUADRIL, entrevistado.getCinturaQuadril());
        valores.put(CriaBanco.INICIAIS_NOME, entrevistado.getIniciaisNome());
        valores.put(CriaBanco.COR_PELE, entrevistado.getCorPele());
        valores.put(CriaBanco.DOENCAS, entrevistado.getDoencas());
        valores.put(CriaBanco.DOENCAS, entrevistado.getDoencas());
        valores.put(CriaBanco.ESCOLARIDADE, entrevistado.getEscolaridade());
        valores.put(CriaBanco.ESPIROMETRIA, entrevistado.getEspirometria());
        valores.put(CriaBanco.GLICEMIA_CAPILAR, entrevistado.getGlicemiaCapilar());
        valores.put(CriaBanco.IDADE, entrevistado.getIdade());
        valores.put(CriaBanco.IMC, entrevistado.getImc());
        valores.put(CriaBanco.PAS, entrevistado.getPas());
        valores.put(CriaBanco.PAD, entrevistado.getPad());
        valores.put(CriaBanco.PESO, entrevistado.getPeso());
        valores.put(CriaBanco.PROFISSAO, entrevistado.getProfissao());
        valores.put(CriaBanco.RELIGIAO, entrevistado.getReligiao());
        valores.put(CriaBanco.SAUDE_FISICA, entrevistado.getSaudeFisica());
        valores.put(CriaBanco.SAUDE_MENTAL, entrevistado.getSaudeMental());
        valores.put(CriaBanco.SEXO, entrevistado.getSexo());
        valores.put(CriaBanco.TEMPO_RELIGIAO, entrevistado.getTempoReligiao());

        valores.put(CriaBanco.COD_IDENTIFICACAO, entrevistado.getCodIdentificacao());
        valores.put(CriaBanco.CINTURA, entrevistado.getCintura());
        valores.put(CriaBanco.QUADRIL, entrevistado.getQuadril());
        valores.put(CriaBanco.TESTE_ESFORCO_ANTES, entrevistado.getTesteEsforcoAntes());
        valores.put(CriaBanco.TESTE_ESFORCO_DEPOIS, entrevistado.getTesteEsforcoDepois());
        valores.put(CriaBanco.QUALIDADE_VIDA, entrevistado.getQualidadeVida());
        valores.put(CriaBanco.O_QUE_MELHORAR, entrevistado.getoQueMelhorar());
        valores.put(CriaBanco.CINTURA_ESTATURA, entrevistado.getCinturaEstatura());
        valores.put(CriaBanco.ESTADO_CIVIL, entrevistado.getEstadoCivil());
        valores.put(CriaBanco.COM_QUEM_MORA, entrevistado.getComQuemMora());

        resultado = db.insert(CriaBanco.TABELA_ENTREVISTADO, null, valores);
        db.close();

        if (resultado ==-1)
            return ERRO_AO_INSERIR_ENTREVISTADO;
        else
            return ENTREVISTADO_INSERIDO_SUCESSO;
    }

    public List<Entrevistado> findAllEntrevistados() {
        List<Entrevistado> entrevistados = new ArrayList<>();

        SQLiteDatabase db = mBanco.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CriaBanco.TABELA_ENTREVISTADO, null);
        if(c.moveToFirst()) do {
            Entrevistado entrevistado = new Entrevistado();

            entrevistado.setId(c.getLong(0));
            entrevistado.setAltura(c.getDouble(1));
            entrevistado.setCinturaQuadril(c.getDouble(2));
            entrevistado.setIniciaisNome(c.getString(3));
            entrevistado.setCorPele(c.getString(4));
            entrevistado.setDoencas(c.getString(5));
            entrevistado.setEscolaridade(c.getInt(6));
            entrevistado.setEspirometria(c.getInt(7));
            entrevistado.setGlicemiaCapilar(c.getDouble(9));
            entrevistado.setIdade(c.getInt(10));
            entrevistado.setImc(c.getDouble(11));
            entrevistado.setPas(c.getDouble(12));
            entrevistado.setPad(c.getDouble(13));
            entrevistado.setPeso(c.getDouble(14));
            entrevistado.setProfissao(c.getString(15));
            entrevistado.setReligiao(c.getString(16));
            entrevistado.setSaudeFisica(c.getString(17));
            entrevistado.setSaudeMental(c.getString(18));
            entrevistado.setSexo(c.getString(19));
            entrevistado.setTempoReligiao(c.getInt(20));

            entrevistado.setCodIdentificacao(c.getString(21));
            entrevistado.setCintura(c.getDouble(22));
            entrevistado.setQuadril(c.getDouble(23));
            entrevistado.setTesteEsforcoAntes(c.getDouble(24));
            entrevistado.setTesteEsforcoDepois(c.getDouble(25));
            entrevistado.setQualidadeVida(c.getString(26));
            entrevistado.setoQueMelhorar(c.getString(27));
            entrevistado.setCinturaEstatura(c.getDouble(28));
            entrevistado.setEstadoCivil(c.getString(29));
            entrevistado.setComQuemMora(c.getString(30));

            entrevistados.add(entrevistado);

            Gson gson = new Gson();
            String textEntrevistado = gson.toJson(entrevistado);
            Log.i("entrevistado", textEntrevistado);
        } while (c.moveToNext());
        c.close();
        db.close();

        if (!entrevistados.isEmpty()) {
            return entrevistados;
        }
        return null;
    }

    public int recuperaLastId() {
        int lastId = 0;

        SQLiteDatabase db = mBanco.getReadableDatabase();
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
