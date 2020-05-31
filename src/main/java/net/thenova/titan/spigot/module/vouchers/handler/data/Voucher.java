package net.thenova.titan.spigot.module.vouchers.handler.data;

import de.arraying.kotys.JSON;
import de.arraying.kotys.JSONArray;
import de.arraying.kotys.JSONField;
import lombok.Getter;
import net.thenova.titan.spigot.compatibility.compat.XItem;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright 2019 ipr0james
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@Getter
public final class Voucher {

    @JSONField(key = "commands") private JSONArray commands;
    private ItemStack item;

    public final void setItem(JSON json) {
        this.item = XItem.parse(json).build();
    }
}
