package src;

import src.LandingPage.MainPage;
import src.PlanPage.PlanPage;
import src.PlanPage.selectionState;

import java.io.File;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage();

            // ActionListener to transition to PlanPage
            mainPage.addNewPlanListener((e) -> {
                try {
                    String planName = JOptionPane.showInputDialog(mainPage,"Enter new plan name: ","New Plan",JOptionPane.PLAIN_MESSAGE);
                if (planName != null){
                    mainPage.dispose(); // Close the MainPage
                    selectionState.filename = planName;
                    PlanPage.main(null);
                }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
                
            });
            mainPage.addLoadPlanListener((e) -> {
                JFileChooser fileChooser = new JFileChooser();
                int value = fileChooser.showOpenDialog(mainPage);
                if (value == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();
                    if(selectedFile.exists()){
                        mainPage.dispose();
                        selectionState.filename = selectedFile.getName();
                        //PlanData planData = FileManager.loadPlan(selectedFile.getAbsolutePath());
                        PlanPage.main(null); 
                    }
                }
            });
        });
    }
}
