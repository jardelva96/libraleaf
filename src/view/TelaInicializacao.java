package view;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class TelaInicializacao extends JFrame {

    public TelaInicializacao() {
        // Configurações da janela
        setTitle("Libraleaf - Inicialização");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setUndecorated(true); // Remove bordas e barra de título

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(28, 40, 51)); // Fundo escuro
        painelPrincipal.setLayout(new BorderLayout());
        add(painelPrincipal);

        // Adiciona o logo
        JLabel labelLogo = new JLabel();
        labelLogo.setHorizontalAlignment(SwingConstants.CENTER);
        labelLogo.setIcon(new ImageIcon(getClass().getResource("/resources/logo.png"))); // Caminho do logo
        painelPrincipal.add(labelLogo, BorderLayout.CENTER);

        // Adiciona uma mensagem de inicialização
        JLabel mensagem = new JLabel("Carregando, por favor aguarde...", SwingConstants.CENTER);
        mensagem.setForeground(Color.WHITE);
        mensagem.setFont(new Font("Arial", Font.PLAIN, 14));
        painelPrincipal.add(mensagem, BorderLayout.SOUTH);

        // Torna a janela visível
        setVisible(true);

        // Timer para exibir a tela de login após alguns segundos
        iniciarTransicao();
    }

    private void iniciarTransicao() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Fecha a tela de inicialização e abre a tela de login
                dispose(); // Fecha a janela atual
                SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true)); // Abre a tela de login
            }
        }, 3000); // Aguarda 3 segundos antes de abrir a tela de login
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaInicializacao::new); // Inicia a tela de inicialização
    }
}
