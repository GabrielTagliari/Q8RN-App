package q8rn.com.q8rn.model;
/* Created by Gabriel on 09/03/2017. */

import android.content.Context;

import q8rn.com.q8rn.controllers.QuestaoController;

public class PopulaBanco {

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
                "Sempre", "Com relativa frequência", "Algumas vezes",
                "Raramente", "Quase nunca", 1);

        controller.insereDados(2,"Quantas refeições você faz por dia? (Desjejum, almoço, jantar, lanches, ceia)",
                "Sempre", "Com relativa frequência", "Algumas vezes",
                "Raramente", "Quase nunca", 1);

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
                "Come carne vermelha, aves e peixes 1 vez ou mais por semana", 1);
    }

    public void deletarAll() {
        controller.deletaAllQuestoes();
    }
}
