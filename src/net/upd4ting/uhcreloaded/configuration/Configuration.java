package net.upd4ting.uhcreloaded.configuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import net.upd4ting.uhcreloaded.Logger;
import net.upd4ting.uhcreloaded.UHCReloaded;
import net.upd4ting.uhcreloaded.exception.InvalidConfigException;
import net.upd4ting.uhcreloaded.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.collect.Lists;

public abstract class Configuration {
	
	public static enum ConfType { STARTUP, ENABLE; }
	
	private static List<Configuration> confs = new ArrayList<>();
	
	protected ConfType type;
	protected FileConfiguration conf;
	protected String fileName;
	protected List<String> sections;
	
	public Configuration(ConfType type, String fileName, String... sections) {
		this.type = type;
		this.fileName = fileName;
		this.sections = Lists.newArrayList(sections);
		this.conf = loadFileConfiguration(fileName);
	}
	
	public abstract void loadData() throws InvalidConfigException;
	
	// Je l'override dans timerConfig car celui la a besoin de se load au startup mais gérer le dm world après le startup
	public void doTaskEnable() {}
	
	private FileConfiguration loadFileConfiguration(String fileName) {
        File customConfigFile = new File(UHCReloaded.getInstance().getDataFolder(), fileName);
        if (!customConfigFile.exists()) {
        	FileConfiguration c = YamlConfiguration.loadConfiguration(customConfigFile);
            Reader defConfigStream = null;
            try {
                defConfigStream = new InputStreamReader(UHCReloaded.getInstance().getResource(fileName), "UTF-8");
                if (defConfigStream != null) {
                    FileConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                    c.setDefaults(defConfig);
                    UHCReloaded.getInstance().saveResource(fileName, false);
                }
            }
            catch (IOException e) { e.printStackTrace(); }
        } else { // On va tenter d'autoreload le fichier alors
			try {
				List<String> modifiedList = readAllLines(customConfigFile);
				customConfigFile = getFileFromInputStream(UHCReloaded.getInstance().getResource(fileName));
				List<String> currentList = readAllLines(customConfigFile);
				
				String path = "";
				Integer lastSpace = 0;
				String lastKey = "";
				Integer toSkip = 0;
				
				for(String line : new ArrayList<>(currentList)) {
					
					if(!isValidLine(line))
						continue;
					
					if(toSkip > 0) {
						toSkip--;
						continue;
					}
					
					Integer space = getNumberSpace(line);
					String key = getKey(line);
					
					if(space == 0)
						path = key;
					else if(space == lastSpace) 
						path = path.replace(lastKey, key);
					else if(space > lastSpace) 
						path += "." + key;
					else if(space < lastSpace) { // May cause problem one day
						String[] splitted = path.split("\\.");
						Integer diff = space / 2;
						
						StringBuilder sb = new StringBuilder();
						
						for(int i = 0; i <= diff; i++) {
							if(i == diff)
								sb.append(key + ".");
							else
								sb.append(splitted[i] + ".");
						}
						sb.substring(0, sb.length() - 2);
						
						path = sb.toString();
					}
					
					lastKey = key;
					lastSpace = space;
					
					if (sections.contains(path)) { // Ici on gère les sections
						
						Integer currentIndex = getIndexWithPath(currentList, path);
						Integer modifiedIndex = getIndexWithPath(modifiedList, path);
						
						if (modifiedIndex != -1) { // On fait ca seulement si la section existe
							Integer number = removeAllSection(currentList, currentIndex);
							
							List<String> list = readAllSection(modifiedList, modifiedIndex);
							
							for (String s : list) {
								currentIndex++;
								currentList.add(currentIndex, s);
							}
							
							toSkip = number;
						}
						
					} else if (containsPath(modifiedList, path) != null) {
						if (isKeyWithValue(line) && !line.contains("[]")) { // Ici on gère les values
							String newLine = containsPath(modifiedList, path);
							Integer index = getIndexWithPath(currentList, path);
							currentList.set(index, newLine);
						}
						else { // Pour gérer les listes
							if (line.contains("[]")) {
								Integer index = getIndexWithPath(currentList, path) + 1;
								
								List<String> list = getValueListString(modifiedList, path);
								
								if (list != null && !list.isEmpty()) {
									currentList.set(index - 1, line.replace("[]", ""));
									
									for(String s : list) {
										currentList.add(index, s);
										index++;
									}
								}
							} else {
								Integer index = getIndexWithPath(currentList, path) + 1;
								
								if (index >= currentList.size())
									continue;
								
								String nextLine = currentList.get(index);
								
								if(isKeyWithList(line, nextLine)) {
									Integer number = removeAllList(currentList, index);
									
									List<String> list = getValueListString(modifiedList, path);
									
									if(list != null) {
										for(String s : list) {
											currentList.add(index, s);
											index++;
										}
									}
									
									toSkip = number;
								}
							}
						}
					}
				}

				writeAllLines(customConfigFile, currentList);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        return YamlConfiguration.loadConfiguration(customConfigFile);
	}
	
	private File getFileFromInputStream(InputStream inputStream) {
		File file = new File(UHCReloaded.getInstance().getDataFolder(), fileName);
		
		OutputStream outputStream = null;

		try {
			outputStream = 
	                    new FileOutputStream(file);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return file;
	}
	
	private Integer getNumberSpace(String s) {
		Integer number = 0;
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == ' ')
				number++;
			else
				break;
		}
		
		return number;
	}
	
	private Boolean isValidLine(String line) {
		if (line.startsWith("#"))
			return false;
		if (line.equals(""))
			return false;

		Boolean isValid = false;
		
		for (int i = 0; i < line.length(); i++)
			if (line.charAt(i) != ' ')
				isValid = true;
		
		return isValid;
	}
	
	private Boolean isKeyWithValue(String s) {
		Boolean isKey = false;
		Boolean founded = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!founded) {
				if (c == ':')
					founded = true;
				else if (c == '-' && s.charAt(i+1) == ' ')
					break;
			} else {
				if (c != ' ')
					isKey = true;
			}
		}
		
