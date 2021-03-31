package assignment02;

import java.util.LinkedList;
import java.util.Random;

public class toolpathOptimizer {
    //------------------------------------------------------------------------------------------------------------------
    //
    // this class can take a list of points as inputs, with the first defined as the "home" position, and can determine
    // the shortest path that touches each of the target spots at least once and returns home. This is akin to the
    // travelling salesman problem, but slightly altered for use in toolpath generation for machining operations
    //
    //------------------------------------------------------------------------------------------------------------------
    //                                                 ATTRIBUTES
    //------------------------------------------------------------------------------------------------------------------
    private LinkedList<target> targetList = new LinkedList<>();
    //------------------------------------------------------------------------------------------------------------------
    //                                             CONSTRUCTOR METHODS
    //------------------------------------------------------------------------------------------------------------------
    toolpathOptimizer() {
        // null constructor
    }
    toolpathOptimizer(int quantity, int xMax, int yMax) {
        // constructor that takes canvas size and quantity as inputs
        generateRandomTargets(quantity, xMax, yMax);
    }
    toolpathOptimizer(LinkedList<target> inputList) {
        // constructor that takes a list of targets as input
        this.targetList = inputList;
    }
    //------------------------------------------------------------------------------------------------------------------
    //                                                PUBLIC METHODS
    //------------------------------------------------------------------------------------------------------------------
    public void generateRandomTargets(int quantity, int xMax, int yMax) {
        // this method takes an integer as an input and generates a number of targets within the specified canvas
        Random rand = new Random();
        for (int i = 0; i < quantity; i++) {
            if (i == 0) {
                // initial target is always the "Home Point" of (0,0). This remains unchanged throughout the program
                targetList.add(new target(0,0));
            } else {
                // all other targets are randomly generated
                int randomX = rand.nextInt(xMax+1);
                int randomY = rand.nextInt(yMax+1);
                targetList.add(new target(randomX, randomY));
            }
        }
    }
    public void addTarget(target inputTarget) {
        // this method takes a target object as input and adds it to the list
        targetList.add(inputTarget);
    }
    public LinkedList<target> getTargetList() {
        // returns the target list as a linked list object
        return this.targetList;
    }
    public double getPathlength() {
        // returns the path length of the entire target list
        return this.pathLength();
    }
    public double optimize(double startingTempInput, double decrementInput, int maxIterationsInput) {
        // method that sorts the target list in order of traversal in order to minimize the total length of travel over
        // the course of the tool path process. The method uses a simulated annealing search algorithm to achieve a near
        // optimal result. The method

        // set pertinent variables and parameters
        int maxIterations;
        if (maxIterationsInput == 0) {
            maxIterations = targetList.size()*10;
        } else {
            maxIterations = maxIterationsInput;
        }
        int iteration = 0;
        int successfulMoves = 0;
        double currentTemp = startingTempInput;

        boolean continueSearch = true;

        while (continueSearch) {
            System.out.println("\n------------- LOOP INSTANCE START --------------------");
            System.out.println("Entered the loop, on iteration: " + iteration);
            System.out.println("Temperature: " + currentTemp);
            iteration++;
            // compute the current path length
            double currentLength = pathLength();
            System.out.println("Path Length Before Swap: " + currentLength);
            // swap two targets in the list
            int[] pairSwap = randomIndices(targetList.size());
            System.out.println("Target List Size: " + targetList.size());
            System.out.println("Pair Swap Indices: " + pairSwap[0] + " and " + pairSwap[1]);
            swapTwoRandomTargets(pairSwap[0], pairSwap[1]);
            // compute the new path length
            double nextLength = pathLength();
            System.out.println("Path Length After Swap: " + nextLength);
            // compute delta
            double delta = currentLength - nextLength;
            System.out.println("Delta is: " + delta);
            // start optimization checks
            if (delta > 0) {
                // keep the move and take note
                successfulMoves++;
                System.out.println("Successful Move, delta: " + delta);
                System.out.println("Successful Moves: " + successfulMoves);
            } else if (delta <= 0) {
                Random rand = new Random();
                double randValue = rand.nextInt(100)/100.0;
                System.out.println("Random Value: " + randValue);
                double tempValue = Math.exp((-delta/currentTemp));
                System.out.println("Temp Value: " + tempValue);
                if (randValue > tempValue) {
                    // keep the move, but not count it as "successful"
                    System.out.println("Chose to keep the bad move");
                } else {
                    // reject the move and undo it
                    swapTwoRandomTargets(pairSwap[0], pairSwap[1]);
                    System.out.println("Did not keep bad move.");
                }
            }
            // change the temperature on a set pattern (every 10 successful moves) and reset iteration count
            if (iteration == maxIterations) {
                System.out.println("Successful Moves: " + successfulMoves);
                currentTemp = currentTemp * decrementInput;
                System.out.println("Iteration before reset: " + iteration);
                iteration = 0;
                if (successfulMoves == 0) {
                    continueSearch = false;
                }
                successfulMoves = 0;
            }
            System.out.println("------------- LOOP INSTANCE END --------------------\n");
        }
        return currentTemp;
    }
    public void reset(){
        // this method clears the target list back to its original state
        targetList.clear();
    }
    //------------------------------------------------------------------------------------------------------------------
    //                                                PRIVATE METHODS
    //------------------------------------------------------------------------------------------------------------------
    private double pathLength() {
        // this method computes the total path length from the first cell in the target list to the last cell and back
        // to the original cell using the Euclidean distance between each target point
        double totalLength = 0.0;
        for (int i = 0; i < targetList.size(); i++) {
            if (i == (targetList.size()-1)) {
                target first = targetList.getLast();
                target second = targetList.getFirst();
                double distance = Math.sqrt((Math.pow((first.getXpos()-second.getXpos()),2) +
                        Math.pow((first.getYpos()-second.getYpos()),2)));
                totalLength = totalLength + distance;
            } else {
                target first = targetList.get(i);
                target second = targetList.get(i+1);
                double distance = Math.sqrt((Math.pow((first.getXpos()-second.getXpos()),2) +
                        Math.pow((first.getYpos()-second.getYpos()),2)));
                totalLength = totalLength + distance;
            }
        }
        return totalLength;
    }
    private int[] randomIndices(int limit) {
        // returns an array of two indices that will be used to swap
        int[] outputArray = {-1,-1};
        Random rand = new Random();
        // the random outputs are offset by 1 in order to prevent an index of 0
        outputArray[0] = rand.nextInt(limit - 1) + 1;
        outputArray[1] = rand.nextInt(limit - 1) + 1;
        return outputArray;
    }
    private void swapTwoRandomTargets(int indexOne, int indexTwo) {
        // this method swaps the location of two targets within the target list.
        target workingTarget = targetList.get(indexOne);
        targetList.set(indexOne,targetList.get(indexTwo));
        targetList.set(indexTwo,workingTarget);
    }
    //------------------------------------------------------------------------------------------------------------------
    //                                              INNER CLASS(ES)
    //------------------------------------------------------------------------------------------------------------------
    public class target {
        // attributes
        private int xPos;
        private int yPos;

        // constructor
        target(int x, int y) {
            this.setXpos(x);
            this.setYpos(y);
        }
        // public methods
        public void setXpos(int x) {
            // sets the X coordinate
            this.xPos = x;
        }
        public int getXpos() {
            // returns the X coordinate
            return this.xPos;
        }
        public void setYpos(int y) {
            // sets the Y coordinate
            this.yPos = y;
        }
        public int getYpos() {
            // returns the Y coordinate
            return this.yPos;
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    //                                     END OF CLASS toolpathOptimizer.java
    //------------------------------------------------------------------------------------------------------------------
}
