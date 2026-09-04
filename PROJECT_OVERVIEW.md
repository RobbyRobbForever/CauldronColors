# CauldronColors

## What This Mod Does

CauldronColors is a client-side Minecraft mod that adds colored-water cauldron behavior, including copper-origin cauldrons, colored-water appearances, dye interactions, crafting recipes, and related item behavior.

The project is experimental and educational. The developer is learning Java, Minecraft modding, IntelliJ IDEA, Git, and GitHub while building it with substantial AI assistance.

## Technical Information

- Project path: `C:\Projects\CauldronColors`
- Minecraft: `1.21.11`
- Mod loader: Fabric Loader `0.19.3`
- Fabric API: `0.141.6+1.21.11`
- Java: `21`
- Mappings: Official Mojang mappings
- Mod ID: `cauldroncolors`

## Current Build Status

- The project builds successfully.
- The client launches successfully with `runClient`.

## Project Structure

- `src/main/java/` — Java source code
- `src/main/resources/` — textures, models, blockstates, recipes, loot tables, language files, and mod metadata
- `src/main/resources/assets/cauldroncolors/` — client-side assets
- `src/main/resources/data/cauldroncolors/` — recipes, loot tables, and other data files
- `gradle/` — Gradle wrapper files
- `.github/` — GitHub-related project files
- `run/` — local development runtime files; normally ignored by Git

## Current Architecture

The mod uses custom cauldron-related blocks and resource files to preserve copper appearance while supporting colored water.

Important behavior includes:

- Colored-water blockstates can preserve copper appearance when `copper_origin=true`.
- Copper-origin variants use custom models:
    - `cauldroncolors:block/copper_cauldron_level1`
    - `cauldroncolors:block/copper_cauldron_level2`
    - `cauldroncolors:block/copper_cauldron_level3`
- Normal colored-water variants use vanilla water-cauldron models.
- Copper cauldrons accept dye while remaining copper-looking.
- Egg dyeing works when the cauldron is directly above a block tagged `minecraft:campfires`.
- Extracting water from a full dyed-water cauldron preserves copper origin.
- Extracting water from a full copper cauldron preserves copper.

## Completed Work

- Colored-water blockstates preserve copper appearance when `copper_origin=true`.
- Copper-origin level 1, level 2, and level 3 models are used correctly.
- Normal colored-water variants use vanilla water-cauldron models.
- Copper cauldrons accept dye and retain their copper appearance.
- Egg dyeing works above blocks tagged `minecraft:campfires`.
- Bucket extraction from full dyed-water cauldrons preserves copper origin.
- Bucket extraction from full copper cauldrons preserves copper.
- Water bucket and glass-bottle behavior has been consolidated into shared handlers.
- All core copper_cauldron interactions function as intended.

## Recent Code Changes

`CauldronInteractions.java` now registers a shared water-bucket handler named `emptyWaterCauldron`.

The handler:

- returns a copper cauldron when `copper_origin=true`;
- returns a vanilla cauldron otherwise.

`CauldronInteractions.java` also registers a shared water glass-bottle handler named `fillWaterBottleFromWaterCauldron`.

The handler:

- removes one water level at a time;
- preserves copper origin;
- preserves copper when the final water level is removed.

The copper-specific bottle handler was changed from setting the water level directly to `0` to reducing the current level by one:

## Future Plans

1 - Copper cauldrons accepting lava

A copper cauldron accepts a lava bucket.
The lava visually occupies the copper cauldron.
The lava begins a delayed destruction process.
Flames eventually appear above the cauldron.
Shortly afterward:
The copper cauldron is destroyed.
A lava source block replaces it.
Warning flames disappear.
If lava is removed before destruction:
The cauldron becomes an empty copper cauldron.
Warning flames stop.
No lava source block is created.
Iron cauldron lava behavior must remain unchanged.

- Recommended state design:

BooleanProperty LAVA
BooleanProperty LAVA_WARNING

LAVA=false, LAVA_WARNING=false
Normal empty/water copper behavior.

LAVA=true, LAVA_WARNING=false
Lava-filled copper cauldron, warning period not started.

LAVA=true, LAVA_WARNING=true
Lava-filled copper cauldron with warning flames active.

