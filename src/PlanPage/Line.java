package src.PlanPage;
import java.awt.Point;
import java.io.Serializable;

public class Line implements Serializable {
    Point start, end;
    int id;

    public Line(Point start, Point end, int _id) {
        this.start = start;
        this.end = end;
        this.id = _id;
    }
}
