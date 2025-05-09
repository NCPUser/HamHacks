package net.grilledham.hamhacks.util;

public class Changelog {
	
	private static final String CHANGELOG = """
			Update 1.11.2
				- Updated to Minecraft 1.21.4
				- Improved modules:
					: Infinite Reach
					: Click TP
				- Fixed Velocity module
				- Buf fixes
			
			Update 1.11.1:
				- Updated to Minecraft 1.21.3
				- Removed module:
					: Bow Bomb
				- Bug fixes
			
			Update 1.11:
				- Updated to Minecraft 1.21
				- Added Target Mode to Freecam
				- Improved moduels:
					: Infinite Reach
					: Click TP
				- Improved automatic updates
				- Improved custom fonts
				- Bug fixes
			
			Update 1.10.4:
				- Updated to Minecraft 1.20.5/1.20.6
				- Added module:
					: Click TP
				- Added entity selector settings
				- Fixed Block Outline with shaders
			
			Update 1.10.3:
				- Updated to Minecraft 1.20.4
			
			Update 1.10.2:
				- Improved modules:
					: Block Outline
					: Infinite Reach
					: XRay
				- Improved performance
				- Fixed scrolling in menus
			
			Update 1.10.1:
				- Updated to Minecraft 1.20.2
				- Fixed Aimbot transations
				- Fixed module 1337
				- Fixed module sorting
			
			Update 1.10:
				- Added custom fonts
				- Added modules:
					: Forcefield
					: Long Jump
					: Air Jump
					: Criticals
					: W-Tap
					: Bow Aimbot
				- Added command:
					: Gamemode
				- Added more HUD info to some modules
				- Added command suggestions
				- Improved Auto Land option for Jetpack mode in the Fly module
				- Fixed XRay module when More Culling is installed
				- Fixed a crash when enabling some modules
				- Bug fixes
			
			Update 1.9.4:
				- Improved module XRay
				- Fixed compatibility with Sodium 0.5
				- Bug fixes
			
			Update 1.9.3:
				- Added modules:
					: Block Outline
					: Borderless Fullscreen
				- Added chroma speed
				- Changed overflow animation
				- Changed module screen open animation
				- Tweaked default ClickGUI colors
				- Fixed HUD chroma animation
				- Fixed category animation
			
			Update 1.9.2:
				- Fixed startup crash
			
			Update 1.9.1:
				- Added tool tip to "Ignore Update" button
				- Fixed Update button position
				- Fixed OpenGL error spam
			
			Update 1.9:
				- Updated to Minecraft 1.20
				- Added Mod Menu integration
				- Added module:
					: TitleBar
				- Improved text rendering
				- Improved modules:
					: Nametags
					: ESP
				- Tweaked UI
				- Bug fixes
			
			Update 1.8.5:
				- Added Click GUI command
				- Improved commands
				- Improved configs
				- Reverted to Sodium 0.4.10
				- Bug fixes
			
			Update 1.8.4:
				- Fixed compatibility with Sodium 0.4.11
				- Bug fixes
			
			Update 1.8.3:
				- Updated to Minecraft 1.19.4
				- Baritone is no longer included (download it separately)
				- Increased max teleport distance
				- Bug fixes
			
			Update 1.8.2:
				- Updated to Minecraft 1.19.3
				- Added module:
					: Velocity
				- Removed module:
					: AntiBan (Use NoChatReports instead: https://github.com/Aizistral-Studios/No-Chat-Reports)
				- Added setting to Nametags module:
					: Scale with Zoom
				- Bug fixes
			
			Update 1.8.1:
				- Rewrote animations
				- Improved XRay
				- Fixed wrong widths in HUD
			
			Update 1.8:
				- Added setting profiles
				- Added pages to the Click GUI
				- Improved notification animation
				- Allow for easier addon mods
				- Moved some modules into pages
				- Added setting categories
				- Tweaked default colors
				- Added anti-spam to Chat module
				- Added modules:
					: Teleport
					: Packet Fly
					: XRay
					: 1337
					: Infinite Reach
				- Added page:
					: Baritone
				- Improved Boat Fly
				- Added server joiner (joins when there is an open space)
				- Added baritone
				- Rewrote settings (again)
				- Improved Click GUI performance
				- Split HUD module into smaller modules
				- Reduced max range values for Killaura and Reach
				- Improved HUD performance
				- Bug fixes
			
			Update 1.7.2:
				- Improved vanilla fly bypass
				- Keybinds can now be bound to a combinations of keys
				- Added more customizability for chat status
				- Added coordinates/direction to HUD module
				- Increased max height of categories
				- Bug fixes
			
			Update 1.7.1:
				- Added more color options to the Click GUI
				- Tweaked some default Click GUI colors
				- Improved the changelog screens
				- Fixed potion effects being behind module list
				- Bug fixes
			
			Update 1.7:
				- Rewrote settings
				- Changed how the Click GUI looks
				- Changed setting in No Fall module:
					: Momentum -> Fly Spoof
				- Added modules:
					: Zoom
					: No Telemetry
					: Freecam
					: Notifications
					: ESP
					: Nametags
					: Auto Totem
				- Added settings to Tracers module:
					: Draw Stem
					: End Position
				- Improved modules:
					: Encase
					: Crystal Aura
					: Trap
				- Improved secure server warning
				- Improved No Fall packet mode
				- Changed some default settings
				- Added module-specific HUD text
				- Added module sorting (currently only alphabetic)
				- Added animations
				- Fixed high speed values in Fly
				- Fixed a crash when offline
				- Fixed some modules causing a crash
				- Bug fixes
			
			Update 1.6.1:
				- Added module:
					: Timer
				- Fixed Tracers module
				- Improved Sprint module
				- Bug fixes
			
			Update 1.6:
				- Added auto-updater
				- Removed dependency on fabric-api
				- Updated to 1.19.1
				- Added modules:
					: Anti Ban
					: Chat
					: Name Hider
				- Changed translation keys
				- Bug fixes
			
			Update 1.5.3:
				- Added module:
					: Fullbright
				- Bug fixes
			
			Update 1.5.2:
				- Re-added commands after 1.19 broke them
				- Added a mod icon
				- Bug fixes
			
			Update 1.5.1:
				- Updated to 1.19
				- Bug fixes
			
			Update 1.5:
				- Added translations to module settings
				- Added config versions
				- Added more customizations to the Click GUI
				- Added scrolling to the Click GUI
				- Added commands
				- Added module:
					: Instant Kill Bow
				- Added settings to Speed module:
					: In Air Multiplier
					: On Ice Multiplier
					: In Tunnel Multiplier
					: In Water Multiplier
				- Added settings to No Fall module:
					: Mode
				- Added option to "Mode" in Fly module:
					: Jetpack
				- Added descriptions and tooltips
				- Bug fixes
			
			Update 1.4.2:
				- Added the ability for modules to force-disable other modules
				- Removed unnecessary settings in Fly
				- Added settings to Fly module:
					: Smooth Movement
				- Split "Range" into "Block Range" (vanilla: 4.5) and "Entity Range" (vanilla: 3.0) in Reach module
				- Added options to show ping, tps, and last server response time in the HUD
				- Added Full Changelog screen
				- Fixed translations not being used and updated translations
				- Bug fixes
			
			Update 1.4.1:
				- Updated to 1.18.2
				- Improved vanilla fly bypass
				- Improved server-side rotations
				- Added module:
					: Reach
				- Bug fixes
			
			Update 1.4:
				- Overhauled event system
				- Overhauled GUI
				- Overhauled settings
				- Added changelog button to title screen
				- Bug fixes
			
			Update 1.3.3:
				- Fixed incompatibility with iris shaders
			
			Update 1.3.2:
				- Updated to 1.18.1
				- Added settings to HUD module:
					: Held Item Scale
					: Shield Height
					: Fire Height
					: Model Bobbing Only
					: No Hurt Cam
				- Bug fixes
			
			Update 1.3.1:
				- Fixed incompatibility with multiconnect
				- Fixed color settings not saving chroma
			
			Update 1.3:
				- Updated to 1.18
			
			Update 1.2:
				- Bug fixes
			
			Update 1.1.3:
				- Bug fixes
			
			Update 1.1.2:
				- Added modules:
					: Sprint
					: Step
				- Updated modules:
					: Speed
					: Fly
				- Bug fixes
			
			Update 1.1.1:
				- Added modules:
					: Click GUI
					: HUD
				- Added a new way to change keybinds and settings (Click GUI)
			
			Update 1.1:
				- Switched handling of keybinds from Minecraft's built in keybinds
					: There is currently no way to change the keys besides editing the config
				- Added a config to save keybinds and settings
				- Added comments to frequently used methods
				- Updated dependencies
				- Renamed "Smooth Aim" to "Aimbot"
				- Bug fixes
			
			Update 1.0:
				- Added modules:
					: Fly
					: Trap
					: Encase
					: Crystal Aura
					: Scroll Clicker
					: No Fall
					: Smooth Aim
					: Boat Fly
					: Auto Elytra
					: Speed
					: Jesus""";
	
	/**
	 * Used to get the entire changelog
	 * @return The entire changelog
	 */
	public static String getChangelog() {
		return CHANGELOG;
	}
	
	/**
	 * Used to get the latest update from the changelog
	 * @return The latest update's changelog
	 */
	public static String getLatest() {
		return CHANGELOG.subSequence(0, CHANGELOG.indexOf("\n\n")).toString();
	}
}
