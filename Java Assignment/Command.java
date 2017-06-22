import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
/**
 * Commands class contains the list of command words to check whether the user has entered correct commands.
 * 
 * @author Arjun K Prasad
 * @version 2016.12.1
 */
public class Command
{
    //a HashMap variable that stores the user inputs to respectve commands (can be extendable).
    private HashMap<String, String> commandWords;
    //carries values of the above HashMap;
    private HashSet<String> commands1;
    //a HashMap variable that stores "yes" and "no" commands.
    private HashMap<String, String> yesNoCommands;
    //carries values of the above HashMap;
    private HashSet<String> commands2;

    /**
     * Store Command words to be made available.
     */
    public Command()
    {
        commandWords = new HashMap<>();
        yesNoCommands = new HashMap<>();
        addCommands();
        yesNoCommands();
        commandValueList();
    }

    /**
     * Adds all the commands the program needs to a HashMap in 2 sets of String and connect it to another string of program recognized commands.
     */
    private void addCommands()
    {
        commandWords.put("help",        "HELP");
        commandWords.put("moveto",      "MOVETO");
        commandWords.put("move",        "MOVE");
        commandWords.put("turnto",      "TURNTO");
        commandWords.put("turn",        "TURN");
        commandWords.put("color",       "COLOR");
        commandWords.put("colour",      "COLOR");
        commandWords.put("colors",      "COLOR");
        commandWords.put("colours",     "COLOR");
        commandWords.put("up",          "UP");
        commandWords.put("down",        "DOWN");
        commandWords.put("bye",         "BYE");
        commandWords.put("goodbye",     "BYE");
        commandWords.put("exit",        "BYE");
        commandWords.put("pen",         "PEN");
        commandWords.put("select",      "SELECT");
        commandWords.put("delete",      "DELETE");
        commandWords.put("remove",      "DELETE");
        commandWords.put("details",     "DETAIL");
        commandWords.put("detail",      "DETAIL");
        commandWords.put("deleted",     "DELETED");
        commandWords.put("pens",        "PENS");
        commandWords.put("recover",     "RECOVER");
        commandWords.put("deselect",    "DESELECT");
        commandWords.put("position",    "POSITION");
        commandWords.put("hi",          "HI");
        commandWords.put("heya",        "HI");
        commandWords.put("hey",         "HI");
        commandWords.put("whatsup",     "HI");
        commandWords.put("current",     "CURRENT");
    }

    /**
     * Adds the command values to separate HashSet.
     */
    private void commandValueList()
    {
        commands1 = new HashSet<>();
        Iterator it1 = commandWords.keySet().iterator();
        while (it1.hasNext()) {
            commands1.add(commandWords.get(it1.next()));
        }
        
        commands2 = new HashSet<>();
        Iterator it2 = commandWords.keySet().iterator();
        while (it2.hasNext()) {
            commands2.add(yesNoCommands.get(it2.next()));
        }
    }

    /**
     * Adds the yes and no commands
     */
    private void yesNoCommands()
    {
        yesNoCommands.put("yes",    "YES");
        yesNoCommands.put("ye",     "YES");
        yesNoCommands.put("y",      "YES");
        yesNoCommands.put("no",     "NO");
        yesNoCommands.put("na",     "NO");
        yesNoCommands.put("n",      "NO");
    }

    /**
     * Checks whether the user command matches one of the commands recognized by the program.
     * 
     * @param order The string that accepts the user command input.
     * @return Program recognized commands if it matches with user command or empty string.
     */
    public String checkCommand1(String order)
    {
        if(!order.equals(null) && !order.equals("")){
            order = order.trim().toLowerCase();                
            if(commandWords.containsKey(order)){
                return commandWords.get(order);
            }
        }
        return "";
    }

    /**
     * Checks whether the user command matches either yes or no commands.
     * 
     * @param order The string that accepts the user command input.
     * @return returns yes or no if it matches, else it retunrs null if null, else it returns error.
     */
    public String checkCommand2(String order)
    {
        if(!order.equals(null) && !order.equals("")){
            order = order.trim().toLowerCase();
            if(yesNoCommands.containsKey(order)){
                return yesNoCommands.get(order);
            }
        }
        else if(order.equals("")){
            return "";
        }
        return "error";
    }

    /**
     * Print all the Commands available when called.
     */
    public void help()
    {
        String tempString = "\r\n  Command Words:\r\n----------------------------------------------------\r\n";
        int temp = 0;
        for(String word: commands1){
            tempString += "  "+word;
            temp++;
            if(temp%5 == 0){
                tempString += "\r\n";
            }
        }
        System.out.println(tempString);
    }
}
