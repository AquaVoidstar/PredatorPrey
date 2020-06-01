// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Mason Stoecker
package base;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.math3.random.MersenneTwister;
import java.lang.reflect.Method;

/**
 * @author aquastar
 *
 * @version 2019.12.09
 */
public class LatticeCalc {
    private int MAX_TIME;
    private double D = .6;
    private double mu = .2;
    private double lambda = .5;
    private double sigma = .5;
    private int sizePriv;
    private int numOfCreatures[];
    private String mode;
    private Cell[][] playGround;
    String[] alphabet = "abcdefghijklmnopqrstuvwxyz".split("");
    String alpha = "abcdefghijklmnopqrstuvwxyz";
    private String targets[] = { "N", "E", "W", "S" };
    private double[][] density;
    private String[] playGroundOverTime;
    private PrintStream fileDens;
    /**
     * Constructor of the major simulation class, this intakes:
     * @param size: number of points on the lattice is if 10 the number of points will be 100
     * @param speciesNum: number of species, interactions will also be assumed to be cyclical unless otherwise scripted
     * @param timeStep: length of the simulation
     * @param modeIn: Specifies the type of lattice configuration
     * 
     * This constructor will also populate the lattice with the species using the Mersene Twister.
     */
    LatticeCalc(
        int size,
        int speciesNum,
        int timeStep,
        String modeIn)
        throws PredetorPreyDataException,
        InterruptedException,
        IOException {
//        Sets the output to a .txt file
        fileDens = new PrintStream("./Ground" + mode + speciesNum + size
            + ".txt");
        System.setOut(fileDens);
//        ensures that there is at least 3 species, this can be set to have 2 species as well
        if (size > 4) {
            this.playGround = new Cell[size][size];
        }
        else {
            this.playGround = new Cell[4][4];
        }
// This is a RNG 
        MersenneTwister operation = new MersenneTwister();
        
        mode = modeIn;
        sizePriv = size;
        
        if (speciesNum >= 3) {
            numOfCreatures = new int[speciesNum + 1];
        }
        else {

            numOfCreatures = new int[3];
        }
        if (timeStep < 0) {
            MAX_TIME = 500;
        }
        else {
            MAX_TIME = timeStep;
        }

        density = new double[numOfCreatures.length][MAX_TIME + 1];

        String eats;
        String prey;
//        This populates the lattice with the creatures
        for (int cycleY = 0; cycleY < sizePriv; cycleY++) {
            for (int cycleX = 0; cycleX < sizePriv; cycleX++) {

                int seed = (((operation.nextInt() % numOfCreatures.length)
                    + numOfCreatures.length) % numOfCreatures.length);
                if (

                alphabet[seed].equals(alphabet[numOfCreatures.length - 1])) {
                    eats = "";
                }
                else {
                    eats =

                        alphabet[(((seed + 1) % (numOfCreatures.length - 1))
                            + numOfCreatures.length - 1)
                            % (numOfCreatures.length - 1)];
                }

                if (

                alphabet[seed].equals(alphabet[numOfCreatures.length - 1])) {
                    prey = "";
                }
                else {
                    prey =

                        alphabet[(((seed - 1) % (numOfCreatures.length - 1))
                            + numOfCreatures.length - 1)
                            % (numOfCreatures.length - 1)];
                }

                playGround[cycleX][cycleY] = new Cell(alphabet[seed], eats,
                    prey);

                numOfCreatures[seed]++;
                density[seed][0] = density[seed][0] + 1;
            }

        }

        System.out.print(this.outputGround());

        
//        This allows for modification of the cells to modify the base parameters. \
//        The parameter being modified is the second input while the first is the amount being modified by
        if (mode.equals("Lace")) {
            try {
                this.terraLace(.7, "Lambda");
            }
            catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            catch (SecurityException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e) {
             e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if (mode.equals("Checkers")) {
            try {
                this.terraLace(.7, "Lambda");
            }
            catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            catch (SecurityException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if (mode.equals("Paint")) {
            try {
                this.terraLace(.7, "Lambda");
            }
            catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            catch (SecurityException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Runs the program for the specified iterations. Randomly selects a location, then an action.
     * It checks to see if that action can be performed and then it decided which adjacent location it will 
     * Interact with. It finally checks to see if the action was successful and updates the lattice accordingly
     */
    public void runSim() throws InterruptedException, IOException {
        MersenneTwister operation = new MersenneTwister();
        double randomroll;
        int x;
        int y;
        String targetTile;
        System.setOut(this.fileDens);
        for (int time = 1; time < MAX_TIME; time++) {
            randomroll = operation.nextDouble();
            x = (((operation.nextInt() % this.playGround.length)
                + this.playGround.length) % this.playGround.length);
            y = (((operation.nextInt() % this.playGround.length)
                + this.playGround.length) % this.playGround.length);
            targetTile = targets[(((operation.nextInt() % targets.length)
                + targets.length) % targets.length)];
            if (randomroll < .25) {
                moveTo(x, y, targetTile);
            }
            else if (randomroll >= .25 && randomroll < .5) {

                eats(x, y, targetTile);
            }
            else if (randomroll >= .5 && randomroll < .75) {
                dies(x, y);
            }
            else {
                reproduces(x, y, targetTile);
            }

            for (int Y = 0; Y < sizePriv; Y++) {
                for (int X = 0; X < sizePriv; X++) {
                    density[alpha.indexOf(playGround[X][Y]
                        .getCreatureType())][time] = density[alpha.indexOf(
                            playGround[X][Y].getCreatureType())][time] + 1;

                }
            }

            if (this.density[0][time] == 0.0) {
                for (; time < MAX_TIME; time++) {
                    density[1][time] = this.sizePriv * this.sizePriv;
                }
                break;
            }
            System.out.print(this.outputGround());

        }

    }

    /**
     * The eats action that will select an adjacent location to interact with.
     * @param x and y are the original locations values
     * @param move: The direction of where the original location will interact
     */
    public void eats(int x_Coord, int y_Coord, String move) {
        int new_Coord;
        MersenneTwister operation = new MersenneTwister();
        double normalizedRand = operation.nextDouble();
        switch (move) {
            case "E":
//                Essentially, it checks if the species at the base location can eat the species at the east tile
//                and vice versa. It also rolls a random number to see if the action is successful
                new_Coord = ((((x_Coord + 1) % this.playGround.length)
                    + this.playGround.length) % this.playGround.length);
                if (this.playGround[new_Coord][y_Coord].getEatenBy(
                    this.playGround[x_Coord][y_Coord].getCreatureType())
                    && this.playGround[x_Coord][y_Coord].getEats(
                        this.playGround[new_Coord][y_Coord].getCreatureType())
                    && this.lambda + this.playGround[x_Coord][y_Coord]
                        .getLambdaMod() > normalizedRand) {

                    this.playGround[new_Coord][y_Coord] = new Cell(String
                        .valueOf(this.alphabet[this.numOfCreatures.length - 1]),
                        "", "");
                }
                break;
            case "N":
                new_Coord = ((((y_Coord + 1) % this.playGround.length)
                    + this.playGround.length) % this.playGround.length);
                if (this.playGround[x_Coord][new_Coord].getEatenBy(
                    this.playGround[x_Coord][y_Coord].getCreatureType())
                    && this.playGround[x_Coord][y_Coord].getEats(
                        this.playGround[x_Coord][new_Coord].getCreatureType())
                    && this.lambda + this.playGround[x_Coord][y_Coord]
                        .getLambdaMod() > normalizedRand) {

                    this.playGround[x_Coord][new_Coord] = new Cell(String
                        .valueOf(this.alphabet[this.numOfCreatures.length - 1]),
                        "", "");
                }
                break;
            case "W":
                new_Coord = ((((x_Coord - 1) % this.playGround.length)
                    + this.playGround.length) % this.playGround.length);
                if (this.playGround[new_Coord][y_Coord].getEatenBy(
                    this.playGround[x_Coord][y_Coord].getCreatureType())
                    && this.playGround[x_Coord][y_Coord].getEats(
                        this.playGround[new_Coord][y_Coord].getCreatureType())
                    && this.lambda + this.playGround[x_Coord][y_Coord]
                        .getLambdaMod() > normalizedRand) {

                    this.playGround[new_Coord][y_Coord] = new Cell(String
                        .valueOf(this.alphabet[this.numOfCreatures.length - 1]),
                        "", "");
                }
                break;
            case "S":
                new_Coord = ((((y_Coord - 1) % this.playGround.length)
                    + this.playGround.length) % this.playGround.length);
                if (this.playGround[x_Coord][new_Coord].getEatenBy(
                    this.playGround[x_Coord][y_Coord].getCreatureType())
                    && this.playGround[x_Coord][y_Coord].getEats(
                        this.playGround[x_Coord][new_Coord].getCreatureType())
                    && this.lambda + this.playGround[x_Coord][y_Coord]
                        .getLambdaMod() > normalizedRand) {

                    this.playGround[x_Coord][new_Coord] = new Cell(String
                        .valueOf(this.alphabet[this.numOfCreatures.length - 1]),
                        "", "");
                }
                break;

        }
    }

    /**
     * The move action that will select an adjacent location to interact with.
     * @param x and y are the original locations values
     * @param move: The direction of where the original location will interact
     */
    public void moveTo(int x_Coord, int y_Coord, String move) {
        int new_Coord = 0;
        String checkCreature;
        MersenneTwister operation = new MersenneTwister();
        double normalizedRand = operation.nextDouble();
        switch (move) {
            case "E":
                new_Coord = ((((x_Coord + 1) % this.playGround.length)
                    + this.playGround.length) % this.playGround.length);
                checkCreature = this.playGround[new_Coord][y_Coord]
                    .getCreatureType();
                if (checkCreature == String.valueOf(
                    this.alphabet[this.numOfCreatures.length - 1]) && this.D
                        + this.playGround[x_Coord][y_Coord]
                            .getDMod() > normalizedRand) {
                    this.playGround[new_Coord][y_Coord] =
                        this.playGround[x_Coord][y_Coord];
                    this.playGround[x_Coord][y_Coord] = new Cell(String.valueOf(
                        this.alphabet[this.numOfCreatures.length - 1]), "", "");
                }
                break;
            case "N":
                new_Coord = ((((y_Coord + 1) % this.playGround.length)
                    + this.playGround.length) % this.playGround.length);
                checkCreature = this.playGround[x_Coord][new_Coord]
                    .getCreatureType();
                if (checkCreature == String.valueOf(
                    this.alphabet[this.numOfCreatures.length - 1]) && this.D
                        + this.playGround[x_Coord][y_Coord]
                            .getDMod() > normalizedRand) {
                    this.playGround[x_Coord][new_Coord] =
                        this.playGround[x_Coord][y_Coord];
                    this.playGround[x_Coord][y_Coord] = new Cell(String.valueOf(
                        this.alphabet[this.numOfCreatures.length - 1]), "", "");
                }
                break;
            case "W":
                new_Coord = ((((x_Coord - 1) % this.playGround.length)
                    + this.playGround.length) % this.playGround.length);
                checkCreature = this.playGround[new_Coord][y_Coord]
                    .getCreatureType();
                if (checkCreature == String.valueOf(
                    this.alphabet[this.numOfCreatures.length - 1]) && this.D
                        + this.playGround[x_Coord][y_Coord]
                            .getDMod() > normalizedRand) {
                    this.playGround[new_Coord][y_Coord] =
                        this.playGround[x_Coord][y_Coord];
                    this.playGround[x_Coord][y_Coord] = new Cell(String.valueOf(
                        this.alphabet[this.numOfCreatures.length - 1]), "", "");
                }
                break;
            case "S":
                new_Coord = ((((y_Coord - 1) % this.playGround.length)
                    + this.playGround.length) % this.playGround.length);
                checkCreature = this.playGround[x_Coord][new_Coord]
                    .getCreatureType();
                if (checkCreature == String.valueOf(
                    this.alphabet[this.numOfCreatures.length - 1]) && this.D
                        + this.playGround[x_Coord][y_Coord]
                            .getDMod() > normalizedRand) {
                    this.playGround[x_Coord][new_Coord] =
                        this.playGround[x_Coord][y_Coord];
                    this.playGround[x_Coord][y_Coord] = new Cell(String.valueOf(
                        this.alphabet[this.numOfCreatures.length - 1]), "", "");
                }
                break;

        }

    }

    /**
     * The dies action that will see if the species at the specified location will die.
     * @param x and y are the original locations values
     */
    public void dies(int x_Coord, int y_Coord) {
        MersenneTwister operation = new MersenneTwister();
        double normalizedRand = operation.nextDouble();
        if (!playGround[x_Coord][y_Coord].getCreatureType().equals(
            alphabet[numOfCreatures.length - 1]) && normalizedRand < this.mu
                + playGround[x_Coord][y_Coord].getMuMod()) {
            playGround[x_Coord][y_Coord] = new Cell(String.valueOf(
                this.alphabet[this.numOfCreatures.length - 1]), "", "");

        }
    }

    /**
     * The reproduces action that will select an adjacent location to interact with.
     * @param x and y are the original locations values
     * @param move: The direction of where the original location will interact and if successful that will be 
     * the location of the new species
     */
    public void reproduces(int x_Coord, int y_Coord, String move) {
        MersenneTwister operation = new MersenneTwister();
        double normalizedRand = operation.nextDouble();
        int new_Coord;
        if (!playGround[x_Coord][y_Coord].getCreatureType().equals(
            alphabet[numOfCreatures.length - 1]) && normalizedRand < this.sigma
                + playGround[x_Coord][y_Coord].getSigmaMod()) {
            switch (move) {
                case "E":
                    new_Coord = ((((x_Coord + 1) % this.playGround.length)
                        + this.playGround.length) % this.playGround.length);
                    if (this.playGround[new_Coord][y_Coord].getCreatureType()
                        .equals(alphabet[numOfCreatures.length - 1])) {
                        this.playGround[new_Coord][y_Coord] = new Cell(
                            this.playGround[x_Coord][y_Coord].getCreatureType(),
                            this.playGround[x_Coord][y_Coord].getEatsString(),
                            this.playGround[x_Coord][y_Coord].getEatenString());
                    }
                    break;
                case "N":
                    new_Coord = ((((y_Coord + 1) % this.playGround.length)
                        + this.playGround.length) % this.playGround.length);
                    if (this.playGround[x_Coord][new_Coord].getCreatureType()
                        .equals(alphabet[numOfCreatures.length - 1])) {
                        this.playGround[x_Coord][new_Coord] = new Cell(
                            this.playGround[x_Coord][y_Coord].getCreatureType(),
                            this.playGround[x_Coord][y_Coord].getEatsString(),
                            this.playGround[x_Coord][y_Coord].getEatenString());
                    }
                    break;
                case "W":
                    new_Coord = ((((x_Coord - 1) % this.playGround.length)
                        + this.playGround.length) % this.playGround.length);
                    if (this.playGround[new_Coord][y_Coord].getCreatureType()
                        .equals(alphabet[numOfCreatures.length - 1])) {
                        this.playGround[new_Coord][y_Coord] = new Cell(
                            this.playGround[x_Coord][y_Coord].getCreatureType(),
                            this.playGround[x_Coord][y_Coord].getEatsString(),
                            this.playGround[x_Coord][y_Coord].getEatenString());
                    }
                    break;
                case "S":
                    new_Coord = ((((y_Coord - 1) % this.playGround.length)
                        + this.playGround.length) % this.playGround.length);
                    if (this.playGround[x_Coord][new_Coord].getCreatureType()
                        .equals(alphabet[numOfCreatures.length - 1])) {
                        this.playGround[x_Coord][new_Coord] = new Cell(
                            this.playGround[x_Coord][y_Coord].getCreatureType(),
                            this.playGround[x_Coord][y_Coord].getEatsString(),
                            this.playGround[x_Coord][y_Coord].getEatenString());
                    }
                    break;

            }
        }
    }

    /**
     * The Simple output function that returns the contents of the lattice.
     * @return String 
     */
    public String outputGround() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < playGround.length; i++) {
            for (int j = 0; j < playGround.length; j++) {
                builder.append(playGround[i][j].getCreatureType());
            }
            builder.append(",");
        }
        builder.append("\n");
        return builder.toString();
    }

    /**
     * The simple output function that returns an array that is Num of species +1 by the time
     * @return double[NumOfspecies+1][MAX_TIME] 
     */
    public double[][] outputDens() {
        return density;
    }

    /**
     * Modifies the terrain in a checkers pattern
     * @param mod: the amount the parameter is being modified by
     * @param type: The parameter that will be modified
     */
    public void terraCheckers(double mod, String type)
        throws NoSuchMethodException,
        SecurityException,
        IllegalAccessException,
        IllegalArgumentException,
        InvocationTargetException {
        String typePrime = "set" + type + "Mod";
// Simple for loop to go through a modify the locations accordingly
        for (int i = 0; i < this.sizePriv; i++) {
            for (int j = 0; j < this.sizePriv; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    Method method = this.playGround[i][j].getClass().getMethod(
                        typePrime, double.class);
                    method.invoke(this.playGround[i][j], mod);
                }
            }
        }
    }

    /**
     * Modifies the terrain in a lace pattern
     * @param mod: the amount the parameter is being modified by
     * @param type: The parameter that will be modified
     */
    public void terraLace(double mod, String type)
        throws NoSuchMethodException,
        SecurityException,
        IllegalAccessException,
        IllegalArgumentException,
        InvocationTargetException {
        String typePrime = "set" + type + "Mod";
     // Simple for loop to go through a modify the locations accordingly
        for (int i = 0; i < this.sizePriv; i++) {
            for (int j = 0; j < this.sizePriv; j++) {
                if (i % 2 == 0 || j % 2 == 0) {
                    Method method = this.playGround[i][j].getClass().getMethod(
                        typePrime, double.class);
                    method.invoke(this.playGround[i][j], mod);
                }
            }
        }
    }

    /**
     * Modifies the terrain in a paint pattern
     * @param mod: the amount the parameter is being modified by
     * @param type: The parameter that will be modified
     */
    public void terraPaint(double mod, String type)
        throws NoSuchMethodException,
        SecurityException,
        IllegalAccessException,
        IllegalArgumentException,
        InvocationTargetException {
        String typePrime = "set" + type + "Mod";
        // Simple for loop to go through a modify the locations accordingly. This will also modify all of the cells
        for (int i = 0; i < this.sizePriv; i++) {
            for (int j = 0; j < this.sizePriv; j++) {

                Method method = this.playGround[i][j].getClass().getMethod(
                    typePrime, double.class);
                method.invoke(this.playGround[i][j], mod);

            }
        }
    }

}
