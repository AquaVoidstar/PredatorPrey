// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Mason Stoecker
package base;

import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.math3.random.MersenneTwister;

/**
 * @author aquastar
 *
 * @version 2019.12.09
 */
public class ProjectRunner {
    private static String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args)
        throws PredetorPreyDataException,
        InterruptedException,
        IOException {
        String name[] = { "Lace", "Checkers", "Paint" };
        int MAX_TIME = 800000;

        LatticeCalc game;
        int size = 100;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(
            "yyyy/MM/dd HH:mm:ss");
        for (int o = 0; o < name.length; o++) {
            for (int j = 3; j < 7; j++) {
                int speciesNum = j;
                game = new LatticeCalc(size, speciesNum, MAX_TIME, name[o]);
                game.runSim();

            }
        }
    }
}
