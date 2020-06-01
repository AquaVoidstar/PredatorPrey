// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Mason Stoecker
package base;

import java.lang.Object;
import org.apache.commons.math3.random.MersenneTwister;

/**
 * @author aquastar
 *
 * @version 2019.12.07
 */
public class Cell {

    private String type;
    private String eats;
    private String eatenBy;
    private double D_mod;
    private double mu_mod;
    private double lambda_mod;
    private double sigma_mod;

    /**
     * The basic object, contains terrain and species information.
     * Only is initialized with a species and modified after with terrain info
     * 
     * @param inType:
     *            The species name
     * @param eatsIn:
     *            What the species eats
     * @param preyIn:
     *            What can eat the species. I know this is very confusing
     */
    Cell(String inType, String eatsIn, String preyIn) {
        type = inType;
        eats = eatsIn;
        eatenBy = preyIn;
        D_mod = 0;
        mu_mod = 0;
        lambda_mod = 0;
        sigma_mod = 0;
    }


    /**
     * Modifies the current creature
     */
    public boolean setCreature(String input) {
        if (input != null) {
            type = input;
            return true;
        }

        return false;
    }


    /**
     * Gets what the current creature eats and returns boolean
     */
    public boolean getEats(String input) {
        return eats.contains(input);
    }


    /**
     * Gets what the current creature eats and returns String
     */
    public String getEatsString() {
        return eats;
    }


    /**
     * Gets what the current creature can be eaten by and returns String
     */
    public String getEatenString() {
        return eatenBy;
    }


    /**
     * Sets what the current creature can eat
     */
    public void setEats(String newEats) {
        eats = newEats;
    }


    /**
     * Gets what the current creature name or type is
     */
    public String getCreatureType() {
        return this.type;
    }


    /**
     * Gets if the current species can be eaten by boolean value
     */
    public boolean getEatenBy(String input) {
        return eatenBy.contains(input);
    }

    /**
     * Getter for D
     */
    public double getDMod() {
        return D_mod;
    }

    /**
     * Setter for D
     */
    public void setDMod(double input) {
        D_mod = input;
    }

    /**
     * Getter for Mu
     */
    public double getMuMod() {
        return mu_mod;
    }

    /**
     * Setter for Mu
     */
    public void setMuMod(double input) {
        mu_mod = input;
    }

    /**
     * Getter for Lambda
     */
    public double getLambdaMod() {
        return lambda_mod;
    }

    /**
     * Setter for Lambda
     */
    public void setLambdaMod(double input) {
        lambda_mod = input;
    }

    /**
     * Getter for Sigma
     */
    public double getSigmaMod() {
        return sigma_mod;
    }

    /**
     * Setter for Sigma
     */
    public void setSigmaMod(double input) {
        sigma_mod = input;
    }

    /**
     * Returns cell information
     * 
     * @return String
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Type:");
        builder.append(this.type);
        builder.append("Eats:");
        builder.append(this.eats);
        builder.append("EatenBy");
        builder.append(this.eatenBy);
        return (builder.toString());
    }

    /**
     * Returns current creature information
     * 
     * @return String
     */
    public String toCreature() {
        StringBuilder builder = new StringBuilder();
        builder.append("Type:");
        builder.append(this.type);
        return (builder.toString());

    }
}