		return isKey;
	}

	private Boolean isKeyWithList(String line, String nextLine) {
		if(isKeyWithValue(line))
			return false;
		
		Integer space = getNumberSpace(line);
		Integer space2 = getNumberSpace(nextLine);
		
		if(space2 < space)
			return false;
		
		if(isKeyFromList(nextLine))
			return true;
		
		return false;
	}
	
	private Boolean isKeyFromList(String line) {
		if(isKeyWithValue(line))
			return false;
		
		Integer space = getNumberSpace(line);
		
		if(line.length() > space && line.charAt(space) == '-')
			return true;
		
		return false;
	}
	
	private String getKey(String line) {
		StringBuilder sb = new StringBuilder();
		Boolean founded = false;
		for(int i = 0; i < line.length() && !founded; i++) {
			char c = line.charAt(i);
			
			if(c == ':')
				founded = true;
			else if(c != ' ')
				sb.append(c);
		}
		
		return sb.toString();
	}
	
	private String containsPath(List<String> list, String path) { 
		String[] splitted = path.contains(".") ? path.split("\\.") : null;
		
		if(splitted == null) {
			splitted = new String[1];
			splitted[0] = path;
		}
		
		String chemin = "";
		Integer lastSpace = 0; // Ne peut que augmenter au fure et a mesure qu'on avance dans l'arbo
		Integer step = 0;
		String lastLineFounded = "";
		
		for(int i = 0; i < list.size() && !path.equals(chemin); i++) {
			String line = list.get(i);
			String key = getKey(line);
			
			if(!isValidLine(line)) // Si c'est un commentaire on s'en bas les couilles
				continue;
			
			Integer space = getNumberSpace(line);
			
			if(space < lastSpace) // On arrete car alors path existe pas
				break;
			
			if(step < splitted.length && key.equals(splitted[step])) {
				lastSpace = space;
				step++;
				if(space > 0)
					chemin += ".";
				chemin += key;
				lastLineFounded = line;
			}
		}
		
		if(path.equals(chemin))  
			return lastLineFounded;
		else
			return null;
	}
	
	private Integer getIndexWithPath(List<String> list, String path) { 
		String[] splitted = path.contains(".") ? path.split("\\.") : null;
		
		if(splitted == null) {
			splitted = new String[1];
			splitted[0] = path;
		}
		
		String chemin = "";
		Integer lastSpace = 0; // Ne peut que augmenter au fure et a mesure qu'on avance dans l'arbo
		Integer step = 0;
		Integer index = null;
		
		for(int i = 0; i < list.size() && !path.equals(chemin); i++) {
			String line = list.get(i);
			String key = getKey(line);
			
			if(!isValidLine(line)) // Si c'est un commentaire on s'en bas les couilles
				continue;
			
			Integer space = getNumberSpace(line);
			
			if(space < lastSpace) // On arrete car alors path existe pas
				break;
			
			if(step < splitted.length && key.equals(splitted[step])) {
				lastSpace = space;
				step++;
				if(space > 0)
					chemin += ".";
				chemin += key;
				index = i;
			}
		}
		
		if(path.equals(chemin))  
			return index;
		else
			return -1;
	}
	
	private List<String> getValueListString(List<String> modifiedList, String path) {
		List<String> list = new ArrayList<>();
		String line = containsPath(modifiedList, path);
		
		Integer index = modifiedList.indexOf(line) + 1; // Index du commencement de la liste
		String listLine = null;
		
		while(index < modifiedList.size() && isKeyFromList((listLine = modifiedList.get(index)))) {
			list.add(listLine);
			index++;
		}
			
		if(list.isEmpty())
			return null;
		
		return list;
	}
	
	private Integer removeAllList(List<String> currentList, int index) {
		Integer number = 0;
		
		while(index < currentList.size() && isKeyFromList(currentList.get(index))) {
			currentList.remove(index);
			number++;
		}
		return number;
	}
	
	private List<String> readAllSection(List<String> list, Integer index) {
		List<String> section = new ArrayList<>();
		
		Integer baseSpace = getNumberSpace(list.get(index));
		
		for (Integer i = index + 1; i < list.size() && getNumberSpace(list.get(i)) > baseSpace; i++)
			section.add(list.get(i));
		
		return section;
	}
	
	private Integer removeAllSection(List<String> list, Integer index) {
		Integer number = 0;
		
		Integer baseSpace = getNumberSpace(list.get(index));
		
		int i = index + 1;
		
		while (i < list.size() && getNumberSpace(list.get(i)) > baseSpace) {
			list.remove(i);
			number++;
		}
		
		return number;
	}
	
	private List<String> readAllLines(File file) {
		List<String> lines = new ArrayList<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null)
				lines.add(line);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return lines;
	}
	
	private void writeAllLines(File file, List<String> lines) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		for (String s : lines)
			bw.write(s + "\n");
		
		bw.close();
	}
	
	public String getStringConfig(String path) {
		return ChatColor.translateAlternateColorCodes('&', conf.getString(path));
	}
	
	public List<String> getStringListConfig(String path) {
		List<String> list = new ArrayList<>();
		for (String s : conf.getStringList(path))
			list.add(ChatColor.translateAlternateColorCodes('&', s));
		return list;
	}
	
	public static Configuration registerConfig(Configuration conf){
		confs.add(conf);
		return conf;
	}
	
	public static void loadConfigs(ConfType type) {
		try {
			for (Configuration c : confs) {
				if (c.type == type) {
					Logger.log(Logger.LogLevel.INFO, "Loading configuration: " + ChatColor.RED + c.fileName);
					c.loadData();
				}
			}
		} catch (InvalidConfigException e){
			e.printStackTrace();
			Bukkit.shutdown();
		}
	}
	
	public static void loadTaskEnable() {
		for (Configuration c : confs)
			c.doTaskEnable();
	}
}
