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

import org.junit.Test;
import org.mockito.InOrder;

import java.util.LinkedList;
import java.util.Queue;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class QueueBuilderTest {

    @Test
    public void Can_create_without_a_seed() {

        // When
        new QueueBuilder<>(new LinkedList<Block<Object>>());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_build_multiple_blocks() {

        final Object seed = new Object();
        final Queue<Block<Object>> blocks = spy(new LinkedList<Block<Object>>());

        final Block<Object> one = mock(Block.class);
        final Block<Object> two = mock(Block.class);
        final Block<Object> three = mock(Block.class);

        final Object resultOne = new Object();
        final Object resultTwo = new Object();

        final Object expected = new Object();

        // Given
        given(one.build(seed)).willReturn(resultOne);
        given(two.build(resultOne)).willReturn(resultTwo);
        given(three.build(resultTwo)).willReturn(expected);

        // When
        final Object actual = new QueueBuilder<>(blocks, seed).add(one).add(two).add(three).build();

        // Then
        assertThat(actual, equalTo(expected));

        final InOrder order = inOrder(blocks);
        order.verify(blocks).offer(one);
        order.verify(blocks).offer(two);
        order.verify(blocks).offer(three);
    }
}