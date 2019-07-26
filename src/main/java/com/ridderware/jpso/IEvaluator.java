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
 * Interface defining methods for an evaluator.
 * @author Jeff Ridder
 */
public interface IEvaluator
{
    /**
     * Method to evaluate the fitness of a particle.
     * @param position The position of the particle to be evaluated.
     * @return The fitness of the particle.
     */
    public double evaluateFitness(Double[] position);

    /**
     * Method to preevaluate a swarm.
     * @param swarm the collection of particles.
     */
    public void preevaluate(Particle[] swarm);

    /**
     * Method to postevaluate a swarm.
     * @param swarm the collection of particles.
     */
    public void postevaluate(Particle[] swarm);
}
