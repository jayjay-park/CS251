public class Main_pattern {

    /**
     * Finds a word in a matrix.
     * @param crossword
     * @param target
     * @return an array of length 3.
     */
    public static int[] find(final char[][] crossword, final String target) {
        // i = start index of column
        // j = start index of row
        // k = directions -> from 0 to 7 | 0 : vertical, 1 : tilted right side by one 
        
        // Method =>  search left, right, up, down, and then diagonally
        
        int length = crossword.length;
        KMP kmp = new KMP();

        // ----- 1. for horizontal and vertical searching ----- //
        // 1-1. row to string : i = row, j = column
        for (int i = 0; i < length; i++) {
            String left = "";
            for (int j = length - 1; j >= 0; j--) {
                left += crossword[i][j];
            }
            // compare pattern with string
            int start = kmp.find(left, target);
            // return array [i, j, 6]
            if (start != -1) {
                return new int[] {i, length - start -1, 6};
            }
            // test circular_left by concat
            String concatLeft = left.concat(left);
            int testC = kmp.find(concatLeft, target);
            if (testC != -1) {
                System.out.println("Circular!");
                return new int[] {i, length - testC -1, 6};
            }


            String right = "";
            for (int j = 0; j < length; j++) {
                right += crossword[i][j];
            }
            // compare pattern with string
            int start_r = kmp.find(right, target);
            // return array [i, j, 2]
            if (start_r != -1) {
                return new int[] {i, start_r, 2};
            }
            // test circular_right by concat
            String concatR = right.concat(right);
            int testR = kmp.find(concatR, target);
            if (testR != -1) {
                System.out.println("Circular");
                return new int[] {i, testR, 2};
            }
        }
        // 1-2. column to string
        for (int j = 0; j < length; j++) {
            String up = "";

            for (int i = length - 1; i >= 0; i--) {
                up += crossword[i][j];
            }
            int startU = kmp.find(up, target);
            if (startU != -1) {
                return new int[] {startU, j, 4};
            }

            String concatUp = up.concat(up);
            int testU = kmp.find(concatUp, target);
            if (testU != -1) {
                System.out.println("Circular!");
                return new int[] {length - testU -1, j, 4};
            }


            String down = "";
            for (int i = 0; i < length; i++) {
                down += crossword[i][j];
            }
            // compare pattern with string
            int startD = kmp.find(down, target);
            // return array [i, j, 2]
            if (startD != -1) {
                return new int[] {startD, j, 0};
            }
            // test circular_right by concat
            String concatD = down.concat(down);
            int testD = kmp.find(concatD, target);
            if (testD != -1) {
                System.out.println("Circular");
                if (testD < length) {
                    return new int[] {testD, j, 0};
                }
                else {
                    return new int[] {length - testD -1, j, 0};
                }
                
            }
        }

        // ----- 2. for diagonal searching ----- //
        // SOUTHEAST = 1
        // SE : Upper right triangle
        // [0,0] [1,1] [2,2] ...
        // [0,1] [1,2] [2,3] [3,4] [4,5] [5,6]
        // [0,2] [1,3] [2,4] [3,5] [4,6] 
        // [0,3] [1,4] [2,5] [3,6]
        // [0,4] [1,5] [2,6]
        // [0,5] [1,6]
        // [0,6]

        String SE = "";
        int i = 0;

        for (int j = 0; j < length; j++) {
            int tempJ = j;
            i = 0;
            SE = "";
            while (tempJ < length) {
                SE += crossword[i][tempJ];
                i++;
                tempJ++;
            }

            int startSE = kmp.find(SE, target);
            if (startSE != -1) {
                return new int[] {startSE, j + startSE, 1};
            }

            //i++;
            tempJ = 0;
            while ((i < length) && (tempJ < length)) {
                SE += crossword[i][tempJ];
                i++;
                tempJ++;
            }
            //System.out.println(SE);
            startSE = kmp.find(SE, target);
            if (startSE != -1) {
                System.out.println("Circular_1");
                return new int[] {startSE, length - j -1, 1};
            }
            /*String concatSE = SE.concat(SE);
            int testSE = kmp.find(concatSE, target);
            if (testSE != -1) {
                System.out.println("Circular!");
                return new int[] {startSE, j, 1};
            }*/
            
        }

        // SE : Bottom left triangle
        // [1,0] [2,1] [3,2] [4,3] [5,4] [6,5]
        // [2,0] [3,1] [4,2] [5,3] [6,4]
        // [3,0] [4,1] [5,2] [6,3]
        // [4,0] [5,1] [6,2]
        // [5,0] [6,1]
        // [6,0]

        /*String SEB = "";
        int j = 0;*/

        /*for (i = 1; i < length; i++) {
            int tempI = i;
            j = 0;
            while (tempI < length) {
                SEB += crossword[tempI][j];
                j++;
                tempI++;
            }

            int startSEB = kmp.find(SEB, target);
            if (startSEB != -1) {
                return new int[] {i, startSEB, 1};
            }

            j++;
            tempI = 0;

            while ((j < length) && (tempI < length)) {
                SEB += crossword[tempI][j];
                j++;
                tempI++;
            }
            startSEB = kmp.find(SEB, target);
            if (startSEB != -1) {
                System.out.println("Circular");
                return new int[] {i, startSEB, 1};
            }
            /*String concatSEB = SEB.concat(SEB);
            int testSEB = kmp.find(concatSEB, target);
            if (testSEB != -1) {
                System.out.println("Circular!");
                return new int[] {i, startSEB, 1};
            }*/
        


        // NORTHEAST = 3
        // NEU : Upper left triangle
        // [6,0] [5,1] [4,2] [3,3] [2,4] [1,5] [0,6]
        // [5,0] [4,1] [3,2] [2,3] [1,4] [0,5]

        String NEU = "";
        int j = 0;

        for (i = length-1; i >= 0; i--) {
            int tempI = i;
            j = 0;
            while (tempI >= 0) {
                NEU += crossword[tempI][j];
                j++;
                tempI--;
            }

            int startNEU = kmp.find(NEU, target);
            if (startNEU != -1) {
                return new int[] {i, startNEU, 3};
            }

            j = tempI + 1;
            tempI = length - 1;

            while ((j < length) && (tempI >= 0)) {
                NEU += crossword[tempI][j];
                j++;
                tempI--;
            }
            startNEU = kmp.find(NEU, target);
            if (startNEU != -1) {
                System.out.println("Circular");
                return new int[] {length - i -1, startNEU, 3};
            }
            /*String concatNEU = NEU.concat(NEU);
            int testNEU = kmp.find(concatNEU, target);
            if (testNEU != -1) {
                System.out.println("Circular!");
                return new int[] {length -i -1, testNEU, 3};
            }*/
        }

        // NEB : Bottom right triangle --> ?
        // [6,1] [5,2] [4,3] [3,4] [2,5] [1,6]
        // [6,2] [5,3] [4,4] [3,5] [2,6]


        //String NEB = "";
        //i = length - 1;

        /*for (j = 1; j < length; j++) {
            int tempJ = j;
            i = length-1;
            while (tempJ < length) {
                NEB += crossword[i][tempJ];
                i--;
                tempJ++;
            }

            int startNEB = kmp.find(NEB, target);
            if (startNEB != -1) {
                return new int[] {startNEB, j, 3};
            }

            i++;
            tempJ = 0;
            while ((i < length) && (tempJ < length)) {
                SE += crossword[i][tempJ];
                i++;
                tempJ++;
            }
            startSE = kmp.find(SE, target);
            if (startSE != -1) {
                System.out.println("Circular");
                return new int[] {startSE, j, 1};
            }
            /*String concatNEB = NEB.concat(NEB);
            int testNEB = kmp.find(concatNEB, target);
            if (testNEB != -1) {
                System.out.println("Circular!");
                return new int[] {length - testNEB -1, j + 1, 1};
            }*/




        // NORTHWEST = 5
        // NWU : Upper left triangle
        // [6,6] [5,5] [4,4] [3,3] [2,2] [1,1] [0,0]
        // [5,6] [4,5] [3,4] [2,3] [1,2] [0,1]

        String NWU = "";
        j = 0;

        for (i = length-1; i >= 0; i--) {
            int tempI = i;
            j = length-1;
            NWU = "";
            while ((tempI >= 0) && (j >= 0)) {
                //System.out.printf("i: %d j: %d\n", tempI, j);
                NWU += crossword[tempI][j];
                j--;
                tempI--;
            }

            int startNWU = kmp.find(NWU, target);
            if (startNWU != -1) {
                return new int[] {length - i - 1, length - startNWU -1, 5};
            }

            //j;
            tempI = length-1;

            while ((j >= 0) && (tempI >= 0)) {
                NWU += crossword[tempI][j];
                //System.out.printf("i*: %d j*: %d\n", tempI, j);
                j--;
                tempI--;
            }
            //System.out.println(NWU);
            startNWU = kmp.find(NWU, target);
            if (startNWU != -1) {
                System.out.println("Circular");
                return new int[] {length - i - 1, length -startNWU -1, 5};
            }
        }


        // SOUTHWEST = 7
        String SW = "";
        i = 0;

        for (j = length-1; j >= 0; j--) {
            int tempJ = j;
            i = 0;
            SW = "";
            while ((i < length) && (tempJ >= 0)) {
                //System.out.printf("i: %d j: %d\n", tempI, j);
                SW += crossword[i][tempJ];
                i++;
                tempJ--;
            }

            int startSW = kmp.find(SW, target);
            if (startSW != -1) {
                return new int[] {startSW, length - j - 1, 7};
            }

            //i++;
            tempJ = length-1;

            while ((i < length) && (tempJ >= 0)) {
                SW += crossword[i][tempJ];
                //System.out.printf("i*: %d j*: %d\n", tempI, j);
                i++;
                tempJ--;
            }
            System.out.println(SW);
            startSW = kmp.find(SW, target);
            if (startSW != -1) {
                System.out.println("Circular");
                return new int[] {startSW, length - j - 1, 7};
            }
        }

        return new int[] {-1,-1,-1};
    }
}
