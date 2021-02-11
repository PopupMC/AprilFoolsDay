package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.OnToggleJokeCommand;

import java.util.Random;

public class SingleEntity extends PacketAdapter {
    public SingleEntity(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.SPAWN_ENTITY_LIVING);

        random = new Random();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        // If disabled for this player do nothing, stop here
        if(!OnToggleJokeCommand.getStatus(event.getPlayer()))
            return;

        event.getPacket().getIntegers().write(1, 12);

        // 10% chance of having the entity not spawn at the client
        if(random.nextInt(100) <= 10) {
            event.setCancelled(true);
        }
    }

    Random random;
}
