package src.PlanPage;
import java.util.Map;

import src.PlanPage.FileManager.PlanData;

import java.util.HashMap;
import java.awt.Color;

public class selectionState {
    public static Map<String, Integer> selection = new HashMap();
    public static Map<Integer, Color> colorMap = new HashMap();
    public static Room selectedRoom = null;
    public static FObject selectedFObject = null;
    public static String filename = null; 
    public static Map<Integer, String> FObjectMap = new HashMap<>();
    public static boolean load;
    public static PlanData plan;
}
