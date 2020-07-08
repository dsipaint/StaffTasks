package listeners;

import java.util.ArrayList;

import main.Main;
import main.Utils;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tasks.Task.Priority;
import tasks.TaskReady;

public class ListListener extends ListenerAdapter
{
	//command for listing all the available tasks in the system- should only
	//display guild-relevant tasks
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		//staff command
		if(Utils.isStaff(e.getMember()))
		{
			String msg = e.getMessage().getContentRaw();
			String[] args = msg.split(" ");
			
			// :StaffList
			if(args[0].equalsIgnoreCase(Main.PREFIX + "StaffList"))
			{				
				// :StaffList
				if(args.length == 1)
				{
					ArrayList<TaskReady> guildtasks = Utils.getReadyTasks(Utils.getTasksByGuild(Main.tasks, e.getGuild()));
					if(guildtasks.isEmpty())
					{
						e.getChannel().sendMessage("There are no active tasks at the moment!").queue();
						return;
					}
					
					//list all priorities for a guild
					for(TaskReady t : guildtasks)
						e.getChannel().sendMessage(t.getEmbed("Task " + (guildtasks.indexOf(t) + 1)+ ":")).queue();
						
					
					return;
				}
								
				// :StaffList HP
				if(args[1].equalsIgnoreCase("HP"))
				{
					ArrayList<TaskReady> guildtasks = Utils.getReadyTasks(Utils.getTasksByGuild(Main.tasks, e.getGuild()));
					
					if(guildtasks.isEmpty())
					{
						e.getChannel().sendMessage("No high-priority tasks at the moment!").queue();
						return;
					}
					
					//list high priorities
					for(TaskReady t : guildtasks)
					{
						//task must be high priotrity
						if(t.getPriority().equals(Priority.HIGH))
							e.getChannel().sendMessage(t.getEmbed("Task " + (guildtasks.indexOf(t) + 1) + ":")).queue();

					}
					
					return;
				}
				else if(args[1].equalsIgnoreCase("MP")) // :StaffList MP
				{
					ArrayList<TaskReady> guildtasks = Utils.getReadyTasks(Utils.getTasksByGuild(Main.tasks, e.getGuild()));
					
					//list medium priorities
					for(TaskReady t : guildtasks)
					{
						//task must be medium priority
						if(t.getPriority().equals(Priority.MEDIUM))
							e.getChannel().sendMessage(((TaskReady) t).getEmbed("Task " + (guildtasks.indexOf(t) + 1) + ":")).queue();
					}
					
					return;
				}
				else if (args[1].equalsIgnoreCase("LP")) // :StaffList LP
				{
					ArrayList<TaskReady> guildtasks = Utils.getReadyTasks(Utils.getTasksByGuild(Main.tasks, e.getGuild()));
					
					if(guildtasks.isEmpty())
					{
						e.getChannel().sendMessage("No low-priority tasks at the moment!").queue();
						return;
					}
					
					//list low priorities
					for(TaskReady t : guildtasks)
					{
						//task must be low priotrity
						if(t.getPriority().equals(Priority.LOW))
							e.getChannel().sendMessage(((TaskReady) t).getEmbed("Task " + (guildtasks.indexOf(t) + 1) + ":")).queue();
					}
					
					return;
				}
			}
		}
	}
}
