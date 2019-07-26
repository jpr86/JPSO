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

import java.util.ArrayList;

/**
 * An array of ValueLimits objects.  This defines the size and dimensions of the
 * search space.
 *
 * @author Jeff Ridder
 */
public class ValueLimitSet
{
    private final ArrayList<ValueLimits> valueLimitSet = new ArrayList<ValueLimits>();

    /** Creates a new instance of ValueLimitSet */
    public ValueLimitSet()
    {
    }

    /**
     * Adds a ValueLimits object to the set.
     * @param valueLimits ValueLimits object.
     */
    public void addValueLimits(ValueLimits valueLimits)
    {
        valueLimitSet.add(valueLimits);
    }

    /**
     * Returns the size of the set (number of ValueLimits objects).
     * @return number of ValueLimits objects in the set.
     */
    public int getSize()
    {
        return valueLimitSet.size();
    }

    /**
     * Returns the ValueLimits object at the specified index.
     * @param index Index of requested object.
     * @return ValueLimits object.
     */
    public ValueLimits getValueLimits(int index)
    {
        return this.valueLimitSet.get(index);
    }
}
