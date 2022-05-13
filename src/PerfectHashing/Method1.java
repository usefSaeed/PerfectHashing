package PerfectHashing;

import java.util.*;

public class Method1 {
    private final int[] input;
    private final int inputSize;
    private final int hashTableSize;
    private int[] hashMatrix;
    private final int[] finalHashTable;
    private final boolean[] filled;
    private final int hashTableBits;
    private int collisionCount;

    public Method1(int[] input) {
        this.input = input;
        this.inputSize = input.length;
        this.hashTableSize = input.length * input.length;
        this.hashTableBits = (int) Math.ceil(Math.log(this.hashTableSize)/Math.log(2));
        this.hashMatrix = generateHashMatrix();
        this.finalHashTable = new int[hashTableSize];
        this.filled = new boolean[hashTableSize];
        this.collisionCount = 0;
        this.generateHashTable();
    }

    int[] generateHashMatrix(){
        int[] res = new int[hashTableBits];
        Random rand = new Random();
        for (int  i = 0; i < hashTableBits; i++)
            res[i] = rand.nextInt(Integer.MAX_VALUE);
        return res;
    }

    int getIndex(int key){
        int index = 0;
        for (int i = 0; i <this.hashTableBits; i++){
            index <<= 1;
            index |= parity(key & this.hashMatrix[i]);
        }
        return index % this.hashTableSize;
    }

    int parity(int p){
        int isOdd=0;
        while (p != 0){
            isOdd ^= 1;
            p &= p-1;
        }
        return isOdd;
    }

    void generateHashTable(){
        int i = 0;
        while (i < this.inputSize){
            boolean collision = insert(this.input[i]);
            i++;
            if (collision){
                Arrays.fill(filled, false);
                Arrays.fill(finalHashTable, 0);
                this.hashMatrix = generateHashMatrix();
                i = 0;
                this.collisionCount++;
            }
        }
    }


    boolean insert(int key){
        int index = getIndex(key);
        if (this.filled[index]){
            return true;
        }
        else{
            this.finalHashTable[index] = key;
            this.filled[index] = true;
        }
        return false;
    }

    public boolean search(int key){
        int index = getIndex(key);
        return this.finalHashTable[index] == key;
    }



    public int[] getFinalHashTable() {
        return finalHashTable;
    }

    public int[] getHashMatrix() {
        return hashMatrix;
    }

    public int getCollisionCount() {   return collisionCount;}
}
