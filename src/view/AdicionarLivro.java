package view;

import util.LivroDAO;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AdicionarLivro extends JFrame {
    public AdicionarLivro() {
        // Configurações da janela
        setTitle("Adicionar Livro - Libraleaf");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(28, 40, 51)); // Fundo escuro azul

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(28, 40, 51)); // Fundo escuro
        painelPrincipal.setLayout(new GridLayout(5, 5, 15, 15)); // Layout com 5 linhas
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem interna
        add(painelPrincipal);

        // Campo de texto para o título do livro
        JTextField txtTitulo = new JTextField();
        txtTitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTitulo.setBackground(new Color(44, 62, 80)); // Fundo do campo
        txtTitulo.setForeground(Color.WHITE); // Texto branco
        txtTitulo.setCaretColor(Color.WHITE); // Cursor branco
        txtTitulo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Margem interna
        txtTitulo.setToolTipText("Digite o título do livro");

        // Botão para selecionar o arquivo PDF
        JButton btnSelecionarArquivo = new JButton("Selecionar PDF");
        btnSelecionarArquivo.setFont(new Font("Arial", Font.BOLD, 14));
        btnSelecionarArquivo.setBackground(new Color(41, 128, 185)); // Azul moderno
        btnSelecionarArquivo.setForeground(Color.WHITE); // Texto branco
        btnSelecionarArquivo.setFocusPainted(false);

        // Label para exibir o caminho do arquivo selecionado
        JLabel lblCaminhoArquivo = new JLabel("Nenhum arquivo selecionado");
        lblCaminhoArquivo.setForeground(Color.LIGHT_GRAY);

        // Botão para salvar o livro
        JButton btnSalvar = new JButton("Salvar Livro");
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.setBackground(new Color(39, 174, 96)); // Verde moderno
        btnSalvar.setForeground(Color.WHITE); // Texto branco
        btnSalvar.setFocusPainted(false);

        // Adicionando os componentes ao painel principal
        painelPrincipal.add(criarLabel("Título do Livro:"));
        painelPrincipal.add(txtTitulo);
        painelPrincipal.add(btnSelecionarArquivo);
        painelPrincipal.add(lblCaminhoArquivo);
        painelPrincipal.add(btnSalvar);

        // Ação do botão Selecionar Arquivo
        btnSelecionarArquivo.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Selecionar Arquivo PDF");
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (file.getName().endsWith(".pdf")) {
                    lblCaminhoArquivo.setText(file.getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, selecione um arquivo PDF!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação do botão Salvar
        btnSalvar.addActionListener(e -> {
            String titulo = txtTitulo.getText().trim();
            String caminhoArquivo = lblCaminhoArquivo.getText();

            if (!titulo.isEmpty() && !caminhoArquivo.equals("Nenhum arquivo selecionado")) {
                // Adicionando livro no banco de dados
                try {
                    LivroDAO.adicionarLivro(titulo, caminhoArquivo);
                    JOptionPane.showMessageDialog(null, "Livro adicionado com sucesso!");
                    dispose(); // Fecha a janela após salvar
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao adicionar livro: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Método utilitário para criar um JLabel estilizado.
     */
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE); // Texto branco
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Inicializando o banco de dados antes de abrir a janela
            util.InicializaBanco.inicializar();
            new AdicionarLivro().setVisible(true);
        });
    }
}
