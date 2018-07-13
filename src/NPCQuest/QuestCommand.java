package NPCQuest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class QuestCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cs.isOp() == false) {
			cs.sendMessage("NPCQuset");
			return false;
		}
		if(args.length == 0) {
			cs.sendMessage("§a/퀘스터 삭제 <ID> - 퀘스터를 삭제합니다");
			cs.sendMessage("§a/퀘스터 설명 <ID> <내용 ~> - 퀘스터의 설명을 변경합니다");
			cs.sendMessage("§a/퀘스터 이동 <ID> - 퀘스터에게 이동합니다");
			cs.sendMessage("§a/퀘스터 목록");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("삭제")) {
			if(args.length != 1) {
				int id = 0;
				try {
					id = Integer.parseInt(args[1]);
				} catch(NumberFormatException e) {
					cs.sendMessage("ID를 입력하세요.");
				}			 
				QuestManager qm = new QuestManager();
				qm.setEnable(qm.getLocation(id), false);
				qm.LoadNPC();
				qm.close();
			} else {			
				cs.sendMessage("§c/퀘스터 삭제 <ID> - 퀘스터를 삭제합니다");
			}
		}
		
		if(args[0].equalsIgnoreCase("목록")) {
			if(args.length != 1) {
				int id = 0;
				try {
					id = Integer.parseInt(args[1]);
				} catch(NumberFormatException e) {
					cs.sendMessage("ID를 입력하세요.");
				}			 
				QuestManager qm = new QuestManager();
				qm.DeleteNPC(qm.getLocation(id));
				qm.LoadNPC();
				qm.close();
			} else {			
				cs.sendMessage("§c/퀘스터 삭제 <ID> - 퀘스터를 삭제합니다");
			}
		}
		
		return false;
	}

}
