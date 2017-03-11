package q8rn.com.q8rn.model;
/* Created by Gabriel on 09/03/2017. */

import android.content.Context;

import q8rn.com.q8rn.controllers.QuestaoController;

public class PopulaBanco {

    public static final String SEMPRE = "Sempre";
    public static final String COM_RELATIVA_FREQUÊNCIA = "Com relativa frequência";
    public static final String ALGUMAS_VEZES = "Algumas vezes";
    public static final String RARAMENTE = "Raramente";
    public static final String QUASE_NUNCA = "Quase nunca";
    public static final String NUTRIÇÃO = "Nutrição";
    public static final String NENHUM = "Nenhum";
    public static final String UM_ITEM = "Um item";
    public static final String DOIS_ITENS = "Dois itens";
    public static final String TRÊS_ITENS = "Três itens";
    public static final String QUATRO_A_CINCO_ITENS = "Quatro a cinco itens";
    public static final String EXERCÍCIO = "Exercício";
    public static final String AGUA = "Água";
    public static final String NUNCA = "Nunca";
    public static final String SOL = "Sol";
    public static final String TEMPERANCA = "Temperança";
    public static final String AR_PURO = "Ar puro";
    public static final String DESCANSO = "Descanso";
    public static final String CONFIANCA = "Confiança";
    private QuestaoController controller;

    public PopulaBanco(Context context) {
        this.controller = new QuestaoController(context);
    }

    public void popularQuestoes() {
        deletarAll();
        inserirAll();
    }

