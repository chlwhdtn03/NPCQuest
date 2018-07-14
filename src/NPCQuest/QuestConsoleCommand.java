package NPCQuest;

import java.io.Console;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class QuestConsoleCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(!(cs instanceof ConsoleCommandSender)) {
			return false;
		}
		// /NPCQuest <닉네임> <퀘스트ID>
		
		String player = args[0];
		int id = Integer.parseInt(args[1]);
		
		return false;
	}

}
