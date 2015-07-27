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

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.Collection;
import java.util.TreeSet;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

public class CollectionBuilderTest {

    private Object seed;
    private Collection<Block<Object>> blocks;
    private CollectionBuilder<Object> builder;

    @Before
    public void setUp() {
        seed = new Object();
        blocks = spy(new TreeSet<Block<Object>>());
        builder = new CollectionBuilder<>(blocks, seed);
    }

    @Test
    public void Can_create_without_a_seed() {

        // When
        new CollectionBuilder<>(blocks);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_build_multiple_blocks() {

        final Block<Object> one = new ComparableBlock<>(mock(Block.class), 0);
        final Block<Object> two = new ComparableBlock<>(mock(Block.class), 1);
        final Block<Object> three = new ComparableBlock<>(mock(Block.class), 2);

        final Object resultOne = new Object();
        final Object resultTwo = new Object();

        final Object expected = new Object();

        // Given
        given(one.build(seed)).willReturn(resultOne);
        given(two.build(resultOne)).willReturn(resultTwo);
        given(three.build(resultTwo)).willReturn(expected);

        // When
        final Object actual = builder.add(one).add(two).add(two).add(three).build();

        // Then
        assertThat(actual, equalTo(expected));

        final InOrder order = inOrder(blocks);
        order.verify(blocks).add(one);
        order.verify(blocks, times(2)).add(two);
        order.verify(blocks).add(three);
    }

    @Test
    public void Can_check_if_the_builder_is_empty() {

        // When
        final boolean actual = builder.isEmpty();

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_if_the_builder_is_not_empty() {

        // When
        @SuppressWarnings("unchecked")
        final boolean actual = builder.add(mock(ComparableBlock.class)).isEmpty();

        // Then
        assertThat(actual, is(false));
    }

    private static class ComparableBlock<T> implements Block<T>, Comparable<ComparableBlock<T>> {

        private final Block<T> block;
        private final Integer order;

        private ComparableBlock(Block<T> block, Integer order) {
            this.block = block;
            this.order = order;
        }

        @Override
        public T build(T result) {
            return block.build(result);
        }

        @Override
        public int compareTo(ComparableBlock<T> that) {
            return order.compareTo(that.order);
        }
    }
}