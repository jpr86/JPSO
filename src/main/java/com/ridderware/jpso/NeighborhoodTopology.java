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
 * Class to define topologies used by particles to identify neighbors.
 * @author Jeff Ridder
 */
public class NeighborhoodTopology
{
    /**
     * Enumerates the types of topologies.
     */
    public enum Topology
    {
        /**
         * All particles are neighbors.
         */
        GLOBAL,
        /**
         * Zeroth particle is neighbor or, if zeroth particle, then all are neighbors.
         */
        STAR,
        /**
         * Particle IDs to each side are neighbors.
         */
        RING,
        /**
         * Particles are arranged in a rectangle, with neighbors above, below, and to the sides
         */
        SQUARE

    }
    private Topology topology;

    private int swarmSize;

    private int neighborhoodSize;

    /**
     * Creates an instance of NeighborhoodTopology.
     * @param topology The topology (e.g., RING, STAR, GLOBAL).
     * @param swarmSize Number of particles in the swarm.
     * @param neighborhoodSize Number of neighbors in a neighborhood.
     */
    public NeighborhoodTopology(Topology topology, int swarmSize,
        int neighborhoodSize)
    {
        this.topology = topology;
        this.swarmSize = swarmSize;
        this.neighborhoodSize = neighborhoodSize;
    }

    /**
     * Returns the ID numbers of the neighbors.
     * @param pid ID of the particle for which neighbors are to be identified.
     * @return Array of neighbor IDs.
     */
    public Integer[] getNeighborIDs(int pid)
    {
        Integer[] neighbors;
        if (topology == Topology.RING)
        {

            neighbors = new Integer[neighborhoodSize];
            for (int i = 0,  n = pid - (neighborhoodSize % 2); i <
                neighborhoodSize; i++)
            {
                neighbors[i] = (n + swarmSize) % swarmSize;
                n++;
            }
        }
        else if (topology == Topology.STAR)
        {
            if (pid == 0)
            {
                neighbors = new Integer[swarmSize];
                for (int i = 0; i < swarmSize; i++)
                {
                    neighbors[i] = i;
                }
            }
            else
            {
                neighbors = new Integer[2];
                neighbors[0] = 0;
                neighbors[1] = pid;
            }
        }
        else if (topology == Topology.GLOBAL)
        {
            neighbors = new Integer[swarmSize];
            for (int i = 0; i < swarmSize; i++)
            {
                neighbors[i] = i;
            }
        }
        else if (topology == Topology.SQUARE)
        {
            //  First, figure out the dimensions of the rectangle.
            int jmax = (int) (Math.sqrt(swarmSize));

            while (swarmSize % jmax != 0 && jmax > 1)
            {
                jmax--;
            }

            int imax = swarmSize / jmax;

            //  Find my row and column
            int row = pid / imax;
            int col = pid - row * imax;

            neighbors = new Integer[5];

            neighbors[0] = pid;

            //  The guy to my left...one lower pid unless in different row
            if ((pid - 1) / imax == row && pid - 1 >= 0)
            {
                neighbors[1] = pid - 1;
            }
            else    //  wrap
            {
                neighbors[1] = pid + imax - 1;
            }

            //  The guy to my right...one higher pid unless in different row
            if ((pid + 1) / imax == row && pid + 1 < swarmSize)
            {
                neighbors[2] = pid + 1;
            }
            else
            {
                neighbors[2] = pid - imax + 1;
            }

            //  The guy up
            if (pid - imax >= 0)
            {
                neighbors[3] = pid - imax;
            }
            else
            {
                neighbors[3] = pid + swarmSize - imax;
            }

            //  The guy down
            if (pid + imax < swarmSize)
            {
                neighbors[4] = pid + imax;
            }
            else
            {
                neighbors[4] = pid - swarmSize + imax;
            }
        }
        else //  won't happen if constructors prevent invalid topologies.
        {
            neighbors = new Integer[0];
        }
        return neighbors;

    }
}
