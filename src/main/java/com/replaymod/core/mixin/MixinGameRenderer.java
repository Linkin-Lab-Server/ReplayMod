//#if MC>=11400
package com.replaymod.core.mixin;

import com.replaymod.core.events.PostRenderWorldCallback;
import com.replaymod.core.events.PreRenderHandCallback;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Inject(
            method = "renderCenter",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z"
            )
    )
    private void postRenderWorld(float partialTicks, long nanoTime, CallbackInfo ci) {
        PostRenderWorldCallback.EVENT.invoker().postRenderWorld();
    }

    @Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
    private void preRenderHand(Camera camera, float partialTicks, CallbackInfo ci) {
        if (PreRenderHandCallback.EVENT.invoker().preRenderHand()) {
            ci.cancel();
        }
    }
}
//#endif
