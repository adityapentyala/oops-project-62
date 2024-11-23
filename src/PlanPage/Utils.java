package src.PlanPage;
import java.util.ArrayList;
import java.awt.Point;
import java.io.IOException;

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
            //System.out.println(room.bottomRight.x +" " +room.bottomRight.y+" "+ room.topLeft.x +" " + room.topLeft.y);
            //System.out.println(new_room.bottomRight.x +" " +new_room.bottomRight.y+" "+ new_room.topLeft.x +" " + new_room.topLeft.y);
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
           if(initial_height>=initial_width){
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
           new_room.width=new_width;}
           else{
            new_topLeft.x=selected_room.topLeft.x;
            new_topLeft.y=selected_room.topLeft.y-(initial_width-initial_height);
 
            new_height = initial_width;
            new_width = initial_height;
 
            new_bottomRight.x=selected_room.bottomRight.x-(initial_width-initial_height);
            new_bottomRight.y=new_room.bottomRight.y;
 
            //Updating room coordinates
            new_room.topLeft=new_topLeft;
            new_room.bottomRight=new_bottomRight;
            new_room.height=new_height;
            new_room.width=new_width;
           }

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

public static void Furniture_mapping(){

    selectionState.FObjectMap.put(1,"assets/Furniture/Bed/Bed_1.jpg");
    selectionState.FObjectMap.put(2,"assets/Furniture/Bed/Bed_2.jpg");
    selectionState.FObjectMap.put(3,"assets/Furniture/Bed/Bed_3.jpg");
    selectionState.FObjectMap.put(4,"assets/Furniture/Bed/Bed_4.jpg");

    selectionState.FObjectMap.put(11,"assets/Furniture/Chiar/Chair_1.jpg");
    selectionState.FObjectMap.put(12,"assets/Furniture/Chair/Chair_2.jpg");
    selectionState.FObjectMap.put(13,"assets/Furniture/Chiar/Chair_3.jpg");
    selectionState.FObjectMap.put(14,"assets/Furniture/Chiar/Chair_4.jpg");

    selectionState.FObjectMap.put(21,"assets/Furniture/Commode/Commode_1.jpg");
    selectionState.FObjectMap.put(22,"assets/Furniture/Commode/Commode_2.jpg");
    selectionState.FObjectMap.put(23,"assets/Furniture/Commode/Commode_3.jpg");
    selectionState.FObjectMap.put(24,"assets/Furniture/Commode/Commode_4.jpg");

    selectionState.FObjectMap.put(31,"assets/Furniture/DiningSet/DiningSet_1.jpg");
    selectionState.FObjectMap.put(32,"assets/Furniture/DiningSet/DiningSet_2.jpg");
    selectionState.FObjectMap.put(33,"assets/Furniture/DiningSet/DiningSet_3.jpg");
    selectionState.FObjectMap.put(34,"assets/Furniture/DiningSet/DiningSet_4.jpg");

    selectionState.FObjectMap.put(41,"assets/Furniture/Shower/Shower_1.jpg");
    selectionState.FObjectMap.put(42,"assets/Furniture/Shower/Shower_2.jpg");
    selectionState.FObjectMap.put(43,"assets/Furniture/Shower/Shower_3.jpg");
    selectionState.FObjectMap.put(44,"assets/Furniture/Shower/Shower_4.jpg");

    selectionState.FObjectMap.put(51,"assets/Furniture/Sink/Sink_1.jpg");
    selectionState.FObjectMap.put(52,"assets/Furniture/Sink/Sink_2.jpg");
    selectionState.FObjectMap.put(53,"assets/Furniture/Sink/Sink_3.jpg");
    selectionState.FObjectMap.put(54,"assets/Furniture/Sink/Sink_4.jpg");

    selectionState.FObjectMap.put(61,"assets/Furniture/Sofa/Sofa_1.jpg");
    selectionState.FObjectMap.put(62,"assets/Furniture/Sofa/Sofa_2.jpg");
    selectionState.FObjectMap.put(63,"assets/Furniture/Sofa/Sofa_3.jpg");
    selectionState.FObjectMap.put(64,"assets/Furniture/Sofa/Sofa_4.jpg");

    selectionState.FObjectMap.put(71,"assets/Furniture/Stove/Stove_1.jpg");
    selectionState.FObjectMap.put(72,"assets/Furniture/Stove/Stove_2.jpg");
    selectionState.FObjectMap.put(73,"assets/Furniture/Stove/Stove_3.jpg");
    selectionState.FObjectMap.put(74,"assets/Furniture/Stove/Stove_4.jpg");
    
    selectionState.FObjectMap.put(81,"assets/Furniture/Table/Table_1.jpg");
    selectionState.FObjectMap.put(82,"assets/Furniture/Table/Table_2.jpg");
    selectionState.FObjectMap.put(83,"assets/Furniture/Table/Table_3.jpg");
    selectionState.FObjectMap.put(84,"assets/Furniture/Table/Table_4.jpg");

    selectionState.FObjectMap.put(91,"assets/Furniture/Washbasin/Washbasin_1.jpg");
    selectionState.FObjectMap.put(92,"assets/Furniture/Washbasin/Washbasin_2.jpg");
    selectionState.FObjectMap.put(93,"assets/Furniture/Washbasin/Washbasin_3.jpg");
    selectionState.FObjectMap.put(94,"assets/Furniture/Washbasin/Washbasin_4.jpg");
}

public static FObject rotate_fixture(FObject object) throws IOException {
    FObject new_fixture; // Declare it outside the try-catch
    int new_id = object.id;
    if (object.id%10!=4){
        new_id +=1;
    } else if (object.id%10==4){
        new_id-=3;
    }
    new_fixture = new FObject(object.topLeft, new_id);

    return new_fixture; // Now it is accessible
}

}