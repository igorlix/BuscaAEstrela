package upe.poli.games;
import upe.poli.games.grafo.Aresta;
import upe.poli.games.grafo.BuscaAEstrela;
import upe.poli.games.grafo.Grafo;
import upe.poli.games.grafo.Vertice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogador {
    private String nome;
    private double saldo;
    private Vertice estacaoAtual;
    private Vertice estacaoAnterior;
    private List<Aresta> arestasVisitadas;
    private List<Vertice> verticesVisitados;
    private Grafo grafo;
    private Vertice destinoFinal;

    private boolean revelar;


    public Jogador(String nome, double saldo, Grafo grafo, Vertice estacaoAtual, Vertice destinoFinal) {
        this.nome = nome;
        this.saldo = saldo;
        this.estacaoAtual = estacaoAtual;
        this.estacaoAnterior = null;
        this.destinoFinal = destinoFinal;
        this.arestasVisitadas = new ArrayList<>();
        this.verticesVisitados = new ArrayList<>();
        this.grafo = grafo;
        this.revelar = true;
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public Vertice getEstacaoAtual() {
        return estacaoAtual;
    }
    public Vertice getDestinoFinal() {
        return destinoFinal;
    }


    public void setRevelar(boolean revelar) {
        this.revelar = revelar;
    }

    public boolean getRevelar(){
        return revelar;
    }

    public void setEstacaoAnterior(Vertice estacaoAnterior) {
        this.estacaoAnterior = estacaoAnterior;
    }

    public void setEstacaoAtual(Vertice estacaoAtual) {
        this.estacaoAtual = estacaoAtual;
    }

    public void adicionarArestaVisitada(Aresta aresta) {
        this.arestasVisitadas.add(aresta);
    }

    public Grafo getGrafo() {
        return grafo;
    }

    public Vertice getEstacaoAnterior() {
        return estacaoAnterior;
    }

    public List<Aresta> getArestasVisitadas() {
        return arestasVisitadas;
    }

    public void adicionarVerticeVisitado(Vertice vertice) {
        this.verticesVisitados.add(vertice);
    }

    public void adicionarVerticesVisitados(List<Vertice> vertices) {
        this.verticesVisitados.addAll(vertices);
    }



    public List<Vertice> getVerticesVisitados() {
        return verticesVisitados;
    }


    public void moverEstacao(Vertice destino) {
        if (estacaoAtual == null || destino == null) {
            System.out.println("Não é possível mover para uma estação nula.");
            return;
        }


        if (estacaoAtual.equals(destino)) {
            System.out.println("Você já está na estação de destino!");
            return;
        }


        Aresta aresta = getGrafo().getAresta(estacaoAtual, destino);

        if (aresta == null) {
            System.out.println("Não é possível mover para esta estação diretamente.");
            return;
        }


        pagarPassagem(aresta.getPrecoPassagem());


        setEstacaoAnterior(estacaoAtual);
        setEstacaoAtual(destino);


        adicionarArestaVisitada(aresta);


        System.out.println("Você se moveu para a estação: " + destino.getNome());
    }

    public void pagarPassagem(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            System.out.println(nome + " pagou " + valor + " reais pela passagem.");
        } else {
            System.out.println(nome + " não possui saldo suficiente para pagar a passagem.");
            // Verifica se o saldo é igual a 0,1 após o pagamento.
            if (saldo + 0.1 >= valor) {
                saldo = 0.1; // Define o saldo como 0,1 reais.
                System.out.println(nome + " pagou com tudo que tinha e agora tem 0,1 reais.");
            } else {
                saldo = 0.0; // Define o saldo como 0,0 reais.
                System.out.println(nome + " ficou sem dinheiro!");
            }
        }
    }


    public void aumentarPrecoPassagem() {
        List<Aresta> arestas = grafo.getArestas();
        for (Aresta aresta : arestas) {
            Vertice origem = aresta.getOrigem();
            Vertice destino = aresta.getDestino();

            if (origem.equals(estacaoAtual) || destino.equals(estacaoAtual)) {
                double novoPrecoPassagem = aresta.getPrecoPassagem() * 1.1;
                aresta.setPrecoPassagem(novoPrecoPassagem);
            }
        }
    }

    public void descontarPrecoPassagem() {
        List<Aresta> arestas = grafo.getArestas();
        for (Aresta aresta : arestas) {
            Vertice origem = aresta.getOrigem();
            Vertice destino = aresta.getDestino();

            if (origem.equals(estacaoAnterior) || destino.equals(estacaoAtual)) {
                saldo += aresta.getPrecoPassagem();
            }
        }
    }

    public void voltarEstacao() {
        if (estacaoAnterior != null) {
            System.out.println(nome + " voltou para a estação anterior: " + estacaoAnterior.getNome());
            estacaoAtual = estacaoAnterior;
            estacaoAnterior = null;
        } else {
            System.out.println(nome + " não pode voltar para a estação anterior.");
        }
    }

    public void voltarEstacoes(int quantidade) {
        int tamanhoCaminho = arestasVisitadas.size();
        if (quantidade <= tamanhoCaminho) {
            for (int i = 0; i < quantidade; i++) {
                Aresta aresta = arestasVisitadas.remove(tamanhoCaminho - 1);
                estacaoAnterior = estacaoAtual;
                estacaoAtual = aresta.getOrigem();
                saldo -= aresta.getPrecoPassagem();
                System.out.println(nome + " voltou para a estação anterior: " + estacaoAtual.getNome());
            }
        } else {
            System.out.println(nome + " não pode voltar " + quantidade + " estações.");
        }
    }

    public void receberDinheiro(double valor) {
        saldo += valor;
        System.out.println(nome + " recebeu " + valor + " reais.");
    }

    public void perderDinheiro(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            System.out.println(nome + " perdeu " + valor + " reais.");
        } else {
            System.out.println(nome + " não possui saldo suficiente para perder " + valor + " reais.");
        }
    }
    public void pularEstacoes(Grafo grafo, Vertice destino) {
        // Verifica se o jogador já está no destino.
        if (estacaoAtual.equals(destino)) {
            System.out.println("Você já está na estação de destino!");
            return;
        }

        // Executa a busca A* para encontrar o caminho mais curto até o destino.
        BuscaAEstrela buscaAEstrela = new BuscaAEstrela(grafo);
        List<Vertice> caminho = buscaAEstrela.buscarCaminho(estacaoAtual, destino);

        // Verifica se um caminho foi encontrado.
        if (caminho == null || caminho.size() <= 2) {
            System.out.println("Não foi possível encontrar um caminho para pular duas estações.");
            return;
        }

        // Move o jogador duas estações mais perto do destino.
        Vertice estacaoPulada = caminho.get(2);
        System.out.println("Você pulou duas estações à frente para: " + estacaoPulada.getNome());
        setEstacaoAnterior(estacaoAtual);
        setEstacaoAtual(estacaoPulada);
        adicionarArestaVisitada(grafo.getAresta(estacaoAtual, estacaoPulada));
    }



    public void moverParaEstacaoAleatoria() {
        Random random = new Random();
        List<Vertice> vizinhos = grafo.getVizinhos(estacaoAtual);
        if (!vizinhos.isEmpty()) {
            int indiceEstacaoAleatoria = random.nextInt(vizinhos.size());
            Vertice estacaoAleatoria = vizinhos.get(indiceEstacaoAleatoria);
            Aresta aresta = grafo.getAresta(estacaoAtual, estacaoAleatoria);
            if (aresta != null) {
                estacaoAnterior = estacaoAtual;
                estacaoAtual = estacaoAleatoria;
                arestasVisitadas.add(aresta);
                pagarPassagem(aresta.getPrecoPassagem());
                System.out.println(nome + " foi para a estação " + estacaoAtual.getNome());
            }
        }
    }
}