package net.thenova.titan.spigot.module.vouchers.commands.subs;

import net.thenova.titan.core.command.CommandTabComplete;
import net.thenova.titan.core.message.MessageHandler;
import net.thenova.titan.core.message.placeholders.Placeholder;
import net.thenova.titan.core.users.UUIDCache;
import net.thenova.titan.core.users.user.User;
import net.thenova.titan.library.command.data.CommandContext;
import net.thenova.titan.library.command.data.CommandPermission;
import net.thenova.titan.library.command.data.CommandUsage;
import net.thenova.titan.spigot.command.SpigotCommand;
import net.thenova.titan.spigot.command.sender.SpigotSender;
import net.thenova.titan.spigot.module.vouchers.handler.VoucherHandler;
import net.thenova.titan.spigot.module.vouchers.handler.data.Voucher;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
@CommandUsage(
        min = 2,
        usage = "/vouchers give <name/uuid> <voucher>",
        description = "Give a voucher to a user"
)
public final class SubCommandVoucherGive extends SpigotCommand<SpigotSender> implements CommandPermission<SpigotSender>, CommandTabComplete {

    public SubCommandVoucherGive() {
        super("give");
    }

    @Override
    public final void execute(final SpigotSender sender, final CommandContext context) {
        final String[] args = context.getArguments();

        final UUID uuid;
        if(args[1].length() == 36) {
            try {
                uuid = UUID.fromString(args[0]);
            } catch (final Exception e) {
                MessageHandler.INSTANCE.build("error.player.invalid-uuid").send(sender);
                return;
            }
        } else {
            try {
                uuid = UUIDCache.INSTANCE.getUUID(args[0]);
            } catch (NullPointerException e) {
                MessageHandler.INSTANCE.build("error.player.exists").send(sender);
                return;
            }
        }

        final Player player;
        if(uuid == null || (player = Bukkit.getPlayer(uuid)) == null || !player.isOnline()) {
            MessageHandler.INSTANCE.build("error.player.offline").send(sender);
            return;
        }

        final Voucher voucher = VoucherHandler.INSTANCE.getVoucher(args[1]);
        if(voucher == null) {
            MessageHandler.INSTANCE.build("module.vouchers.give.invalid-voucher")
                    .placeholder(new Placeholder("name", args[1]))
                    .send(sender);
            return;
        }

        player.getInventory().addItem(voucher.getItem());
        MessageHandler.INSTANCE.build("module.vouchers.give.given").send(sender);
    }

    @Override
    public final boolean hasPermission(final SpigotSender sender) {
        return sender.hasPermission("titan.command.voucher.give");
    }

    @Override
    public final List<String> tabComplete(final User user, final String[] args) {
        switch(args.length) {
            case 1:
                return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
            case 2:
                return new ArrayList<>(VoucherHandler.INSTANCE.getVouchers().keySet());
            default:
                return null;
        }
    }
}
