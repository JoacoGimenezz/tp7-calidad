package calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Calculadora con interfaz gráfica usando Java Swing.
 * Diseño oscuro, estilo moderno.
 */
public class Calculadora extends JFrame {

    // ── Lógica ───────────────────────────────────────────────────
    private final CalculadoraLogica logica = new CalculadoraLogica();

    // ── Colores ──────────────────────────────────────────────────
    private static final Color C_FONDO       = new Color(32,  32,  32);
    private static final Color C_DISPLAY     = new Color(32,  32,  32);
    private static final Color C_BTN_NUM     = new Color(51,  51,  51);
    private static final Color C_BTN_OP      = new Color(58,  58,  76);
    private static final Color C_BTN_IGUAL   = new Color(0,  120, 212);
    private static final Color C_BTN_ESP     = new Color(42,  42,  42);
    private static final Color C_TEXTO       = Color.WHITE;
    private static final Color C_HISTORIAL   = new Color(160, 160, 160);

    // ── Displays ─────────────────────────────────────────────────
    private JLabel lblHistorial;
    private JLabel lblDisplay;

    // ── Constructor ──────────────────────────────────────────────
    public Calculadora() {
        configurarVentana();
        add(crearDisplay(), BorderLayout.NORTH);
        add(crearPanelBotones(), BorderLayout.CENTER);
        configurarTeclado();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ── Ventana ──────────────────────────────────────────────────
    private void configurarVentana() {
        setTitle("Calculadora");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(C_FONDO);
        setLayout(new BorderLayout());
    }

    // ── Display ──────────────────────────────────────────────────
    private JPanel crearDisplay() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(C_DISPLAY);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));

        lblHistorial = new JLabel(" ");
        lblHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblHistorial.setForeground(C_HISTORIAL);
        lblHistorial.setAlignmentX(Component.RIGHT_ALIGNMENT);

        lblDisplay = new JLabel("0");
        lblDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 40));
        lblDisplay.setForeground(C_TEXTO);
        lblDisplay.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lblDisplay.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(lblHistorial);
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblDisplay);
        panel.add(Box.createVerticalStrut(8));

        // Línea separadora
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(60, 60, 60));
        panel.add(sep);

        return panel;
    }

    // ── Botones ──────────────────────────────────────────────────
    private static final String[][] LAYOUT = {
        { "C",   "CE",  "%",   "÷" },
        { "7",   "8",   "9",   "×" },
        { "4",   "5",   "6",   "−" },
        { "1",   "2",   "3",   "+" },
        { "+/-", "0",   ".",   "=" }
    };

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new GridLayout(5, 4, 6, 6));
        panel.setBackground(C_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        for (String[] fila : LAYOUT) {
            for (String texto : fila) {
                panel.add(crearBoton(texto));
            }
        }
        return panel;
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setForeground(C_TEXTO);
        btn.setBackground(colorBoton(texto));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setPreferredSize(new Dimension(76, 62));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover
        Color base = colorBoton(texto);
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setBackground(aclarar(base, 20));
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setBackground(base);
            }
            @Override public void mousePressed(MouseEvent e) {
                btn.setBackground(aclarar(base, -10));
            }
            @Override public void mouseReleased(MouseEvent e) {
                btn.setBackground(base);
            }
        });

        btn.addActionListener(e -> procesar(texto));
        return btn;
    }

    // ── Teclado físico ───────────────────────────────────────────
    private void configurarTeclado() {
        KeyStroke[] teclas = {
            KeyStroke.getKeyStroke(KeyEvent.VK_0,  0), KeyStroke.getKeyStroke(KeyEvent.VK_1,  0),
            KeyStroke.getKeyStroke(KeyEvent.VK_2,  0), KeyStroke.getKeyStroke(KeyEvent.VK_3,  0),
            KeyStroke.getKeyStroke(KeyEvent.VK_4,  0), KeyStroke.getKeyStroke(KeyEvent.VK_5,  0),
            KeyStroke.getKeyStroke(KeyEvent.VK_6,  0), KeyStroke.getKeyStroke(KeyEvent.VK_7,  0),
            KeyStroke.getKeyStroke(KeyEvent.VK_8,  0), KeyStroke.getKeyStroke(KeyEvent.VK_9,  0),
            KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0, 0), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0),
            KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2, 0), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD3, 0),
            KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, 0), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5, 0),
            KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD6, 0), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD7, 0),
            KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD8, 0), KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD9, 0),
            KeyStroke.getKeyStroke(KeyEvent.VK_ADD,      0), KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, 0),
            KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY, 0), KeyStroke.getKeyStroke(KeyEvent.VK_DIVIDE,   0),
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,    0), KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS,   0),
            KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            KeyStroke.getKeyStroke(KeyEvent.VK_DECIMAL, 0),    KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0),
        };

        getRootPane().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_0, KeyEvent.VK_NUMPAD0 -> procesar("0");
                    case KeyEvent.VK_1, KeyEvent.VK_NUMPAD1 -> procesar("1");
                    case KeyEvent.VK_2, KeyEvent.VK_NUMPAD2 -> procesar("2");
                    case KeyEvent.VK_3, KeyEvent.VK_NUMPAD3 -> procesar("3");
                    case KeyEvent.VK_4, KeyEvent.VK_NUMPAD4 -> procesar("4");
                    case KeyEvent.VK_5, KeyEvent.VK_NUMPAD5 -> procesar("5");
                    case KeyEvent.VK_6, KeyEvent.VK_NUMPAD6 -> procesar("6");
                    case KeyEvent.VK_7, KeyEvent.VK_NUMPAD7 -> procesar("7");
                    case KeyEvent.VK_8, KeyEvent.VK_NUMPAD8 -> procesar("8");
                    case KeyEvent.VK_9, KeyEvent.VK_NUMPAD9 -> procesar("9");
                    case KeyEvent.VK_ADD      -> procesar("+");
                    case KeyEvent.VK_SUBTRACT -> procesar("−");
                    case KeyEvent.VK_MULTIPLY -> procesar("×");
                    case KeyEvent.VK_DIVIDE   -> procesar("÷");
                    case KeyEvent.VK_ENTER, KeyEvent.VK_EQUALS -> procesar("=");
                    case KeyEvent.VK_BACK_SPACE -> procesar("⌫");
                    case KeyEvent.VK_ESCAPE     -> procesar("C");
                    case KeyEvent.VK_DECIMAL, KeyEvent.VK_PERIOD -> procesar(".");
                }
            }
        });
        setFocusable(true);
        addKeyListener(getRootPane().getKeyListeners()[0]);
    }

    // ── Lógica de procesamiento ──────────────────────────────────
    private void procesar(String entrada) {
        switch (entrada) {
            case "0","1","2","3","4","5","6","7","8","9","." ->
                logica.ingresarDigito(entrada);
            case "+"  -> logica.establecerOperador("+");
            case "−"  -> logica.establecerOperador("-");
            case "×"  -> logica.establecerOperador("*");
            case "÷"  -> logica.establecerOperador("/");
            case "="  -> logica.calcular();
            case "C"  -> logica.limpiarTodo();
            case "CE" -> logica.limpiarEntrada();
            case "%"  -> logica.porcentaje();
            case "+/-"-> logica.cambiarSigno();
            case "⌫"  -> logica.retroceso();
        }
        actualizarDisplay();
    }

    private void actualizarDisplay() {
        String texto = logica.getEntradaActual();
        lblDisplay.setText(texto);
        lblHistorial.setText(logica.getHistorial().isEmpty() ? " " : logica.getHistorial());

        // Ajusta tamaño de fuente según longitud
        int size = texto.length() <= 9 ? 40 : texto.length() <= 13 ? 30 : 22;
        lblDisplay.setFont(new Font("Segoe UI", Font.PLAIN, size));
    }

    // ── Helpers ──────────────────────────────────────────────────
    private static Color colorBoton(String texto) {
        return switch (texto) {
            case "="              -> C_BTN_IGUAL;
            case "+","−","×","÷" -> C_BTN_OP;
            case "C","CE","%","+/-" -> C_BTN_ESP;
            default               -> C_BTN_NUM;
        };
    }

    private static Color aclarar(Color c, int cantidad) {
        return new Color(
            Math.clamp(c.getRed()   + cantidad, 0, 255),
            Math.clamp(c.getGreen() + cantidad, 0, 255),
            Math.clamp(c.getBlue()  + cantidad, 0, 255)
        );
    }

    // ── Main ─────────────────────────────────────────────────────
    public static void main(String[] args) {
        // Look & feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(Calculadora::new);
    }
}
