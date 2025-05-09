package net.grilledham.hamhacks.modules.render;

import net.grilledham.hamhacks.animation.Animation;
import net.grilledham.hamhacks.animation.AnimationType;
import net.grilledham.hamhacks.modules.Category;
import net.grilledham.hamhacks.modules.Keybind;
import net.grilledham.hamhacks.modules.Module;
import net.grilledham.hamhacks.modules.ModuleManager;
import net.grilledham.hamhacks.setting.BoolSetting;
import net.grilledham.hamhacks.setting.ColorSetting;
import net.grilledham.hamhacks.setting.SettingCategory;
import net.grilledham.hamhacks.setting.StringSetting;
import net.grilledham.hamhacks.util.ChatUtil;
import net.grilledham.hamhacks.util.Color;
import net.grilledham.hamhacks.util.ConnectionUtil;
import net.grilledham.hamhacks.util.RenderUtil;
import net.grilledham.hamhacks.util.math.DirectionHelper;
import net.grilledham.hamhacks.util.math.Vec3;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HUD extends Module {
	
	private static List<Module> widthSortedModules;
	
	private static final HashMap<String, Float> cachedWidths = new HashMap<>();
	private static final HashMap<Module, String> lastTexts = new HashMap<>();
	private static boolean sort = true;
	
	private final SettingCategory APPEARANCE_CATEGORY = new SettingCategory("hamhacks.module.hud.category.appearance");
	
	private final BoolSetting animate = new BoolSetting("hamhacks.module.hud.animate", true, () -> true);
	
	private final ColorSetting accentColor = new ColorSetting("hamhacks.module.hud.accentColor", new Color(1, 1, 1, 1, true), () -> true);
	
	private final ColorSetting bgColor = new ColorSetting("hamhacks.module.hud.backgroundColor", new Color(0x80000000), () -> true);
	
	private final ColorSetting textColor = new ColorSetting("hamhacks.module.hud.textColor", Color.getWhite(), () -> true);
	
	private final SettingCategory ELEMENTS_CATEGORY = new SettingCategory("hamhacks.module.hud.category.elements");
	
	private final BoolSetting showLogo = new BoolSetting("hamhacks.module.hud.showLogo", true, () -> true);
	
	private final StringSetting logoText = new StringSetting("hamhacks.module.hud.logoText", "", showLogo::get, "&4&l&oHamHacks");
	
	private final BoolSetting showFPS = new BoolSetting("hamhacks.module.hud.showFps", true, () -> true);
	
	private final BoolSetting showPing = new BoolSetting("hamhacks.module.hud.showPing", true, () -> true);
	
	private final BoolSetting showTPS = new BoolSetting("hamhacks.module.hud.showTps", true, () -> true);
	
	private final BoolSetting showTimeSinceLastTick = new BoolSetting("hamhacks.module.hud.showTimeSinceLastTick", true, () -> true);
	
	private final BoolSetting showModules = new BoolSetting("hamhacks.module.hud.showModules", true, () -> true);
	
	private final BoolSetting showCoordinates = new BoolSetting("hamhacks.module.hud.showCoordinates", true, () -> true);
	
	private final BoolSetting showDirection = new BoolSetting("hamhacks.module.hud.showDirection", true, () -> true);
	
	private final BoolSetting directionYawPitch = new BoolSetting("hamhacks.module.hud.directionYawPitch", false, showDirection::get);
	
	public HUD() {
		super(Text.translatable("hamhacks.module.hud"), Category.RENDER, new Keybind(0));
		setEnabled(true);
		showModule.set(true);
		settingCategories.add(0, APPEARANCE_CATEGORY);
		APPEARANCE_CATEGORY.add(animate);
		APPEARANCE_CATEGORY.add(accentColor);
		APPEARANCE_CATEGORY.add(bgColor);
		APPEARANCE_CATEGORY.add(textColor);
		settingCategories.add(1, ELEMENTS_CATEGORY);
		ELEMENTS_CATEGORY.add(showLogo);
		ELEMENTS_CATEGORY.add(logoText);
		ELEMENTS_CATEGORY.add(showFPS);
		ELEMENTS_CATEGORY.add(showPing);
		ELEMENTS_CATEGORY.add(showTPS);
		ELEMENTS_CATEGORY.add(showTimeSinceLastTick);
		ELEMENTS_CATEGORY.add(showModules);
		ELEMENTS_CATEGORY.add(showCoordinates);
		ELEMENTS_CATEGORY.add(showDirection);
		ELEMENTS_CATEGORY.add(directionYawPitch);
	}
	
	@Override
	public String getHUDText() {
		return super.getHUDText() + " \u00a77" + ModuleManager.getModules().stream().filter(Module::isEnabled).toList().size() + "|" + ModuleManager.getModules().size();
	}
	
	private final List<Animation> animations = new ArrayList<>();
	
	public float leftHeight = 0;
	public float rightHeight = 0;
	
	public void render(DrawContext context, float tickDelta, TextRenderer textRenderer) {
		if(MinecraftClient.getInstance().getDebugHud().shouldShowDebugHud()) {
			leftHeight = 0;
			rightHeight = 0;
			return;
		}
		
		MatrixStack matrices = context.getMatrices();
		
		matrices.push();
		
		float[] textC = textColor.get().getHSB();
		
		int j = 0;
		int i = 0;
		float yAdd = 0;
		Animation animation = getAnimation(j++);
		if(animate.get()) {
			animation.set(showLogo.get() && isEnabled());
		} else {
			animation.setAbsolute(showLogo.get() && isEnabled());
		}
		if(animation.get() > 0) {
			float finalTextHue;
			if(textColor.get().getChroma()) {
				finalTextHue = (textC[0] - (i * 0.025f)) % 1f;
			} else {
				finalTextHue = textC[0];
			}
			int textColor = Color.toRGB(finalTextHue, textC[1], textC[2], textC[3]);
			String text = logoText.get().equals("") ? "§4§l§oHamHacks" : ChatUtil.format(logoText.get());
			float textX = 2;
			float textY = 2;
			matrices.push();
			matrices.translate(textX, textY, 0);
			matrices.scale(2, 2, 1);
			matrices.translate(-textX, -textY, 0);
			RenderUtil.drawString(context, text, textX - (int)(RenderUtil.getStringWidth(text) * (1 - animation.get())), textY, textColor, true);
			matrices.pop();
			yAdd += ((RenderUtil.getFontHeight() * 2) + 4) * animation.get();
			i++;
		}
		animation = getAnimation(j++);
		if(animate.get()) {
			animation.set(showFPS.get() && isEnabled());
		} else {
			animation.setAbsolute(showFPS.get() && isEnabled());
		}
		if(animation.get() > 0) {
			int fps = MinecraftClient.getInstance().getCurrentFps();
			yAdd += drawLeftAligned(context, textRenderer, fps + " fps", i, yAdd, animation);
			i++;
		}
		animation = getAnimation(j++);
		if(animate.get()) {
			animation.set(showPing.get() && isEnabled());
		} else {
			animation.setAbsolute(showPing.get() && isEnabled());
		}
		if(animation.get() > 0) {
			String ping = "0 ms";
			if(MinecraftClient.getInstance().player != null) {
				PlayerListEntry playerListEntry = MinecraftClient.getInstance().player.networkHandler.getPlayerListEntry(MinecraftClient.getInstance().player.getUuid());
				if(playerListEntry != null) {
					int latency = playerListEntry.getLatency();
					ServerInfo serverInfo = ConnectionUtil.getServerInfo();
					if(serverInfo != null) {
						ping = (latency <= 0 ? serverInfo.ping : latency) + " ms";
					} else {
						ping = latency + " ms";
					}
				}
			}
			yAdd += drawLeftAligned(context, textRenderer, ping, i, yAdd, animation);
			i++;
		}
		animation = getAnimation(j++);
		if(animate.get()) {
			animation.set(showTPS.get() && isEnabled());
		} else {
			animation.setAbsolute(showTPS.get() && isEnabled());
		}
		if(animation.get() > 0) {
			String tps = String.format("%.2f tps", ConnectionUtil.getTPS());
			yAdd += drawLeftAligned(context, textRenderer, tps, i, yAdd, animation);
			i++;
		}
		animation = getAnimation(j++);
		if(animate.get()) {
			animation.set(showTimeSinceLastTick.get() && isEnabled());
		} else {
			animation.setAbsolute(showTimeSinceLastTick.get() && isEnabled());
		}
		if(animation.get() > 0) {
			float timeSinceLastTick = ConnectionUtil.getTimeSinceLastTick() / 1000f;
			if(timeSinceLastTick >= 2) {
				String timeSinceLastTickString = String.format("Seconds Since Last Tick: %.2f", timeSinceLastTick);
				yAdd += drawLeftAligned(context, textRenderer, timeSinceLastTickString, i, yAdd, animation);
				i++;
			}
		}
		leftHeight = yAdd;
		
		yAdd = 0;
		int k = j;
		Map<Module, Animation> moduleAnimations = new HashMap<>();
		for(Module m : ModuleManager.getModules()) {
			animation = getAnimation(k++);
			if(animate.get()) {
				animation.set(m.isEnabled() && m.shouldShowModule() && showModules.get() && isEnabled());
			} else {
				animation.setAbsolute(m.isEnabled() && m.shouldShowModule() && showModules.get() && isEnabled());
			}
			moduleAnimations.put(m, animation);
		}
		if(widthSortedModules == null || sort) {
			sort = false;
			widthSortedModules = ModuleManager.getModules().stream().sorted((a, b) -> Float.compare(RenderUtil.getStringWidth(b.getHUDText()), RenderUtil.getStringWidth(a.getHUDText()))).toList();
		}
		for(Module m : widthSortedModules) {
			animation = moduleAnimations.get(m);
			j++;
			if(animation.get() > 0) {
				String hudText = m.getHUDText();
				if(!lastTexts.getOrDefault(m, "").equals(hudText)) {
					sort = true;
					lastTexts.put(m, hudText);
				}
				yAdd += drawRightAligned(context, textRenderer, hudText, i, yAdd, animation);
				i++;
			}
		}
		rightHeight = yAdd;
		
		yAdd = 0;
		animation = getAnimation(j++);
		if(animate.get()) {
			animation.set((showCoordinates.get() || showDirection.get()) && isEnabled());
		} else {
			animation.setAbsolute((showCoordinates.get() || showDirection.get()) && isEnabled());
		}
		if(animation.get() > 0 && MinecraftClient.getInstance().player != null) {
			Freecam freecam = ModuleManager.getModule(Freecam.class);
			Vec3 pos = freecam.isEnabled() ? new Vec3(freecam.pos) : new Vec3(MinecraftClient.getInstance().player.getPos());
			float yaw = freecam.isEnabled() ? freecam.yaw : MinecraftClient.getInstance().player.getYaw();
			float pitch = freecam.isEnabled() ? freecam.pitch : MinecraftClient.getInstance().player.getPitch();
			String coords = "";
			if(showCoordinates.get()) {
				coords += String.format("Coords: %.2f, %.2f, %.2f ", pos.getX(), pos.getY(), pos.getZ());
			}
			if(showDirection.get()) {
				if(!coords.equals("")) {
					coords += "| ";
				} else {
					coords += "Facing: ";
				}
				if(directionYawPitch.get()) {
					coords += String.format("%.2f, %.2f ", yaw, pitch);
				} else {
					coords += String.format("%s ", DirectionHelper.fromRotation(yaw));
				}
			}
			coords = coords.trim();
			yAdd += drawCoords(context, textRenderer, coords, i, yAdd, animation);
			i++;
		}
		
		matrices.pop();
		
		if(animate.get()) {
			animations.forEach(Animation::update);
		}
	}
	
	private float drawLeftAligned(DrawContext context, TextRenderer fontRenderer, String text, int i, float yAdd, Animation animation) {
		MatrixStack matrices = context.getMatrices();
		float[] barC = accentColor.get().getHSB();
		float[] bgC = bgColor.get().getHSB();
		float[] textC = textColor.get().getHSB();
		float finalBarHue;
		if(accentColor.get().getChroma()) {
			finalBarHue = (barC[0] - (i * 0.025f));
			if(finalBarHue < 0) finalBarHue += 1;
		} else {
			finalBarHue = barC[0];
		}
		float finalBGHue;
		if(bgColor.get().getChroma()) {
			finalBGHue = (bgC[0] - (i * 0.025f));
			if(finalBGHue < 0) finalBGHue += 1;
		} else {
			finalBGHue = bgC[0];
		}
		float finalTextHue;
		if(textColor.get().getChroma()) {
			finalTextHue = (textC[0] - (i * 0.025f));
			if(finalTextHue < 0) finalTextHue += 1;
		} else {
			finalTextHue = textC[0];
		}
		int barColor = Color.toRGB(finalBarHue, barC[1], barC[2], barC[3]);
		int bgColor = Color.toRGB(finalBGHue, bgC[1], bgC[2], bgC[3]);
		int textColor = Color.toRGB(finalTextHue, textC[1], textC[2], textC[3]);
		float textWidth = cachedWidths.getOrDefault(text, RenderUtil.getStringWidth(text));
		cachedWidths.put(text, textWidth);
		float textX = (float)(2 - ((textWidth + 7) * (1 - animation.get())));
		float textY = yAdd + 2;
		RenderUtil.preRender();
		RenderUtil.drawRect(matrices, textX - 2, textY - 2, textWidth + 4, RenderUtil.getFontHeight() + 2, bgColor);
		RenderUtil.drawRect(matrices, textX + textWidth + 2,  textY - 2, 3, RenderUtil.getFontHeight() + 2, barColor);
		RenderUtil.postRender();
		RenderUtil.drawString(context, text, textX, textY, textColor, true);
		return (float)((RenderUtil.getFontHeight() + 2) * animation.get());
	}
	
	private float drawRightAligned(DrawContext context, TextRenderer fontRenderer, String text, int i, float yAdd, Animation animation) {
		MatrixStack matrices = context.getMatrices();
		float[] barC = accentColor.get().getHSB();
		float[] bgC = bgColor.get().getHSB();
		float[] textC = textColor.get().getHSB();
		float finalBarHue;
		if(accentColor.get().getChroma()) {
			finalBarHue = (barC[0] - (i * 0.025f));
			if(finalBarHue < 0) finalBarHue += 1;
		} else {
			finalBarHue = barC[0];
		}
		float finalBGHue;
		if(bgColor.get().getChroma()) {
			finalBGHue = (bgC[0] - (i * 0.025f));
			if(finalBGHue < 0) finalBGHue += 1;
		} else {
			finalBGHue = bgC[0];
		}
		float finalTextHue;
		if(textColor.get().getChroma()) {
			finalTextHue = (textC[0] - (i * 0.025f));
			if(finalTextHue < 0) finalTextHue += 1;
		} else {
			finalTextHue = textC[0];
		}
		int barColor = Color.toRGB(finalBarHue, barC[1], barC[2], barC[3]);
		int bgColor = Color.toRGB(finalBGHue, bgC[1], bgC[2], bgC[3]);
		int textColor = Color.toRGB(finalTextHue, textC[1], textC[2], textC[3]);
		float textWidth = cachedWidths.getOrDefault(text, RenderUtil.getStringWidth(text));
		cachedWidths.put(text, textWidth);
		float textX = MinecraftClient.getInstance().getWindow().getScaledWidth() - textWidth - 2 + (float)((textWidth + 7 ) * (1 - animation.get()));
		float textY = yAdd + 2;
		RenderUtil.preRender();
		RenderUtil.drawRect(matrices, textX - 2, textY - 2, textWidth + 4, RenderUtil.getFontHeight() + 2, bgColor);
		RenderUtil.drawRect(matrices, textX - 5, textY - 2, 3, RenderUtil.getFontHeight() + 2, barColor);
		RenderUtil.postRender();
		RenderUtil.drawString(context, text, textX, textY, textColor, true);
		return (float)((RenderUtil.getFontHeight() + 2) * animation.get());
	}
	
	private float drawCoords(DrawContext context, TextRenderer fontRenderer, String text, int i, float yAdd, Animation animation) {
		float[] textC = textColor.get().getHSB();
		float finalTextHue;
		if(textColor.get().getChroma()) {
			finalTextHue = (textC[0] - (i * 0.025f));
			if(finalTextHue < 0) finalTextHue += 1;
		} else {
			finalTextHue = textC[0];
		}
		int textColor = Color.toRGB(finalTextHue, textC[1], textC[2], textC[3]);
		float textWidth = cachedWidths.getOrDefault(text, RenderUtil.getStringWidth(text));
		cachedWidths.put(text, textWidth);
		float textX = (float)(2 - ((textWidth + 7) * (1 - animation.get())));
		float textY = MinecraftClient.getInstance().getWindow().getScaledHeight() - yAdd - (RenderUtil.getFontHeight() + 2);
		RenderUtil.drawString(context, text, textX, textY, textColor, true);
		return -(float)((RenderUtil.getFontHeight() + 2) * animation.get());
	}
	
	private Animation getAnimation(int i) {
		while(animations.size() <= i) {
			animations.add(animations.size(), new Animation(AnimationType.EASE, 0.25));
		}
		return animations.get(i);
	}
	
	public void reloadResources() {
		widthSortedModules = ModuleManager.getModules().stream().sorted((a, b) -> Float.compare(RenderUtil.getStringWidth(b.getHUDText()), RenderUtil.getStringWidth(a.getHUDText()))).toList();
		cachedWidths.clear();
	}
}
