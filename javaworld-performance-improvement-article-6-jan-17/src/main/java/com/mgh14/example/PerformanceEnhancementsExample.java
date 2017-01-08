package com.mgh14.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Program for testing a number of performance enhancement examples given in
 * a JavaWorld article here:
 *
 * http://www.javaworld.com/article/2078623/core-java/jvm-performance-optimization-part-1-a-jvm-technology-primer.html
 *
 * Note: Execute this program with the following JVM opts to analyze JVM behavior
 * (the quotation characters are NOT part of the string):
 *
 *      "-XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps
 *      -Xloggc:gcOut.txt -XX:+PrintCompilation"
 */
public class PerformanceEnhancementsExample {

    private static final int NUM_INTEGERS_TO_CREATE = 1000000;
    private static final int NUM_DATE_OBJECTS_TO_CREATE = 100000000;

    // We'll use a data structure to store the print statements so that
    // the expensive print statements don't interfere with the time
    // measurements.
    private static final List<String> printStatements = new ArrayList<>(100);

    public static void main(String... args) {
        printStatements.add("\nBeginning performance enhancement tests...");

        runIntegerObjectArrayCreationTest();
        runPrimitiveIntegerArrayCreationTest();

        runDateCreationNewObjectEachTest();
        runDateCreationOneObjectTest();

        printStatements.add("\nPerformance enhancement tests finished.");

        for (String statement : printStatements) {
            System.out.println(statement);
        }
    }

    private static void runIntegerObjectArrayCreationTest() {
        String testName = "Creating an array of " + NUM_INTEGERS_TO_CREATE +
                " Integer objects";
        long startTime = startTestWithStartTime(testName);

        Integer[] arrayOfIntegerObjs = new Integer[NUM_INTEGERS_TO_CREATE];
        for (int i = 0; i < NUM_INTEGERS_TO_CREATE; i++) {
            arrayOfIntegerObjs[i] = new Integer(i);
        }

        printTestResults(testName, startTime);
    }

    private static void runPrimitiveIntegerArrayCreationTest() {
        String testName = "Creating an array of " + NUM_INTEGERS_TO_CREATE +
                " int primitives";
        long startTime = startTestWithStartTime(testName);

        int[] arrayOfIntegerPrimitives = new int[NUM_INTEGERS_TO_CREATE];
        for (int i = 0; i < NUM_INTEGERS_TO_CREATE; i++) {
            arrayOfIntegerPrimitives[i] = i;
        }

        printTestResults(testName, startTime);
    }

    private static void runDateCreationNewObjectEachTest() {
        String testName = "Displaying new date object for each of " + NUM_DATE_OBJECTS_TO_CREATE +
                " iterations";
        long startTime = startTestWithStartTime(testName);

        for (int i = 0; i < NUM_DATE_OBJECTS_TO_CREATE; i++) {
            Date newDate = new Date(System.nanoTime());
            newDate.getTime();
        }

        printTestResults(testName, startTime);
    }

    private static void runDateCreationOneObjectTest() {
        String testName = "Displaying one re-used date object for each of " + NUM_DATE_OBJECTS_TO_CREATE +
                " iterations";
        long startTime = startTestWithStartTime(testName);

        Date dateObj = new Date();
        for (int i = 0; i < NUM_DATE_OBJECTS_TO_CREATE; i++) {
            dateObj.setTime(System.nanoTime());
            dateObj.getTime();
        }

        printTestResults(testName, startTime);
    }

    private static long startTestWithStartTime(String testName) {
        printStatements.add("\n================");
        printStatements.add("\nNew Test: " + testName);

        long startTime = System.nanoTime();
        printStatements.add("Start time: " + startTime + "\n");

        return startTime;
    }

    private static void printTestResults(String testName, long startTime) {
        long timeTaken = System.nanoTime() - startTime;
        printStatements.add("Time taken for test [" + testName + "]: " + timeTaken +
                " nanoseconds");
        printStatements.add("Time in seconds: " + getSecondsFromNanoSeconds(timeTaken) +
                " seconds");
    }

    private static double getSecondsFromNanoSeconds(long time) {
        return time * Math.pow(10, -9);
    }
}
