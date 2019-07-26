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
import com.ridderware.jpso.ARPSOSwarm;
import com.ridderware.jpso.ValueLimitSet;
import com.ridderware.jpso.NeighborhoodTopology;
import com.ridderware.jpso.ARPSOMover;
import com.ridderware.jpso.State;
import com.ridderware.jpso.Swarm;
import com.ridderware.jpso.ValueLimits;

/**
 * Main driver for parabolic test.
 * @author Jeff Ridder
 */
public class Main
{
    /**
     * Main.
     * @param args args.
     */
    public static void main(String[] args)
    {
        int numParticles = 40;
        Swarm swarm = new ARPSOSwarm(numParticles, new Evaluator(), new NeighborhoodTopology(NeighborhoodTopology.Topology.STAR, numParticles, 3), 5.e-6, 0.25);
        
        ValueLimitSet vLimSet = new ValueLimitSet();
        for ( int i = 0; i < 4; i++ )
        {
            vLimSet.addValueLimits(new ValueLimits(-10., 10., ValueLimits.BoundaryType.STICK));
        }
        
        swarm.createParticles(vLimSet, new ARPSOMover(1.,0.,2., 2.));
        
        swarm.initializeParticles();
        
        swarm.iterate(4000);
        
        State best = swarm.getBestSoFar();
        
        System.out.println("Best Fitness: "+best.getFitness());
        
        Double[] best_position = best.getPosition();
        for ( int i = 0; i < best_position.length; i++)
        {
            System.out.println((i+1)+": "+best_position[i]);
        }
        
    }
    
}
