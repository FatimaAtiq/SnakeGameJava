package snakegameeval;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class SidePanel extends JPanel {
    private static final long serialVersionUID = -40557434900946408L;
    private static final Font LARGE_FONT = new Font("Serif", Font.BOLD | Font.ITALIC, 25);
    private static final Font MEDIUM_FONT = new Font("SansSerif", Font.BOLD, 16);
    private static final Font SMALL_FONT = new Font("Monospaced", Font.BOLD, 12);
    private SnakeGame game;

    public SidePanel(SnakeGame game) {
        this.game = game;
        setPreferredSize(new Dimension(300, BoardPanel.ROW_COUNT * BoardPanel.TILE_SIZE));
        setBackground(Color.white);
    }

    private static final int STATISTICS_OFFSET = 150;
    private static final int CONTROLS_OFFSET = 320;
    private static final int MESSAGE_STRIDE = 30;
    private static final int SMALL_OFFSET = 30;
    private static final int LARGE_OFFSET = 50;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawTitle(g2d);
        drawStatistics(g2d);
        drawControls(g2d);
    }
    private void drawTitle(Graphics2D g2d) {
        g2d.setColor(Color.blue);
        g2d.setFont(LARGE_FONT);
        String title = "Snake Game";
        int titleWidth = g2d.getFontMetrics().stringWidth(title);
        g2d.drawString(title, getWidth() / 2 - titleWidth / 2, 50);
    }
    private void drawStatistics(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.setFont(MEDIUM_FONT);
        g2d.drawString("Statistics", SMALL_OFFSET, STATISTICS_OFFSET);
        g2d.setFont(SMALL_FONT);

        int drawY = STATISTICS_OFFSET;
        drawStringWithShadow(g2d, "Total Score: " + game.getScore(), LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        drawStringWithShadow(g2d, "Fruit Eaten: " + game.getFruitsEaten(), LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        drawStringWithShadow(g2d, "Fruit Score: " + game.getNextFruitScore(), LARGE_OFFSET, drawY += MESSAGE_STRIDE);
    }

    private void drawControls(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.setFont(MEDIUM_FONT);
        g2d.drawString("Controls", SMALL_OFFSET, CONTROLS_OFFSET);
        g2d.setFont(SMALL_FONT);

        int drawY = CONTROLS_OFFSET;
        drawStringWithShadow(g2d, "Up: W / Up Arrow key", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        drawStringWithShadow(g2d, "Down: S / Down Arrow key", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        drawStringWithShadow(g2d, "Left: A / Left Arrow key", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        drawStringWithShadow(g2d, "Right: D / Right Arrow key", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        drawStringWithShadow(g2d, "Pause / Resume Game: P", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        drawStringWithShadow(g2d, "Exit: esc key", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
    }

    private void drawStringWithShadow(Graphics2D g2d, String text, int x, int y) {
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g2d.setColor(Color.lightGray);
        g2d.drawString(text, x + 1, y + 1);
        g2d.setColor(Color.black);
        g2d.drawString(text, x, y);
    }
}
