import java.util.ArrayList;
import java.util.Random;

/**
 * Row.java
 * Stores row information so
 * that it is easier to access
 * Places. This also sudo-randomly
 * generates the rows using
 * pre-determined, valid rows.
 *
 * @author Ben Forman
 * @version April 20, 2026
 */

public class Row {
    private Place[] row;
    private PlaceType type;
    private static String[][] possibleRowsString = {
            {"GGBBGGBG", "GBGGBGBG"},
            {"GGBBGGGB", "GGBGGBBG"},
            {"GGBBGBBG", "GBGGGBBG"},
            {"BGBBGGGB", "GGBGGGBG"},
            {"WWLWWLWW", "WWLWWLWW"},
            {"WLWWWWLW", "WLLWWLWW"},
            {"GBBBBGGG", "GGGGBBBB"},
            {"LWWWLLLW", "LLLLWWWW"},
            {"GGBBBGGB", "BGGBBGBG", "BGBBGBGB"},
            {"WWLWLLWW", "WLLWWLWW", "WWLWLLWW"},
            {"GBBGGGBB", "BGGGBGBG", "GBBGGGBB"},
            {"WLWWWWLW", "LLWWWWLL", "WLWWWWLW"},
            {"GBBBBGGG", "GBGGBBBG", "GGGGBBBB"},
            {"LWWWLLLW", "LWLLWWLW", "LLLLWWWW"},
            {"GGBGBBGG", "BGBGBGGB", "GGBGBBGG"},
            {"GBBBGGBG", "GGBBGGBG", "BGGBGGBG", "GGBBBGGB"},
            {"LWWWLLLW", "LWLLWWLW", "LLWLWWLW", "LLLLWWWW"},
            {"GGBBGBBG", "GBGGBGBG", "GGBGGGBG", "BGBBGGBG"},
            {"LWWWLLLW", "LWLLWWLW", "LLWLWWLW", "LLLLWWWW"},
            {"GBBBBGGG", "GBBGBBGG", "GBGGBBBG", "GGGGBBBB"},
            {"LWWWLLLW", "LWLLWWLW", "LLWLWWLW", "LLLLWWWW"},
            {"GGBBGBBG", "GBGGBGBG", "BGBBGBBG", "GGBGGBBG"}
    };

    public Row(int size, PlaceType type) {
        row = new Place[size];
        this.type = type;
    }

    // Create the clone method functionality
    @Override
    public Row clone() {
        Row clone = new Row(row.length, type);
        for (int i = 0; i < row.length; i++) {
            clone.row[i] = row[i].clone();
        }
        return clone;
    }

    public Place get(int index) {
        return row[index];
    }

    public int length() {
        return row.length;
    }

    // Helper String parse method
    private static Row parseRow(String rowString) {
        char[] letters = rowString.toCharArray();
        // Determine what type of row is being parsed.
        PlaceType typ = (letters[0] == 'G' || letters[0] == 'B')
                ? PlaceType.GRASS : PlaceType.WATER;
        Row parsed = new Row(rowString.length(), typ);
        for (int i = 0; i < rowString.length(); i++) {
            if (typ == PlaceType.GRASS) {
                parsed.row[i] = new Grass();
                if (letters[i] == 'B')
                    parsed.row[i].setObstacle(new Bush());
            } else {
                parsed.row[i] = new Water();
                if (letters[i] == 'L')
                    parsed.row[i].setObstacle(new Lilypad());
            }
        }
        return parsed;
    }

    public PlaceType type() {
        return type;
    }

