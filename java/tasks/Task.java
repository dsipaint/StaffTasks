package tasks;
import java.time.Instant;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public abstract class Task
{
	public static enum Priority
	{
		LOW, MEDIUM, HIGH;
	}
	
	private Member member;
	private String message;
	private Instant time;
	
	public Task(Member member, String message, Instant time)
	{
		this.member = member;
		this.message = message;
		this.time = time; //the Instant maintains a record of when the task was created
	}
	
	public Member getMember()
	{
		return this.member;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
	public Instant getTime()
	{
		return this.time;
	}
	
	public Guild getGuild()
	{
		if(this.getMember() == null)
			return null;
		
		return this.getMember().getGuild();
	}
}
