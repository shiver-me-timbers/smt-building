package shiver.me.timbers.building;

import java.util.Queue;

/**
 * This builder will build it's blocks in FIFO order.
 *
 * @author Karl Bennett
 */
public class QueueBuilder<T> extends CollectionBuilder<T> {

    private final Queue<Block<T>> blocks;

    public QueueBuilder(Queue<Block<T>> blocks) {
        this(blocks, null);
    }

    public QueueBuilder(Queue<Block<T>> blocks, T seed) {
        super(blocks, seed);
        this.blocks = blocks;
    }

    @Override
    public Builder<T> add(Block<T> block) {
        this.blocks.offer(block);
        return this;
    }
}
