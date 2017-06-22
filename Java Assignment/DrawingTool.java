import java.awt.Color;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class drawingTool - enable a user to create multiple pens and draw on canvas using it.
 * Accept commands from user and accordingly make changes on canvas.
 *
 * @author David J. Barnes, Michael Kölling, Arjun K Prasad
 * @version 2016.12.1
 */
public class DrawingTool
{
    // Canvas tool to draw onto.
    private Canvas canvas;
    // The drawing pen which is used currently.
    private Pen currentPen;
    // The name of the pen that is being currently used.
    private String currentPenName;
    // Stores the color of the pen currently in use.
    private String currentPenColor;
    // Source of user commands.
    private InputReader reader;
    // Source to check user commands.
    private Command command;
    // Source to check the Color data
    private ColorData colorData;
    // holds boolean value for drawing.
    private boolean finished;
    // holds all the pen tools.
    private HashMap<String,Pen> penTools;
    // holds all the deleted pen tools.
    private HashMap<String,Pen> deletedPen;
    // for system.in
    private Scanner scanner;
    // Sourse to access and print error messages
    private Error error;

    /**
     * Prepare to draw on a canvas of default size.
     * The pen starts up at position (0, 0) and its
     * colour is black.
     */
    public DrawingTool()
    {
        this(500, 400);
    }

    /**
     * Prepare to draw on a canvas of specified size.
     * Prints Welcome statement and initializes the current pen to null.
     * The pen starts up at position (0, 0) and its
     * colour is black.
     * 
     * @param width The canvas width.
     * @param height The canvas height.
     */
    public DrawingTool(int width, int height)
    {
        canvas = new Canvas("Drawing Program", width, height);
        command = new Command();
        reader = new InputReader();
        penTools = new HashMap<>();
        deletedPen = new HashMap<>();
        currentPenName = new String();
        colorData = new ColorData();
        error = new Error();
        printWelcome();
        currentPen = null;
        draw();
    }

    /**
     * Add new Pen each time this method is called.
     * Set the new pen as current pen to be used
     * 
     * @param penName name of the pen.
     */
    private void addPen(String penName)
    {
        penTools.put(penName, new Pen(0, 0, canvas));
        currentPen = penTools.get(penName);
        currentPen.setColor(Color.BLACK);
        currentPen.penUp();
        System.out.println();
        currentPenName = penName;
        System.out.println("A new pen named \"" +penName+ "\" has been added.");   
        penDetails(penName);  
        System.out.println("Color:              BLACK"); 
        System.out.println("The pen has been automatically selected for you.");
    }

    /**
     * Print pen details for the given pen name.
     * 
     * @param penName name of the pen.
     */
    private void penDetails(String penName)
    {   
        System.out.println("   DETAILS:");    
        printPenName(penName); 
        getPenPosition(penName);
        getPenAngle(penName);
        upOrDown(penName);
    }

    /**
     * Print pen position.
     * 
     * @param penName name of the pen.
     */
    private void getPenPosition(String penName)
    {   
        System.out.println("Pen Position:       " +penTools.get(penName).getX()+ " (X axis), " +penTools.get(penName).getY()+ " (Y axis).");
    }

    /**
     * Print pen angle.
     * 
     * @param penName name of the pen.
     */
    private void getPenAngle(String penName)
    {   
        int angle = penTools.get(penName).getRotation();
        if(angle >= 360){
            int temp = angle/360;
            angle = angle-(360*temp);
        }
        System.out.println("Pen angle:          " +angle+ " degrees from right.");
    }

    /**
     * Print whether the pen is up or down.
     * 
     * @param penName name of the pen.
     */
    private void upOrDown(String penName)
    {   
        System.out.println("Pen UP/DOWN?:       " +(penTools.get(penName).isPenDown() ? "DOWN" : "UP") +""+ (penTools.get(penName).isPenDown() ? " Feel free to draw." : ""));
    }

    /**
     * Displays the name of the current pen.
     * 
     * @param penName the name of the current pen.
     */
    private void printPenName(String penName)
    {
        System.out.println("Pen Name:           " +penName.toUpperCase()); 
    }

