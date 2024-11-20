package src.PlanPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlanPage {
    static int LENGTH = 40;
    static int WIDTH = 25;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlanPage::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Snap Grid Designer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(LENGTH*30, WIDTH*30);

        // Design Pane
        JPanel designPane = new LeftPaneDesign();
        designPane.setBackground(Color.LIGHT_GRAY);
        designPane.setPreferredSize(new Dimension((int)(0.75*LENGTH*30), WIDTH*30));

        // Snap Grid Pane
        SnapGridPane snapGridPane = new SnapGridPane();

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, designPane, snapGridPane);
        splitPane.setDividerLocation(LENGTH*30/3);
        splitPane.setResizeWeight(0.25);

        frame.add(splitPane);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
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
    public int gridMatrix[][] = new int[40*30][25*30];
    public ArrayList<Room> rooms = new ArrayList<>();
    

    public SnapGridPane() {
        selectionState.colorMap.put(1, Color.BLACK); // Wall
        selectionState.colorMap.put(2, Color.WHITE); // Door
        selectionState.colorMap.put(3, Color.BLUE); // Window
        selectionState.colorMap.put(4, Color.RED); // Kitchen
        selectionState.colorMap.put(5, Color.GREEN); // Bedroom
        selectionState.colorMap.put(6, Color.BLUE); // Bathroom
        selectionState.colorMap.put(7, Color.ORANGE); // Dining area
        selectionState.colorMap.put(8, Color.YELLOW); // Drawing room

        /*selectionState.selection.put("view", 2);
        selectionState.selection.put("room", 4);
        selectionState.selection.put("boundary", 2);
        selectionState.selection.put("fixture", 1);*/
        this.setBackground(Color.WHITE);
        for (int i=0; i<rows; i++){
            for (int j=0; j<rows; j++){
                this.gridMatrix[i][j] = 0;
            }
        }
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point snapPoint = snapToGrid(e.getPoint());
                if (startPoint == null) {
                    startPoint = snapPoint;
                } else {
                    if (selectionState.selection.get("view") == 1 && findValidEndpoints(startPoint, 1).contains(snapPoint)){
                        lines.add(new Line(startPoint, snapPoint, selectionState.selection.get("boundary")));
                        startPoint = null;
                        placed = true;
                    } else if (selectionState.selection.get("view") == 2 && selectionState.selection.get("room") !=0) {
                        //System.out.println(" "+startPoint+snapPoint);
                        matrixUtilities.addRoom(gridMatrix, startPoint, snapPoint, 4);
                        lines.add(new Line(startPoint, new Point (snapPoint.x, startPoint.y), 1));
                        lines.add(new Line(startPoint, new Point (startPoint.x, snapPoint.y), 1));
                        lines.add(new Line(snapPoint, new Point (startPoint.x, snapPoint.y), 1));
                        lines.add(new Line(snapPoint, new Point (snapPoint.x, startPoint.y), 1));
                        rooms.add(new Room(startPoint, snapPoint, selectionState.selection.get("room")));
                        System.out.println(" "+selectionState.selection.get("room"));
                        // if (selectionState.selection.get("room")<8){
                        //     selectionState.selection.put("room", selectionState.selection.get("room")+1);
                        // } else {
                        //     selectionState.selection.put("room", 4);
                        // }
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
        drawRooms(g);
        drawGrid(g);
        drawLines(g);
        drawStartPointMarker(g);
        drawValidEndpoints(g, startPoint, selectionState.selection.get("view"));
        drawEndPointMarker(g);
        drawCurrentPosition(g);
    }

    private void drawGrid(Graphics g) {
        Color original = g.getColor();
        g.setColor(Color.LIGHT_GRAY);

        int width = getWidth();
        int height = getHeight();

        for (int x = 0; x < width; x += GRID_SIZE) {
            g.drawLine(x, 0, x, height);
        }
        for (int y = 0; y < height; y += GRID_SIZE) {
            g.drawLine(0, y, width, y);
        }
        g.setColor(original);
    }

    private void drawLines(Graphics g) {
        Color original = g.getColor();
        Graphics2D g2d =(Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        for (Line line : lines) {
            if (line.id == 1){
                g2d.setColor(Color.BLACK);
            } else if (line.id == 3){
                g2d.setColor(Color.CYAN);
            } else if (line.id == 2) {
                g2d.setColor(Color.WHITE);
            }
            g2d.drawLine(line.start.x, line.start.y, line.end.x, line.end.y);
        }
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(original);
    }

    private void drawCurrentPosition(Graphics g){
        Color original = g.getColor();
        if (currentPos!=null && startPoint==null && placed==false){
            g.setColor(Color.PINK);
            int markerSize = 8;
            //System.out.print("position is"+currentPos+" ");
            g.fillOval(currentPos.x - markerSize / 2, currentPos.y - markerSize / 2, markerSize, markerSize);
        }
        g.setColor(original);
    }

    private void drawStartPointMarker(Graphics g) {
        Color original = g.getColor();
        if (startPoint != null) {
            g.setColor(Color.RED);
            int markerSize = 8; 
            g.fillOval(startPoint.x - markerSize / 2, startPoint.y - markerSize / 2, markerSize, markerSize);
        }
        g.setColor(original);
    }

    private void drawEndPointMarker(Graphics g) {
        Color original = g.getColor();
        if (startPoint!=null && placed==false){
            g.setColor(Color.GREEN);
            int markerSize = 8;
            g.fillOval(currentPos.x - markerSize / 2, currentPos.y - markerSize / 2, markerSize, markerSize);
        }
        g.setColor(original);
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

    private void drawValidEndpoints(Graphics g, Point startPoint, int view) {
        Color original = g.getColor();
        if (view != 1){
            return;
        }
        if (startPoint!=null){
            g.setColor(Color.BLUE);
            int markerSize = 8;
            ArrayList<Point> validPoints = findValidEndpoints(startPoint, 1);
            for (int i=0; i<validPoints.size(); i++){
                g.fillOval(validPoints.get(i).x - markerSize / 2, validPoints.get(i).y - markerSize / 2, markerSize, markerSize);
            }
        }
        g.setColor(original);
    }

    private void drawRooms(Graphics g){
        Color original = g.getColor();
        for (Room room: rooms) {
            g.setColor(selectionState.colorMap.get(room.id));
            g.fillRect(room.topLeft.x, room.topLeft.y, room.width+1, room.height+1);
            // System.out.println(" "+room);
        }
        g.setColor(original);
    }

    private Point snapToGrid(Point point) {
        int x = (point.x / GRID_SIZE) * GRID_SIZE;
        int y = (point.y / GRID_SIZE) * GRID_SIZE;
        return new Point(x, y);
    }

    private class Line {
        Point start, end;
        int id;

        public Line(Point start, Point end, int _id) {
            this.start = start;
            this.end = end;
            this.id = _id;
        }
    }

    private class Room{
        Point topLeft, bottomRight, topRight, bottomLeft;
        int width, height, id;
        public Room(Point start, Point end, int _id){
            if (start.x<end.x && start.y<end.y){
                this.topLeft=new Point(start.x, start.y);
                this.bottomRight=new Point(end.x, end.y);
            } else if (start.x<end.x && start.y>end.y){
                this.topLeft=new Point(start.x, end.y);
                this.bottomRight=new Point(end.x, start.y);
            } else if (start.x>end.x && start.y<end.y){
                this.topLeft=new Point(end.x, start.y);
                this.bottomRight=new Point(start.x, end.y);
            } else {
                this.topLeft=new Point(end.x, end.y);
                this.bottomRight=new Point(start.x, start.y);
            }
            this.bottomLeft=new Point(topLeft.x, bottomRight.y);
            this.width = bottomRight.x-topLeft.x;
            this.height = bottomRight.y-topLeft.y;
            this.id = _id;
        }
    }
}
