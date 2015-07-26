package shiver.me.timbers.building;

/**
 * Build up operations by adding {@link Block}s that can then be built.
 *
 * @author Karl Bennett
 */
public interface Builder<T> extends Iterable<T> {

    /**
     * Add a new {@link Block} to be built.
     */
    Builder<T> add(Block<T> block);

    /**
     * @return {@code true} if no blocks have been added, otherwise {@code false}.
     */
    boolean isEmpty();

    /**
     * Build all the blocks into a final result.
     */
    T build();

    /**
     * Creates an iterable for this builder that will iterate the supplied number of times.
     *
     * If the supplied number of iterations is less than the number of {@code Block}s then only that number of blocks
     * will be built and have their results returned.
     *
     * If the supplied number of iterations is greater than the number of {@code Block}s then all blocks will be built
     * and the final result of the built {@code Block}s will be passed back into the first {@code Block} where they will
     * be iterated through again until the supplied number of iterations is reached.
     */
    Iterable<T> iterable(int iterations);
}
