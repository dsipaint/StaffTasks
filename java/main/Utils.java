package main;

import java.util.ArrayList;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import tasks.Task;
import tasks.TaskReady;

public class Utils
{	
	//return true if a member has discord mod, admin or is owner
	public static boolean isStaff(Member m)
	{
		//if owner
		if(m.isOwner())
			return true;
		
		//if admin
		if(m.hasPermission(Permission.ADMINISTRATOR))
			return true;
		
		//if discord mod TODO: Make discord mod module for all servers
		switch(m.getGuild().getId())
		{
			case "565623426501443584" : //wilbur's discord
				for(Role r : m.getRoles())
				{
					if(r.getId().equals("565626094917648386")) //wilbur discord mod
						return true;
				}
				break;
				
			case "640254333807755304" : //charlie's server
				for(Role r : m.getRoles())
				{
					if(r.getId().equals("640255355401535499")) //charlie discord mod
						return true;
				}
				break;
		}
		
		return false;
	}
	
	//return all tasks from a certain guild
	public static ArrayList<Task> getTasksByGuild(ArrayList<Task> tasks, Guild g)
	{	
		ArrayList<Task> returntasks = new ArrayList<Task>();
		
		for(Task t : tasks)
		{
			if(t.getGuild() == null) //don't add null values
				continue;
			else if(t.getGuild().equals(g))
				returntasks.add(t);
		}
		
		return returntasks;
	}
	
	public static ArrayList<TaskReady> getReadyTasks(ArrayList<Task> tasks)
	{
		ArrayList<TaskReady> readytasks = new ArrayList<TaskReady>();
		
		for(Task t : tasks)
		{
			if(t instanceof TaskReady)
				readytasks.add((TaskReady) t);
		}
		
		return readytasks;
	}
	
	//only send the embed to the modlogs channel for the server it was created in
	public static void sendToModLogs(MessageEmbed emb, Guild g)
	{
		//TODO undo server hard-coding here
		switch(g.getId())
		{
			case "565623426501443584": //wilbur's discord
				g.getTextChannelById("565631919728099338").sendMessage(emb).queue(); //send to wilbur modlogs
				break;
				
			case "640254333807755304": //charlie's discord
				g.getTextChannelById("642424285344038941").sendMessage(emb).queue(); //send to charlie modlogs
				break;
				
			default:
				return;
		}
	}
}
