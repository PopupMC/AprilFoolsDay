## April Fools Day

A purely cosmetic plugin that modifies quite a lot of packets to the client 
for funny and sometimes mildly annoying effects. Planned to be enabled by 
default for non-moderators and non-admins but to allow anyone to toggle it.

Toggling effects would require to logout and back in.

### Protocol Lib Resources

Protocol Lib was not too easy to get the hang of, but its a matter of learning
the ropes a bit and getting around. This was my first time ever working with
it and I'm amazed by how quickly I'm picking it up.

You have to figure out how to get around to find what your looking for

To get access to the plugin you just do `ProtocolLibrary.getProtocolManager();`
and from there you can muck around with packets

For example here I replace all sounds with grass step sounds, now for some reason it only affects entity sounds and I 
think thats because other sounds are created by other packets not just this one but thats fine because I like this for 
April Fools Day

```java
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        // Remove all entity sounds and replace with a grass step sound
        manager.addPacketListener(new PacketAdapter(this, PacketType.Play.Server.NAMED_SOUND_EFFECT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                event.getPacket().getSoundEffects().write(0, Sound.BLOCK_GRASS_STEP);
            }
        });
```

At first ot looks realy complicated and a bit scary, I know it was for me

But its actually not

We're telling the game to listen for all packets of a certain type, namely, `PacketType.Play.Server.NAMED_SOUND_EFFECT` 
---- That is a `play` packet which means during normal gameplay (as opposed to logging in and out or other misc packets), 
originating from the `server` to the client and the packet is to conjure a sound effect by name.

The server already planned to send this to the client but we've intercepted this communication so we can mess around 
with it. We overwrite whatever the game code put as the sound effect and place there instead `BLOCK_GRASS_STEP`. We've 
essentially hi-jacked the communication between the server and client and both are none the wiser

This wiki covers 1.15 packets,
https://wiki.vg/index.php?title=Protocol&oldid=15901 
and this website 
https://minidigger.github.io/MiniMappingViewer/#/spigot/server/1.15.2 covers 1.15 code allowing us to peek inside the 
games code.

With both of these tools you can learn the packet has a field of type `SoundEffect` that holds a sound 
effect name. So we use ProtocolLib to fill-in the first soundeffect field of the packet (really the only one as we can 
see from those 2 links) `getSoundEffects().write(0, Sound.BLOCK_GRASS_STEP);`

This is really all you need to know for ProtocolLib, you just need those 2 links and basic understanding of 
manipulating, intercepting, changing, and creating packets as shown above. That's really it. You now have all the 
knowledge you need to succeed with ProtocolLib. Its just a matter of taking slow steps, being patient with packets not 
working right, and getting better at manipulating them and mastering getting around and using those 2 links so you can 
succeed with what your wanting to do.

------------

Normally I would fill out a lot of into here but this is an internal plugin
so maybe later.

License: Do whatever you want as long as you credit me back (Apache 2)

Contributions always welcome, fork and send pull request.

More plugins to come.
