package ang.gimozzi.kickdealbase.infrastructure.persistence;

import ang.gimozzi.kickdealbase.domain.product.Category;
import ang.gimozzi.kickdealbase.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductsByCategory(Category category, Pageable pageable);

    @Query(
            value = "SELECT * FROM tbl_product " +
                    "WHERE name_tsv @@ plainto_tsquery('simple', :keyword)",
            nativeQuery = true
    )
    Page<Product> findProductsByNameContaining(@Param("keyword") String keyword, Pageable pageable);

    Page<Product> findAllBySellerIdOrBuyerId(Long sellerId, Long buyerId, Pageable pageable);
}
