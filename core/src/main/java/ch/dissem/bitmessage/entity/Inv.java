/*
 * Copyright 2015 Christian Basler
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

package ch.dissem.bitmessage.entity;

import ch.dissem.bitmessage.entity.valueobject.InventoryVector;
import ch.dissem.bitmessage.utils.Encode;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * The 'inv' command holds up to 50000 inventory vectors, i.e. hashes of inventory items.
 */
public class Inv implements MessagePayload {
    private List<InventoryVector> inventory;

    private Inv(Builder builder) {
        inventory = builder.inventory;
    }

    public List<InventoryVector> getInventory() {
        return inventory;
    }

    @Override
    public Command getCommand() {
        return Command.INV;
    }

    @Override
    public void write(OutputStream out) throws IOException {
        Encode.varInt(inventory.size(), out);
        for (InventoryVector iv : inventory) {
            iv.write(out);
        }
    }

    public static final class Builder {
        private List<InventoryVector> inventory = new LinkedList<>();

        public Builder addInventoryVector(InventoryVector inventoryVector) {
            this.inventory.add(inventoryVector);
            return this;
        }

        public Builder inventory(List<InventoryVector> inventory) {
            this.inventory = inventory;
            return this;
        }

        public Inv build() {
            return new Inv(this);
        }
    }
}
