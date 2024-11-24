package view;

import util.LivroDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RemoverLivro extends JFrame {

    public RemoverLivro() {
        // Configurações da janela
        setTitle("Remover Livro - Libraleaf");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(28, 40, 51)); // Fundo escuro azul

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(28, 40, 51));
        painelPrincipal.setLayout(new GridLayout(3, 1, 15, 15));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(painelPrincipal);

        // Buscar livros do banco de dados
        List<String> livros = LivroDAO.listarTitulosLivros();

        // Campo suspenso para selecionar o livro
        JComboBox<String> comboLivros = new JComboBox<>(livros.toArray(new String[0]));
        comboLivros.setBackground(new Color(44, 62, 80));
        comboLivros.setForeground(Color.WHITE);
        comboLivros.setFont(new Font("Arial", Font.PLAIN, 14));

        // Botão para remover o livro
        JButton btnRemover = new JButton("Remover Livro");
        btnRemover.setFont(new Font("Arial", Font.BOLD, 14));
        btnRemover.setBackground(new Color(192, 57, 43));
        btnRemover.setForeground(Color.WHITE);
        btnRemover.setFocusPainted(false);

        painelPrincipal.add(criarLabel("Selecione o livro para remover:"));
        painelPrincipal.add(comboLivros);
        painelPrincipal.add(btnRemover);

        // Ação do botão Remover
        btnRemover.addActionListener(e -> {
            String livroSelecionado = (String) comboLivros.getSelectedItem();
            if (livroSelecionado != null) {
                boolean sucesso = LivroDAO.removerLivro(livroSelecionado);
                if (sucesso) {
                    JOptionPane.showMessageDialog(null, "Livro '" + livroSelecionado + "' removido com sucesso!");
                    comboLivros.removeItem(livroSelecionado); // Atualiza a lista do combo box
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao remover o livro!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum livro selecionado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RemoverLivro().setVisible(true));
    }
}
