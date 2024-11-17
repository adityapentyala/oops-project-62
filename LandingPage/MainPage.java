package LandingPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame {

    public MainPage() {
        // Frame properties
        setTitle("AVA Floor Planner 2D");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(70, 0, 70, 0)); // Add top and bottom padding

        // Logo Panel
        JPanel logoPanel = new JPanel();


        // Logo Label with icon and text
        JLabel logoLabel = new JLabel("", JLabel.CENTER);
        ImageIcon icon = new ImageIcon("LandingPage/images/logo.png"); 
        Image resizedImage = icon.getImage().getScaledInstance(600,250, java.awt.Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(resizedImage);
        logoLabel.setIcon(logoIcon);

        logoPanel.add(logoLabel);
        mainPanel.add(logoPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // New Plan Button
        JButton newPlanButton = new JButton("New Plan");
        ImageIcon icon2 = new ImageIcon("LandingPage/images/newfile.png"); 
        Image resizedImage2 = icon2.getImage().getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newPlanIcon = new ImageIcon(resizedImage2);
        newPlanButton.setIcon(newPlanIcon);
        newPlanButton.setPreferredSize(new Dimension(200, 80));
        newPlanButton.setIconTextGap(5);
        buttonPanel.add(newPlanButton);

        // Load Plan Button
        JButton loadPlanButton = new JButton("Load Plan");
        ImageIcon icon3 = new ImageIcon("LandingPage/images/folder.png"); 
        Image resizedImage3 = icon3.getImage().getScaledInstance(30,30, java.awt.Image.SCALE_SMOOTH);
        ImageIcon loadPlanIcon = new ImageIcon(resizedImage3);
        loadPlanButton.setIcon(loadPlanIcon);
        loadPlanButton.setPreferredSize(new Dimension(200, 80));
        loadPlanButton.setIconTextGap(5);
        buttonPanel.add(loadPlanButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Button Actions
        newPlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a new empty frame
                JFrame newPlanFrame = new JFrame("New Plan");
                newPlanFrame.setSize(1000, 600);
                newPlanFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newPlanFrame.setLocationRelativeTo(null);
                newPlanFrame.setVisible(true);
            }
        });

        loadPlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open file chooser but do nothing with the selected file
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(MainPage.this);
            }
        });
        setVisible(true);
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(new Runnable() {
    //         @Override
    //         public void run() {
    //             new MainPage().setVisible(true);
    //         }
    //     });
    // }
}
