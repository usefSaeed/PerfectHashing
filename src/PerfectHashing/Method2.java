package PerfectHashing;

import java.util.ArrayList;
import java.util.Random;

public class Method2 {
    private final int[] input;
    private final int[] mainHashMatrix;
    private int[] hashLvl1;
    private final int[][] hashMatricesLvl2;
    private final int[][] hashLvl2;
    private final int size;
    private boolean usedLvl2;

    public Method2(int[] input) {
        this.input = input;
        this.size = input.length;
        this.hashLvl1 = new int[this.size];
        this.mainHashMatrix = generateHashMatrix(this.size);
        this.hashMatricesLvl2 = new int[this.size][];
        this.hashLvl2 = new int[this.size][];
        this.usedLvl2 = false;
        this.hashPerfectionist();
    }

    int[] generateHashMatrix(int size){
        int[] res = new int[size];
        Random rand = new Random();
        for (int  i=0; i<size;i++)
            res[i] = rand.nextInt(Integer.MAX_VALUE);
        return res;
    }

    int getKey(int[] h,int x){
        int key=0;
        int size = h.length;
        for (int i = 0; i < size; i++){
            key=key<<1;
            key |= parity(x & h[i]);
        }
        return key%size;
    }

    int parity(int p){
        int isOdd=0;
        while(p!=0){
            isOdd ^= 1;
            p &= p-1;
        }
        return isOdd;
    }

    void hashPerfectionist(){
        if (!doneByLvl1()) perfectRehash();
    }

    boolean doneByLvl1(){
        boolean[] beenHereBefore = new boolean[size];
        for (int i=0;i<size;i++){
            int key = getKey(mainHashMatrix,input[i]);
            if (beenHereBefore[key]) return false;
            beenHereBefore[key] = true;
            hashLvl1[key] = input[i];
        }
        return true;
    }

    void perfectRehash(){
        usedLvl2 = true;
        hashLvl1 = new int[size];
        boolean[] beenHereBefore = new boolean[size];
        ArrayList[] collisionCont = new ArrayList[size];
        for (int i=0;i<size;i++){
            int key = getKey(mainHashMatrix,input[i]);
            hashLvl1[key]++;
            if (!beenHereBefore[key])
                collisionCont[key] = new ArrayList<Integer>();
            beenHereBefore[key] = true;
            collisionCont[key].add(input[i]);
        }
        for (int i=0;i<size;i++){
            int collisionsNum = hashLvl1[i];
            if (collisionsNum==0) continue;
            int newHashSize = collisionsNum*collisionsNum;
            hashMatricesLvl2[i] = generateHashMatrix(newHashSize);
            hashLvl2[i] = new int[newHashSize];
            if (newHashSize==1){
                hashLvl2[i][0] = (int) collisionCont[i].get(0);
                continue;
            }
            int[] lvl2CurrentBlock = convertToArray((ArrayList<Integer>) collisionCont[i]);
            Method1 m = new Method1(lvl2CurrentBlock);
            hashLvl2[i] = m.getFinalHashTable();
            hashMatricesLvl2[i] = m.getHashMatrix();
        }

    }

    int[] convertToArray(ArrayList<Integer> a){
        int[] b = new int[a.size()];
        int i=0;
        for (int elem : a) b[i++] = elem;
        return b;
    }

    void search(int rtf){
        int key1 = getKey(mainHashMatrix,rtf);
        boolean found = false;
        if(!usedLvl2){
            if (hashLvl1[key1]==rtf) found = true;
        }else{
            int key2 = getKey(hashMatricesLvl2[key1],rtf);
            if (hashLvl2[key1][key2]==rtf) found = true;
        }
        if (found)
            System.out.print("Found it :)");
        else
            System.out.print("It doesn't exist :(");
    }

//    public static void main(String[] args){
//        int[] a = {12,21,32,43,4,5,3,356,3224,54,455};
//        Method2 m  = new Method2(a);
//    }
}
