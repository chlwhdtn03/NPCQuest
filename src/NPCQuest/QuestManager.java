package NPCQuest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class QuestManager {
	
	private Connection con;
	private Statement stmt;
	
	public QuestManager() {
		try {
			con = DriverManager.getConnection("jdbc:sqlite:plugins/NPCQuest/NPC.db");
			stmt = con.createStatement();
			
			String sql = "CREATE TABLE IF NOT EXISTS NPC " + "(ID Number, Name VARCHAR, World VARCHAR, X Number, Y Number, Z Number, Yaw Number, Type VARCHAR, Target VARCHAR, Value Number, TimeAttack Number, Description VARCHAR, Enable Number)";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void AddNPC(String NPCName, Location loc, QuestType type, String target, int value, long millisec, String description) {
		try {
			String sql = "INSERT INTO NPC (ID, Name, World, X, Y, Z, Yaw, Type, Target, Value, TimeAttack, Description, Enable) "
					+ "VALUES ("+nextID()+",'" + NPCName + "','" + loc.getWorld().getName() + "',"+ (loc.getBlockX() + 0.5) +","+loc.getBlockY()+","+(loc.getBlockZ() + 0.5)+","+loc.getYaw()+",'"+type.toString()+"','"+target+"',"+value+","+millisec+",'"+description+"',"+1+")";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void DeleteNPC(Location loc) {
		try {
			String sql = "DELETE FROM NPC WHERE World='"+loc.getWorld().getName()+"' AND X="+(loc.getBlockX() + 0.5)+" AND Y="+loc.getY()+" AND Z="+(loc.getBlockZ() + 0.5);			
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int nextID() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM NPC");
			int result = 1;
			while(rs.next()) {
				result++;
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public boolean isEnable(Location loc) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM NPC WHERE World='"+loc.getWorld().getName()+"' AND X="+(loc.getBlockX() + 0.5)+" AND Y="+loc.getY()+" AND Z="+(loc.getBlockZ() + 0.5));		
			return rs.getBoolean("Enable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isEnable(int id) {
		try {
			Location loc = getLocation(id);
			ResultSet rs = stmt.executeQuery("SELECT * FROM NPC WHERE World='"+loc.getWorld().getName()+"' AND X="+(loc.getBlockX() + 0.5)+" AND Y="+loc.getY()+" AND Z="+(loc.getBlockZ() + 0.5));		
			return rs.getBoolean("Enable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void setEnable(Location loc, boolean enable) {
		try {
			int result = enable ? 1:0;
			String sql = "UPDATE NPC SET Enable="+result+" WHERE World='"+loc.getWorld().getName()+"' AND X="+(loc.getBlockX() + 0.5)+" AND Y="+loc.getY()+" AND Z="+(loc.getBlockZ() + 0.5);	
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void setEnable(int id, boolean enable) {
		try {
			Location loc = getLocation(id);
			int result = enable ? 1:0;
			String sql = "UPDATE NPC SET Enable="+result+" WHERE World='"+loc.getWorld().getName()+"' AND X="+(loc.getBlockX() + 0.5)+" AND Y="+loc.getY()+" AND Z="+(loc.getBlockZ() + 0.5);	
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public Location getLocation(int id) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM NPC WHERE ID="+id);	
			Location l = new Location(Bukkit.getWorld(rs.getString("World")), rs.getDouble("X"), rs.getDouble("Y"), rs.getDouble("Z"), rs.getFloat("Yaw"), 0);
			return l;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	public int getID(Location loc) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM NPC WHERE World='"+loc.getWorld().getName()+"' AND X="+(loc.getBlockX() + 0.5)+" AND Y="+loc.getY()+" AND Z="+(loc.getBlockZ() + 0.5));	
			return rs.getInt("ID");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return 1;
	}
	
	public Quest getQuestInfo(Location loc) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM NPC WHERE World='"+loc.getWorld().getName()+"' AND X="+(loc.getBlockX() + 0.5)+" AND Y="+loc.getY()+" AND Z="+(loc.getBlockZ() + 0.5));	
			Quest q = new Quest(QuestType.valueOf(rs.getString("Type")), rs.getInt("Value"), rs.getString("Target"), rs.getLong("TimeAttack"), rs.getString("Description"));
			return q;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Quest getQuestInfo(int id) {
		try {
			Location loc = getLocation(id);
			ResultSet rs = stmt.executeQuery("SELECT * FROM NPC WHERE World='"+loc.getWorld().getName()+"' AND X="+(loc.getBlockX() + 0.5)+" AND Y="+loc.getY()+" AND Z="+(loc.getBlockZ() + 0.5));	
			Quest q = new Quest(QuestType.valueOf(rs.getString("Type")), rs.getInt("Value"), rs.getString("Target"), rs.getLong("TimeAttack"), rs.getString("Description"));
			return q;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getNPCName(Location loc) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM NPC WHERE World='"+loc.getWorld().getName()+"' AND X="+(loc.getBlockX() + 0.5)+" AND Y="+loc.getY()+" AND Z="+(loc.getBlockZ() + 0.5));	
			return rs.getString("Name");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getNPCName(int id) {
		try {
			Location loc = getLocation(id);
			ResultSet rs = stmt.executeQuery("SELECT * FROM NPC WHERE World='"+loc.getWorld().getName()+"' AND X="+(loc.getBlockX() + 0.5)+" AND Y="+loc.getY()+" AND Z="+(loc.getBlockZ() + 0.5));	
			return rs.getString("Name");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean hasNPC(Location loc) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM NPC WHERE Enable=1 AND World='"+loc.getWorld().getName()+"' AND X="+(loc.getBlockX() + 0.5)+" AND Y="+loc.getY()+" AND Z="+(loc.getBlockZ() + 0.5));		
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean hasID(int id) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM NPC WHERE ID=" + id);		
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Location> getLocations() {
		try {
			List<Location> result = new ArrayList<>();
			ResultSet rs = stmt.executeQuery("SELECT * FROM NPC");
			while(rs.next()) {
				Location l = new Location(Bukkit.getWorld(rs.getString("World")), rs.getDouble("X"), rs.getDouble("Y"), rs.getDouble("Z"), rs.getFloat("Yaw"), 0);
				result.add(l);
			}
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	public void LoadNPC() {
		for(Location loc : getLocations()) {
			Villager vill = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
			Collection<Entity> es = loc.getWorld().getNearbyEntities(loc, 0, 1, 0);
			boolean Already = false;
			boolean Disable = false;
			System.out.println(es.size());
			if(es.isEmpty()) {
				if(isEnable(loc) == false) {
					vill.remove();
					continue;
				}
			}
			for(Entity e : es) {
				if(e.getName().equals(getNPCName(loc)) && e.getType().equals(vill.getType()) && e.getLocation().equals(loc)) {
					System.out.println("0");
					if(isEnable(loc) == false) {
						System.out.println("0.5");
						Disable = true;
						e.remove();
					}
					System.out.println("1");
					Already = true;
				} else {
					continue;
				}
			}
			System.out.println("1.5");
			if(Already) {
				vill.remove();
				continue;
			}
			if(Disable) {
				vill.remove();
				continue;
			}
						
			vill.setCustomName(getNPCName(loc));
			System.out.println(getNPCName(loc));
			vill.setAI(false);
			vill.setCollidable(false);
			vill.setInvulnerable(true);
		}
	}

	public void close() {
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
