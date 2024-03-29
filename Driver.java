import java.util.Random;

public class Driver {
    public static void main(String[] args){
        int[] a = createArray(10000);
        PerfectHashing.Method1 m1  = new PerfectHashing.Method1(a);
        for (int i = 0; i < a.length; i++){
            System.out.println(m1.search(a[i]));
        }
        System.out.println(m1.search(100));
        PerfectHashing.Method2 m2  = new PerfectHashing.Method2(a);
        for (int i = 0; i < a.length; i++){
            m2.search(a[i]);
            System.out.println();
        }
        /**
         * MONO DO YOUR THING (ANALYSIS)
         */
    }

    public static int[] createArray(int n){
        Random rd = new Random();
        int[] arr = new int[n];
        int upperBound = Integer.MAX_VALUE;
        int lowerBound = Integer.MIN_VALUE;
        for(int i = 0; i < arr.length; i++){
            arr[i] = rd.nextInt(lowerBound, upperBound);
        }
        return arr;
    }
}
/**
 * Important Notes:
 * 1. Turned out that collision may happen in perfect hashing method2 for some reason
 *      1.1. Realised that though testing
 *      1.2. Used method1 code (still incomplete) in method2 perfectRehash() after figuring out all collisions
 *      1.3. Once the mainLogic() in method1 works perfectly, method2 also works perfectly
 *          1.3.1. finalHashTable includes all elements passed to method1 as well as zeros in null places
 *          1.3.2. hashMatrix includes matrix used to get the element's keys
 * 2. Method2 is fully tested (not including the method1 part for sure)
 * 3. If no collision takes place in method2, method1 is not used. So, testcase runs successfully
 * 4. Don't forget the search() in method1 because I was about to, lol
 * 5. Running the same testcase several times will produce different outcome due to randomization
 *      5.1. may make debugging certain cases really difficult as some runs may work and others not
 * 6. I'll sleep at 4-5 am and wake up at 12 pm, be free at 1-6 pm
 * 7. Yeah. I also added some methods from method2 you will need in method1, some getters and attributes that are also needed
 * GOODLUCK BRO
 */