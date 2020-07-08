package listeners;

import java.util.ArrayList;

import main.Main;
import main.Utils;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tasks.TaskReady;

public class ListCancelListener extends ListenerAdapter
{
	//listener for commands to delete tasks from the list of tasks
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		if(Utils.isStaff(e.getMember()))
		{
			String msg = e.getMessage().getContentRaw();
			String[] args = msg.split(" ");
			
			//:stafflist
			if(args[0].equalsIgnoreCase(Main.PREFIX + "stafflist"))
			{
				// :stafflist close
				if(args.length > 1 && args[1].equalsIgnoreCase("close"))
				{
					if(args.length > 2)
					{
						ArrayList<TaskReady> guildtasks = Utils.getReadyTasks(Utils.getTasksByGuild(Main.tasks, e.getGuild()));
						
						//if is an integer and is within the possible range of tasks
						if(args[2].matches("\\d+") &&
								Integer.parseInt(args[2]) > 0 && Integer.parseInt(args[2]) <= guildtasks.size())
						{
							//remove the task
							Main.tasks.remove(Main.tasks.indexOf(guildtasks.get(Integer.parseInt(args[2]) - 1 ))); //minus 1 to remove the correct task
							e.getChannel().sendMessage("Task " + args[2] + " closed.").queue();
						}
						else
							e.getChannel().sendMessage("please specify a valid task number!").queue();
					}
					else
						e.getChannel().sendMessage("please specify a valid task number!").queue();
				}
				
				return;
			}
		}
	}
}
