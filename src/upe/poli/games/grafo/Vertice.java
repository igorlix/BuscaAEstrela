package upe.poli.games.grafo;


public class Vertice {
    private String nome;
    private int indice;

    public Vertice(String nome, int indice) {
        this.nome = nome;
        this.indice = indice;
    }

    public String getNome() {
        return nome;
    }


    public int getIndice() {
        return indice;
    }


    @Override
    public String toString() {
        return nome;
    }
}
