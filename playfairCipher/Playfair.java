package playfairCipher;// encodes text input using the Playfair cipher
// results (both encode and decode) are output with the table
// requires a user keyword for the cipher
// ues letter 'X' for insertion, I replaces J
import java.awt.Point;
import java.util.Scanner;

public final class Playfair {
    // length of digraph array
    private int length = 0;

    // table for Playfair cipher
    private String[][] table;

    // choice to encode or decode
    private String choice;

    // main method to test Playfair method
    public static void main(String[] args) {
        
        Playfair pf = new Playfair();
        

    }

    // main run of the program, Playfair method
    private Playfair() {
        // let user choose to encode or decode
        try (Scanner scan = new Scanner(System.in)) {
            do {
                System.out.println("Type 'e' to encode or 'd' to decode a message.");
                choice = scan.nextLine();
            } while (!choice.equals("e") && !choice.equals("d"));

            // prompts user for the keyword to use for encoding & creates tables
            System.out.println("Please input the keyword for the Playfair cipher.");
            String keyword = parseString(scan);
            while (keyword.equals(""))
                keyword = parseString(scan);
            System.out.println();
            table = this.cipherTable(keyword);

            // prompts user for message to be encoded
            System.out.println("Please input the message you would like to "
                    + (choice.equals("e") ? "encode." : "decode."));
            String input = parseString(scan);
            while (input.equals(""))
                input = parseString(scan);
            System.out.println();
            scan.close();
            // encodes and then decodes the encoded message
            String output = "";
            switch (choice) {
                case "e":
                    output = cipher(input);
                    break;
                case "d":
                    output = decode(input);
                    break;
                default:
                    break;
            }
            //String output = cipher(input);
            //String decodedOutput = decode(output);

            // output the results to user
            this.printTable(table);
            this.printResults(output);
        }
    }
    // parses any input string to remove numbers, punctuation,
    // replaces any J's with I's, and makes string all caps
    private String parseString(Scanner s){
        StringBuilder parse = new StringBuilder();
        while(s.hasNextLine()) {
            String line = s.nextLine();
            if (line.isEmpty()) break; // Stop reading on empty line
            line = line.toUpperCase();
            line = line.replaceAll("[^A-Z]", "");
            line = line.replace("J", "I");
            parse.append(line);
        }
        return parse.toString();
        //parse = parse.replace("[\\r\\n]", "");
    }

    // creates the cipher table based on some input string (already parsed)
    private String[][] cipherTable(String key){
        String[][] playfairTable = new String[5][5];
        String keyString = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ";

        // fill string array with empty string
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                playfairTable[i][j] = "";

        for(int k = 0; k < keyString.length(); k++){
            boolean repeat = false;
            boolean used = false;
            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5; j++){
                    if(playfairTable[i][j].equals("" + keyString.charAt(k))){
                        repeat = true;
                    }else if(playfairTable[i][j].equals("") && !repeat && !used){
                        playfairTable[i][j] = "" + keyString.charAt(k);
                        used = true;
                    }
                }
            }
        }
        return playfairTable;
    }

    // cipher: takes input (all upper-case), encodes it, and returns output
    private String cipher(String in){
        length = (int) in.length() / 2 + in.length() % 2;

        // insert x between double-letter digraphs & redefines "length"
        for(int i = 0; i < (length - 1); i++){
            if(in.charAt(2 * i) == in.charAt(2 * i + 1)){
                in = new StringBuffer(in).insert(2 * i + 1, 'X').toString();
                length = (int) in.length() / 2 + in.length() % 2;
            }
        }

        // adds an x to the last digraph, if necessary
        String[] digraph = new String[length];
        for(int j = 0; j < length ; j++){
            if(j == (length - 1) && in.length() / 2 == (length - 1))
                in = in + "X";
            digraph[j] = in.charAt(2 * j) +""+ in.charAt(2 * j + 1);
        }

        // encodes the digraphs and returns the output
        StringBuilder out = new StringBuilder();
        String[] encDigraphs = new String[length];
        encDigraphs = encodeDigraph(digraph);
        for(int k = 0; k < length; k++)
            out.append(encDigraphs[k]);
        return out.toString();
    }

    // encodes the digraph input with the cipher's specifications
    private String[] encodeDigraph(String di[]){
        String[] enc = new String[length];
        for(int i = 0; i < length; i++){
            char a = di[i].charAt(0);
            char b = di[i].charAt(1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();

            // case 1: letters in digraph are of same row, shift columns to right
            if(r1 == r2){
                c1 = (c1 + 1) % 5;
                c2 = (c2 + 1) % 5;

                // case 2: letters in digraph are of same column, shift rows down
            }else if(c1 == c2){
                r1 = (r1 + 1) % 5;
                r2 = (r2 + 1) % 5;

                // case 3: letters in digraph form rectangle, swap first column # with second column #
            }else{
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }

            //performs the table look-up and puts those values into the encoded array
            enc[i] = table[r1][c1] + "" + table[r2][c2];
        }
        return enc;
    }

    // decodes the output given from the cipher and decode methods (opp. of encoding process)
    private String decode(String out){
        String decoded = "";
        for(int i = 0; i < out.length() / 2; i++){
            char a = out.charAt(2*i);
            char b = out.charAt(2*i+1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();
            if(r1 == r2){
                c1 = (c1 + 4) % 5;
                c2 = (c2 + 4) % 5;
            }else if(c1 == c2){
                r1 = (r1 + 4) % 5;
                r2 = (r2 + 4) % 5;
            }else{
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }
            decoded = decoded + table[r1][c1] + table[r2][c2];
        }
        return decoded;
    }

    // returns a point containing the row and column of the letter
    private Point getPoint(char c){
        Point pt = new Point(0,0);
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                if(c == table[i][j].charAt(0))
                    pt = new Point(i,j);
        return pt;
    }

    // prints the cipher table out for the user
    private void printTable(String[][] printedTable){
        System.out.println("This is the cipher table from the given keyword.");
        System.out.println();

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(printedTable[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // prints results (encoded and decoded)
    private void printResults(String out){
        switch (choice) {
            case "e":
                System.out.println("This is the encoded message:");
                break;
            case "d":
                System.out.println("This is the decoded message:");
                break;
            default:
                break;
        }
        System.out.println(out);
    }
}
