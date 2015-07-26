package shiver.me.timbers.building;

/**
 * A block that can be added to a {@link Builder} to be executed on the build.
 *
 * @author Karl Bennett
 */
public interface Block<T> {

    T build(T result);
}
