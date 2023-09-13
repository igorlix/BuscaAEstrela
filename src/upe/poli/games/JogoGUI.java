package upe.poli.games;

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
    private ButtonGroup estacoesButtonGroup;
    private JButton continuarButton;
    private JButton sorteOuRevesButton;
    private boolean revelar;

    public JogoGUI(Jogador jogador) {
        this.jogador = jogador;
        this.frame = new JFrame("Jogo do Metrô");
        this.infoTextArea = new JTextArea(10, 40);
        this.estacoesPanel = new JPanel();
        this.estacoesButtonGroup = new ButtonGroup();
        this.continuarButton = new JButton("Continuar");
        this.sorteOuRevesButton = new JButton("Sorte ou Revés");
        this.revelar = true;

        // Configurar a área de informações
        infoTextArea.setEditable(false);

        // Configurar o botão "Continuar"
        continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String estacaoEscolhida = obterEstacaoEscolhida();
                if (estacaoEscolhida != null) {
                    exibirSorteOuReves();
                }
            }
        });

        // Configurar o botão "Sorte ou Revés"
        sorteOuRevesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lidarComEventoSorteOuReves();
                exibirEstacoesVizinhas(); // Atualizar a tela das estações após Sorte ou Revés
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
        exibirEstacoesVizinhas();
    }

    private String obterEstacaoEscolhida() {
        for (Enumeration<AbstractButton> buttons = estacoesButtonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getActionCommand();
            }
        }
        return null;
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

        estacoesPanel.revalidate();
        estacoesPanel.repaint();
    }

    private void exibirSorteOuReves() {
        frame.remove(estacoesPanel);
        frame.remove(continuarButton);
        frame.add(sorteOuRevesButton, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
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

        // Simule a exibição de uma imagem para representar o efeito
        mostrarImagemDeEfeito();
    }

    private void mostrarImagemDeEfeito() {
        // Aqui você pode mostrar uma imagem que representa o efeito sorte ou revés.
        // Por exemplo, você pode usar um JOptionPane para mostrar uma mensagem ou imagem.
        JOptionPane.showMessageDialog(frame, "Efeito Sorte ou Revés!");

        // Volte para a tela das estações após o efeito
        frame.remove(sorteOuRevesButton);
        frame.add(estacoesPanel, BorderLayout.CENTER);
        frame.add(continuarButton, BorderLayout.SOUTH);
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

}