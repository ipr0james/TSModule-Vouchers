package net.thenova.titan.spigot.module.vouchers.commands.subs;

import net.thenova.titan.core.message.MessageHandler;
import net.thenova.titan.library.command.data.CommandContext;
import net.thenova.titan.library.command.data.CommandPermission;
import net.thenova.titan.spigot.command.SpigotCommand;
import net.thenova.titan.spigot.command.sender.SpigotSender;
import net.thenova.titan.spigot.module.vouchers.handler.VoucherHandler;

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
public final class SubCommandVoucherReload extends SpigotCommand<SpigotSender> implements CommandPermission<SpigotSender> {

    public SubCommandVoucherReload() {
        super("reload");
    }

    @Override
    public final void execute(final SpigotSender sender, final CommandContext context) {
        VoucherHandler.INSTANCE.load();
        MessageHandler.INSTANCE.build("module.vouchers.reload.reloaded").send(sender);
    }

    @Override
    public final boolean hasPermission(final SpigotSender sender) {
        return sender.hasPermission("titan.command.voucher.reload");
    }
}
