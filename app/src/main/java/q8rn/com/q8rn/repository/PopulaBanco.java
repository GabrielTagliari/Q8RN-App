package q8rn.com.q8rn.repository;
/* Created by Gabriel on 09/03/2017. */

import android.content.Context;

import q8rn.com.q8rn.manager.QuestaoManager;

public class PopulaBanco {

    private static final String SEMPRE = "Sempre";
    private static final String COM_RELATIVA_FREQUENCIA = "Com relativa frequência";
    private static final String ALGUMAS_VEZES = "Algumas vezes";
    private static final String RARAMENTE = "Raramente";
    private static final String QUASE_NUNCA = "Quase nunca";
    private static final String NUTRIÇÃO = "Nutrição";
    private static final String NENHUM = "Nenhum";
    private static final String UM_ITEM = "Um item";
    private static final String DOIS_ITENS = "Dois itens";
    private static final String TRÊS_ITENS = "Três itens";
    private static final String QUATRO_A_CINCO_ITENS = "Quatro a cinco itens";
    private static final String EXERCÍCIO = "Exercício";
    private static final String AGUA = "Água";
    private static final String NUNCA = "Nunca";
    private static final String SOL = "Sol";
    private static final String TEMPERANCA = "Temperança";
    private static final String AR_PURO = "Ar puro";
    private static final String DESCANSO = "Descanso";
    private static final String CONFIANCA = "Confiança";
    private static final String MUITAS_VEZES = "Muitas vezes";
    public static final String NÃO = "Não";
    public static final String SIM = "Sim";

    private QuestaoManager mQuestaoManager;

    public PopulaBanco(Context context) {
        this.mQuestaoManager = new QuestaoManager(context);
    }

    public void popularQuestoes() {
        deletarAll();
        inserirAll();
    }

