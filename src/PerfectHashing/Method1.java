package PerfectHashing;

import java.util.Random;

public class Method1 {
    private final int[] input;
    private final int size;
    private int[] hashMatrix;
    private int[] finalHashTable;

    public Method1(int[] input) {
        this.input = input;
        this.size = input.length;
        this.hashMatrix = generateHashMatrix(this.size*this.size);
        this.finalHashTable = new int[this.size*this.size];
        this.mainLogic();
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

    void mainLogic(){
        /**
         * MONO DO YOUR THING
         */
    }



    public int[] getFinalHashTable() {
        return finalHashTable;
    }

    public int[] getHashMatrix() {
        return hashMatrix;
    }
}
