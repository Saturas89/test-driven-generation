package nl.boukenijhuis.dto;

import nl.boukenijhuis.ClassNameNotFoundException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CodeContainer {
    private final String content;
    private final String fileName;
    private final String packageName;
    private final int attempts;

    public CodeContainer(String content) throws ClassNameNotFoundException {
        this(content, 1);
    }

    public CodeContainer(String content, int attempts) throws ClassNameNotFoundException {
        this.content = content;
        this.fileName = extractClassName() + ".java";
        this.packageName = extractPackageName();
        this.attempts = attempts;
    }

    // TODO: first look for 'public class' and then for 'class
    private String extractClassName() throws ClassNameNotFoundException {
        // matches "public" (optional) followed by "class" and then the class name
        String regex = "\\b(?:public\\s+)?class\\s+(\\w+)\\b";
        Matcher matcher = Pattern.compile(regex).matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new ClassNameNotFoundException("Class name not found in: " + content);
        }
    }

    // find the package name in source code
    private String extractPackageName() {
        String regex = "package\\s+(\\w+(\\.\\w+)*)";
        Matcher matcher = Pattern.compile(regex).matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getContent() {
        return content;
    }

    public int getAttempts() {
        return attempts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeContainer that = (CodeContainer) o;
        return attempts == that.attempts && Objects.equals(content, that.content) && Objects.equals(fileName, that.fileName) && Objects.equals(packageName, that.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, fileName, packageName, attempts);
    }

    @Override
    public String toString() {
        return "CodeContainer{content='%s', fileName='%s', packageName='%s', attempts=%d}"
                .formatted(content, fileName, packageName, attempts);
    }

}
