package util;

import java.sql.Connection;
import java.sql.Statement;

public class InicializaBanco {

    public static void inicializar() {
        String sqlCriarTabela = """
            CREATE TABLE IF NOT EXISTS livros (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                caminho_pdf TEXT NOT NULL,
                lendo INTEGER DEFAULT 0
            );
        """;

        try (Connection conn = ConexaoSQLite.conectar();
             Statement stmt = conn.createStatement()) {

            // Criação da tabela
            stmt.execute(sqlCriarTabela);

            // Atualiza a tabela para adicionar a coluna "lendo" se ela não existir
            String sqlAdicionarColunaLendo = """
                ALTER TABLE livros ADD COLUMN lendo INTEGER DEFAULT 0;
            """;

            try {
                stmt.execute(sqlAdicionarColunaLendo);
            } catch (Exception e) {
                // Ignora o erro se a coluna já existir
                System.out.println("A coluna 'lendo' já existe na tabela 'livros'.");
            }

            System.out.println("Banco de dados inicializado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao inicializar o banco de dados: " + e.getMessage());
        }
    }
}
