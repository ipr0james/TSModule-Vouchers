package net.thenova.titan.spigot.module.vouchers;

import de.arraying.kotys.JSONArray;
import net.thenova.titan.core.message.MessageHandler;
import net.thenova.titan.core.users.user.module.UserModule;
import net.thenova.titan.library.command.data.Command;
import net.thenova.titan.library.database.connection.IDatabase;
import net.thenova.titan.library.database.sql.table.DatabaseTable;
import net.thenova.titan.spigot.module.SpigotModule;
import net.thenova.titan.spigot.module.vouchers.commands.CommandVoucher;
import net.thenova.titan.spigot.module.vouchers.handler.VoucherHandler;
import net.thenova.titan.spigot.module.vouchers.listeners.InteractEvent;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.List;

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
public final class Vouchers implements SpigotModule {

    @Override
    public final String name() {
        return "Vouchers";
    }

    @Override
    public final void load() {
        VoucherHandler.INSTANCE.load();
    }

    @Override
    public final void messages(final MessageHandler handler) {
        handler.add("module.vouchers.help-message", new JSONArray().append("",
                "&d/voucher give <player> <type> &7- Give a player a booster",
                "&d/voucher list &7- List current available vouchers",
                ""));

        // Give Command
        handler.add("module.vouchers.give.invalid-voucher", "%prefix.error% There is not a voucher by the name '&c%name%&7', " +
                "Refer to &c/vouchers list &7to view valid voucher names");
        handler.add("module.vouchers.give.given", "%prefix.info% A voucher has been added to their inventory");
        // List Command
        handler.add("module.vouchers.list.format", "%prefix.info% Voucher types: %list%");
        handler.add("module.vouchers.list.display", "&d");
        handler.add("module.vouchers.list.separator", "&8, &d");
        // Reload Command
        handler.add("module.vouchers.reload.reloaded", "%prefix.info% Plugin configuration has been reloaded");
    }

    @Override
    public final void reload() {

    }

    @Override
    public final void shutdown() { }

    @Override
    public final IDatabase database() {
        return null;
    }

    @Override
    public final List<DatabaseTable> tables() {
        return null;
    }

    @Override
    public final List<Listener> listeners() {
        return Collections.singletonList(new InteractEvent());
    }

    @SuppressWarnings("rawtypes")
    @Override
    public final List<Command> commands() {
        return Collections.singletonList(new CommandVoucher());
    }

    @Override
    public final List<Class<? extends UserModule>> user() {
        return null;
    }
}
