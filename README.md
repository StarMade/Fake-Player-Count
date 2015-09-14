# Fake-Player-Count
A small mod that allows you to fake the number of players on your server, using both unnamed and named players!

## Why?
I have released this mod as the developers of the game need to fix simple issues. This issue should never of existed and the only way for this bug to be fixed anytime soon is to force them to.

## Abuse
I did not release this just for everyone to fake their player counts, if you do this, people will most likely not use your server and you will probably get banned off server listing.

## Features
- Unnamed Players, these players are just added to the amount sent to server lists, they do not apear ingame
- Named Players, these players are a bit more buggy as they are based on fake network connections but this does mean they are full player and therefore apear ingame to all clients like any other user.
- Random pings on named players that randomly changes
- Random amount of extra named players on each server reboot

## Limits
- Players have no factions (i need to look into this as this will probably require fake factions)
- Players do not apear ingame (this is on purpose!)
- Some memory issues caused by the network data not being flushed (easy to fix but haven't got around to it)
- Players do not randomly join or leave (this is pretty easy to do)

## Config
unnamedPlayers = The amount of users that are just added to the player count.

namedPlayers = The amount of users that are actually in the game

randomPlayers = The amount of players than can be randomly added on a server restart (namedPlayers + rand(randomPlayers))

## Building
Mapping File: 20150907_154125-08092015-raw_min.smtmap

#### Method 1 - Easier Way
1) You will need to compile the source files

2) You will need to use the SMRemapper along with the above mapping file to remap the jar into the starmade mappings

3) You will need to place the libs the libs folder

4) You will want copy the files from your new jar into the StarMade.jar file (Do not copy the meta-inf!)

5) You will need to start the server via "java -Xmx4G -XX:MaxPermSize=512m -cp "StarMade.jar;lib/*" org.schema.game.common.Starter -server -gui" from now on

#### Method 2 - Case sensitivity issue
1) You will need to compile the source files

2) You will need to use the SMRemapper along with the above mapping file to remap the jar into the starmade mappings

3) You will need to place the libs and your new jar into the libs folder

4) You will want to remove any files from the StarMade.jar file that are in the new jar file

5) You will need to start the server via "java -Xmx4G -XX:MaxPermSize=512m -cp "StarMade.jar;lib/*" org.schema.game.common.Starter -server -gui" from now on
