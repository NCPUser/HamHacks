package net.grilledham.hamhacks.mixin;

import net.grilledham.hamhacks.modules.ModuleManager;
import net.grilledham.hamhacks.modules.movement.BoatFly;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
	
	private MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Inject(method = "shouldDismount", at = @At("HEAD"), cancellable = true)
	public void shouldDismount(CallbackInfoReturnable<Boolean> cir) {
		BoatFly boatFly = ModuleManager.getModule(BoatFly.class);
		if(boatFly.isEnabled()) {
			cir.setReturnValue(boatFly.shouldDismount());
		}
	}
}