    // Initializes and returns a Row[] which can be used as the game board.
    // Parameter determines the number of combinations we should use.
    public static Row[] init(int length) {
        // Create a road row
        Row road = new Row(possibleRowsString[0][0].length(), PlaceType.ROAD);
        for (int i = 0; i < possibleRowsString[0][0].length(); i++) {
            road.row[i] = new Road();
        }

        // Add the starting row first which is grass
        Row starting = new Row(possibleRowsString[0][0].length(), PlaceType.GRASS);
        for (int i = 0; i < possibleRowsString[0][0].length(); i++) {
            starting.row[i] = new Grass(); // <-- You need this loop!
        }

        ArrayList<Row> board = new ArrayList<>();
        board.add(starting);

        // Iterate through number of rows and randomly add some generated rows.
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            // Determine if we should have roads
            if (rand.nextInt(3) == 1) {
                // Determine how many roads to generate, up to 3
                int numRoads = rand.nextInt(3) + 1;
                for (int j = 0; j < numRoads; j++)
                    board.add(road.clone());
                continue;
            }
            // If we get here, we aren't making roads!
            int index = rand.nextInt(possibleRowsString.length);
            // Iterate through the possible row and add them all.
            for (String j : possibleRowsString[index]) {
                board.add(parseRow(j));
            }
            // Always add and empty grass piece at the end
            board.add(starting.clone());
        }

        // Always end on a finish line!
        Row r = new Row(8, PlaceType.FINISH);
        for (int i = 0; i < r.row.length; i++)
            r.row[i] = new Finish();
        board.add(r);

        // Convert to an array
        Row[] boardArray = new Row[board.size()];
        for (int i = 0; i < boardArray.length; i++)
            boardArray[i] = board.get(i);

        return boardArray;
    }

    /*
    COMMENTED OUT Due to a more favorable sudo-random generation method used.
    // Generates a random row based off of enum passed through
    private void initRow(PlaceType p){

        if(starting){
            for(int i = 0; i < row.length; i++){
                row[i] = new Grass();
            }
        }

        // Generate a grass row
        if(p == PlaceType.GRASS){
            // Generate a random number of bushes in this row
            ArrayList<Integer> validIndices = new ArrayList<>();
            for(int i = 0; i < row.length; i++)
                validIndices.add(i);

            // Generate a random number of bushes in this row
            Random rand = new Random();
            int numBush = rand.nextInt(validIndices.size() / 2 + 1);
            for(int i = 0; i < numBush; i++) {
                // Sets the obstacles at random, valid positions. No more than half of the row
                int index = rand.nextInt(validIndices.size());
                row[validIndices.get(index)].setObstacle(new Bush());
                // Note to self: Which int will this call? Object or position?
                validIndices.remove(index);
            }
        }
        // Generate a water row
        if(p == PlaceType.WATER){

        }
        // Generate a road row
        if(p == PlaceType.ROAD){

        }

    }

    // Intended to be used if the last generated row was water
    private void initRow(PlaceType p, Row previous){

        // Generate a grass row
        if(p == PlaceType.GRASS){
            // Determine which locations cannot have bushes
            ArrayList<Integer> indices = new ArrayList<>();
            for(int i = 0; i < previous.row.length; i++){
                if(previous.row[i].hasObstacle() && previous.row[i].getObstacle() instanceof Lilypad)
                    indices.add(i);
            }

            // If the previous row is all lilys, just make this row empty. Shouldn't happen.
            if(indices.size() == row.length){
                for(int i = 0; i < row.length; i++)
                    row[i] = new Grass();
                return;
            }
            ArrayList<Integer> validIndices = new ArrayList<>();
            for(int i = 0; i < row.length; i++)
                if(!indices.contains(i))
                    validIndices.add(i);

            // Generate a random number of bushes in this row
            Random rand = new Random();
            int numBush = rand.nextInt(validIndices.size() / 2 + 1);
            for(int i = 0; i < numBush; i++) {
                // Sets the obstacles at random, valid positions. No more than half of the row
                int index = rand.nextInt(validIndices.size());
                row[validIndices.get(index)].setObstacle(new Bush());
                // Note to self: Which int will this call? Object or position?
                validIndices.remove(index);
            }
        }
        // Generate a water row
        if(p == PlaceType.WATER){

        }
        // Generate a road row
        if(p == PlaceType.ROAD){

        }

    }*/

}
