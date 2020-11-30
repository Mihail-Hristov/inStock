import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

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
        stock.find(stock.getCount());
    }

    @Test
    public void testChaneQuantityByGivenProduct() {
        int newQuantity = 5;
        Product product = createProducts();
        stock.add(product);
        stock.changeQuantity("Test_level_1", newQuantity);
        Product productWithNewQuantity = stock.find(0);
        Assert.assertNotNull(productWithNewQuantity);
        Assert.assertEquals(newQuantity, productWithNewQuantity.getQuantity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeQuantityForNotPresentProduct() {
        Product product = createProducts();
        stock.changeQuantity(product.getLabel(), product.getQuantity() + 10);
    }

    @Test
    public void testFindByLabelReturnCorrectProductByGivenLabel() {
        String testLabel = "Current_Label";
        Product product = new Product(testLabel, 3, 1);
        fillProductsInStack(10);
        stock.add(product);
        Product foundProduct = stock.findByLabel(testLabel);
        Assert.assertNotNull(foundProduct);
        Assert.assertEquals(testLabel, foundProduct.getLabel());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindByLabelWhenLabelIsNotPresentInTheStock() {
        Product product = createProducts();
        fillProductsInStack(10);
        stock.add(product);
        Product foundProduct = stock.findByLabel("No_Exist_Label");
    }

    @Test
    public void testFindFirstByAlphabeticalOrderReturnCorrectNumberOfProduct() {
        fillProductsInStack(10);
        stock.findFirstByAlphabeticalOrder(8);
        Iterable<Product> product = stock.findFirstByAlphabeticalOrder(6);
        Assert.assertNotNull(product);
        List<Product> products = createListFromIterable(product);
        Assert.assertEquals(6, products.size());
    }

    @Test
    public void testFindFirstByAlphabeticalOrderReturnCorrectProduct() {
        fillProductsInStack(10);
        stock.findFirstByAlphabeticalOrder(8);
        Iterable<Product> product = stock.findFirstByAlphabeticalOrder(6);
        Assert.assertNotNull(product);
        List<Product> returnedProducts = createListFromIterable(product);
        Set<String> expectedLabels = returnedProducts.stream()
                .map(Product::getLabel)
                .collect(Collectors.toCollection(TreeSet::new));

        int index = 0;
        for (String expectLabel : expectedLabels) {
            Assert.assertEquals(expectLabel, returnedProducts.get(index++).getLabel());
        }
        Assert.assertEquals(6, returnedProducts.size());
    }

    @Test
    public void testFindFirstByAlphabeticalOrderReturnEmptyCollectionIfArgumentIsOutOfRange() {
        fillProductsInStack(10);
        Iterable<Product> products = stock.findFirstByAlphabeticalOrder(stock.getCount() + 1);
        Assert.assertNotNull(products);
        List<Product> returnedProducts = createListFromIterable(products);
        Assert.assertTrue(returnedProducts.isEmpty());
    }

    @Test
    public void testFindAllInPriceRangeReturnCorrectProductInDescendingOrder() {
        Product product5 = new Product("Test_Label_5", 5, 1);
        Product product6 = new Product("Test_Label_6", 6, 1);
        Product product7 = new Product("Test_Label_7", 7, 1);
        Product product8 = new Product("Test_Label_8", 8, 1);
        Product product9 = new Product("Test_Label_9", 9, 1);
        Product product10 = new Product("Test_Label_10", 10, 1);
        Product product11 = new Product("Test_Label_11", 11, 1);

        stock.add(product5);
        stock.add(product6);
        stock.add(product7);
        stock.add(product8);
        stock.add(product9);
        stock.add(product10);
        stock.add(product11);

        Iterable<Product> products = stock.findAllInRange(7, 10);
        Assert.assertNotNull(products);
        List<Product> returnedList = createListFromIterable(products);

        Assert.assertEquals(3, returnedList.size());

        Assert.assertEquals("Test_Label_10", returnedList.get(0).getLabel());
        Assert.assertEquals("Test_Label_9", returnedList.get(1).getLabel());
        Assert.assertEquals("Test_Label_8", returnedList.get(2).getLabel());

    }

    @Test
    public void testFindAllInPriceReturnEmptyCollectionIsNoSuchElement() {
        Product product5 = new Product("Test_Label_5", 5, 1);
        Product product6 = new Product("Test_Label_6", 6, 1);
        Product product7 = new Product("Test_Label_7", 7, 1);
        Product product8 = new Product("Test_Label_8", 8, 1);
        Product product9 = new Product("Test_Label_9", 9, 1);
        Product product10 = new Product("Test_Label_10", 10, 1);
        Product product11 = new Product("Test_Label_11", 11, 1);

        stock.add(product5);
        stock.add(product6);
        stock.add(product7);
        stock.add(product8);
        stock.add(product9);
        stock.add(product10);
        stock.add(product11);

        Iterable<Product> products = stock.findAllInRange(11, 15);
        Assert.assertNotNull(products);
        List<Product> returnedList = createListFromIterable(products);
        Assert.assertTrue(returnedList.isEmpty());
    }

    @Test
    public void testFindAllByPriceReturnCorrectProducts() {
        Product product5 = new Product("Test_Label_5", 5, 1);
        Product product6 = new Product("Test_Label_6", 7, 1);
        Product product7 = new Product("Test_Label_7", 7, 1);
        Product product8 = new Product("Test_Label_8", 7, 1);
        Product product9 = new Product("Test_Label_9", 7, 1);
        Product product10 = new Product("Test_Label_10", 10, 1);
        Product product11 = new Product("Test_Label_11", 11, 1);

        stock.add(product5);
        stock.add(product6);
        stock.add(product7);
        stock.add(product8);
        stock.add(product9);
        stock.add(product10);
        stock.add(product11);

        Iterable<Product> products = stock.findAllByPrice(7);
        Assert.assertNotNull(products);
        List<Product> returnedList = createListFromIterable(products);
        Assert.assertEquals(4, returnedList.size());

        Assert.assertEquals(7, returnedList.get(0).getPrice(), 0);
        Assert.assertEquals(7, returnedList.get(1).getPrice(), 0);
        Assert.assertEquals(7, returnedList.get(2).getPrice(), 0);
        Assert.assertEquals(7, returnedList.get(3).getPrice(), 0);
    }

    @Test
    public void testFindAllByPriceReturnEmptyCollectionWhenNoSuchPrice(){
        Product product5 = new Product("Test_Label_5", 5, 1);
        Product product6 = new Product("Test_Label_6", 7, 1);
        Product product7 = new Product("Test_Label_7", 7, 1);
        Product product8 = new Product("Test_Label_8", 7, 1);
        Product product9 = new Product("Test_Label_9", 7, 1);
        Product product10 = new Product("Test_Label_10", 10, 1);
        Product product11 = new Product("Test_Label_11", 11, 1);

        stock.add(product5);
        stock.add(product6);
        stock.add(product7);
        stock.add(product8);
        stock.add(product9);
        stock.add(product10);
        stock.add(product11);

        Iterable<Product> products = stock.findAllByPrice(8);
        Assert.assertNotNull(products);
        List<Product> returnedList = createListFromIterable(products);
        Assert.assertTrue(returnedList.isEmpty());
    }

    @Test
    public void testFindFirstMostExpensiveProductsReturnCorrectProducts() {
        Product product5 = new Product("Test_Label_5", 5, 1);
        Product product6 = new Product("Test_Label_6", 6, 1);
        Product product7 = new Product("Test_Label_7", 7, 1);
        Product product8 = new Product("Test_Label_8", 8, 1);
        Product product9 = new Product("Test_Label_9", 9, 1);
        Product product10 = new Product("Test_Label_10", 10, 1);
        Product product11 = new Product("Test_Label_11", 11, 1);

        stock.add(product5);
        stock.add(product6);
        stock.add(product7);
        stock.add(product8);
        stock.add(product9);
        stock.add(product10);
        stock.add(product11);

        Iterable<Product> products = stock.findFirstMostExpensiveProducts(4);
        Assert.assertNotNull(products);
        List<Product> returnedList = createListFromIterable(products);
        Assert.assertEquals(4, returnedList.size());

        Assert.assertEquals(11, returnedList.get(0).getPrice(), 0);
        Assert.assertEquals(10, returnedList.get(1).getPrice(), 0);
        Assert.assertEquals(9, returnedList.get(2).getPrice(), 0);
        Assert.assertEquals(8, returnedList.get(3).getPrice(), 0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindFirstMostExpensiveProductToThrowExceptionWhenSearchMoreThanExist() {
        Product product5 = new Product("Test_Label_5", 5, 1);
        Product product6 = new Product("Test_Label_6", 6, 1);
        Product product7 = new Product("Test_Label_7", 7, 1);
        Product product8 = new Product("Test_Label_8", 8, 1);
        Product product9 = new Product("Test_Label_9", 9, 1);
        Product product10 = new Product("Test_Label_10", 10, 1);
        Product product11 = new Product("Test_Label_11", 11, 1);

        stock.add(product5);
        stock.add(product6);
        stock.add(product7);
        stock.add(product8);
        stock.add(product9);
        stock.add(product10);
        stock.add(product11);

        Iterable<Product> products = stock.findFirstMostExpensiveProducts(9);
        Assert.assertNotNull(products);
    }

    @Test
    public void testFindAllByQuantityReturnCorrectProducts() {
        Product product5 = new Product("Test_Label_5", 5, 14);
        Product product6 = new Product("Test_Label_6", 6, 7);
        Product product7 = new Product("Test_Label_7", 7, 3);
        Product product8 = new Product("Test_Label_8", 8, 11);
        Product product9 = new Product("Test_Label_9", 9, 11);
        Product product10 = new Product("Test_Label_10", 10, 17);
        Product product11 = new Product("Test_Label_11", 11, 20);

        stock.add(product5);
        stock.add(product6);
        stock.add(product7);
        stock.add(product8);
        stock.add(product9);
        stock.add(product10);
        stock.add(product11);

        Iterable<Product> products = stock.findAllByQuantity(11);
        Assert.assertNotNull(products);
        List<Product> returnedList = createListFromIterable(products);
        Assert.assertEquals(2, returnedList.size());

        Assert.assertEquals(11, returnedList.get(0).getQuantity());
        Assert.assertEquals(11, returnedList.get(1).getQuantity());
    }

    @Test
    public void testFindAllByQuantityReturnEmptyCollectionIfNoSuchQuantity() {
        Product product5 = new Product("Test_Label_5", 5, 14);
        Product product6 = new Product("Test_Label_6", 6, 7);
        Product product7 = new Product("Test_Label_7", 7, 3);
        Product product8 = new Product("Test_Label_8", 8, 11);
        Product product9 = new Product("Test_Label_9", 9, 11);
        Product product10 = new Product("Test_Label_10", 10, 17);
        Product product11 = new Product("Test_Label_11", 11, 20);

        stock.add(product5);
        stock.add(product6);
        stock.add(product7);
        stock.add(product8);
        stock.add(product9);
        stock.add(product10);
        stock.add(product11);

        Iterable<Product> products = stock.findAllByQuantity(2);
        Assert.assertNotNull(products);
        List<Product> returnedList = createListFromIterable(products);
        Assert.assertTrue(returnedList.isEmpty());
    }

    @Test
    public void testGetIterableProductReturnAllProductsInStock() {
        Product product5 = new Product("Test_Label_5", 5, 14);
        Product product6 = new Product("Test_Label_6", 6, 7);
        Product product7 = new Product("Test_Label_7", 7, 3);
        Product product8 = new Product("Test_Label_8", 8, 11);
        Product product9 = new Product("Test_Label_9", 9, 11);
        Product product10 = new Product("Test_Label_10", 10, 17);
        Product product11 = new Product("Test_Label_11", 11, 20);

        stock.add(product5);
        stock.add(product6);
        stock.add(product7);
        stock.add(product8);
        stock.add(product9);
        stock.add(product10);
        stock.add(product11);

        Iterator<Product> products = stock.iterator();
        Assert.assertNotNull(products);
        List<Product> returnedProducts = createListFromIterator(products);

        Assert.assertEquals(7, returnedProducts.size());

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

    private static <T> List<T> createListFromIterable(Iterable<T> products) {
        List<T> result = new ArrayList<>();

        for (T product : products) {
            result.add(product);
        }

        return result;
    }

    private static <T> List<T> createListFromIterator(Iterator<T> products) {
        List<T> result = new ArrayList<>();

        while (products.hasNext()) {
            result.add(products.next());
        }

        return result;
    }

}
