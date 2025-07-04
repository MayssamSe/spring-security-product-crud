package net.mayssam.bdccfsmspringmvc.repository;

import net.mayssam.bdccfsmspringmvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainsIgnoreCase(String keyword);
}
