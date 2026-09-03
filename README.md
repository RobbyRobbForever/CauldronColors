# CauldronColors

CauldronColors is a client-side Minecraft Fabric mod that adds colored-water
cauldron behavior. It includes copper-origin cauldrons, colored-water
appearances, dye interactions, crafting recipes, and related item behavior.

The project is experimental and currently under development.

## Features

- Colored water in cauldrons
- Copper cauldrons that preserve their copper appearance
- Dye interactions with cauldrons
- Dyeing eggs when the cauldron is above a campfire
- Crafting recipes for copper cauldrons
- Vanilla style water bucket and glass bottle interactions
- Copper-origin behavior preserved while water is added or removed

## Requirements

- Minecraft: `1.21.11`
- Fabric Loader: `0.19.3`
- Fabric API: `0.141.6+1.21.11`
- Java: `21`

## Installation

This project is currently in development and may be unstable and/or contain bugs. To use a development build:

1. Install Java 21.
2. Clone or download this repository.
3. Open the project in IntelliJ IDEA.
4. Allow Gradle to import the project.
5. Run the `runClient` Gradle task.

## Development Setup

The project is developed using:

- IntelliJ IDEA
- Java 21
- Gradle
- Fabric Loader
- Fabric API
- Official Mojang mappings

The project builds successfully and currently launches successfully using
the `runClient` task.

## Project Structure

- `src/main/java/` — Java source code
- `src/main/resources/` — textures, models, blockstates, recipes, loot tables, language files, and mod metadata
- `src/main/resources/assets/cauldroncolors/` — client-side assets
- `src/main/resources/data/cauldroncolors/` — recipes, loot tables, and other data files
- `gradle/` — Gradle wrapper files
- `.github/` — GitHub-related project files

## Project Status

CauldronColors is experimental and actively being developed.

The current build includes colored-water cauldrons, copper-origin cauldron
behavior, dye interactions, egg dyeing, and water bucket and glass bottle
interactions.

## License

This project is available under the CC0 1.0 Universal license. See the
`LICENSE` file for details.
