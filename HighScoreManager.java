import java.io.*;

public class HighScoreManager {
    private static final String FILE_NAME = "highscore.txt";

    public static int loadHighScore() {
        int highScore = 0;
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                return 0;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            reader.close();
            if (line != null) {
                highScore = Integer.parseInt(line.trim());
            }
        } catch (Exception e) {
            highScore = 0;
        }
        return highScore;
    }

    public static void saveHighScore(int score) {
        try {
            FileWriter writer = new FileWriter(FILE_NAME, false);
            writer.write(Integer.toString(score));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            // ignore write errors
        }
    }
}
