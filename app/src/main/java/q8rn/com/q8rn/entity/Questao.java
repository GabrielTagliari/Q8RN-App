package q8rn.com.q8rn.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/* Created by Gabriel on 05/03/2017. */

public class Questao implements Parcelable {

    private long id;
    private long codQuestao;
    private String titulo;
    private String alternativa1;
    private String alternativa2;
    private String alternativa3;
    private String alternativa4;
    private String alternativa5;
    private String dominio;

    public Questao() {}

    public Questao(JSONObject jsonObject) throws JSONException, ParseException {
        this.id = jsonObject.getLong("id");
        this.codQuestao = jsonObject.getLong("codQuestao");
        this.titulo = jsonObject.getString("titulo");
        this.alternativa1 = jsonObject.getString("alternativa1");
        this.alternativa2 = jsonObject.getString("alternativa2");
        this.alternativa3 = jsonObject.getString("alternativa3");
        this.alternativa4 = jsonObject.getString("alternativa4");
        this.alternativa5 = jsonObject.getString("alternativa5");
    }

    public Questao(Parcel in) {
        id = in.readLong();
        codQuestao = in.readLong();
        titulo = in.readString();
        alternativa1 = in.readString();
        alternativa2 = in.readString();
        alternativa3 = in.readString();
        alternativa4 = in.readString();
        alternativa5 = in.readString();
        dominio = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(codQuestao);
        dest.writeString(titulo);
        dest.writeString(alternativa1);
        dest.writeString(alternativa2);
        dest.writeString(alternativa3);
        dest.writeString(alternativa4);
        dest.writeString(alternativa5);
        dest.writeString(dominio);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Questao> CREATOR = new Creator<Questao>() {
        @Override
        public Questao createFromParcel(Parcel in) {
            return new Questao(in);
        }

        @Override
        public Questao[] newArray(int size) {
            return new Questao[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAlternativa1() {
        return alternativa1;
    }

    public void setAlternativa1(String alternativa1) {
        this.alternativa1 = alternativa1;
    }

    public String getAlternativa2() {
        return alternativa2;
    }

    public void setAlternativa2(String alternativa2) {
        this.alternativa2 = alternativa2;
    }

    public String getAlternativa3() {
        return alternativa3;
    }

    public void setAlternativa3(String alternativa3) {
        this.alternativa3 = alternativa3;
    }

    public String getAlternativa4() {
        return alternativa4;
    }

    public void setAlternativa4(String alternativa4) {
        this.alternativa4 = alternativa4;
    }

    public String getAlternativa5() {
        return alternativa5;
    }

    public void setAlternativa5(String alternativa5) {
        this.alternativa5 = alternativa5;
    }

    public long getCodQuestao() {
        return codQuestao;
    }

    public void setCodQuestao(long codQuestao) {
        this.codQuestao = codQuestao;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String retornaAlternativaByNumero(int num) {
        String alternativa = null;

        if (num == 0)
            return alternativa5;
        if (num == 1)
            return alternativa4;
        if (num == 2)
            return alternativa3;
        if (num == 3)
            return alternativa2;
        if (num == 4)
            return alternativa1;

        return alternativa;
    }
}
