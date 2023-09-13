package upe.poli.games;

import upe.poli.games.grafo.BuscaAEstrela;
import upe.poli.games.grafo.Grafo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Bem-vindo ao jogo do metrô!");
        System.out.print("Digite o nome do jogador: ");
        Scanner scanner = new Scanner(System.in);
        String nomeJogador = scanner.nextLine();

        System.out.print("Digite o número da estação: ");
        int i = scanner.nextInt()-1;

        Grafo grafo = new Grafo();
        BuscaAEstrela buscaAEstrela = new BuscaAEstrela(grafo);
        Jogador jogador = new Jogador(nomeJogador, 50, grafo, grafo.getVertices().get(i),buscaAEstrela.mapearEstacaoDestino(grafo.getVertices().get(i)) );
        JogoGUI jogoGUI = new JogoGUI(jogador);

        Jogo jogo = new Jogo(jogador);
        jogo.start();

        System.out.println("Jogo encerrado. Obrigado por jogar!");
        scanner.close();
    }
}