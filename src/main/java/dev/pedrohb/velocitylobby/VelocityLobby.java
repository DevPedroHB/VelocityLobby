package dev.pedrohb.velocitylobby;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import dev.pedrohb.velocitylobby.listeners.Listeners;
import lombok.Getter;
import lombok.Setter;

@Plugin(
    id = "velocitylobby",
    name = "VelocityLobby",
    version = "1.0-SNAPSHOT"
)
public class VelocityLobby {

  @Getter
  private final ProxyServer proxy;
  @Getter
  private final Logger logger;
  @Getter
  private static final List<UUID> connectedPlayers = new ArrayList<>();
  @Getter
  @Setter
  private static String defaultServer;

  @Subscribe
  public void onProxyInitialization(ProxyInitializeEvent event) {
    Listeners.setupListeners(this, proxy, logger);
  }

  @Inject
  public VelocityLobby(ProxyServer proxy, Logger logger) {
    this.proxy = proxy;
    this.logger = logger;

    defaultServer = "lobby";

    logger.info("Plugin inicializado.");
  }
}
