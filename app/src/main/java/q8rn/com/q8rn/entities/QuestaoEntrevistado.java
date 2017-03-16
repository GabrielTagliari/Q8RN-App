package q8rn.com.q8rn.entities;
/* Created by Gabriel on 15/03/2017. */

public class QuestaoEntrevistado {

    private long idEntrevistado;
    private long idQuestao;
    private int escore;

    public QuestaoEntrevistado() {}

    public QuestaoEntrevistado(long idEntrevistado, long idQuestao, int escore) {
        this.idEntrevistado = idEntrevistado;
        this.idQuestao = idQuestao;
        this.escore = escore;
    }

    public long getIdEntrevistado() {
        return idEntrevistado;
    }

    public void setIdEntrevistado(long idEntrevistado) {
        this.idEntrevistado = idEntrevistado;
    }

    public long getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(long idQuestao) {
        this.idQuestao = idQuestao;
    }

    public int getEscore() {
        return escore;
    }

    public void setEscore(int escore) {
        this.escore = escore;
    }
}
