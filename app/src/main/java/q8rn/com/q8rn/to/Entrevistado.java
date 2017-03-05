package q8rn.com.q8rn.to;

/**
 * Created by Gabriel on 04/03/2017.
 */

public class Entrevistado {

    private transient long id;
    private String codIdentificacao;
    private int idade;
    private String sexo;
    private String corPele;
    private String religiao;
    private String tempoReligiao;
    private String profissao;
    private String escolaridade;
    private double peso;
    private double altura;
    private double imc;
    private double cinturaQuadril;
    private double pa;
    private double glicemiaCapilar;
    private int espirometria;
    private String saudeFisica;
    private String saudeMental;
    private String doencas;
    private transient Boolean flgAtivo = true;

    public Entrevistado(String codIdentificacao, int idade, String sexo, String corPele,
                        String religiao, String tempoReligiao, String profissao,
                        String escolaridade, double peso, double altura, double imc,
                        double cinturaQuadril, double pa, double glicemiaCapilar, int espirometria,
                        String saudeFisica, String saudeMental, String doencas) {
        this.codIdentificacao = codIdentificacao;
        this.idade = idade;
        this.sexo = sexo;
        this.corPele = corPele;
        this.religiao = religiao;
        this.tempoReligiao = tempoReligiao;
        this.profissao = profissao;
        this.escolaridade = escolaridade;
        this.peso = peso;
        this.altura = altura;
        this.imc = imc;
        this.cinturaQuadril = cinturaQuadril;
        this.pa = pa;
        this.glicemiaCapilar = glicemiaCapilar;
        this.espirometria = espirometria;
        this.saudeFisica = saudeFisica;
        this.saudeMental = saudeMental;
        this.doencas = doencas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodIdentificacao() {
        return codIdentificacao;
    }

    public void setCodIdentificacao(String codIdentificacao) {
        this.codIdentificacao = codIdentificacao;
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

    public String getTempoReligiao() {
        return tempoReligiao;
    }

    public void setTempoReligiao(String tempoReligiao) {
        this.tempoReligiao = tempoReligiao;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
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

    public double getPa() {
        return pa;
    }

    public void setPa(double pa) {
        this.pa = pa;
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

    public Boolean getFlgAtivo() {
        return flgAtivo;
    }

    public void setFlgAtivo(Boolean flgAtivo) {
        this.flgAtivo = flgAtivo;
    }
}