    /**
     * Prints the welcome message when called.
     */
    private void printWelcome()
    {
        System.out.println("*******************************************************************");
        System.out.println("*******************************************************************");
        System.out.println("* WELCOME TO CREATICONS          *       **************************");
        System.out.println("* ---------------------         * *        ************************");
        System.out.println("* bringing life into art   * * * * * * *     **********************");
        System.out.println("*     for centuries           *     *       ***********************");
        System.out.println("*                            *   *   *       **********************");
        System.out.println("*      estd. 1900           *         *       *********************");
        System.out.println("*******************************************************************");
        System.out.println("*******************************************************************");
        System.out.println();
        System.out.println("    for help -> type HELP");
        System.out.println("    to exit  -> type BYE");
        System.out.println("    to start -> type PEN (name) -> give any one word name");
        System.out.println("    * HAPPY * DRAWING *");
        System.out.println();
    }

    /**
     * Print the Bye command when called.
     */
    private void printBye()
    {
        System.out.println("It was fun drawing with you.");
        System.out.println("   Bye. See you later.");
    }

    /**
     * Allow the user to draw on the canvas by typing commands.
     * Get user input.
     */
    private void draw()
    {
        finished = false;
        while(!finished){
            String firstWord = "";
            String secondWord = "";
            String thirdWord = "";
            String fourthWord = "";
            LinkedList<String> userCommand = reader.getInput();
            int commandSize = userCommand.size();
            if(commandSize < 6){
                if(!userCommand.isEmpty()) {
                    firstWord = userCommand.pop();                
                }
                if(!userCommand.isEmpty()) {
                    secondWord = userCommand.pop();                
                }
                if(!userCommand.isEmpty()) {
                    thirdWord = userCommand.pop();                
                }
                if(!userCommand.isEmpty()) {
                    fourthWord = userCommand.pop();                
                }
                if(commandSize != 0){
                    interpretCommand(firstWord, secondWord, thirdWord, fourthWord, commandSize);
                }
                else{
                    error.nullError();
                    System.out.println();
                }
            }
            else {
                error.defaultSizeError();
                System.out.println();
            }
        }
    }

