package upe.poli.games;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.util.Set;

import upe.poli.games.GUI.GrafoGUI;
import upe.poli.games.grafo.BuscaAEstrela;
import upe.poli.games.grafo.Vertice;
import upe.poli.games.logica.Reves;
import upe.poli.games.logica.Sorte;

public class Jogo {
    private Jogador jogador;

    private boolean gameOver;

    public Jogo(Jogador jogador) {
        this.jogador = jogador;
        gameOver = false;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        JFrame jogoGUI = new JFrame("Jogo do Metrô");
        jogoGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jogoGUI.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        jogoGUI.setSize(800, 600);

        GrafoGUI grafoGUI = new GrafoGUI(jogador.getGrafo().getVertices(), jogador.getGrafo().getDistanciaReal(), null);


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(grafoGUI);

        jogoGUI.add(panel);

        jogoGUI.setVisible(true);

        while (!gameOver) {
            jogador.adicionarVerticeVisitado(jogador.getEstacaoAtual());
            jogador.adicionarVerticesVisitados(jogador.getGrafo().getVizinhos(jogador.getEstacaoAtual()));
            grafoGUI.setVerticesVisitados(jogador.getVerticesVisitados());
            grafoGUI.setEstacaoAtual(jogador.getEstacaoAtual());
            grafoGUI.adicionarUltimaEstacaoVisitada(jogador.getEstacaoAnterior());
            grafoGUI.repaint();

            System.out.println("* Destino Final: " + jogador.getDestinoFinal().getNome() + "   Distancia em linha reta: " + jogador.getGrafo().getDistanciaDireta(jogador.getEstacaoAtual(), jogador.getDestinoFinal()));
            System.out.println("* Estação Atual: " + jogador.getEstacaoAtual().getNome());
            System.out.println("* Saldo: " + jogador.getSaldo());
            System.out.println("............");

            Set<Vertice> vizinhosUnicos = new HashSet<>();

            for (Vertice vizinho : jogador.getGrafo().getVizinhos(jogador.getEstacaoAtual())) {
                vizinhosUnicos.add(vizinho);
            }

            System.out.println("Estações Vizinhas:");
            for (Vertice vizinho : vizinhosUnicos) {
                System.out.println(vizinho.getNome());
                if (jogador.getRevelar()) {
                    System.out.println("Preço:" + jogador.getGrafo().getAresta(vizinho,jogador.getEstacaoAtual()).getPrecoPassagem());
                    System.out.println("Distância:" + jogador.getGrafo().getAresta(vizinho,jogador.getEstacaoAtual()).getDistancia());
                }
            }

            System.out.print("Digite o nome da próxima estação: ");
            String nomeProximaEstacao = scanner.nextLine();
            Vertice proximaEstacao = encontrarEstacaoPorNome(nomeProximaEstacao);

            if (proximaEstacao != null) {
                jogador.moverEstacao(proximaEstacao);
                lidarComEventoSorteOuReves();
                if (jogador.getDestinoFinal() == jogador.getEstacaoAtual()) {
                    gameOver = true;
                }
                if (jogador.getSaldo() == 0)
                    gameOver = true;
            } else {
                System.out.println("Estação não encontrada.");
            }
        }

        scanner.close();
    }

    private Vertice encontrarEstacaoPorNome(String nomeEstacao) {
        for (Vertice vertice : jogador.getGrafo().getVertices()) {
            if (vertice.getNome().equalsIgnoreCase(nomeEstacao)) {
                return vertice;
            }
        }
        return null;
    }

    private void lidarComEventoSorteOuReves() {
        Random random = new Random();
        int chanceEvento = random.nextInt(100);

        if (chanceEvento < 50) {
            Sorte sorte = new Sorte();
            sorte.aplicarSorte(jogador);
        } else {
            Reves reves = new Reves();
            reves.aplicarReves(jogador);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Pressione Enter para continuar...");
        scanner.nextLine();
    }
}
