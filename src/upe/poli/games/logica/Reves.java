package upe.poli.games.logica;

import upe.poli.games.Jogador;
import upe.poli.games.grafo.Vertice;

import java.util.Random;

public class Reves {
    private Random random = new Random();

    public void aplicarReves(Jogador jogador) {
        int casoReves = random.nextInt(100); // Gera um número aleatório de 0 a 99.

        if (casoReves < 23) { // 23% de chance
            // Caso 1: Sua passagem anterior aumentou de preço misteriosamente
            System.out.println("Reves: Sua passagem anterior aumentou de preço misteriosamente!");
            jogador.setRevelar(true);
            jogador.aumentarPrecoPassagem();
            System.out.println("Você teve que pagar: "+ jogador.getGrafo().getAresta(jogador.getEstacaoAnterior(), jogador.getEstacaoAtual()).getPrecoPassagem()* 1.1);
        } else if (casoReves < 46) { // 23% de chance
            // Caso 2: Você deve voltar uma estação pagando
            System.out.println("Reves: Você esqueceu seu celular na estação anterior e teve que voltar!");
            jogador.setRevelar(true);
            jogador.moverEstacao(jogador.getEstacaoAnterior());
        } else if (casoReves < 69) { // 23% de chance
            // Caso 3: Você foi roubado
            System.out.println("Reves: Você foi roubado e perdeu 10 reais!");
            jogador.setRevelar(true);
            jogador.perderDinheiro(10);
        } else if (casoReves < 92) { // 23% de chance
            // Caso 4: Esconder preço e distância na próxima escolha de estação (implemente sua lógica)
            jogador.setRevelar(true);
            System.out.println("Reves: O preço e distância da próxima escolha de estação estão escondidos!");
        } else {
            // Caso 5: Volte duas estações
            jogador.voltarEstacoes(2);
            jogador.setRevelar(true);
            System.out.println("Reves: Você voltou duas estações para trás!");
        }

    }
}
