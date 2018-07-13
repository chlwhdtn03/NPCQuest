package NPCQuest;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuestSummonCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(!(cs instanceof Player)) {
			return false;
		}
		if(cs.isOp() == false) {
			return false;
		}
		if(args.length < 2) {
			
			cs.sendMessage("§bNPC QUEST");
			String msg = "/퀘스터 <생성할NPC이름> " + Arrays.toString(QuestType.values());
			String NPCName;
			String MissionType;
			try {
				NPCName = args[0];
			} catch(ArrayIndexOutOfBoundsException e) {
				NPCName = "<생성할NPC이름>";
			}
			try {
				MissionType = args[1];
			} catch(ArrayIndexOutOfBoundsException e) {
				MissionType = Arrays.toString(QuestType.values());
			}
				
			if(NPCName.isEmpty() == false) {
				msg = msg.replace("<생성할NPC이름>", NPCName);
			}
			if(MissionType.isEmpty() == false) {
				msg = msg.replace(Arrays.toString(QuestType.values()), MissionType);
			}
			
			cs.sendMessage(msg);
			return true;
		}
		
		if(args.length >= 2) {
			String NPCName = args[0];
			String MissionType = args[1];
			boolean pass = false;
			for(QuestType type : QuestType.values()) {
				if(type.name().equals(MissionType)) {
					pass = true;
					break;
				} 
			}
			if(!pass) {
				cs.sendMessage("§c유효하지 않은 목적입니다.");
				return false;
			}
			
			if(MissionType.equals("사냥")) {
					NPCName = args[0];
					MissionType = args[1];
					System.out.println("진입");
					String msg = "/퀘스터 " + NPCName + " " + MissionType + " <사냥감> <목표값> <시간제한>";
					String Target;
					int value = 0;
					long millisec = 0;
					try {
						Target = args[2];
					} catch(ArrayIndexOutOfBoundsException e) {
						cs.sendMessage(msg);
						return false;
					}
					
					try {
						value = Integer.parseInt(args[3]);
					}  catch(NumberFormatException e) {
						cs.sendMessage("목표값에 자연수를 입력하세요.");
						return false;
					} catch(ArrayIndexOutOfBoundsException e) {
						msg = "/퀘스터 " + NPCName + " " + MissionType + " " + Target + " <목표값> <시간제한>";
						cs.sendMessage(msg);
						return false;
					}
					
					try {
						millisec = Long.parseLong(args[4]);
					} catch(NumberFormatException e) {
						cs.sendMessage("시간에 자연수를 입력하세요.");
						return false;
					} catch(ArrayIndexOutOfBoundsException e) {
						msg = "/퀘스터 " + NPCName + " " + MissionType + " " + Target + " " + value + " <시간제한>";
						cs.sendMessage(msg);
						return false;
					}
					QuestManager qm = new QuestManager();
					if(qm.hasNPC(((Player)cs).getLocation())) {
							cs.sendMessage("§c이곳엔 이미 NPC가 존재합니다.");
							qm.close();
							return false;
					}
					qm.AddNPC(NPCName, ((Player)cs).getLocation(), QuestType.valueOf(MissionType), Target, value, millisec, "TEST");
					Quest q = qm.getQuestInfo(((Player)cs).getLocation());
					cs.sendMessage(q.getTarget() + "을 " + q.getValue() + "번 " + q.getTimeout() + "까지 " + q.getQuestType() + "하세요.");
					qm.LoadNPC();
					qm.close();
				}
			return true;
		}
		return false;
	}

}
