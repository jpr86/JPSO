# JPSO
Java Particle Swarm Optimization

            This package provides a set of classes for implementing the particle swarm
            optimization (PSO) technique pioneered by Kennedy and Eberhart.  It models a swarm
            of particles that move through a continuous space in search of an optimal solution
            to the specified problem.  The fundamental unit manipulated by the PSO is the 
            particle, which has a defined search space, a location in the search space, and
            a velocity vector that guides its search.  Particles are aggregated in swarms, 
            and interact with each other as defined by the kinematics (a Mover) and the
            neighborhood topology.  The problem itself is specified by an evaluator.

        
        Examples        
                 
            The jpso package contains two sample applications which
            demonstrate some of the library features and the style
            of code that an application developer would create.  
      
           
            jpso.examples.parabolic Simple example to find the minimum of a parabola
            jpso.examples.levy3 Multi-modal example to find the 9 global minima in a space including 760 local
