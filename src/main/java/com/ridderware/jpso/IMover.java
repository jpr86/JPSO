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
 *  Interface for object to calculate the velocity and new position of the particle.
 * @author Jeff Ridder
 */
public interface IMover
{
    /**
     * Moves the particle.
     * @param current The current state (position and velocity) of the particle.
     * @param personal_best The best state ever achieved by the particle.
     * @param valueLimitSet The value limits of the search space for the particle.
     * @param neighbors Neighbors of particle being moved.
     * @param current_iteration Current iteration.
     * @param max_iterations Max number of iterations.
     */
    public void moveParticle(State current, State personal_best,
        ValueLimitSet valueLimitSet, Particle[] neighbors, int current_iteration,
        int max_iterations);
}
