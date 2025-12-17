import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class JogoDaVelha {
    private JFrame frame;
    private JPanel mainPanel;
    private JButton[] buttons;
    private Tabuleiro tabuleiro;
    private Jogador jogador2;
    private boolean simboloHumano = true;
    private int escolha;
    private JLabel statusLabel;
    private JButton menuButton;

    private final Color BG_COLOR = new Color(245, 245, 245);
    private final Color PANEL_COLOR = new Color(255, 255, 255);
    private final Color BUTTON_COLOR = new Color(240, 240, 240);
    private final Color BUTTON_HOVER = new Color(230, 230, 230);
    private final Color TEXT_COLOR = new Color(60, 60, 60);
    private final Color ACCENT_COLOR = new Color(100, 149, 237);
    private final Color O_COLOR = new Color(70, 130, 180);
    private final Color X_COLOR = new Color(220, 20, 60);
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 48);
    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);

    public JogoDaVelha(int tipoJogo, int escolha) {
        this.tabuleiro = new Tabuleiro();
        
        if (tipoJogo == 1) {
            jogador2 = new Minimax(false);
        } else {
            jogador2 = new Heuristica(false);
        }
        
        this.escolha = escolha;
        initializeGUI();
        atualizarTabuleiro();
        
        if (escolha == 2) {
            jogadaMaquina();
        }
    }

    private void initializeGUI() {
        frame = new JFrame("Jogo da Velha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(BG_COLOR);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(PANEL_COLOR);
                g2d.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 20, 20);
            }
        };
        mainPanel.setLayout(new GridLayout(3, 3, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
        mainPanel.setOpaque(false);

        buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = createGameButton();
            final int pos = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jogadaHumana(pos);
                }
            });
            mainPanel.add(buttons[i]);
        }

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setOpaque(false);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(MAIN_FONT);
        statusLabel.setForeground(TEXT_COLOR);

        menuButton = new JButton("Voltar ao Menu");
        menuButton.setFont(MAIN_FONT);
        menuButton.setBackground(ACCENT_COLOR);
        menuButton.setForeground(Color.WHITE);
        menuButton.setFocusPainted(false);
        menuButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        menuButton.setVisible(false);
        menuButton.addActionListener(e -> {
            frame.dispose();
            mostrarMenuInicial();
        });

        menuButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuButton.setBackground(ACCENT_COLOR.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuButton.setBackground(ACCENT_COLOR);
            }
        });

        statusPanel.add(statusLabel, BorderLayout.CENTER);
        statusPanel.add(menuButton, BorderLayout.SOUTH);

        frame.add(createHeaderPanel(), BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        
        JLabel titleLabel = new JLabel("JOGO DA VELHA");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        
        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JButton createGameButton() {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFont(BUTTON_FONT);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(BUTTON_HOVER);
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }

    private void atualizarTabuleiro() {
        Boolean[][] board = tabuleiro.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int pos = i * 3 + j;
                if (board[i][j] == null) {
                    buttons[pos].setText("");
                } else {
                    buttons[pos].setText(board[i][j] ? "O" : "X");
                    buttons[pos].setForeground(board[i][j] ? O_COLOR : X_COLOR);
                }
            }
        }
        
        atualizarStatus();
    }

    private void atualizarStatus() {
        Boolean vencedor = tabuleiro.checarVencedor();
        if (vencedor != null) {
            if (vencedor) {
                statusLabel.setText("Jogador (O) venceu!");
            } else {
                statusLabel.setText("Máquina (X) venceu!");
            }
            desabilitarBotoes();
            menuButton.setVisible(true);
        } else if (tabuleiro.tabuleiroCheio()) {
            statusLabel.setText("Empate!");
            desabilitarBotoes();
            menuButton.setVisible(true);
        } else {
            menuButton.setVisible(false);
            if ((escolha == 1 && tabuleiro.getJogadasFeitas() % 2 == 0) || 
                (escolha == 2 && tabuleiro.getJogadasFeitas() % 2 == 1)) {
                statusLabel.setText("Sua vez (O)");
            } else {
                statusLabel.setText("Vez da máquina (X)");
            }
        }
    }

    private void desabilitarBotoes() {
        for (JButton button : buttons) {
            button.setEnabled(false);
            button.setBackground(BUTTON_COLOR);
        }
    }

    private void jogadaHumana(int pos) {
        if (tabuleiro.jogar(pos, simboloHumano)) {
            atualizarTabuleiro();
            if (!tabuleiro.verificaFimDeJogo()) {
                jogadaMaquina();
            }
        }
    }

    private void jogadaMaquina() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void done() {
                jogador2.jogar(tabuleiro);
                atualizarTabuleiro();
            }
        };
        worker.execute();
    }

    private static void mostrarMenuInicial() {
        JFrame configFrame = new JFrame("Configuração do Jogo");
        configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        configFrame.setSize(400, 300);
        configFrame.setLayout(new BorderLayout());
        configFrame.getContentPane().setBackground(new Color(245, 245, 245));
        configFrame.setLocationRelativeTo(null);

        JPanel configPanel = new JPanel();
        configPanel.setLayout(new GridLayout(5, 1, 10, 10));
        configPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));
        configPanel.setBackground(new Color(255, 255, 255));

        JLabel titleLabel = new JLabel("Configuração do Jogo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(60, 60, 60));

        JLabel tipoLabel = new JLabel("Tipo de jogo:");
        tipoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Humano vs Máquina (Minimax)", "Humano vs Máquina (Heurística)"});
        tipoCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JLabel inicioLabel = new JLabel("Quem começa?");
        inicioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JComboBox<String> inicioCombo = new JComboBox<>(new String[]{"Jogador (O)", "Máquina (X)"});
        inicioCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton startButton = new JButton("Iniciar Jogo");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        startButton.setBackground(new Color(100, 149, 237));
        startButton.setForeground(Color.WHITE);
        startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> {
            int tipoJogo = tipoCombo.getSelectedIndex() + 1;
            int escolha = inicioCombo.getSelectedIndex() + 1;
            configFrame.dispose();
            new JogoDaVelha(tipoJogo, escolha);
        });

        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(65, 105, 225));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(100, 149, 237));
            }
        });

        configPanel.add(titleLabel);
        configPanel.add(tipoLabel);
        configPanel.add(tipoCombo);
        configPanel.add(inicioLabel);
        configPanel.add(inicioCombo);

        configFrame.add(configPanel, BorderLayout.CENTER);
        configFrame.add(startButton, BorderLayout.SOUTH);
        configFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            mostrarMenuInicial();
        });
    }
}