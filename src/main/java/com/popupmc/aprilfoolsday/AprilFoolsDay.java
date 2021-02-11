package com.popupmc.aprilfoolsday;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.popupmc.aprilfoolsday.commands.FakePlayer;
import com.popupmc.aprilfoolsday.commands.Toggle;
import com.popupmc.aprilfoolsday.events.OnPlayerJoinEvent;
import com.popupmc.aprilfoolsday.packets.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class AprilFoolsDay extends JavaPlugin {
    @Override
    public void onEnable() {

        // Setup event listeners
        Bukkit.getPluginManager().registerEvents(new OnPlayerJoinEvent(this), this);

        // Setup Command Code
        // Debug command to spawn fake herobrine player
        // doesnt work though, trying to work with people to figure out why
        //Objects.requireNonNull(this.getCommand("afd-spawn")).setExecutor(new FakePlayer());

        // Allow user to toggle joke
        Objects.requireNonNull(this.getCommand("toggle-joke")).setExecutor(new Toggle());

        // Grab Protocol Manager
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        // Make sure the client sees all entities as creepers
        manager.addPacketListener(new SingleEntity(this));

        // Remove all entity sounds
        // For some reason this packet doesnt ever seem to be called
        manager.addPacketListener(new NoEntitySound(this));

        // Remove all named sounds and replace with a grass step sound
        manager.addPacketListener(new SingleNamedSound(this));

        // Make world nether on login with other cosmetic changes and enable instant respawn
        manager.addPacketListener(new LoginModifications(this));

        // Make world nether on login with other cosmetic changes and enable instant respawn
        manager.addPacketListener(new RespawnModifications(this));

        // Ensure rain remains disabled and quick respawn enabled
        manager.addPacketListener(new GameStateModifications(this));

        // Corrupt Rotation & Y data in Move & Rotate packets of all but the player
        manager.addPacketListener(new CorruptPositionAndRotation(this));

        // Corrupt Rotation data in Rotate packets of all but the player
        manager.addPacketListener(new CorruptRotation(this));

        // Corrupt Y data in Rotate packets of all but the player
        manager.addPacketListener(new CorruptPosition(this));

        // All paintings are creeper paintings
        manager.addPacketListener(new CreeperPaintings(this));

        // All animations are off-hand arm swings
        manager.addPacketListener(new SingleAnimation(this));

        // Block breaking progress is reversed
        // This doesnt seem to be called
        manager.addPacketListener(new ReverseBlockBreak(this));

        // Log enabled status
        getLogger().info("AprilFoolsDay is enabled.");
    }

    // Log disabled status
    @Override
    public void onDisable() {
        getLogger().info("AprilFoolsDay is disabled");
    }
}
