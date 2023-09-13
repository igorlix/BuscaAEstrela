package upe.poli.games.grafo;
public class Aresta {
    private Vertice origem;
    private Vertice destino;
    private double precoPassagem;
    private double distancia;
    private boolean visitada;

    public Aresta(Vertice origem, Vertice destino, double precoPassagem, double distancia) {
        this.origem = origem;
        this.destino = destino;
        this.precoPassagem = precoPassagem;
        this.distancia = distancia;
        this.visitada = false; // Inicialmente, a aresta não foi visitada.
    }

    public Vertice getOrigem() {
        return origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public double getPrecoPassagem() {
        return precoPassagem;
    }

    public void setPrecoPassagem(double precoPassagem) {
        this.precoPassagem = precoPassagem;
    }

    public double getDistancia() {
        return distancia;
    }

    public boolean foiVisitada() {
        return visitada;
    }

    public void marcarComoVisitada() {
        visitada = true;
    }
}
