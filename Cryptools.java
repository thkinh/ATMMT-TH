import java.io.Console;
import java.util.*;
import playfairCipher.Playfair;

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
        System.out.print("Choose encrypt with key, decrypt with key, or brute force?: ");
        String option = sc.nextLine();
        if (option.equals("b"))
        {
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
        }
        System.out.print("Enter the key: ");
        int key = sc.nextInt();
        sc.nextLine();
        if (option.equals("e")) {
            //Encrypt
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
        else if (option.equals("d")) {
            //Decrypt
            result = "";
            for(int i = 0; i < length; i++)
            {
                char character = input.charAt(i);
                if (!chars_list.contains(character))
                {
                    result += character;
                    continue;
                }
                int delta = chars_list.indexOf(character) - key;
                if (delta < 0) {
                    delta +=26;
                }
                character = chars_list.get(delta);
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

        System.out.print("Nhap option(d,e): ");
        String option = sc.nextLine();
        if (option.equals("e"))
        {
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
        }
        else if (option.equals("d"))
        {
            for(int i = 0; i<length; i++)
            {
                char character = input.charAt(i);
                if (!chars_list.contains(character))
                {
                    result += character;
                    continue;
                }
                int delta = chars_list.indexOf(character) - new_key.get(current_key_pos);
                if (delta < 0) {
                    delta += 26;
                }
                character = chars_list.get(delta);
                current_key_pos++;
                if(current_key_pos == key_length)
                {
                    current_key_pos = 0;
                }
                result+= character;
            }   
        }
        return result;
    }

    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");
            
            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }

    public static void Print_Menu()
    {
        System.out.println("============================================");
        System.out.println("Lua chon thuat toan muon su dung");
        System.out.println("1. CaesarCipher");
        System.out.println("2. PlayfairCipher");
        System.out.println("3. Vigenere");
        System.out.println("4. Exit");
        System.out.println("============================================");
    }

    public static void Start() {
        String input = "";
        while (true) {
            Print_Menu();
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine(); //Clear "/n" character
                System.out.println("Nhap van ban:");
                switch (choice) {
                    case 1:
                        input = sc.nextLine();
                        Caesar_Cipher(input);
                        break;
                    case 2:
                        Playfair ins = new Playfair();
                        // Replace with the actual method call
                        break;
                    case 3:
                        input = sc.nextLine();
                        String output = Vigenere_Cipher(input);
                        System.out.println(output);
                        break;
                    case 4:
                        return;
                    default:
                        break;
                }
            } else {
                System.out.println("Invalid input");
                sc.next();
            }
        }
    }

    public static void main(String[] args)
    {
        init();
        Start();   
        
        sc.close();
    }

}