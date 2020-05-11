/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;


public class var {
    
    int x;
    int y;
    int part;
    int val;
    ArrayList <Integer> p = new ArrayList <Integer>();
    int pCount;
    int posibleA;
    int[][] map = new int[9][9];
    
    var (int x ,int y , int val , int part , int[][] map) {
        
        for(int i=0;i<9;i++) {
            for(int j=0; j<9 ; j++) {
                this.map[i][j] = map[i][j];
            }
        }
        this.x= x;
        this.y= y;
        this.val = val;
        this.part= part;
        pCount=0;
        
    }
    
}
