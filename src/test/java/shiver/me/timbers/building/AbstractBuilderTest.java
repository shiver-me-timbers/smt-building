package shiver.me.timbers.building;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class AbstractBuilderTest {

    private Object subject;
    private List<Block<Object>> blocks;
    private AbstractBuilder<Object> builder;
    private Block<Object> one;
    private Block<Object> two;
    private Block<Object> three;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        subject = new Object();
        blocks = spy(new ArrayList<Block<Object>>());

        builder = new AbstractBuilder<Object>(blocks, subject) {
            @Override
            public Builder<Object> addBlock(Block<Object> block) {
                blocks.add(block);
                return this;
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
        assertThat(actual, equalTo(subject));
    }

    @Test
    public void Can_build_a_single_block() {

        final Object expected = new Object();

        // Given
        given(one.build(subject)).willReturn(expected);

        // When
        final Object actual = builder.addBlock(one).build();

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
        given(one.build(subject)).willReturn(resultOne);
        given(two.build(resultOne)).willReturn(resultTwo);
        given(three.build(resultTwo)).willReturn(expected);

        // When
        final Object actual = builder.addBlock(one).addBlock(two).addBlock(three).build();

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
        given(one.build(subject)).willReturn(resultOne);
        given(two.build(resultOne)).willReturn(resultTwo);
        given(three.build(resultTwo)).willReturn(expected);
        builder.addBlock(one).addBlock(two).addBlock(three).build();

        // When
        final Object actual = builder.build();

        // Then
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void Can_iteratively_build_all_blocks() {

        final Object resultOne = new Object();
        final Object resultTwo = new Object();

        final List<Object> actual = new ArrayList<>(3);
        final Object expected = new Object();

        // Given
        given(one.build(subject)).willReturn(resultOne);
        given(two.build(resultOne)).willReturn(resultTwo);
        given(three.build(resultTwo)).willReturn(expected);
        builder.addBlock(one).addBlock(two).addBlock(three);

        // When
        for (Object result : builder) {
            actual.add(result);
        }

        // Then
        assertThat(actual, contains(resultOne, resultTwo, expected));
    }

    @Test
    public void Can_remove_item_from_iterator() {

        // Simply here ot provide full coverage.
        final Iterator<Object> iterator = builder.addBlock(one).iterator();
        iterator.next();
        iterator.remove();
    }
}