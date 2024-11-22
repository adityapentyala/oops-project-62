package src.PlanPage;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    // Inner class to hold plan data for deserialization
    public static class PlanData implements Serializable{
        public ArrayList<Room> rooms;
        public ArrayList<Line> doors;
        public ArrayList<Line> walls;

        public PlanData(ArrayList<Room> rooms, ArrayList<Line> doors, ArrayList<Line> walls) {
            this.rooms = rooms;
            this.doors = doors;
            this.walls = walls;
        }
    }

    public static void savePlan(String fileName, ArrayList<Room> rooms, ArrayList<Line> doors, ArrayList<Line> walls) {
        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            PlanData planData = new PlanData(rooms, doors, walls);
            stream.writeObject(planData);
            System.out.println("Plan saved successfully to: "+fileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public static PlanData loadPlan(String fileName) {
        try(ObjectInputStream stream = new ObjectInputStream(new FileInputStream(fileName))) {
            PlanData planData = (PlanData) stream.readObject();
            System.out.println("Plan loaded successfully from: "+fileName);
            return planData;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}


