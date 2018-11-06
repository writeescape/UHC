package net.upd4ting.uhcreloaded;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import net.milkbowl.vault.economy.Economy;
import net.upd4ting.uhcreloaded.board.Var;
import net.upd4ting.uhcreloaded.configuration.Configuration;
import net.upd4ting.uhcreloaded.configuration.Configuration.ConfType;
import net.upd4ting.uhcreloaded.configuration.configs.BiomesConfig;
import net.upd4ting.uhcreloaded.configuration.configs.BoardConfig;
import net.upd4ting.uhcreloaded.configuration.configs.ChatConfig;
import net.upd4ting.uhcreloaded.configuration.configs.CommandConfig;
import net.upd4ting.uhcreloaded.configuration.configs.EconomyConfig;
import net.upd4ting.uhcreloaded.configuration.configs.GenerationConfig;
import net.upd4ting.uhcreloaded.configuration.configs.KitConfig;
import net.upd4ting.uhcreloaded.configuration.configs.LangConfig;
import net.upd4ting.uhcreloaded.configuration.configs.LootConfig;
import net.upd4ting.uhcreloaded.configuration.configs.MainConfig;
import net.upd4ting.uhcreloaded.configuration.configs.MySQLConfig;
import net.upd4ting.uhcreloaded.configuration.configs.TeamConfig;
import net.upd4ting.uhcreloaded.configuration.configs.TimerConfig;
import net.upd4ting.uhcreloaded.event.EventManager;
import net.upd4ting.uhcreloaded.event.events.EventServerPing;
import net.upd4ting.uhcreloaded.leaderboard.Hooker;
import net.upd4ting.uhcreloaded.nms.NMSHandler;
import net.upd4ting.uhcreloaded.placeholderapi.PlaceholderHook;
import net.upd4ting.uhcreloaded.task.TaskManager;
import net.upd4ting.uhcreloaded.task.tasks.UpdateSQLTask;
import net.upd4ting.uhcreloaded.team.TeamCommand;
import net.upd4ting.uhcreloaded.util.Util;

public class UHCReloaded extends JavaPlugin {

    private static UHCReloaded instance;
    private static Game game;
    private static NMSHandler nmsHandler;
    private static ProtocolManager pManager;
    private static Economy economy;

    private static MainConfig mainConfiguration;
    private static TeamConfig teamConfiguration;
    private static LootConfig lootConfiguration;
    private static BiomesConfig biomesConfiguration;
    private static LangConfig langConfiguration;
    private static ChatConfig chatConfiguration;
    private static BoardConfig boardConfiguration;
    private static TimerConfig timerConfiguration;
    private static MySQLConfig mysqlConfiguration;
    private static EconomyConfig economyConfiguration;
    private static KitConfig kitConfiguration;
    private static GenerationConfig generationConfiguration;
    private static CommandConfig commandConfiguration;

    public static String uid = "%%__USER__%%";
    public static String RESOURCE = "%%__RESOURCE__%%";
    public static String uidspigotlink = "https://spigotmc.org/members/" + uid;

