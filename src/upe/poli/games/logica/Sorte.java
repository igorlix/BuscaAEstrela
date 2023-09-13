package upe.poli.games.logica;
import upe.poli.games.Jogador;

import java.util.Random;

public class Sorte {
    private Random random = new Random();

    public void aplicarSorte(Jogador jogador) {
        int casoSorte = random.nextInt(100); // Gera um número aleatório de 0 a 99.

        if (casoSorte < 23) { // 23% de chance

            System.out.println("Sorte: Sua passagem anterior foi gratuita!");
            jogador.setRevelar(true);
            jogador.descontarPrecoPassagem();
        } else if (casoSorte < 46) { // 23% de chance
            // Caso 2: Pode voltar para a estação anterior gratuitamente
            jogador.setRevelar(true);
            jogador.voltarEstacao();
            System.out.println("Sorte: Você pode voltar para a estação anterior gratuitamente!");
        } else if (casoSorte < 69) { // 23% de chance
            // Caso 3: Recebe 5 reais
            jogador.setRevelar(true);
            jogador.receberDinheiro(5);
            System.out.println("Sorte: Você recebeu 5 reais!");
        } else if (casoSorte < 92) { // 23% de chance

            jogador.setRevelar(true);
            System.out.println("Sorte: Dica de qual a melhor estação a seguir!");
        } else {
            // Caso 5: Pule duas estações a frente
            jogador.setRevelar(true);
            jogador.pularEstacoes(jogador.getGrafo(), jogador.getDestinoFinal());
            System.out.println("Sorte: Você pulou duas estações à frente!");
        }
    }
}
