package shiver.me.timbers.building;

/**
 * Build up operation by adding {@link Block}s that can then be built.
 *
 * @author Karl Bennett
 */
public interface Builder<T> extends Iterable<T> {

    /**
     * Add a new {@link Block} to be built.
     */
    Builder<T> addBlock(Block<T> block);

    /**
     * Build all the blocks.
     */
    T build();
}
