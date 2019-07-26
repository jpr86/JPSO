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
 *  Attractive-Repulsive PSO Mover, ARPSOMover.
 *  Based on paper of Riget and Vesterstrom.
 * @author Jeff Ridder
 */
public class ARPSOMover implements IMover
{
    private double wstart;

    private double wend;

    private double c1;

    private double c2;

    private int direction;

    /**
     * Creates a new instance of ARPSOMover
     * @param wstart Starting value of inertia weight.
     * @param wend Ending value of inertia weight.
     * @param c1 Parameter c1 of basic PSO kinematic equation.
     * @param c2 Paramter c2 of basic PSO kinematic equation.
     */
    public ARPSOMover(double wstart, double wend, double c1, double c2)
    {
        this.wstart = wstart;
        this.wend = wend;
        this.c1 = c1;
        this.c2 = c2;
        this.direction = 1;
    }

    /**
     * Sets the direction of the attracting/repelling force.
     * @param direction If direction = 1, then particles are attracting.  If direction = -1, then particles are repelling.
     */
    public void setDirection(int direction)
    {
        this.direction = direction;
    }

    /**
     * Returns the direction of the attracting/repelling force.
     * @return the direction.
     */
    public int getDirection()
    {
        return this.direction;
    }

    /**
     * Moves the particle.
     * @param current The current state (position and velocity) of the particle.
     * @param personal_best The best state ever achieved by the particle.
     * @param valueLimitSet The value limits of the search space for the particle.
     * @param neighbors The neighboring particles.
     * @param current_iteration Current iteration.
     * @param max_iterations Max number of iterations.
     */
    public void moveParticle(State current, State personal_best,
        ValueLimitSet valueLimitSet, Particle[] neighbors, int current_iteration,
        int max_iterations)
    {
        Double[] neighborhood_best_position =
            Particle.getNeighborhoodBestPosition(neighbors);

        Double[] current_position = current.getPosition();
        Double[] current_velocity = current.getVelocity();
        Double[] personal_best_position = personal_best.getPosition();

        Double[] next_position = new Double[current_position.length];
        Double[] next_velocity = new Double[current_velocity.length];

        for (int i = 0; i < current_position.length; i++)
        {
            double x = current_position[i];
            double v = current_velocity[i];

            ValueLimits vLim = valueLimitSet.getValueLimits(i);

            double max_v = vLim.getMaximum() - vLim.getMinimum();

            double w = wstart + ((double) (current_iteration + 1.) /
                (double) max_iterations) * (wend - wstart);
            double next_v = Math.max(-max_v, Math.min(max_v, w * v + direction * (c1 * MersenneTwisterFast.getInstance().
                nextDouble() * (personal_best_position[i] - x) +
                c2 * MersenneTwisterFast.getInstance().nextDouble() *
                (neighborhood_best_position[i] - x))));

            double next_x = x + next_v;

            if (next_x > vLim.getMaximum())
            {
                switch (vLim.getBoundaryType())
                {
                    case BOUNCE:
                    {
                        next_x = 2. * vLim.getMaximum() - next_x;
                        break;
                    }
                    case STICK:
                    {
                        next_x = vLim.getMaximum();
                        break;
                    }
                    case WRAP:
                    default:
                    {
                        next_x = -max_v + next_x;
                        break;
                    }
                }
            }
            else if (next_x < vLim.getMinimum())
            {
                switch (vLim.getBoundaryType())
                {
                    case BOUNCE:
                    {
                        next_x = 2. * vLim.getMinimum() - next_x;
                        break;
                    }
                    case STICK:
                    {
                        next_x = vLim.getMinimum();
                        break;
                    }
                    case WRAP:
                    default:
                    {
                        next_x = max_v + next_x;
                        break;
                    }
                }
            }

            next_position[i] = next_x;
            next_velocity[i] = next_v;
        }
        current.setVelocity(next_velocity);
        current.setPosition(next_position);
    }
}
