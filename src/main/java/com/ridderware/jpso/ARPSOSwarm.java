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
 *  Implements the diversity enhanced particle movement scheme of Riget and Vesterstrom.
 *
 * @author Jeff Ridder
 */
public class ARPSOSwarm extends Swarm
{
    private double diversity_low;

    private double diversity_high;

    /**
     * Creates an attractive/repulsive swarm.
     * @param numberOfParticles Number of particles to create in the swarm.
     * @param evaluator The evaluator for particles in the swarm.
     * @param neighborhoodTopology The topology used to define neighbors.
     * @param diversity_low The low diversity parameter (specific to ARPSO).
     * @param diversity_high The high diversity parameter (specific to ARPSO).
     */
    public ARPSOSwarm(int numberOfParticles, IEvaluator evaluator,
        NeighborhoodTopology neighborhoodTopology,
        double diversity_low, double diversity_high)
    {
        super(numberOfParticles, evaluator, neighborhoodTopology);
        this.diversity_low = diversity_low;
        this.diversity_high = diversity_high;
    }

    /**
     * Creates an attractive/repulsive swarm.
     * @param numberOfParticles Number of particles to create in the swarm.
     * @param evaluator The evaluator for particles in the swarm.
     * @param neighborhoodTopology The topology used to define neighbors.
     * @param stats_filename Name of the statistics file.
     * @param diversity_low The low diversity parameter (specific to ARPSO).
     * @param diversity_high The high diversity parameter (specific to ARPSO).
     */
    public ARPSOSwarm(int numberOfParticles, IEvaluator evaluator,
        NeighborhoodTopology neighborhoodTopology, String stats_filename,
        double diversity_low, double diversity_high)
    {
        super(numberOfParticles, evaluator, neighborhoodTopology, stats_filename);
        this.diversity_low = diversity_low;
        this.diversity_high = diversity_high;
    }

    /**
     * Moves the particles by first considering the swarm diversity in order to set whether the swarm is attracting or repelling.
     * @param current_iteration The current iteration.
     * @param max_iterations The max number of iterations.
     */
    @Override
    protected void moveParticles(int current_iteration, int max_iterations)
    {
        if (this.getMover() instanceof ARPSOMover)
        {
            //  Assess diversity and set direction on the mover
            double diversity = this.getDiversity(this.getParticles());

            ARPSOMover arMover = ((ARPSOMover) this.getMover());

            if (diversity < diversity_low && arMover.getDirection() > 0)
            {
                //  Tell the ARPSOMover to set direction = -1;
                arMover.setDirection(-1);
                System.out.println("Repelling at iteration: " +
                    current_iteration);
            }
            else if (diversity > diversity_high && arMover.getDirection() < 0)
            {
                //  Tell the ARPSOMover to set direction = 1;
                arMover.setDirection(1);
                System.out.println("Attracting at iteration " +
                    current_iteration);
            }
        }

        for (int p = 0; p < this.getParticles().length; p++)
        {
            this.getParticles()[p].moveParticle(getNeighbors(p),
                current_iteration, max_iterations);
        }
    }

    /**
     * Returns the diversity factor of the swarm.  This is calculated using the equation of Riget and Vesterstrom.
     * @param particles The particles in the swarm.
     * @return the diversity of the swarm.
     */
    protected double getDiversity(Particle[] particles)
    {
        int pos_length = particles[0].getCurrentPosition().length;

        //  Find average position
        Double[] ave_position = new Double[pos_length];
        for (int i = 0; i < pos_length; i++)
        {
            ave_position[i] = 0.;
            for (Particle p : particles)
            {
                ave_position[i] += p.getCurrentPosition()[i];
            }

            ave_position[i] /= particles.length;
        }

        //  Find max diagonal in search space
        ValueLimitSet vLim = particles[0].getValueLimitSet();
        double diag_length = 0.;
        for (int i = 0; i < vLim.getSize(); i++)
        {
            double diff = vLim.getValueLimits(i).getMaximum() - vLim.getValueLimits(i).
                getMinimum();

            diag_length += diff * diff;
        }

        diag_length = Math.sqrt(diag_length);

        double diversity = 0.;
        for (Particle p : particles)
        {
            double variance = 0.;
            for (int i = 0; i < pos_length; i++)
            {
                variance +=
                    Math.pow(p.getCurrentPosition()[i] - ave_position[i], 2);
            }

            diversity += Math.sqrt(variance);
        }

        diversity /= (particles.length * diag_length);

        return diversity;
    }
}
