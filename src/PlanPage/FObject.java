package src.PlanPage;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Image;

public class FObject {
    Point topLeft;
    int x, y;
    int id;
    Image im;
    String fname;

    public FObject(Point tl, int ID){
        this.topLeft=tl;
        this.id = ID;
        Toolkit t=Toolkit.getDefaultToolkit();  
        fname = selectionState.FObjectMap.get(ID);
        this.im=t.getImage(fname);  
    }
}
