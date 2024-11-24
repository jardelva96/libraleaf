package view;

import util.ConexaoSQLite;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListarLivros extends JFrame {

    public ListarLivros() {
        // Configurações da janela
        setTitle("Listar Todos os Livros - Libraleaf");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(28, 40, 51)); // Fundo escuro azul

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(28, 40, 51)); // Fundo escuro
        painelPrincipal.setLayout(new BorderLayout());
        add(painelPrincipal);

        // Título
        JLabel titulo = new JLabel("Todos os Livros Cadastrados", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Área para exibir os livros
        JTextArea areaLivros = new JTextArea();
        areaLivros.setEditable(false);
        areaLivros.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaLivros.setBackground(new Color(44, 62, 80)); // Fundo escuro
        areaLivros.setForeground(Color.WHITE); // Texto branco
        areaLivros.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margem interna

        // Obtem a lista de livros do banco de dados
        List<String> livros = buscarLivrosDoBanco();

        // Exibe os livros na área de texto
        if (livros.isEmpty()) {
            areaLivros.setText("Nenhum livro cadastrado.");
        } else {
            areaLivros.setText(String.join("\n", livros));
        }

        painelPrincipal.add(new JScrollPane(areaLivros), BorderLayout.CENTER);
    }

    /**
     * Método para buscar livros do banco de dados.
     *
     * @return Lista de livros no formato "Título: [Título] | Caminho: [Caminho do PDF]"
     */
    private List<String> buscarLivrosDoBanco() {
        List<String> livros = new ArrayList<>();
        String sql = "SELECT titulo, caminho_pdf FROM livros";

        try (Connection conn = ConexaoSQLite.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String caminhoPdf = rs.getString("caminho_pdf");
                livros.add("Título: " + titulo + " | Caminho: " + caminhoPdf);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar livros do banco de dados: " + e.getMessage());
        }

        return livros;
    }

    public static void main(String[] args) {
        // Inicializa o banco de dados e exibe a janela de listagem
        SwingUtilities.invokeLater(() -> {
            util.InicializaBanco.inicializar();
            new ListarLivros().setVisible(true);
        });
    }
}
