package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import com.popupmc.aprilfoolsday.commands.OnToggleJokeCommand;
import org.bukkit.entity.Player;

public class SingleAnimation extends PacketAdapter {
    public SingleAnimation(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.ANIMATION);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        Player player = event.getPlayer();

        // If disabled for this player do nothing, stop here
        if(!OnToggleJokeCommand.getStatus(player))
            return;

        // Exclude the player by comparing entity ids
        if(packet.getIntegers().read(0) == player.getEntityId())
            return;

        // All animations are off-hand arm swing
        packet.getIntegers().write(1, 3);
    }
}
