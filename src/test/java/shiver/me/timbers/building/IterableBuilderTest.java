package shiver.me.timbers.building;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class IterableBuilderTest {

    private Object seed;
    private List<Block<Object>> blocks;
    private IterableBuilder<Object> builder;
    private Block<Object> one;
    private Block<Object> two;
    private Block<Object> three;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        seed = new Object();
        blocks = spy(new ArrayList<Block<Object>>());

        builder = new IterableBuilder<Object>(blocks, seed) {
            @Override
            public Builder<Object> add(Block<Object> block) {
                blocks.add(block);
                return this;
            }

            @Override
            public boolean isEmpty() {
                return blocks.isEmpty();
            }
        };

        one = mock(Block.class);
        two = mock(Block.class);
        three = mock(Block.class);
    }

    @Test
    public void Can_build_no_blocks() {

        // When
        final Object actual = builder.build();

        // Then
        assertThat(actual, equalTo(seed));
    }

    @Test
    public void Can_build_a_single_block() {

        final Object expected = new Object();

        // Given
        given(one.build(seed)).willReturn(expected);

        // When
        final Object actual = builder.add(one).build();

        // Then
        assertThat(actual, equalTo(expected));
        verify(blocks).add(one);
    }

    @Test
    public void Can_build_multiple_blocks() {

        final Object resultOne = new Object();
        final Object resultTwo = new Object();

        final Object expected = new Object();

        // Given
        given(one.build(seed)).willReturn(resultOne);
        given(two.build(resultOne)).willReturn(resultTwo);
        given(three.build(resultTwo)).willReturn(expected);

        // When
        final Object actual = builder.add(one).add(two).add(three).build();

        // Then
        assertThat(actual, equalTo(expected));

        final InOrder order = inOrder(blocks);
        order.verify(blocks).add(one);
        order.verify(blocks).add(two);
        order.verify(blocks).add(three);
    }

    @Test
    public void Can_re_build_all_blocks() {

        final Object resultOne = new Object();
        final Object resultTwo = new Object();

        final Object expected = new Object();

        // Given
        given(one.build(seed)).willReturn(resultOne);
        given(two.build(resultOne)).willReturn(resultTwo);
        given(three.build(resultTwo)).willReturn(expected);
        builder.add(one).add(two).add(three).build();

        // When
        final Object actual = builder.build();

        // Then
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void Can_iteratively_build_all_blocks() {

        final Object resultOne = new Object();
        final Object resultTwo = new Object();
        final Object resultThree = new Object();

        final List<Object> actual = new ArrayList<>(3);

        // Given
        given(one.build(seed)).willReturn(resultOne);
        given(two.build(resultOne)).willReturn(resultTwo);
        given(three.build(resultTwo)).willReturn(resultThree);
        builder.add(one).add(two).add(three);

        // When
        for (Object result : builder) {
            actual.add(result);
        }

        // Then
        assertThat(actual, contains(resultOne, resultTwo, resultThree));
    }

    @Test
    public void Can_iteratively_build_less_blocks_that_have_been_added() {

        final Object resultOne = new Object();
        final Object resultTwo = new Object();

        final List<Object> actual = new ArrayList<>(3);

        // Given
        given(one.build(seed)).willReturn(resultOne);
        given(two.build(resultOne)).willReturn(resultTwo);
        builder.add(one).add(two).add(three);

        // When
        for (Object result : builder.iterable(2)) {
            actual.add(result);
        }

        // Then
        assertThat(actual, contains(resultOne, resultTwo));
        verifyZeroInteractions(three);
    }

    @Test
    public void Can_iteratively_build_more_blocks_that_have_been_added() {

        final Object resultOne = new Object();
        final Object resultTwo = new Object();
        final Object resultThree = new Object();
        final Object resultFour = new Object();

        final List<Object> actual = new ArrayList<>(3);

        // Given
        given(one.build(seed)).willReturn(resultOne);
        given(two.build(resultOne)).willReturn(resultTwo);
        given(one.build(resultTwo)).willReturn(resultThree);
        given(two.build(resultThree)).willReturn(resultFour);
        builder.add(one).add(two);

        // When
        for (Object result : builder.iterable(4)) {
            actual.add(result);
        }

        // Then
        assertThat(actual, contains(resultOne, resultTwo, resultThree, resultFour));
    }

    @Test
    public void Can_iteratively_build_some_blocks_when_none_have_been_added() {

        // Given
        @SuppressWarnings("unchecked")
        final List<Object> list = mock(List.class);

        // When
        for (Object result : builder.iterable(4)) {
            list.add(result);
        }

        // Then
        verifyZeroInteractions(list);
    }

    @Test
    public void Can_remove_item_from_iterator() {

        // Simply here to provide full coverage.
        final Iterator<Object> iterator = builder.add(one).iterator();
        iterator.next();
        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void Cannot_remove_from_a_limited_iterator() {

        // Simply here to provide full coverage.
        builder.iterable(1).iterator().remove();
    }
}