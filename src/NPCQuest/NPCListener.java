package NPCQuest;

import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftMetaBook;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;

public class NPCListener implements Listener {
	
	@EventHandler
	public void onIntract(PlayerInteractEntityEvent e) {
		QuestManager qm = new QuestManager();
		for(Location loc : qm.getLocations()) {
			if(e.getRightClicked().getCustomName().equals(qm.getNPCName(loc)) && e.getRightClicked().getType().equals(e.getRightClicked().getType()) && e.getRightClicked().getLocation().equals(loc)) {
				e.setCancelled(true);
				ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta bookMeta = (BookMeta) book.getItemMeta();
				List<IChatBaseComponent> pages;
				
				try {
				    pages = (List<IChatBaseComponent>) CraftMetaBook.class.getDeclaredField("pages").get(bookMeta);
				} catch (ReflectiveOperationException ex) {
				    ex.printStackTrace();
				    return;
				}
				Quest q = qm.getQuestInfo(loc);
				TextComponent descript = new TextComponent(q.getDescription() + "\n\n");
				TextComponent text = new TextComponent(q.getTarget() + "을 " + q.getTimeout() + "까지 " + q.getValue() + "번(개) " + q.getQuestType() + "하세요.\n\n");
				TextComponent allow = new TextComponent("[좋아]     ");
				allow.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("퀘스트를 수락합니다").create()));
				TextComponent disallow = new TextComponent("[싫어]   ");
				disallow.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("퀘스트를 나중에 합니다.").create()));
	
				allow.addExtra(disallow);
				text.addExtra(allow);
				descript.addExtra(text);
				IChatBaseComponent page = ChatSerializer.a(ComponentSerializer.toString(descript) + ComponentSerializer.toString(allow) + "    " + ComponentSerializer.toString(disallow));
				pages.add(page);
				
				book.setItemMeta(bookMeta);
				BookUtil.openBook(book, e.getPlayer());
			}
		}
		qm.close();
	}
	
	
}
