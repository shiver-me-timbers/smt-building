package shiver.me.timbers.building;

import org.junit.Test;
import org.mockito.InOrder;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class StackBuilderTest {

    @Test
    public void Can_create_without_a_seed() {

        // When
        new StackBuilder<>(new ArrayDeque<Block<Object>>());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_build_multiple_blocks() {

        final Object seed = new Object();
        final Deque<Block<Object>> blocks = spy(new ArrayDeque<Block<Object>>());

        final Block<Object> one = mock(Block.class);
        final Block<Object> two = mock(Block.class);
        final Block<Object> three = mock(Block.class);

        final Object resultOne = new Object();
        final Object resultTwo = new Object();

        final Object expected = new Object();

        // Given
        given(three.build(seed)).willReturn(resultOne);
        given(two.build(resultOne)).willReturn(resultTwo);
        given(one.build(resultTwo)).willReturn(expected);

        // When
        final Object actual = new StackBuilder<>(blocks, seed).add(one).add(two).add(three).build();

        // Then
        assertThat(actual, equalTo(expected));

        final InOrder order = inOrder(blocks);
        order.verify(blocks).push(one);
        order.verify(blocks).push(two);
        order.verify(blocks).push(three);
    }
}