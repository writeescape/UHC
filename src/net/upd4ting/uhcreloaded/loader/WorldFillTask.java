package net.upd4ting.uhcreloaded.loader;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.upd4ting.uhcreloaded.Logger;
import org.bukkit.Chunk;
import org.bukkit.Server;
import org.bukkit.World;

import net.upd4ting.uhcreloaded.util.Util;
import net.upd4ting.uhcreloaded.util.Util.Callback;


public class WorldFillTask implements Runnable
{	
	private static volatile DecimalFormat coord = new DecimalFormat("0.0");
	
	// general task-related reference data
	private  Server server = null;
	private World world = null;
	private boolean readyToGo = false;
	private boolean paused = false;
	private boolean pausedForMemory = false;
	private int taskID = -1;
	private int chunksPerRun = 1;
	private int radius = -1;
	private Callback callback;

	// values for the pattern check which fills out the map to the border
	private int x = 0;
	private int z = 0;
	private List<CoordXZ> storedChunks = new LinkedList<CoordXZ>();
	private Set<CoordXZ> originalChunks = new HashSet<CoordXZ>();
	private CoordXZ lastChunk = new CoordXZ(0, 0);

	// for reporting progress back to user occasionally
	private long lastReport = Config.Now();
	private int reportTarget = 0;
	private int reportTotal = 0;
	private int reportNum = 0;


	public WorldFillTask(Server theServer, String worldName, int radius, int chunksPerRun, Callback callback)
	{
		this.server = theServer;
		this.chunksPerRun = chunksPerRun;
		this.radius = radius;
		this.callback = callback;

		this.world = server.getWorld(worldName);
		if (this.world == null)
		{
			if (worldName.isEmpty())
				sendMessage("You must specify a world!");
			else
				sendMessage("World \"" + worldName + "\" not found!");
			this.stop();
			return;
		}
		
		this.x = CoordXZ.blockToChunk(-radius);
		this.z = CoordXZ.blockToChunk(-radius);

		int chunkWidth = (int) Math.ceil((double)((radius + 16) * 2) / 16);
		this.reportTarget = (chunkWidth * chunkWidth) + chunkWidth + 1;

		
		// keep track of the chunks which are already loaded when the task starts, to not unload them
		Chunk[] originals = world.getLoadedChunks();
		for (Chunk original : originals)
		{
			originalChunks.add(new CoordXZ(original.getX(), original.getZ()));
		}

		this.readyToGo = true;
	}

	public void setTaskID(int ID)
	{	
		if (ID == -1) this.stop();
		this.taskID = ID;
	}


	@Override
	public void run()
	{
		if (pausedForMemory)
		{	// if available memory gets too low, we automatically pause, so handle that
			if (Config.AvailableMemoryTooLow())
				return;

			pausedForMemory = false;
			readyToGo = true;
			sendMessage("Enough memory availaible! Automatically continuing!");
		}

		if (server == null || !readyToGo || paused)
			return;

		// this is set so it only does one iteration at a time, no matter how frequently the timer fires
		readyToGo = false;
		// and this is tracked to keep one iteration from dragging on too long and possibly choking the system if the user specified a really high frequency
		long loopStartTime = Config.Now();

		for (int loop = 0; loop < chunksPerRun; loop++)
		{
			// in case the task has been paused while we're repeating...
			if (paused || pausedForMemory)
				return;

			long now = Config.Now();

			// every 5 seconds or so, give basic progress report to let user know how it's going
			if (now > lastReport + 5000)
				reportProgress();

			// if this iteration has been running for 45ms (almost 1 tick) or more, stop to take a breather
			if (now > loopStartTime + 45)
			{
				readyToGo = true;
				return;
			}

			// load the target chunk and generate it if necessary
			world.loadChunk(x, z, true);
			//worldData.chunkExistsNow(x, z);

			// make sure the previous chunk is loaded as well (might have already existed and been skipped over)
			if (!storedChunks.contains(lastChunk) && !originalChunks.contains(lastChunk))
			{
				world.loadChunk(lastChunk.x, lastChunk.z, false);
				storedChunks.add(new CoordXZ(lastChunk.x, lastChunk.z));
			}

			// Store the coordinates of these latest 2 chunks we just loaded, so we can unload them after a bit...
			storedChunks.add(new CoordXZ(x, z));

			// If enough stored chunks are buffered in, go ahead and unload the oldest to free up memory
			while (storedChunks.size() > 8)
			{
				CoordXZ coord = storedChunks.remove(0);
				if (!originalChunks.contains(coord))
					world.unloadChunkRequest(coord.x, coord.z);
			}

			// move on to next chunk
			if (!moveToNext())
				return;
		}

		// ready for the next iteration to run
		readyToGo = true;
	}

