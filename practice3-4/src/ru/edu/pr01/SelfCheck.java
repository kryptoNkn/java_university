package ru.edu.pr01;

public class SelfCheck {
    public static void main(String[] args) {
        boolean calcOk = false;
        try {
            calcOk = OrderCalculator.calculateBase(2, 100.0) == 200.0
                && Math.abs(OrderCalculator.calculateTotal(2, 100.0, 10.0, 20.0) - 216.0) < 0.001;
        } catch (Exception e) {
            System.out.println("FAIL exception " + e.getMessage());
        }

        boolean boundsOk = true;
        boundsOk &= check("типовое значение", "2;100;10", "216,00 руб.");
        boundsOk &= check("нижняя граница включена", "1;0.01;0", OrderCalculator.formatTotal(
            OrderCalculator.calculateTotal(1, 0.01, 0, OrderCalculator.VAT_PERCENT)));
        boundsOk &= check("верхняя граница включена", "10000;5000000;45", OrderCalculator.formatTotal(
            OrderCalculator.calculateTotal(10000, 5_000_000.0, 45.0, OrderCalculator.VAT_PERCENT)));
        boundsOk &= check("количество 0 исключено", "0;1000;10", "некорректное количество");
        boundsOk &= check("количество 10001 исключено", "10001;100;10", "некорректное количество");
        boundsOk &= check("цена 0 исключена", "5;0;5", "некорректная цена");
        boundsOk &= check("цена выше 5000000 исключена", "1;5000000.01;0", "некорректная цена");
        boundsOk &= check("скидка 45 включена", "3;999.99;45", OrderCalculator.formatTotal(
            OrderCalculator.calculateTotal(3, 999.99, 45.0, OrderCalculator.VAT_PERCENT)));
        boundsOk &= check("скидка 45.01 исключена", "3;999.99;45.01", "некорректная скидка");
        boundsOk &= check("пустая строка", "   ", "отсутствует значение");
        boundsOk &= check("пропущенное поле", "1;;5", "отсутствует значение");
        boundsOk &= check("неверный формат", "abc;10;5", "неверный формат");
        String duplicate = OrderCalculator.evaluateLine("12;2490.50;5");
        boundsOk &= check("повтор той же строки", "12;2490.50;5", duplicate);

        System.out.println(calcOk && boundsOk ? "PASS" : "FAIL  complete TODO methods");
    }

    private static boolean check(String name, String line, String expected) {
        String actual = OrderCalculator.evaluateLine(line);
        boolean ok = actual.equals(expected);
        System.out.println((ok ? "OK  " : "FAIL ") + name + ": " + actual);
        return ok;
    }
}
