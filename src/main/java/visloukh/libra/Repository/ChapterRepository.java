package visloukh.libra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import visloukh.libra.Entity.ChapterEntity;

public interface ChapterRepository extends JpaRepository<ChapterEntity,Integer> {
  //  ChapterEntity findTopByOrderByIdDesc();
}
