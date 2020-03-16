package FaFaRen.Minecraft.WhiteList.Commands;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class AddListCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("owl")) {	
			String playerName = null;
			switch (args[0]) {
			case "add":
				if (sender.hasPermission("OffLineWhiteList.add")) {	
					if (args[1] != null) {
						playerName = args[1];
					}else {
						return false;
					}
					sender.sendMessage(ChatColor.DARK_GREEN + "Adding UUID: " + getOfflinePlayerUUID(playerName)
							+ " name: " + playerName + " to whitelist");
					try {
						AddWhitelist(playerName);
						sender.sendMessage(ChatColor.GREEN + "Whitelist added");
						Bukkit.dispatchCommand(sender, "whitelist reload");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						sender.sendMessage(ChatColor.RED + "Whitelist added unsucessfully");
						e.printStackTrace();
					}
				}else {
					sender.sendMessage(ChatColor.DARK_RED + "Sorry, but you don't have this permission");
				}

				return true;
			case "list":
				if (sender.hasPermission("OffLineWhiteList.list")) {	
				sender.sendMessage(ChatColor.GREEN + "Whitelist list:");
				Bukkit.dispatchCommand(sender, "whitelist list");
				}else {
					sender.sendMessage(ChatColor.DARK_RED + "Sorry, but you don't have this permission");
				}
				
				return true;
			case "remove":
				if (sender.hasPermission("OffLineWhiteList.list")) {
				if (args[1] != null) {
					playerName = args[1];
				}else {
					return false;
				}
				Bukkit.dispatchCommand(sender, "whitelist remove " + playerName);
				}else {
					sender.sendMessage(ChatColor.DARK_RED + "Sorry, but you don't have this permission");
				}
				
				return true;
			default:
				return false;
			}

		}
		return false;
	}

	@SuppressWarnings({ "unchecked" })
	private void AddWhitelist(String userID) throws IOException {
		String UUID = getOfflinePlayerUUID(userID);
		JSONObject playerDetails = new JSONObject();
		playerDetails.put("uuid", UUID);
		playerDetails.put("name", userID);

		// get whitelist json array from file first
		JSONArray playerList = getWhiteListFromJSon();

		playerList.add(playerDetails);

		// Write JSON file
		try (FileWriter file = new FileWriter("whitelist.json")) {
			file.write(playerList.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JSONArray getWhiteListFromJSon() {
		JSONParser parser = new JSONParser();
		JSONArray playerList = null;
		try {
			playerList = (JSONArray) parser.parse(new FileReader("whitelist.json"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return playerList;
	}
	
	
	
	 public String getOfflinePlayerUUID(String username) {
		 UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8));
		 return uuid.toString();
	 }
	 

}

