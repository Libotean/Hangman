import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private String wordToGuess;
    private int attempts;
    private StringBuilder currentProgress;
    public Game() throws IOException {
        this.wordToGuess = chooseRandomWord();
        this.attempts = 7;
        this.currentProgress = new StringBuilder("_".repeat(wordToGuess.length()));
    }

    public String chooseRandomWord() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/words.txt"));
        List<String> words = new ArrayList<>();
        String line;
        while((line = reader.readLine())!= null){
            words.add(line.trim());
        }
        reader.close();
        int randomIndex = (int) (Math.random() * words.size());
        return words.get(randomIndex);
    }

    public boolean guessLetter(char letter) {
        boolean found = false;
        letter = Character.toLowerCase(letter);
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == letter) {
                currentProgress.setCharAt(i, letter);
                found = true;
            }
        }
        if(!found) {
            attempts--;
        }
        return found;
    }

    public boolean isGameWon() {
        return wordToGuess.equals(currentProgress.toString());
    }

    public boolean isGameOver() {
        return attempts <= 0;
    }

    public String getCurrentProgress() {
        return currentProgress.toString();
    }

    public int getAttempts() {
        return attempts;
    }

    public Game(String word){
        this.wordToGuess = word.toLowerCase();
        this.attempts = 7;
        this.currentProgress = new StringBuilder("_".repeat(wordToGuess.length()));
    }

}
