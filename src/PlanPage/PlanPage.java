package src.PlanPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

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
    private Point startPoint = null;
    public Point currentPos = null;
    public boolean placed = false;
    public int rows = 40;
    public int cols = 25;
    public int gridMatrix[][] = new int[40*30][25*30];
    public static ArrayList<Line> lines = new ArrayList<>();
    public static ArrayList<Room> rooms = new ArrayList<>();
    public Point dragPoint = null;
    boolean dragging = false;
    Room selectedRoom = null;

    Room movedRoom = null;
    boolean moving = false;
    int dist_x=0;
    int dist_y=0;
    int _width = 0;
    int _height = 0;
    int _id;

    

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
                dragging = true;
                Point snapPoint = snapToGrid(e.getPoint());
                if (selectionState.selection.get("view") == 0){
                    boolean selected = false;
                    for (Room room: rooms){
                        if (e.getPoint().x>room.topLeft.x && e.getPoint().x<room.bottomRight.x 
                        && e.getPoint().y>room.topLeft.y && e.getPoint().y<room.bottomRight.y){
                            selectedRoom = room;
                            selectionState.selectedRoom = selectedRoom;
                            selected = true;
                            repaint();
                            break;
                        }
                    }
                    if (selected==false){
                        selectedRoom = null;
                    }
                    repaint();
                }
                else if (startPoint == null) {
                    startPoint = snapPoint;
                } else {
                    if (selectionState.selection.get("view") == 1 && findValidEndpoints(startPoint, 1).contains(snapPoint)){
                        lines.add(new Line(startPoint, snapPoint, selectionState.selection.get("boundary")));
                        startPoint = null;
                        placed = true;
                        repaint();
                        placed = false;
                    } else if (selectionState.selection.get("view") == 2 && selectionState.selection.get("room") !=0) {
                        //System.out.println(" "+startPoint+snapPoint);
                        if (Utils.overlap_checker(rooms, new Room(startPoint, snapPoint, selectionState.selection.get("room"), new ArrayList<Line>())) == 1){
                            System.out.println("OVERLAP!!!!");
                            startPoint = null;
                            placed = true;
                            repaint();
                            placed = false;
                        } else {
                            matrixUtilities.addRoom(gridMatrix, startPoint, snapPoint, 4);
                            Line l1, l2, l3, l4;
                            l1 = new Line(startPoint, new Point (snapPoint.x, startPoint.y), 1);
                            l2= new Line(startPoint, new Point (startPoint.x, snapPoint.y), 1);
                            l3 = new Line(snapPoint, new Point (startPoint.x, snapPoint.y), 1);
                            l4 = new Line(snapPoint, new Point (snapPoint.x, startPoint.y), 1);
                            lines.add(l1);
                            lines.add(l2);
                            lines.add(l3);
                            lines.add(l4);
                            ArrayList<Line> newWalls = new ArrayList<>();
                            newWalls.add(l1);
                            newWalls.add(l2);
                            newWalls.add(l3);
                            newWalls.add(l4);

                            rooms.add(new Room(startPoint, snapPoint, selectionState.selection.get("room"), newWalls));
                            //System.out.println(" "+selectionState.selection.get("room"));
                            startPoint = null;
                            placed = true;
                            repaint();
                            placed = false;
                        }
                    }
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e){
                dragging = false;
                if (moving == true){
                    rooms.remove(selectedRoom);
                    if (Utils.overlap_checker(rooms, movedRoom)==1){
                        System.out.println("OVERLAP!!!!");
                        rooms.add(selectedRoom);
                    } else {
                        for (Line line: selectedRoom.walls){
                            lines.remove(line);
                        }
                        Line l1, l2, l3, l4;
                        l1 = new Line(movedRoom.topLeft, new Point(movedRoom.topLeft.x, movedRoom.bottomRight.y), 1);
                        l2 = new Line(movedRoom.topLeft, new Point(movedRoom.bottomRight.x, movedRoom.topLeft.y), 1);
                        l3 = new Line(movedRoom.bottomRight, new Point(movedRoom.topLeft.x, movedRoom.bottomRight.y), 1);
                        l4 = new Line(movedRoom.bottomRight, new Point(movedRoom.bottomRight.x, movedRoom.topLeft.y), 1);
                        lines.add(l1);
                        lines.add(l2);
                        lines.add(l3);
                        lines.add(l4);
                        ArrayList<Line> newWalls = new ArrayList<>();
                        newWalls.add(l1);
                        newWalls.add(l2);
                        newWalls.add(l3);
                        newWalls.add(l4);
                        movedRoom.walls = newWalls;
                        rooms.add(movedRoom);
                    }
                    moving = false;
                    dist_x = 0;
                    dist_y = 0;
                    movedRoom = null;
                    _width = 0;
                    _height = 0;
                    _id = 0;
                    selectedRoom = null;
                    repaint();
                }

                //System.out.println(startPoint+" "+dragPoint);
                if (startPoint!=null && dragPoint!=null && selectionState.selection.get("view") == 1 && findValidEndpoints(startPoint, 1).contains(dragPoint)){
                    lines.add(new Line(startPoint, dragPoint, selectionState.selection.get("boundary")));
                    startPoint = null;
                    placed = true;
                } else if (startPoint!=null && dragPoint!=null&& selectionState.selection.get("view") == 2 && selectionState.selection.get("room") !=0) {
                    //System.out.println(" "+startPoint+snapPoint);
                    if (Utils.overlap_checker(rooms, new Room(startPoint, dragPoint, selectionState.selection.get("room"), new ArrayList<Line>())) == 1){
                        System.out.println("OVERLAP!!!!");
                        startPoint = null;
                        placed = true;
                        repaint();
                        placed = false;
                    } else {
                        matrixUtilities.addRoom(gridMatrix, startPoint, dragPoint, 4);
                    Line l1, l2, l3, l4;
                    l1=new Line(startPoint, new Point (dragPoint.x, startPoint.y), 1);
                    l2=new Line(startPoint, new Point (startPoint.x, dragPoint.y), 1);
                    l3=new Line(dragPoint, new Point (startPoint.x, dragPoint.y), 1);
                    l4=new Line(dragPoint, new Point (dragPoint.x, startPoint.y), 1);
                    lines.add(l1);
                    lines.add(l2);
                    lines.add(l3);
                    lines.add(l4);

                    ArrayList<Line> newWalls = new ArrayList<>();
                    newWalls.add(l1);
                    newWalls.add(l2);
                    newWalls.add(l3);
                    newWalls.add(l4);
                    rooms.add(new Room(startPoint, dragPoint, selectionState.selection.get("room"), newWalls));
                    startPoint = null;
                    placed = true;
                    //System.out.println(" "+selectionState.selection.get("room"));
                    }
                }
                repaint();
                dragPoint=null;
                placed = false;
            }

    });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                //System.out.println("moved");
                currentPos = snapToGrid(e.getPoint());
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e){
                if (dragging==true){
                    dragPoint=snapToGrid(e.getPoint());
                    currentPos = snapToGrid(e.getPoint());
                    // System.out.println(currentPos+" "+dragPoint);
                    repaint();
                    Room tempRoom = null;
                    if (selectionState.selection.get("view") == 0 && moving == false){
                        boolean selected = false;
                        for (Room room: rooms){
                            if (e.getPoint().x>room.topLeft.x && e.getPoint().x<room.bottomRight.x 
                            && e.getPoint().y>room.topLeft.y && e.getPoint().y<room.bottomRight.y){
                                selectedRoom = room;
                                selectionState.selectedRoom = selectedRoom;
                                selected = true;
                                repaint();
                                break;
                            }
                        }
                        if (selected==false){
                            selectedRoom = null;
                        } else {
                            movedRoom = selectedRoom;
                            moving = true;
                            dist_x = dragPoint.x-selectedRoom.topLeft.x;
                            dist_y = dragPoint.y-selectedRoom.topLeft.y;
                            _width = selectedRoom.width;
                            _height = selectedRoom.height;
                            _id = selectedRoom.id;
                        }
                        repaint();
                    } else if (selectionState.selection.get("view") == 0 && moving == true){
                        movedRoom = new Room(new Point(dragPoint.x-dist_x, dragPoint.y-dist_y), 
                                        new Point(dragPoint.x-dist_x+_width, dragPoint.y-dist_y+_height),
                                         _id, new ArrayList<>());
                    }
                }
            }
        });


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRooms(g);
        drawSelectedRoom(g);
        drawGrid(g);
        drawLines(g);
        drawStartPointMarker(g);
        drawValidEndpoints(g, startPoint, selectionState.selection.get("view"));
        drawEndPointMarker(g);
        drawMovingRoom(g);
        drawCurrentPosition(g);
        drawDragPointMarker(g);
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
        //System.out.println("currentpos is "+currentPos);
        if (currentPos!=null && placed==false){
            g.setColor(Color.PINK);
            int markerSize = 8;
            //System.out.print("position is"+currentPos+" ");
            g.fillOval(currentPos.x - markerSize / 2, currentPos.y - markerSize / 2, markerSize, markerSize);
        }
        g.setColor(original);
    }

    private void drawDragPointMarker(Graphics g){
        Color original = g.getColor();
        //System.out.println("Dragpoint is "+dragPoint);
        if (dragPoint!=null && placed == false){
            g.setColor(Color.GREEN);
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
            g.fillRect(room.topLeft.x+2, room.topLeft.y+2, room.width-2, room.height-2);
            // System.out.println(" "+room);
        }
        g.setColor(original);
    }

    private void drawSelectedRoom(Graphics g){
        if (selectedRoom==null || selectionState.selection.get("view")!=0){
            return;
        }
        Color original = g.getColor();
        for (Room room: rooms) {
            if (room == selectedRoom){
                g.setColor(selectionState.colorMap.get(room.id));
                g.fillRect(room.topLeft.x+2, room.topLeft.y+2, room.width-2, room.height-2);
                int markerSize = 12;
                g.setColor(Color.MAGENTA);
                g.fillOval(room.topLeft.x - markerSize / 2, room.topLeft.y - markerSize / 2, markerSize, markerSize);
            } else {
                g.setColor(Color.DARK_GRAY);
                g.fillRect(room.topLeft.x+2, room.topLeft.y+2, room.width-2, room.height-2);
            }
            
            // System.out.println(" "+room);
        }
        g.setColor(original);
    }

    private void drawMovingRoom(Graphics g){
        Color original = g.getColor();
        if (moving==true && movedRoom!=null){
            g.setColor(selectionState.colorMap.get(movedRoom.id));
            g.fillRect(movedRoom.topLeft.x+2, movedRoom.topLeft.y+2, movedRoom.width-2, movedRoom.height-2);
        }
        g.setColor(original);
    }

    private Point snapToGrid(Point point) {
        int x = (point.x / GRID_SIZE) * GRID_SIZE;
        int y = (point.y / GRID_SIZE) * GRID_SIZE;
        return new Point(x, y);
    }

}
