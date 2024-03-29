/*
 *    IademAttributeSplitSuggestion.java
 *
 *    @author Isvani Frias-Blanco
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *    
 *    
 */

package moa.classifiers.trees.iadem;

import moa.classifiers.core.AttributeSplitSuggestion;
import moa.classifiers.core.conditionaltests.InstanceConditionalTest;

public class IademAttributeSplitSuggestion extends AttributeSplitSuggestion {
    private static final long serialVersionUID = 1L;
    protected double meritLowerBound;

    public IademAttributeSplitSuggestion(InstanceConditionalTest splitTest, 
            double[][] resultingClassDistributions, 
            double merit,
            double infMerit) {
        super(splitTest, resultingClassDistributions, merit);
        this.meritLowerBound = infMerit;
    }

    public double getMeritLowerBound() {
        return this.meritLowerBound;
    }
}
