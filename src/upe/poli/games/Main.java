package upe.poli.games;

import upe.poli.games.GUI.GrafoGUI;
import upe.poli.games.GUI.JogoGUI;
import upe.poli.games.grafo.BuscaAEstrela;
import upe.poli.games.grafo.Grafo;
import upe.poli.games.grafo.Vertice;

import javax.swing.*;
import java.util.List;
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

        //JogoGUI jogoGUI = new JogoGUI(jogador);


        JFrame frame = new JFrame("Grafo do Metrô");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GrafoGUI grafoGUI = new GrafoGUI(grafo.getVertices(), grafo.getDistanciaReal(),buscaAEstrela.buscarCaminho(jogador.getEstacaoAtual(), jogador.getDestinoFinal()));
        frame.add(grafoGUI);
        frame.setSize(800, 600);
        frame.setVisible(true);

        System.out.println("Melhor caminho:");
        for (Vertice vertice : buscaAEstrela.buscarCaminho(jogador.getEstacaoAtual(), jogador.getDestinoFinal())) {
            System.out.println(vertice.getNome());
        }

        Jogo jogo = new Jogo(jogador);
        jogo.start();


        System.out.println("Jogo encerrado. Obrigado por jogar!");
        scanner.close();
    }
}