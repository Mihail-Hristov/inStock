import jdk.jshell.spi.ExecutionControl;

import java.util.Iterator;

public class Instock implements ProductStock {

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean contains(Product product) {
        return false;
    }

    @Override
    public void add(Product product) {

    }

    @Override
    public void changeQuantity(String product, int quantity) {

    }

    @Override
    public Product find(int index) {
        return null;
    }

    @Override
    public Product findByLabel(String label) {
        return null;
    }

    @Override
    public Iterable<Product> findFirstByAlphabeticalOrder(int count) {
        return null;
    }

    @Override
    public Iterable<Product> findAllInRange(double lo, double hi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Product> findAllByPrice(double price) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Product> findFirstMostExpensiveProducts(int count) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Product> findAllByQuantity(int quantity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Product> iterator() {
        throw new UnsupportedOperationException();
    }
}
