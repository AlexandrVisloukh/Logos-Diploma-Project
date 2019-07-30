package visloukh.libra.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import visloukh.libra.Domain.BookDTO;
import visloukh.libra.Domain.ChapterDTO;
import visloukh.libra.Entity.BookEntity;
import visloukh.libra.Entity.ChapterEntity;
import visloukh.libra.Entity.UserEntity;
import visloukh.libra.Repository.BookRepository;
import visloukh.libra.Repository.ChapterRepository;
import visloukh.libra.Service.BookService;
import visloukh.libra.Service.CategoryService;

import visloukh.libra.Service.UserService;
import visloukh.libra.Utils.ObjectMapperUtils;
import visloukh.libra.exceptions.AlreadyExistsException;
import visloukh.libra.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
     private BookRepository bookRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
//    @Autowired
//    private ChapterService chapterService;
    @Autowired ChapterRepository chapterRepository;

    @Autowired
    private ObjectMapperUtils modelMapper;

    @Override
    public void createBook(BookEntity bookEntity) {

            bookEntity.setStatus("created");
            bookEntity.setCreateDate(new Date());
            bookRepository.save(bookEntity);
       // System.out.println("Saved to base: "+ bookEntity.toString());
    }

    @Override
    public void createBook(BookDTO bookDTO) {
        BookEntity bookEntity = modelMapper.map(bookDTO,BookEntity.class);
        createBook(bookEntity);
    }

    @Override
    public void createDefaultBooks() {

        for (int i=0; i<20; i++) {
            if (userService.existUser(1)) {
                BookEntity book = new BookEntity();
                book.setUser(userService.findUserById(1));
                if (categoryService.existCategory(1))
                book.setCategory(categoryService.findCategoryById(1));
                book.setTitle("the Book No " + i);
                book.setAuthor("admin");
                book.setDescription("some description to book No " + i);
          //     book.setFileName("book" + i + ".txt");
 //               book.setFileLogo("book" + i + ".png");

                createBook(book);
            } else System.out.println("user with this id"+1+" are not exist");
        }
    }
    @Override
    public boolean existBook(int id) {
        return bookRepository.existsById(id);
    }

    @Override
    public void deleteBook(int id) {
        BookEntity bookEntity= bookRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("book with this id ["+id+"] are not exist"));
        bookEntity.setStatus("deleted");
        bookEntity.setRedactDate(new Date());
        bookRepository.save(bookEntity);
    }

    @Override
    public BookEntity findBookById(Integer id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<BookEntity> findAllBooksByCategoryId(Integer categoryId) {
        if(categoryService.existCategory(categoryId)){
        return bookRepository.findAllByCategoryId(categoryId);
    } else
        return null;
    }

    @Override
    public List<BookEntity> findAllBooksByCategoryName(String categoryName) {
        if(categoryService.existCategory(categoryName)){
            return bookRepository.findAllByCategoryName(categoryName);
        }else
            return null;
    }

    @Override
    public Page<BookEntity> getBooksByPage(Pageable pageable) {
        Page<BookEntity> bookEntities =
                bookRepository.findAll(pageable);
        // page = 0
        // size = 10
        return bookEntities;
    }

    @Override
    public void addImageToBook(int id, String fileName) {
        BookEntity bookEntity =
                bookRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Book not found"));
        bookEntity.setFileLogo(fileName);
        bookRepository.save(bookEntity);
    }
    @Override
    public ChapterEntity addFileToChapter(int chapterId, String fileName) {
        ChapterEntity chapterEntity =
                chapterRepository.findById(chapterId)
                        .orElseThrow(() -> new NotFoundException("Chapter not found"));
        chapterEntity.setPath(fileName);
        chapterEntity.setStatus("created");
        return chapterRepository.save(chapterEntity);
    }

    @Override
    public BookEntity updateBook(BookDTO bookDTO) {

        BookEntity bookToUpdate = modelMapper.map(bookDTO,BookEntity.class);
        BookEntity book = bookRepository.findById(bookToUpdate.getId()).orElseThrow(
                ()->new NotFoundException("Book with this id are not found"));
        book.setAuthor(bookToUpdate.getAuthor());
       // book.setFileName(bookToUpdate.getFileName());
        book.setTitle(bookToUpdate.getTitle());
        book.setDescription(bookToUpdate.getDescription());
        book.setStatus("updated");
        book.setCategory(categoryService.findCategoryById(book.getCategory().getId()));
        book.setRedactDate(new Date());
        return bookRepository.save(book);
    }

    @Override
    public BookEntity addChapterToBook(int bookId, ChapterDTO chapterDTO) {
        BookEntity book= bookRepository.findById(bookId).orElseThrow(()-> new NotFoundException("book with this id ["+bookId+"] is not found"));

        ChapterEntity chapter = modelMapper.map(chapterDTO,ChapterEntity.class);
            chapter.setStatus("noFile");
            chapter.setCreateDate(new Date());
            chapter=chapterRepository.save(chapter);
            book.getChapters().add(chapter);
            book.setRedactDate(new Date());
        bookRepository.save(book);
        System.out.println("chapter Saved with id= "+ chapter.getId()+" into book with id="+ bookId);

        return book;
    }

    @Override
    public void createDefaultChapters() {

        for (int bookId = 1; bookId <=10 ; bookId++) {


        for (int chapterNo = 1; chapterNo <=10 ; chapterNo++) {

        ChapterDTO chapter = new ChapterDTO();

        chapter.setChapterName("chapter No=" + chapterNo + " to book No=" + (bookId-1));
        chapter.setDescription("some description to chapter No=" + chapterNo + " to book No=" + (bookId-1));
        addChapterToBook(bookId, chapter);
        }
        }
    }
    @Override
    public ChapterEntity findChapterById(int id) {
        return chapterRepository.findById(id).orElseThrow(()-> new NotFoundException("chapter with this id ["+id+"] is not exist"));
    }

    @Override
    public void deleteChapterById(int id) {
        ChapterEntity chapter =chapterRepository.findById(id).orElseThrow(()-> new NotFoundException("chapter with this id ["+id+"] is not exist"));
        chapter.setRedactDate(new Date());
        chapter.setStatus("deleted");
        chapterRepository.save(chapter);
        System.out.println("chapter with id "+ id+ "successful deleted");

    }
    @Override
    public ChapterEntity updateChapter(ChapterDTO chapterToUpdate, int chapterId){
        ChapterEntity chapter = chapterRepository.findById(chapterId).orElseThrow(()-> new NotFoundException("chapter with this id ["+chapterId+"] is not exist"));
        chapter.setChapterName(chapterToUpdate.getChapterName());
        chapter.setDescription(chapterToUpdate.getDescription());
        if (chapter.getStatus()!="noFile"){
            chapter.setStatus("updated");
        }
        chapter.setRedactDate(new Date());
        return chapterRepository.save(chapter);
    }

}
