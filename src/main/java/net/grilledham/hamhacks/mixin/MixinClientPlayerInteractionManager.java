package net.grilledham.hamhacks.mixin;

import net.grilledham.hamhacks.mixininterface.IClientPlayerInteractionManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class MixinClientPlayerInteractionManager implements IClientPlayerInteractionManager {
	
	@Shadow public abstract ActionResult interactItem(PlayerEntity player, Hand hand);
	
	@Shadow public abstract ActionResult interactBlock(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult);
	
	@Final
	@Shadow
	private MinecraftClient client;
	
	@Override
	public boolean hamHacks$rightClickBlock(BlockPos pos, Direction side, Vec3d hitVec) {
		if(interactBlock(client.player, Hand.MAIN_HAND, new BlockHitResult(hitVec, side, pos, false)).isAccepted()) {
			interactItem(client.player, Hand.MAIN_HAND);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void hamHacks$leftClickEntity(Entity entity) {
		attackEntity(client.player, entity);
	}
	
	@Shadow
	public abstract void attackEntity(PlayerEntity player, Entity target);
}
