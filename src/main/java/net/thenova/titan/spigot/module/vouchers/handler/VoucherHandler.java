package net.thenova.titan.spigot.module.vouchers.handler;

import de.arraying.kotys.JSON;
import net.thenova.titan.core.module.data.JSONFileModuleData;
import net.thenova.titan.library.file.FileHandler;
import net.thenova.titan.spigot.module.vouchers.handler.data.Voucher;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

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
public enum VoucherHandler {
    INSTANCE;

    private final Map<String, Voucher> vouchers = new HashMap<>();

    public void load() {
        this.vouchers.clear();

        final JSON json = FileHandler.INSTANCE.loadJSONFile(new JSONFileModuleData("vouchers")).getJSON();
        json.raw().keySet()
                .forEach(key -> {
            final JSON vc = json.json(key);
            final Voucher voucher = vc.marshal(Voucher.class);
            voucher.setItem(vc.json("item"));

            VoucherHandler.this.vouchers.put(key.toLowerCase(), voucher);
        });
    }

    /**
     * Return the voucher from an ItemStack being clicked
     * @param item - Item to be checked
     * @return - Return the Voucher object
     */
    public final Voucher getVoucher(final ItemStack item) {
        return this.vouchers.values()
                .stream()
                .filter(voucher -> voucher.getItem().equals(item))
                .findFirst()
                .orElse(null);
    }

    /**
     * Return the voucher from a String name
     * @param type - String type/name to be checked
     * @return - Return the Voucher object
     */
    public final Voucher getVoucher(String type) {
        return this.vouchers.get(type.toLowerCase());
    }

    /**
     * @return - Return a HashMap of all vouchers
     */
    public final Map<String, Voucher> getVouchers() {
        return this.vouchers;
    }
}
