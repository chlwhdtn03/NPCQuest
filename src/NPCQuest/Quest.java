package NPCQuest;

public class Quest {
	
	private QuestType Type;
	private int Value;
	private long Millisec;
	private String Description, Target;
	
	public Quest(QuestType type, int value, String target, long millisec) {
		Type = type;
		Value = value;
		Target = target;
		Millisec = millisec;
	}
	
	public Quest(QuestType type, int value, String target, long millisec, String description) {
		Type = type;
		Value = value;
		Target = target;
		Millisec = millisec;
		Description = description;
	}
	
	public String getTarget() {
		return Target;
	}

	public QuestType getQuestType() {
		return Type;
	}
	
	public int getValue() {
		return Value;
	}
	
	public long getTimeout() {
		return Millisec;
	}
	
	public String getDescription() {
		return Description;
	}
}
