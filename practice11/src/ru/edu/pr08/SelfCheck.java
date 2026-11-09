package ru.edu.pr08;
import java.nio.file.Files;
import java.nio.file.Path;
public class SelfCheck {
    public static void main(String[] args) {
        boolean ok = false;
        try {
            Path sample = Path.of("data", "sample.csv");
            ClientRepository repository = new ClientRepository();
            ImportResult result = new ClientImporter(repository).importFile(sample);
            ok = result.accepted() == 2 && result.errors().size() == 2 && repository.findByInn("7701234567") != null && repository.findByInn("781234567890") != null && repository.findByInn("123") == null;

            Path extra = Files.createTempFile("clients", ".csv");
            Files.writeString(extra, String.join("\n", "inn;name;email", "7701234567;Alpha LLC;office@alpha.ru", "7701234567;Copy LLC;copy@alpha.ru", "770123456789;" + "N".repeat(46) + ";long@name.ru", "770123456789;" + "N".repeat(45) + ";ok@name.ru"));
            ClientRepository extraRepo = new ClientRepository();
            ImportResult extraResult = new ClientImporter(extraRepo).importFile(extra);
            ok = ok && extraResult.accepted() == 2 && extraResult.errors().size() == 2 && extraRepo.findByInn("770123456789") != null && extraRepo.findByInn("770123456789").name().length() == 45;
            Files.deleteIfExists(extra);
            boolean missingFailed = false;
            try {
                new ClientImporter(new ClientRepository()).importFile(Path.of("data", "missing.csv"));
            } catch (Exception ex) {
                missingFailed = true;
            }
            ok = ok && missingFailed;
        } catch (Exception e) {
            System.out.println("FAIL exception " + e.getMessage());
            ok = false;
        }
        System.out.println(ok ? "PASS" : "FAIL  complete TODO methods");
    }
}
