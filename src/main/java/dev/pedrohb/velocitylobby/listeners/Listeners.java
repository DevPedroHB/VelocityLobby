package dev.pedrohb.velocitylobby.listeners;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.velocitypowered.api.proxy.ProxyServer;

import dev.pedrohb.velocitylobby.VelocityLobby;

public class Listeners {

  private static final List<Object> eventsRegistered = new ArrayList<>();

  public static void setupListeners(VelocityLobby plugin, ProxyServer proxy, Logger logger) {
    eventsRegistered.add(new OnServerPreConnectEvent(proxy));
    eventsRegistered.add(new OnDisconnectEvent());

    eventsRegistered.forEach(event -> proxy.getEventManager().register(plugin, event));

    logger.info("Eventos registrado: {}", getEventsRegisteredName());
  }

  public static List<String> getEventsRegisteredName() {
    return eventsRegistered.stream().map(event -> event.getClass().getSimpleName()).toList();
  }
}
