package src.PlanPage;
import java.util.ArrayList;
//import java.awt.Point;

public class Utils {
    public static int overlap_checker(ArrayList<Room> rooms, Room new_room) {
        int overlap = 0;
        System.out.println(rooms.size()+" "+new_room);
        for (Room room : rooms) {
            System.out.println(room.bottomRight.x +" " +room.bottomRight.y+" "+ room.topLeft.x +" " + room.topLeft.y);
            System.out.println(new_room.bottomRight.x +" " +new_room.bottomRight.y+" "+ new_room.topLeft.x +" " + new_room.topLeft.y);
            // Check if the rectangles overlap
            if (!(room.bottomRight.x <= new_room.topLeft.x ||   // room is to the left
                  room.topLeft.x >= new_room.bottomRight.x ||   // room is to the right
                  room.topLeft.y >= new_room.bottomRight.y ||   // room is below
                  room.bottomRight.y <= new_room.topLeft.y)) {  // room is above
                System.out.println("OVERLAPPPPP");
                overlap = 1;
                break;
            }
        }
        return overlap;
    }

//   public static void rotate(Room new_room,int button_input){

//         if(button_input==1){
//            Point new_topRight;
//            Point new_topLeft;
//            Point new_bottomRight;
//            Point new_bottomLeft;

//            new_topRight.x=new_room.topLeft.x+

//         }
//   }

}
