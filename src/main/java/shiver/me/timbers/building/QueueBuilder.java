package shiver.me.timbers.building;

import java.util.Queue;

/**
 * This builder will build it's blocks in FIFO order.
 *
 * @author Karl Bennett
 */
public class QueueBuilder<T> extends AbstractBuilder<T> {

    private final Queue<Block<T>> blocks;

    public QueueBuilder(Queue<Block<T>> blocks, T subject) {
        super(blocks, subject);
        this.blocks = blocks;
    }

    @Override
    public Builder<T> addBlock(Block<T> block) {
        this.blocks.offer(block);
        return this;
    }
}
