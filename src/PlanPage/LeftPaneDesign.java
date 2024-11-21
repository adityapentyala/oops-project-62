package src.PlanPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeftPaneDesign extends JPanel {
    // Main frame components
    private JPanel mainPanel, viewPanel, actionPanel, controlPanel, bottomPanel;
    private JButton boundaryViewBtn, roomViewBtn, selectionViewBtn;
    private JButton wallBtn, windowBtn, doorBtn;
    private JButton livingRoomBtn, kitchenBtn, bathroomBtn, bedroomBtn, diningRoomBtn;
    private JButton deleteBtn, rotateBtn, saveBtn;

    public LeftPaneDesign() {
        selectionState.selection.put("view", 0);
        selectionState.selection.put("room", 0);
        selectionState.selection.put("boundary", 0);
        selectionState.selection.put("fixture", 0);
        // setTitle("Floor Planner - Left Pane Design");
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 600);
        setLayout(new BorderLayout());

        // Main Panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        // Top View Panel for Boundary/Room toggle buttons
        viewPanel = new JPanel();
        viewPanel.setLayout(new GridLayout(1, 2, 5, 5));
        boundaryViewBtn = new JButton("Boundary View");
        roomViewBtn = new JButton("Room View");
        selectionViewBtn = new JButton("Selection View");

        // Set initial state
        boundaryViewBtn.setEnabled(true); // Default is Boundary View
        roomViewBtn.setEnabled(true);
        selectionViewBtn.setEnabled(false);

        // Add toggle buttons
        viewPanel.add(boundaryViewBtn);
        viewPanel.add(roomViewBtn);
        viewPanel.add(selectionViewBtn);

        // Action Panel for dynamic buttons (Boundary or Room View options)
        actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(5, 1, 5, 5)); // Placeholder for dynamic buttons
        //bottomPanel.setPreferredSize(new Dimension(300, 80));
        showBoundaryButtons(); // Initially show Boundary View buttons


        // Bottom Panel for Delete, Cancel, Save buttons
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setLayout(new GridLayout(1, 3, 5, 5));

        ImageIcon icon1 = new ImageIcon("assets/delete.png");
        Image resizedImage1 = icon1.getImage().getScaledInstance(40,40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon deleteIcon = new ImageIcon(resizedImage1);

        ImageIcon icon2 = new ImageIcon("assets/rotate.png");
        Image resizedImage2 = icon2.getImage().getScaledInstance(40,40, java.awt.Image.SCALE_SMOOTH);
        ImageIcon rotateIcon = new ImageIcon(resizedImage2);

        ImageIcon icon3 = new ImageIcon("assets/save.png");
        Image resizedImage3 = icon3.getImage().getScaledInstance(40,40, java.awt.Image.SCALE_SMOOTH);
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
                    System.out.println(SnapGridPane.lines.size());
                    SnapGridPane.lines.remove(line);
                    System.out.println(SnapGridPane.lines.size());
                }
            }
        });

        // Combine panels
        mainPanel.add(viewPanel, BorderLayout.NORTH);
        mainPanel.add(actionPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Event Listeners
        boundaryViewBtn.addActionListener(e -> {
            boundaryViewBtn.setEnabled(false);
            roomViewBtn.setEnabled(true);
            selectionViewBtn.setEnabled(true);
            showBoundaryButtons();
            selectionState.selection.put("view", 1);
            selectionState.selection.put("room", 0);
            selectionState.selection.put("boundary", 2);
            selectionState.selection.put("fixture", 0);
        });

        roomViewBtn.addActionListener(e -> {
            roomViewBtn.setEnabled(false);
            boundaryViewBtn.setEnabled(true);
            selectionViewBtn.setEnabled(true);
            showRoomButtons();
            selectionState.selection.put("view", 2);
            selectionState.selection.put("room", 4);
            selectionState.selection.put("boundary", 0);
            selectionState.selection.put("fixture", 0);
        });

        selectionViewBtn.addActionListener(e -> {
            selectionViewBtn.setEnabled(false);
            boundaryViewBtn.setEnabled(true);
            roomViewBtn.setEnabled(true);
            selectionState.selection.put("view", 0);
            selectionState.selection.put("room", 0);
            selectionState.selection.put("boundary", 0);
            selectionState.selection.put("fixture", 0);
        });
    }

    private void showBoundaryButtons() {
        actionPanel.removeAll();
        wallBtn = new JButton("Wall");
        windowBtn = new JButton("Window");
        doorBtn = new JButton("Door");

        actionPanel.add(wallBtn);
        actionPanel.add(windowBtn);
        actionPanel.add(doorBtn);

        // add listeners
        wallBtn.addActionListener(e -> {
            wallBtn.setEnabled(false);
            windowBtn.setEnabled(true);
            doorBtn.setEnabled(true);
            showBoundaryButtons();
            selectionState.selection.put("boundary", 1);
        });

        windowBtn.addActionListener(e -> {
            wallBtn.setEnabled(true);
            windowBtn.setEnabled(false);
            doorBtn.setEnabled(true);
            showBoundaryButtons();
            selectionState.selection.put("boundary", 3);
        });

        doorBtn.addActionListener(e -> {
            wallBtn.setEnabled(true);
            windowBtn.setEnabled(true);
            doorBtn.setEnabled(false);
            showBoundaryButtons();
            selectionState.selection.put("boundary", 2);
        });

        // Redraw panel
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    private void showRoomButtons() {
        actionPanel.removeAll();
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
            livingRoomBtn.setEnabled(false);
            kitchenBtn.setEnabled(true);
            bathroomBtn.setEnabled(true);
            bedroomBtn.setEnabled(true);
            diningRoomBtn.setEnabled(true);
            showRoomButtons();
            selectionState.selection.put("room", 8);
        });

        kitchenBtn.addActionListener(e -> {
            livingRoomBtn.setEnabled(false);
            kitchenBtn.setEnabled(true);
            bathroomBtn.setEnabled(true);
            bedroomBtn.setEnabled(true);
            diningRoomBtn.setEnabled(true);
            showRoomButtons();
            selectionState.selection.put("room", 4);
        });

        bathroomBtn.addActionListener(e -> {
            livingRoomBtn.setEnabled(true);
            kitchenBtn.setEnabled(true);
            bathroomBtn.setEnabled(false);
            bedroomBtn.setEnabled(true);
            diningRoomBtn.setEnabled(true);
            showRoomButtons();
            selectionState.selection.put("room", 6);
        });

        bedroomBtn.addActionListener(e -> {
            livingRoomBtn.setEnabled(true);
            kitchenBtn.setEnabled(true);
            bathroomBtn.setEnabled(true);
            bedroomBtn.setEnabled(false);
            diningRoomBtn.setEnabled(true);
            showRoomButtons();
            selectionState.selection.put("room", 5);
        });

        diningRoomBtn.addActionListener(e -> {
            livingRoomBtn.setEnabled(true);
            kitchenBtn.setEnabled(true);
            bathroomBtn.setEnabled(true);
            bedroomBtn.setEnabled(true);
            diningRoomBtn.setEnabled(false);
            showRoomButtons();
            selectionState.selection.put("room", 7);
        });

        // Redraw panel
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LeftPaneDesign frame = new LeftPaneDesign();
            frame.setVisible(true);
        });
    }
}
