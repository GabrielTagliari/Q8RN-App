package q8rn.com.q8rn.entity;

import android.os.Parcel;
import android.os.Parcelable;

import q8rn.com.q8rn.infrastructure.Constants;

/* Created by Gabriel on 04/03/2017. */
public class Entrevistado implements Parcelable{

    private transient long id;
    private String iniciaisNome;
    private int idade;
    private String sexo;
    private String corPele;
    private String religiao;
    private int tempoReligiao;
    private String profissao;
    private int escolaridade;
    private double peso;
    private double altura;
    private double imc;
    private double cinturaQuadril;
    private double pas;
    private double pad;
    private double glicemiaCapilar;
    private int espirometria;
    private String saudeFisica;
    private String saudeMental;
    private String doencas;

    // Pos alteracao no formulario
    private String codIdentificacao;
    private double cintura;
    private double quadril;
    private double testeEsforcoAntes;
    private double testeEsforcoDepois;
    private String qualidadeVida;
    private String oQueMelhorar;
    private double cinturaEstatura;

    private String estadoCivil;
    private String comQuemMora;

    public Entrevistado() {}

    private Entrevistado(Parcel in) {
        iniciaisNome = in.readString();
        idade = in.readInt();
        sexo = in.readString();
        corPele = in.readString();
        religiao = in.readString();
        tempoReligiao = in.readInt();
        profissao = in.readString();
        escolaridade = in.readInt();
        peso = in.readDouble();
        altura = in.readDouble();
        imc = in.readDouble();
        cinturaQuadril = in.readDouble();
        pas = in.readDouble();
        pad = in.readDouble();
        glicemiaCapilar = in.readDouble();
        espirometria = in.readInt();
        saudeFisica = in.readString();
        saudeMental = in.readString();
        doencas = in.readString();
        // Pos alteracao no formulario
        codIdentificacao = in.readString();
        cintura = in.readDouble();
        quadril = in.readDouble();
        testeEsforcoAntes = in.readDouble();
        testeEsforcoDepois = in.readDouble();
        qualidadeVida = in.readString();
        oQueMelhorar = in.readString();
        cinturaEstatura = in.readDouble();
        estadoCivil = in.readString();
        comQuemMora = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iniciaisNome);
        dest.writeInt(idade);
        dest.writeString(sexo);
        dest.writeString(corPele);
        dest.writeString(religiao);
        dest.writeInt(tempoReligiao);
        dest.writeString(profissao);
        dest.writeInt(escolaridade);
        dest.writeDouble(peso);
        dest.writeDouble(altura);
        dest.writeDouble(imc);
        dest.writeDouble(cinturaQuadril);
        dest.writeDouble(pas);
        dest.writeDouble(pad);
        dest.writeDouble(glicemiaCapilar);
        dest.writeInt(espirometria);
        dest.writeString(saudeFisica);
        dest.writeString(saudeMental);
        dest.writeString(doencas);
        // Pos alteracao no formulario
        dest.writeString(codIdentificacao);
        dest.writeDouble(cintura);
        dest.writeDouble(quadril);
        dest.writeDouble(testeEsforcoAntes);
        dest.writeDouble(testeEsforcoDepois);
        dest.writeString(qualidadeVida);
        dest.writeString(oQueMelhorar);
        dest.writeDouble(cinturaEstatura);
        dest.writeString(estadoCivil);
        dest.writeString(comQuemMora);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Entrevistado> CREATOR = new Creator<Entrevistado>() {
        @Override
        public Entrevistado createFromParcel(Parcel in) {
            return new Entrevistado(in);
        }

        @Override
        public Entrevistado[] newArray(int size) {
            return new Entrevistado[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIniciaisNome() {
        return iniciaisNome;
    }

    public void setIniciaisNome(String iniciaisNome) {
        this.iniciaisNome = iniciaisNome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCorPele() {
        return corPele;
    }

    public void setCorPele(String corPele) {
        this.corPele = corPele;
    }

    public String getReligiao() {
        return religiao;
    }

    public void setReligiao(String religiao) {
        this.religiao = religiao;
    }

    public int getTempoReligiao() {
        return tempoReligiao;
    }

    public void setTempoReligiao(int tempoReligiao) {
        this.tempoReligiao = tempoReligiao;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public int getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(int escolaridade) {
        this.escolaridade = escolaridade;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public double getCinturaQuadril() {
        return cinturaQuadril;
    }

    public void setCinturaQuadril(double cinturaQuadril) {
        this.cinturaQuadril = cinturaQuadril;
    }

    public double getPas() {
        return pas;
    }

    public void setPas(double pas) {
        this.pas = pas;
    }

    public double getPad() {
        return pad;
    }

    public void setPad(double pad) {
        this.pad = pad;
    }

    public double getGlicemiaCapilar() {
        return glicemiaCapilar;
    }

    public void setGlicemiaCapilar(double glicemiaCapilar) {
        this.glicemiaCapilar = glicemiaCapilar;
    }

    public int getEspirometria() {
        return espirometria;
    }

    public void setEspirometria(int espirometria) {
        this.espirometria = espirometria;
    }

    public String getSaudeFisica() {
        return saudeFisica;
    }

    public void setSaudeFisica(String saudeFisica) {
        this.saudeFisica = saudeFisica;
    }

    public String getSaudeMental() {
        return saudeMental;
    }

    public void setSaudeMental(String saudeMental) {
        this.saudeMental = saudeMental;
    }

    public String getDoencas() {
        return doencas;
    }

    public void setDoencas(String doencas) {
        this.doencas = doencas;
    }

    public String getCodIdentificacao() {
        return codIdentificacao;
    }

    public void setCodIdentificacao(String codIdentificacao) {
        this.codIdentificacao = codIdentificacao;
    }

    public double getCintura() {
        return cintura;
    }

    public void setCintura(double cintura) {
        this.cintura = cintura;
    }

    public double getQuadril() {
        return quadril;
    }

    public void setQuadril(double quadril) {
        this.quadril = quadril;
    }

    public double getTesteEsforcoAntes() {
        return testeEsforcoAntes;
    }

    public void setTesteEsforcoAntes(double testeEsforcoAntes) {
        this.testeEsforcoAntes = testeEsforcoAntes;
    }

    public double getTesteEsforcoDepois() {
        return testeEsforcoDepois;
    }

    public void setTesteEsforcoDepois(double testeEsforcoDepois) {
        this.testeEsforcoDepois = testeEsforcoDepois;
    }

    public String getQualidadeVida() {
        return qualidadeVida;
    }

    public void setQualidadeVida(String qualidadeVida) {
        this.qualidadeVida = qualidadeVida;
    }

    public String getoQueMelhorar() {
        return oQueMelhorar;
    }

    public void setoQueMelhorar(String oQueMelhorar) {
        this.oQueMelhorar = oQueMelhorar;
    }

    public double getCinturaEstatura() {
        return cinturaEstatura;
    }

    public void setCinturaEstatura(double cinturaEstatura) {
        this.cinturaEstatura = cinturaEstatura;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getComQuemMora() {
        return comQuemMora;
    }

    public void setComQuemMora(String comQuemMora) {
        this.comQuemMora = comQuemMora;
    }

    public int getCodEscolaridade(String escolaridade){
        switch (escolaridade){
            case Constants.SIOUFI:
                return 1;
            case Constants.FCOUEI:
                return 2;
            case Constants.EMOUSI:
                return 3;
            case Constants.SC:
                return 4;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Entrevistado{" +
                "id=" + id +
                ", iniciaisNome='" + iniciaisNome + '\'' +
                ", idade=" + idade +
                ", sexo='" + sexo + '\'' +
                ", corPele='" + corPele + '\'' +
                ", religiao='" + religiao + '\'' +
                ", tempoReligiao=" + tempoReligiao +
                ", profissao='" + profissao + '\'' +
                ", escolaridade=" + escolaridade +
                ", peso=" + peso +
                ", altura=" + altura +
                ", imc=" + imc +
                ", cinturaQuadril=" + cinturaQuadril +
                ", pas=" + pas +
                ", pad=" + pad +
                ", glicemiaCapilar=" + glicemiaCapilar +
                ", espirometria=" + espirometria +
                ", saudeFisica='" + saudeFisica + '\'' +
                ", saudeMental='" + saudeMental + '\'' +
                ", doencas='" + doencas + '\'' +
                ", codIdentificacao='" + codIdentificacao + '\'' +
                ", cintura=" + cintura +
                ", quadril=" + quadril +
                ", testeEsforcoAntes=" + testeEsforcoAntes +
                ", testeEsforcoDepois=" + testeEsforcoDepois +
                ", qualidadeVida='" + qualidadeVida + '\'' +
                ", oQueMelhorar='" + oQueMelhorar + '\'' +
                ", cinturaEstatura=" + cinturaEstatura +
                ", estadoCivil='" + estadoCivil + '\'' +
                ", comQuemMora='" + comQuemMora + '\'' +
                '}';
    }

    public Double getIMC() {
        return this.peso / (this.altura * this.altura);
    }
}
