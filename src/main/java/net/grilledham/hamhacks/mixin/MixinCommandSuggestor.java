package net.grilledham.hamhacks.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import net.grilledham.hamhacks.command.CommandManager;
import net.grilledham.hamhacks.page.PageManager;
import net.grilledham.hamhacks.page.pages.Commands;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.CompletableFuture;

@Mixin(ChatInputSuggestor.class)
public abstract class MixinCommandSuggestor {
	
	@Shadow @Final TextFieldWidget textField;
	
	@Shadow @Nullable private ParseResults<CommandSource> parse;
	
	@Shadow @Nullable private ChatInputSuggestor.SuggestionWindow window;
	
	@Shadow boolean completingSuggestions;
	
	@Shadow @Nullable private CompletableFuture<Suggestions> pendingSuggestions;
	
	@Shadow public abstract void show(boolean narrateFirstSuggestion);
	
	@Inject(method = "refresh", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/StringReader;canRead()Z"), cancellable = true, remap = false, locals = LocalCapture.CAPTURE_FAILHARD)
	public void refresh(CallbackInfo ci, String string, StringReader stringReader) {
		String prefix = PageManager.getPage(Commands.class).getPrefix();
		int length = prefix.length();
		
		if (stringReader.canRead(length) && stringReader.getString().startsWith(prefix, stringReader.getCursor())) {
			stringReader.setCursor(stringReader.getCursor() + length);
			
			CommandDispatcher<CommandSource> commandDispatcher = CommandManager.getDispatcher();
			if (this.parse == null) {
				this.parse = commandDispatcher.parse(stringReader, CommandManager.getSource());
			}
			
			int cursor = this.textField.getCursor();
			int pos = 1;
			if (cursor >= pos && (this.window == null || !this.completingSuggestions)) {
				this.pendingSuggestions = commandDispatcher.getCompletionSuggestions(this.parse, cursor);
				this.pendingSuggestions.thenRun(() -> {
					if (this.pendingSuggestions.isDone()) {
						this.show(false);
					}
				});
			}
			ci.cancel();
		}
	}
}
