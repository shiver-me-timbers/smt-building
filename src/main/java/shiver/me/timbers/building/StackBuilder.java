package shiver.me.timbers.building;

import java.util.Deque;

/**
 * This builder will build it's blocks in LIFO order.
 *
 * @author Karl Bennett
 */
public class StackBuilder<T> extends AbstractBuilder<T> implements Builder<T> {

    private final Deque<Block<T>> blocks;

    public StackBuilder(Deque<Block<T>> blocks, T subject) {
        super(blocks, subject);
        this.blocks = blocks;
    }

    @Override
    public Builder<T> addBlock(Block<T> block) {
        this.blocks.push(block);
        return this;
    }
}
