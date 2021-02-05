package com.popupmc.aprilfoolsday.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.popupmc.aprilfoolsday.AprilFoolsDay;
import org.bukkit.Sound;

public class SingleNamedSound extends PacketAdapter {
    public SingleNamedSound(AprilFoolsDay plugin) {
        super(plugin, PacketType.Play.Server.NAMED_SOUND_EFFECT);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        event.getPacket().getSoundEffects().write(0, Sound.BLOCK_GRASS_STEP);
    }
}
