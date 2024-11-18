package src.PlanPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class PlanPage {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlanPage::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Snap Grid Designer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 1000);

        // Design Pane
        JPanel designPane = new JPanel();
        designPane.setBackground(Color.LIGHT_GRAY);
        designPane.setPreferredSize(new Dimension(600, 1000));

        // Snap Grid Pane
        SnapGridPane snapGridPane = new SnapGridPane();

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, designPane, snapGridPane);
        splitPane.setDividerLocation(600);
        splitPane.setResizeWeight(0.25);

        frame.add(splitPane);
        frame.setVisible(true);
    }
}

class SnapGridPane extends JPanel {
    private static final int GRID_SIZE = 30;
    private final ArrayList<Line> lines = new ArrayList<>();
    private Point startPoint = null;
    public Point currentPos = null;
    public boolean placed = false;

    public SnapGridPane() {
        this.setBackground(Color.WHITE);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point snapPoint = snapToGrid(e.getPoint());
                if (startPoint == null) {
                    startPoint = snapPoint;
                } else {
                    lines.add(new Line(startPoint, snapPoint));
                    startPoint = null;
                    placed = true;
                }
                repaint();
                placed = false;
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                currentPos = snapToGrid(e.getPoint());
                repaint(); // Ensure the hover marker is updated
            }
        });


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawLines(g);
        drawStartPointMarker(g);
        drawEndPointMarker(g);
        drawCurrentPosition(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        int width = getWidth();
        int height = getHeight();

        for (int x = 0; x < width; x += GRID_SIZE) {
            g.drawLine(x, 0, x, height);
        }
        for (int y = 0; y < height; y += GRID_SIZE) {
            g.drawLine(0, y, width, y);
        }
    }

    private void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3)); // Set line thickness to 3 pixels

        for (Line line : lines) {
            g2d.drawLine(line.start.x, line.start.y, line.end.x, line.end.y);
        }
    }

    private void drawCurrentPosition(Graphics g){
        if (startPoint==null && placed==false){
            g.setColor(Color.PINK);
            int markerSize = 8;
            System.out.print("position is"+currentPos+" ");
            g.fillOval(currentPos.x - markerSize / 2, currentPos.y - markerSize / 2, markerSize, markerSize);
        }
    }

    private void drawStartPointMarker(Graphics g) {
        if (startPoint != null) {
            g.setColor(Color.RED);
            int markerSize = 8; // Size of the marker
            g.fillOval(startPoint.x - markerSize / 2, startPoint.y - markerSize / 2, markerSize, markerSize);
        }
    }

    private void drawEndPointMarker(Graphics g) {
        if (startPoint!=null && placed==false){
            g.setColor(Color.GREEN);
            int markerSize = 8;
            g.fillOval(currentPos.x - markerSize / 2, currentPos.y - markerSize / 2, markerSize, markerSize);
        }
    }

    private Point snapToGrid(Point point) {
        int x = (point.x / GRID_SIZE) * GRID_SIZE;
        int y = (point.y / GRID_SIZE) * GRID_SIZE;
        return new Point(x, y);
    }

    private static class Line {
        Point start, end;

        public Line(Point start, Point end) {
            this.start = start;
            this.end = end;
        }
    }
}
