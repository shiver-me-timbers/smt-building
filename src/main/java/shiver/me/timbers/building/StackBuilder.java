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

import java.util.Deque;

/**
 * This builder will build it's blocks in LIFO order.
 *
 * @author Karl Bennett
 */
public class StackBuilder<T> extends CollectionBuilder<T> {

    private final Deque<Block<T>> blocks;

    public StackBuilder(Deque<Block<T>> blocks) {
        this(blocks, null);
    }

    public StackBuilder(Deque<Block<T>> blocks, T seed) {
        super(blocks, seed);
        this.blocks = blocks;
    }

    @Override
    public Builder<T> add(Block<T> block) {
        this.blocks.push(block);
        return this;
    }
}
