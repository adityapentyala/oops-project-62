package src;

import src.LandingPage.MainPage;
import src.PlanPage.PlanPage;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage();

            // ActionListener to transition to PlanPage
            mainPage.addNewPlanListener((e) -> {
                mainPage.dispose(); // Close the MainPage
                PlanPage.main(null); // Open PlanPage
            });
        });
    }
}
