/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lasertank;

/**
 *
 * @author Tonaï
 */
public class BoardBox {
    private String path = "images/";
    private String file = "Grass.png";
    private String subObjectFile = "Grass.png";
    private int number = 0;
    private int subObjectNumber = 0;
    
    public String getFile() {
        return file;
    }

    public String getSubObjectFile() {
        return subObjectFile;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        switch (number) {
            case 0:
                this.file = "Grass.png";
                break;
            case 1:
                this.file = "Water.png";
                break;
            case 2:
                this.file = "TankUp.png";
                break;
            case 3:
                this.file = "Flag.png";
                break;
            case 4:
                this.file = "Block.png";
                break;
            case 5:
                this.file = "CrossBlock.png";
                break;
            case 6:
                this.file = "BreakableBlock.png";
                break;
            case 7:
                this.file = "MovableBlock.png";
                break;
            case 8:
                this.file = "MovableMirrorDL.png";
                break;
            case 9:
                this.file = "MovableMirrorUL.png";
                break;
            case 10:
                this.file = "MovableMirrorUR.png";
                break;
            case 11:
                this.file = "MovableMirrorDR.png";
                break;
            case 12:
                this.file = "RotatingMirrorDL.png";
                break;
            case 13:
                this.file = "RotatingMirrorUL.png";
                break;
            case 14:
                this.file = "RotatingMirrorUR.png";
                break;
            case 15:
                this.file = "RotatingMirrorDR.png";
                break;
            case 16:
                this.file = "EnnemiUp.png";
                break;
            case 17:
                this.file = "EnnemiLeft.png";
                break;
            case 18:
                this.file = "EnnemiDown.png";
                break;
            case 19:
                this.file = "EnnemiRight.png";
                break;
            case 36:
                this.file = "WaterBlock.png";
                break;
            case 37:
                this.file = "TankLeft.png";
                break;
            case 38:
                this.file = "TankUp.png";
                break;
            case 39:
                this.file = "TankRight.png";
                break;
            case 40:
                this.file = "TankDown.png";
                break;
        }
    }

    public int getSubObjectNumber() {
        return subObjectNumber;
    }

    public void setSubObjectNumber(int subObjectNumber) {
        this.subObjectNumber = subObjectNumber;
        switch (subObjectNumber) {
            case 0:
                this.subObjectFile = "Grass.png";
                break;
            case 1:
                this.subObjectFile = "Water.png";
                break;
            case 2:
                this.subObjectFile = "Grass.png";
                break;
            case 36:
                this.subObjectFile = "WaterBlock.png";
                break;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public BoardBox() {
    }

}
