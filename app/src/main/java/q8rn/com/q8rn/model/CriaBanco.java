package q8rn.com.q8rn.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* Created by Gabriel on 08/03/2017. */

public class CriaBanco extends SQLiteOpenHelper{

    /* NOME BANCO */
    private static final String NOME_BANCO = "q8rn.db";

    /* VERSAO */
    private static final int VERSAO = 1;

    /* NOMES TABELAS */
    public static final String TABELA_QUESTAO = "questao";
    private static final String TABELA_DOMINIO = "dominio";

    /* IDS */
    public static final String ID_QUESTAO = "_id";

    /* CAMPOS TABELA QUESTAO */
    public static final String COD_QUESTAO = "cod_questao";
    public static final String ALTERNATIVA_QUESTAO = "alternativa";
    public static final String TITULO_QUESTAO = "titulo";
    private static final String DTAALTERACAO_QUESTAO = "dta_alteracao";
    public static final String PK_DOMINIO_QUESTAO = "id_dominio";

    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(criaTabelaQuestao());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABELA_QUESTAO);
        onCreate(sqLiteDatabase);
    }

    private String criaTabelaQuestao() {
        StringBuffer sql = new StringBuffer();
        sql.append("CREATE TABLE " + TABELA_QUESTAO);
        sql.append(" ("+ ID_QUESTAO + " integer primary key autoincrement, ");
        sql.append(" "+ COD_QUESTAO +" integer, ");
        for (int numQuestao = 1;numQuestao < 6;numQuestao++) {
            sql.append(" " + ALTERNATIVA_QUESTAO + numQuestao + " varchar(255), ");
        }
        sql.append(" "+ DTAALTERACAO_QUESTAO +" timestamp, ");
        sql.append(" "+ TITULO_QUESTAO +" varchar(255), ");
        sql.append(" "+ PK_DOMINIO_QUESTAO +" integer ");
        sql.append(" )");
        return String.valueOf(sql);
    }
}
