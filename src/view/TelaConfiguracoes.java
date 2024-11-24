package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaConfiguracoes extends JFrame {

    public TelaConfiguracoes(String usuarioAtual) {
        // Configurações da janela
        setTitle("Configurações - Libraleaf");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBackground(new Color(44, 62, 80));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Configurações do Sistema", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descricao = new JLabel("<html><div style='text-align: center;'>Configure abaixo o nome de usuário, senha, tema, e horário do sistema.</div></html>");
        descricao.setFont(new Font("Arial", Font.PLAIN, 14));
        descricao.setForeground(Color.LIGHT_GRAY);
        descricao.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelCentral.add(Box.createVerticalGlue());
        painelCentral.add(titulo);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        painelCentral.add(descricao);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 20)));

        // Campo para alterar o nome de usuário
        JLabel lblUsuario = new JLabel("Nome de Usuário:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtUsuario = new JTextField(usuarioAtual);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsuario.setMaximumSize(new Dimension(300, 30));

        painelCentral.add(lblUsuario);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelCentral.add(txtUsuario);

        // Campo para alterar a senha
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Arial", Font.BOLD, 14));
        lblSenha.setForeground(Color.WHITE);
        lblSenha.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSenha.setMaximumSize(new Dimension(300, 30));

        painelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        painelCentral.add(lblSenha);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelCentral.add(txtSenha);

        // Campo para alterar a data e hora
        JLabel lblDataHora = new JLabel("Data e Hora:");
        lblDataHora.setFont(new Font("Arial", Font.BOLD, 14));
        lblDataHora.setForeground(Color.WHITE);
        lblDataHora.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtDataHora = new JTextField(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        txtDataHora.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDataHora.setMaximumSize(new Dimension(300, 30));

        painelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        painelCentral.add(lblDataHora);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelCentral.add(txtDataHora);

        // Campo para alterar o tema
        JLabel lblTema = new JLabel("Escolha o Tema:");
        lblTema.setFont(new Font("Arial", Font.BOLD, 14));
        lblTema.setForeground(Color.WHITE);
        lblTema.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] temas = {"Claro", "Escuro"};
        JComboBox<String> comboTema = new JComboBox<>(temas);
        comboTema.setFont(new Font("Arial", Font.PLAIN, 14));
        comboTema.setMaximumSize(new Dimension(200, 30));

        painelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        painelCentral.add(lblTema);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelCentral.add(comboTema);
        painelCentral.add(Box.createVerticalGlue());

        add(painelCentral, BorderLayout.CENTER);

        // Painel inferior com botões
        JPanel painelInferior = new JPanel();
        painelInferior.setBackground(new Color(33, 47, 61));
        painelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Botão "Voltar"
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setBackground(new Color(41, 128, 185));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setPreferredSize(new Dimension(150, 40));
        btnVoltar.addActionListener(e -> {
            new TelaPrincipal(usuarioAtual).setVisible(true);
            dispose();
        });

        // Botão "Salvar Configurações"
        JButton btnSalvar = new JButton("Salvar Configurações");
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.setBackground(new Color(39, 174, 96));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setPreferredSize(new Dimension(200, 40));
        btnSalvar.addActionListener(e -> {
            String novoUsuario = txtUsuario.getText();
            String novaSenha = new String(txtSenha.getPassword());
            String novaDataHora = txtDataHora.getText();
            String temaSelecionado = (String) comboTema.getSelectedItem();

            // Aqui você deve salvar os dados no banco de dados
            JOptionPane.showMessageDialog(null, "Configurações salvas com sucesso!\n"
                    + "Usuário: " + novoUsuario + "\n"
                    + "Data e Hora: " + novaDataHora + "\n"
                    + "Tema: " + temaSelecionado);
        });

        painelInferior.add(btnVoltar);
        painelInferior.add(btnSalvar);

        add(painelInferior, BorderLayout.SOUTH);
    }
}
