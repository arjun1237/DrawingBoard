import java.awt.Color;
import java.util.HashMap;
/**
 * ColorData class stores all the required colors and executes operations based on colors.
 * 
 * @author Arjun K Prasad 
 * @version 2016.12.1
 */
public class ColorData
{
    //a HashMap variable that stores the colors (can be extendable).
    private HashMap<String, Color> colors;
    
    /**
     * Adds all the colors the program needs in 2 sets of String and Color itself in a HashMap.
     */
    public ColorData()
    {        
        colors = new HashMap<>();
        addColors();
    }
    
    /**
     * Adds all the colors the program needs in 2 sets linking String of colors to Color itself in a HashMap.
     */
    private void addColors()
    {       
        colors.put("red",         Color.RED);
        colors.put("blue",        Color.BLUE);
        colors.put("yellow",      Color.YELLOW);
        colors.put("magenta",     Color.MAGENTA);
        colors.put("green",       Color.GREEN);
        colors.put("black",       Color.BLACK);
        colors.put("pink",        Color.PINK);
        colors.put("orange",      Color.ORANGE);
        colors.put("gray",        Color.GRAY);
        colors.put("cyan",        Color.CYAN);
    }
    
    /**
     * Checks whether the color asked by the user is available and returns it.
     * 
     * @param color Color input from the user.
     * @return The selected color if available, else null
     */
    public Color checkColor(String color)
    {
        color = color.trim().toLowerCase();
        if(colors.containsKey(color)){
            return colors.get(color);
        }
        return null;
    }

    /**
     * Print the list of colors available.
     */
    public void printColors()
    {
        String colorConcat = "\r\n   Colors available:\r\n----------------------------------------------------\r\n";
        int tempNum = 0;
        for(String tempColor: colors.keySet()){
            colorConcat += "   "+tempColor.toUpperCase();
            tempNum++;
            if(tempNum%5 == 0){
                colorConcat += "\r\n";
            }
        }
        System.out.println(colorConcat);
    }

    /**
     * Print the list of colors available.
     * 
     * @param color the color value
     * @return The selected color to equivalent string values
     */
    public String colorToString(Color color)
    {
        for(String tempColor: colors.keySet()){
            if(colors.get(tempColor) == color){
                return tempColor;
            }
        }
        String tempColor = color.toString();
        return tempColor.substring(tempColor.length()-16,tempColor.length());
    }
}
