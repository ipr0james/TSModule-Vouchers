package net.thenova.titan.spigot.module.vouchers.listeners;

import de.arraying.kotys.JSONArray;
import net.thenova.titan.spigot.compatibility.XHandler;
import net.thenova.titan.spigot.compatibility.compat.XItem;
import net.thenova.titan.spigot.module.vouchers.handler.VoucherHandler;
import net.thenova.titan.spigot.module.vouchers.handler.data.Voucher;
import net.thenova.titan.spigot.util.UValidate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
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
public final class InteractEvent implements Listener {

    @EventHandler
    public final void onClick(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Action action = event.getAction();
        final ItemStack item = XHandler.getItemInHand(player);

        if((action != Action.RIGHT_CLICK_AIR
                    && action != Action.RIGHT_CLICK_BLOCK)
                || !UValidate.notNull(item)) {
            return;
        }

        final Voucher voucher = VoucherHandler.INSTANCE.getVoucher(item);
        if(voucher != null) {
            final JSONArray commands = voucher.getCommands();
            for(int i = 0; i < commands.length(); i++) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commands.string(i).replace("{player}", player.getName()));
            }

            XHandler.setItemInHand(player, XItem.deductItem(item));
        }
    }
}