    /**
     * Take the first three command values and analyze
     * 
     * @param firstWord first word from inputline.
     * @param secondWord second word from inputline.
     * @param thirdWord third word from inputline.
     * @param fourthWord third word from inputline.
     * @param commandSize no. of words from the inputline.
     */
    private void interpretCommand(String firstWord, String secondWord, String thirdWord, String fourthWord, int commandSize)
    {
        boolean isCommandWrong = false;
        String tempWord = command.checkCommand1(firstWord);
        if(tempWord.equals("")){
            error.commandError(firstWord);            
            System.out.println();
            return;
        }
        // These commands should work even if there are no pens selected.
        switch(tempWord){
            case "BYE":
            if(commandSize == 1){
                printBye();
                finished = true;
            }
            else{
                error.sizeErrorMessage(firstWord,1);
            }
            break;
            case "HELP":
            if(commandSize == 1){
                command.help();
            }
            else{
                error.sizeErrorMessage(firstWord,1);
            }
            break;
            case "HI":
            if(commandSize == 1){
                greet();
            }
            else{
                error.sizeErrorMessage(firstWord,1);
            }
            break;
            case "PENS":
            if(commandSize == 1){
                showAvailablePens();
            }
            else{
                error.sizeErrorMessage(firstWord,1);
            }
            break;
            case "DELETED":
            if(commandSize == 1){
                showDeletedPens();
            }
            else{
                error.sizeErrorMessage(firstWord,1);
            }
            break;
            case "DESELECT":
            if(commandSize == 1){
                deselect();
            }
            else{
                error.sizeErrorMessage(firstWord,1);
            }
            break;
            case "PEN":
            if(commandSize <= 2){
                penAdd(secondWord);
            }
            else{
                error.sizeErrorMessage(firstWord,2);
            }
            break;
            case "DELETE":
            if(commandSize <= 2){
                delete(secondWord);
            }
            else{
                error.sizeErrorMessage(firstWord,2);
            }
            break;
            case "RECOVER":
            if(commandSize <= 2){
                recover(secondWord);
            }
            else{
                error.sizeErrorMessage(firstWord,2);
            }
            break;
            case "DETAIL":
            if(commandSize <= 2){
                detail(secondWord);
            }
            else{
                error.sizeErrorMessage(firstWord,2);
            }
            break;
            case "SELECT":
            if(commandSize <= 2){
                select(secondWord);
            }
            else{
                error.sizeErrorMessage(firstWord,2);
            }
            break;
            default:
            isCommandWrong = true;
            break;
        }

        // These commands are to be accessed only if a pen is selected.
        if(currentPen != null && isCommandWrong == true){
            isCommandWrong = false;
            switch(tempWord){
                case "CURRENT":
                if(commandSize == 1){
                    penDetails(currentPenName);
                }
                else{
                    error.sizeErrorMessage(firstWord,1);
                }
                break;
                case "POSITION":
                if(commandSize == 1){
                    getPenPosition(currentPenName); 
                }
                else{
                    error.sizeErrorMessage(firstWord,1);
                }
                break;
                case "UP":
                if(commandSize == 1){
                    currentPen.penUp();
                    upOrDown(currentPenName);
                }
                else{
                    error.sizeErrorMessage(firstWord,1);
                }
                break;
                case "DOWN":
                if(commandSize == 1){
                    currentPen.penDown();
                    upOrDown(currentPenName);
                }
                else{
                    error.sizeErrorMessage(firstWord,1);
                }
                break;
                case "MOVE":
                if(commandSize <= 2){
                    move(secondWord);
                }
                else{
                    error.sizeErrorMessage(firstWord,2);
                }
                break;
                case "MOVETO":
                if(commandSize <= 3){
                    moveTo(secondWord, thirdWord);
                }
                else{
                    error.sizeErrorMessage(firstWord,3);
                }
                break;
                case "COLOR":
                if(commandSize <= 4){
                    color(secondWord, thirdWord, fourthWord, commandSize);
                }
                else{
                    error.sizeErrorMessage2(firstWord, 4);
                }
                break;
                case "TURN":
                if(commandSize <= 2){
                    turn(secondWord);
                }
                else{
                    error.sizeErrorMessage(firstWord,2);
                }
                break;
                case "TURNTO":
                if(commandSize <= 2){
                    turnTo(secondWord);
                }
                else{
                    error.sizeErrorMessage(firstWord,2);
                }
                break;
                default:
                isCommandWrong = true;
                break;
            }
        }
        else if (isCommandWrong == true){
            System.out.println("FYI: Currently none of the pens are in use.");
        }
        System.out.println();
    }

    /**
     * Print the greeting command when called
     */    
    private void greet()
    {
        System.out.println("Hi...! Now get back to drawing.");
    }

    /**
     * Deselect current pen in use or let the user know that no pen is in use currently.
     */
    private void deselect()
    {
        if(currentPen != null){
            currentPen = null;
            System.out.println("Your pen \"" +currentPenName+ "\" has been deselected.");
            currentPenName = null;
            System.out.println("Create a new Pen tool or select any existing pen tool to continue drawing.");
        }
        else{
            System.out.println("Currently no pen tool is in use to deselect.");
        }
    }

    /**
     * Recover the deleted pen if present or print respective message.
     * 
     * @param secondWord name of the pen.
     */
    private void recover(String secondWord)
    {
        if(!secondWord.equals("")){
            if(deletedPen.containsKey(secondWord)){
                penTools.put(secondWord, deletedPen.get(secondWord));
                deletedPen.remove(secondWord);
                System.out.println("The deleted Pen tool \"" +secondWord+ "\" has been recovered. To use it, select.");
            }
            else{
                error.penNameError(secondWord, "Deleted");
            }
        }
        else{
            System.out.println("Recover what?");
        }
    }

    /**
     * Print all the pens available or print message letting user know that none exist.
     */
    private void showAvailablePens()
    {
        if(penTools.size() != 0){
            String stringPens = "AVAILABLE Pens:";
            for(String temp : penTools.keySet()){
                stringPens += "   "+temp;
            }
            System.out.println(stringPens);
        }
        else{
            error.nullPenError("Available Pen");
        }
    }

