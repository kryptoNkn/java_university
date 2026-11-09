package ru.edu.pr08; 
public class ClientCsvParser { 
    public ClientDraft parse(String line,int lineNumber) { 
        if(line == null || line.isBlank()) {
            throw new IllegalArgumentException("пустая строка");
        }

        String[] parts = line.split(";", -1);
        if(parts.length != 3) {
            throw new IllegalArgumentException("ожидалось 3 поля через ';', получено " + parts.length);
        }
        return new ClientDraft(parts[0].trim(), parts[1].trim(), parts[2].trim());
    } 
}
