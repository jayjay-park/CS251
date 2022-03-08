/**
 * CS 251: Data Structures and Algorithms
 * Project 3: Part 1
 *
 * TODO: implement gritSort.
 *
 * @author TODO: add your name here
 * @username TODO: add your Purdue username here
 * @sources TODO: list your sources here
 *
 * Use the algorithms written in sorting to implement this class.
 *
 */
import java.util.ArrayList;
import java.util.HashMap;

public class GritSort<Item extends Comparable<Item>> {

    /**
     *
     * Default Constructor
     *
     */
    public GritSort() {
    }

    /**
     *
     * Implement Grit Sort as defined in the Handout.
     * for example:
     * list = {3,5,6,8,10,2,4,5,6,1,2,4,5}
     * return value = {1,2,2,3,4,4,5,5,5,6,6,8,10}
     *
     *
     * @param list
     * @return sorted list
     *
     */
    public ArrayList<Item> grit(ArrayList<Item> list) {
        /*
            TODO: part 1
            TODO: STEP 1: implement and call makeChunks on list to divide the list into the sorted chunks.
            TODO: STEP 1.5: make the required number of buckets(can differ from implementation to implementation) and
            TODO            put each chunk into a bucket of proper size.
            TODO: STEP 2: implement and call mergeChunk to merge the chunks in a bucket. do this for every bucket
            TODO: STEP 3: merge the buckets and return the elements of the merged buckets as a list
         */

         ArrayList<ArrayList<Item>> chunks = new ArrayList<ArrayList<Item>>();
         ArrayList<Item> merged = new ArrayList<Item>();

         chunks = makeChunks(list);
         merged = mergeChunk(chunks);
         

        return merged;
    }

    /**
     *
     * This function takes in a list and returns an ArrayList of ArrayList.
     * Each chunk found is stored in an array list and then all the chunks are in turn stored in an array list which is
     * then returned.
     *
     * for example
     * list = {3,5,6,8,10,2,4,5,6,1,2,4,5}
     *
     * return value = {
     *                     {3,5,6,8,10},
     *                     {2,4,5,6},
     *                     {1,2,4,5}
     *                }
     *
     * @param list
     * @return array list of chunks(sorted sub-lists in the original input list)
     *
     */
    public ArrayList<ArrayList<Item>> makeChunks(ArrayList<Item> list) {
        // TODO: part 1
        ArrayList<ArrayList<Item>> chunk = new ArrayList<ArrayList<Item>>();
        ArrayList<Item> temp;
        Sorting<Item> s = new Sorting<Item>();
        int start = 0;

        for (int i = 0; i < list.size() - 1; i++) {
            //System.out.println(list.get(i));
            if (list.get(i).compareTo(list.get(i+1)) > 0 || i == list.size() - 1) {
                temp = new ArrayList<Item>(list.subList(start, i + 1));
                chunk.add(temp);
                start = i + 1;
            }
                     
        }
        if (start <= list.size()) {
            temp = new ArrayList<Item>(list.subList(start, list.size()));
            chunk.add(temp);
        }

        return chunk;
    }



    /**
    *
    * This function takes in an ArrayList of ArrayList and returns an HashMap<Integer, ArrayList<ArrayList<Item>>> 
    * Chunks of the same size are stored in the same bucket. 
    * Each chunk of the size say S is stored in a bucket which corresponds to that size S.
    * Thus, for the returned HashMap, the key is the chunk size, and the corresponding value is a list of chunks of that size.
    * for example
    * chunks = {
    *              {3,5,6,8,10},
    *              {2,4,5,6},
    *              {1,2,4,5}
    *          }
    *
    * return value = {
    * 					4: {{2,4,5,6},{1,2,4,5}},
    * 					5: {{3,5,6,8,10}}
    * 				  }
    * i.e., the bucket for chunks of size 4 will have {{2,4,5,6},{1,2,4,5}} and
    * bucket for chunks of size 5 will have {{3,5,6,8,10}}
    *
    * @param chunks
    * @return HashMap, where key is the chunk size and value is a list of chunks of that size
    * (sorted sub-lists in the original input list)
    *
    */
    public HashMap<Integer, ArrayList<ArrayList<Item>>> makeBuckets(ArrayList<ArrayList<Item>> chunks) {
    	
        HashMap<Integer, ArrayList<ArrayList<Item>>> buckets = new HashMap<Integer, ArrayList<ArrayList<Item>>>();
    	// make buckets is a helper function. 
        int key = 0;
    	// find out chunks size
        for (int i = 0; i < chunks.size(); i++) {
            ArrayList<ArrayList<Item>> sublist = new ArrayList<ArrayList<Item>>();
            key = chunks.get(i).size();
            
            if (buckets.keySet().contains(key)) {
                sublist = buckets.get(key);
                sublist.add(chunks.get(i));
            }
            else {
                sublist.add(chunks.get(i));
            }
            buckets.put(key, sublist);
    
        }
    	
	   	return buckets;
	}




    /**
     *
     * This function takes in a bucket(ArrayList of ArrayList) and returns an ArrayList which has the chunks in
     * the bucket merged.
     *
     * for example
     * bucket = {
     *          {2,4,5,6},
     *          {1,2,4,5}
     *         }
     *
     * return value = {1,2,2,4,4,5,5,6}
     *
     *
     * @param bucket
     * @return merged and sorted list
     *
     */
    public ArrayList<Item> mergeChunk(ArrayList<ArrayList<Item>> bucket) {
        // TODO: part 1
        int len = bucket.size();
        ArrayList<Item> merged = new ArrayList<Item>();
        ArrayList<Item> sorted = new ArrayList<Item>();

        for (int i = 0; i < len; i++) {
            merged.addAll(bucket.get(i));
        }

        Sorting s = new Sorting();

        sorted = s.mergeSort(merged);

        return sorted;
    }




    /**
     *
     * This is the main function to run the gritSort class to help with debugging.
     *
     * @param args
     */
    public static void main(String[] args) {
        Sorting<Integer> s = new Sorting<>();
        ArrayList<Integer> A = new ArrayList<Integer>();
        Integer[] K = {3,5,6,8,10,2,4,5,6,1,2,4,5};

        GritSort<Integer> g = new GritSort<Integer>();

        for (Integer k : K) {
            A.add(k);
        }

        s.print(A);

        ArrayList<ArrayList<Integer>> B = new ArrayList<ArrayList<Integer>>();
        B = g.makeChunks(A);
        for (int i = 0; i < B.size(); i++) {
            s.print(B.get(i));
        }

    }

}
