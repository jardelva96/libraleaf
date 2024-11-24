package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TelaLendo extends JFrame {

    public TelaLendo(String titulo, String caminhoPdf) {
        // Configurações da janela
        setTitle("Leitura - " + titulo);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(28, 40, 51));

        // Barra de navegação
        JPanel barraNavegacao = new JPanel();
        barraNavegacao.setBackground(new Color(33, 47, 61));
        barraNavegacao.setLayout(new GridLayout(1, 3, 10, 10));
        barraNavegacao.setPreferredSize(new Dimension(800, 50));

        JButton btnVoltar = criarBotao("← Voltar");
        JButton btnInicio = criarBotao("Início");
        JTextField campoPesquisar = new JTextField();
        JButton btnPesquisar = criarBotao("Pesquisar");

        barraNavegacao.add(btnVoltar);
        barraNavegacao.add(btnInicio);
        barraNavegacao.add(campoPesquisar);
        barraNavegacao.add(btnPesquisar);
        add(barraNavegacao, BorderLayout.NORTH);

        // Área de leitura
        JTextArea areaLeitura = new JTextArea();
        areaLeitura.setEditable(false);
        areaLeitura.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaLeitura.setBackground(new Color(44, 62, 80));
        areaLeitura.setForeground(Color.WHITE);
        areaLeitura.setText(carregarTextoDoPDF(caminhoPdf));
        add(new JScrollPane(areaLeitura), BorderLayout.CENTER);

        // Ações dos botões
        btnVoltar.addActionListener(e -> {
            new TelaPrincipal().setVisible(true);
            dispose();
        });

        btnPesquisar.addActionListener(e -> {
            String termo = campoPesquisar.getText().trim().toLowerCase();
            if (termo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite algo para pesquisar.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (areaLeitura.getText().toLowerCase().contains(termo)) {
                JOptionPane.showMessageDialog(this, "Termo encontrado no texto!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Termo não encontrado no texto.", "Resultado", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBackground(new Color(41, 128, 185));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        return botao;
    }

    private String carregarTextoDoPDF(String caminhoPdf) {
        // Simula o carregamento do texto do PDF.
        // Substitua por uma lógica real de leitura de arquivos PDF.
        return "Exemplo de texto extraído do arquivo PDF em: " + caminhoPdf;
    }
}
