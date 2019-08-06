package visloukh.libra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import visloukh.libra.Entity.BookEntity;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    List<BookEntity> findAllByCategoryId (Integer categoryID);
    List<BookEntity> findAllByCategoryName (String categoryName);
//    BookEntity findByFileName(String fileName);
//    boolean existsByFileName(String fileName);
}
