package shiver.me.timbers.building;

import org.junit.Test;
import org.mockito.InOrder;

import java.util.Collection;
import java.util.TreeSet;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class CollectionBuilderTest {

    @Test
    @SuppressWarnings("unchecked")
    public void Can_build_multiple_blocks() {

        final Object subject = new Object();
        final Collection<Block<Object>> blocks = spy(new TreeSet<Block<Object>>());

        final Block<Object> one = new ComparableBlock<>(mock(Block.class), 0);
        final Block<Object> two = new ComparableBlock<>(mock(Block.class), 1);
        final Block<Object> three = new ComparableBlock<>(mock(Block.class), 2);

        final Object resultOne = new Object();
        final Object resultTwo = new Object();

        final Object expected = new Object();

        // Given
        given(one.build(subject)).willReturn(resultOne);
        given(two.build(resultOne)).willReturn(resultTwo);
        given(three.build(resultTwo)).willReturn(expected);

        // When
        final Object actual = new CollectionBuilder<>(blocks, subject)
                .addBlock(one)
                .addBlock(two)
                .addBlock(two)
                .addBlock(three)
                .build();

        // Then
        assertThat(actual, equalTo(expected));

        final InOrder order = inOrder(blocks);
        order.verify(blocks).add(one);
        order.verify(blocks, times(2)).add(two);
        order.verify(blocks).add(three);
    }

    private static class ComparableBlock<T> implements Block<T>, Comparable<ComparableBlock<T>> {

        private final Block<T> block;
        private final Integer order;

        private ComparableBlock(Block<T> block, Integer order) {
            this.block = block;
            this.order = order;
        }

        @Override
        public T build(T subject) {
            return block.build(subject);
        }

        @Override
        public int compareTo(ComparableBlock<T> that) {
            return order.compareTo(that.order);
        }
    }
}