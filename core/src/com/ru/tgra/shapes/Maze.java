package com.ru.tgra.shapes;

/**
 * Created by VilhjalmurAlex on 11/10/2016.
 */
public class Maze {
    private Cell[][] cells;

    public Maze(){
        cells = new Cell[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                cells[i][j] = new Cell();
            }
        }
    }

    public Cell[][] getCells(){
        fillMaze();
        return cells;
    }

    public void fillMaze(){

        cells[0][0].setCell(false,false);
        cells[1][0].setCell(false,false);
        cells[2][0].setCell(false,false);
        cells[3][0].setCell(true,false);
        cells[4][0].setCell(false,false);
        cells[5][0].setCell(false,false);
        cells[6][0].setCell(true,false);
        cells[7][0].setCell(false,false);
        cells[8][0].setCell(false,false);
        cells[9][0].setCell(false,false);

        cells[0][1].setCell(false,true);
        cells[1][1].setCell(false,true);
        cells[2][1].setCell(false,false);
        cells[3][1].setCell(true,false);
        cells[4][1].setCell(false,true);
        cells[5][1].setCell(true,false);
        cells[6][1].setCell(false,false);
        cells[7][1].setCell(true,true);
        cells[8][1].setCell(false,false);
        cells[9][1].setCell(true,false);

        cells[0][2].setCell(false,false);
        cells[1][2].setCell(false,true);
        cells[2][2].setCell(true,true);
        cells[3][2].setCell(false,false);
        cells[4][2].setCell(true,true);
        cells[5][2].setCell(false,true);
        cells[6][2].setCell(false,true);
        cells[7][2].setCell(true,false);
        cells[8][2].setCell(true,true);
        cells[9][2].setCell(false,false);

        cells[0][3].setCell(false,false);
        cells[1][3].setCell(false,true);
        cells[2][3].setCell(true,false);
        cells[3][3].setCell(true,true);
        cells[4][3].setCell(true,false);
        cells[5][3].setCell(true,true);
        cells[6][3].setCell(false,false);
        cells[7][3].setCell(true,false);
        cells[8][3].setCell(true,true);
        cells[9][3].setCell(false,true);

        cells[0][4].setCell(false,true);
        cells[1][4].setCell(false,false);
        cells[2][4].setCell(true,false);
        cells[3][4].setCell(false,false);
        cells[4][4].setCell(true,false);
        cells[5][4].setCell(false,true);
        cells[6][4].setCell(true,true);
        cells[7][4].setCell(false,false);
        cells[8][4].setCell(true,false);
        cells[9][4].setCell(true,false);

        cells[0][5].setCell(false,false);
        cells[1][5].setCell(true,false);
        cells[2][5].setCell(false,true);
        cells[3][5].setCell(true,false);
        cells[4][5].setCell(false,false);
        cells[5][5].setCell(true,false);
        cells[6][5].setCell(true,false);
        cells[7][5].setCell(false,true);
        cells[8][5].setCell(true,false);
        cells[9][5].setCell(true,true);

        cells[0][6].setCell(false,false);
        cells[1][6].setCell(true,true);
        cells[2][6].setCell(false,true);
        cells[3][6].setCell(false,true);
        cells[4][6].setCell(false,true);
        cells[5][6].setCell(false,false);
        cells[6][6].setCell(true,true);
        cells[7][6].setCell(true,false);
        cells[8][6].setCell(false,false);
        cells[9][6].setCell(true,false);

        cells[0][7].setCell(false,false);
        cells[1][7].setCell(true,false);
        cells[2][7].setCell(true,true);
        cells[3][7].setCell(false,true);
        cells[4][7].setCell(false,true);
        cells[5][7].setCell(true,false);
        cells[6][7].setCell(true,false);
        cells[7][7].setCell(true,true);
        cells[8][7].setCell(false,true);
        cells[9][7].setCell(false,false);

        cells[0][8].setCell(false,false);
        cells[1][8].setCell(true,false);
        cells[2][8].setCell(false,false);
        cells[3][8].setCell(true,false);
        cells[4][8].setCell(true,false);
        cells[5][8].setCell(true,false);
        cells[6][8].setCell(false,false);
        cells[7][8].setCell(true,false);
        cells[8][8].setCell(true,false);
        cells[9][8].setCell(false,true);

        cells[0][9].setCell(false,false);
        cells[1][9].setCell(false,true);
        cells[2][9].setCell(false,true);
        cells[3][9].setCell(false,false);
        cells[4][9].setCell(true,false);
        cells[5][9].setCell(false,true);
        cells[6][9].setCell(false,true);
        cells[7][9].setCell(false,true);
        cells[8][9].setCell(false,true);
        cells[9][9].setCell(false,false);
    }
}
