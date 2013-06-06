package mcteleport.timer;

import mcteleport.MCTeleport;

import org.bukkit.entity.Player;

public class BroadcastTimer extends Thread {
	
	Player player;
	String message;
	BroadcastListener listener;
	long currentTime;
	int currentIndex;
	long[] timeIntervals = new long[]{18000,10800, 3600,1800,1200,600,300,120,60,30,20,10,5,4,3,2,1,0};
	
	/**
	 * Creating this object will automatically start a broadcast
	 * message which will automatically broadcast in intervals
	 * which decrease as you get closer to the event
	 * 
	 * @param player Who will receive this message
	 * @param message The message to broadcast - #t will be replaced with time remaining
	 * @param time How long to broadcast this message for (in seconds)
	 * @param listener this will be used when the broadcast is finished
	 */
	public BroadcastTimer(Player player, String message, long time, BroadcastListener listener) {
		this.player = player;
		this.message = message;
		currentTime = time;
		this.listener = listener;
		currentIndex = timeIntervals.length-1;
		while(currentTime > timeIntervals[currentIndex]) {
			currentIndex--;
			if (currentIndex == -1) break;
		}
		start();
	}
	
	@Override
	public void run() {
		long timeToSleep = currentTime-timeIntervals[currentIndex];
		try{ Thread.sleep(timeToSleep*1000); } catch(Exception e) {/*should probs log this*/ return;}
		while(currentIndex < timeIntervals.length-1) {
			if(message != null) {
				String msg = message.replace("#t", MCTeleport.secondsToString(timeIntervals[currentIndex]));
				player.sendMessage(msg);
			}
			timeToSleep = timeIntervals[currentIndex]-timeIntervals[currentIndex+1];
			currentIndex++;
			try{ Thread.sleep(timeToSleep*1000); } catch(Exception e) {/*should probs log this*/ break;}
		}
		listener.countdownComplete(player);
	}
}
