package q8rn.com.q8rn.to;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Gabriel on 05/03/2017.
 */

public class Questao implements Parcelable{

    private long id;
    private String titulo;
    private Date dtaAlteracao;
    private String alternativa1;
    private String alternativa2;
    private String alternativa3;
    private String alternativa4;
    private String alternativa5;

    protected Questao(Parcel in) {
        id = in.readLong();
        titulo = in.readString();
        alternativa1 = in.readString();
        alternativa2 = in.readString();
        alternativa3 = in.readString();
        alternativa4 = in.readString();
        alternativa5 = in.readString();
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

    public Date getDtaAlteracao() {
        return dtaAlteracao;
    }

    public void setDtaAlteracao(Date dtaAlteracao) {
        this.dtaAlteracao = dtaAlteracao;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(titulo);
        parcel.writeString(alternativa1);
        parcel.writeString(alternativa2);
        parcel.writeString(alternativa3);
        parcel.writeString(alternativa4);
        parcel.writeString(alternativa5);
    }
}
