/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;


public class state {
    int[][] map;
    int[][][] x;
    int depth=0;
    
    state(int[][] map) {
        
        this.map = new int[9][9];
        this.x = new int[9][9][9];
        for(int p=0; p<9; p++) {
            for(int l=0; l<9; l++)
                this.map[p][l]= map[p][l];
        }
  
    }
    
    public boolean isEmpty(int x){
        if(x==0) return true;
        else return false;
    }
    
    public void printMap() {
        for(int i=0 ; i<9 ;i++) {
            System.out.printf("|");
            for(int j=0 ; j<9; j++) {
                System.out.printf(" " + map[i][j] + " |" );
            }
            System.out.println();
        }
    }

}
