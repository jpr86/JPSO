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
 *  ValueLimits describe the acceptable search space for a variable.
 *
 * @author Jeff Ridder
 */
public class ValueLimits
{
    /**
     * Enumerates the types of boundaries.
     */
    public enum BoundaryType
    {
        /**
         * Values will wrap through boundaries (as in periodic boundary conditions).
         */
        WRAP,
        /**
         * Values will bounce off of boundaries.
         */
        BOUNCE,
        /**
         * Values will stick to boundaries.
         */
        STICK

    }
    private double minimum;

    private double maximum;

    private BoundaryType boundaryType;

    /**
     * Creates a new instance of ValueLimits
     * @param minimum Lower bound.
     * @param maximum Upper bound.
     * @param boundaryType BoundaryType.
     */
    public ValueLimits(double minimum, double maximum,
        BoundaryType boundaryType)
    {
        this.minimum = minimum;
        this.maximum = maximum;
        this.boundaryType = boundaryType;
    }

    /**
     * Returns the lower bound.
     * @return lower bound.
     */
    public double getMinimum()
    {
        return this.minimum;
    }

    /**
     * Returns the upper bound.
     * @return upper bound.
     */
    public double getMaximum()
    {
        return this.maximum;
    }

    /**
     * Returns the boundary type.
     * @return BoundaryType.
     */
    public BoundaryType getBoundaryType()
    {
        return this.boundaryType;
    }
}
