package src.PlanPage;

import java.awt.Point;

public class matrixUtilities {

    public static int[][] addRoom(int[][] matrix, Point topLeft, Point bottomRight, int Room){
        int[][] newMatrix = matrix; 
        for (int i=topLeft.x; i<=bottomRight.x; i++){
            for (int j=topLeft.y; j<=bottomRight.y; j++){
                newMatrix[i][j] = Room;
            }
        }

        return newMatrix;
    }
}
