import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Main extends JFrame {
    private JTextField inputField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextArea resultArea;
    private JComboBox<String> confidenceLevelBox;

    public Main() {
        setTitle("Prueba de Kolmogorov-Smirnov");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior para la entrada de datos
        JPanel topPanel = new JPanel();
        inputField = new JTextField(30);
        JButton calculateButton = new JButton("Calcular");
        confidenceLevelBox = new JComboBox<>(new String[]{"90%", "95%", "99%"});
        topPanel.add(new JLabel("Ingrese los números separados por comas:"));
        topPanel.add(inputField);
        topPanel.add(new JLabel("Nivel de confianza:"));
        topPanel.add(confidenceLevelBox);
        topPanel.add(calculateButton);
        add(topPanel, BorderLayout.NORTH);

        // Configuración de la tabla
        String[] columnNames = {"i", "i/n", "ri", "(i-1)/n", "i/n - ri", "ri - (i-1)/n"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);
        add(tableScroll, BorderLayout.CENTER);

        // Área de resultado
        resultArea = new JTextArea(5, 50);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        // Acción del botón
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateKS();
            }
        });
    }

    private void calculateKS() {
        try {
            // Obtener los datos ingresados
            String inputText = inputField.getText();
            String[] tokens = inputText.split(",");
            double[] values = Arrays.stream(tokens).mapToDouble(Double::parseDouble).toArray();
            Arrays.sort(values);
            int n = values.length;

            if (tokens.length > 20) {
                JOptionPane.showMessageDialog(this, "El conjunto debe tener máximo 20 elementos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener el nivel de confianza
            double criticalValue;
            String selectedConfidence = (String) confidenceLevelBox.getSelectedItem();
            if ("90%".equals(selectedConfidence)) {
                criticalValue = 1.22 / Math.sqrt(n);
            } else if ("95%".equals(selectedConfidence)) {
                criticalValue = 1.36 / Math.sqrt(n);
            } else {
                criticalValue = 1.63 / Math.sqrt(n);
            }

            // Limpiar la tabla
            tableModel.setRowCount(0);

            double Dplus = 0, Dminus = 0;
            for (int i = 0; i < n; i++) {
                double i_n = Math.round(((i + 1) / (double) n) * 100.0) / 100.0;
                double i_1_n = Math.round((i / (double) n) * 100.0) / 100.0;
                double dPlus = Math.round((i_n - values[i]) * 100.0) / 100.0;
                double dMinus = Math.round((values[i] - i_1_n) * 100.0) / 100.0;
                Dplus = Math.max(Dplus, dPlus);
                Dminus = Math.max(Dminus, dMinus);

                tableModel.addRow(new Object[]{i + 1, i_n, values[i], i_1_n, dPlus, dMinus});
            }
            double D = Math.round(Math.max(Dplus, Dminus) * 100.0) / 100.0;

            // Mostrar resultados
            resultArea.setText(String.format("D+ = %.2f\nD- = %.2f\nD = %.2f\n", Dplus, Dminus, D));
            if (D > criticalValue) {
                resultArea.append("Los valores no siguen una distribución uniforme.");
            } else {
                resultArea.append("No se detecta diferencia significativa con la distribución uniforme.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en la entrada de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
