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

import org.apache.logging.log4j.*;

/**
 *  A collection of particles.
 *
 * @author Jeff Ridder
 */
public class Swarm
{
    private IEvaluator evaluator;

    private NeighborhoodTopology neighborhoodTopology;

    private IMover mover;

    private Statistics stats;

    private Particle[] particles;

    private final static Logger logger = LogManager.getLogger(Swarm.class);

    /**
     * Creates a new instance of Swarm
     * @param numberOfParticles number of particles in the swarm.
     * @param evaluator IEvaluator to be used by members of the swarm.
     * @param neighborhoodTopology NeighborhoodTopology to be used by members of the swarm.
     */
    public Swarm(int numberOfParticles, IEvaluator evaluator,
        NeighborhoodTopology neighborhoodTopology)
    {
        this.evaluator = evaluator;
        this.neighborhoodTopology = neighborhoodTopology;
        this.particles = new Particle[numberOfParticles];
        this.stats = null;
    }

    /**
     * Creates a new instance of Swarm
     * @param numberOfParticles number of particles in the swarm.
     * @param evaluator IEvaluator to be used by members of the swarm.
     * @param neighborhoodTopology NeighborhoodTopology to be used by members of the swarm.
     * @param stats_filename Name name of the statistics file.
     */
    public Swarm(int numberOfParticles, IEvaluator evaluator,
        NeighborhoodTopology neighborhoodTopology, String stats_filename)
    {
        this(numberOfParticles, evaluator, neighborhoodTopology);
        this.stats = new Statistics(stats_filename);
    }

    /**
     * Returns the particles of the swarm.
     * @return array of Particles.
     */
    public Particle[] getParticles()
    {
        return this.particles;
    }

    /**
     * Creates the particles in the swarm.
     * @param valueLimitSet ValueLimitSet object defining the search space for the particles.
     * @param mover IMover object defining particle kinematics.
     */
    public void createParticles(ValueLimitSet valueLimitSet, IMover mover)
    {
        this.mover = mover;

        for (int i = 0; i < particles.length; i++)
        {
            particles[i] = new Particle(i, valueLimitSet, mover);
        }
    }

    /**
     * Returns the mover.
     * @return IMover object.
     */
    public IMover getMover()
    {
        return this.mover;
    }

    /**
     * Initializes the particles in the swarm.  Randomly sets initial position and evaluates fitness of that position.
     */
    public void initializeParticles()
    {
        logger.info("Initializing Swarm");

        for (int i = 0; i < particles.length; i++)
        {
            particles[i].initialize();
        }

        this.preevaluateSwarm();

        for (int i = 0; i < particles.length; i++)
        {
            particles[i].setCurrentFitness(evaluator.evaluateFitness(particles[i].getCurrentPosition()));

            particles[i].setBestPosition(particles[i].getCurrentPosition());
            particles[i].setBestFitness(particles[i].getCurrentFitness());
        }

        this.postevaluateSwarm();
    }

    /**
     * Evaluates the particles in the swarm.
     */
    protected void evaluateParticles()
    {
        for (int i = 0; i < particles.length; i++)
        {
            particles[i].setCurrentFitness(evaluator.evaluateFitness(particles[i].getCurrentPosition()));

            if (particles[i].getCurrentFitness() < particles[i].getBestFitness())
            {
                particles[i].setBestPosition(particles[i].getCurrentPosition());
                particles[i].setBestFitness(particles[i].getCurrentFitness());
            }
        }
    }

    /**
     * Preevaluates the swarm.
     */
    protected void preevaluateSwarm()
    {
        evaluator.preevaluate(particles);
    }

    /**
     * Postevaluates the swarm.
     */
    protected void postevaluateSwarm()
    {
        evaluator.postevaluate(particles);
    }

    /**
     * Iterates the swarm.
     * @param max_iterations max number of iterations to be computed by the swarm.
     */
    public void iterate(int max_iterations)
    {
        for (int i = 0; i < max_iterations; i++)
        {
            logger.info("Iteration: " + (i + 1));

            if (this.stats != null)
            {
                this.stats.outputSwarmStats(particles, i);
            }

            this.moveParticles(i, max_iterations);

            this.preevaluateSwarm();

            this.evaluateParticles();

            this.postevaluateSwarm();
        }
    }

    /**
     * Moves the particles in the swarm.
     * @param current_iteration Current iteration.
     * @param max_iterations Max number of iterations.
     */
    protected void moveParticles(int current_iteration, int max_iterations)
    {
        for (int p = 0; p < particles.length; p++)
        {
            particles[p].moveParticle(getNeighbors(p), current_iteration,
                max_iterations);
        }
    }

    /**
     * Returns the neighboring particles.
     * @param pid ID of the particle for which neighbors are to be returned.
     * @return a Java array of particles.
     */
    protected Particle[] getNeighbors(int pid)
    {
        Integer[] neighbors = this.neighborhoodTopology.getNeighborIDs(pid);

        Particle[] nbrs = new Particle[neighbors.length];

        for (int i = 0; i < nbrs.length; i++)
        {
            nbrs[i] = particles[neighbors[i]];
        }

        return nbrs;
    }

    /**
     * Returns the best state achieved by any particle so far.
     * @return State object.
     */
    public State getBestSoFar()
    {
        int best_index = 0;
        double best_fitness = Double.MAX_VALUE;

        for (int i = 0; i < particles.length; i++)
        {
            if (particles[i].getBestFitness() < best_fitness)
            {
                best_index = i;
                best_fitness = particles[i].getBestFitness();
            }
        }

        return particles[best_index].getBestState();
    }

    /**
     * Returns the evaluator for the Swarm.
     * @return the evaluator.
     */
    public IEvaluator getEvaluator()
    {
        return evaluator;
    }
}
