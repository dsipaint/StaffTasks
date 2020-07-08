package listeners;

import main.Main;
import main.Utils;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StopListener extends ListenerAdapter {
	
  public void onGuildMessageReceived(GuildMessageReceivedEvent e)
  {
    String msg = e.getMessage().getContentRaw();
    String[] args = msg.split(" ");

    
    if(Utils.isStaff(e.getMember()))
    {
      if(args[0].equalsIgnoreCase(Main.PREFIX + "shutdown"))
      {
        e.getJDA().shutdownNow();
        System.exit(0);
      } 

      
      if(args[0].equalsIgnoreCase(Main.PREFIX + "disable") && args[1].equalsIgnoreCase("stafftodo"))
      {
        e.getChannel().sendMessage("*Disabling al's todolist code...*").queue();
        e.getJDA().shutdown();
        System.exit(0);
      } 
    } 
  }
}