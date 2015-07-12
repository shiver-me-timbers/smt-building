package shiver.me.timbers.building;

import java.util.Iterator;

/**
 * @author Karl Bennett
 */
public abstract class AbstractBuilder<T> implements Builder<T> {

    private final T subject;
    private final Iterable<Block<T>> blocks;

    public AbstractBuilder(Iterable<Block<T>> blocks, T subject) {
        this.blocks = blocks;
        this.subject = subject;
    }

    @Override
    public T build() {

        T finalResult = subject;

        for (T result : this) {
            finalResult = result;
        }

        return finalResult;
    }

    @Override
    public Iterator<T> iterator() {
        return new ResultIterator<>(blocks.iterator(), subject);
    }

    private static class ResultIterator<T> implements Iterator<T> {

        private final Iterator<Block<T>> iterator;
        private T result;

        public ResultIterator(Iterator<Block<T>> iterator, T result) {
            this.iterator = iterator;
            this.result = result;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            return (result = iterator.next().build(result));
        }

        @Override
        public void remove() {
            iterator.remove();
        }
    }
}
