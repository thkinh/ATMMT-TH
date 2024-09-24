import java.io.Console;
import java.util.*;


class Cryptools
{
    public static List<Character> chars_list = new ArrayList<Character>();
    public static Scanner sc;
    public static Console c;
    public static void init()
    {
        c = System.console();   
        sc = new Scanner(System.in);
        Collections.addAll(chars_list, 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z');
    }

    public static String Caesar_Cipher(String input)
    {
        int length = input.length();
        input = input.toLowerCase();

        String result = "";

        
        for(int key = 1; key < 27; key++)
        {
            result = "";
            for(int i = 0; i < length; i++)
            {
                char character = input.charAt(i);
                if (!chars_list.contains(character))
                {
                    result += character;
                    continue;
                }

                character = chars_list.get((chars_list.indexOf(character) + key) % 26);
                result += character;
            }
            System.out.print(result + "\n");
            System.out.print("==================================");
            c.readLine();
        }
        return result;
    }

    public static String Vigenere_Cipher(String input)
    {
        int length = input.length();
        int current_key_pos = 0;
        String result = "";
        System.out.print("Nhap key: ");
        
        String key = sc.nextLine();
        char[] key_elements = key.toCharArray();
        List<Integer> new_key = new ArrayList<Integer>() ;
        for(char character:key_elements)
        {
            int number_of_this_key = chars_list.indexOf(character);
            new_key.add(number_of_this_key);
        }
        int key_length = new_key.size();

        for(int i = 0; i<length; i++)
        {
            char character = input.charAt(i);
            if (!chars_list.contains(character))
            {
                result += character;
                continue;
            }
            character = chars_list.get((chars_list.indexOf(character) + new_key.get(current_key_pos)) % 26);
            current_key_pos++;
            if(current_key_pos == key_length)
            {
                current_key_pos = 0;
            }
            result+= character;
        }


        return result;
    }

    public static int Find_key_Length(String input) {
        Map<String, List<Integer>> sequences = new HashMap<>();
        int minSeqLength = 3;  // Minimum sequence length to consider

        // Step 1: Find all repeated substrings and their positions
        for (int i = 0; i < input.length() - minSeqLength; i++) {
            for (int len = minSeqLength; len <= 5; len++) {  // Check sequences of varying lengths
                if (i + len > input.length()) break;
                String sequence = input.substring(i, i + len);

                // Store the positions of this sequence in the map
                if (!sequences.containsKey(sequence)) {
                    sequences.put(sequence, new ArrayList<>());
                }
                sequences.get(sequence).add(i);
            }
        }

        // Step 2: Calculate distances between repeated sequences
        List<Integer> distances = new ArrayList<>();
        for (List<Integer> positions : sequences.values()) {
            if (positions.size() > 1) {
                for (int i = 1; i < positions.size(); i++) {
                    int distance = positions.get(i) - positions.get(i - 1);
                    distances.add(distance);
                }
            }
        }

        if (distances.isEmpty()) {
            return -1;  // No repeating patterns found
        }

        // Step 3: Find the GCD of all distances
        int keyLength = distances.get(0);
        for (int i = 1; i < distances.size(); i++) {
            keyLength = gcd(keyLength, distances.get(i));
        }

        return keyLength;
    }

    // Helper function to compute the GCD of two numbers
    private static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }




    public static void main(String[] args)
    {
        init();
        String input = sc.nextLine();
        int output = Find_key_Length(input);

        System.out.print("Estimated key's length: " + output);
        sc.close();
    }

}