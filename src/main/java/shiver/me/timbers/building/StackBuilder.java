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
