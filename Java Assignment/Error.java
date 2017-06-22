
/**
 * Error class prints the appropriate error messages when called.
 * 
 * @author Arjun 
 * @version 2016.12.1
 */
public class Error
{
    public Error()
    {        
        // Nothing to declare .... //
    }    
    
    /**
     * Displays the error message for command words user input if it exceeds the given limit.
     * 
     * @param firstWord the main command word.
     * @param commandSize the word size of the command line input.
     */
    public void sizeErrorMessage(String firstWord, int commandSize)
    {
        System.out.println("To execute the " +firstWord+ " command, the word size in the command line needs to be exactly "+ commandSize+ " words or numbers as per the command");
    }    
    
    /**
     * Displays the error message for command words whose minimum limit is unknown.
     * 
     * @param firstWord the main command word.
     * @param commandSize maximum limit of the command line.
     */
    public void sizeErrorMessage2(String firstWord, int commandSize)
    {
        System.out.println("To execute the " +firstWord+ " command, the word size in the command line cannot exceed " +commandSize+ " words or numbers.");
    }    
    
    /**
     * Displays the default error message for command words user input if it exceeds given limit.
     */
    public void defaultSizeError()
    {
        System.out.println("The system was overwhelmed with extra unnecessary information to implement the command. Please retype.");
    }
    
    /**
     * Displays error message if the pen does not exist.
     * 
     * @param name pen name is received.
     */
    public void penNameError(String name)
    {
        System.out.println("The pen with the name \"" +name+ "\" does not exist.");
    }
    
    /**
     * Displays error message that the pen does not exist.
     * 
     * @param name pen name.
     * @param list list from where the pen was not to be found.
     */
    public void penNameError(String name, String list)
    {
        System.out.println(list+ " list does not contain pen with the name: \"" +name+ "\".");
    }
    
    /**
     * Displays that the list contains no pen.
     * 
     * @param list list from where the pen was not to be found.
     */
    public void nullPenError(String list)
    {
        System.out.println(list+ " list contains no pen currently.");
    }
    
    /**
     * Displays error message for null response from user.
     */
    public void nullError()
    {
        System.out.println("Dont PLAY with your keyborad.");
    }
    
    /**
     * Displays error message for wrong command.
     * 
     * @param wrongCommand the wrong command typed by the user.
     */
    public void commandError(String wrongCommand)
    {
        System.out.println("Can't-Recognize-Command-Exception: \"" +wrongCommand+ "\"");
    }    
    
}
