package upe.poli.games;

import upe.poli.games.grafo.BuscaAEstrela;
import upe.poli.games.grafo.Grafo;
import upe.poli.games.grafo.Vertice;
import upe.poli.games.logica.Reves;
import upe.poli.games.logica.Sorte;

import java.util.Random;
import java.util.Scanner;

public class Jogo {
    private Jogador jogador;
    private boolean gameOver;


    public Jogo(Jogador jogador) {
        this.jogador = jogador;
        gameOver = false;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);


        while (!gameOver) {
            System.out.println("* Destino Final: " + jogador.getDestinoFinal().getNome() + "   Distancia em linha reta: " + jogador.getGrafo().getDistanciaDireta(jogador.getEstacaoAtual(), jogador.getDestinoFinal()));
            System.out.println("* Estação Atual: " + jogador.getEstacaoAtual().getNome());
            System.out.println("* Saldo: " + jogador.getSaldo());
            System.out.println("............");

            System.out.println("Estações Vizinhas:");
            for (Vertice vizinho : jogador.getGrafo().getVizinhos(jogador.getEstacaoAtual())) {
                System.out.println(vizinho.getNome());
                if(jogador.getRevelar()){
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
                if(jogador.getDestinoFinal()==jogador.getEstacaoAtual()){
                    gameOver = true;
                }
                if(jogador.getSaldo()==0)
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

        if (chanceEvento < 50) { // 50% de chance de ocorrer evento de sorte ou revés.
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