    @Override
    public void onEnable() {
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.11 Safari/537.36");
        try {
            Field charset = Charset.class.getDeclaredField("defaultCharset");
            charset.setAccessible(true);
            charset.set(null, null);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        instance = this;

        nmsHandler = new NMSHandler();

        pManager = ProtocolLibrary.getProtocolManager();

        initConfig();

        Configuration.loadConfigs(ConfType.STARTUP);

        game = new Game();

        Var.registerVars();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        EventManager.registerEvents();

        // On lance le vrai enable
        new BukkitRunnable() {
            @Override
            public void run() {
                enable();
            }
        }.runTaskLater(this, 1);
    }

    public void enable() {
        Bukkit.getPluginManager().registerEvents(new EventServerPing(), UHCReloaded.getInstance());

        // -- Load enable Config / Task config enable
        Configuration.loadConfigs(ConfType.ENABLE);
        Configuration.loadTaskEnable();

        // -- Prevent protection from spawn
        Bukkit.getServer().setSpawnRadius(0);

        // -- Init command
        Bukkit.getPluginCommand("game").setExecutor(new GameCommand());
        Bukkit.getPluginCommand("team").setExecutor(new TeamCommand());
        Bukkit.getPluginCommand("stat").setExecutor(new StatCommand());
        if (mainConfiguration.isLobbyEnabled())
            Bukkit.getPluginCommand("hub").setExecutor(new HubCommand());

        // -- Init Task Manager && Board Task
        new TaskManager();

        // Log message
        Logger.log(Logger.LogLevel.INFO, "Starting UHCReloaded v" + ChatColor.RED + getDescription().getVersion());
        Logger.log(Logger.LogLevel.INFO, "Developer: Vouchs | https://twitter.com/MartinEkstrom_");
        if (mysqlConfiguration.isEnabled()) {
            UpdateSQLTask task = TaskManager.updateSQLTask;
            Logger.log(Logger.LogLevel.INFO, ChatColor.AQUA + "MySQL status: " + (task.isConnected() ? ChatColor.GREEN + "ON" :
                    ChatColor.RED + "ERROR"));
        }

        // Vault CHECK
        checkVault();

        // LeaderHeads
        Hooker.hookInLeaderheads();

        // PlaceholderAPI
        if (mainConfiguration.getPlaceholderApi() && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderHook(this).hook();
        }

        // Check if purchase kit is enabled...
        checkKitConfig();

        // -- Start loading the world & create dm world
        if (timerConfiguration.isDmEnabled()) {
            WorldCreator creator = new WorldCreator(timerConfiguration.getDmWorldName());
            creator.createWorld();
        }

        game.disableSaving();

        new BukkitRunnable() {
            @Override
            public void run() {
                game.generateLobby();
            }
        }.runTaskLater(this, 10);
    }

    @Override
    public void onDisable() {
        game.unloadWorld();
    }

    private void checkKitConfig() {
        if (kitConfiguration.isKitPurchaseEnabled() && (!mysqlConfiguration.isEnabled() || !economyConfiguration.isVaultEnabled())) {
            Logger.log(Logger.LogLevel.WARNING, "Kit purchase is enabled and need mysql and vault! Please enable these option or disable kits purchase! Stopping the server...");
            Bukkit.shutdown();
        }
    }

    private void checkVault() {
        if (economyConfiguration.isVaultEnabled()) {
            if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
                Logger.log(Logger.LogLevel.INFO, "Vault: enabled");
                Logger.log(Logger.LogLevel.INFO, "Analysing economy plugin...");
                RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
                if (economyProvider != null)
                    economy = economyProvider.getProvider();

                if (economy != null)
                    Logger.log(Logger.LogLevel.SUCCESS,  "REGISTERED - OKAY");
                else {
                    Logger.log(Logger.LogLevel.WARNING,  "NOT REGISTERED ! To use vault you need an economy plugin that hook into it ! You need to add one or disable vault. Stopping the server....");
                    Bukkit.shutdown();
                }
            } else {
                Logger.log(Logger.LogLevel.WARNING, "Vault option is enabled, but isn't installed.");
                Bukkit.shutdown();
            }
        } else
            Logger.log(Logger.LogLevel.INFO, "Vault: disabled");
    }

    private void initConfig() {
        chatConfiguration = (ChatConfig) Configuration.registerConfig(new ChatConfig());
        mainConfiguration = (MainConfig) Configuration.registerConfig(new MainConfig());
        lootConfiguration = (LootConfig) Configuration.registerConfig(new LootConfig());
        biomesConfiguration = (BiomesConfig) Configuration.registerConfig(new BiomesConfig());
        teamConfiguration = (TeamConfig) Configuration.registerConfig(new TeamConfig());
        langConfiguration = (LangConfig) Configuration.registerConfig(new LangConfig());
        boardConfiguration = (BoardConfig) Configuration.registerConfig(new BoardConfig());
        timerConfiguration = (TimerConfig) Configuration.registerConfig(new TimerConfig());
        mysqlConfiguration = (MySQLConfig) Configuration.registerConfig(new MySQLConfig());
        economyConfiguration = (EconomyConfig) Configuration.registerConfig(new EconomyConfig());
        kitConfiguration = (KitConfig) Configuration.registerConfig(new KitConfig());
        generationConfiguration = (GenerationConfig) Configuration.registerConfig(new GenerationConfig());
        commandConfiguration = (CommandConfig) Configuration.registerConfig(new CommandConfig());
    }

    public static MainConfig getMainConfiguration() {
        return mainConfiguration;
    }

    public static LootConfig getLootConfiguration() {
        return lootConfiguration;
    }

    public static BiomesConfig getBiomesConfiguration() {
        return biomesConfiguration;
    }

    public static TeamConfig getTeamConfiguration() {
        return teamConfiguration;
    }

    public static LangConfig getLangConfiguration() {
        return langConfiguration;
    }

    public static ChatConfig getChatConfiguration() {
        return chatConfiguration;
    }

    public static BoardConfig getBoardConfiguration() {
        return boardConfiguration;
    }

    public static TimerConfig getTimerConfiguration() {
        return timerConfiguration;
    }

    public static MySQLConfig getMysqlConfiguration() {
        return mysqlConfiguration;
    }

    public static EconomyConfig getEconomyConfiguration() {
        return economyConfiguration;
    }

    public static KitConfig getKitConfiguration() {
        return kitConfiguration;
    }

    public static GenerationConfig getGenerationConfiguration() {
        return generationConfiguration;
    }

    public static CommandConfig getCommandConfiguration() {
        return commandConfiguration;
    }

    public static UHCReloaded getInstance() {
        return instance;
    }

    public static ProtocolManager getProtocolManager() {
        return pManager;
    }

    public static NMSHandler getNMSHandler() {
        return nmsHandler;
    }

    public static Economy getEconomyHandler() {
        return economy;
    }

    public static Game getGame() {
        return game;
    }

    public static String getPrefix() {
        return chatConfiguration.getPrefix();
    }
}
