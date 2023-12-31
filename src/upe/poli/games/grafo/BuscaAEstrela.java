package upe.poli.games.grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class BuscaAEstrela {
    private Grafo grafo;

    public BuscaAEstrela(Grafo grafo) {
        this.grafo = grafo;
    }

    public List<Vertice> buscarCaminho(Vertice origem, Vertice destino) {
        Map<Vertice, Double> custoG = new HashMap<>();
        Map<Vertice, Vertice> caminho = new HashMap<>();
        PriorityQueue<Vertice> filaPrioridade = new PriorityQueue<>((v1, v2) -> {
            double f1 = custoG.get(v1) + heuristica(v1, destino);
            double f2 = custoG.get(v2) + heuristica(v2, destino);
            return Double.compare(f1, f2);
        });

        
        for (Vertice vertice : grafo.getVertices()) {
            custoG.put(vertice, Double.POSITIVE_INFINITY);
        }

        custoG.put(origem, 0.0);
        filaPrioridade.add(origem);

        while (!filaPrioridade.isEmpty()) {
            Vertice atual = filaPrioridade.poll();

            if (atual.equals(destino)) {
                return reconstruirCaminho(caminho, destino);
            }

            for (Vertice vizinho : obterVizinhos(atual)) {
                double custoAtual = custoG.get(atual);
                double custoAresta = obterCustoAresta(atual, vizinho);
                double novoCusto = custoAtual + custoAresta;

                if (novoCusto < custoG.get(vizinho)) {
                    custoG.put(vizinho, novoCusto);
                    caminho.put(vizinho, atual);
                    filaPrioridade.add(vizinho);
                }
            }
        }

        return null;
    }

    private List<Vertice> obterVizinhos(Vertice vertice) {
        List<Vertice> vizinhos = new ArrayList<>();
        for (Aresta aresta : grafo.getArestas()) {
            if (aresta.getOrigem().equals(vertice)) {
                vizinhos.add(aresta.getDestino());
            }
        }
        return vizinhos;
    }

    private double obterCustoAresta(Vertice origem, Vertice destino) {
        for (Aresta aresta : grafo.getArestas()) {
            if (aresta.getOrigem().equals(origem) && aresta.getDestino().equals(destino)) {
                return grafo.getDistanciaReal()[origem.getIndice()][destino.getIndice()] > 0 ?
                        grafo.getDistanciaReal()[origem.getIndice()][destino.getIndice()] :
                        grafo.getDistanciaDireta()[origem.getIndice()][destino.getIndice()];
            }
        }
        return Double.POSITIVE_INFINITY;
    }

    private double heuristica(Vertice atual, Vertice destino) {
        double distanciaDireta = grafo.getDistanciaDireta()[atual.getIndice()][destino.getIndice()];
        return distanciaDireta;
    }

    private List<Vertice> reconstruirCaminho(Map<Vertice, Vertice> caminho, Vertice destino) {
        List<Vertice> caminhoReverso = new ArrayList<>();
        Vertice atual = destino;
        while (atual != null) {
            caminhoReverso.add(atual);
            atual = caminho.get(atual);
        }
        List<Vertice> caminhoFinal = new ArrayList<>();
        for (int i = caminhoReverso.size() - 1; i >= 0; i--) {
            caminhoFinal.add(caminhoReverso.get(i));
        }
        return caminhoFinal;
    }


    public Vertice mapearEstacaoDestino(Vertice estacaoInicial) {
        Map<Vertice, Vertice> mapeamentoEstacoes = new HashMap<>();
        List<Vertice> vertices = grafo.getVertices();

        mapeamentoEstacoes.put(vertices.get(0), vertices.get(5));
        mapeamentoEstacoes.put(vertices.get(1), vertices.get(6));
        mapeamentoEstacoes.put(vertices.get(2), vertices.get(5));
        mapeamentoEstacoes.put(vertices.get(3), vertices.get(10));
        mapeamentoEstacoes.put(vertices.get(4), vertices.get(10));
        mapeamentoEstacoes.put(vertices.get(5), vertices.get(9));
        mapeamentoEstacoes.put(vertices.get(6), vertices.get(8));
        mapeamentoEstacoes.put(vertices.get(7), vertices.get(13));
        mapeamentoEstacoes.put(vertices.get(8), vertices.get(6));
        mapeamentoEstacoes.put(vertices.get(9), vertices.get(11));
        mapeamentoEstacoes.put(vertices.get(10), vertices.get(13));
        mapeamentoEstacoes.put(vertices.get(11), vertices.get(0));
        mapeamentoEstacoes.put(vertices.get(12), vertices.get(10));
        mapeamentoEstacoes.put(vertices.get(13), vertices.get(5));

        return mapeamentoEstacoes.get(estacaoInicial);
    }

}