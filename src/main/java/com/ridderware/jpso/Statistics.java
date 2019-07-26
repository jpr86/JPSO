/* %%
 * 
 * JPSO
 *
 * Copyright 2006 Jeff Ridder
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ridderware.jpso;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.logging.log4j.*;

/**
 *  Class for computing population statistics and writing them to a file.
 *
 * @author Jeff Ridder
 */
public class Statistics
{
    /**
     * File to write the statistics to.
     */
    protected File file;

    private final static Logger logger = LogManager.getLogger(Statistics.class);

    /**
     *  Constructor for the Statistics object
     */
    public Statistics()
    {
        this.file = null;
    }

    /**
     *  Constructor for the Statistics object
     *
     * @param  file statistics file.
     */
    public Statistics(File file)
    {
        this.file = file;
    }

    /**
     *  Constructor for the Statistics object
     *
     * @param  filename name of the statistics file to create.
     */
    public Statistics(String filename)
    {
        this.file = new File(filename);
    }

    /**
     * Prints a header at the beginning of the statistics file.
     */
    public void printHeader()
    {
        if (file != null)
        {
            String header = "Iteration #";
            header += "\tMinimum Fitness Score";
            header += "\tAverage Fitness Score";
            header += "\tMaximum Fitness Score";
            header += "\tStandard Deviation";

            string2file(false, header, file);
        }
    }

    /**
     *  Outputs the statistics of the swarm at the current iteration.
     *
     * @param  particles  the array of particles for which statistics are to be computed and printed.
     * @param  current_iteration the current iteration number to be printed in the statistics file.
     */
    public void outputSwarmStats(Particle[] particles, int current_iteration)
    {
        if (file == null)
        {
            return;
        }

        if (particles.length == 0)
        {
            logger.error("Error. Statistics (outputSwarmStats) called with 0-length particles array");
        }

        if (current_iteration == 0)
        {
            this.printHeader();
        }

        //  Compute average fitness, and find min and max
        double ave_fitness = 0.;
        double min_fitness = Double.MAX_VALUE;
        double max_fitness = -Double.MAX_VALUE;
        for (Particle p : particles)
        {
            double f = p.getCurrentFitness();

            ave_fitness += f;

            min_fitness = Math.min(f, min_fitness);
            max_fitness = Math.max(f, max_fitness);
        }
        ave_fitness /= particles.length;

        //  Now compute std. dev.
        double sigma = 0.;
        for (Particle p : particles)
        {
            sigma += Math.pow(p.getCurrentFitness() - ave_fitness, 2.);
        }

        if (particles.length > 1)
        {
            sigma = Math.sqrt(sigma / (particles.length - 1));
        }
        else
        {
            sigma = 0.;
        }

        //generation #
        String line = "  " + current_iteration;
        //minimum fitness score
        line += "\t\t\t" + min_fitness;
        //average fitness
        line += "\t\t\t" + ave_fitness;
        //maximum fitness score
        line += "\t\t\t" + max_fitness;
        //average fitness standard deviation
        line += "\t\t\t" + sigma;

        string2file(true, line, file);
    }

    /**
     * Writes the specified string to the specified file.
     *
     * @param  append  true appends data to current filename. false overwrites
     *      current file.
     * @param  line    line to write to file.
     * @param  file file to write to.
     * @return true if there was a IOException.
     */
    public boolean string2file(boolean append, String line, File file)
    {
        if (file == null)
        {
            return true;
        }

        boolean error = false;
        line.trim();

        try
        {
            FileWriter fw = new FileWriter(file, append);
            PrintWriter outFile = new PrintWriter(fw);

            outFile.println(line);
            outFile.close();
        }
        catch (IOException e)
        {
            logger.error("ERROR - Could not write to file: " +
                file.getAbsolutePath());
            logger.error("IO Exception: " + e);
            error = true;
        }

        return error;
    }
//end ArrayList2file
}

