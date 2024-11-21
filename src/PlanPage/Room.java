package src.PlanPage;
import java.awt.Point;

public class Room{
    Point topLeft, bottomRight, topRight, bottomLeft;
    int width, height, id;
    public Room(Point start, Point end, int _id){
        if (start.x<end.x && start.y<end.y){
            this.topLeft=new Point(start.x, start.y);
            this.bottomRight=new Point(end.x, end.y);
        } else if (start.x<end.x && start.y>end.y){
            this.topLeft=new Point(start.x, end.y);
            this.bottomRight=new Point(end.x, start.y);
        } else if (start.x>end.x && start.y<end.y){
            this.topLeft=new Point(end.x, start.y);
            this.bottomRight=new Point(start.x, end.y);
        } else {
            this.topLeft=new Point(end.x, end.y);
            this.bottomRight=new Point(start.x, start.y);
        }
        this.bottomLeft=new Point(topLeft.x, bottomRight.y);
        this.width = bottomRight.x-topLeft.x;
        this.height = bottomRight.y-topLeft.y;
        this.id = _id;
    }
}
