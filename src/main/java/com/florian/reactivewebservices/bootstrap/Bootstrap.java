package com.florian.reactivewebservices.bootstrap;

import com.florian.reactivewebservices.domain.Category;
import com.florian.reactivewebservices.domain.Vendor;
import com.florian.reactivewebservices.repositories.CategoryRepository;
import com.florian.reactivewebservices.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    //    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("tessssss");
        if (categoryRepository.count().block() == 0) {
            initializeCategories();
        }
//        initializeCustomers();
        if (vendorRepository.count().block() == 0) {
            initialiseVendors();
        }

    }

    private void initialiseVendors() {
        for (int i = 0; i < 5; i++) {
            Vendor vendor = new Vendor();
            vendor.setFirstName(RandomStringUtils.random(10, true, false));
            vendor.setLastName(RandomStringUtils.random(10, true, false));
            vendorRepository.save(vendor).block();
        }
        log.debug("Vendors Data Loaded = " + vendorRepository.count().block());

    }

    private void initializeCategories() {
        Category fruits = new Category();
        fruits.setDescription("Fruits");

        Category dried = new Category();
        dried.setDescription("Dried");

        Category fresh = new Category();
        fresh.setDescription("Fresh");

        Category exotic = new Category();
        exotic.setDescription("Exotic");

        Category nuts = new Category();
        nuts.setDescription("Nuts");


        categoryRepository.save(fruits).block();
        categoryRepository.save(dried).block();
        categoryRepository.save(fresh).block();
        categoryRepository.save(exotic).block();
        categoryRepository.save(nuts).block();


        log.debug("Category Data Loaded = " + categoryRepository.count().block());
    }

    /*private void initializeCustomers() {
        Customer customer1 = new Customer();
        customer1.setFirstName("Florian");
        customer1.setLastName("Lowe");

        Customer customer2 = new Customer();
        customer2.setFirstName("Yasmine");
        customer2.setLastName("Wiwa");

        Customer customer3 = new Customer();
        customer3.setFirstName("Nathan");
        customer3.setLastName("Lowe");

        customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));

        log.debug("Customer Data Loaded = " + customerRepository.count());

    }*/
}
