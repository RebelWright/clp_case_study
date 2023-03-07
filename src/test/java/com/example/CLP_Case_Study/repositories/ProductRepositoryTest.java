package com.example.CLP_Case_Study.repositories;

import com.example.CLP_Case_Study.models.Product;
import com.sun.xml.bind.marshaller.XMLWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void findAllByProductTypeSuccess() {
        Product product1 = new Product(1,"Product 1","Type A","Description A", 10.99, "image.com");
        productRepository.save(product1);

        Product product2 = new Product(2,"Product 2","Type B","Description B", 19.99,"image.com2");
        productRepository.save(product2);
        entityManager.flush();

        Optional<List<Product>> products = productRepository.findAllByProductType("Type A");
        assertTrue(products.isPresent());
        assertEquals(1, products.get().size());
        assertEquals(product1.getProductId(), products.get().get(0).getProductId());
    }

}