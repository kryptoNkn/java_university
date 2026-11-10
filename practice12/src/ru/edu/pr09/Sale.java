package ru.edu.pr09; 
public record Sale(String id,String manager,String region,double amount) {
    public Sale {
        if(id==null || id.isBlank()) {
            throw new IllegalArgumentException("пустой id");
        }

        if(manager == null || manager.isBlank()) {
            throw new IllegalArgumentException("пустой менеджер");
        }

        if(region == null || region.isBlank()) {
            throw new IllegalArgumentException("пустой регион");
        }

        if(Double.isNaN(amount) || amount < 0) {
            throw new IllegalArgumentException("некорректная сумма: " + amount);
        }
    }

    public static Sale parse(String line) {
        if(line == null || line.isBlank()) {
            throw new IllegalArgumentException("пустая строка продажи");
        }

        String[] p = line.split(";");
        if(p.length != 4) {
            throw new IllegalArgumentException("неверный формат: " + line);
        }

        double amount;
        try {
            amount = Double.parseDouble(p[3].trim());
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("сумма не число: " + p[3]);
        }

        return new Sale(p[0].trim(), p[1].trim(), p[2].trim(), amount);
    }
}