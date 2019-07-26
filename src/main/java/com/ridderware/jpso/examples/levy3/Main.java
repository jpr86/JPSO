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
package com.ridderware.jpso.examples.levy3;
import com.ridderware.jpso.FIPSMover;
import com.ridderware.jpso.ValueLimitSet;
import com.ridderware.jpso.Particle;
import com.ridderware.jpso.NeighborhoodTopology;
import com.ridderware.jpso.Swarm;
import com.ridderware.jpso.ValueLimits;

/**
 * Main routine for driving Levy #3.
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
        int numParticles = 100;
        Swarm swarm = new Swarm(numParticles, new Evaluator(), new NeighborhoodTopology(NeighborhoodTopology.Topology.SQUARE, numParticles, 3), "stats.out");
        
        ValueLimitSet vLimSet = new ValueLimitSet();
        for ( int i = 0; i < 2; i++ )
        {
            vLimSet.addValueLimits(new ValueLimits(-10., 10., ValueLimits.BoundaryType.STICK));
        }
        
        swarm.createParticles(vLimSet, new FIPSMover(4.1));
//        swarm.createParticles(vLimSet, new BasicMover(1.,0., 2., 2.));
//        swarm.createParticles(vLimSet, new CFMover(2.05,2.05));
        
        swarm.initializeParticles();
        
        swarm.iterate(4000);
        
        //  Now let's see how many of the 9 global minima are discovered
        
        Particle[] particles = swarm.getParticles();
        for ( int i = 0; i < particles.length; i++ )
        {
            if ( particles[i].getBestFitness() < -176.54 )
            {
                Double[] best_position = particles[i].getBestPosition();
                System.out.println((i+1)+"\t"+particles[i].getBestFitness()+"\t"+best_position[0]+"\t"+best_position[1]);
            }
        }
        
    }
    
}
