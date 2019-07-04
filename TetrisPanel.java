import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TetrisPanel extends JFrame {

    Tetris game;

    public TetrisPanel() {
        super("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(12*26+10, 26*23+25);
        game = new Tetris();
        /* game.init(); */
        add(game);

        // BARRA DE MENUS
        JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic('O');
        
        JMenuItem instructionItem = new JMenuItem("Instructions");
        optionsMenu.add(instructionItem);

        JMenuItem highScoreItem = new JMenuItem("High Score");
        optionsMenu.add(highScoreItem);

        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        bar.add(optionsMenu);

        instructionItem.addActionListener(new ActionListener() { // classe interna
            // anonima
            // exibe dialog quando usuario seleciona About...
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(TetrisPanel.this,
                        "Para girar as peças use: \u2191\nPara mover as peças use: \u2190  \u2192\nPara descer a peça use: SPACEBAR\n\nPressione S para começar", "Instructions",
                        JOptionPane.PLAIN_MESSAGE);
            } // fim do metodo actionPerformed
        } // fim da classe interna anonima
        ); // fim da chamada ao addActionListener

        highScoreItem.addActionListener(new ActionListener() { // classe interna
            // anonima
            // exibe dialog quando usuario seleciona About...
            public void actionPerformed(ActionEvent event) {
                Ranking rank = new Ranking();
                String highest = rank.getHighest();
                String message = String.format("HIGHEST SCORE: \n%s", highest);
                JOptionPane.showMessageDialog(TetrisPanel.this, message, "High Score", JOptionPane.PLAIN_MESSAGE);
            } // fim do metodo actionPerformed
        } // fim da classe interna anonima
        ); // fim da chamada ao addActionListener
        

        
        this.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    game.rotate(-1);
                    break;
                case KeyEvent.VK_DOWN:
                    game.rotate(+1);
                    break;
                case KeyEvent.VK_LEFT:
                    game.move(-1);
                    break;
                case KeyEvent.VK_RIGHT:
                    game.move(+1);
                    break;
                case KeyEvent.VK_SPACE:
                    game.dropDown();
                    game.addScore(1);
                    break;
                case KeyEvent.VK_S:
                    game.unpause();
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });

        // Make the falling piece drop every half-ssecond
        new Thread() {
            @Override
            public void run() {
                while (!game.getGameOver()) {
                    try {
                        Thread.sleep(500);
                        game.dropDown();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();
    }

    public void jogar() {
        this.game.init();
    }
}
