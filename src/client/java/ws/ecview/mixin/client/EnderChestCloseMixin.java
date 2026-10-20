package ws.ecview.mixin.client;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ws.ecview.feature.EnderChestTracker;

@Mixin(ClientPlayerEntity.class)
public class EnderChestCloseMixin {

    @Inject(method = "closeScreen", at = @At("HEAD"))
    private void enderchestview$captureEnderChest(CallbackInfo ci) {
        EnderChestTracker.captureOnClose((ClientPlayerEntity) (Object) this);
    }
}
