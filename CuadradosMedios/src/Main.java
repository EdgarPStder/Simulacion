import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JTextField semillaField, cantidadField;
    private JTable resultadoTabla;
    private DefaultTableModel tableModel;

    public Main() {
        setTitle("Generador de Números Pseudoaleatorios - Cuadrados Medios");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelEntrada = new JPanel(new GridLayout(0, 2, 5, 5));
        panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelEntrada.add(new JLabel("Semilla (Y):"));
        semillaField = new JTextField(10);
        panelEntrada.add(semillaField);

        panelEntrada.add(new JLabel("Cantidad de números a generar:"));
        cantidadField = new JTextField(10);
        panelEntrada.add(cantidadField);

        JButton generarBtn = new JButton("Generar");
        generarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarNumeros();
            }
        });
        panelEntrada.add(new JLabel()); // Espacio vacío para alineación
        panelEntrada.add(generarBtn);

        add(panelEntrada, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Y", "(Y)^2", "Resultado", "X", "r"}, 0);
        resultadoTabla = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultadoTabla);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void generarNumeros() {
        try {
            int semilla = Integer.parseInt(semillaField.getText());
            int cantidad = Integer.parseInt(cantidadField.getText());

            if (String.valueOf(semilla).length() <= 3) {
                JOptionPane.showMessageDialog(this, "La semilla debe tener más de 3 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.setRowCount(0);
            int D = String.valueOf(semilla).length();
            int Xi = semilla;

            for (int i = 0; i < cantidad; i++) {
                long Yi = (long) Xi * Xi;
                String YiStr = String.format("%0" + (2 * D) + "d", Yi);
                String XiStr = YiStr.substring((YiStr.length() - D) / 2, (YiStr.length() - D) / 2 + D);
                double ri = (double) Integer.parseInt(XiStr) / Math.pow(10, D);

                tableModel.addRow(new Object[]{Xi, "(" + Xi + ")^2", YiStr, XiStr, ri});
                Xi = Integer.parseInt(XiStr);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
