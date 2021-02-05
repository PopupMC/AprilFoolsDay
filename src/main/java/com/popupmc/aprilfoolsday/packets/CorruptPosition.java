package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import org.bukkit.entity.Player;

import java.util.Random;

public class CorruptPosition extends PacketAdapter {
    public CorruptPosition(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.REL_ENTITY_MOVE);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        Player player = event.getPlayer();

        // Exclude the player by comparing entity ids
        if(packet.getIntegers().read(0) == player.getEntityId())
            return;

        // 10% chance of slightly corrupting y
        if(random.nextInt(100) <= yChance) {

            // Get Current Y Delta
            short curY = packet.getShorts().read(1);

            // Add or subtract noise
            if(random.nextBoolean())
                packet.getShorts().write(1, (short) (curY + yNoise));
            else
                packet.getShorts().write(1, (short) (curY - yNoise));
        }
    }

    Random random = new Random();

    // A full block in delta values
    final static short fullBlock = 4096;

    // yNoise Range 1/4 block
    final static short yNoise = (short)(fullBlock * 0.25);

    // 10% chance of corruption
    final static int yChance = 10;
}