	// step through chunks from center; returns false if we're done, otherwise returns true
	public boolean moveToNext()
	{
		if (paused || pausedForMemory)
			return false;

		reportNum++;

		// keep track of the last chunk we were at
		lastChunk.x = x;
		lastChunk.z = z;
		
		// Move of chunk
		x += 1;
		if (x > CoordXZ.blockToChunk(radius)){
			x = CoordXZ.blockToChunk(-radius);
			z += 1;
			if (z > CoordXZ.blockToChunk(radius)) {
				finish();
				return false;
			}
		}

		return true;
	}

	// for successful completion
	public void finish()
	{
		this.paused = true;
		reportProgress();
		sendMessage("Filling task successfully completed for world \"" + world.getName() + "\"!");
		this.stop();
	}

	// for cancelling prematurely
	public void cancel()
	{
		this.stop();
	}

	// we're done, whether finished or cancelled
	private void stop()
	{
		if (server == null)
			return;

		readyToGo = false;
		if (taskID != -1)
			server.getScheduler().cancelTask(taskID);
		server = null;

		// go ahead and unload any chunks we still have loaded
		while(!storedChunks.isEmpty())
		{
			CoordXZ coord = storedChunks.remove(0);
			if (!originalChunks.contains(coord))
				world.unloadChunkRequest(coord.x, coord.z);
		}
		
		callback.run();
	}

	// is this task still valid/workable?
	public boolean valid()
	{
		return this.server != null;
	}

	// handle pausing/unpausing the task
	public void pause()
	{
		if(this.pausedForMemory)
			pause(false);
		else
			pause(!this.paused);
	}
	public void pause(boolean pause)
	{
		if (this.pausedForMemory && !pause)
			this.pausedForMemory = false;
		else
			this.paused = pause;
		
		reportProgress();
	}
	public boolean isPaused()
	{
		return this.paused || this.pausedForMemory;
	}

	// let the user know how things are coming along
	private void reportProgress()
	{
		lastReport = Config.Now();
		sendMessage(reportNum + " chunks have been processed (" + (reportTotal + reportNum) + " total, around " + getFormattedPercentageCompleted() + ")");
		reportTotal += reportNum;
		reportNum = 0;
	}

	// send a message to the server console/log and possibly to an in-game player
	private void sendMessage(String text)
	{
		// Due to chunk generation eating up memory and Java being too slow about GC, we need to track memory availability
		int availMem = Config.AvailableMemory();

		Logger.log(Logger.LogLevel.INFO, (text + " (memory available: " + availMem + " MB)"));

		if (availMem < 200)
		{	// running low on memory, auto-pause
			pausedForMemory = true;
			text = "Available memory is very low. Pausing...";
			Logger.log(Logger.LogLevel.INFO, (text));
			// prod Java with a request to go ahead and do GC to clean unloaded chunks from memory; this seems to work wonders almost immediately
			// yes, explicit calls to System.gc() are normally bad, but in this case it otherwise can take a long long long time for Java to recover memory
			System.gc();
		}
	}
	
	public String getFormattedPercentageCompleted() {
		double perc = getPercentageCompleted();
		if (perc > 100) perc = 100;
		return WorldFillTask.coord.format(perc) + "%";
	}
	
	/**
	 * Get the percentage completed for the fill task.
	 * 
	 * @return Percentage
	 */
	public double getPercentageCompleted() {
		return ((double) (reportTotal + reportNum) / (double) reportTarget) * 100;
	}

	/**
	 * Amount of chunks completed for the fill task.
	 * 
	 * @return Number of chunks processed.
	 */
	public int getChunksCompleted() {
		return reportTotal;
	}

	/**
	 * Total amount of chunks that need to be generated for the fill task.
	 * 
	 * @return Number of chunks that need to be processed.
	 */
	public int getChunksTotal() {
		return reportTarget;
	}
}