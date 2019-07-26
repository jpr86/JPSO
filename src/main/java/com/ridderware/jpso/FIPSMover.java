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
import org.apache.logging.log4j.*;

/**
 *  Fully informed particle swarm (FIPS) mover of Rui Mendes.
 *
 * @author Jeff Ridder
 */
public class FIPSMover implements IMover
{
    private double phi;

    private static final Logger logger = LogManager.getLogger(FIPSMover.class);

    /**
     * Creates a new instance of FIPSMover
     * @param phi Parameter phi of FIPS mover.
     */
    public FIPSMover(double phi)
    {
        this.phi = phi;
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
        Double[] current_position = current.getPosition();
        Double[] current_velocity = current.getVelocity();
        Double[] personal_best_position = personal_best.getPosition();

        Double[] next_position = new Double[current_position.length];
        Double[] next_velocity = new Double[current_velocity.length];

        double k = 2 / Math.abs(2. - phi - Math.sqrt(phi * phi - 4. * phi));

        for (int i = 0; i < current_position.length; i++)
        {
            double x = current_position[i];
            double v = current_velocity[i];

            ValueLimits vLim = valueLimitSet.getValueLimits(i);

            double sum = 0.;
            for (Particle n : neighbors)
            {
                sum += MersenneTwisterFast.getInstance().nextDouble() *
                    (n.getBestPosition()[i] - x);
            }

            sum *= phi / neighbors.length;

            double max_v = vLim.getMaximum() - vLim.getMinimum();

            double next_v = Math.max(-max_v, Math.min(max_v, k * (v + sum)));

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

            if (next_x < vLim.getMinimum() || next_x > vLim.getMaximum())
            {
                logger.error("Position " + i + " value of " + next_x +
                    " is out of bounds [min,max]: [" + vLim.getMinimum() + ", " +
                    vLim.getMaximum() + "]");
            }
            next_position[i] = next_x;
            next_velocity[i] = next_v;
        }
        current.setVelocity(next_velocity);
        current.setPosition(next_position);
    }
}
