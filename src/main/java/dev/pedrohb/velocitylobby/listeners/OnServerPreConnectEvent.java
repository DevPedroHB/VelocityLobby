package dev.pedrohb.velocitylobby.listeners;

import java.util.Optional;

import com.velocitypowered.api.event.EventHandler;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import dev.pedrohb.velocitylobby.VelocityLobby;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerPreConnectEvent implements EventHandler<ServerPreConnectEvent> {

  private final ProxyServer proxy;

  @Override
  public void execute(ServerPreConnectEvent event) {
    var connectedPlayers = VelocityLobby.getConnectedPlayers();
    var defaultServer = VelocityLobby.getDefaultServer();

    Player player = event.getPlayer();

    if(!connectedPlayers.contains(player.getUniqueId())) {
      connectedPlayers.add(player.getUniqueId());

      if(!event.getOriginalServer().getServerInfo().getName().equalsIgnoreCase(defaultServer)) {
        event.setResult(ServerPreConnectEvent.ServerResult.denied());

        Optional<RegisteredServer> server = proxy.getServer(defaultServer);

        server.ifPresent(registeredServer -> event.getPlayer().createConnectionRequest(registeredServer));
      }
    }
  }
}
