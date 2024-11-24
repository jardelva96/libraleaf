package view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TelaLogin extends JFrame {

    // Simulando um banco de dados de usuários e senhas
    public static final Map<String, String> usuarios = new HashMap<>();

    static {
        // Adicionando usuários e senhas para o teste
        usuarios.put("admin", "senha123");
        usuarios.put("user", "userpass");
    }

    public TelaLogin() {
        // Configurações da janela principal
        setTitle("Login - Libraleaf");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true); // Remove a barra de título para um efeito mais moderno
        setBackground(new Color(0, 0, 0, 0)); // Define o fundo como transparente

        // Painel principal para centralizar a caixa de login
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

        // Painel central da caixa de login
        JPanel painelCaixaLogin = new JPanel();
        painelCaixaLogin.setBackground(new Color(33, 47, 61, 220)); // Fundo da caixa de login com transparência
        painelCaixaLogin.setPreferredSize(new Dimension(350, 500));
        painelCaixaLogin.setLayout(new GridLayout(7, 1, 15, 15));
        painelCaixaLogin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem interna

        // Título
        JLabel titulo = new JLabel("LOGIN LIBRALEAF", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);

        // Campo de texto para o nome do usuário
        JTextField txtNome = new JTextField();
        txtNome.setPreferredSize(new Dimension(300, 40));
        txtNome.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNome.setBackground(new Color(44, 62, 80)); // Fundo do campo
        txtNome.setForeground(Color.WHITE); // Texto branco
        txtNome.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Sem bordas padrão
        txtNome.setCaretColor(Color.WHITE); // Cursor branco

        // Placeholder para o campo de usuário
        txtNome.setText("Usuário");
        txtNome.setForeground(Color.GRAY);
        txtNome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txtNome.getText().equals("Usuário")) {
                    txtNome.setText("");
                    txtNome.setForeground(Color.WHITE);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtNome.getText().isEmpty()) {
                    txtNome.setText("Usuário");
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
        txtSenha.setText("Senha");
        txtSenha.setForeground(Color.GRAY);
        txtSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (String.valueOf(txtSenha.getPassword()).equals("Senha")) {
                    txtSenha.setText("");
                    txtSenha.setForeground(Color.WHITE);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (String.valueOf(txtSenha.getPassword()).isEmpty()) {
                    txtSenha.setText("Senha");
                    txtSenha.setForeground(Color.GRAY);
                }
            }
        });

        // Botão de Login
        JButton btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(new Dimension(300, 40));
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogin.setBackground(new Color(41, 128, 185)); // Azul moderno
        btnLogin.setForeground(Color.WHITE); // Texto branco
        btnLogin.setFocusPainted(false);

        // Ação do botão Login
        btnLogin.addActionListener(e -> {
            String nome = txtNome.getText();
            String senha = new String(txtSenha.getPassword());
            if (usuarios.containsKey(nome) && usuarios.get(nome).equals(senha)) {
                // Se o usuário e senha forem válidos, vai para a tela principal
                JOptionPane.showMessageDialog(null, "Bem-vindo, " + nome + "!", "Login bem-sucedido", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Fecha a tela de login
                new TelaPrincipal(nome).setVisible(true); // Abre a tela principal
            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botão de Registrar
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setPreferredSize(new Dimension(300, 40));
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegistrar.setBackground(new Color(52, 73, 94)); // Fundo cinza-azulado
        btnRegistrar.setForeground(Color.WHITE); // Texto branco
        btnRegistrar.setFocusPainted(false);

        // Ação do botão Registrar
        btnRegistrar.addActionListener(e -> {
            dispose();
            new TelaRegistrar().setVisible(true); // Abrir a tela de registro
        });

        // Botão de Visitante
        JButton btnVisitante = new JButton("Entrar como Visitante");
        btnVisitante.setPreferredSize(new Dimension(300, 40));
        btnVisitante.setFont(new Font("Arial", Font.BOLD, 16));
        btnVisitante.setBackground(new Color(39, 174, 96)); // Verde moderno
        btnVisitante.setForeground(Color.WHITE); // Texto branco
        btnVisitante.setFocusPainted(false);

        // Ação do botão Visitante
        btnVisitante.addActionListener(e -> {
            dispose();
            new TelaPrincipal().setVisible(true); // Abrir a tela principal no modo visitante
        });

        // Adicionar componentes à caixa de login
        painelCaixaLogin.add(titulo);
        painelCaixaLogin.add(txtNome);
        painelCaixaLogin.add(txtSenha);
        painelCaixaLogin.add(btnLogin);
        painelCaixaLogin.add(btnRegistrar);
        painelCaixaLogin.add(btnVisitante);

        painelPrincipal.add(painelCaixaLogin, BorderLayout.CENTER);
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
            TelaLogin telaLogin = new TelaLogin();
            telaLogin.setOpacity(0.95f); // Define a opacidade da janela
            telaLogin.setVisible(true);
        });
    }
}
