import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

public class main {
    private ArrayList<String> frutas; // Lista para armazenar as frutas
    private DefaultListModel<String> listModel; // Modelo para a JList
    private JList<String> list; // Componente JList para exibir as frutas

    public main() {
        frutas = new ArrayList<>(); // Inicializa a lista de frutas
        listModel = new DefaultListModel<>(); // Inicializa o modelo da lista
        JFrame frame = new JFrame("Gerenciador de Frutas"); // Criação da janela principal
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ação para fechar a janela
        frame.setSize(600, 300); // Tamanho da janela
        frame.setLayout(new BorderLayout()); // Layout principal da janela

        // Painel para os botões e o campo de texto
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout()); // Layout de fluxo para os componentes

        // Campo de texto para inserir uma nova fruta
        JTextField frutaField = new JTextField(15);
        panel.add(frutaField); // Adiciona o campo ao painel

        // Botão para adicionar frutas
        JButton addButton = new JButton("Adicionar");
        panel.add(addButton);

        // Botão para modificar frutas (inicialmente desativado)
        JButton modifyButton = new JButton("Modificar");
        modifyButton.setEnabled(false); // Desativa inicialmente
        panel.add(modifyButton);

        // Botão para remover frutas (inicialmente desativado)
        JButton removeButton = new JButton("Remover");
        removeButton.setEnabled(false); // Desativa inicialmente
        panel.add(removeButton);

        frame.add(panel, BorderLayout.NORTH); // Adiciona o painel na parte superior

        // Componente de lista para exibir as frutas
        list = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER); // Adiciona a lista à janela

        // Botão para listar todas as frutas
        JButton listButton = new JButton("Listar Frutas");
        frame.add(listButton, BorderLayout.SOUTH);

        // Ação do botão "Adicionar"
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String novaFruta = frutaField.getText(); // Pega o texto do campo
                if (!novaFruta.isEmpty()) {
                    // Verifica se a fruta já está na lista
                    if (frutas.contains(novaFruta)) {
                        JOptionPane.showMessageDialog(frame, "A fruta \"" + novaFruta + "\" já foi adicionada.");
                    } else {
                        frutas.add(novaFruta); // Adiciona a fruta à lista
                        listModel.addElement(novaFruta); // Adiciona a fruta ao modelo da lista
                        frutaField.setText(""); // Limpa o campo de texto
                        JOptionPane.showMessageDialog(frame, novaFruta + " foi adicionada.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Digite o nome da fruta.");
                }
            }
        });

        // Ação do botão "Modificar"
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list.getSelectedIndex(); // Obtém o índice da fruta selecionada
                if (selectedIndex != -1) { // Verifica se uma fruta foi selecionada
                    String frutaSelecionada = listModel.getElementAt(selectedIndex);
                    String novaFruta = JOptionPane.showInputDialog(frame, 
                        "Modificar " + frutaSelecionada + " para:", frutaSelecionada); 
                    if (novaFruta != null && !novaFruta.isEmpty()) {
                        // Verifica se a nova fruta já existe na lista
                        if (frutas.contains(novaFruta)) {
                            JOptionPane.showMessageDialog(frame, "A fruta \"" + novaFruta + "\" já está na lista.");
                        } else {
                            frutas.set(selectedIndex, novaFruta); // Modifica na lista lógica
                            listModel.set(selectedIndex, novaFruta); // Modifica no modelo da lista
                            JOptionPane.showMessageDialog(frame, "Fruta " + frutaSelecionada +
                                    " foi modificada para " + novaFruta);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione uma fruta para modificar.");
                }
            }
        });

        // Ação do botão "Remover"
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list.getSelectedIndex(); // Obtém o índice da fruta selecionada
                if (selectedIndex != -1) {
                    // Remove a fruta tanto da lista lógica (ArrayList) quanto da lista visual (JList)
                    String frutaRemovida = frutas.remove(selectedIndex); // Remove da lista lógica
                    listModel.remove(selectedIndex); // Remove do modelo da lista
                    JOptionPane.showMessageDialog(frame, frutaRemovida + " foi removida.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione uma fruta para remover.");
                }
            }
        });

        // Ação do botão "Listar Frutas"
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (frutas.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Nenhuma fruta na lista.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Frutas: " + frutas);
                }
            }
        });

        // Ouvinte para seleção de itens na lista
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                boolean selectionExists = !list.isSelectionEmpty(); // Verifica se há item selecionado
                removeButton.setEnabled(selectionExists); // Ativa ou desativa o botão "Remover"
                modifyButton.setEnabled(selectionExists); // Ativa ou desativa o botão "Modificar"
            }
        });

        // Torna a janela visível
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new main(); // Cria a interface gráfica
            }
        });
    }
}
