package FaFaRen.Minecraft.WhiteList;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import FaFaRen.Minecraft.WhiteList.Commands.AddListCommand;



public class OffLineWhiteList extends JavaPlugin{
	
 public void onEnable() {
	 getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "Offline white list is enable");
	 this.getCommand("owl").setExecutor(new AddListCommand()); 
 }
 
 public void onDisable() {
	 getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "Offline white list is disable");
 }
 
}
