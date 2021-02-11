package com.popupmc.aprilfoolsday.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.OnToggleJokeCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

// I have no idea, yet another thing where theres no help online and I cant figure it out, this is a failure
// Until I can find someone to help me
public class OnPlayerJoinEvent implements Listener {
    public OnPlayerJoinEvent(AprilFoolsDay plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        // Get Player
        Player p = e.getPlayer();

        // Get enabled status
        String status = (OnToggleJokeCommand.getStatus(p)) ? "enabled" : "disabled";
        status = ChatColor.YELLOW + status;

        final String status2 = status;

        // Send resource pack only if player has April Fools Enabled
        // sha1sum <file> used to properly calculate hash on Linux
        if(OnToggleJokeCommand.getStatus(p)) {
            p.setResourcePack("https://www.dropbox.com/s/8fe5x31uvyzul88/MS%2BPainted%2B1.19.zip?dl=1", "73891393d9e34273c2f75cae304ee2718adb2b77");
        }

        // Announce to player after 1 second
        new BukkitRunnable() {
            @Override
            public void run() {

                // Only if player is still valid
                if(!p.isValid())
                    return;

                p.sendMessage(ChatColor.GOLD + "April Fools Day is " + status2 + ChatColor.GOLD + " for you. Toggle with " +
                        ChatColor.YELLOW + "/toggle-joke");
            }
        }.runTaskLater(plugin, 20);

//        // Get Protocol Manager
//        manager = ProtocolLibrary.getProtocolManager();
//
//        // 1st spawn fake creeper to ensure the client has a creeper loaded
//        LivingEntity creeper = spawnFakeCreeperAttempt2(p);
//
//        // 2nd switch game mode to spectator
//        switchGamemode(p, 3f);
//
//        // 3rd switch camera shader
//        changeCameraShader(p, creeper.getEntityId());
    }

    public void spawnFakeCreeper(Player p) {
        // Prepare to create and send a fake Creeper
        PacketContainer packet = manager.createPacket(PacketType.Play.Server.SPAWN_ENTITY_LIVING);

        // Set ID to creeper
        packet.getIntegers().write(0, 12);

        // Set UUID to something random
        UUID uuid = UUID.randomUUID();
        packet.getUUIDs().write(0, uuid);

        // Get players location
        Location location = p.getLocation();

        // Set fake creeper to be 5 blocks away on X & Z
        packet.getDoubles().write(0, location.getX() + 5);
        packet.getDoubles().write(1, location.getY());
        packet.getDoubles().write(2, location.getZ() + 5);

        // No Velocity
        packet.getIntegers().write(0, 0);
        packet.getIntegers().write(1, 0);
        packet.getIntegers().write(2, 0);

        // No yaw, pitch, or head pitch
        packet.getBytes().write(0, (byte) 0);
        packet.getBytes().write(1, (byte) 0);
        packet.getBytes().write(2, (byte) 0);

        // Send Fake Creeper
        sendPacket(p, packet);
    }

    public LivingEntity spawnFakeCreeperAttempt2(Player p) {
        Location location = new Location(
                p.getWorld(),
                p.getLocation().getX() + 5,
                p.getLocation().getY(),
                p.getLocation().getZ() + 5
        );

        LivingEntity creeper = (LivingEntity)p.getWorld().spawnEntity(location, EntityType.CREEPER);
        creeper.setAI(false);

        return creeper;
    }

    public void switchGamemode(Player p, float mode) {
        // Prepare to switch player's game mode
        PacketContainer packet = manager.createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);

        // Change Gamemode
        packet.getIntegers().write(0, 3);

        // to mode
        packet.getFloat().write(0, mode);

        // Send game mode change
        sendPacket(p, packet);
    }

    public void changeCameraShader(Player p, int entity) {
        PacketContainer packet = manager.createPacket(PacketType.Play.Server.CAMERA);

        // Im not sure what it means by entity id, like 12 for Creeper or the entities UUID?
        packet.getIntegers().write(0, entity);

        // Send camera shader change
        sendPacket(p, packet);
    }

    public void sendPacket(Player p, PacketContainer packet) {
        try {
            manager.sendServerPacket(p, packet);
        } catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
        }
    }

    public ProtocolManager manager;
    public AprilFoolsDay plugin;
}
