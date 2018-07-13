package NPCQuest;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class NPCQuest extends JavaPlugin {
	
	@Override
	public void onEnable() {
		getCommand("NPCQuest").setExecutor(new QuestConsoleCommand());
		getCommand("퀘스터").setExecutor(new QuestCommand());
		getCommand("퀘스터생성").setExecutor(new QuestSummonCommand());
		if(new File("plugins/NPCQuest").isDirectory() == false) {
			new File("plugins/NPCQuest").mkdirs();
		}
		Bukkit.getPluginManager().registerEvents(new NPCListener(), this);
		QuestManager qm = new QuestManager();
		qm.LoadNPC();
		qm.close();
 	}
	
	@Override
	public void onDisable() {

	}
	
}
