import java.io.*;

public class TextNormalizer {
    public static void main(String[] args) {
        try {
            normalizeText("input.txt", "output.txt");
            System.out.println("Text normalization completed.");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public static void normalizeText(String inputFile, String outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            boolean isFirstLine = true;
            String line;
            StringBuilder normalizedText = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing spaces

                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }

                if (!isFirstLine) {
                    normalizedText.append(" "); // Add a space before non-first lines
                } else {
                    isFirstLine = false;
                }

                line = line.replaceAll("\\s+", " "); // Replace multiple spaces with one space
                line = line.replaceAll("(?<=\\w)([.,:])", " $1"); // Add space after comma, dot, or colon
                line = line.replaceAll("\"(.*?)\"", "$1"); // Remove spaces inside quotes
                line = line.substring(0, 1).toUpperCase() + line.substring(1); // Capitalize first character

                normalizedText.append(line);
            }

            normalizedText.append("."); // Add a dot at the end

            writer.write(normalizedText.toString());
        } catch (FileNotFoundException e) {
            throw new IOException("Input file not found: " + inputFile);
        }
    }
}
