# JayBlock
Minecraft bukkit/spigot plugin for blocking or preventing the use of specified commands. Tested and working in 1.8.x, and built with the spigot 1.8.8 API.

## Download
Download the plugin [here](https://github.com/Cannicide/JayBlock/raw/master/out/artifacts/JayBlock_jar/JayBlock.jar).

## Features
JayBlock's main feature is the ability to block specific commands. Though this should typically be managed through permissions (LuckPerms, for instance), JayBlock makes it easier to block specific commands, and even allows you to block specific arguments and prefixes to commands, granting you more control over how you want your commands to be used than permissions may provide.

For example, say you want to allow users to use vanilla minecraft's `/give` command, but you don't want them to use the @a selector. By specifying `/minecraft:give @a` and/or `/give @a` in the config, JayBlock allows you to do exactly that! You can get far more specific than this, for example, if you wanted to allow the @a selector but disable the ability to use selector attributes/filters such as radius. This could be done by specifying `/give @a[` in the config, preventing players from using any attributes such as `r=` on their @a selector.

Another feature of JayBlock is "nonexist", which allows you to customize the message sent when a player enters an unknown/invalid command. The "nonexist" feature can be enabled/disabled, and the message itself can be customized. The messages for the `/jayblock` command and for when a player tries to use a blocked command can also be customized. All customizable messages support minecraft color codes such as `&6` (although other formatting codes such as `&l` are not supported yet), and some messages also support custom variables (such as the name of the command that was blocked).

One feature in the config is "allow-unregistered", which determines whether or not non-plugin/unrecognized commands can be used. Disabling this forces players to use the "bukkit:" or "minecraft:" prefixes to use those respective commands, if they aren't blocked in your config, because Bukkit does not recognize those commands as registered plugin commands. Enabling this feature indirectly disables "nonexist", because differentiating between nonexistent commands and unregistered commands becomes impossible. However, if your server contains unregistered or hacky commands that need to be blocked for some reason, this feature must be enabled in order to block those commands, forcing the default unknown command message to be used as opposed to JayBlock's customizable version.

JayBlock also comes with three different permissions: `jayblock.reload`, `jayblock.bypass`, and `jayblock.nonexist.bypass`. The reload permission gives players the ability to reload the JayBlock config, and is intended for admins only. The bypass permission allows players to use blocked commands (bypasses the blocked command filter), and is also intended for admins and senior staff only. The nonexist bypass permission allows players to bypass the "nonexist" feature as well as the consequences of "allow-unregistered" (only if the player has BOTH nonexist-bypass AND bypass), allowing players to use minecraft and bukkit commands without specifying the "bukkit:" or "minecraft:" prefixes, and to see the default unknown command message. However, the nonexist-bypass perm alone does not allow the player to bypass "nonexist" and does not allow the player to bypass the blocked commands, and similarly the bypass perm does not allow the player to bypass the "nonexist" and "allow-unregistered" restrictions.

## Configuration
JayBlock's [config.yml](https://github.com/Cannicide/JayBlock/blob/master/src/config.yml) is fully commented and highly customizable. Read the YAML comments and the above features section for details on each config option.

Thank you for downloading my plugin. I am not an experienced Spigot plugin developer, though I have years of experience in Java. I've tested this plugin numerous times with various different scenarios, and found no issues with the plugin. If you find any issues, feel free to open an Issue on this repository and I will fix the problem as soon as possible.

## Credits
Created entirely by Cannicide (minecraft ign: JayCraft2).
Developed in the excellent *IntelliJ IDEA* IDE.
