package tasks;
import java.time.Instant;
import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class TaskReady extends Task
{
	private Priority priority;
	private ArrayList<Note> notes;
	
	//change member to message object? has more metadata
	public TaskReady(Member member, String message, Priority priority, Instant time)
	{
		super(member, message, time);
		this.priority = priority;
		notes = new ArrayList<Note>();
	}
	
	//note: use this constructor to update tasks
	public TaskReady(TaskAwaitingPriority task, Priority priority)
	{
		super(task.getMember(), task.getMessage(), task.getTime());
		this.priority = priority;
		notes = new ArrayList<Note>();
	}
	
	public Priority getPriority()
	{
		return this.priority;
	}
	
	public ArrayList<Note> getNotes()
	{
		return this.notes;
	}
	
	//add a note to this task's notes
	public boolean addNote(Note n)
	{
		return this.notes.add(n);
	}
	
	//returns true if there are notes for this task
	public boolean hasNotes()
	{
		if(this.notes.isEmpty())
			return false;
		
		return true;
	}
	
	public MessageEmbed getEmbed(String title)
	{
		EmbedBuilder eb = new EmbedBuilder();
		
		//if member is no longer here, call them "banned user" and don't set a pfp
		eb = eb.setTitle(title)
		.setAuthor(this.getMember() == null ? "Banned user" : this.getMember().getUser().getAsTag(), null,
				this.getMember() == null ? null : this.getMember().getUser().getAvatarUrl())
		.addField("Priority", this.getPriority().toString(), true);
		
		if(this.hasNotes()) //might change aesthetics of this part later
			eb = eb.addField("(contains notes)", "", false);
		
		eb = eb.setDescription(this.getMessage())
		.setColor(65280) //I just hacked this out of dan's stuff basically
		.setTimestamp(this.getTime());
		
		return eb.build();
	}
	
	public MessageEmbed getNotesAsEmbed(String title)
	{
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(title);
		
		for(Note n : this.notes)
			eb = eb.appendDescription(n.toString() + "\n\n");
		
		eb = eb.setColor(65280); //hacked colour out of dan's stuff
		
		return eb.build();
	}
	
	//Note class inside TaskReady, made more sense to keep it here
	public static class Note
	{
		private Member member;
		private Instant time;
		private String message;
		
		//object for retaining note-information
		//we do not store information about which task this concerns, however (potential oversight?)
		public Note(Member member, Instant time, String message)
		{
			this.member = member;
			this.time = time;
			this.message = message;
		}
		
		public Member getMember()
		{
			return this.member;
		}
		
		//maybe remove time from constructor and create this object with Instant.now() in the constructor here?
		public Instant getTime()
		{
			return this.time;
		}
		
		public String getMessage()
		{
			return this.message;
		}
		
		@Override
		public String toString()
		{
			return this.getMember().getUser().getAsTag()
					+ " " + this.getTime().toString().replace("T", " ").replace("Z", "")
					+ ": " + this.getMessage();
		}
	}
}
