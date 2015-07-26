package shiver.me.timbers.building;

import java.util.Collection;

/**
 * This builder will build it's blocks in what ever way the supplied {@link Collection} implementation dictates.
 *
 * @author Karl Bennett
 */
public class CollectionBuilder<T> extends IterableBuilder<T> implements Builder<T> {

    private final Collection<Block<T>> blocks;

    public CollectionBuilder(Collection<Block<T>> blocks) {
        this(blocks, null);
    }

    public CollectionBuilder(Collection<Block<T>> blocks, T seed) {
        super(blocks, seed);
        this.blocks = blocks;
    }

    @Override
    public Builder<T> add(Block<T> block) {
        this.blocks.add(block);
        return this;
    }

    @Override
    public boolean isEmpty() {
        return blocks.isEmpty();
    }
}
