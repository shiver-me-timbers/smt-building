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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ItemBlockTest {

    @Test
    public void Can_build_an_item_block() {

        // Given
        final Object expected = new Object();

        // When
        final Object actual = new ItemBlock<>(expected).build();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void The_item_block_has_equality() {
        EqualsVerifier.forClass(ItemBlock.class).usingGetClass().verify();
    }

    @Test
    public void The_item_block_has_a_useful_to_string() {

        // Given
        final String item = "This should be contained in the to string.";

        // When
        final String actual = new ItemBlock<>(item).toString();

        // Then
        assertThat(actual, containsString(item));
    }
}