package nl.boukenijhuis;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class IntegrationTest {

    /**
     * Reads the contents of a file and returns it as a string.
     *
     * @param fileName the name of the file to read (give path from resources/)
     * @return the contents of the file as a string
     * @throws IOException if an I/O error occurs
     */
    protected static String readFile(String fileName) throws IOException {
        try (var in = IntegrationTest.class.getResourceAsStream("/" + fileName)) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (NullPointerException e) {
            throw new RuntimeException(String.format("The file '%s' cannot be found in the resources directory.", fileName));
        }
    }

    // use this when you want a request containing a piece of code
    public static String responseWithCode(String code) throws IOException {
        return java.lang.String.format(readFile("stub/ollama/stub_with_input_parameter.json"), convertToJsonValue(code));
    }

    // use this when you want a request containing a piece of text
    protected String responseWithText(String text) throws IOException {
        return String.format(readFile("stub/ollama/stub_with_empty_response.json"), convertToJsonValue(text));
    }

    // escape double qoutes and convert end of lines
    private static String convertToJsonValue(String input) {
        return input.replace("\n", "\\n").replace("\"", "\\\"");
    }
}