Keep the existing LEVEL property for water levels. A lava-filled state should not be treated as dyed water.
Potentially use level=0 while lava is present, provided all lava interactions explicitly check LAVA=true.
The isFull method may need to treat lava as full:

return state.getValue(LAVA)
|| state.getValue(LEVEL) == 3;
This should be confirmed against the interaction code before changing it.

2 - Delayed destruction
Use scheduled block ticks rather than an entity or global timer.

Lava bucket fills the copper cauldron.
Schedule a destruction tick using a semi-random delay.
At the warning stage:
Set LAVA_WARNING=true.
Begin client-side flame particles.
Schedule the final destruction tick.
Before destroying, verify:
The block is still the copper cauldron.
LAVA=true.
The warning state is still valid.
Replace the cauldron with a lava source block.
Do not drop the copper cauldron.
Every scheduled tick must safely do nothing if lava was removed or the block was replaced.

Do not restart the warning timer on every tick.

3 - Removing lava
Add a copper-specific empty-bucket interaction.
When lava is removed before destruction.

LAVA=false
LAVA_WARNING=false
LEVEL=0
Return an empty copper cauldron and stop the warning effect.
Ensure the vanilla iron-cauldron lava bucket and empty-bucket handlers are not modified.

4 - Lava visuals
Add blockstate variants for the lava states.
The exact model can be simple initially, but it must not reuse a water model accidentally.

Possible variants:
level=0,lava=true,lava_warning=false
level=0,lava=true,lava_warning=true
The warning flame effect should be client-side and localized above the cauldron.

It must stop when:
Lava is removed.
The cauldron is destroyed.
The block changes to another state.
The chunk unloads or reloads.

5 - Lava sound
The copper lava-bucket interaction should play a more dramatic sound than the vanilla lava-bucket sound.

Possible approach:
Reuse an existing lava sound event.
Increase volume slightly.
Raise pitch modestly.
Do not call the vanilla lava handler as well, or two sounds may play.
Keep the sound change limited to copper cauldrons.

6 - Campfire Smoke Suppression

Empty cauldron above a campfire: smoke remains.
Empty cauldron above a soul campfire: smoke remains.
Any amount of water or dye above a campfire: suppress smoke.
Any amount of water or dye above a soul campfire: suppress smoke.
Lava-filled copper cauldron: suppress smoke in lieu of flame particles.
All other campfires throughout the game: unchanged.
The suppression should check only the block directly above the campfire.

Likely affected blocks:
Vanilla water cauldron with level > 0
colored_water_cauldron with level > 0
Copper-origin colored water with copper_origin=true and level > 0

Recommended implementation:
Inspect the existing mixin configuration first.
Use a narrow client-side mixin targeting campfire smoke generation.
Avoid replacing all campfire particle behavior globally.
Do not modify vanilla campfire block classes directly.

Important Edge Cases
Breaking a lava-filled copper cauldron before ignition should NOT create lava.
Breaking a lava-filled copper cauldron before ignition should NOT drop a cauldron.
The scheduled destruction tick must verify the current block state.
Removing lava must invalidate the pending destruction logically, even if the scheduled tick still executes.
Chunk unload/reload must not create lava from a stale scheduled event.
Explosion or command replacement must not leave a permanent warning state.
The final replacement must be a lava source block, not flowing lava.
Copper cauldrons must retain their normal mining and drop behavior.
Dyed copper cauldrons must retain their existing copper_origin behavior.
Iron cauldron lava behavior must remain untouched.
Lava behavior for non-copper cauldrons remains deferred.

Recommended Next Session Order
Inspect:
CauldronInteractions.java
CopperCauldronBlock.java
CauldronColors.java
Existing mixin configuration
Existing copper blockstate and model files
Implement only lava state properties and registration.
Build and launch.
Test lava bucket insertion and removal.
Add scheduled destruction.
Build and test delayed destruction.
Add lava models and particles.
Add the dramatic sound.
Inspect and implement campfire smoke suppression.
Build and run a focused regression test covering:
Empty copper cauldron
Water copper cauldron
Dyed copper cauldron
Lava copper cauldron
Lava removal
Campfire smoke
Soul campfire smoke
Iron cauldron lava behavior
