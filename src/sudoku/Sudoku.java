/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;


/**
 *
 * @author S-AJAD
 */
public class Sudoku {
    public int mm,nn;
    public static var[][] fmap = new var[9][9];
    public static int[][] mapPart;
    public static int[][] map;
    public static int[][] tempMap;
    public static int[][] goalMap = new int[9][9];
    public static ArrayList <var> vars = new ArrayList <var>();
    public static ArrayList <var> varsTemp = new ArrayList <var>();
    state answer = null;
    int nextX=0 , nextY=0;
    
    public void readMap() {
        
        String fileName = "map.txt";
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            map = new int[9][9];
            tempMap = new int[9][9];
            
            int row=0;
            boolean endStartState = false;
            while((line = bufferedReader.readLine()) != null) {
                if (!endStartState && line.length()>1) {
                    for(int i =0 ; i<9;i++) {
                        map[row][i] = line.charAt(i) - '0';
                        tempMap[row][i] = line.charAt(i) - '0';

                    }
                    row++;
                }
                
            }
            // Always close files.
            bufferedReader.close();   
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }   
    }
    
    public void readMapPart() {
        
        String fileName = "mapPart.txt";
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            mapPart = new int[9][9];
            int row=0;
            boolean endStartState = false;
            while((line = bufferedReader.readLine()) != null) {
                if (!endStartState && line.length()>1) {
                    for(int i =0 ; i<9;i++) {
                        
                        mapPart[row][i] = line.charAt(i) - '0';
                    }
                    row++;
                }
                
            }
            // Always close files.
            bufferedReader.close();   
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }   
    }
    
    public void setMap() {
        
        for(int i=0;i<9;i++) {
            for(int j=0 ; j<9;j++) {
                
                fmap[i][j] = new var(i,j, map[i][j] , mapPart[i][j] , map);
                
            }
        }
        
    }
    
    public void setPs() {
        
        for(int i=0;i<9;i++) {
            for(int j=0 ; j<9;j++) {
                if(map[i][j]==0) {
                    for(int k=1;k<=9;k++){

                        //tempMap[i][j]=k;
                        //if (check(tempMap)) {
                            fmap[i][j].p.add(k);
                           //fmap[i][j].p.get(fmap[i][j].pCount) = k;
                           //fmap[i][j].pCount++;
                           //tempMap[i][j]=0;

                       // }

                    }
                }
                
            }
        }
        
    }
   
    public boolean checkRow(int[][] map){
        int seen=0;
        for(int i =0; i<9 ;i++) {
           for(int j=1;j<=9;j++) {
               for(int k=0;seen<2 && k<9;k++) {
                   if(map[i][k]==j && map[i][k]!=0) seen++;
                   if(seen==2) {
                       
                       return false;
                   }
               }
               seen=0;
               
           } 
        }
        return true;
        
    }
    
    public boolean checkCol(int[][] map){
        int seen=0;
        for(int k =0; k<9 ;k++) {
           for(int j=1;j<=9;j++) {
               for(int i=0;seen<2 && i<9;i++) {
                   if(map[i][k]==j && map[i][k]!=0) seen++;
                   if(seen==2) {
                       return false;
                   }
               }
               seen=0;
           } 
        }
        return true;
    }
    
    public boolean checkPart(int[][] map) {
        int[] partSeen = new int[9];
        int counter=0;
        for(int p =1 ; p<=9;p++) {
            for(int sz =0 ; sz<9;sz++) {
                partSeen[sz] = 0;
                counter=0;
            }
            for(int i =0 ; i<9;i++) {
            
                for(int j=0 ; j<9;j++) {
                    if(mapPart[i][j]==p) {
                        if(map[i][j]!=0) {
                            for(int m=0;m<counter;m++){
                                if( partSeen[m]==map[i][j]) {
                                    return false;
                                }
                            }
                            partSeen[counter] = map[i][j];
                            counter++;
                        }
                    }

                }
            
             }
          
        }
        return true;
        
    }
    
    public boolean check(int[][] map) {
        
        if( checkRow(map) && checkPart(map) && checkCol(map) ) {
            return true;
        }
        return false;
        
    }
    
    public boolean isAnswer(int[][] map) {
        for(int i=0;i<9 ;i++){
            
            for(int j=0 ; j<9;j++) {
                if (map[i][j]==0) return false;
            }
            
        }
        if(check(map)) return true;
        else return false;
    }
    
    public var nextMove(int[][] map) {
       
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){     
              if(map[i][j]==0) {
                  return fmap[i][j];
              }  
            }
        }
        return null;
        
    }
    
    public int emptyInRow(int[][] map , int i) {
        int c=0;
        for(int j=0;j<9;j++) {
            if(map[i][j]==0) c++;
        }
        return c;
    }
    
    public int emptyInCol(int[][] map ,int i) {
        int c=0;
        for(int j=0;j<9;j++) {
            if(map[j][i]==0) c++;
        }
        return c;
    }
    
    public int emptyInPart(int[][] map ,int p) {
        int c=0;
        for(int i=0;i<9;i++) {
            for(int j=0 ; j<9 ;j++) {
                if(mapPart[i][j]==p && map[i][j]==0) c++;
            }
        }
        return c;
    }
    
    public var nextMoveDgree(int[][] map) {
        int c=0;
        int ct=0;
        var x=null;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(map[i][j]==0) {
                    
                    ct = emptyInCol(map , j) +emptyInRow(map , i) + emptyInPart(map , mapPart[i][j]);  
                    if(ct>c) {
                        x= fmap[i][j];
                        //System.out.println(ct);
                    }
                    
                    
                }
                
            }
        }
        //System.out.println();
        //printMap(x.map);
        return x;
    }
    
    public var nextMoveMRV(int[][] map) {
        int c=10;
        int tc=0;
        var x=null;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                tc=0;
                if(map[i][j]==0) {
                  //eturn fmap[i][j];
                    for(int k=1;k<=9;k++){

                        map[i][j]=k;
                        if (check(map)) {
                           tc++;
                           //fmap[i][j].p.get(fmap[i][j].pCount) = k;
                           //fmap[i][j].pCount++;

                        }

                    }
                    map[i][j]=0;

                    //System.out.println(i+"-"+j+"="+c);

                    if(tc<c) {
                        x = fmap[i][j];
                        c=tc;
                        //System.out.println(c);
                    }
              }  
            }
        }
        return x;
        
    }
    
    public void mrv() {
        int c =0;
        int tc=0;
        int x=0 ,y=0;
        int[][] newMap = new int[9][9];
                
        for(int i =0 ; i<9 ; i++) {
            for(int j=0 ; j<9 ; j++) {
                
                if(map[i][j]==0) {
                    for(int k=1;k<=9;k++){
                        newMap[i][j]=k;
                        if (check(newMap)) {
                           tc++;
                           newMap[i][j]=0;    
                        }
                    }
                    if (tc > c) {
                        c = tc;
                        x =i;
                        y=j;
                    }
                }
                
            }
        }
        nextX=x;
        nextY=y;
        
    }
   
    public void s(var var , int[][] map) {
        
        var t;
        for(int i =0 ;i<var.p.size();i++) {
          //System.out.println(var.x +"-"+ var.y +"-"+ var.p.get(i) +"-"+ var.part);
          t= new var(var.x , var.y , var.p.get(i) , var.part ,map);
          //System.out.println(var.p.get(i));
          vars.add(t);
        }
        
    }
    
    public static void printMap(int[][] map) {
        for(int i=0 ; i<9 ;i++) {
            System.out.printf("|");
            for(int j=0 ; j<9; j++) {
                System.out.printf(" " + map[i][j] + " |" );
            }
            System.out.println();
        }
    }
    
    public void addVarToMap(int[][] map , var var) {
        
        tempMap[var.x][var.y] = var.val;
        
    }
    
    public int possibleValue(int[][] map) {
        int c=0;
        for(int i=0;i<9;i++) {
            for(int j =0 ; j<9 ; j++) {
                if(map[i][j]==0) {
                    for(int k=1;k<=9;k++) {
                        map[i][j]=k;
                        if (check(map)) c++;
                        map[i][j]=0;
                    }
                }
            }
        }
        return c;
    }
    
    public void sortV() {
     
        var temp;
        
        for (int i = 0; i < vars.size(); i++)
        {
            for (int j = i + 1; j < vars.size(); j++)
            {
                int f1= vars.get(i).posibleA ;
                int f2= vars.get(j).posibleA ;

                if (f1 > f2)
                {
                    temp =  vars.get(i);
                    vars.set(i,vars.get(j));
                    vars.set(j,temp);
                }
                
            }
        
        }
        
        
    }
    
    public int z(int[][] map) {
        
        int c=0;
        for(int i=0 ; i<9 ;i++) {
            for(int j=0 ; j<9; j++) {
                if ( map[i][j]==0) c++;
            }
        }
        return c;
    }
    
    public void bt(){
        boolean end=false;
        Stack stack = new Stack();
        var t = nextMove(tempMap);
        s(t ,tempMap);
        for(int i=0;i<vars.size();i++) {
            //System.out.println(i+1);
            stack.push(vars.get(i));
        }
        vars.clear();
        
        //stack.add();
        
        
        
        while(!stack.isEmpty() && !end){
            //removes from front of queue

            
            var r = (var)stack.pop();
            for(int i=0;i<9;i++) {
                for(int j=0; j<9 ; j++) {
                    tempMap[i][j] = r.map[i][j];
                }
            }
            //System.out.println("from stack : " + r.x+ "x"+ r.y +"=" + r.val);
            addVarToMap(tempMap , r);
            //System.out.println("from tempMap : " + tempMap[r.x][r.y]);
            
           // System.out.println("from r.map : " + r.map[r.x][r.y]);


            if(!check(tempMap)) continue;
            

            //printMap(r.map);
            //System.out.println(r.x+ "x"+ r.y +"-->" + r.val);

            //System.out.println(z(r.map));
            
            //tempMap[r.x][r.y]=r.val;

            t = nextMove(tempMap);
            
           // if(t!=null) {
                if(z(tempMap)==0) {
                    end=true;
                    for(int i=0;i<9 ; i++) {
                        for(int j=0;j<9;j++) {
                            goalMap[i][j]= tempMap[i][j];
                            end= true;
                        }
                    }
                    break;
                }
                //System.out.println(t.x + "x"+t.y);
                s(t,tempMap);
                for(int i=0;i<vars.size();i++) {
                    stack.push(vars.get(i));
                }
                vars.clear();
           // }
         }
        
         System.out.println("Algorithm: BT \n");
        
    }
    
    public void btWithMRV(){
        boolean end=false;
        Stack stack = new Stack();
        var t = nextMoveMRV(tempMap);
        //System.out.println(t.x +"-" +t.y);
        s(t ,tempMap);
        //System.out.println(vars.size());
        for(int i=0;i<vars.size();i++) {
            //System.out.println(i+1);
            stack.push(vars.get(i));
        }
        vars.clear();
        
        //stack.add();
        
        
        
        while(!stack.isEmpty() && !end){
            //removes from front of queue

           // System.out.println(1);
            var r = (var)stack.pop();
            //System.out.println(stack.size());
            for(int i=0;i<9;i++) {
                for(int j=0; j<9 ; j++) {
                    tempMap[i][j] = r.map[i][j];
                }
            }
            //System.out.println("from stack : " + r.x+ "x"+ r.y +"=" + r.val);
            addVarToMap(tempMap , r);
            //System.out.println("from tempMap : " + tempMap[r.x][r.y]);
            
           // System.out.println("from r.map : " + r.map[r.x][r.y]);


            if(!check(tempMap)) continue;
            //System.out.println(2);


            //printMap(r.map);
            //System.out.println(r.x+ "x"+ r.y +"-->" + r.val);

            //System.out.println(z(r.map));
            
            //tempMap[r.x][r.y]=r.val;

            t = nextMoveMRV(tempMap);
            
           // if(t!=null) {
                if(z(tempMap)==0) {
                    end=true; 
                   for(int i=0;i<9 ; i++) {
                        for(int j=0;j<9;j++) {
                            goalMap[i][j]= tempMap[i][j];
                            end= true;
                        }
                    }
                    break;
                }
                //System.out.println(t.x + "x"+t.y);
                s(t,tempMap);
                for(int i=0;i<vars.size();i++) {
                    stack.push(vars.get(i));
                }
                vars.clear();
           // }
         }
        
         System.out.println("Algorithm: BT with MRV \n");
        
    }
    
    public void btWithDgree(){
        boolean end=false;
        Stack stack = new Stack();
        var t = nextMoveDgree(tempMap);
        //System.out.println(t.x +"-" +t.y);
        s(t ,tempMap);
        //System.out.println(vars.size());
        for(int i=0;i<vars.size();i++) {
            stack.push(vars.get(i));
        }
        vars.clear();
        
        
        
        
        while(!stack.isEmpty() && !end){

            var r = (var)stack.pop();
            for(int i=0;i<9;i++) {
                for(int j=0; j<9 ; j++) {
                    tempMap[i][j] = r.map[i][j];
                }
            }
            addVarToMap(tempMap , r);
            //printMap(tempMap);
            if(!check(tempMap)) continue;

            //printMap(r.map);
            //System.out.println(r.x+ "x"+ r.y +"-->" + r.val);

            //System.out.println(z(r.map));
            
            //tempMap[r.x][r.y]=r.val;

                t = nextMoveDgree(tempMap);
                //System.out.println(z(tempMap));
                if(z(tempMap)==0) {
                    end=true; 
                   for(int i=0;i<9 ; i++) {
                        for(int j=0;j<9;j++) {
                            goalMap[i][j]= tempMap[i][j];
                            end= true;
                        }
                    }
                    break;
                }
                //System.out.println(t.x + "x"+t.y);
                s(t,tempMap);
                for(int i=0;i<vars.size();i++) {
                    stack.push(vars.get(i));
                }
                vars.clear();
           // }
         }
        
         System.out.println("Algorithm: BT with Dgree \n");
        
    }
    
    public void btWithLCV(){
        boolean end=false;
        Stack stack = new Stack();
        var t = nextMove(tempMap);
        s(t ,tempMap);
        for(int i=0;i<vars.size();i++) {
            //System.out.println(i+1);
            stack.push(vars.get(i));
        }
        vars.clear();
        
        //stack.add();
        
        
        
        while(!stack.isEmpty() && !end){
            //removes from front of queue

            
            var r = (var)stack.pop();
            for(int i=0;i<9;i++) {
                for(int j=0; j<9 ; j++) {
                    tempMap[i][j] = r.map[i][j];
                }
            }
            //System.out.println("from stack : " + r.x+ "x"+ r.y +"=" + r.val);
            addVarToMap(tempMap , r);
            //System.out.println("from tempMap : " + tempMap[r.x][r.y]);
            
           // System.out.println("from r.map : " + r.map[r.x][r.y]);


            if(!check(tempMap)) continue;
            

            //printMap(r.map);
            //System.out.println(r.x+ "x"+ r.y +"-->" + r.val);

            //System.out.println(z(r.map));
            
            //tempMap[r.x][r.y]=r.val;

            t = nextMove(tempMap);
            
           // if(t!=null) {
                if(z(tempMap)==0) {
                    end=true;
                    for(int i=0;i<9 ; i++) {
                        for(int j=0;j<9;j++) {
                            goalMap[i][j]= tempMap[i][j];
                            end= true;
                        }
                    }
                    break;
                }
                //System.out.println(t.x + "x"+t.y);
                s(t,tempMap);
            
                for(int i=0;i<vars.size();i++) {
                    
                    tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
                    if(check(tempMap)) {
                        vars.get(i).posibleA = possibleValue(tempMap);
                        //System.out.println(vars.get(i).posibleA);
                        tempMap[vars.get(i).x][vars.get(i).y] = 0;                        
                    }
                }
                
                sortV();
                for(int i=0;i<vars.size();i++) {
                        stack.push(vars.get(i)); 
                }
           // }
                //System.out.println("====");
                vars.clear();
            }
         
        
         System.out.println("Algorithm: BT With LCV\n");
        
    }
    
    public void btWithMRV_LCV(){
        boolean end=false;
        Stack stack = new Stack();
        var t = nextMoveMRV(tempMap);
        s(t ,tempMap);
        for(int i=0;i<vars.size();i++) {
            //System.out.println(i+1);
            stack.push(vars.get(i));
        }
        vars.clear();
        
        //stack.add();
        
        
        
        while(!stack.isEmpty() && !end){
            //removes from front of queue

            
            var r = (var)stack.pop();
            for(int i=0;i<9;i++) {
                for(int j=0; j<9 ; j++) {
                    tempMap[i][j] = r.map[i][j];
                }
            }
            //System.out.println("from stack : " + r.x+ "x"+ r.y +"=" + r.val);
            addVarToMap(tempMap , r);
            //System.out.println("from tempMap : " + tempMap[r.x][r.y]);
            
           // System.out.println("from r.map : " + r.map[r.x][r.y]);


            if(!check(tempMap)) continue;
            

            //printMap(r.map);
            //System.out.println(r.x+ "x"+ r.y +"-->" + r.val);

            //System.out.println(z(r.map));
            
            //tempMap[r.x][r.y]=r.val;

            t = nextMoveMRV(tempMap);
            
           // if(t!=null) {
                if(z(tempMap)==0) {
                    end=true;
                    for(int i=0;i<9 ; i++) {
                        for(int j=0;j<9;j++) {
                            goalMap[i][j]= tempMap[i][j];
                            end= true;
                        }
                    }
                    break;
                }
                //System.out.println(t.x + "x"+t.y);
                s(t,tempMap);
            
                for(int i=0;i<vars.size();i++) {
                    
                    tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
                    if(check(tempMap)) {
                        vars.get(i).posibleA = possibleValue(tempMap);
                        tempMap[vars.get(i).x][vars.get(i).y] = 0;                        
                    }
                }
                
                sortV();
                for(int i=0;i<vars.size();i++) {
                        stack.push(vars.get(i)); 
                }
           // }
                //System.out.println("====");
                vars.clear();
            }
         
        
         System.out.println("Algorithm: BT With MRV and LCV\n");
        
    }
   
    public void btWithDgree_LCV(){
        boolean end=false;
        Stack stack = new Stack();
        var t = nextMoveMRV(tempMap);
        s(t ,tempMap);
        for(int i=0;i<vars.size();i++) {
            //System.out.println(i+1);
            stack.push(vars.get(i));
        }
        vars.clear();
        
        //stack.add();
        
        
        
        while(!stack.isEmpty() && !end){
            //removes from front of queue

            
            var r = (var)stack.pop();
            for(int i=0;i<9;i++) {
                for(int j=0; j<9 ; j++) {
                    tempMap[i][j] = r.map[i][j];
                }
            }
            //System.out.println("from stack : " + r.x+ "x"+ r.y +"=" + r.val);
            addVarToMap(tempMap , r);
         
            if(!check(tempMap)) continue;
            

            t = nextMoveMRV(tempMap);
            
           // if(t!=null) {
                if(z(tempMap)==0) {
                    end=true;
                    for(int i=0;i<9 ; i++) {
                        for(int j=0;j<9;j++) {
                            goalMap[i][j]= tempMap[i][j];
                            end= true;
                        }
                    }
                    break;
                }
                //System.out.println(t.x + "x"+t.y);
                s(t,tempMap);
            
                for(int i=0;i<vars.size();i++) {
                    
                    tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
                    if(check(tempMap)) {
                        vars.get(i).posibleA = possibleValue(tempMap);
                        tempMap[vars.get(i).x][vars.get(i).y] = 0;                        
                    }
                }
                
                sortV();
                for(int i=0;i<vars.size();i++) {
                        stack.push(vars.get(i)); 
                }
           // }
                //System.out.println("====");
                vars.clear();
            }
         
        
         System.out.println("Algorithm: BT With dgree and LCV\n");
        
    }
    
    public boolean fcCheck(int[][] map) {
        int p=0;
        for(int i=0 ; i<9 ;i++) {
            for(int j=0;j<9;j++) {
                p=0;
                fmap[i][j].p.clear();
                if(map[i][j]==0) {
                    for(int k=1;k<=9;k++) {
                        map[i][j] = k;
                        if(check(map)) { 
                            p++;
                            fmap[i][j].p.add(k);
                        }
                        map[i][j]=0;
                    }
                    fmap[i][j].posibleA = p;
                    if(p==0) {
                        mm = i;
                        nn=j;
                        return false;
                    }
                }
                
            }
        }
        return true;
    }
    
    public void fc() {
        
        boolean end=false;
        Stack stack = new Stack();
        var tv;
        var t = nextMove(tempMap);
        for(int i=0;i<t.p.size();i++) {
            tempMap[t.x][t.y] = t.p.get(i);
            tv= new var(t.x , t.y , t.p.get(i) , t.part ,tempMap);
            //System.out.println(var.p.get(i));
            vars.add(tv);
            tempMap[t.x][t.y] = 0;
        }
        for(int i=0;i<vars.size();i++) {
            tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
            if(check(tempMap)) {
                stack.push(vars.get(i));
            }
            tempMap[vars.get(i).x][vars.get(i).y] = 0;
            //System.out.println(vars.get(i).val);
        }
        vars.clear();
            
        while(!stack.isEmpty() && !end ) {
            var r = (var)stack.pop();
            for(int i=0;i<9;i++) {
                for(int j=0; j<9 ; j++) {
                    tempMap[i][j] = r.map[i][j];
                }
            }
//            if(!fcCheck(tempMap)) {
//              printMap(tempMap);
//              System.out.println(mm + "--"+ nn);
//            }
            if(fcCheck(tempMap)) {   

                

                if(z(tempMap)==0) {
                    end=true;
                    for(int i=0;i<9 ; i++) {
                        for(int j=0;j<9;j++) {
                            goalMap[i][j]= tempMap[i][j];
                            end= true;
                        }
                    }
                    break;
                }

                    //System.out.println("1");
                    t = nextMove(tempMap);
                    //printMap(tempMap);
                    //System.out.println(t.x + "-" + t.y);
                    for(int i=0;i<t.p.size();i++) {
                        tempMap[t.x][t.y] = t.p.get(i);
                        tv= new var(t.x , t.y , t.p.get(i) , t.part ,tempMap);
                        //System.out.println(var.p.get(i));
                        vars.add(tv);
                        tempMap[t.x][t.y] = 0;
                    }

                for(int i=0;i<vars.size();i++) {
                    tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
                    if(check(tempMap)) {
                        stack.push(vars.get(i));
                    }
                    tempMap[vars.get(i).x][vars.get(i).y] = 0;
                }
                vars.clear();
            }
        }
        
        System.out.println("Algorithm: FC \n");

    }
    
    public void fcWithMRV() {
        
        boolean end=false;
        Stack stack = new Stack();
        var tv;
        var t = nextMoveMRV(tempMap);
        for(int i=0;i<t.p.size();i++) {
            tempMap[t.x][t.y] = t.p.get(i);
            tv= new var(t.x , t.y , t.p.get(i) , t.part ,tempMap);
            //System.out.println(var.p.get(i));
            vars.add(tv);
            tempMap[t.x][t.y] = 0;
        }
        for(int i=0;i<vars.size();i++) {
            tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
            if(check(tempMap)) {
                stack.push(vars.get(i));
            }
            tempMap[vars.get(i).x][vars.get(i).y] = 0;
            //System.out.println(vars.get(i).val);
        }
        vars.clear();
            
        while(!stack.isEmpty() && !end ) {
            var r = (var)stack.pop();
            for(int i=0;i<9;i++) {
                for(int j=0; j<9 ; j++) {
                    tempMap[i][j] = r.map[i][j];
                }
            }
//            if(!fcCheck(tempMap)) {
//              printMap(tempMap);
//              System.out.println(mm + "--"+ nn);
//            }
            if(fcCheck(tempMap)) {   

                

                if(z(tempMap)==0) {
                    end=true;
                    for(int i=0;i<9 ; i++) {
                        for(int j=0;j<9;j++) {
                            goalMap[i][j]= tempMap[i][j];
                            end= true;
                        }
                    }
                    break;
                }

                    //System.out.println("1");
                    t = nextMoveMRV(tempMap);
                    //printMap(tempMap);
                    //System.out.println(t.x + "-" + t.y);
                    for(int i=0;i<t.p.size();i++) {
                        tempMap[t.x][t.y] = t.p.get(i);
                        tv= new var(t.x , t.y , t.p.get(i) , t.part ,tempMap);
                        //System.out.println(var.p.get(i));
                        vars.add(tv);
                        tempMap[t.x][t.y] = 0;
                    }

                for(int i=0;i<vars.size();i++) {
                    tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
                    if(check(tempMap)) {
                        stack.push(vars.get(i));
                    }
                    tempMap[vars.get(i).x][vars.get(i).y] = 0;
                }
                vars.clear();
            }
        }
        
        System.out.println("Algorithm: FC+ MRV \n");

    }
    
    public void fcWithMRV_LCV() {
        
        boolean end=false;
        Stack stack = new Stack();
        var tv;
        var t = nextMoveMRV(tempMap);
        for(int i=0;i<t.p.size();i++) {
            tempMap[t.x][t.y] = t.p.get(i);
            tv= new var(t.x , t.y , t.p.get(i) , t.part ,tempMap);
            //System.out.println(var.p.get(i));
            vars.add(tv);
            tempMap[t.x][t.y] = 0;
        }
        for(int i=0;i<vars.size();i++) {
            tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
            if(check(tempMap)) {
                stack.push(vars.get(i));
            }
            tempMap[vars.get(i).x][vars.get(i).y] = 0;
            //System.out.println(vars.get(i).val);
        }
        vars.clear();
            
        while(!stack.isEmpty() && !end ) {
            var r = (var)stack.pop();
            for(int i=0;i<9;i++) {
                for(int j=0; j<9 ; j++) {
                    tempMap[i][j] = r.map[i][j];
                }
            }
//            if(!fcCheck(tempMap)) {
//              printMap(tempMap);
//              System.out.println(mm + "--"+ nn);
//            }
            if(fcCheck(tempMap)) {   

                

                if(z(tempMap)==0) {
                    end=true;
                    for(int i=0;i<9 ; i++) {
                        for(int j=0;j<9;j++) {
                            goalMap[i][j]= tempMap[i][j];
                            end= true;
                        }
                    }
                    break;
                }

                    //System.out.println("1");
                    t = nextMoveMRV(tempMap);
                    //printMap(tempMap);
                    //System.out.println(t.x + "-" + t.y);
                    for(int i=0;i<t.p.size();i++) {
                        tempMap[t.x][t.y] = t.p.get(i);
                        tv= new var(t.x , t.y , t.p.get(i) , t.part ,tempMap);
                        //System.out.println(var.p.get(i));
                        vars.add(tv);
                        tempMap[t.x][t.y] = 0;
                    }
                for(int i=0;i<vars.size();i++) {
                    
                    tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
                    if(check(tempMap)) {
                        vars.get(i).posibleA = possibleValue(tempMap);
                        tempMap[vars.get(i).x][vars.get(i).y] = 0;                        
                    }
                }
                
                sortV();
                for(int i=0;i<vars.size();i++) {
                    tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
                    if(check(tempMap)) {
                        stack.push(vars.get(i));
                    }
                    tempMap[vars.get(i).x][vars.get(i).y] = 0;
                }
                vars.clear();
            }
        }
        
        System.out.println("Algorithm: FC + MRV + LCV \n");

    }
     
    public void fcWithDgree() {
        
        boolean end=false;
        Stack stack = new Stack();
        var tv;
        var t = nextMoveDgree(tempMap);
        for(int i=0;i<t.p.size();i++) {
            tempMap[t.x][t.y] = t.p.get(i);
            tv= new var(t.x , t.y , t.p.get(i) , t.part ,tempMap);
            //System.out.println(var.p.get(i));
            vars.add(tv);
            tempMap[t.x][t.y] = 0;
        }
        for(int i=0;i<vars.size();i++) {
            tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
            if(check(tempMap)) {
                stack.push(vars.get(i));
            }
            tempMap[vars.get(i).x][vars.get(i).y] = 0;
            //System.out.println(vars.get(i).val);
        }
        vars.clear();
            
        while(!stack.isEmpty() && !end ) {
            var r = (var)stack.pop();
            for(int i=0;i<9;i++) {
                for(int j=0; j<9 ; j++) {
                    tempMap[i][j] = r.map[i][j];
                }
            }
//            if(!fcCheck(tempMap)) {
//              printMap(tempMap);
//              System.out.println(mm + "--"+ nn);
//            }
            if(fcCheck(tempMap)) {   

                

                if(z(tempMap)==0) {
                    end=true;
                    for(int i=0;i<9 ; i++) {
                        for(int j=0;j<9;j++) {
                            goalMap[i][j]= tempMap[i][j];
                            end= true;
                        }
                    }
                    break;
                }

                    //System.out.println("1");
                    t = nextMoveDgree(tempMap);
                    //printMap(tempMap);
                    //System.out.println(t.x + "-" + t.y);
                    for(int i=0;i<t.p.size();i++) {
                        tempMap[t.x][t.y] = t.p.get(i);
                        tv= new var(t.x , t.y , t.p.get(i) , t.part ,tempMap);
                        //System.out.println(var.p.get(i));
                        vars.add(tv);
                        tempMap[t.x][t.y] = 0;
                    }

                for(int i=0;i<vars.size();i++) {
                    tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
                    if(check(tempMap)) {
                        stack.push(vars.get(i));
                    }
                    tempMap[vars.get(i).x][vars.get(i).y] = 0;
                }
                vars.clear();
            }
        }
        
        System.out.println("Algorithm: FC + Dgree \n");

    }
    
    public void fcWithDgree_LCV() {
        
        boolean end=false;
        Stack stack = new Stack();
        var tv;
        var t = nextMoveDgree(tempMap);
        for(int i=0;i<t.p.size();i++) {
            tempMap[t.x][t.y] = t.p.get(i);
            tv= new var(t.x , t.y , t.p.get(i) , t.part ,tempMap);
            //System.out.println(var.p.get(i));
            vars.add(tv);
            tempMap[t.x][t.y] = 0;
        }
        for(int i=0;i<vars.size();i++) {
            tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
            if(check(tempMap)) {
                stack.push(vars.get(i));
            }
            tempMap[vars.get(i).x][vars.get(i).y] = 0;
            //System.out.println(vars.get(i).val);
        }
        vars.clear();
            
        while(!stack.isEmpty() && !end ) {
            var r = (var)stack.pop();
            for(int i=0;i<9;i++) {
                for(int j=0; j<9 ; j++) {
                    tempMap[i][j] = r.map[i][j];
                }
            }
//            if(!fcCheck(tempMap)) {
//              printMap(tempMap);
//              System.out.println(mm + "--"+ nn);
//            }
            if(fcCheck(tempMap)) {   

                

                if(z(tempMap)==0) {
                    end=true;
                    for(int i=0;i<9 ; i++) {
                        for(int j=0;j<9;j++) {
                            goalMap[i][j]= tempMap[i][j];
                            end= true;
                        }
                    }
                    break;
                }

                    //System.out.println("1");
                    t = nextMoveDgree(tempMap);
                    //printMap(tempMap);
                    //System.out.println(t.x + "-" + t.y);
                    for(int i=0;i<t.p.size();i++) {
                        tempMap[t.x][t.y] = t.p.get(i);
                        tv= new var(t.x , t.y , t.p.get(i) , t.part ,tempMap);
                        //System.out.println(var.p.get(i));
                        vars.add(tv);
                        tempMap[t.x][t.y] = 0;
                    }
                for(int i=0;i<vars.size();i++) {
                    
                    tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
                    if(check(tempMap)) {
                        vars.get(i).posibleA = possibleValue(tempMap);
                        tempMap[vars.get(i).x][vars.get(i).y] = 0;                        
                    }
                }
                
                sortV();
                for(int i=0;i<vars.size();i++) {
                    tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
                    if(check(tempMap)) {
                        stack.push(vars.get(i));
                    }
                    tempMap[vars.get(i).x][vars.get(i).y] = 0;
                }
                vars.clear();
            }
        }
        
        System.out.println("Algorithm: FC + Dgree + LCV \n");

    }
    
    public boolean ac3Check() {
        int p;
        for(int i=0 ; i<9 ;i++) {
            for(int j=0;j<9;j++) {
                if(map[i][j]==0) {
                    for(int k=1;k<=9;k++) {
                        map[i][j]=k;
                        if(check(map)) {
                            for(int ii=0 ; ii<9 ;ii++) {
                                for(int jj=0;jj<9;jj++) {
                                    if(map[ii][jj]==0) {
                                        p=0;
                                        for(int kk=1;kk<=9;kk++) {
                                            map[ii][jj]=kk;
                                            if (check(map)) {
                                                p++;
                                                continue;
                                            }
                                        }
                                        if(p==0) {
                                            return false;
                                        }
                                       
                                    }
                                }
                            }
                        }
                    }
                }
            }
        
        }
        return true;
    }
    
    public void ac3() {
        
        boolean end=false;
        Stack stack = new Stack();
        var tv;
        var t = nextMove(tempMap);
        for(int i=0;i<t.p.size();i++) {
            tempMap[t.x][t.y] = t.p.get(i);
            tv= new var(t.x , t.y , t.p.get(i) , t.part ,tempMap);
            //System.out.println(var.p.get(i));
            vars.add(tv);
            tempMap[t.x][t.y] = 0;
        }
        for(int i=0;i<vars.size();i++) {
            tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
            if(check(tempMap)) {
                stack.push(vars.get(i));
            }
            tempMap[vars.get(i).x][vars.get(i).y] = 0;
            //System.out.println(vars.get(i).val);
        }
        vars.clear();
            
        while(!stack.isEmpty() && !end ) {
            var r = (var)stack.pop();
            for(int i=0;i<9;i++) {
                for(int j=0; j<9 ; j++) {
                    tempMap[i][j] = r.map[i][j];
                }
            }
//            if(!fcCheck(tempMap)) {
//              printMap(tempMap);
//              System.out.println(mm + "--"+ nn);
//            }
            if(ac3Check() ) {   

                

                if(z(tempMap)==0) {
                    end=true;
                    for(int i=0;i<9 ; i++) {
                        for(int j=0;j<9;j++) {
                            goalMap[i][j]= tempMap[i][j];
                            end= true;
                        }
                    }
                    break;
                }

                    //System.out.println("1");
                    t = nextMove(tempMap);
                    //printMap(tempMap);
                    //System.out.println(t.x + "-" + t.y);
                    for(int i=0;i<t.p.size();i++) {
                        tempMap[t.x][t.y] = t.p.get(i);
                        tv= new var(t.x , t.y , t.p.get(i) , t.part ,tempMap);
                        //System.out.println(var.p.get(i));
                        vars.add(tv);
                        tempMap[t.x][t.y] = 0;
                    }

                for(int i=0;i<vars.size();i++) {
                    tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
                    if(check(tempMap)) {
                        stack.push(vars.get(i));
                    }
                    tempMap[vars.get(i).x][vars.get(i).y] = 0;
                }
                vars.clear();
            }
        }
        
        System.out.println("Algorithm: AC3 \n");

    }
    
    public void ac3withFc() {
        
        boolean end=false;
        Stack stack = new Stack();
        var tv;
        var t = nextMove(tempMap);
        for(int i=0;i<t.p.size();i++) {
            tempMap[t.x][t.y] = t.p.get(i);
            tv= new var(t.x , t.y , t.p.get(i) , t.part ,tempMap);
            //System.out.println(var.p.get(i));
            vars.add(tv);
            tempMap[t.x][t.y] = 0;
        }
        for(int i=0;i<vars.size();i++) {
            tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
            if(check(tempMap)) {
                stack.push(vars.get(i));
            }
            tempMap[vars.get(i).x][vars.get(i).y] = 0;
            //System.out.println(vars.get(i).val);
        }
        vars.clear();
            
        while(!stack.isEmpty() && !end ) {
            var r = (var)stack.pop();
            for(int i=0;i<9;i++) {
                for(int j=0; j<9 ; j++) {
                    tempMap[i][j] = r.map[i][j];
                }
            }
//            if(!fcCheck(tempMap)) {
//              printMap(tempMap);
//              System.out.println(mm + "--"+ nn);
//            }
            if(ac3Check() && fcCheck(tempMap)) {   

                

                if(z(tempMap)==0) {
                    end=true;
                    for(int i=0;i<9 ; i++) {
                        for(int j=0;j<9;j++) {
                            goalMap[i][j]= tempMap[i][j];
                            end= true;
                        }
                    }
                    break;
                }

                    //System.out.println("1");
                    t = nextMove(tempMap);
                    //printMap(tempMap);
                    //System.out.println(t.x + "-" + t.y);
                    for(int i=0;i<t.p.size();i++) {
                        tempMap[t.x][t.y] = t.p.get(i);
                        tv= new var(t.x , t.y , t.p.get(i) , t.part ,tempMap);
                        //System.out.println(var.p.get(i));
                        vars.add(tv);
                        tempMap[t.x][t.y] = 0;
                    }

                for(int i=0;i<vars.size();i++) {
                    tempMap[vars.get(i).x][vars.get(i).y] = vars.get(i).val;
                    if(check(tempMap)) {
                        stack.push(vars.get(i));
                    }
                    tempMap[vars.get(i).x][vars.get(i).y] = 0;
                }
                vars.clear();
            }
        }
        
        System.out.println("Algorithm: AC3 + FC \n");

    }
    
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("select Algorithm :\n 1.BT \n 2.BT + MRV \n 3.BT + DGREE \n 4.BT + LCV \n 5.BT + MRV + LCV \n 6.BT + DGREE + LCV \n"
                + " 7.FC \n 8.FC + BT + MRV \n 9.FC + BT + DGREE \n 10.FC + BT + MRV + LCV \n 11.FC + BT + DGREE + LCV \n 12.AC3 \n"
                + " 13.AC3 + FC");
        int myint = keyboard.nextInt();
        Sudoku s = new Sudoku();
        long startTime=0 ,endTime =0 ;

        s.readMap();
        s.readMapPart();
        s.setMap();
        s.setPs();
        
        if(myint==1) {
            startTime = System.currentTimeMillis();
            s.bt();
            endTime = System.currentTimeMillis();

        }
        if(myint==2) {
            startTime = System.currentTimeMillis();
            s.btWithMRV();
            endTime = System.currentTimeMillis();

        }
        if(myint==3) {
            startTime = System.currentTimeMillis();
            s.btWithDgree();
            endTime = System.currentTimeMillis();
        }
        
        if(myint==4) {
            startTime = System.currentTimeMillis();
            s.btWithLCV();
            endTime = System.currentTimeMillis();
        }
        if(myint==5) {
            startTime = System.currentTimeMillis();
            s.btWithMRV_LCV();
            endTime = System.currentTimeMillis();
        }
        if(myint==6) {
            startTime = System.currentTimeMillis();
            s.btWithDgree_LCV();
            endTime = System.currentTimeMillis();
        }
        if(myint==7) {
            startTime = System.currentTimeMillis();
            s.fc();
            endTime = System.currentTimeMillis();
        }
        if(myint==8) {
            startTime = System.currentTimeMillis();
            s.fcWithMRV();
            endTime = System.currentTimeMillis();
        }
        if(myint==9) {
            startTime = System.currentTimeMillis();
            s.fcWithDgree();
            endTime = System.currentTimeMillis();
        }
        if(myint==10) {
            startTime = System.currentTimeMillis();
            s.fcWithMRV_LCV();
            endTime = System.currentTimeMillis();
        }
        if(myint==11) {
            startTime = System.currentTimeMillis();
            s.fcWithDgree_LCV();
            endTime = System.currentTimeMillis();
        }
        
        if(myint==12) {
            startTime = System.currentTimeMillis();
            s.ac3();
            endTime = System.currentTimeMillis();
        }
        if(myint==13) {
            startTime = System.currentTimeMillis();
            s.ac3withFc();
            endTime = System.currentTimeMillis();
        }
        
        
        printMap(goalMap);
        System.out.println("Time: " + (endTime - startTime) + " MiliSec");

        
    }
    
}
