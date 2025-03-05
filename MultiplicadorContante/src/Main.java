import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JTextField semillaField, constanteField, cantidadField;
    private JTable resultadoTabla;
    private DefaultTableModel tableModel;

    public Main() {
        setTitle("Generador de Números Pseudoaleatorios - Productos Medios");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelEntrada = new JPanel(new GridLayout(0, 2, 5, 5));
        panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelEntrada.add(new JLabel("Semilla (X0):"));
        semillaField = new JTextField(10);
        panelEntrada.add(semillaField);

        panelEntrada.add(new JLabel("Constante (a):"));
        constanteField = new JTextField(10);
        panelEntrada.add(constanteField);

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

        tableModel = new DefaultTableModel(new String[]{"Y", "(a * X)", "Resultado", "X", "r"}, 0);
        resultadoTabla = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultadoTabla);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void generarNumeros() {
        try {
            int semilla = Integer.parseInt(semillaField.getText());
            int constante = Integer.parseInt(constanteField.getText());
            int cantidad = Integer.parseInt(cantidadField.getText());

            // Validar que la semilla y constante tengan más de 3 dígitos
            if (String.valueOf(semilla).length() <= 3 || String.valueOf(constante).length() <= 3) {
                JOptionPane.showMessageDialog(this, "La semilla y la constante deben tener más de 3 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.setRowCount(0);  // Limpiar tabla
            int D = String.valueOf(semilla).length();
            int X0 = semilla;
            int a = constante;

            for (int i = 0; i < cantidad; i++) {
                // Calcular Yi = a * Xi
                long Yi = (long) a * X0;
                String YiStr = String.format("%0" + (2 * D) + "d", Yi);  // Asegurarse de que Yi tenga suficientes dígitos

                // Obtener los D dígitos del centro de Yi
                int centerStart = (YiStr.length() - D) / 2;
                String XiStr = YiStr.substring(centerStart, centerStart + D);

                // Si no se pueden obtener D dígitos, agregar ceros a la izquierda
                if (XiStr.length() < D) {
                    XiStr = String.format("%0" + D + "d", Long.parseLong(YiStr));
                }

                int Xi = Integer.parseInt(XiStr);  // Xi para el siguiente ciclo
                double ri = (double) Xi / Math.pow(10, D);  // r como fracción de Xi

                // Agregar fila a la tabla con los datos correspondientes
                tableModel.addRow(new Object[]{X0, "(" + a + " * " + X0 + ")", YiStr, XiStr, ri});

                // Preparar para la siguiente iteración
                X0 = Xi;
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
