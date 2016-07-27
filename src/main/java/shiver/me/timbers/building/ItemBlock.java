/*
 * Copyright 2016 Karl Bennett
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package shiver.me.timbers.building;

/**
 * @author Karl Bennett
 */
public class ItemBlock<T> extends AtomicBlock<T> {

    private final T item;

    public ItemBlock(T item) {
        this.item = item;
    }

    @Override
    protected T build() {
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ItemBlock<?> that = (ItemBlock<?>) o;

        return item != null ? item.equals(that.item) : that.item == null;

    }

    @Override
    public int hashCode() {
        return item != null ? item.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ItemBlock{" +
            "item=" + item +
            '}';
    }
}
