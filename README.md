flyingblocksapi
===============

> API for developers to create flying and moving blocks in [Minecraft](http://minecraft.net) using [Bukkit](http://bukkit.org).

### Some Impressions

<img alt="Huh, a nice-looking image should be here :(" title="A waving flag" src="http://i.imgur.com/skxCTsf.gif" width=140 height=94>

### Used in...

... no plugin yet :( You can write me a PM on [Bukkit](https://forums.bukkit.org/members/ase34.90684193/), on [Reddit](http://www.reddit.com/user/ase34/), send me an [email](asehrm34@gmail.com), or make a pull request if you want your plugin to appear here.

### Important Links

* BukkitDev Project Page - http://dev.bukkit.org/bukkit-plugins/flyingblocksapi/
* Javadocs - http://ase34.github.io/flyingblocksapi/javadocs

Overview
--------

* [Commands & Permissions](#commands--permissions)
* [Building](#building)
* [Underlying Technique](#underlying-technique)
* [API Usage](#api-usage)
* [Credits & Special Thanks](#credits--special-thanks)
* [License](#license)
* [Language Notice](#language-notice)

Commands & Permissions
----------------------

* */flyingblocks-removeall* - Removes all flying blocks from the worlds. Good for debugging.
  `flyingblocks.removeall` (OP by default)
* */flyingblocks-examples* - Lists all commands of the examples, check them out! :P *Note:* Most of the examples depend on world ticks (day/night cycle needs to be running)
  `flyingblocks.examples` (OP by default)

Building
--------

This plugin uses [Maven](http://maven.apache.org/) as build tool. Just [download](https://github.com/ase34/flyingblocksapi/archive/master.zip) this repositotry's zipfile or clone it via Git using `git clone http://github.com/ase34/flyingblocksapi.git`, then invoke `mvn clean package` to build a new JAR file.

Underlying Technique
--------------------

This technique is heavily inspired by [The Holographic Displays](http://www.youtube.com/watch?v=q1B19JvX5TE) of [Asdjke](http://www.youtube.com/user/AsdjkeAndBro). Without his ideas and research, this plugin would likely never been made. Thanks. This should give you an abstract explanation how this plugin achieves its behavior:

* First, a *Wither Skull* will be spawned about 100 blocks above the block's desired spawnpoint.
  That skull is not affected by gravity at the client, so there is *no* constant downfall-cancellation (for example, using packets) needed.
* Then, a *Horse* is attached to the skull with an age of -4057200. The extremely low age causes the client to render the horse as invisible, but also setting the position of their passengers around 100 block below the horse.
* Finally, a *Falling Sand Block* with a different block id is attached to the horse. Because of the age of the horse and the height of the skull, the block appears a the exact spawn position. The falling block is not affected by gravity on the client because it is an (indirect) passenger of the skull.

API Usage
---------

### Adding to build path

In order to use the API, you need to add *flyingblocksapi* to your plugin's build path. If you are not using Maven or comparable, you actually do the same thing like with your `craftbukkit.jar` or `bukkit.jar`. For Maven users, I provide a public repository on this GitHub project page. Just add these bits of markup in your pom.xml:

```
...
<repositories>
  <!-- Bukkit repo -->
  ...
  <repository>
    <id>ase34-repo</id>
    <url>http://ase34.github.io/maven-repo</url>
  </repository>
</repositories>
...
<dependencies>
  <!-- more depenencies -->
  ...
  <dependency>
    <groupId>de.ase34</groupId>
    <artifactId>flyingblocksapi</artifactId>
    <version>LATEST</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
...
```

Then, don't forget to add `depend: [flyingblocksapi]` to your *plugin.yml*.

### Implement own movement logic

Using the API as a developer is quite easy, he/she just has to extend the class [de.ase34.flyingblocksapi.FlyingBlock](http://ase34.github.io/flyingblocksapi/javadocs/de/ase34/flyingblocksapi/FlyingBlock.html) and override the [onTick()](http://ase34.github.io/flyingblocksapi/javadocs/de/ase34/flyingblocksapi/FlyingBlock.html#onTick()) method by his own movement logic. In the method body, some special rules need to be follown:

* Due to a misimplementation/bug/whatever **`getBukkitEntity().teleport(Location)` does fail for entities** with a passenger attached. *flyingblocksapi* provides an **alternative by invoking [setLocation(Location)](http://ase34.github.io/flyingblocksapi/javadocs/de/ase34/flyingblocksapi/FlyingBlock.html#setLocation(org.bukkit.Location))**. You might use [setBlockLocation(Location)](http://ase34.github.io/flyingblocksapi/javadocs/de/ase34/flyingblocksapi/FlyingBlock.html#setBlockLocation(org.bukkit.Location)) to **set the location of the block's center** and not of the skull (If you are *not* using default parameters for horse age and height offset, this might not work).
* In order to **set the velocity/direction/movement of the skull** using the Bukkit API, please **use `setVelocity(Vector)` and *not* `setDirection(Vector)`**.
* Please keep in mind that you **are modifying the skull entity, not the falling block**. The skull is normally located 100 blocks higher than the appearance of the block. (See [getHeightOffset()](http://ase34.github.io/flyingblocksapi/javadocs/de/ase34/flyingblocksapi/FlyingBlock.html#getHeightOffset()))
* The offset is calibrated so that the y-coordinate of the skull minus the default height offset ([getHeightOffset()](http://ase34.github.io/flyingblocksapi/javadocs/de/ase34/flyingblocksapi/FlyingBlock.html#getHeightOffset())) is equal to the y-coordinate of the **center** and *not* the downfacing side of the block. So, a block spawned at `(10.5, 60.5, 23.5)` will perfectly align with the grid of the 'normal' blocks.

To spawn the prepared flying block, just call [spawn(Location)](http://ase34.github.io/flyingblocksapi/javadocs/de/ase34/flyingblocksapi/FlyingBlock.html#spawn(org.bukkit.Location)), and you're done! Then the [onTick()](http://ase34.github.io/flyingblocksapi/javadocs/de/ase34/flyingblocksapi/FlyingBlock.html#onTick()) gets then called once every tick. For more information about the methods, please go to the [Javadoc](http://ase34.github.io/flyingblocksapi/javadocs/de/ase34/flyingblocksapi/FlyingBlock.html) page.

### Examples

Sample codes are available in the [`de.ase34.flyingblocksapi.commands.examples` package](tree/master/src/main/java/de/ase34/flyingblocksapi/commands/examples). Many of the examples are using anonymous or nested classes. Here's a small selection:

* [RisingBlockCommandExecutor.java](src/main/java/de/ase34/flyingblocksapi/commands/examples/RisingBlockCommandExecutor.java) - Simple example that spawns a flying block which rises wiht constant velocity in the sky
* [SineWaveBlockCommandExecutor.java](src/main/java/de/ase34/flyingblocksapi/commands/examples/SineWaveBlockCommandExecutor.java) - Complex example code for a flying block moving up and down in a sine-wave-style

To see these examples in-game, use the `/flyingblocks-examples` command. 

### Other notes for the developer

The developer has to take certain precautions when using this plugin:

* Flying blocks get removed during
  * Disablings of *flyingblocksapi* (reloads/stops of the server) in all worlds.
  * [WorldUnloadEvents](http://jd.bukkit.org/dev/apidocs/org/bukkit/event/world/WorldUnloadEvent.html) in the unloaded world.
  * When the last player leaves a world in the now empty world.
  
  To make them persistent, the developer needs to save the blocks (in a file for example) and to respawn them during the opposite events of the above-mentioned (`onEnable()`, WorldLoadEvent, PlayerJoinEvent). 
  
Credits & Special Thanks
--------------------------

* [Asdjke](http://www.youtube.com/user/AsdjkeAndBro) - Underlying technique (<http://www.youtube.com/watch?v=q1B19JvX5TE>) Thanks!
* [Jogy34](https://forums.bukkit.org/members/jogy34.90565555/) - code to spawn custom entites (<https://forums.bukkit.org/threads/tutorial-1-7-creating-a-custom-entity.212849/>) Thanks!
* [ase34](https://forums.bukkit.org/members/ase34.90684193/) - Plugin design and core development

License
-------

The code is licensed under the terms of the GNU General Public License Version 3. See [LICENSE](LICENSE) for full license text.

> Copyright (C) 2014 ase34
> 
> This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
> 
> This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
> 
> You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.

Language Notice
-------------------

As I'm not a native english speaker, I appreciate every suggestion concerning the written words of this paper, of the comments in the code, or of the javadoc (spelling, grammar, phrasing, language style, ...). You can write me a PM on [Bukkit](https://forums.bukkit.org/members/ase34.90684193/), on [Reddit](http://www.reddit.com/user/ase34/), send me an [email](asehrm34@gmail.com), or make a pull request. Thanks for your tolerance.
