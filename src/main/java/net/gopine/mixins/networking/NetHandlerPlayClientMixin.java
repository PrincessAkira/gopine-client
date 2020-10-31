package net.gopine.mixins.networking;

import net.gopine.events.impl.client.EventChatReceived;
import net.gopine.events.impl.client.EventClientChatReceived;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S02PacketChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * NetHandlerPlayerClient mixin class used to add events.
 * @author MatthewTGM | MatthewTGM#4058
 * @since b0.1
 */
@Mixin(NetHandlerPlayClient.class)
public class NetHandlerPlayClientMixin {

    /**
     * Calls the following events:
     * {@link EventChatReceived}
     * @param packetIn the packet being received
     * @param callbackInfo unused
     * @author MatthewTGM
     * @since b1.0
     */
    @Inject(method = "handleChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = At.Shift.AFTER), cancellable = true)
    private void onChatPacket(S02PacketChat packetIn, CallbackInfo callbackInfo) {
        new EventChatReceived(packetIn.getChatComponent()).call();
    }

    /**
     * Calls the following events:
     * {@link EventClientChatReceived}
     * @param callbackInfo unused
     * @param packetIn chat packet received
     * @author MatthewTGM | MatthewTGM#4058
     * **/
    @Inject(method = "handleChat", at = @At("HEAD"))
    private void handleChat(S02PacketChat packetIn, CallbackInfo callbackInfo) {
        new EventClientChatReceived(packetIn.getType(), packetIn.getChatComponent()).call();
    }

}
