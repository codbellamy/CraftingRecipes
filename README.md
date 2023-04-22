# CraftingRecipes
## Features
- Custom crafting recipes for mob spawners and spawner eggs
- Toggle spawners by interacting with them
- Tweak to experience gained (+2.0x)
## Installation
- Place Crafting.jar into 'plugins' folder
## Permissions
- cr.* Gives access to all CraftingRecipe permissions
  - cr.craft.*: true
  - cr.mine: true
  - cr.exp: false
  - cr.trusted: true
  - cr.create: true
- cr.craft.* Gives a player permission to all crafting recipes
  - cr.craft.egg: true
  - cr.craft.spawner: true
- cr.craft.egg Gives a player permission to craft a spawn egg
- cr.craft.spawner Gives a player permission to craft a blank spawner
- cr.exp 2x xp multiplier
- cr.create Gives a player permission to use a spawn egg on a spawner
- cr.trusted Gives a player a mark of trust
- cr.mine Allows a player to mine spawners normally
## Development
Depends on Maven to build. Ensure that spigot API is included as a Module.