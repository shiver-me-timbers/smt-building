/*
 * Copyright 2015 Karl Bennett
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

package shiver.me.timbers.building;

/**
 * Build up operations by adding {@link Block}s that can then be built.
 *
 * @author Karl Bennett
 */
public interface Builder<T> extends Iterable<T> {

    /**
     * Add a new {@link Block} to be built.
     */
    Builder<T> add(Block<T> block);

    /**
     * @return {@code true} if no blocks have been added, otherwise {@code false}.
     */
    boolean isEmpty();

    /**
     * Build all the blocks into a final result.
     */
    T build();

    /**
     * Creates an iterable for this builder that will iterate the supplied number of times.
     * <p>
     * If the supplied number of iterations is less than the number of {@code Block}s then only that number of blocks
     * will be built and have their results returned.
     * <p>
     * If the supplied number of iterations is greater than the number of {@code Block}s then all blocks will be built
     * and the final result of the built {@code Block}s will be passed back into the first {@code Block} where they will
     * be iterated through again until the supplied number of iterations is reached.
     */
    Iterable<T> iterable(int iterations);
}
