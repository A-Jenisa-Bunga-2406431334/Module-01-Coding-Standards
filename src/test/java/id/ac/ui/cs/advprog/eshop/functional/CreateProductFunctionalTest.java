package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    
    @LocalServerPort
    private int serverPort;
    
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;
    
    private String baseUrl;
    
    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }
    
    @Test
    void createProduct_isCorrect(ChromeDriver driver) throws Exception {
        // Navigate to home page
        driver.get(baseUrl);
        
        // Click "Create Product" button/link
        WebElement createProductLink = driver.findElement(By.linkText("Create Product"));
        createProductLink.click();
        
        // Fill in product form
        WebElement productNameInput = driver.findElement(By.id("nameInput"));
        productNameInput.clear();
        productNameInput.sendKeys("Sampo Cap Bambang");
        
        WebElement productQuantityInput = driver.findElement(By.id("quantityInput"));
        productQuantityInput.clear();
        productQuantityInput.sendKeys("100");
        
        // Submit the form
        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();
        
        // Wait a moment for redirect
        Thread.sleep(1000);
        
        // Verify we're redirected to product list
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/list"));
        
        // Verify the product appears in the list
        WebElement productTable = driver.findElement(By.tagName("table"));
        List<WebElement> tableRows = productTable.findElements(By.tagName("tr"));
        
        // Check if product name exists in table (skip header row)
        boolean productFound = false;
        for (int i = 1; i < tableRows.size(); i++) {
            List<WebElement> cells = tableRows.get(i).findElements(By.tagName("td"));
            if (!cells.isEmpty() && cells.get(0).getText().equals("Sampo Cap Bambang")) {
                productFound = true;
                // Verify quantity is also correct
                assertEquals("100", cells.get(1).getText());
                break;
            }
        }
        
        assertTrue(productFound, "Product 'Sampo Cap Bambang' should appear in the product list");
    }
    
    @Test
    void createMultipleProducts_isCorrect(ChromeDriver driver) throws Exception {
        // Create first product
        driver.get(baseUrl + "/product/create");
        
        driver.findElement(By.id("nameInput")).sendKeys("Product A");
        driver.findElement(By.id("quantityInput")).sendKeys("50");
        driver.findElement(By.tagName("button")).click();
        
        Thread.sleep(500);
        
        // Create second product
        driver.get(baseUrl + "/product/create");
        
        driver.findElement(By.id("nameInput")).sendKeys("Product B");
        driver.findElement(By.id("quantityInput")).sendKeys("75");
        driver.findElement(By.tagName("button")).click();
        
        Thread.sleep(500);
        
        // Verify both products in list
        WebElement productTable = driver.findElement(By.tagName("table"));
        List<WebElement> tableRows = productTable.findElements(By.tagName("tr"));
        
        // Should have at least 3 rows (header + 2 products)
        assertTrue(tableRows.size() >= 3, "Should have at least 2 products in the list");
    }
    
    @Test
    void productListPage_hasCorrectNumberOfItems(ChromeDriver driver) throws Exception {
        // Navigate to product list
        driver.get(baseUrl + "/product/list");
        
        // Count initial products
        WebElement productTable = driver.findElement(By.tagName("table"));
        List<WebElement> initialRows = productTable.findElements(By.tagName("tr"));
        int initialCount = initialRows.size() - 1; // Exclude header
        
        // Add new product
        driver.get(baseUrl + "/product/create");
        driver.findElement(By.id("nameInput")).sendKeys("Test Product");
        driver.findElement(By.id("quantityInput")).sendKeys("10");
        driver.findElement(By.tagName("button")).click();
        
        Thread.sleep(500);
        
        // Verify count increased
        productTable = driver.findElement(By.tagName("table"));
        List<WebElement> newRows = productTable.findElements(By.tagName("tr"));
        int newCount = newRows.size() - 1; // Exclude header
        
        assertEquals(initialCount + 1, newCount, "Product count should increase by 1");
    }
}