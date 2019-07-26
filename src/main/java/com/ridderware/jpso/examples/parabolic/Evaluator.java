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

package com.ridderware.jpso.examples.parabolic;
import com.ridderware.jpso.IEvaluator;
import com.ridderware.jpso.Particle;

/**
 * Evaluator for parabolic test function.
 * @author Jeff Ridder
 */
public class Evaluator implements IEvaluator
{
    
    /** Creates a new instance of Evaluator */
    public Evaluator()
    {
    }
    
    /**
     * Preevaluates the swarm.  Does nothing for this example.
     *
     * @param swarm swarm to preevaluate.
     */
    public void preevaluate(Particle[] swarm)
    {
        
    }
    
    /**
     * Postevaluates the swarm.  Does nothing for this example.
     *
     * @param swarm swarm to postevaluate.
     */    
    public void postevaluate(Particle[] swarm)
    {
        
    }
    
    /**
     * Evaluates the fitness of the particle.
     * @param position Position to be evaluated.
     * @return fitness.
     */
    public double evaluateFitness(Double[] position)
    {
        double fitness = 0.;
        for ( int i = 0; i < position.length; i++ )
        {
            fitness += position[i]*position[i];
        }
        
        fitness = Math.sqrt(fitness);
        
        return fitness;
    }
}
