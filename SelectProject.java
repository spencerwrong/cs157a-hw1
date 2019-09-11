import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;

public class SelectProject {

  public static void main(String[] args) {


    String[][] fileContents = getFileContents(args[0]);
    // column names are the in the first index of the array
    String[] columnNames = fileContents[0];
    // String[] columnNames = getColumnNames(args[0]);


    // Split into two sub arrays
    int wIndex = java.util.Arrays.asList(args).indexOf("WHERE");
    String[] sel = new String[wIndex - 1];
    String[] cond = new String[args.length - wIndex - 1];
    System.arraycopy(args, 1, sel,  0, wIndex - 1);
    System.arraycopy(args, wIndex + 1, cond, 0, args.length - wIndex - 1);

    int[] activeCols = getActiveCols(sel, columnNames);

    print(activeCols, fileContents, columnNames);
  }

  /**
  * Converts the file contents into a 2D Array
  * @param  fileName file name of the file to be read
  * @return          a 2d array of the file split by tabs and newlines
  */
  public static String[][] getFileContents (String fileName) {
    String[][] fileContents = null;
    try{
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      ArrayList<String[]> rows = new ArrayList<String[]>();

      String[] arr = null;
      boolean eof = false;
      while(!eof) {
        String line = br.readLine();
        if(line == null || line == "") {
          eof = true;
        }
        else {
          arr = line.split("\\t");
          rows.add(arr); // add array of each line to arraylist
        }
      }
      // change arraylist to arrays
      fileContents = rows.toArray(new String[rows.size()][arr.length]);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return fileContents;
  }

  /**
  * Returns an array of the correct indicies of the columns to print out.
  * @param  sel   selection of of columns from command line
  * @param  columnNames names of the columns in the table
  * @return             an array of the indicies of the corresponding columns
  */
  public static int[] getActiveCols(String[] sel, String[] columnNames) {
    ArrayList<Integer> activeColumnIndicies = new ArrayList<Integer>();
    int[] activeCols = null;
    for(String s : sel) {
      for(int i = 0; i < columnNames.length; i++) {
        if(columnNames[i].equals(s)) {
          activeColumnIndicies.add(i);
        }
      }
    }

    activeCols = new int[activeColumnIndicies.size()];
    for (int i = 0; i < activeCols.length; i++) {
      activeCols[i] = activeColumnIndicies.get(i);
    }
    return activeCols;
  }

  /**
   * Prints the database based on the selected queries
   * @param activeCols indicies of the active columns to print
   * @param fileContents  2d array of the file contnets
   * @param columnNames   names of the columns
   */
  public static void print(int[] activeCols, String[][] fileContents, String[] columnNames)
  {
    for(int i = 0; i < fileContents.length; i++) {
      for(int j = 0; j < activeCols.length; j++) {

        System.out.print(fileContents[i][activeCols[j]] + "\t");

      }
      System.out.print("\n");
    }
  }
}



// /**
//  * Reads the first line of a file
//  * @param  fileName name of the file to be read
//  * @return first line of a given file
//  */
// public static String[] getColumnNames(String fileName) {
//   try {
//     BufferedReader br = new BufferedReader(new FileReader(fileName));
//     return br.readLine().split("\\t");
//   }
//   catch (IOException e) {
//     e.printStackTrace();
//   }
//   return null;
// }
