package net.upd4ting.uhcreloaded.board;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.ChatColor;

public class Sideline {
	Sidebar sb;

	HashMap<Integer,String> old = new HashMap<>();
	Deque<String> buffer = new ArrayDeque<>();
	

	public Sideline (Sidebar sb) {
		this.sb = sb;
	}
	
	public void clear() {
		sb.clear();
		old.clear();
	}
	
	public void set(Integer i, String str) {
		if (old.containsKey(i)) {
			sb.removeLine(old.get(i));
		}
		
		if (str.equals(""))
			str = " ";
		
		str = makeUnique(str);
		
		old.put(i, str);
		sb.setLine(str, i);
	}
	
	public String makeUnique(String str) {
		while (old.containsValue(str)) {
			for (int j=0; j<ChatColor.values().length; j++) {
				if (!old.containsValue(str + ChatColor.values()[j])) {
					str = str + ChatColor.values()[j];
					return str;
				}
			}
			str = str + ChatColor.RESET;
		}
		return str;
	}
	
	public void add(String s) {
		buffer.add(s);
	}
	
	public void flush() {
		clear();
		Integer i = 0;
		Iterator<String> it = buffer.descendingIterator();
		while (it.hasNext()) {
			String line = it.next();
			i++;
			set(i, line);
		}
		
		buffer.clear();
		
		sb.send();
	}
	
	public void send() {
		sb.send();
	}
	
	public Integer getRemainingSize() {
		return 15 - buffer.size();
	}
}
