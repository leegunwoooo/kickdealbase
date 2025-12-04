package ang.gimozzi.kickdealbase.infrastructure.persistence;

import ang.gimozzi.kickdealbase.domain.product.Category;
import ang.gimozzi.kickdealbase.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByCategory(Category category);

    @Query(
            value = "SELECT * FROM tbl_product " +
                    "WHERE name_tsv @@ plainto_tsquery('simple', :keyword)",
            nativeQuery = true
    )
    List<Product> findProductsByNameContaining(@Param("keyword") String keyword);
}
