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

```text
currentLevel - 1

- This file is both very new and somewhat incomplete. Mostly accurate but needs some revision.
