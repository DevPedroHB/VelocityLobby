package dev.pedrohb.velocitylobby.commands;

import java.util.Collection;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.VelocityBrigadierMessage;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import dev.pedrohb.velocitylobby.VelocityLobby;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class DefaultLobbyCommand {

  public static BrigadierCommand createBrigadierCommand(final ProxyServer proxy) {
    LiteralCommandNode<CommandSource> setDefaultLobbyNode = LiteralArgumentBuilder.<CommandSource>literal("defaultlobby")
        .requires(commandSource -> {
          if(!commandSource.hasPermission("permission.node")) {
            commandSource.sendMessage(Component.text("No permission."));

            return false;
          }

          return true;
        })
        .then(RequiredArgumentBuilder.<CommandSource, String>argument("server", StringArgumentType.word())
            .suggests((ctx, builder) -> {
              Collection<RegisteredServer> servers = proxy.getAllServers();

              servers.forEach(server -> {
                try {
                  String argument = ctx.getArgument("server", String.class);

                  if(server.getServerInfo().getName().startsWith(argument)) {
                    builder.suggest(server.getServerInfo().getName(), VelocityBrigadierMessage.tooltip(MiniMessage.miniMessage().deserialize("<rainbow>" + server.getServerInfo().getName())));
                  }
                } catch (IllegalArgumentException e) {
                  builder.suggest(server.getServerInfo().getName(), VelocityBrigadierMessage.tooltip(MiniMessage.miniMessage().deserialize("<rainbow>" + server.getServerInfo().getName())));
                }
              });

              return builder.buildFuture();
            }).executes(context -> {
              if(!(context.getSource() instanceof Player)) {
                context.getSource().sendMessage(Component.text("You must be a player."));

                return 0;
              }

              String argumentProvided = context.getArgument("server", String.class);

              VelocityLobby.setDefaultServer(argumentProvided);

              Player player = (Player) context.getSource();

              player.sendMessage(Component.text("Set default server to " + argumentProvided));

              return Command.SINGLE_SUCCESS;
            })).executes(context -> {
              if(!(context.getSource() instanceof Player)) {
                context.getSource().sendMessage(Component.text("You must be a player."));

                return 0;
              }

              return Command.SINGLE_SUCCESS;
        }).build();

    return new BrigadierCommand(setDefaultLobbyNode);
  }
}
