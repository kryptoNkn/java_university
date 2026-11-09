package ru.edu.pr08;
import java.util.ArrayList;
import java.util.List;
public class ClientValidator {
    public static final int MAX_NAME_LENGTH = 45;
    public List<String> validate(ClientDraft draft) {
        List<String> errors = new ArrayList<>();
        if (draft.inn() == null || !draft.inn().matches("\\d{10}|\\d{12}")) {
            errors.add("инн должен содержать 10 или 12 цифр");
        }
        if (draft.name() == null || draft.name().isBlank()) {
            errors.add("пустое название");
        } else if (draft.name().length() > MAX_NAME_LENGTH) {
            errors.add("длина названия больше " + MAX_NAME_LENGTH);
        }
        if (!validEmail(draft.email())) {
            errors.add("email должен содержать один @ и непустые части слева и справа");
        }
        return errors;
    }

    private boolean validEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        int at = email.indexOf('@');
        return at > 0 && at == email.lastIndexOf('@') && at < email.length() - 1;
    }
}