package shiver.me.timbers.building;

import java.util.Iterator;

/**
 * @author Karl Bennett
 */
public abstract class IterableBuilder<T> implements Builder<T> {

    private final T seed;
    private final Iterable<Block<T>> blocks;

    public IterableBuilder(Iterable<Block<T>> blocks, T seed) {
        this.blocks = blocks;
        this.seed = seed;
    }

    @Override
    public T build() {

        T finalResult = seed;

        for (T result : this) {
            finalResult = result;
        }

        return finalResult;
    }

    @Override
    public Iterable<T> iterable(final int iterations) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new LimitedIterator<>(IterableBuilder.this, iterations);
            }
        };
    }

    @Override
    public Iterator<T> iterator() {
        return iterator(seed);
    }

    Iterator<T> iterator(T seed) {
        return new ResultIterator<>(blocks.iterator(), seed);
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

    private static class LimitedIterator<T> implements Iterator<T> {

        private final IterableBuilder<T> builder;
        private final int iterations;
        private Iterator<T> iterator;
        private int count;
        private T result;

        public LimitedIterator(IterableBuilder<T> builder, int iterations) {
            this.builder = builder;
            this.iterations = iterations;
            this.iterator = builder.iterator();
        }

        @Override
        public boolean hasNext() {
            return !builder.isEmpty() && count < iterations;
        }

        @Override
        public T next() {
            count++;
            return iterator.hasNext() ?
                (result = iterator.next()) : (result = (iterator = builder.iterator(result)).next());
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
