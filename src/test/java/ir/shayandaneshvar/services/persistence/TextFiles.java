package ir.shayandaneshvar.services.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class TextFiles {
    private static TextPersistence textPersistence = new TextPersistence();
    private static String string;

    static {
        string = new String("hello how are you this is some random words\n" +
                "coming right at this miserable text file\n" +
                "lorem ipsum dolor sit amet.\n" +
                "end of file\n" +
                "asdasd\n" +
                "asdsa\n" +
                "\n" +
                "ad\n" +
                "s\n" +
                "ds\n" +
                "fsad\n" +
                "f\n" +
                "sdaf\n" +
                "sd\n" +
                "f\n" +
                "asd\n" +
                "f\n" +
                "as\n" +
                "df\n" +
                "as\n" +
                "df\n" +
                "a\n" +
                "sdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" +
                "\n" +
                "\n" +
                "asds\n");
    }

    @Test
    public void testTextReading() throws MalformedURLException, FileNotFoundException {
        File file = new File("C:\\Users\\TOP\\Desktop\\AP " +
                "Assignments\\huffmancompressor\\src\\test\\java\\ir\\shayandaneshvar\\services\\persistence\\sample.txt");
        String read = textPersistence.read(file.toString());
        Assertions.assertEquals(read.trim(), string.trim());
//        File file = new File("src/test/java/ir/shayandaneshvar/services" +
//                "/persistence/" + "sample.txt");
//        Scanner scanner =
//                new Scanner(file);
//        while (scanner.hasNextLine()) System.out.println(scanner.nextLine());
    }

    @Test
    public void testTextWriting() {
        File file = new File("C:\\Users\\TOP\\Desktop\\AP " +
                "Assignments\\huffmancompressor\\src\\test\\java\\ir" +
                "\\shayandaneshvar\\services\\persistence\\sampleWrite.txt");
        textPersistence.write(file.toString(), string);
    }
}