    /**
     * Print all the deleted pens or print message that none exist.
     */
    private void showDeletedPens()
    {
        if(deletedPen.size() != 0){
            String stringDelete = "DELETED Pens:";
            for(String temp : deletedPen.keySet()){
                stringDelete += "   "+temp;
            }
            System.out.println(stringDelete);
        }
        else{
            error.nullPenError("Deleted Pen");
        }
    }

    /**
     * Prints details of the currently selected pen or print message saying no pen is currently selected
     * 
     * @param secondWord name of the pen.
     */
    private void detail(String secondWord)
    {
        if(!secondWord.equals("")){
            if(penTools.containsKey(secondWord)){
                penDetails(secondWord);
            }
            else{
                error.penNameError(secondWord);
            }
        }
        else{
            System.out.println("Please rewrite the command along with the existing Pen name to get details.");
        }
    }

    /**
     * delete the particular pen if available or print message saying it does not exist.
     * 
     * @param secondWord name of the pen.
     */
    private void delete(String secondWord)
    {
        if(!secondWord.equals("")){
            if(penTools.containsKey(secondWord)){
                deletedPen.put(secondWord, penTools.get(secondWord));   
                System.out.println("The Pen \""+secondWord+"\" has been deleted.");
                if (penTools.get(secondWord) == currentPen){
                    System.out.println("The deleted pen was in select mode. To continue drawing, select another pen or get a new one.");
                }
                penTools.remove(secondWord);
                if(penTools.size() == 0){
                    System.out.println("You currently have no more Pen Tools left.");
                }
                currentPen = null;
                currentPenName = null;
            }
            else{
                error.penNameError(secondWord);
            }
        }
        else{
            System.out.println("Delete which pen?");
        }
    }

    /**
     * select the particular pen if available or print message saying it does not exist.
     * 
     * @param secondWord name of the pen.
     */
    private void select(String secondWord)
    {
        if(!secondWord.equals("")){
            if(penTools.containsKey(secondWord)){
                currentPen = penTools.get(secondWord);
                currentPenName = secondWord;
                System.out.println();
                System.out.println("Your current Pen has been changed");
                penDetails(currentPenName);
            }
            else{
                error.penNameError(secondWord);
            }
        }
        else{
            System.out.println("Select which pen?");
        }
    }

    /**
     * get user input.
     * 
     * @return return String value of user input.
     */    
    private String getInput()
    {
        scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine().trim().toLowerCase();
        return inputLine;
    }

    /**
     * add the pen if it does not exist, else print saying it does.
     * 
     * @param secondWord name of the pen to be added.
     */    
    private void penAdd(String secondWord)
    {
        if(!secondWord.equals("")){
            if(penTools.containsKey(secondWord)){
                System.out.println("A pen with the name \"" +secondWord+ "\" was used previously. Try another name.");                        
            }
            else if(deletedPen.containsKey(secondWord)){
                boolean rightCommand = false;
                System.out.println();
                System.out.println("A pen with the name \"" +secondWord+ "\" was in \"deleted\" list. Do you want to overwrite it?");
                while(!rightCommand){
                    System.out.print("> ");
                    String ask = command.checkCommand2(getInput());
                    switch(ask){
                        case "NO":
                            System.out.println("Pen tool \"" +secondWord+ "\" was NOT created.");
                            rightCommand = true;
                            break;
                        case "YES":
                            addPen(secondWord);
                            deletedPen.remove(secondWord);
                            rightCommand = true;
                            break;
                        case "":
                            error.nullError();
                            System.out.println();
                            break;
                        default:
                            System.out.println();
                            System.out.println("I dont think you understood the question.");
                            System.out.println("A pen with the name \"" +secondWord+ "\" was in \"deleted\" list. Do you want to overwrite it?");
                            break;
                    }
                }
            }
            else{
                addPen(secondWord);
                currentPen = penTools.get(secondWord);
                currentPenName = secondWord;
            }
        }
        else{
            System.out.println("Please rewrite the command along with new Pen name.");
        }
    }

