package net.grilledham.hamhacks.mixin;

import net.grilledham.hamhacks.event.EventRender2D;
import net.grilledham.hamhacks.event.EventRender3D;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public class MixinGameRenderer implements SynchronousResourceReloader, AutoCloseable {
	
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "net.minecraft.client.gui.hud.InGameHud.render(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
	public void render2DEvent(InGameHud instance, MatrixStack matrices, float tickDelta) {
		instance.render(matrices, tickDelta);
		EventRender2D event = new EventRender2D(matrices, tickDelta);
		event.call();
	}
	
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "net.minecraft.client.render.GameRenderer.renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V"))
	public void render3DEvent(GameRenderer instance, float tickDelta, long limitTime, MatrixStack matrices) {
		instance.renderWorld(tickDelta, limitTime, matrices);
		EventRender3D event = new EventRender3D(tickDelta, limitTime, matrices);
		event.call();
	}
	
	@Shadow
	public void close() {}
	
	@Shadow
	public void reload(ResourceManager manager) {}
}