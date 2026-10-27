package ru.edu.pr04;

public class SelfCheck {
    private static int passed;
    private static int failed;

    public static void main(String[] args) {
        check("типовой заказ: 2 мыши по 1000", () -> {
            Order order = order();
            order.addProduct(new Product("P1", "Mouse", 1000), 2);
            assertEquals(2000.0, order.total());
            assertEquals(1, order.getItems().size());
            assertEquals(OrderStatus.DRAFT, order.getStatus());
        });

        check("нижняя граница количества 1", () -> {
            Order order = order();
            order.addProduct(new Product("P1", "Mouse", 1000), OrderItem.MIN_QUANTITY);
            assertEquals(1, order.getItems().get(0).getQuantity());
        });

        check("верхняя граница количества 100", () -> {
            Order order = order();
            order.addProduct(new Product("P1", "Mouse", 10), OrderItem.MAX_QUANTITY);
            assertEquals(100, order.getItems().get(0).getQuantity());
            assertEquals(1000.0, order.total());
        });

        check("количество 0 отклоняется", () -> {
            expectFail(() -> order().addProduct(new Product("P1", "Mouse", 1000), 0));
        });

        check("количество 101 отклоняется", () -> {
            expectFail(() -> order().addProduct(new Product("P1", "Mouse", 1000), 101));
        });

        check("пустые поля и неположительная цена", () -> {
            expectFail(() -> new Product("", "Mouse", 1000));
            expectFail(() -> new Product("P1", "  ", 1000));
            expectFail(() -> new Product("P1", "Mouse", 0));
            expectFail(() -> new Product("P1", "Mouse", -100));
        });

        check("повтор товара сливается в одну строку", () -> {
            Order order = order();
            Product monitor = new Product("M01", "Monitor 27", 32990);
            order.addProduct(monitor, 2);
            order.addProduct(new Product("M01", "Monitor 27", 32990), 1);
            assertEquals(1, order.getItems().size());
            assertEquals(3, order.getItems().get(0).getQuantity());
            assertEquals(32990.0 * 3, order.total());
        });

        check("после confirm заказ нельзя менять", () -> {
            Order order = order();
            order.addProduct(new Product("K01", "Keyboard", 4490), 1);
            order.confirm();
            assertEquals(OrderStatus.CONFIRMED, order.getStatus());
            expectFail(() -> order.addProduct(new Product("P1", "Mouse", 1000), 1));
            expectFail(() -> order.removeProduct("K01"));
            assertEquals(4490.0, order.total());
        });

        System.out.println(failed == 0
                ? "PASS " + passed
                : "FAIL passed=" + passed + " failed=" + failed);
    }

    private static Order order() {
        return new OrderService().createOrder(new Employee("E1", "Ivanov Ivan"));
    }

    private static void check(String name, Runnable test) {
        try {
            test.run();
            passed++;
            System.out.println("OK  " + name);
        } catch (Throwable error) {
            failed++;
            System.out.println("FAIL " + name + " — " + error.getMessage());
        }
    }

    private static void expectFail(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException | IllegalStateException expected) {
            return;
        }
        throw new AssertionError("ожидалась ошибка входных данных");
    }

    private static void assertEquals(double expected, double actual) {
        if (Double.compare(expected, actual) != 0) {
            throw new AssertionError(expected + " != " + actual);
        }
    }

    private static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError(expected + " != " + actual);
        }
    }

    private static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
