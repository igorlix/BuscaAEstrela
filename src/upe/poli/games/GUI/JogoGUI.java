package upe.poli.games.GUI;

import upe.poli.games.Jogador;
import upe.poli.games.grafo.Vertice;
import upe.poli.games.logica.Reves;
import upe.poli.games.logica.Sorte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Random;

public class JogoGUI {
    private Jogador jogador;
    private JFrame frame;
    private JTextArea infoTextArea;
    private JPanel estacoesPanel;
    private JButton continuarButton;
    private ButtonGroup estacoesButtonGroup;
    private boolean revelar;

    public JogoGUI(Jogador jogador) {
        this.jogador = jogador;
        this.frame = new JFrame("Jogo do Metrô");
        this.infoTextArea = new JTextArea(10, 40);
        this.estacoesPanel = new JPanel();
        this.continuarButton = new JButton("Continuar");
        this.estacoesButtonGroup = new ButtonGroup();
        this.revelar = true;

        // Configurar a área de informações
        infoTextArea.setEditable(false);

        // Configurar o painel de estações com botões de rádio
        estacoesPanel.setLayout(new GridLayout(0, 1));
        exibirEstacoesVizinhas();

        // Configurar o botão "Continuar"
        continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String estacaoEscolhida = obterEstacaoEscolhida();
                if (estacaoEscolhida != null) {
                    Vertice proximaEstacao = encontrarEstacaoPorNome(estacaoEscolhida);
                    if (proximaEstacao != null) {
                        jogador.moverEstacao(proximaEstacao);
                        lidarComEventoSorteOuReves();
                        atualizarInfoTextArea();
                        if (jogador.getDestinoFinal() == jogador.getEstacaoAtual() || jogador.getSaldo() == 0) {
                            gameOver();
                        } else {
                            exibirEstacoesVizinhas();
                        }
                    }
                }
            }
        });

        // Configurar o layout do frame
        frame.setLayout(new BorderLayout());
        frame.add(infoTextArea, BorderLayout.NORTH);
        frame.add(estacoesPanel, BorderLayout.CENTER);
        frame.add(continuarButton, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        atualizarInfoTextArea();
    }

    private String obterEstacaoEscolhida() {
        ButtonModel selectedButton = null;
        for (Enumeration<AbstractButton> buttons = estacoesButtonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                selectedButton = button.getModel();
                break;
            }
        }

        if (selectedButton != null) {
            return selectedButton.getActionCommand();
        } else {
            return null;
        }
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
    }

    private void exibirEstacoesVizinhas() {
        estacoesPanel.removeAll();
        estacoesButtonGroup.clearSelection();

        for (Vertice vizinho : jogador.getGrafo().getVizinhos(jogador.getEstacaoAtual())) {
            JRadioButton radioButton = new JRadioButton(vizinho.getNome() + " (Distância: " +
                    jogador.getGrafo().getAresta(vizinho, jogador.getEstacaoAtual()).getDistancia() + " Preço: " +
                    jogador.getGrafo().getAresta(vizinho, jogador.getEstacaoAtual()).getPrecoPassagem() + ")");
            radioButton.setActionCommand(vizinho.getNome());
            estacoesButtonGroup.add(radioButton);
            estacoesPanel.add(radioButton);
        }

        frame.revalidate();
        frame.repaint();
    }

    private void atualizarInfoTextArea() {
        String info = "* Destino Final: " + jogador.getDestinoFinal().getNome() +
                "   Distância em linha reta: " + jogador.getGrafo().getDistanciaDireta(jogador.getEstacaoAtual(), jogador.getDestinoFinal()) +
                "\n* Estação Atual: " + jogador.getEstacaoAtual().getNome() +
                "\n* Saldo: " + jogador.getSaldo() +
                "\n............\n";

        if (revelar) {
            info += "Estações Vizinhas:\n";
            for (Vertice vizinho : jogador.getGrafo().getVizinhos(jogador.getEstacaoAtual())) {
                info += vizinho.getNome() + "\n";
                info += "Preço:" + jogador.getGrafo().getAresta(vizinho, jogador.getEstacaoAtual()).getPrecoPassagem() + "\n";
                info += "Distância:" + jogador.getGrafo().getAresta(vizinho, jogador.getEstacaoAtual()).getDistancia() + "\n";
            }
        }

        infoTextArea.setText(info);
    }

    private void gameOver() {
        frame.dispose();
        JOptionPane.showMessageDialog(null, "Jogo encerrado. Obrigado por jogar!");
        System.exit(0);
    }
}
