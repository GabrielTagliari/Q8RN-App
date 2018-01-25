package q8rn.com.q8rn.entity;

/* Created by Gabriel on 15/03/2017. */
public class QuestaoEntrevistado {

    private long idEntrevistado;
    private long idQuestao;
    private int escore;

    public QuestaoEntrevistado() {}

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
