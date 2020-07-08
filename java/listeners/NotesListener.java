package listeners;

import java.time.Instant;
import java.util.ArrayList;

import main.Main;
import main.Utils;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tasks.TaskReady;
import tasks.TaskReady.Note;

public class NotesListener extends ListenerAdapter
{
	//staff commands to do with adding/viewing notes for a given task
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		if(Utils.isStaff(e.getMember()))
		{
			String msg = e.getMessage().getContentRaw();
			String[] args = msg.split(" ");
			
			// :stafflist
			if(args[0].equalsIgnoreCase(Main.PREFIX + "stafflist"))
			{
				if(args.length == 1)
					return;
				
				// :stafflist addnote
				if(args[1].equalsIgnoreCase("addnote"))
				{
					ArrayList<TaskReady> guildtasks = Utils.getReadyTasks(Utils.getTasksByGuild(Main.tasks, e.getGuild()));
					
					//if an integer and number is in the correct range
					if(args.length > 2 && args[2].matches("\\d+") && Integer.parseInt(args[2]) > 0
							&& Integer.parseInt(args[2]) <= guildtasks.size())
					{
						if(!(args.length > 3))
						{
							e.getChannel().sendMessage("Please specify a note to add to this task!").queue();
							return;
						}
						
						//extracting the message
						String message = "";
						for(int i = 3; i < args.length; i++)
							message += " " + args[i];
						
						message = message.substring(1);
						
						//add the note (clean up by just referencing guildtasks.get()? )
						((TaskReady) Main.tasks.get(Main.tasks.indexOf(guildtasks.get(Integer.parseInt(args[2]) - 1))))
							.addNote(new Note(e.getMember(), Instant.now(), message));
						
						e.getChannel().sendMessage("Note added to task " + args[2]).queue();
					}
					else
						e.getChannel().sendMessage("please specify a valid task number!").queue();
					
					return;
				}
				
				// :stafflist viewnotes
				if(args[1].equalsIgnoreCase("viewnotes"))
				{
					ArrayList<TaskReady> guildtasks = Utils.getReadyTasks(Utils.getTasksByGuild(Main.tasks, e.getGuild()));
					
					//if an integer and number is in the correct range
					if(args.length > 2 && args[2].matches("\\d+") && Integer.parseInt(args[2]) > 0
							&& Integer.parseInt(args[2]) <= guildtasks.size())
					{
						e.getChannel().sendMessage(guildtasks.get(Integer.parseInt(args[2]) - 1)
								.getNotesAsEmbed("Notes for task " + args[2] + ":")).queue();
					}
					else
						e.getChannel().sendMessage("please specify a valid task number!").queue();
					
					return;
				}
			}
		}
	}
}
