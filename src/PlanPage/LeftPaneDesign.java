package src.PlanPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class LeftPaneDesign extends JPanel {
    // Main frame components
    private JPanel mainPanel, viewPanel, actionPanel, controlPanel, bottomPanel,logoPanel,topPanel;
    private JButton boundaryViewBtn, roomViewBtn, selectionViewBtn, furnitureViewBtn;
    private JButton wallBtn, windowBtn, doorBtn;
    private JButton livingRoomBtn, kitchenBtn, bathroomBtn, bedroomBtn, diningRoomBtn;
    private JButton bedBtn, chairBtn, commodeBtn, diningSetBtn, showerBtn, sofaBtn, stoveBtn, tableBtn, washbasinBtn;
    private JButton deleteBtn, rotateBtn, saveBtn;

    public LeftPaneDesign() {
        selectionState.selection.put("view", 2);
        selectionState.selection.put("room", 0);
        selectionState.selection.put("boundary", 0);
        selectionState.selection.put("fixture", 0);
        Utils.Furniture_mapping();
        // setTitle("Floor Planner - Left Pane Design");
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 600);
        setLayout(new BorderLayout());

        // Main Panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        topPanel = new JPanel(new BorderLayout());

        // Add a logo panel at the top
        logoPanel = new JPanel();
        logoPanel.setLayout(new BorderLayout());
        logoPanel.setBackground(Color.WHITE); 

        // Load the logo image
        ImageIcon logoIcon = new ImageIcon("assets/logo.png"); 
        Image logoImage = logoIcon.getImage().getScaledInstance(400, 120, Image.SCALE_SMOOTH); // Scale to fit width
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);

        // Add logo to a JLabel
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(resizedLogoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoPanel.add(logoLabel, BorderLayout.CENTER);      

        // Top View Panel for Boundary/Room toggle buttons
        viewPanel = new JPanel();
        viewPanel.setLayout(new GridLayout(1, 2, 5, 5));
        roomViewBtn = new JButton("Rooms");
        furnitureViewBtn = new JButton("Furniture");
        boundaryViewBtn = new JButton("Fixtures");
        selectionViewBtn = new JButton("Select");

        // Set initial state
        boundaryViewBtn.setEnabled(true); // Default is room View
        roomViewBtn.setEnabled(false);
        selectionViewBtn.setEnabled(true);
        furnitureViewBtn.setEnabled(true);

        Insets buttonPadding = new Insets(15, 20, 15, 20); // Top, Left, Bottom, Right
        boundaryViewBtn.setMargin(buttonPadding);
        roomViewBtn.setMargin(buttonPadding);
        selectionViewBtn.setMargin(buttonPadding);
        furnitureViewBtn.setMargin(buttonPadding);

        // Add toggle buttons
        viewPanel.add(boundaryViewBtn);
        viewPanel.add(roomViewBtn);
        viewPanel.add(selectionViewBtn);
        viewPanel.add(furnitureViewBtn);
        viewPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        topPanel.add(logoPanel, BorderLayout.NORTH);
        topPanel.add(viewPanel, BorderLayout.SOUTH);

        // Action Panel for dynamic buttons (Boundary or Room View options)
        actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(5, 1, 5, 5)); // Placeholder for dynamic buttons
        //bottomPanel.setPreferredSize(new Dimension(300, 80));
        showRoomButtons(); // Initially show Room View buttons


        // Bottom Panel for Delete, Cancel, Save buttons
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setLayout(new GridLayout(1, 3, 5, 5));

        ImageIcon icon1 = new ImageIcon("assets/delete.png");
        Image resizedImage1 = icon1.getImage().getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH);
        ImageIcon deleteIcon = new ImageIcon(resizedImage1);

        ImageIcon icon2 = new ImageIcon("assets/rotate.png");
        Image resizedImage2 = icon2.getImage().getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH);
        ImageIcon rotateIcon = new ImageIcon(resizedImage2);

        ImageIcon icon3 = new ImageIcon("assets/save.png");
        Image resizedImage3 = icon3.getImage().getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH);
        ImageIcon saveIcon = new ImageIcon(resizedImage3);

        deleteBtn = new JButton(deleteIcon);
        deleteBtn.setBorderPainted(false);
        rotateBtn = new JButton(rotateIcon);
        rotateBtn.setBorderPainted(false);
        saveBtn = new JButton(saveIcon);
        saveBtn.setBorderPainted(false);

        deleteBtn.setFocusable(false);
        rotateBtn.setFocusable(false);
        saveBtn.setFocusable(false);


        bottomPanel.add(deleteBtn);
        bottomPanel.add(rotateBtn);
        bottomPanel.add(saveBtn);

        deleteBtn.addActionListener(e -> {
            if (selectionState.selectedRoom!=null){
                SnapGridPane.rooms.remove(selectionState.selectedRoom);
                for (Line line: selectionState.selectedRoom.walls){
                    //System.out.println(SnapGridPane.lines.size());
                    SnapGridPane.lines.remove(line);
                    //System.out.println(SnapGridPane.lines.size());
                }
                ArrayList<Line> toRemove = new ArrayList<>();
                for (Line window: SnapGridPane.windows){
                    for (Line wall: selectionState.selectedRoom.walls){
                        if (SnapGridPane.onWall(wall, window.start, window.end)){
                            toRemove.add(window);
                        }
                    }
                }
                for (Line window: toRemove){
                    SnapGridPane.windows.remove(window);
                }
                selectionState.selectedRoom=null;
                selectionState.selectedFObject=null;
            } else if (selectionState.selectedFObject!=null){
                SnapGridPane.fobjects.remove(selectionState.selectedFObject);
                selectionState.selectedFObject=null;
                selectionState.selectedRoom=null;
            }
        });

        rotateBtn.addActionListener(e -> {
            if (selectionState.selectedFObject!=null){
                try {
                    FObject newobj = Utils.rotate_fixture(selectionState.selectedFObject);
                    System.out.println(SnapGridPane.fobjects);
                    SnapGridPane.fobjects.remove(selectionState.selectedFObject);
                    System.out.println(SnapGridPane.fobjects);
                    SnapGridPane.fobjects.add(newobj);
                    System.out.println("rotated");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                selectionState.selectedFObject = null;
                selectionState.selectedRoom=null;
            }
            else if (selectionState.selectedRoom!=null){
                System.out.println("Rotating");
                for (Line line: selectionState.selectedRoom.walls) {
                    SnapGridPane.lines.remove(line);
                }
                SnapGridPane.rooms.remove(selectionState.selectedRoom);
                Room newRoom = Utils.rotate(selectionState.selectedRoom,1, SnapGridPane.rooms);
                Line l1, l2, l3, l4;
                        l1 = new Line(newRoom.topLeft, new Point(newRoom.topLeft.x, newRoom.bottomRight.y), 1);
                        l2 = new Line(newRoom.topLeft, new Point(newRoom.bottomRight.x, newRoom.topLeft.y), 1);
                        l3 = new Line(newRoom.bottomRight, new Point(newRoom.topLeft.x, newRoom.bottomRight.y), 1);
                        l4 = new Line(newRoom.bottomRight, new Point(newRoom.bottomRight.x, newRoom.topLeft.y), 1);
                        SnapGridPane.lines.add(l1);
                        SnapGridPane.lines.add(l2);
                        SnapGridPane.lines.add(l3);
                        SnapGridPane.lines.add(l4);
                        ArrayList<Line> newWalls = new ArrayList<>();
                        newWalls.add(l1);
                        newWalls.add(l2);
                        newWalls.add(l3);
                        newWalls.add(l4);
                        newRoom.walls = newWalls;
                SnapGridPane.rooms.add(newRoom);
                selectionState.selectedRoom=null;
                selectionState.selectedFObject=null;
                ArrayList<Line> toRemoveWindows = new ArrayList<>();
                        for (Line window: SnapGridPane.windows){
                            int wc = 0;
                            for (Line wall: SnapGridPane.lines){
                                if (SnapGridPane.onWall(wall, window.start, window.end)){
                                    wc++;                                    
                                }
                            }
                            if (wc>=2 || wc == 0){
                                //System.out.println("Removing"+window)
                                toRemoveWindows.add(window);
                            }
                        }
                        ArrayList<Line> toRemoveDoors = new ArrayList<>();
                        for (Line door: SnapGridPane.doors){
                            int dc = 0;
                            for (Line wall: SnapGridPane.lines){
                                if (SnapGridPane.onWall(wall, door.start, door.end)){
                                    dc++;
                                }
                            }
                            if (dc < 1){
                                System.out.println("Removing"+door+"with"+dc+"count");
                                toRemoveDoors.add(door);
                            }
                        }
                        for (Line window: toRemoveWindows){
                            SnapGridPane.windows.remove(window);
                        }
                        for (Line door: toRemoveDoors){
                            SnapGridPane.doors.remove(door);
                        }
                        toRemoveDoors = new ArrayList<>();
                        toRemoveWindows = new ArrayList<>();
            }
        });

        saveBtn.addActionListener(e -> {
            System.out.println("Clicked!!");
            FileManager.savePlan(selectionState.filename, SnapGridPane.rooms, SnapGridPane.doors, SnapGridPane.lines, SnapGridPane.windows, SnapGridPane.fobjects);
            System.out.println("Save button clicked, file saved! ");
        });

        // Combine panels
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(actionPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        disableFocusPaintedForAllButtons(mainPanel);
        // Event Listeners
        boundaryViewBtn.addActionListener(e -> {
            actionPanel.setVisible(true);
            boundaryViewBtn.setEnabled(false);
            roomViewBtn.setEnabled(true);
            selectionViewBtn.setEnabled(true);
            furnitureViewBtn.setEnabled(true);
            showBoundaryButtons();
            selectionState.selection.put("view", 1);
            selectionState.selection.put("room", 0);
            selectionState.selection.put("boundary", 2);
            selectionState.selection.put("fixture", 0);
        });

        roomViewBtn.addActionListener(e -> {
            actionPanel.setVisible(true);
            roomViewBtn.setEnabled(false);
            boundaryViewBtn.setEnabled(true);
            selectionViewBtn.setEnabled(true);
            furnitureViewBtn.setEnabled(true);
            showRoomButtons();
            selectionState.selection.put("view", 2);
            selectionState.selection.put("room", 4);
            selectionState.selection.put("boundary", 0);
            selectionState.selection.put("fixture", 0);
        });

        selectionViewBtn.addActionListener(e -> {
            System.out.println("clicked");
            selectionViewBtn.setEnabled(false);
            boundaryViewBtn.setEnabled(true);
            roomViewBtn.setEnabled(true);
            actionPanel.setVisible(false);
            furnitureViewBtn.setEnabled(true);
            selectionState.selection.put("view", 0);
            selectionState.selection.put("room", 0);
            selectionState.selection.put("boundary", 0);
            selectionState.selection.put("fixture", 0);
        });

        furnitureViewBtn.addActionListener(e -> {
            System.out.println("clicked");
            actionPanel.setVisible(true);
            furnitureViewBtn.setEnabled(false);
            selectionViewBtn.setEnabled(true);
            boundaryViewBtn.setEnabled(true);
            roomViewBtn.setEnabled(true);
            showFurnitureButtons();
            selectionState.selection.put("view", 4);
            selectionState.selection.put("room", 0);
            selectionState.selection.put("boundary", 0);
            selectionState.selection.put("fixture", 1);
        });
    }

    private void showBoundaryButtons() {
        actionPanel.removeAll();
        //wallBtn = new JButton("Wall");
        windowBtn = new JButton("Window");
        doorBtn = new JButton("Door");

        //actionPanel.add(wallBtn);
        actionPanel.add(windowBtn);
        actionPanel.add(doorBtn);

        // add listeners
        // wallBtn.addActionListener(e -> {
        //     wallBtn.setEnabled(false);
        //     windowBtn.setEnabled(true);
        //     doorBtn.setEnabled(true);
        //     showBoundaryButtons();
        //     selectionState.selection.put("boundary", 1);
        // });

        windowBtn.addActionListener(e -> {
            // wallBtn.setEnabled(true);
            // windowBtn.setEnabled(false);
            // doorBtn.setEnabled(true);
            showBoundaryButtons();
            updateRoomButton(windowBtn);
            selectionState.selection.put("boundary", 3);
        });

        doorBtn.addActionListener(e -> {
            // wallBtn.setEnabled(true);
            // windowBtn.setEnabled(true);
            // doorBtn.setEnabled(false);
            showBoundaryButtons();
            updateRoomButton(doorBtn);
            selectionState.selection.put("boundary", 2);
        });

        // Redraw panel
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    private void showRoomButtons() {
        System.out.println("removing");
        actionPanel.removeAll();
        System.out.println("removed");
        livingRoomBtn = new JButton("Living Room");
        kitchenBtn = new JButton("Kitchen");
        bathroomBtn = new JButton("Bathroom");
        bedroomBtn = new JButton("Bedroom");
        diningRoomBtn = new JButton("Dining Room");

        actionPanel.add(livingRoomBtn);
        actionPanel.add(kitchenBtn);
        actionPanel.add(bathroomBtn);
        actionPanel.add(bedroomBtn);
        actionPanel.add(diningRoomBtn);

        // add action listeners
        livingRoomBtn.addActionListener(e -> {
            // livingRoomBtn.setEnabled(true);
            // kitchenBtn.setEnabled(true);
            // bathroomBtn.setEnabled(true);
            // bedroomBtn.setEnabled(true);
            // diningRoomBtn.setEnabled(true);
            // livingRoomBtn.setEnabled(false);
            showRoomButtons();
            updateRoomButton(livingRoomBtn);
            selectionState.selection.put("room", 8);
        });

        kitchenBtn.addActionListener(e -> {
            // livingRoomBtn.setEnabled(false);
            // kitchenBtn.setEnabled(true);
            // bathroomBtn.setEnabled(true);
            // bedroomBtn.setEnabled(true);
            // diningRoomBtn.setEnabled(true);
            showRoomButtons();
            updateRoomButton(kitchenBtn);
            selectionState.selection.put("room", 4);
        });

        bathroomBtn.addActionListener(e -> {
            // livingRoomBtn.setEnabled(true);
            // kitchenBtn.setEnabled(true);
            // bathroomBtn.setEnabled(false);
            // bedroomBtn.setEnabled(true);
            // diningRoomBtn.setEnabled(true);
            showRoomButtons();
            updateRoomButton(bathroomBtn);
            selectionState.selection.put("room", 6);
        });

        bedroomBtn.addActionListener(e -> {
            // livingRoomBtn.setEnabled(true);
            // kitchenBtn.setEnabled(true);
            // bathroomBtn.setEnabled(true);
            // bedroomBtn.setEnabled(false);
            // diningRoomBtn.setEnabled(true);
            showRoomButtons();
            updateRoomButton(bedroomBtn);
            selectionState.selection.put("room", 5);
        });

        diningRoomBtn.addActionListener(e -> {
            // livingRoomBtn.setEnabled(true);
            // kitchenBtn.setEnabled(true);
            // bathroomBtn.setEnabled(true);
            // bedroomBtn.setEnabled(true);
            // diningRoomBtn.setEnabled(false);
            showRoomButtons();
            updateRoomButton(diningRoomBtn);
            selectionState.selection.put("room", 7);
        });

        // Redraw panel
        actionPanel.revalidate();
        actionPanel.repaint();

        
    }

    private void showFurnitureButtons() {
        System.out.println("removing");
        actionPanel.removeAll();
        System.out.println("removed");
    
        // Define buttons
        bedBtn = new JButton("Bed");
        chairBtn = new JButton("Chair");
        commodeBtn = new JButton("Commode");
        diningSetBtn = new JButton("Dining Set");
        showerBtn = new JButton("Shower");
        sofaBtn = new JButton("Sofa");
        stoveBtn = new JButton("Stove");
        tableBtn = new JButton("Table");
        washbasinBtn = new JButton("Washbasin");
    
        // Add buttons to action panel
        actionPanel.add(bedBtn);
        actionPanel.add(chairBtn);
        actionPanel.add(commodeBtn);
        actionPanel.add(diningSetBtn);
        actionPanel.add(showerBtn);
        actionPanel.add(sofaBtn);
        actionPanel.add(stoveBtn);
        actionPanel.add(tableBtn);
        actionPanel.add(washbasinBtn);
    
        // Add action listeners
        bedBtn.addActionListener(e -> {
            showFurnitureButtons();
            updateFurnitureButton(bedBtn);
            selectionState.selection.put("fixture", 1);
        });
    
        chairBtn.addActionListener(e -> {
            showFurnitureButtons();
            updateFurnitureButton(chairBtn);
            selectionState.selection.put("fixture", 11);
        });
    
        commodeBtn.addActionListener(e -> {
            showFurnitureButtons();
            updateFurnitureButton(commodeBtn);
            selectionState.selection.put("fixture", 21);
        });
    
        diningSetBtn.addActionListener(e -> {
            showFurnitureButtons();
            updateFurnitureButton(diningSetBtn);
            selectionState.selection.put("fixture", 31);
        });
    
        showerBtn.addActionListener(e -> {
            showFurnitureButtons();
            updateFurnitureButton(showerBtn);
            selectionState.selection.put("fixture", 41);
        });
    
        sofaBtn.addActionListener(e -> {
            showFurnitureButtons();
            updateFurnitureButton(sofaBtn);
            selectionState.selection.put("fixture", 61);
        });
    
        stoveBtn.addActionListener(e -> {
            showFurnitureButtons();
            updateFurnitureButton(stoveBtn);
            selectionState.selection.put("fixture", 71);
        });
    
        tableBtn.addActionListener(e -> {
            showFurnitureButtons();
            updateFurnitureButton(tableBtn);
            selectionState.selection.put("fixture", 81);
        });
    
        washbasinBtn.addActionListener(e -> {
            showFurnitureButtons();
            updateFurnitureButton(washbasinBtn);
            selectionState.selection.put("fixture", 91);
        });
    
        // Redraw the panel
        actionPanel.revalidate();
        actionPanel.repaint();
    }
    

    private void updateRoomButton(JButton selectedButton){
        livingRoomBtn.setEnabled(true);
        kitchenBtn.setEnabled(true);
        bathroomBtn.setEnabled(true);
        bedroomBtn.setEnabled(true);
        diningRoomBtn.setEnabled(true);
        selectedButton.setEnabled(false);
    }  

    private void updateFurnitureButton(JButton selectedButton) {
        bedBtn.setEnabled(true);
        chairBtn.setEnabled(true);
        commodeBtn.setEnabled(true);
        diningSetBtn.setEnabled(true);
        showerBtn.setEnabled(true);
        sofaBtn.setEnabled(true);
        stoveBtn.setEnabled(true);
        tableBtn.setEnabled(true);
        washbasinBtn.setEnabled(true);
        selectedButton.setEnabled(false);
    }
    

    private void disableFocusPaintedForAllButtons(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).setFocusPainted(false);
            } else if (component instanceof Container) {
                // Recursively check for buttons in nested containers
                disableFocusPaintedForAllButtons((Container) component);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LeftPaneDesign frame = new LeftPaneDesign();
            frame.setVisible(true);
        });
    }
}
