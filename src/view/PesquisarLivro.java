package view;

import util.LivroDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PesquisarLivro extends JFrame {

    public PesquisarLivro() {
        // Configurações da janela
        setTitle("Pesquisar Livro - Libraleaf");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(28, 40, 51)); // Fundo escuro azul

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(28, 40, 51));
        painelPrincipal.setLayout(new BorderLayout());
        add(painelPrincipal);

        // Campo de pesquisa
        JTextField txtPesquisa = new JTextField();
        txtPesquisa.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPesquisa.setBackground(new Color(44, 62, 80));
        txtPesquisa.setForeground(Color.WHITE);
        txtPesquisa.setCaretColor(Color.WHITE);
        txtPesquisa.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelPrincipal.add(txtPesquisa, BorderLayout.NORTH);

        // Área para exibir os resultados
        JTextArea areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaResultados.setBackground(new Color(44, 62, 80)); // Fundo escuro
        areaResultados.setForeground(Color.WHITE); // Texto branco
        areaResultados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelPrincipal.add(new JScrollPane(areaResultados), BorderLayout.CENTER);

        // Botão de pesquisa
        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setFont(new Font("Arial", Font.BOLD, 14));
        btnPesquisar.setBackground(new Color(41, 128, 185)); // Azul moderno
        btnPesquisar.setForeground(Color.WHITE);
        btnPesquisar.setFocusPainted(false);
        painelPrincipal.add(btnPesquisar, BorderLayout.SOUTH);

        // Ação do botão de pesquisa
        btnPesquisar.addActionListener(e -> {
            String query = txtPesquisa.getText().trim();
            if (query.isEmpty()) {
                areaResultados.setText("Digite algo para pesquisar.");
                return;
            }

            // Busca no banco de dados
            List<String[]> resultados = LivroDAO.pesquisarLivros(query);
            if (resultados.isEmpty()) {
                areaResultados.setText("Nenhum livro encontrado para o termo: " + query);
            } else {
                StringBuilder textoResultados = new StringBuilder("Resultados encontrados:\n");
                for (String[] livro : resultados) {
                    textoResultados.append("Título: ").append(livro[0]).append("\n");
                    textoResultados.append("Caminho PDF: ").append(livro[1]).append("\n\n");
                }
                areaResultados.setText(textoResultados.toString());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PesquisarLivro().setVisible(true));
    }
}
