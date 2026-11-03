package ru.edu.pr06;
public class SelfCheck {
    public static void main(String[] args) {
        boolean ok = false;
        try {
            Warehouse w = new Warehouse();
            w.addItem(new StockItem("NB-01", "Notebook", Category.COMPUTER, 12));
            w.addItem(new StockItem("EX-01", "Spare", Category.EXTRA, 40));
            w.receive("EX-01", 5);
            boolean over = false;
            try { w.receive("EX-01", 1); } catch (IllegalArgumentException e) { over = true; }
            boolean badCard = false;
            try { w.addItem(new StockItem("EX-02", "Too much", Category.EXTRA, 46)); }
            catch (IllegalArgumentException e) { badCard = true; }
            boolean dup = false;
            try { w.addItem(new StockItem("NB-01", "X", Category.COMPUTER, 1)); }
            catch (IllegalArgumentException e) { dup = true; }
            boolean missing = false;
            try { w.receive("", 1); } catch (IllegalArgumentException e) { missing = true; }
            boolean minus = false;
            try { w.issue("NB-01", 100); } catch (IllegalArgumentException e) { minus = true; }
            var found = w.findByCategory(Category.EXTRA);
            found.clear();
            ok = over && badCard && dup && missing && minus
                    && w.findByCategory(Category.EXTRA).size() == 1
                    && w.totalsByCategory().get(Category.EXTRA) == 45
                    && w.codes().contains("EX-01");
        } catch (Exception e) {
            System.out.println("FAIL exception " + e.getMessage());
        }
        System.out.println(ok ? "PASS" : "FAIL  complete TODO methods");
    }
}