    public void inserirAll() {
        controller.insereDados(1,"Com que frequência você inclui em suas refeições diária todos " +
                        "esses alimentos: feijões, castanhas, frutas, legumes e verduras?",
                SEMPRE, COM_RELATIVA_FREQUÊNCIA, ALGUMAS_VEZES,
                RARAMENTE, QUASE_NUNCA, NUTRIÇÃO);

        controller.insereDados(2,"Quantas refeições você faz por dia? (Desjejum, almoço, jantar, lanches, ceia)",
                "2 a 3", null, null, null, "4 ou mais", NUTRIÇÃO);

        controller.insereDados(3,"Além de consumir outros alimentos do dia a dia como: feijão, " +
                "arroz, massa, frutas, legumes e verduras, como você classifica o seu padrão " +
                "alimentar/dietético*, de acordo com a frequência de consumo dos alimentos de " +
                "origem animal?", "Vegetariano estrito come laticínios, ovos, carne vermelha, " +
                "aves e peixes, menos que 1 vez por mês", "Ovo lacto, Vegetariano, " +
                "Come Laticínios e ovos mais que 1 vez por mês e carne, " +
                "aves e peixes, menos que 1 vez por mês", "Pesco, Vegetariano, " +
                "Come carne vermelha e aves menos que 1 vez por mês e peixes, " +
                "mais que 1 vez por mês", "Semi Vegetariano, Come carne vermelha, " +
                "aves e peixes, menos de 1 vez por semana", "Não vegetariano, " +
                "Come carne vermelha, aves e peixes 1 vez ou mais por semana", NUTRIÇÃO);

        controller.insereDados(4, "Dos itens a seguir: salgadinhos, bolachas, frituras, " +
                "refrigerantes, doces; quantos deles você consome com relativa frequência?",
                NENHUM, UM_ITEM, DOIS_ITENS, TRÊS_ITENS, QUATRO_A_CINCO_ITENS, NUTRIÇÃO);

        controller.insereDados(5, "Você pratica atividades de lazer: caminhar, pedalar, " +
                "jogar bola, esportes radicais ou outros hobbies e outras atividades do dia a dia.",
                SEMPRE, COM_RELATIVA_FREQUÊNCIA, ALGUMAS_VEZES, QUASE_NUNCA, NUNCA,
                EXERCÍCIO);

        controller.insereDados(6, "Quantas vezes por semana você pratica exercício físico vigoroso " +
                "(faz suar e aumentar os batimentos cardíacos): caminhada forçada, corrida, " +
                "bicicleta, etc?", "5 vezes ou mais por semana", "3-4 vezes por semana",
                "1-2 vezes por semana", "Menos de 1 vez por semana", NUNCA, EXERCÍCIO);

        controller.insereDados(7, "Quantos minutos você gasta em média, em cada sessão de " +
                "exercício vigoroso?", "Acima de 30 minutos", "21 a 30 minutos", "10-20" +
                "minutos", "Menos de 10 minutos", NUNCA, EXERCÍCIO);

        controller.insereDados(8, "Quantos copos de água, em média, você bebe diariamente?",
                "8 ou mais", "7 copos", "4-6 copos", "1-3 copos", NENHUM, AGUA);

        controller.insereDados(9, "Você utiliza a água para tratamentos? (Exemplo: " +
                "compressas quentes e frias, aplicação de gelo, inalação, banhos em geral). ",
                SEMPRE, COM_RELATIVA_FREQUÊNCIA, ALGUMAS_VEZES, QUASE_NUNCA, NUNCA, AGUA);

        controller.insereDados(10, "Com que frequência você se expõe ao sol durante 15 a 20 " +
                "minutos/dia?",SEMPRE, COM_RELATIVA_FREQUÊNCIA, ALGUMAS_VEZES, QUASE_NUNCA, NUNCA,
                SOL);

        controller.insereDados(11, "Você mantém diariamente as janelas abertas da casa para " +
                "entrada de luz natural? ",SEMPRE, COM_RELATIVA_FREQUÊNCIA, ALGUMAS_VEZES,
                QUASE_NUNCA, NUNCA, SOL);

        controller.insereDados(12, "Você se considera equilibrado ao tempo dedicado para estudos, " +
                "trabalho, uso de tecnologia, alimentação, etc?", "Em todos os aspectos",
                "Em quase todos os aspectos ", "Em alguns aspectos", "Quase nenhum",
                "Em nenhum aspecto", TEMPERANCA);

        controller.insereDados(13, "Você ingere bebida alcóolica (cerveja, vinho e bebidas " +
                "alcóolicas destiladas)?", NUNCA, null, null, null, ALGUMAS_VEZES, TEMPERANCA);

        controller.insereDados(14, "Você fuma cigarros, fumo de rolo ou charuto?", NUNCA, null,
                null, null, ALGUMAS_VEZES, TEMPERANCA);

        controller.insereDados(15, "Você faz uso de drogas como: maconha, crack, cocaína etc?",
                NUNCA, null, null, null, ALGUMAS_VEZES, TEMPERANCA);

        controller.insereDados(16, "Você ingere bebidas que contém cafeína? (café, chá preto, " +
                "chá verde, chá mate, chá branco ou refrigerantes de “cola”)", NUNCA, null, null,
                null, ALGUMAS_VEZES, TEMPERANCA);

        controller.insereDados(17, "Considerando os ambientes em que você vive, como classifica " +
                "a qualidade do ar que respira regularmente?", "Muito boa", "Boa", "Regular",
                "Ruim", "Muito ruim", AR_PURO);

        controller.insereDados(18, "Você pratica a respiração profunda em ambientes arejados?",
                SEMPRE, COM_RELATIVA_FREQUÊNCIA, ALGUMAS_VEZES, RARAMENTE, NUNCA, AR_PURO);

        controller.insereDados(19, "Você dorme entre 7 a 8h por noite (em média)?",
                SEMPRE, COM_RELATIVA_FREQUÊNCIA, ALGUMAS_VEZES, RARAMENTE, NUNCA, DESCANSO);

        controller.insereDados(20, "Você costuma dormir cedo? (por volta das 22h).",
                SEMPRE, COM_RELATIVA_FREQUÊNCIA, ALGUMAS_VEZES, RARAMENTE, NUNCA, DESCANSO);

        controller.insereDados(21, "Você separa um dia na semana para descansar?",
                SEMPRE, COM_RELATIVA_FREQUÊNCIA, ALGUMAS_VEZES, RARAMENTE, NUNCA, DESCANSO);

        controller.insereDados(22, "Você confia em Deus? (ou Ser Superior ou algo sagrado)",
                SEMPRE, COM_RELATIVA_FREQUÊNCIA, ALGUMAS_VEZES, RARAMENTE, NUNCA, CONFIANCA);

        controller.insereDados(23, "Sua confiança em Deus (Ser Superior ou algo sagrado) " +
                "influencia positivamente na sua maneira de viver?", SEMPRE, COM_RELATIVA_FREQUÊNCIA,
                ALGUMAS_VEZES, RARAMENTE, NUNCA, CONFIANCA);

        controller.insereDados(24, "Com que frequência você participa de reuniões religiosas ou " +
                "espirituais?", "Mais que uma vez por semana", "Uma vez por semana",
                "Duas a três vezes por mês", "Algumas vezes por ano", "Raramente ou nunca",
                CONFIANCA);

        controller.insereDados(25, "Você pratica as atividades religiosas ou espirituais em " +
                "particular como: meditar, rezar ou orar, ler a Bíblia ou livros religiosos, etc.",
                "Mais que uma vez ao dia", "Uma vez ao dia", "Duas ou mais vezes por semana",
                "Poucas vezes por mês", "Raramente ou nunca", CONFIANCA);
    }

    public void deletarAll() {
        controller.deletaAllQuestoes();
    }
}
