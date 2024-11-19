package src.PlanPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import src.PlanPage.LeftPaneDesign;

public class PlanPage {
    static int LENGTH = 1200;
    static int WIDTH = 750;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlanPage::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Snap Grid Designer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(LENGTH, WIDTH);

        // Design Pane
        JPanel designPane = new LeftPaneDesign();
        designPane.setBackground(Color.LIGHT_GRAY);
        designPane.setPreferredSize(new Dimension((int)(0.75*LENGTH), WIDTH));

        // Snap Grid Pane
        SnapGridPane snapGridPane = new SnapGridPane();

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, designPane, snapGridPane);
        splitPane.setDividerLocation(LENGTH/3);
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
    public int rows = 40;
    public int cols = 25;
    public int gridMatrix[][];

    public SnapGridPane() {
        this.setBackground(Color.WHITE);
        for (int i=0; i<rows; i++){
            for (int j=0; j<rows; j++){
                // this.gridMatrix[i][j] = 0;
            }
        }
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point snapPoint = snapToGrid(e.getPoint());
                if (startPoint == null) {
                    startPoint = snapPoint;
                } else {
                    if (findValidEndpoints(startPoint, 1).contains(snapPoint)){
                        lines.add(new Line(startPoint, snapPoint));
                        startPoint = null;
                        placed = true;
                    }
                }
                repaint();
                placed = false;
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                currentPos = snapToGrid(e.getPoint());
                repaint();
            }
        });


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawLines(g, 1);
        drawStartPointMarker(g);
        drawValidEndpoints(g, startPoint);
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

    private void drawLines(Graphics g, int type) {
        Graphics2D g2d = (Graphics2D) g;
        if (type == 1){
            g2d.setColor(Color.BLACK);
        } else if (type == 2){
            g2d.setColor(Color.CYAN);
        } else if (type == 3) {
            g2d.setColor(Color.ORANGE);
        }
        g2d.setStroke(new BasicStroke(3));
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
            int markerSize = 8; 
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

    private ArrayList<Point> findValidEndpoints(Point startPoint, int type) {
        ArrayList<Point> validPoints = new ArrayList<>();
        for (int j=0; j<33; j++){
            if (startPoint.y != j*30){
                Point p = new Point(startPoint.x, j*30);
                validPoints.add(p);
            }
        }
        for (int i=0; i<53; i++){
            if (startPoint.x != i*30) {
                Point p = new Point(i*30, startPoint.y);
                validPoints.add(p);
            }
        }
        return validPoints;
    }

    private void drawValidEndpoints(Graphics g, Point startPoint) {
        if (startPoint!=null){
            g.setColor(Color.BLUE);
            int markerSize = 8;
            ArrayList<Point> validPoints = findValidEndpoints(startPoint, 1);
            for (int i=0; i<validPoints.size(); i++){
                g.fillOval(validPoints.get(i).x - markerSize / 2, validPoints.get(i).y - markerSize / 2, markerSize, markerSize);
            }
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
