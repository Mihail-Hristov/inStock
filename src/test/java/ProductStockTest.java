import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProductStockTest {

    private ProductStock stock;

    @Before
    public void initializationStock() {
        stock = new Instock();
    }

    @Test
    public void testGetCorrectCountFromStock() {
        Product product = createProducts();
        Assert.assertEquals(0, stock.getCount());
        stock.add(product);
        Assert.assertEquals(1, stock.getCount());
    }

    @Test
    public void testContainsThatCurrentProductIsPresentInTheStack() {
        Product product = createProducts();
        Assert.assertFalse(stock.contains(product));

        stock.add(product);
        Assert.assertTrue(stock.contains(product));

        Product notPresentProduct = new Product("No_Exist_Label", 3, 1);
        Assert.assertFalse(stock.contains(notPresentProduct));
    }

    @Test
    public void testAddProductCorrectlyInTheStock() {
        Assert.assertEquals(0, stock.getCount());
        Product product = createProducts();
        stock.add(product);
        Assert.assertEquals(1, stock.getCount());
        Assert.assertTrue(stock.contains(product));
    }

    @Test
    public void testFindByIndexWhenIndexIsInTheBeginning() {
        Product product = createProducts();
        stock.add(product);
        Product findByIndex = stock.find(0);
        Assert.assertNotNull(findByIndex);
        Assert.assertEquals(product.getLabel(), findByIndex.getLabel());
    }

    @Test
    public void testFindByIndexWhenIndexIsInTheEnd() {
        fillProductsInStack(5);

        Product product = createProducts();
        stock.add(product);
        Product findByIndex = stock.find(stock.getCount() - 1);
        Assert.assertNotNull(findByIndex);
        Assert.assertEquals(product.getLabel(), findByIndex.getLabel());
    }

    @Test
    public void testFindByIndexWhenIndexIsInTheMiddle() {
        int count = 5;
        fillProductsInStack(count);

        Product product = createProducts();
        stock.add(product);

        fillProductsInStack(count);
        Product findByIndex = stock.find(stock.getCount() - (count + 1));
        Assert.assertNotNull(findByIndex);
        Assert.assertEquals(product.getLabel(), findByIndex.getLabel());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testFindByIndexWhenIndexIsOutOfBound() {
        Product product = createProducts();
        stock.add(product);
        Product findByIndex = stock.find(stock.getCount());
        Assert.assertNotNull(findByIndex);
        Assert.assertEquals(product.getLabel(), findByIndex.getLabel());
    }




    private static Product createProducts() {
        return new Product("Test_level_1", 3, 1);
    }

    private static Product[] createMultipleProducts(int count) {
        Product[] result = new Product[count];

        for (int i = 0; i < result.length; i++) {
            result[i] = new Product("Test_Label_" + i, 3 + (4.0 / i), 1);
        }
        return result;
    }

    private void fillProductsInStack(int count) {
        Product[] products = createMultipleProducts(count);

        for (Product product : products) {
            stock.add(product);
        }
    }

}
