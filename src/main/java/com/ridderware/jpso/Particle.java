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

import com.ridderware.jrandom.MersenneTwisterFast;

/**
 * Class defining a particle - the basic unit of a swarm.
 * @author Jeff Ridder
 */
public class Particle
{
    private int id;

    private ValueLimitSet valueLimitSet;

    private IMover mover;

    private State current;

    private State personal_best;

    /**
     * Creates a new instance of Particle.
     * @param id The ID number of the new particle.
     * @param valueLimitSet A ValueLimitSet object containing a definition of the search space.
     * @param mover An IMover object defining how a particle will move through the search space.
     */
    public Particle(int id, ValueLimitSet valueLimitSet, IMover mover)
    {
        this.id = id;
        this.valueLimitSet = valueLimitSet;
        this.mover = mover;

        this.current = new State(valueLimitSet.getSize());
        this.personal_best = new State(valueLimitSet.getSize());
    }

    /**
     * Initializes a particle.
     */
    public void initialize()
    {
        Double[] position = new Double[valueLimitSet.getSize()];

        for (int i = 0; i < valueLimitSet.getSize(); i++)
        {
            double minimum = valueLimitSet.getValueLimits(i).getMinimum();
            double maximum = valueLimitSet.getValueLimits(i).getMaximum();

            double value = minimum + MersenneTwisterFast.getInstance().
                nextDouble() * (maximum - minimum);
            position[i] = value;
        }

        current.setPosition(position);
    }

    /**
     * Returns the ValueLimitSet object that contains the search space.
     * @return a ValueLimitSet object.
     */
    public ValueLimitSet getValueLimitSet()
    {
        return this.valueLimitSet;
    }

    /**
     * Returns the current state (position, velocity, fitness) of the particle.
     * @return current state.
     */
    public State getCurrentState()
    {
        return current;
    }

    /**
     * Returns the personal best state ever achieved by the particle.
     * @return personal best state.
     */
    public State getBestState()
    {
        return personal_best;
    }

    /**
     * Returns the current position vector of the particle.
     * @return current position vector.
     */
    public Double[] getCurrentPosition()
    {
        return current.getPosition();
    }

    /**
     * Sets the current fitness of the particle.  This should be called only by an
     * IEvaluator object.
     * @param fitness The fitness of the particle.
     */
    public void setCurrentFitness(double fitness)
    {
        current.setFitness(fitness);
    }

    /**
     * Returns the current fitness of the particle.
     * @return current fitness.
     */
    public double getCurrentFitness()
    {
        return this.current.getFitness();
    }

    /**
     * Sets the best position of the particle.
     * @param best_position position vector.
     */
    public void setBestPosition(Double[] best_position)
    {
        this.personal_best.setPosition(best_position);
    }

    /**
     * Returns the best position of the particle.
     * @return position vector.
     */
    public Double[] getBestPosition()
    {
        return personal_best.getPosition();
    }

    /**
     * Sets the best fitness of the particle.
     * @param best_fitness best fitness.
     */
    public void setBestFitness(double best_fitness)
    {
        this.personal_best.setFitness(best_fitness);
    }

    /**
     * Returns the best fitness of the particle.
     * @return best fitness.
     */
    public double getBestFitness()
    {
        return this.personal_best.getFitness();
    }

    /**
     * Calls the moveParticle method of the particle's IMover.
     * @param neighbors Java array of my neighbors.
     * @param current_iteration current iteration.
     * @param max_iterations max number of iterations.
     */
    public void moveParticle(Particle[] neighbors, int current_iteration,
        int max_iterations)
    {
        mover.moveParticle(current, personal_best, valueLimitSet, neighbors,
            current_iteration, max_iterations);
    }

    /**
     * Returns the particle's IMover.
     * @return an IMover object.
     */
    public IMover getMover()
    {
        return this.mover;
    }

    /**
     * Returns the best position amongst the neighors of the particle.
     *
     * @param particles Java array of particles from which best position is to be determined.
     * @return position vector.
     */
    public static Double[] getNeighborhoodBestPosition(Particle[] particles)
    {
        //  Find most fit particle in neighborhood
        double best_fitness = Double.MAX_VALUE;
        Particle best = null;
        for (Particle p : particles)
        {
            if (p.getBestFitness() < best_fitness)
            {
                best = p;
                best_fitness = p.getBestFitness();
            }
        }

        //  return most fit particle's position
        return best.getBestPosition();
    }
}
