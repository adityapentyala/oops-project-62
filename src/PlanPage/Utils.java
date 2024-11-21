package src.PlanPage;
import java.util.ArrayList;
import java.awt.Point;

public class Utils {
    public static int overlap_checker(ArrayList<Room> rooms, Room new_room, boolean rotating) {
        int overlap = 0;
        if (rotating==false){
            if(new_room.topLeft.y==new_room.bottomRight.y || new_room.topLeft.x==new_room.bottomRight.x){
                System.out.println("Same line overlap!!");
                return 1;
            }
        }   

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

  public static Room rotate(Room selected_room,int button_input,ArrayList<Room> rooms){
        Room new_room = new Room(selected_room.topLeft, selected_room.bottomRight, selected_room.id, selected_room.walls);
        if(button_input==1){
           Point new_topLeft=new Point(0,0);
           Point new_bottomRight=new Point(0,0);
           int new_width, new_height;

           //Original Coordinates of room
           Point initial_topLeft=selected_room.topLeft;
           Point initial_bottomRight=selected_room.bottomRight;
           int initial_width=selected_room.width;
           int initial_height=selected_room.height;

           //Updates values
           new_topLeft.x=selected_room.topLeft.x;
           new_topLeft.y=selected_room.topLeft.y;

           new_height = initial_width;
           new_width = initial_height;

           new_bottomRight.x=new_room.topLeft.x+new_width;
           new_bottomRight.y=new_room.topLeft.y+new_height;

           //Updating room coordinates
           new_room.topLeft=new_topLeft;
           new_room.bottomRight=new_bottomRight;
           new_room.height=new_height;
           new_room.width=new_width;

        //    new_room.topLeft.x=new_topLeft.x;
        //    new_room.topLeft.y=new_topLeft.y;

        //    new_room.topRight.x=new_topRight.x;
        //    new_room.topRight.y=new_topRight.y;

        //    new_room.bottomLeft.x=new_bottomLeft.x;
        //    new_room.bottomLeft.y=new_bottomLeft.y;

        //    new_room.bottomRight.x=new_bottomRight.x;
        //    new_room.bottomRight.y=new_bottomRight.y;

        if(overlap_checker(rooms, new_room, true)==1){
            System.out.println("OVERLAPPPPPP AFTER ROTATING");
            return selected_room;
            }
        }
        return new_room;
  }

  

}
