package src.PlanPage;
import java.util.ArrayList;
import java.awt.Point;

public class Utils {
  public static int overlap_checker(ArrayList<Room> rooms,Room new_room){
    int overlap=0;
    for (Room room : rooms){
        if(room.topLeft.x<=new_room.bottomRight.x && room.bottomRight.x>=new_room.topLeft.x &&
         room.topLeft.y>=new_room.bottomRight.y && room.bottomRight.y<=new_room.topLeft.y){
            overlap=1;
            break;
        }
    }    
    return overlap;
  }

//   public static void rotate(Room new_room,int button_input){

//         if(button_input==1){


//         }
//   }

}
