package dev.pedrohb.velocitylobby.listeners;

import java.util.List;
import java.util.UUID;

import com.velocitypowered.api.event.EventHandler;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;

import dev.pedrohb.velocitylobby.VelocityLobby;

public class OnDisconnectEvent implements EventHandler<DisconnectEvent> {

  private static final List<UUID> connectedPlayers = VelocityLobby.getConnectedPlayers();

  @Override
  public void execute(DisconnectEvent event) {
    Player player = event.getPlayer();

    connectedPlayers.remove(player.getUniqueId());
  }
}
