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
    private final int hashTableBits;
    private int collisionsCount;

    public Method2(int[] input) {
        this.input = input;
        this.size = input.length;
        this.hashLvl1 = new int[this.size];
        this.hashTableBits = (int) Math.ceil(Math.log(size)/Math.log(2));
        this.mainHashMatrix = generateHashMatrix(this.hashTableBits);
        this.hashMatricesLvl2 = new int[this.size][];
        this.hashLvl2 = new int[this.size][];
        this.usedLvl2 = false;
        this.collisionsCount = 0;
        this.hashPerfectionist();
    }

    int[] generateHashMatrix(int size){
        int[] res = new int[size];
        Random rand = new Random();
        for (int  i=0; i<size;i++)
            res[i] = rand.nextInt(Integer.MAX_VALUE);
        return res;
    }

    int getIndexM1(int[] h, int x){
        int index=0;
        int bits = h.length;
        for (int i = 0; i < bits; i++){
            index=index<<1;
            index |= parity(x & h[i]);
        }
        return index % size;
    }

    int getIndexM2(int[] h, int x,int inputSizeM1){
        int index=0;
        int bits = h.length;
        for (int i = 0; i < bits; i++){
            index=index<<1;
            index |= parity(x & h[i]);
        }
        return index % inputSizeM1;
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
            int index = getIndexM1(mainHashMatrix,input[i]);
            if (beenHereBefore[index]) return false;
            beenHereBefore[index] = true;
            hashLvl1[index] = input[i];
        }
        return true;
    }

    void perfectRehash(){
        usedLvl2 = true;
        hashLvl1 = new int[size];
        boolean[] beenHereBefore = new boolean[size];
        ArrayList[] collisionCont = new ArrayList[size];
        for (int i=0;i<size;i++){
            int index = getIndexM1(mainHashMatrix,input[i]);
            hashLvl1[index]++;
            collisionsCount++;
            if (!beenHereBefore[index])
                collisionCont[index] = new ArrayList<Integer>();
            beenHereBefore[index] = true;
            collisionCont[index].add(input[i]);
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
            collisionsCount += m.getCollisionCount();
        }
    }


    int[] convertToArray(ArrayList<Integer> a){
        int[] b = new int[a.size()];
        int i=0;
        for (int elem : a) b[i++] = elem;
        return b;
    }

    public void search(int rtf){
        int index1 = getIndexM1(mainHashMatrix,rtf);
        boolean found = false;
        if(!usedLvl2){
            if (hashLvl1[index1]==rtf) found = true;
        }else{
            int inputSizeM2 = hashLvl1[index1]*hashLvl1[index1];
            int index2 = hashLvl1[index1]!=1 ? getIndexM2(hashMatricesLvl2[index1],rtf,inputSizeM2) : 0;
            if (hashLvl2[index1][index2]==rtf) found = true;
        }
        if (found)
            System.out.print("Found it :)");
        else
            System.out.print("It doesn't exist :(");
    }

    public int[] getHashLvl1() {
        return hashLvl1;
    }

    //    public static void main(String[] args){
//        int[] a = {12,21,32,43,4,5,3,356,3224,54,455};
//        Method2 m  = new Method2(a);
//    }
}
