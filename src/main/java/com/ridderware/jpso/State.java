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

/**
 *  Describes the state of the particle.  The State is the current position,
 *  velocity that resulted in the position, and fitness of the particle at that
 *  position.
 *
 * @author Jeff Ridder
 */
public class State
{
    private Double[] position;

    private Double[] velocity;

    private double fitness;

    /**
     * Creates a new instance of State
     * @param size Length of position and velocity vectors in the state.
     */
    public State(int size)
    {
        this.position = new Double[size];
        this.velocity = new Double[size];
        for (int i = 0; i < size; i++)
        {
            this.position[i] = 0.;
            this.velocity[i] = 0.;
        }
        this.fitness = Double.MAX_VALUE;
    }

    /**
     * Returns the position.
     * @return position vector.
     */
    public Double[] getPosition()
    {
        return this.position;
    }

    /**
     * Returns the velocity.
     * @return velocity vector.
     */
    public Double[] getVelocity()
    {
        return this.velocity;
    }

    /**
     * Sets the position.
     * @param position position vector.
     */
    public void setPosition(Double[] position)
    {
        this.position = position;
    }

    /**
     * Sets the velocity.
     * @param velocity velocity vector.
     */
    public void setVelocity(Double[] velocity)
    {
        this.velocity = velocity;
    }

    /**
     * Sets the fitness.
     * @param fitness fitness.
     */
    public void setFitness(double fitness)
    {
        this.fitness = fitness;
    }

    /**
     * Returns the fitness.
     * @return fitness.
     */
    public double getFitness()
    {
        return this.fitness;
    }
}
