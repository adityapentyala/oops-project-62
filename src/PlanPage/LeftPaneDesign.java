package src.PlanPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeftPaneDesign extends JPanel {
    // Main frame components
    private JPanel mainPanel, viewPanel, actionPanel, controlPanel, bottomPanel;
    private JButton boundaryViewBtn, roomViewBtn;
    private JButton wallBtn, windowBtn, doorBtn;
    private JButton livingRoomBtn, kitchenBtn, bathroomBtn, bedroomBtn, balconyBtn;
    private JButton deleteBtn, cancelBtn, saveBtn;

    public LeftPaneDesign() {
        selectionState.selection.put("view", 1);
        selectionState.selection.put("room", 0);
        selectionState.selection.put("boundary", 1);
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

        // Set initial state
        boundaryViewBtn.setEnabled(false); // Default is Boundary View
        roomViewBtn.setEnabled(true);

        // Add toggle buttons
        viewPanel.add(boundaryViewBtn);
        viewPanel.add(roomViewBtn);

        // Action Panel for dynamic buttons (Boundary or Room View options)
        actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(5, 1, 5, 5)); // Placeholder for dynamic buttons
        showBoundaryButtons(); // Initially show Boundary View buttons

        // Bottom Panel for Delete, Cancel, Save buttons
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3, 5, 5));
        deleteBtn = new JButton("Delete");
        cancelBtn = new JButton("Cancel");
        saveBtn = new JButton("Save");
        bottomPanel.add(deleteBtn);
        bottomPanel.add(cancelBtn);
        bottomPanel.add(saveBtn);

        // Combine panels
        mainPanel.add(viewPanel, BorderLayout.NORTH);
        mainPanel.add(actionPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Event Listeners
        boundaryViewBtn.addActionListener(e -> {
            boundaryViewBtn.setEnabled(false);
            roomViewBtn.setEnabled(true);
            showBoundaryButtons();
            selectionState.selection.put("view", 1);
            selectionState.selection.put("room", 0);
            selectionState.selection.put("boundary", 2);
            selectionState.selection.put("fixture", 0);
        });

        roomViewBtn.addActionListener(e -> {
            roomViewBtn.setEnabled(false);
            boundaryViewBtn.setEnabled(true);
            showRoomButtons();
            selectionState.selection.put("view", 2);
            selectionState.selection.put("room", 4);
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
        balconyBtn = new JButton("Balcony");

        actionPanel.add(livingRoomBtn);
        actionPanel.add(kitchenBtn);
        actionPanel.add(bathroomBtn);
        actionPanel.add(bedroomBtn);
        actionPanel.add(balconyBtn);

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
