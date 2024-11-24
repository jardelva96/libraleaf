package view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class TelaRegistrar extends JFrame {

    // Referência para o HashMap de usuários da TelaLogin
    private static final Map<String, String> usuarios = TelaLogin.usuarios;

    public TelaRegistrar() {
        // Configurações da janela de registro
        setTitle("Registrar - Libraleaf");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true); // Remove a barra de título para um efeito mais moderno
        setBackground(new Color(0, 0, 0, 0)); // Define o fundo como transparente

        // Painel principal para centralizar a caixa de registro
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(28, 40, 51, 180)); // Fundo escuro com transparência
        add(painelPrincipal);

        // Barra superior com os botões de controle
        JPanel barraSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        barraSuperior.setPreferredSize(new Dimension(500, 30));
        barraSuperior.setBackground(new Color(33, 47, 61, 220)); // Fundo com transparência

        // Botão Minimizar
        JButton btnMinimizar = criarBotaoControle("_", Color.YELLOW);
        btnMinimizar.addActionListener(e -> setState(JFrame.ICONIFIED)); // Minimiza a janela

        // Botão Maximizar/Restaurar
        JButton btnMaximizar = criarBotaoControle("□", Color.GREEN);
        btnMaximizar.addActionListener(e -> {
            if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                setExtendedState(JFrame.NORMAL); // Restaura a janela ao tamanho original
            } else {
                setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza a janela
            }
        });

        // Botão Fechar
        JButton btnFechar = criarBotaoControle("X", Color.RED);
        btnFechar.addActionListener(e -> System.exit(0)); // Fecha o programa

        barraSuperior.add(btnMinimizar);
        barraSuperior.add(btnMaximizar);
        barraSuperior.add(btnFechar);

        painelPrincipal.add(barraSuperior, BorderLayout.NORTH);

        // Painel central da caixa de registro
        JPanel painelCaixaRegistro = new JPanel();
        painelCaixaRegistro.setBackground(new Color(33, 47, 61, 220)); // Fundo da caixa de registro com transparência
        painelCaixaRegistro.setPreferredSize(new Dimension(350, 500));
        painelCaixaRegistro.setLayout(new GridLayout(7, 1, 15, 15));
        painelCaixaRegistro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem interna

        // Título
        JLabel titulo = new JLabel("REGISTRE-SE LIBRALEAF", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);

        // Campo de texto para o nome de usuário
        JTextField txtNome = new JTextField();
        txtNome.setPreferredSize(new Dimension(300, 40));
        txtNome.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNome.setBackground(new Color(44, 62, 80)); // Fundo do campo
        txtNome.setForeground(Color.WHITE); // Texto branco
        txtNome.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Sem bordas padrão
        txtNome.setCaretColor(Color.WHITE); // Cursor branco

        // Placeholder para o campo de usuário
        txtNome.setText("Escolha um nome de usuário");
        txtNome.setForeground(Color.GRAY);
        txtNome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txtNome.getText().equals("Escolha um nome de usuário")) {
                    txtNome.setText("");
                    txtNome.setForeground(Color.WHITE);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtNome.getText().isEmpty()) {
                    txtNome.setText("Escolha um nome de usuário");
                    txtNome.setForeground(Color.GRAY);
                }
            }
        });

        // Campo de texto para a senha
        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setPreferredSize(new Dimension(300, 40));
        txtSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSenha.setBackground(new Color(44, 62, 80)); // Fundo do campo
        txtSenha.setForeground(Color.WHITE); // Texto branco
        txtSenha.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Sem bordas padrão
        txtSenha.setCaretColor(Color.WHITE); // Cursor branco

        // Placeholder para o campo de senha
        txtSenha.setText("Escolha uma senha");
        txtSenha.setForeground(Color.GRAY);
        txtSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (String.valueOf(txtSenha.getPassword()).equals("Escolha uma senha")) {
                    txtSenha.setText("");
                    txtSenha.setForeground(Color.WHITE);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (String.valueOf(txtSenha.getPassword()).isEmpty()) {
                    txtSenha.setText("Escolha uma senha");
                    txtSenha.setForeground(Color.GRAY);
                }
            }
        });

        // Botão de Registrar
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setPreferredSize(new Dimension(300, 40));
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegistrar.setBackground(new Color(41, 128, 185)); // Azul moderno
        btnRegistrar.setForeground(Color.WHITE); // Texto branco
        btnRegistrar.setFocusPainted(false);

        // Ação do botão Registrar
        btnRegistrar.addActionListener(e -> {
            String nome = txtNome.getText();
            String senha = new String(txtSenha.getPassword());
            if (!nome.isEmpty() && !senha.isEmpty()) {
                // Verifica se o nome de usuário já existe
                if (usuarios.containsKey(nome)) {
                    JOptionPane.showMessageDialog(null, "Este usuário já existe!", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Registra o novo usuário
                    usuarios.put(nome, senha);
                    JOptionPane.showMessageDialog(null, "Usuário registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Fecha a tela de registro
                    new TelaLogin().setVisible(true); // Retorna à tela de login
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Adicionar componentes à caixa de registro
        painelCaixaRegistro.add(titulo);
        painelCaixaRegistro.add(txtNome);
        painelCaixaRegistro.add(txtSenha);
        painelCaixaRegistro.add(btnRegistrar);

        painelPrincipal.add(painelCaixaRegistro, BorderLayout.CENTER);
    }

    private JButton criarBotaoControle(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Margem interna
        return botao;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaRegistrar telaRegistrar = new TelaRegistrar();
            telaRegistrar.setOpacity(0.95f); // Define a opacidade da janela
            telaRegistrar.setVisible(true);
        });
    }
}
