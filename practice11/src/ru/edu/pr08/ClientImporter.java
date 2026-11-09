package ru.edu.pr08;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
public class ClientImporter {
    private final ClientCsvParser parser = new ClientCsvParser();
    private final ClientValidator validator = new ClientValidator();
    private final ClientRepository repository;

    public ClientImporter(ClientRepository repository) {
        this.repository = repository;
    }

    public ImportResult importFile(Path path) throws IOException {
        ImportResult result = new ImportResult();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            if (line == null) {
                throw new IOException("файл пуст");
            }
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                importLine(line, lineNumber, result);
            }
        }
        return result;
    }

    private void importLine(String line, int lineNumber, ImportResult result) {
        try {
            ClientDraft draft = parser.parse(line, lineNumber);
            List<String> problems = validator.validate(draft);
            if (!problems.isEmpty()) {
                result.addError(new ImportError(lineNumber, String.join("; ", problems)));
                return;
            }
            if (repository.findByInn(draft.inn()) != null) {
                result.addError(new ImportError(lineNumber, "дубликат ИНН " + draft.inn()));
                return;
            }
            repository.save(new Client(draft.inn(), draft.name(), draft.email()));
            result.addAccepted();
        } catch (RuntimeException ex) {
            result.addError(new ImportError(lineNumber, ex.getMessage()));
        }
    }
}