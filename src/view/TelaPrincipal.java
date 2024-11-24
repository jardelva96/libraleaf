package view;

import util.InicializaBanco;
import util.LivroDAO;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TelaPrincipal extends JFrame {
    private JPanel painelCentral;
    private JButton btnUsuarioLogado;
    private JButton btnQuantidadeLivros;
    private JButton btnDataHora;

    public TelaPrincipal(String usuarioLogado) {
        inicializarTela(usuarioLogado);
    }

    public TelaPrincipal() {
        this("Visitante");
    }

    private void inicializarTela(String usuarioLogado) {
        setTitle("Libraleaf - Biblioteca");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel esquerdo fixo
        JPanel painelEsquerdo = criarPainelEsquerdo(usuarioLogado);
        add(painelEsquerdo, BorderLayout.WEST);

        // Adicionar linha separadora entre os painéis esquerdo e direito
        JSeparator linhaSeparadora = new JSeparator(SwingConstants.VERTICAL);
        linhaSeparadora.setForeground(new Color(41, 128, 185));
        linhaSeparadora.setBackground(new Color(41, 128, 185));
        add(linhaSeparadora, BorderLayout.CENTER);

        // Painel direito
        JPanel painelDireito = criarPainelDireito();
        add(painelDireito, BorderLayout.CENTER);

        exibirMensagemInicial();
    }

    private JPanel criarPainelEsquerdo(String usuarioLogado) {
        JPanel painelEsquerdo = new JPanel();
        painelEsquerdo.setBackground(new Color(33, 47, 61));
        painelEsquerdo.setPreferredSize(new Dimension(300, getHeight()));
        painelEsquerdo.setLayout(new BorderLayout());

        // Logo
        JPanel painelLogo = new JPanel();
        painelLogo.setBackground(new Color(33, 47, 61));
        painelLogo.setPreferredSize(new Dimension(250, 100));
        painelLogo.setLayout(new GridBagLayout());

        JLabel lblLogo = new JLabel("LIBRALEAF");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 20));
        lblLogo.setForeground(Color.WHITE);
        painelLogo.add(lblLogo);

        painelEsquerdo.add(painelLogo, BorderLayout.NORTH);

        // Informações centrais
        JPanel painelInformacoes = criarPainelInformacoes(usuarioLogado);
        painelEsquerdo.add(painelInformacoes, BorderLayout.CENTER);

        // Configurações
        JPanel painelInferior = criarPainelInferior(usuarioLogado);
        painelEsquerdo.add(painelInferior, BorderLayout.SOUTH);

        return painelEsquerdo;
    }

    private JPanel criarPainelInformacoes(String usuarioLogado) {
        JPanel painelInformacoes = new JPanel();
        painelInformacoes.setBackground(new Color(44, 62, 80));
        painelInformacoes.setLayout(new BoxLayout(painelInformacoes, BoxLayout.Y_AXIS));
        painelInformacoes.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        btnUsuarioLogado = criarBotaoLargo("Usuário Logado: " + usuarioLogado);
        btnQuantidadeLivros = criarBotaoLargo("Livros: " + obterQuantidadeLivros());
        btnDataHora = criarBotaoLargo("");
        atualizarDataHora();

        painelInformacoes.add(btnUsuarioLogado);
        painelInformacoes.add(Box.createRigidArea(new Dimension(0, 10)));
        painelInformacoes.add(btnQuantidadeLivros);
        painelInformacoes.add(Box.createRigidArea(new Dimension(0, 10)));
        painelInformacoes.add(btnDataHora);

        return painelInformacoes;
    }

    private JPanel criarPainelInferior(String usuarioLogado) {
        JPanel painelInferior = new JPanel();
        painelInferior.setBackground(new Color(33, 47, 61));
        painelInferior.setLayout(new BoxLayout(painelInferior, BoxLayout.Y_AXIS));
        painelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnConfiguracoes = criarBotaoLargo("Configurações");
        btnConfiguracoes.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfiguracoes.setBackground(new Color(41, 128, 185));
        btnConfiguracoes.setForeground(Color.WHITE);
        btnConfiguracoes.setFocusPainted(false);
        btnConfiguracoes.addActionListener(e -> {
            new TelaConfiguracoes(usuarioLogado).setVisible(true);
            this.dispose();
        });
        painelInferior.add(btnConfiguracoes);

        JLabel lblFrase = new JLabel("<html><div style='text-align: center;'>Criado para guardar e<br>ler seus livros favoritos.</div></html>");
        lblFrase.setFont(new Font("Arial", Font.ITALIC, 12));
        lblFrase.setForeground(Color.LIGHT_GRAY);
        lblFrase.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelInferior.add(lblFrase);

        return painelInferior;
    }

    private JPanel criarPainelDireito() {
        JPanel painelDireito = new JPanel();
        painelDireito.setLayout(new BorderLayout());
        painelDireito.setBackground(new Color(44, 62, 80));

        JPanel barraNavegacao = criarBarraNavegacao();
        painelDireito.add(barraNavegacao, BorderLayout.NORTH);

        painelCentral = new JPanel();
        painelCentral.setBackground(new Color(44, 62, 80));
        painelCentral.setLayout(new BorderLayout());
        painelDireito.add(painelCentral, BorderLayout.CENTER);

        return painelDireito;
    }

    private JPanel criarBarraNavegacao() {
        JPanel barraNavegacao = new JPanel();
        barraNavegacao.setBackground(new Color(33, 47, 61));
        barraNavegacao.setLayout(new GridLayout(1, 5, 10, 10));
        barraNavegacao.setPreferredSize(new Dimension(800, 60));

        JButton btnTodos = criarBotaoNavegacao("TODOS");
        JButton btnLendo = criarBotaoNavegacao("LENDO");
        JButton btnAdicionar = criarBotaoNavegacao("ADICIONAR LIVRO");
        JButton btnRemover = criarBotaoNavegacao("REMOVER LIVRO");
        JButton btnPesquisar = criarBotaoNavegacao("PESQUISAR");

        barraNavegacao.add(btnTodos);
        barraNavegacao.add(btnLendo);
        barraNavegacao.add(btnAdicionar);
        barraNavegacao.add(btnRemover);
        barraNavegacao.add(btnPesquisar);

        btnTodos.addActionListener(e -> exibirLivros());
        btnLendo.addActionListener(e -> exibirLivros("LENDO"));
        btnAdicionar.addActionListener(e -> new AdicionarLivro().setVisible(true));
        btnRemover.addActionListener(e -> new RemoverLivro().setVisible(true));
        btnPesquisar.addActionListener(e -> new PesquisarLivro().setVisible(true));

        return barraNavegacao;
    }

    private JButton criarBotaoLargo(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBackground(new Color(44, 62, 80));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        return botao;
    }

    private JButton criarBotaoNavegacao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBackground(new Color(41, 128, 185));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        return botao;
    }

    private void atualizarDataHora() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                btnDataHora.setText("Data e Hora: " + dataHora);
            }
        }, 0, 1000);
    }

    private int obterQuantidadeLivros() {
        return LivroDAO.listarLivrosComCaminho(null).size();
    }

    private void exibirMensagemInicial() {
        painelCentral.removeAll();
        JLabel mensagem = new JLabel("<html><div style='text-align: center;'>"
                + "<h1>Bem-vindo ao Libraleaf!</h1>"
                + "<p>Este programa foi desenvolvido para ajudá-lo a organizar sua biblioteca digital.</p>"
                + "<p>Adicione, leia e pesquise livros de forma simples e eficiente!</p>"
                + "</div></html>", JLabel.CENTER);
        mensagem.setForeground(Color.WHITE);
        painelCentral.add(mensagem, BorderLayout.CENTER);
        painelCentral.revalidate();
        painelCentral.repaint();
    }

    private void exibirLivros() {
        exibirLivros(null);
    }

    private void exibirLivros(String status) {
        painelCentral.removeAll();

        List<String[]> livros = LivroDAO.listarLivrosComCaminho(status);

        if (livros.isEmpty()) {
            JLabel mensagem = new JLabel("Nenhum livro encontrado.", JLabel.CENTER);
            mensagem.setForeground(Color.WHITE);
            painelCentral.add(mensagem, BorderLayout.CENTER);
        } else {
            JPanel painelLivros = new JPanel();
            painelLivros.setBackground(new Color(44, 62, 80));
            painelLivros.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

            for (String[] livro : livros) {
                String titulo = livro[0];
                String caminhoPdf = livro[1];

                JButton botaoLivro = new JButton("<html><center>" + titulo + "</center></html>");
                botaoLivro.setFont(new Font("Arial", Font.PLAIN, 14));
                botaoLivro.setPreferredSize(new Dimension(120, 160));
                botaoLivro.setBackground(new Color(41, 128, 185));
                botaoLivro.setForeground(Color.WHITE);
                botaoLivro.setFocusPainted(false);

                botaoLivro.addActionListener(e -> abrirTelaLeitura(titulo, caminhoPdf));
                painelLivros.add(botaoLivro);
            }

            painelCentral.add(painelLivros, BorderLayout.CENTER);
        }

        painelCentral.revalidate();
        painelCentral.repaint();
    }

    private void abrirTelaLeitura(String titulo, String caminhoPdf) {
        new TelaLeitura(titulo, caminhoPdf).setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InicializaBanco.inicializar();
            new TelaPrincipal("Visitante").setVisible(true);
        });
    }
}
