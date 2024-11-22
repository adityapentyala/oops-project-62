package src.PlanPage;

import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import java.awt.Image;

public class FObject implements Serializable {
    Point topLeft;
    int x, y;
    int id;
    //Image im;
    String fname;

    public FObject(Point tl, int ID) throws IOException{
        this.topLeft=tl;
        this.id = ID;
        //Toolkit t=Toolkit.getDefaultToolkit();  
        fname = selectionState.FObjectMap.get(ID);  
        //this.im = ImageIO.read(new File(fname));
        //this.im = t.getImage(fname);
    }
}
