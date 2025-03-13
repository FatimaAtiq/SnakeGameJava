package snakegameeval;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.RadialGradientPaint;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {
    private static final long serialVersionUID = -1102632585936750607L;
    public static final int COL_COUNT = 27;
    public static final int ROW_COUNT = 27;
    public static final int TILE_SIZE = 20;
    private static final int EYE_LARGE_INSET = TILE_SIZE / 3;
    private static final int EYE_SMALL_INSET = TILE_SIZE / 6;
    private static final int EYE_LENGTH = TILE_SIZE / 5;
    private static final Font FONT = new Font("Arial", Font.ITALIC | Font.BOLD, 25);
    private SnakeGame game;
    private TileType[] tiles;
    public BoardPanel(SnakeGame game) {
        this.game = game;
        this.tiles = new TileType[ROW_COUNT * COL_COUNT];
        setPreferredSize(new Dimension(COL_COUNT * TILE_SIZE, ROW_COUNT * TILE_SIZE));
        setBackground(Color.green);
    }
    public void clearBoard() {
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = null;
        }
    }
    public void setTile(Point point, TileType type) {
        setTile(point.x, point.y, type);
    }
    public void setTile(int x, int y, TileType type) {
        tiles[y * ROW_COUNT + x] = type;
    }
    public TileType getTile(int x, int y) {
        return tiles[y * ROW_COUNT + x];
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int x = 0; x < COL_COUNT; x++) {
            for (int y = 0; y < ROW_COUNT; y++) {
                TileType type = getTile(x, y);
                if (type != null) {
                    drawTile(x * TILE_SIZE, y * TILE_SIZE, type, g2D);
                }
            }
        }
        g2D.setColor(Color.black);
        g2D.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        for (int x = 0; x < COL_COUNT; x++) {
            for (int y = 0; y < ROW_COUNT; y++) {
                g2D.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, getHeight());
                g2D.drawLine(0, y * TILE_SIZE, getWidth(), y * TILE_SIZE);
            }
        }
        if (game.isGameOver() || game.isNewGame() || game.isPaused()) {
            g2D.setColor(Color.black);
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            String largeMessage = null;
            String smallMessage = null;
            if (game.isNewGame()) {
                largeMessage = "Snake Game!";
                smallMessage = "Press 'Enter' to Start";
            } else if (game.isGameOver()) {
                largeMessage = "Game Over!";
                smallMessage = "Press 'Enter' to Restart";
            } else if (game.isPaused()) {
                largeMessage = "Paused";
                smallMessage = "Press 'P' to Resume";
            }
            g2D.setFont(FONT);
            g2D.drawString(largeMessage, centerX - g2D.getFontMetrics().stringWidth(largeMessage) / 2, centerY - 50);
            g2D.drawString(smallMessage, centerX - g2D.getFontMetrics().stringWidth(smallMessage) / 2, centerY + 50);
        }
    }
    private void drawTile(int x, int y, TileType type, Graphics2D g2D) {
        switch (type) {
            case Fruit:
                int fruitX = x + 2;
                int fruitY = y + 2;
                int fruitSize = TILE_SIZE - 4;

                float centerX = fruitX + fruitSize / 2.0f;
                float centerY = fruitY + fruitSize / 2.0f;
                float radius = fruitSize / 2.0f;

                RadialGradientPaint fruitGradient = new RadialGradientPaint(
                        new Point2D.Float(centerX, centerY), radius,
                        new float[]{0.0f, 1.0f},
                        new Color[]{Color.red.brighter(), Color.red.darker()}
                );
                g2D.setPaint(fruitGradient);
                g2D.fillOval(fruitX, fruitY, fruitSize, fruitSize);
                RadialGradientPaint highlightGradient = new RadialGradientPaint(
                        new Point2D.Float(centerX, centerY), radius,
                        new float[]{0.0f, 0.6f},
                        new Color[]{new Color(255, 255, 255, 128), new Color(255, 255, 255, 0)}
                );
                g2D.setPaint(highlightGradient);
                g2D.fillOval(fruitX, fruitY, fruitSize, fruitSize);
                break;

            case SnakeBody:
                GradientPaint bodyGradient = new GradientPaint(x, y, Color.pink, x + TILE_SIZE, y + TILE_SIZE, Color.black, true);
                g2D.setPaint(bodyGradient);
                g2D.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                break;

            case SnakeHead:
                GradientPaint headGradient = new GradientPaint(x, y, Color.darkGray.darker(), x + TILE_SIZE, y + TILE_SIZE, Color.darkGray.brighter(), true);
                g2D.setPaint(headGradient);
                g2D.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                g2D.setColor(Color.BLACK);
                int eyeSize = TILE_SIZE / 5;
                int eyeOffsetX = TILE_SIZE / 5;
                int eyeOffsetY = TILE_SIZE / 4;

                switch (game.getDirection()) {
                    case North:
                        g2D.fillOval(x + eyeOffsetX, y + eyeOffsetY, eyeSize, eyeSize);
                        g2D.fillOval(x + TILE_SIZE - eyeOffsetX - eyeSize, y + eyeOffsetY, eyeSize, eyeSize);
                        break;
                    case South:
                        g2D.fillOval(x + eyeOffsetX, y + TILE_SIZE - eyeOffsetY - eyeSize, eyeSize, eyeSize);
                        g2D.fillOval(x + TILE_SIZE - eyeOffsetX - eyeSize, y + TILE_SIZE - eyeOffsetY - eyeSize, eyeSize, eyeSize);
                        break;
                    case West:
                        g2D.fillOval(x + eyeOffsetY, y + eyeOffsetX, eyeSize, eyeSize);
                        g2D.fillOval(x + eyeOffsetY, y + TILE_SIZE - eyeOffsetX - eyeSize, eyeSize, eyeSize);
                        break;
                    case East:
                        g2D.fillOval(x + TILE_SIZE - eyeOffsetY - eyeSize, y + eyeOffsetX, eyeSize, eyeSize);
                        g2D.fillOval(x + TILE_SIZE - eyeOffsetY - eyeSize, y + TILE_SIZE - eyeOffsetX - eyeSize, eyeSize, eyeSize);
                        break;
                }
                break;
        }
    }
}