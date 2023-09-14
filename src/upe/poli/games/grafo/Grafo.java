package upe.poli.games.grafo;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
    private double[][] distanciaReal;
    private double[][] distanciaDireta;
    private List<Vertice> vertices;

    private List<Aresta> arestas;

    public Grafo() {
        this.distanciaReal = new double[14][14];
        this.distanciaDireta = new double[14][14];
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();


        // Tabela de distâncias diretas (linha reta)
        double[][] tabelaDistanciaDireta = {
                // E1  E2    E3     E4    E5   E6    E7    E8    E9   E10   E11   E12   E13  E14
                {0.0, 10.0, 18.5, 24.8, 36.4, 38.8, 35.8, 25.4, 17.6, 9.1, 16.7, 27.3, 27.6, 29.8},
                {10.0, 0.0, 8.5, 14.8, 26.6, 29.1, 26.1, 17.3, 10.0, 3.5, 15.5, 20.9, 19.1, 21.8},
                {18.5, 8.5, 0.0, 6.3, 18.2, 20.6, 17.6, 13.6, 9.4, 10.3, 19.5, 19.1, 12.1, 16.6},
                {24.8, 14.8, 6.3, 0.0, 12.0, 14.4, 11.5, 12.4, 12.6, 16.7, 23.6, 18.6, 10.6, 15.4},
                {36.4, 26.6, 18.2, 12.0, 0.0, 3.0, 2.4, 19.4, 23.3, 28.2, 34.2, 24.8, 14.5, 17.9},
                {38.8, 29.1, 20.6, 14.4, 3.0, 0.0, 3.3, 22.3, 25.7, 30.3, 36.7, 27.6, 15.2, 18.2},
                {35.8, 26.1, 17.6, 11.5, 2.4, 3.3, 0.0, 20.0, 23.0, 27.3, 34.2, 25.7, 12.4, 15.6},
                {25.4, 17.3, 13.6, 12.4, 19.4, 22.3, 20.0, 0.0, 8.2, 20.3, 16.1, 6.4, 22.7, 27.6},
                {17.6, 10.0, 9.4, 12.6, 23.3, 25.7, 23.0, 8.2, 0.0, 13.5, 11.2, 10.9, 21.2, 26.6},
                {9.1, 3.5, 10.3, 16.7, 28.2, 30.3, 27.3, 20.3, 13.5, 0.0, 17.6, 24.2, 18.7, 21.2},
                {16.7, 15.5, 19.5, 23.6, 34.2, 36.7, 34.2, 16.1, 11.2, 17.6, 0.0, 14.2, 31.5, 35.5},
                {27.3, 20.9, 19.1, 18.6, 24.8, 27.6, 25.7, 6.4, 10.9, 24.2, 14.2, 0.0, 28.8, 33.6},
                {27.6, 19.1, 12.1, 10.6, 14.5, 15.2, 12.4, 22.7, 21.2, 18.7, 31.5, 28.8, 0.0, 5.1},
                {29.8, 21.8, 16.6, 15.4, 17.9, 18.2, 15.6, 27.6, 26.6, 21.2, 35.5, 33.6, 5.1, 0.0}
        };

        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                distanciaDireta[i][j] = tabelaDistanciaDireta[i][j];
            }
        }

        // Tabela de distâncias reais
        double[][] tabelaDistanciaReal = {
                {0.0, 10.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {10.0, 0, 8.5, 0.0, 0.0, 0.0, 0.0, 0.0, 10.0, 3.5, 0.0, 0.0, 0.0, 0.0},
                {0.0,  8.5, 0, 6.3, 0.0, 0.0, 0.0, 0.0, 9.4, 0.0, 0.0, 0.0, 18.7, 0.0},
                {0.0, 0.0, 6.3, 0, 13.0, 0.0, 0.0, 15.3, 0.0, 0.0, 0.0, 0.0, 12.8, 0.0},
                {0.0, 0.0, 0.0, 13.0, 0, 3.0, 2.4, 30.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 3.0, 0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 2.4, 0.0, 0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 15.3, 30.0, 0.0, 0.0, 0, 9.6, 0.0, 0.0, 6.4, 0.0, 0.0},
                {0.0, 10.0, 9.4, 0.0, 0.0, 0.0, 0.0, 9.6, 0, 0.0, 12.2, 0.0, 0.0, 0.0},
                {0.0, 3.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 12.2, 0.0, 0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 6.4, 0.0, 0.0, 0, 0.0, 0.0},
                {0.0, 0.0, 18.7, 12.8, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 5.1},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 5.1, 0}
        };

        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                distanciaReal[i][j] = tabelaDistanciaReal[i][j];
            }
        }

        //instancias de vertices e arestas

        for (int i = 0; i < 14; i++) {
            Vertice vertice = new Vertice("E" + (i + 1), i);
            vertices.add(vertice);
        }


        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                if (i != j && distanciaReal[i][j] > 0) {
                    double precoPassagem = distanciaReal[i][j] / 2.0;
                    double distancia = tabelaDistanciaReal[i][j];
                    Aresta aresta = new Aresta(vertices.get(i), vertices.get(j), precoPassagem, distancia);
                    arestas.add(aresta);
                }
            }
        }
    }

    public double[][] getDistanciaDireta() {
        return distanciaDireta;
    }

    public double[][] getDistanciaReal() {
        return distanciaReal;
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public List<Aresta> getArestas() {
        return arestas;
    }

    public Vertice getVerticeIndice(int indice) {
        for (Vertice vertice : vertices) {
            if (vertice.getIndice() == indice) {
                return vertice;
            }
        }
        return null;
    }



    public List<Vertice> getVizinhos(Vertice estacaoAtual) {
        List<Vertice> vizinhos = new ArrayList<>();

        for (Aresta aresta : arestas) {
            if (aresta.getOrigem().equals(estacaoAtual)) {
                vizinhos.add(aresta.getDestino());
            } else if (aresta.getDestino().equals(estacaoAtual)) {
                vizinhos.add(aresta.getOrigem());
            }
        }

        return vizinhos;
    }

    public Aresta getAresta(Vertice estacao1, Vertice estacao2) {
        for (Aresta aresta : arestas) {
            if ((aresta.getOrigem().equals(estacao1) && aresta.getDestino().equals(estacao2)) ||
                    (aresta.getOrigem().equals(estacao2) && aresta.getDestino().equals(estacao1))) {
                return aresta;
            }
        }
        return null;
    }

    public double getDistanciaDireta(Vertice estacao1, Vertice estacao2) {
        int indiceEstacao1 = vertices.indexOf(estacao1);
        int indiceEstacao2 = vertices.indexOf(estacao2);

        if (indiceEstacao1 != -1 && indiceEstacao2 != -1) {
            return distanciaDireta[indiceEstacao1][indiceEstacao2];
        } else {
            return -1.0;
        }
    }

}
