package upe.poli.games.grafo;


public class Vertice {
    private String nome;
    private int indice; // Adicione um campo para o índice

    public Vertice(String nome) {
        this.nome = nome;
        this.indice = indice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    @Override
    public String toString() {
        return nome;
    }
}
