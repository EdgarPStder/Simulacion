import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JTextField semillaField, cantidadField, constanteaField, constantecField, moduloField;
    private JTable resultadoTabla;
    private DefaultTableModel tableModel;

    public Main() {
        setTitle("Generador de Números Pseudoaleatorios - Algoritmo Lineal");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelEntrada = new JPanel(new GridLayout(0, 2, 5, 5));
        panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelEntrada.add(new JLabel("Semilla (X):"));
        semillaField = new JTextField(10);
        panelEntrada.add(semillaField);

        panelEntrada.add(new JLabel("Constante (a):"));
        constanteaField = new JTextField(10);
        panelEntrada.add(constanteaField);

        panelEntrada.add(new JLabel("Constante aditiva (c):"));
        constantecField = new JTextField(10);
        panelEntrada.add(constantecField);

        panelEntrada.add(new JLabel("Modulo (m):"));
        moduloField = new JTextField(10);
        panelEntrada.add(moduloField);

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

        tableModel = new DefaultTableModel(new String[]{"X", "(a*X+c)mod(m)", "Resultado", "Division", "r"}, 0);
        resultadoTabla = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultadoTabla);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void generarNumeros() {
        try {
            int semilla = Integer.parseInt(semillaField.getText());
            int ca = Integer.parseInt(constanteaField.getText());
            int cc = Integer.parseInt(constantecField.getText());
            int mod = Integer.parseInt(moduloField.getText());
            int cantidad = Integer.parseInt(cantidadField.getText());

            if (semilla <= 0 ) {
                JOptionPane.showMessageDialog(this, "Las semilla deben ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (ca <= 0 ) {
                JOptionPane.showMessageDialog(this, "Las constante deben ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cc <= 0 ) {
                JOptionPane.showMessageDialog(this, "Las constate aditiva deben ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (mod <= 0 ) {
                JOptionPane.showMessageDialog(this, "El mod deben ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.setRowCount(0); // Limpiar tabla antes de agregar nuevos datos

            int Xi = semilla; // Valor inicial de la semilla
            int a = ca; // Constante multiplicativa
            int c = cc; // Constante aditiva
            int m = mod; // Módulo

            // Generar los números aleatorios
            for (int i = 0; i < cantidad; i++) {
                // Generación de número aleatorio según la fórmula (a * X + c) mod m
                Xi = (a * Xi + c) % m;
                double ri = (double) Xi / (m-1); // División para obtener un valor en el rango [0, 1)

                // Redondear 'ri' a 4 cifras decimales
                //String riRedondeado = String.format("%.5f", ri);

                // Agregar una nueva fila en la tabla
                tableModel.addRow(new Object[]{Xi, "(" + a + "*" + Xi + "+" + c + ") mod " + m, Xi, Xi+"/"+(m-1), ri});
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

