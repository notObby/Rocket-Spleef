package me.obby.rocketspleef.commands;

import me.obby.rocketspleef.Rocketspleef;
import me.obby.rocketspleef.items.ItemManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class StartGame implements CommandExecutor {

    private Rocketspleef plugin;
    public StartGame(Rocketspleef plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if(Bukkit.getOnlinePlayers().size() >= 1) {
                Player player = (Player) sender;
                if (plugin.getAlive().size() <= 1 || sender.isOp()) {
                    //player is parsed to determine world for entity clearing
                    startGame(player);
                } else {
                    Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[RS] " + ChatColor.WHITE + "Get some friends on you lonely boi");
                }

                return true;
            }
            return true;
    }
    public void startGame(Player player){
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[RS] " + ChatColor.WHITE + "Game is starting...");
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setGameMode(GameMode.SURVIVAL);
            p.getInventory().clear();
            p.getInventory().addItem(ItemManager.rocketlauncher);
            p.getInventory().addItem(ItemManager.firework);
            p.getInventory().setChestplate(ItemManager.elytra);
            p.setExp(0);
            p.setFlying(false);

            for (PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
            p.addPotionEffect((new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255, false, false)));
            p.addPotionEffect((new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255, false, false)));
            p.teleport(plugin.getSpawn());
            plugin.getAmmo().put(p, 1);
            p.setLevel(plugin.getAmmo().get(p));
            p.setGlowing(true);
            plugin.getReloading().put(p, false);
        }
        plugin.everyoneAlive();
        //clearentities
        List<Entity> entList = player.getWorld().getEntities();
        for (Entity current : entList) {
            if (current.getType() == EntityType.DROPPED_ITEM) {
                current.remove();
            }
        }
        World world = player.getWorld();
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setTime(1000);
        world.setWeatherDuration(0);

        plugin.setGamestate(true);

    }








}
