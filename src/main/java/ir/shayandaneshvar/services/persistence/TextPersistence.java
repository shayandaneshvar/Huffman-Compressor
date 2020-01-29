package ir.shayandaneshvar.services.persistence;

import java.io.*;
import java.util.Scanner;

public class TextPersistence implements Persistence<String, String> {
    private static final String EXTENSION = ".txt";

    @Override
    public void write(String address, String text) {
        File file = new File(address);
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter writer = new PrintWriter(bufferedWriter)) {
            writer.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getEXTENSION() {
        return EXTENSION;
    }

    @Override
    public String read(String address) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new File(address));
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine() + "\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString().trim();
    }
}
