package com.ru.tgra.shapes;

/**
 * Created by VilhjalmurAlex on 10/10/2016.
 */
public class Cell {
    boolean southWall;
    boolean westWall;

    public Cell(){
        southWall = false;
        westWall = false;
    }
    public void setCell(boolean south, boolean west){
        southWall = south;
        westWall = west;
    }
}
