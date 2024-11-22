package src.PlanPage;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    // Inner class to hold plan data for deserialization
    public static class PlanData implements Serializable{
        public ArrayList<Room> rooms;
        public ArrayList<Line> doors;
        public ArrayList<Line> walls;
        public ArrayList<Line> windows;

        public PlanData(ArrayList<Room> rooms, ArrayList<Line> doors, ArrayList<Line> walls, ArrayList<Line> windows) {
            this.rooms = rooms;
            this.doors = doors;
            this.walls = walls;
            this.windows = windows;
        }
    }

    public static void savePlan(String fileName, ArrayList<Room> rooms, ArrayList<Line> doors, ArrayList<Line> walls, ArrayList<Line> windows) {
        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            PlanData planData = new PlanData(rooms, doors, walls, windows);
            stream.writeObject(planData);
            System.out.println("Plan saved successfully to: "+fileName);
        } catch (IOException e) {
            System.out.println("Error saving");
            System.out.println(e);
        }
    }


    public static PlanData loadPlan(String fileName) {
        try(ObjectInputStream stream = new ObjectInputStream(new FileInputStream(fileName))) {
            PlanData planData = (PlanData) stream.readObject();
            System.out.println("Plan loaded successfully from: "+fileName+" "+planData);
            //System.out.println(planData.rooms);
            //System.out.println(planData.walls);
            return planData;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}


