/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * a class rep the Board which x = 0 & y = 0 in top-left of the board 
 */
public class Board {
    
    private int xSize, ySize;
    private String[][] boardState = null;
    private boolean[][] bomb = null;

    private static final String UNTOUCHED = new String("-");
    private static final String FLAG = new String("F");
    private static final String DUG = new String(" ");

    private static final List<String> VALID_LIST = new ArrayList<>(Arrays.asList(UNTOUCHED, FLAG, DUG,
                                                    "1", "2", "3", "4", "5", "6", "7", "8"));

    /**
     * Abstraction function:
     *    boardState[i][j] rep the index i, j state of the board;
     * Represent Invariance:
     *    each String in boardState is in set{F, 1 - 8, -}  
     * Rep exposure:
     *    argument is private , all mutator & producer return  
     * a new instance
     */


    public Board(File file) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(file));
        String[] lineString = in.readLine().split(" ");
        xSize = Integer.parseInt(lineString[0]);
        ySize = Integer.parseInt(lineString[1]);
        boardState = new String[ySize][xSize];
        bomb = new boolean[ySize][xSize];

        for(int i = 0; i < ySize; i++) {
            lineString = in.readLine().split(" ");
            for(int j = 0; j < xSize; j++) {
                boardState[i][j] = UNTOUCHED;
                bomb[i][j] = lineString[j].equals("1");
            }
        }

        in.close();

    }

    public Board(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        boardState = new String[ySize][xSize];
        bomb = new boolean[ySize][xSize];
        Random r = new Random();

        for(int i = 0; i < ySize; i++) {
            for(int j = 0; j < xSize; j++) {
                boardState[i][j] = UNTOUCHED;
                bomb[i][j] = ((r.nextInt()%2==0)?true:false);
            }
        }
    }

    public void checkrep() {
        for(int i = 0; i < ySize; i++)
            for(int j = 0; j < xSize; j++) {
                assert (VALID_LIST.contains(boardState[i][j])); 
            }
    }

    /**
     * Dig the position x, y in board
     * 
     * @param x index
     * @param y index
     * @return return false while x, y is a bomb
     */
    public synchronized boolean dig(int x, int y) {
        if(!checkPositionValid(x, y) || bomb[y][x] || !boardState[y][x].equals(UNTOUCHED)) 
            return false;
        
        boardState[y][x] = ((countBomb(x, y)==0)?DUG:String.valueOf(countBomb(x, y)));
        if(countBomb(x, y)==0) {
            List<List<Integer>> around = aroudPosition(x, y);
            for(List<Integer> point:around) {
                dig(point.get(0), point.get(1));
            }
        }
        return true;
    }

    /**
     * mark position x, y in as flag
     * if x, y is invalid or it isnot untouched ,
     * donot change anything
     * 
     * @param x
     * @param y
     */
    public synchronized void flag(int x, int y) {
        if(!checkPositionValid(x, y) || boardState[y][x].equals(UNTOUCHED)) 
            return ;

        boardState[y][x] = FLAG;
        return ;
    }

    /**
     * mark position x, y as untouched
     * if x, y is invalid or it isnot flag,
     * donot change anything
     * 
     * @param x
     * @param y
     */
    public synchronized void deflag(int x, int y) {
        if(!checkPositionValid(x, y) || boardState[y][x].equals(FLAG)) 
            return ;

        boardState[y][x] = UNTOUCHED;
        return;
    }

    /**
     * return state of x, y
     * 
     * @param x
     * @param y
     * @return state of x, y 
     */
    public synchronized boolean look(int x, int y) {
        if(!checkPositionValid(x, y))
            return false;
        
        return bomb[y][x];
    }

    /**
     * change a bomb to none
     * 
     * @param x
     * @param y
     */
    public synchronized void deBomb(int x, int y) {
        bomb[y][x] = false;
    }
   
    /**
     * count bomb num around the position x,y
     * 
     * @param x index 
     * @param y index
     * @return num from 0-8
     */
    private int countBomb(int x, int y) {
        assert(checkPositionValid(x, y));

        int count = 0;
        List<List<Integer>> around = aroudPosition(x, y);
        for(List<Integer> point:around) {
            count = count + (bomb[point.get(0)][point.get(1)]?0:1);
        }

        return count;
    }

    /**
     * get the aroud position of x, y
     * @param x index
     * @param y index
     * @return a List, which include the aroud position
     */
    private List<List<Integer>> aroudPosition(int x, int y) {
        assert(checkPositionValid(x, y));
        
        List<List<Integer>> pos = new ArrayList<>();
        for(int xBias:new ArrayList<Integer>(Arrays.asList(1, -1, 0)))
            for(int yBias:new ArrayList<Integer>(Arrays.asList(1, -1, 0))) {
                if((xBias!=0 || yBias!=0) && checkPositionValid(x+xBias, y+yBias)) {
                    pos.add(Arrays.asList(x+xBias, y+yBias));
                }
            }
        
        return pos;
    }

    /**
     * check a position is valid or not
     * @param x index
     * @param y index
     * @return true if valid else false
     */
    private boolean checkPositionValid(int x, int y) {
        if((x>=0) &&(x<=xSize) && (y>=0) && (y<=ySize))
            return true;
        else 
            return false;
    }
    
    @Override
    public synchronized String toString() {
        String res = "";
        for(int i = 0; i < ySize; i++) {
            for(int j = 0; j < xSize; j++) {
                if(j != xSize - 1)
                    res = res.concat(boardState[i][j] + " ");
                else
                    res = res.concat(boardState[i][j] + "\n");
            }
        }
        return res;
    }
}
