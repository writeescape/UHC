package net.upd4ting.uhcreloaded.task;


public abstract class Task {
	
	private String name;
	
	private Long step; // Step en tick !!
	private Long lastStep;
	
	public Task(String name, Integer tick) {
		this.name = name;
		this.step = (long) tick * 50;
		this.lastStep = System.currentTimeMillis() - this.step;
	}
	
	public void processTick() {
		tick();
		this.lastStep = System.currentTimeMillis();
	}
	
	public abstract void tick();
	
	Boolean isElapsed() { return System.currentTimeMillis() - lastStep >= step; }
	
	public String getName() { return name; }
}
