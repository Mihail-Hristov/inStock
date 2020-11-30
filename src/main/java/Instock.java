import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Instock implements ProductStock {
    private List<Product> stock;

    public Instock() {
        this.stock = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return this.stock.size();
    }

    @Override
    public boolean contains(Product product) {
        for (Product currentProduct : stock) {
            if (currentProduct.getLabel().equals(product.getLabel())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void add(Product product) {
        this.stock.add(product);
    }

    @Override
    public void changeQuantity(String product, int quantity) {
        boolean exist = false;
        for (Product currentProduct : stock) {
            if (currentProduct.getLabel().equals(product)){
                currentProduct.setQuantity(quantity);
                exist = true;
            }
        }
        if (exist) {
            return;
        }
        throw new IllegalArgumentException("No exist product in the Stock with label " + product);
    }

    @Override
    public Product find(int index) {
        if (!stock.isEmpty() && index >= 0 && index < stock.size()) {
            return stock.get(index);
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public Product findByLabel(String label) {
        for (Product product : stock) {
            if (product.getLabel().equals(label)) {
                return product;
            }
        }
        throw new IllegalArgumentException("No product in the stock with label " + label);
    }

    @Override
    public Iterable<Product> findFirstByAlphabeticalOrder(int count) {
        if (count > this.getCount()) {
            return new ArrayList<>();
        }
        return stock.stream()
                .sorted(Comparator.comparing(Product::getLabel))
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Product> findAllInRange(double lo, double hi) {
        return stock.stream()
                .filter(p -> p.getPrice() > lo && p.getPrice() <= hi)
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .collect(Collectors.toList());


    }

    @Override
    public Iterable<Product> findAllByPrice(double price) {
        return stock.stream()
                .filter(p -> p.getPrice() == price)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Product> findFirstMostExpensiveProducts(int count) {
        if (count > this.getCount()) {
            throw new IllegalArgumentException("The count " + count + " is more then the count of the stock" + this.getCount());
        }

        return stock.stream()
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Product> findAllByQuantity(int quantity) {
        return stock.stream()
                .filter(p -> p.getQuantity() == quantity)
                .collect(Collectors.toList());
    }

    @Override
    public Iterator<Product> iterator() {
        return stock.iterator();
    }
}
