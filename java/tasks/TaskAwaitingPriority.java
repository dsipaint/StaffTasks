package tasks;
import java.time.Instant;

import net.dv8tion.jda.api.entities.Member;

public class TaskAwaitingPriority extends Task
{
	private String reactionid;
	
	//reactionid is the MESSAGE id, not the emote id or anything- this is used for message deletion purposes
	//change reactionid and member to just the message object? contains all the metadata
	public TaskAwaitingPriority(Member member, String message, String reactionid, Instant time)
	{
		super(member, message, time);
		this.reactionid = reactionid;
	}
	
	public String getReactionid()
	{
		return this.reactionid;
	}
}
