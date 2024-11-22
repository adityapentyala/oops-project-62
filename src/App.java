package src;

import src.LandingPage.MainPage;
import src.PlanPage.PlanPage;
import src.PlanPage.selectionState;
import src.PlanPage.FileManager;

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
                    selectionState.filename = "plandata/"+planName+".ava";
                    System.out.println(selectionState.filename);
                    PlanPage.main(null);
                    selectionState.load=false;
                }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
                
            });
            mainPage.addLoadPlanListener((e) -> {
                File initialDirectory = new File("C:/Users/adity/oops-project-62/plandata");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(initialDirectory);
                int value = fileChooser.showOpenDialog(mainPage);
                if (value == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();
                    if(selectedFile.exists()){
                        mainPage.dispose();
                        selectionState.filename = "plandata/"+selectedFile.getName();
                        System.out.println(selectedFile.getAbsolutePath());
                        selectionState.load = true;
                        FileManager.PlanData planData = FileManager.loadPlan(selectedFile.getAbsolutePath());
                        System.out.println("Received plan"+planData);
                        System.out.println(planData.rooms);
                        System.out.println(planData.walls);
                        System.out.println(planData.windows);
                        System.out.println(planData.doors);
                        System.out.println(selectionState.filename);
                        selectionState.plan = planData;
                        System.out.println(selectionState.plan);
                        //System.out.println(planData.rooms.size()+" "+ planData.windows.size()+" "+ planData.walls.size());
                        PlanPage.main(planData); 
                    }
                }
            });
        });
    }
}
