package src.PlanPage;
import java.util.ArrayList;
import java.awt.Point;

public class Utils {
    public static int overlap_checker(ArrayList<Room> rooms, Room new_room) {
        int overlap = 0;
        if(new_room.topLeft.y==new_room.bottomRight.y || new_room.topLeft.x==new_room.bottomRight.x){
            return 1;
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

  public static void rotate(Room new_room,int button_input,ArrayList<Room> rooms){

        if(button_input==1){
           Point new_topRight=new Point(0,0);
           Point new_topLeft=new Point(0,0);
           Point new_bottomRight=new Point(0,0);
           Point new_bottomLeft=new Point(0,0);

           //Original Coordinates of room
           Point initial_topRight=new_room.topRight;
           Point initial_topLeft=new_room.topLeft;
           Point initial_bottomRight=new_room.bottomRight;
           Point initial_bottomLeft=new_room.bottomLeft;

           //Updates values
           new_topLeft.x=new_room.topLeft.x;
           new_topLeft.y=new_room.topLeft.y;

           new_topRight.x=new_topLeft.x+new_room.height;
           new_topRight.y=new_topLeft.y;

           new_bottomLeft.x=new_topLeft.x;
           new_bottomLeft.y=new_topLeft.y+new_room.width;

           new_bottomRight.x=new_topRight.x;
           new_bottomRight.y=new_bottomLeft.y;

           //Updating room coordinates
           new_room.topLeft=new_topLeft;
           new_room.topRight=new_topRight;
           new_room.bottomLeft=new_bottomLeft;
           new_room.bottomRight=new_bottomRight;

        //    new_room.topLeft.x=new_topLeft.x;
        //    new_room.topLeft.y=new_topLeft.y;

        //    new_room.topRight.x=new_topRight.x;
        //    new_room.topRight.y=new_topRight.y;

        //    new_room.bottomLeft.x=new_bottomLeft.x;
        //    new_room.bottomLeft.y=new_bottomLeft.y;

        //    new_room.bottomRight.x=new_bottomRight.x;
        //    new_room.bottomRight.y=new_bottomRight.y;

        if(overlap_checker(rooms, new_room)==1){
            new_room.topLeft=initial_topLeft;
            new_room.topRight=initial_topRight;
            new_room.bottomLeft=initial_bottomLeft;
            new_room.bottomRight=initial_bottomRight;
        }
        }
  }

  

}
