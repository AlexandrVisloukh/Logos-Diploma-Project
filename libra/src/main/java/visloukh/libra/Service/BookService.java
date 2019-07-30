package visloukh.libra.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import visloukh.libra.Domain.BookDTO;
import visloukh.libra.Domain.ChapterDTO;
import visloukh.libra.Entity.BookEntity;
import visloukh.libra.Entity.ChapterEntity;

import java.util.List;

public interface BookService {

    boolean existBook(int id);

    void createBook (BookDTO bookDTO);
    void createBook (BookEntity bookEntity);

    BookEntity updateBook(BookDTO bookDTO);

    void createDefaultBooks();

    List<BookEntity> findAllBooksByCategoryId(Integer categoryId);
    List<BookEntity> findAllBooksByCategoryName(String categoryName);

    BookEntity findBookById(Integer id);

    List<BookEntity> getAllBooks();

    Page<BookEntity> getBooksByPage(Pageable pageable);
    void deleteBook(int i);
    void addImageToBook(int id, String fileName);

    BookEntity addChapterToBook(int bookId, ChapterDTO chapterDTO);

    public void createDefaultChapters();
    ChapterEntity findChapterById(int id);

    void deleteChapterById(int id);
    ChapterEntity updateChapter(ChapterDTO chapterToUpdate, int chapterId);
    ChapterEntity addFileToChapter(int chapterId, String fileName);
}
