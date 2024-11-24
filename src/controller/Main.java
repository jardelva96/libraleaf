package controller;

import util.InicializaBanco; // Inicializador do banco de dados
import view.TelaInicializacao;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Garantir que a interface gráfica seja criada na thread correta
        SwingUtilities.invokeLater(() -> {
            try {
                // Inicializa o banco de dados antes de abrir a interface
                System.out.println("Inicializando o banco de dados...");
                InicializaBanco.inicializar();

                // Exibe a tela de inicialização
                TelaInicializacao telaInicializacao = new TelaInicializacao();
                telaInicializacao.setVisible(true);

            } catch (Exception e) {
                System.err.println("Erro durante a inicialização do programa: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
