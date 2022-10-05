import java.util.HashMap;
import java.util.List;
import java.util.*;
import java.io.*;


public class Anagrams {
    public static Random rand = new Random();
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        List<String> words = readFile("anagrams_words.txt");
        playGame(words);
    }

    public static List<String> readFile(String filename){
        List<String> words = new ArrayList<>();
        try(Scanner sc = new Scanner(new File(filename))){
            while(sc.hasNext()){
                String word = sc.next();
                words.add(word);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return words;
    }

    public static boolean isAnagram(String word1, String word2, List<String> words){
        if(words.contains(word1) && words.contains(word2)) {
            if (word1.isEmpty() && word2.isEmpty()) {
                return false;
            }
            HashMap<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < word1.length(); i++) {
                if (map.containsKey(word1.charAt(i))) {
                    int value = map.get(word1.charAt(i));
                    map.put(word1.charAt(i), value + 1);
                } else {
                    map.put(word1.charAt(i), 1);
                }
            }
            for (int i = 0; i < word2.length(); i++) {
                char key = word2.charAt(i);
                if (!map.containsKey(key)) {
                    return false;
                }
                if (map.containsKey(key)) {
                    int val = map.get(key) - 1;
                    map.put(key, val);
                    if (map.get(key) == 0) {
                        map.remove(key);
                    }
                }
            }
            if (map.isEmpty()) {
                return true;
            }else{return false;}
        }
        return false;
    }

    public static int numOfAnagrams(String str, String word, Set<String> anagrams, List<String> words) {
        if (word.length() <= 1) {
            String formed_word = str+word;
            if(words.contains(formed_word)) {
                anagrams.add(formed_word);
            }
        } else {
            for (int i = 0; i < word.length(); i++) {
                String a = word.substring(i, i + 1);
                String b = word.substring(0, i);
                String c = word.substring(i + 1);

                numOfAnagrams(str + a, b + c, anagrams, words);
            }
        }
        return anagrams.size();
    }

    public static void playGame(List<String> words){
        int words_entered = 0;
        int points_attained = 0;
        String target_word = words.get(rand.nextInt(words.size()));

        System.out.println("Welcome to the Anagram game! Your goal is to enter all the possible anagrams of a random word given to you.");
        System.out.printf("Target word is '%s'\n\n", target_word);
        Set<String> anagrams = new HashSet<>();
        int n = numOfAnagrams("", target_word, anagrams, words);

        System.out.printf("Enter possible anagrams for '%s'\nEnter 'stop' to quit\n\n", target_word);
        String user_input ="";

        List<String> used_words = new ArrayList<>();

        while(!user_input.toLowerCase().equals(("0")) && n > 0){
            System.out.print("Enter an anagram: ");
            user_input =sc.next();

            if(user_input.toLowerCase().equals("0")){
                break;
            }

            if(used_words.contains(user_input)){
                System.out.println("Word already entered, try again.\n");
            }else{
                if(isAnagram(user_input, target_word, words)){
                    points_attained++;
                    words_entered++;
                    n--;
                    System.out.println("Point! That is an anagram of the target word\n");
                } else{
                    words_entered++;
                    System.out.println("No point! That is NOT an anagram of the target word\n");
                }
            }
            System.out.printf("\nWords entered so far: %d\n", words_entered);
            System.out.printf("Current points attained: %d\n", points_attained);
            used_words.add(user_input);
        }

        if(n == 0){System.out.printf("Successfully entered all anagrams of '%s'\n", target_word);}

        System.out.printf("\nTotal distinct words entered: %d\n", words_entered);
        System.out.printf("Final score attained (correct anagrams entered): %d\n", points_attained);

        playAgain(words);
    }

    public static void playAgain(List<String> words){
        System.out.print("\nWould you like to play again (Y or N)? ");
        String answer = sc.next();
        while(!(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("N"))){
            System.out.println("Enter Y or N.");
            System.out.print("Would you like to play again (Y or N)? ");
            answer = sc.next();
        }
        if(answer.toUpperCase().equals("Y")){
            System.out.println("-----------------------------------------------------------");
            playGame(words);
        }else{
            System.out.println("Thanks for playing!");
        }
    }

}
