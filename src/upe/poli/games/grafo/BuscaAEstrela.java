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

    public List<Vertice> buscarCaminho(Vertice origem, Vertice destino, boolean usarDistanciaReal) {
        Map<Vertice, Double> custoG = new HashMap<>();
        Map<Vertice, Vertice> caminho = new HashMap<>();
        PriorityQueue<Vertice> filaPrioridade = new PriorityQueue<>((v1, v2) -> {
            double f1 = custoG.get(v1) + heuristica(v1, destino, usarDistanciaReal);
            double f2 = custoG.get(v2) + heuristica(v2, destino, usarDistanciaReal);
            return Double.compare(f1, f2);
        });

        // Inicialização
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

            for (Vertice vizinho : obterVizinhos(atual, usarDistanciaReal)) {
                double custoAtual = custoG.get(atual);
                double custoAresta = obterCustoAresta(atual, vizinho, usarDistanciaReal);
                double novoCusto = custoAtual + custoAresta;

                if (novoCusto < custoG.get(vizinho)) {
                    custoG.put(vizinho, novoCusto);
                    caminho.put(vizinho, atual);
                    filaPrioridade.add(vizinho);
                }
            }
        }

        return null; // Não foi encontrado um caminho
    }

    private List<Vertice> obterVizinhos(Vertice vertice, boolean usarDistanciaReal) {
        List<Vertice> vizinhos = new ArrayList<>();
        for (Aresta aresta : grafo.getArestas()) {
            if (aresta.getOrigem().equals(vertice)) {
                if (usarDistanciaReal && grafo.getDistanciaReal()[aresta.getOrigem().getIndice()][aresta.getDestino().getIndice()] > 0) {
                    vizinhos.add(aresta.getDestino());
                } else if (!usarDistanciaReal && grafo.getDistanciaDireta()[aresta.getOrigem().getIndice()][aresta.getDestino().getIndice()] > 0) {
                    vizinhos.add(aresta.getDestino());
                }
            }
        }
        return vizinhos;
    }

    private double obterCustoAresta(Vertice origem, Vertice destino, boolean usarDistanciaReal) {
        for (Aresta aresta : grafo.getArestas()) {
            if (aresta.getOrigem().equals(origem) && aresta.getDestino().equals(destino)) {
                return usarDistanciaReal ? grafo.getDistanciaReal()[origem.getIndice()][destino.getIndice()] : grafo.getDistanciaDireta()[origem.getIndice()][destino.getIndice()];
            }
        }
        return Double.POSITIVE_INFINITY;
    }

    private double heuristica(Vertice atual, Vertice destino, boolean usarDistanciaReal) {
        if (usarDistanciaReal) {
            return grafo.getDistanciaReal()[atual.getIndice()][destino.getIndice()];
        } else {
            return grafo.getDistanciaDireta()[atual.getIndice()][destino.getIndice()];
        }
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
        // Crie um mapa de mapeamento de estações iniciais para destinos.
        Map<Vertice, Vertice> mapeamentoEstacoes = new HashMap<>();
        List<Vertice> vertices = grafo.getVertices();

        // Defina o mapeamento com base na sua lógica.
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

        // Use o mapa para obter a estação de destino correspondente.
        return mapeamentoEstacoes.get(estacaoInicial);
    }

}