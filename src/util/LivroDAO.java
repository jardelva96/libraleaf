package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    /**
     * Lista os títulos dos livros cadastrados no banco de dados.
     * 
     * @return Lista de títulos dos livros.
     */
    public static List<String> listarTitulosLivros() {
        List<String> titulos = new ArrayList<>();
        String sql = "SELECT titulo FROM livros";

        try (Connection conn = ConexaoSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                titulos.add(rs.getString("titulo"));
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar títulos de livros: " + e.getMessage());
        }

        return titulos;
    }

    /**
     * Lista os livros cadastrados no banco de dados com base no status.
     * 
     * @param status Pode ser null para todos os livros ou "Lendo" para filtrar os livros sendo lidos.
     * @return Lista de livros [título, caminho_pdf].
     */
    public static List<String[]> listarLivrosComCaminho(String status) {
        List<String[]> livros = new ArrayList<>();
        String sql;

        if (status == null || status.equalsIgnoreCase("Todos")) {
            sql = "SELECT titulo, caminho_pdf FROM livros";
        } else if (status.equalsIgnoreCase("Lendo")) {
            sql = "SELECT titulo, caminho_pdf FROM livros WHERE lendo = 1";
        } else {
            return livros; // Retorna vazio se o status for inválido
        }

        try (Connection conn = ConexaoSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String caminhoPdf = rs.getString("caminho_pdf");
                livros.add(new String[]{titulo, caminhoPdf});
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }

        return livros;
    }

    /**
     * Adiciona um novo livro ao banco de dados.
     * 
     * @param titulo      Título do livro.
     * @param caminhoPdf  Caminho do arquivo PDF do livro.
     */
    public static void adicionarLivro(String titulo, String caminhoPdf) {
        String sql = "INSERT INTO livros (titulo, caminho_pdf, lendo) VALUES (?, ?, 0)";
        try (Connection conn = ConexaoSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, titulo);
            pstmt.setString(2, caminhoPdf);
            pstmt.executeUpdate();
            System.out.println("Livro adicionado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao adicionar livro: " + e.getMessage());
        }
    }

    /**
     * Remove um livro do banco de dados pelo título.
     * 
     * @param titulo Título do livro a ser removido.
     * @return true se o livro foi removido com sucesso, false caso contrário.
     */
    public static boolean removerLivro(String titulo) {
        String sql = "DELETE FROM livros WHERE titulo = ?";
        try (Connection conn = ConexaoSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, titulo);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            System.out.println("Erro ao remover livro: " + e.getMessage());
            return false;
        }
    }

    /**
     * Marca um livro como "LENDO".
     * 
     * @param titulo Título do livro a ser marcado.
     */
    public static void marcarComoLendo(String titulo) {
        String sql = "UPDATE livros SET lendo = 1 WHERE titulo = ?";
        try (Connection conn = ConexaoSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, titulo);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Livro marcado como 'LENDO' com sucesso!");
            } else {
                System.out.println("Nenhum livro encontrado com o título fornecido.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao marcar livro como 'LENDO': " + e.getMessage());
        }
    }

    /**
     * Desmarca um livro como "LENDO".
     * 
     * @param titulo Título do livro a ser desmarcado.
     */
    public static void desmarcarComoLendo(String titulo) {
        String sql = "UPDATE livros SET lendo = 0 WHERE titulo = ?";
        try (Connection conn = ConexaoSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, titulo);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Livro desmarcado como 'LENDO' com sucesso!");
            } else {
                System.out.println("Nenhum livro encontrado com o título fornecido.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao desmarcar livro como 'LENDO': " + e.getMessage());
        }
    }

    /**
     * Pesquisa livros com base no título fornecido.
     * 
     * @param termo Termo de busca para o título.
     * @return Lista de livros correspondentes.
     */
    public static List<String[]> pesquisarLivros(String termo) {
        List<String[]> livros = new ArrayList<>();
        String sql = "SELECT titulo, caminho_pdf FROM livros WHERE LOWER(titulo) LIKE ?";

        try (Connection conn = ConexaoSQLite.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + termo.toLowerCase() + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String titulo = rs.getString("titulo");
                    String caminhoPdf = rs.getString("caminho_pdf");
                    livros.add(new String[]{titulo, caminhoPdf});
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao pesquisar livros: " + e.getMessage());
        }

        return livros;
    }
}