    /**
     * turn the degree of angle to the said direction.
     * 
     * @param secondWord degree direction.
     */
    private void turnTo(String secondWord)
    {
        if(reader.isAnInteger(secondWord)){
            int second = reader.convertToInteger(secondWord);
            currentPen.turnTo(second);
            getPenAngle(currentPenName);
        }
        else{
            System.out.println("Turn to what angle?");
        }
    }

    /**
     * turn the angle by particular degree from the current.
     * 
     * @param secondWord degree to the turned from current.
     */
    private void turn(String secondWord)
    {
        if(reader.isAnInteger(secondWord)){
            int second = reader.convertToInteger(secondWord);
            currentPen.turn(second);
            getPenAngle(currentPenName);
        }
        else{
            System.out.println("How much to turn?");
        }
    }

    /**
     * changes the color of the pen tool.
     * 
     * @param secondWord color to be changed to or the R value.
     * @param thirdWord G value.
     * @param secondWord B value.
     * @param secondWord inputline size.
     */    
    private void color(String secondWord, String thirdWord, String fourthWord, int commandSize)
    {
        int second = -1;
        int third = -1;
        int fourth = -1;
        switch(commandSize){
            case 1:
            colorData.printColors();
            System.out.println("Please rewrite the command by including any of the above color.");
            System.out.println("Alternatively, you can set any color by just typing Color followed by RGB values.");
            break;
            case 2:
            Color color = colorData.checkColor(secondWord);
            if(color != null){
                currentPen.setColor(color);
                System.out.println("The color of your pen has been changed to: " +secondWord.toUpperCase()+ ".");
            }
            else{
                System.out.println("The pen does not come in \"" +secondWord+ "\" color. Please try a different color.");
                System.out.println("Alternatively, you can set any color by just typing Color followed by RGB values.");
            }
            break;
            case 3:
            System.out.println("Your command line must be missing an extra number or you might have entered an extra word. Please retype.");
            break;
            case 4:
            boolean isR_Integer = false;
            boolean isG_Integer = false;
            boolean isB_Integer = false;
            if(reader.isAnInteger(secondWord)){
                second = reader.convertToInteger(secondWord);
                isR_Integer = true;
            }
            if(reader.isAnInteger(thirdWord)){
                third = reader.convertToInteger(thirdWord);
                isG_Integer = true;
            }
            if(reader.isAnInteger(fourthWord)){
                fourth = reader.convertToInteger(fourthWord);
                isB_Integer = true;
            }
            if(isR_Integer && isG_Integer && isB_Integer){
                if((second >= 0 && second < 256) && (third >= 0 && third < 256) && (fourth >= 0 && fourth < 256)){
                    color = new Color(second,third,fourth);
                    currentPen.setColor(color);
                    String colorName = color.toString();
                    int index = colorName.indexOf('[');
                    System.out.println("The color of your pen has been changed to: " +colorName.substring(index).toUpperCase()+ ".");
                }
                else{
                    System.out.println("Either none or some of the RGB color values seems to be out of 0-255 range. Please retype");
                }   
            }
            else{
                System.out.println("Seems like either none or some of the RGB values are not Integer type. Please retype.");
            }
        }
    }
    
    /**
     * moves the pen to a particular position in the canvas.
     * 
     * @param secondWord X axis to be moved to.
     * @param thirdWord Y axis to be moved to.
     */
    private void moveTo(String secondWord, String thirdWord)
    {
        if(reader.isAnInteger(secondWord)){
            int second = reader.convertToInteger(secondWord);
            if(reader.isAnInteger(thirdWord)){
                int third = reader.convertToInteger(thirdWord);
                currentPen.moveTo(second,third);
                getPenPosition(currentPenName);
            }
            else{
                System.out.println("Please enter the command again with Y axis value");
            }
        }
        else{
            System.out.println("Where to move?");
        }
    }

    /**
     * moves the pen particular distance in the current direction.
     * 
     * @param secondWord no. of pixels to be moved in the current direction.
     */
    private void move(String secondWord)
    {
        if(reader.isAnInteger(secondWord)){
            int second = reader.convertToInteger(secondWord);
            currentPen.move(second);
            getPenPosition(currentPenName);
        }
        else{
            System.out.println("How much to move?");
        }
    }

}
