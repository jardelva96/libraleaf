package util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQLite {
    // Caminho do banco de dados (relativo ao projeto)
    private static final String URL = "jdbc:sqlite:database/libraleaf.db";

    /**
     * Estabelece uma conexão com o banco de dados SQLite.
     *
     * @return Conexão ativa ou null em caso de erro.
     */
    public static Connection conectar() {
        try {
            // Cria o diretório "database" se não existir
            File diretorio = new File("database");
            if (!diretorio.exists()) {
                if (diretorio.mkdirs()) {
                    System.out.println("Diretório criado: " + diretorio.getAbsolutePath());
                } else {
                    System.out.println("Erro: Não foi possível criar o diretório " + diretorio.getAbsolutePath());
                    return null;
                }
            }

            // Conecta ao banco de dados
            return DriverManager.getConnection(URL);

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }
}
