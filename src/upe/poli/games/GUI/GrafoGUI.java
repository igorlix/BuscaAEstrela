package upe.poli.games.GUI;

import upe.poli.games.grafo.Vertice;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GrafoGUI extends JPanel {
    private List<Vertice> vertices;
    private double[][] distanciaReal;
    private List<Vertice> melhorCaminho;
    private List<Vertice> verticesVisitados;
    private int margemEsquerda = 50;
    private int margemDireita = 50;
    private int margemSuperior = 50;
    private int margemInferior = 50;
    private Vertice estacaoAtual;
    private List<Vertice> ultimasEstacoesVisitadas = new ArrayList<>();

    public GrafoGUI(List<Vertice> vertices, double[][] distanciaReal, List<Vertice> melhorCaminho) {
        this.vertices = vertices;
        this.distanciaReal = distanciaReal;
        this.melhorCaminho = melhorCaminho;
    }

    public void setVerticesVisitados(List<Vertice> verticesVisitados) {
        this.verticesVisitados = verticesVisitados;
    }

    public void setEstacaoAtual(Vertice estacaoAtual) {
        this.estacaoAtual = estacaoAtual;
    }

    public void adicionarUltimaEstacaoVisitada(Vertice estacaoVisitada) {
        ultimasEstacoesVisitadas.add(estacaoVisitada);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int numEstacoes = vertices.size();
        int largura = getWidth() - margemEsquerda - margemDireita;
        int altura = getHeight() - margemSuperior - margemInferior;

        for (int i = 0; i < numEstacoes; i++) {
            double angulo = 2 * Math.PI * i / numEstacoes;
            int x = margemEsquerda + (int) (largura / 2 + largura / 2 * Math.cos(angulo));
            int y = margemSuperior + (int) (altura / 2 + altura / 2 * Math.sin(angulo));

            if (verticesVisitados != null && verticesVisitados.contains(vertices.get(i))) {
                Color verticeColor;
                if (vertices.get(i).equals(estacaoAtual)) {
                    verticeColor = Color.BLUE.darker();
                } else if (ultimasEstacoesVisitadas.contains(vertices.get(i))) {
                    verticeColor = Color.CYAN;
                } else {
                    verticeColor = Color.BLACK;
                }

                g.setColor(verticeColor);
                g.fillOval(x - 5, y - 5, 10, 10);
                g.setColor(Color.BLACK);
                g.drawString(vertices.get(i).getNome(), x - 10, y - 15);


                for (int j = 0; j < numEstacoes; j++) {
                    double distancia = distanciaReal[i][j];
                    if (distancia > 0 && verticesVisitados.contains(vertices.get(j))) {
                        double anguloI = 2 * Math.PI * i / numEstacoes;
                        double anguloJ = 2 * Math.PI * j / numEstacoes;
                        int xi = margemEsquerda + (int) (largura / 2 + largura / 2 * Math.cos(anguloI));
                        int yi = margemSuperior + (int) (altura / 2 + altura / 2 * Math.sin(anguloI));
                        int xj = margemEsquerda + (int) (largura / 2 + largura / 2 * Math.cos(anguloJ));
                        int yj = margemSuperior + (int) (altura / 2 + altura / 2 * Math.sin(anguloJ));

                        Color arestaColor = Color.BLACK;


                        if ((vertices.get(i).equals(estacaoAtual) || ultimasEstacoesVisitadas.contains(vertices.get(i))) &&
                                (vertices.get(j).equals(estacaoAtual) || ultimasEstacoesVisitadas.contains(vertices.get(j)))) {
                            arestaColor = Color.CYAN;
                        }

                        g.setColor(arestaColor);
                        g.drawLine(xi, yi, xj, yj);
                        g.setColor(Color.BLACK);
                        g.drawString(String.format("%.1f", distancia), (xi + xj) / 2, (yi + yj) / 2);
                    }
                }
            }
        }

        if (melhorCaminho != null && ultimasEstacoesVisitadas.isEmpty()) {
            desenharGrafoCompleto(g);
            g.setColor(Color.GREEN);
            for (int i = 0; i < melhorCaminho.size() - 1; i++) {
                Vertice v1 = melhorCaminho.get(i);
                Vertice v2 = melhorCaminho.get(i + 1);
                int xi = margemEsquerda + getXCoordenada(v1, largura);
                int yi = margemSuperior + getYCoordenada(v1, altura);
                int xj = margemEsquerda + getXCoordenada(v2, largura);
                int yj = margemSuperior + getYCoordenada(v2, altura);
                g.drawLine(xi, yi, xj, yj);
            }
        }
    }
    private void desenharGrafoCompleto(Graphics g) {
        int numEstacoes = vertices.size();
        int largura = getWidth() - margemEsquerda - margemDireita;
        int altura = getHeight() - margemSuperior - margemInferior;


        for (int i = 0; i < numEstacoes; i++) {
            for (int j = i + 1; j < numEstacoes; j++) {
                double distancia = distanciaReal[i][j];
                if (distancia > 0) {
                    double anguloI = 2 * Math.PI * i / numEstacoes;
                    double anguloJ = 2 * Math.PI * j / numEstacoes;
                    int xi = margemEsquerda + (int) (largura / 2 + largura / 2 * Math.cos(anguloI));
                    int yi = margemSuperior + (int) (altura / 2 + altura / 2 * Math.sin(anguloI));
                    int xj = margemEsquerda + (int) (largura / 2 + largura / 2 * Math.cos(anguloJ));
                    int yj = margemSuperior + (int) (altura / 2 + altura / 2 * Math.sin(anguloJ));

                    g.drawLine(xi, yi, xj, yj);
                    g.drawString(String.format("%.1f", distancia), (xi + xj) / 2, (yi + yj) / 2);
                }
            }
        }


        for (int i = 0; i < numEstacoes; i++) {
            double angulo = 2 * Math.PI * i / numEstacoes;
            int x = margemEsquerda + (int) (largura / 2 + largura / 2 * Math.cos(angulo));
            int y = margemSuperior + (int) (altura / 2 + altura / 2 * Math.sin(angulo));

            g.fillOval(x - 5, y - 5, 10, 10);
            g.drawString(vertices.get(i).getNome(), x - 10, y - 15);
        }
    }


    private int getXCoordenada(Vertice vertice, int largura) {
        double angulo = 2 * Math.PI * vertice.getIndice() / vertices.size();
        return (int) (largura / 2 + largura / 2 * Math.cos(angulo));
    }

    private int getYCoordenada(Vertice vertice, int altura) {
        double angulo = 2 * Math.PI * vertice.getIndice() / vertices.size();
        return (int) (altura / 2 + altura / 2 * Math.sin(angulo));
    }
}

