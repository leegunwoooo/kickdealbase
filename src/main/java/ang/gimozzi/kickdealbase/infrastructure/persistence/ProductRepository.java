package ang.gimozzi.kickdealbase.infrastructure.persistence;

import ang.gimozzi.kickdealbase.domain.product.Category;
import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.domain.product.Status;
import ang.gimozzi.kickdealbase.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductsByCategoryAndStatus(Category category, Status status, Pageable pageable);

    Page<Product> findAllByStatus(Status status, Pageable pageable);

    @Query(
            value = "SELECT * FROM tbl_product " +
                    "WHERE name_tsv @@ plainto_tsquery('simple', :keyword)",
            nativeQuery = true
    )
    Page<Product> findProductsByNameContainingAndStatus(@Param("keyword") String keyword, Pageable pageable, Status status);

    Page<Product> findAllBySellerOrBuyer(User seller, User buyer, Pageable pageable);
}
