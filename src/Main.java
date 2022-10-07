import java.util.*;
import java.io.*;
import static java.util.Arrays.sort;

public class Main {
    static Scanner wordIn = new Scanner(System.in);

    static int runs = 1;

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Anagrams game! Try to enter as many anagrams as you can think of.");
        play();
    }

    private static ArrayList<String> extract_file() throws IOException {
        BufferedReader output;
        output = new BufferedReader(new FileReader("all_words.txt"));
        ArrayList<String> words = new ArrayList<>(List.of());
        String line;
        while ((line = output.readLine()) != null) {
            words.add(line);
        }
        return words;
    }

    private static String input(String wordIn) {
        return wordIn;
    }

    private static String GetWord() throws IOException {
        Random randint = new Random();
        String word1;
        word1 = extract_file().get(randint.nextInt(extract_file().size()));
        return word1;
    }

    private static int fact(int number) {
        int f = 1;
        int j = 1;
        while(j <= number) {
            f = f * j;
            j++;
        }
        return f;
    }

    private static int letters(String w) {
        String[] word = w.split("");
        ArrayList<Object> letter = new ArrayList<>();
        for (int i = 0; i < word.length - 1; i++) {
            if (!letter.contains(word[i])) {
                letter.add(word[i]);
            }
        }
        return letter.size();
    }

    private static int permutations(String w) {
        int f = 1;
        int j = 1;
        int x = w.length();
        int r = letters(w);
        int result;
        while(j <= x) {
            f = f * j;
            j++;
        }
        result = fact(x) / fact(x - r);
        return result;
        }

    private static int anagram_words(String str, String word, Set<String> anagrams, List<String> words) {
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
                anagram_words(str + a, b + c, anagrams, words);
            }
        }
        return anagrams.size();
    }

    private static String[] array2(String word) {
        String[] array = input(word).split("");
        sort(array);
        return array;
    }

    public static void play() throws IOException {
        int words = 0;
        int score = 0;
        List<String> file = extract_file();
        String target = GetWord();
        ArrayList<String> entered = new ArrayList<>();
        System.out.println("Your target word is: " + target);
        Set<String> anagrams = new HashSet<>();
        int words_are_anagrams = anagram_words("", target, anagrams, file) - 1;
        int attempts = permutations(target);
        System.out.println("Words to type: " + words_are_anagrams);
        String YourWord;
        System.out.println("attempts in total: " + attempts);
        if (attempts > 1 && words_are_anagrams == 0) {
            System.out.println("No anagrams exist for " + target + "!"); // Edge case
        }
        else {
            if (runs > 1) { // This exists because a bug exists in the round2() call.
                YourWord = wordIn.nextLine();
                String lower = YourWord.toLowerCase();
                System.out.println("Run " + (runs));
            }
            do {
                System.out.println("\nEnter a word or press 0 to exit: ");
                YourWord = wordIn.nextLine();
                String lower = YourWord.toLowerCase();
                System.out.println("\nYour word is " + lower);
                String[] array = target.split("");
                sort(array);
                attempts -= 1;
                if (Arrays.equals(array, array2(lower)) && !entered.contains(lower.toLowerCase()) &&
                !Objects.equals(lower, target) && file.contains(lower)) {
                    System.out.println("This word is an anagram! +1");
                    words_are_anagrams -= 1;
                    score += 1;
                } else if (entered.contains(lower.toLowerCase())) {
                    System.out.println("You already entered this word. +0");
                } else if (Objects.equals(lower, target)) {
                    System.out.println("This is the target word. +0");
                } else if (!file.contains(lower) && Arrays.equals(array, array2(lower.toLowerCase()))) {
                    System.out.println("This is not a word. +0");
                } else if (lower.equals("0")) {
                    System.out.println("Bye.");
                    attempts = 0;
                } else {
                    System.out.println("This word is NOT an anagram. +0");
                }
                if (!Objects.equals(lower, "0")) {
                    words += 1;
                    entered.add(lower);
                    System.out.println("Words you typed in: " + words);
                    System.out.println("All words you entered: " + entered);
                    System.out.println("\nYour current score: " + score);
                }
                System.out.println("attempts left: " + attempts);
            } while (!YourWord.equals("0") && words_are_anagrams > 0 && attempts > 1);
            System.out.println("\nAll words you entered: " + entered);
            System.out.println("Total number of words you entered: " + words);
            System.out.println("Game finished! Your final score is " + score);
            if (attempts <= 0 && words_are_anagrams != 0) {
            System.out.println("The game finished before you entered all possible anagrams of " + target + "!");
            } else if (words_are_anagrams == 0 && attempts > 1) {
                System.out.println("You did it! You entered all possible anagrams for " + target + "!\n");
                System.out.println(
                    """
                    \\   /    ______     |       |        \\              /     _____     |\\    |   |
                     \\ /    /      \\    |       |         \\            /        |       | \\   |   |
                      |    |        |   |       |          \\    /\\    /         |       |  \\  |   |
                      |    |        |    \\     /            \\  /  \\  /          |       |   \\ |   |
                      |     \\______/      \\___/              \\/    \\/         _____     |    \\|   o""");
            }
            round2();
        }
    }

    public static void round2() throws IOException {
        System.out.print("\nWould you like to play again (1 or 0)? ");
        String answer = wordIn.next();
        while(!(answer.equalsIgnoreCase("1") || answer.equalsIgnoreCase("0"))){
            System.out.println("Enter 1 for yes or 0 for no.");
            System.out.print("Would you like to play again (1 for yes or 0 for no)? ");
            answer = wordIn.next();
        }
        if(answer.equalsIgnoreCase("1")){
            runs += 1;
            System.out.println("\n================================================================\n");
            play();
        }else{
            System.out.println("Thanks for playing!");
        }
    }
}
