package q8rn.com.q8rn.repository;

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
    public static final String TABELA_ENTREVISTADO = "entrevistado";
    public static final String TABELA_QUESTAO_ENTREVISTADO = "questaoentrevistado";

    /* IDS */
    private static final String ID_QUESTAO = "id_questao";
    public static final String ID_ENTREVISTADO = "id_entrevistado";

    /* CAMPOS TABELA QUESTAO */
    public static final String COD_QUESTAO = "cod_questao";
    public static final String ALTERNATIVA_QUESTAO = "alternativa";
    public static final String TITULO_QUESTAO = "titulo";
    private static final String DTAALTERACAO_QUESTAO = "dta_alteracao";
    public static final String DOMINIO = "dominio";

    /* CAMPOS TABELA ENTREVISTADO */
    public static final String ALTURA = "altura";
    public static final String CINTURA_QUADRIL = "cintura_quadril";
    public static final String COD_IDENTIFICACAO = "cod_identificacao";
    public static final String COR_PELE = "cor_pele";
    public static final String DOENCAS = "doencas";
    public static final String IDADE = "idade";
    public static final String ESCOLARIDADE = "escolar" + IDADE;
    public static final String ESPIROMETRIA = "espirometria";
    private static final String FLG_ATIVO = "flg_ativo";
    public static final String GLICEMIA_CAPILAR = "glicemia_capilar";
    public static final String IMC = "imc";
    public static final String PAS = "pas";
    public static final String PAD = "pad";
    public static final String PESO = "peso";
    public static final String PROFISSAO = "profissao";
    public static final String RELIGIAO = "religiao";
    public static final String SAUDE_FISICA = "saude_fisica";
    public static final String SAUDE_MENTAL = "saude_mental";
    public static final String SEXO = "sexo";
    public static final String TEMPO_RELIGIAO = "tempo_religiao";

    /* TABELA RELACIONAMENTO QUESTAO USUARIO */
    public static final String ENTREVISTADO_ID = "entrevistado_id";
    public static final String QUESTAO_ID = "questao_id";
    public static final String ESCORE = "escore";

    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(criaTabelaQuestao());
        sqLiteDatabase.execSQL(criaTabelaEntrevistado());
        sqLiteDatabase.execSQL(criaTabelaQuestaoEntrevistado());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABELA_QUESTAO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABELA_ENTREVISTADO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABELA_QUESTAO_ENTREVISTADO);
        onCreate(sqLiteDatabase);
    }

    private String criaTabelaQuestao() {
        StringBuffer sql = new StringBuffer();
        sql.append("CREATE TABLE " + TABELA_QUESTAO);
        sql.append(" ("+ ID_QUESTAO + " integer primary key autoincrement, ");
        sql.append(" "+ COD_QUESTAO +" integer, ");
        for (int numQuestao = 1;numQuestao < 6;numQuestao++) {
            sql.append(" " + ALTERNATIVA_QUESTAO).append(numQuestao).append(" varchar(255), ");
        }
        sql.append(" "+ DTAALTERACAO_QUESTAO +" timestamp, ");
        sql.append(" "+ TITULO_QUESTAO +" varchar(255), ");
        sql.append(" "+ DOMINIO +" varchar(50) ");
        sql.append(" )");
        return String.valueOf(sql);
    }

    private String criaTabelaEntrevistado() {
        StringBuffer sql = new StringBuffer();
        sql.append("CREATE TABLE " + TABELA_ENTREVISTADO);
        sql.append("( ");
        sql.append("   " + ID_ENTREVISTADO + " integer PRIMARY KEY NOT NULL, ");
        sql.append("   " + ALTURA + " float(19), ");
        sql.append("   " + CINTURA_QUADRIL + " float(19), ");
        sql.append("   " + COD_IDENTIFICACAO + " varchar(255), ");
        sql.append("   " + COR_PELE + " varchar(255), ");
        sql.append("   " + DOENCAS + " varchar(255), ");
        sql.append("   " + ESCOLARIDADE + " int, ");
        sql.append("   " + ESPIROMETRIA + " int, ");
        sql.append("   " + FLG_ATIVO + " bool, ");
        sql.append("   " + GLICEMIA_CAPILAR + " float(19), ");
        sql.append("   " + IDADE + " int, ");
        sql.append("   " + IMC + " float(19), ");
        sql.append("   " + PAS + " float(19), ");
        sql.append("   " + PAD + " float(19), ");
        sql.append("   " + PESO + " float(19), ");
        sql.append("   " + PROFISSAO + " varchar(255), ");
        sql.append("   " + RELIGIAO + " varchar(255), ");
        sql.append("   " + SAUDE_FISICA + " varchar(255), ");
        sql.append("   " + SAUDE_MENTAL + " varchar(255), ");
        sql.append("   " + SEXO + " varchar(255), ");
        sql.append("   " + TEMPO_RELIGIAO + " int ");
        sql.append(")");
        return String.valueOf(sql);
    }

    private String criaTabelaQuestaoEntrevistado() {
        StringBuffer sql = new StringBuffer();
        sql.append("CREATE TABLE " + TABELA_QUESTAO_ENTREVISTADO);
        sql.append("( ");
        sql.append(" " + ENTREVISTADO_ID + " integer, ");
        sql.append(" " + QUESTAO_ID + " integer, ");
        sql.append(" " + ESCORE + " integer, ");
        sql.append(" PRIMARY KEY(" + ENTREVISTADO_ID + ", " + QUESTAO_ID + ") ");
        sql.append(")");
        return String.valueOf(sql);
    }
}