    private void inserirAll() {
        mQuestaoManager.insereQuestao(1,"Com que frequência você inclui nas " +
                        "principais refeições do dia: feijões, cereais integrais, castanhas, frutas, " +
                        "legumes e verduras?", SEMPRE, MUITAS_VEZES, ALGUMAS_VEZES,
                RARAMENTE, QUASE_NUNCA, NUTRIÇÃO);

        mQuestaoManager.insereQuestao(2,"Quantas refeições você faz por dia? " +
                "(desjejum, almoço, jantar, lanche, etc.)", "2 a 3", null,
                null, null, "4 ou mais", NUTRIÇÃO);

        mQuestaoManager.insereQuestao(3,"Como você se classifica no que " +
                "se refere ao tipo de alimento que você mais consome?* Escolha a opção " +
                "mais adequada a seu caso.",
                "Vegetariano estrito: Consome leite, queijo, ovos, peixe ou carne no máximo 1 vez por mês ou menos",
                "Ovolacto Vegetariano: Come laticínios e ovos mais que 1 vez por mês e peixes e carnes menos que 1 vez por mês",
                "Pesco Vegetariano: Come carne, frango e outras aves menos de 1 vez por mês, e come peixe mais de 1 vez por mês",
                "Semi Vegetariano: Come carne de tipos variados no máximo 1 vez por semana",
                "Não vegetariano: Come carne de tipos variados mais de 1 vez por semana",
                NUTRIÇÃO);

        mQuestaoManager.insereQuestao(4, "Quantos dos itens a seguir você consome uma ou mais vezes por semana? (salgadinhos, bolachas, frituras, refrigerantes e doces de maneira geral)",
                NENHUM, UM_ITEM, DOIS_ITENS, TRÊS_ITENS, QUATRO_A_CINCO_ITENS, NUTRIÇÃO);

        mQuestaoManager.insereQuestao(5, "Você pratica atividades de lazer, tais como caminhar, pedalar, jogar bola, esportes radicais ou outros hobbies e atividades prazerosas?",
                SEMPRE, MUITAS_VEZES, ALGUMAS_VEZES, QUASE_NUNCA, NUNCA, EXERCÍCIO);

        mQuestaoManager.insereQuestao(6, "Quantas vezes por semana você pratica exercício físico intenso (que faz suar e aumentar os batimentos cardíacos, como caminhada longa, corrida, bicicleta, etc)?",
                "5 vezes ou mais por semana",
                "3 a 4 vezes por semana",
                "1 a 2 vezes por semana",
                "Menos de 1 vez por semana",
                NUNCA,
                EXERCÍCIO);

        mQuestaoManager.insereQuestao(7, "Quantos minutos você gasta “em média” quando faz exercícios intensos até suar? ",
                "30 a 60 minutos",
                "21 a 30 minutos",
                "10 a 20 minutos",
                "5 a 10 minutos",
                "Nenhum, não faço",
                EXERCÍCIO);

        mQuestaoManager.insereQuestao(8, "Quantos copos (250 ml) de água você bebe diariamente?",
                "8 ou mais", "7 copos", "4 a 6 copos", "1 a 3 copos", NENHUM, AGUA);

        mQuestaoManager.insereQuestao(9, "Você utiliza a água como remédio para tratamentos caseiros quando necessário? (Por exemplo, compressas quentes e frias, aplicação de gelo, inalação, escalda pés e banhos em geral).",
                SEMPRE, MUITAS_VEZES, ALGUMAS_VEZES, QUASE_NUNCA, NUNCA, AGUA);

        mQuestaoManager.insereQuestao(10, "Com que frequência você se expõe ao sol pelo menos 15 a 20 minutos por dia?",
                SEMPRE, MUITAS_VEZES, ALGUMAS_VEZES, QUASE_NUNCA, NUNCA, SOL);

        mQuestaoManager.insereQuestao(11, "Em sua casa, as janelas e persianas são abertas diariamente para que entrem sol e luz natural?",
                SEMPRE, MUITAS_VEZES, ALGUMAS_VEZES, QUASE_NUNCA, NUNCA, SOL);

        mQuestaoManager.insereQuestao(12, "Você se considera equilibrado quanto ao tempo dedicado aos estudos, trabalho, internet, televisão, refeições, amizades, sexualidade, etc.?",
                "Em todos esses itens",
                "Em quase todos esses itens",
                "Só em alguns desses itens",
                "Em quase nenhum desses itens",
                "Não, em nada disso",
                TEMPERANCA);

        mQuestaoManager.insereQuestao(13, "Você ingere bebida alcóolica (cerveja, vinho, licor, aguardente, pinga ou qualquer outra)? ",
                NÃO, null, null, null, SIM, TEMPERANCA);

        mQuestaoManager.insereQuestao(14, "Você fuma cigarro, charuto, cachimbo ou usa fumo de rolo?",
                NÃO, null,null, null, SIM, TEMPERANCA);

        mQuestaoManager.insereQuestao(15, "Você fez uso de alguma droga, tipo maconha, crack, cocaína, etc nos últimos três meses?",
                NÃO, null, null, null, SIM, TEMPERANCA);

        mQuestaoManager.insereQuestao(16, "Você ingere bebidas que contém cafeína? (café, chá preto, chá verde, chá mate, chá branco ou refrigerantes)",
                NÃO, null, null,null, SIM, TEMPERANCA);

        mQuestaoManager.insereQuestao(17, "Considerando os lugares onde passa a maior parte do tempo, como você classifica a qualidade do ar que respira?",
                "Muito boa qualidade",
                "Boa qualidade",
                "Regular",
                "Ruim",
                "Muito ruim",
                AR_PURO);

        mQuestaoManager.insereQuestao(18, "Você faz respiração profunda ao ar livre ou quando precisa controlar a tensão e a ansiedade?",
                SEMPRE, MUITAS_VEZES, ALGUMAS_VEZES, RARAMENTE, NUNCA, AR_PURO);

        mQuestaoManager.insereQuestao(19, "Você dorme de 7 a 8 horas por noite e acorda descansado(a) e com boa disposição na maioria das vezes?",
                SEMPRE, MUITAS_VEZES, ALGUMAS_VEZES, RARAMENTE, NUNCA, DESCANSO);

        mQuestaoManager.insereQuestao(20, "Você costuma dormir cedo? (por volta das 22h ou antes desse horário).",
                SEMPRE, MUITAS_VEZES, ALGUMAS_VEZES, RARAMENTE, NUNCA, DESCANSO);

        mQuestaoManager.insereQuestao(21, "Você separa um dia na semana para descansar das atividades rotineiras de trabalho, da casa ou dos estudos?",
                SEMPRE, MUITAS_VEZES, ALGUMAS_VEZES, RARAMENTE, NUNCA, DESCANSO);

        mQuestaoManager.insereQuestao(22, "Você confia em Deus? (em um Ser Superior ou algo sagrado)",
                SEMPRE, MUITAS_VEZES, ALGUMAS_VEZES, RARAMENTE, NUNCA, CONFIANCA);

        mQuestaoManager.insereQuestao(23, "Sua confiança em Deus (Ser Superior ou algo sagrado) influencia positivamente sua maneira de viver?",
                SEMPRE, MUITAS_VEZES, ALGUMAS_VEZES, RARAMENTE, NUNCA, CONFIANCA);

        mQuestaoManager.insereQuestao(24, "Com que frequência você participa de reuniões religiosas ou espirituais?",
                "Mais de 1 vez por semana",
                "Uma vez por semana",
                "Duas a três vezes por mês",
                "Algumas vezes por ano",
                "Raramente ou nunca",
                CONFIANCA);

        mQuestaoManager.insereQuestao(25, "Você pratica atividades religiosas ou espirituais em sua vida particular? (meditar, rezar ou orar, ler a Bíblia ou livros religiosos, fazer caridade, etc.).",
                "Mais de uma vez ao dia",
                "1 vez ao dia",
                "Duas ou mais vezes por semana",
                "Poucas vezes por mês",
                "Raramente ou nunca",
                CONFIANCA);
    }

    private void deletarAll() {
        mQuestaoManager.deletaAllQuestoes();
    }
}
