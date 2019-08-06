package visloukh.libra.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import visloukh.libra.Domain.BookDTO;
import visloukh.libra.Domain.ChapterDTO;
import visloukh.libra.Entity.BookEntity;
import visloukh.libra.Entity.ChapterEntity;
import visloukh.libra.Service.BookService;
import visloukh.libra.Service.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private FileStorageService fileStorageService;

    public final String SEPARATOR = System.getProperty("file.separator");
    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        return new ResponseEntity<>(
                bookService.getAllBooks(), HttpStatus.OK
        );
    }

    @GetMapping("id={bookId}")
    public ResponseEntity<?> findBookById(@PathVariable("bookId") Integer id) {
        return new ResponseEntity<>(bookService.findBookById(id), HttpStatus.OK);
    }
    @GetMapping("id={bookId}/{chapterId}")
    public ResponseEntity<?> findChapterById(@PathVariable("bookId") Integer bookId,
                                             @PathVariable("chapterId") Integer chapterId) {
        return new ResponseEntity<>(bookService.findChapterById(chapterId), HttpStatus.OK);
    }
    @GetMapping("id={bookId}/{chapterId}/download") // books/chapter?chapterName=....
    public ResponseEntity<?> getChapter(
            @PathVariable("bookId") Integer bookId,
            @PathVariable("chapterId") Integer chapterId)
    {
        String name= bookService.findChapterById(chapterId).getPath();
        System.out.println("books"+SEPARATOR+"chapters" +" getMapping");
        Resource resource = fileStorageService.loadFile(name,"books"+SEPARATOR+"chapters");

        return ResponseEntity.ok()
                //   .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    @PutMapping("id={bookId}")
    public ResponseEntity<?> updateBook(
            @PathVariable("bookId") Integer id,
            @RequestBody BookDTO bookDTO) {

        bookService.updateBook(bookDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("id={bookId}/{chapterId}")
    public ResponseEntity<?> updateChapter(
            @PathVariable("bookId") Integer id,
            @PathVariable("chapterId") Integer chapterId,
            @RequestBody ChapterDTO chapterDTO) {

        bookService.updateChapter(chapterDTO,chapterId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("id={bookId}")
    public ResponseEntity<?> createChapter(
            @PathVariable("bookId") int bookId,
            @Valid @RequestBody ChapterDTO chapterDTO) {

        BookEntity book= bookService.addChapterToBook(bookId,chapterDTO);
        ChapterEntity addedChapter = book.getChapters().get(book.getChapters().size()-1);
        return new ResponseEntity<>(addedChapter,HttpStatus.CREATED);
    }
    @PostMapping("id={bookId}/image")
    public ResponseEntity<?> uploadImage(
            @PathVariable("bookId") int id,
            @RequestParam("imageFile") MultipartFile file
    ) {
        System.out.println(file.getOriginalFilename());
        String newFileName= fileStorageService.storeFile(file,id,"books");
        bookService.addImageToBook(id, newFileName);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PostMapping("id={bookId}/{chapterId}/file")
    public ResponseEntity<?> uploadChapter(
            @PathVariable("bookId") int id,
            @PathVariable("chapterId") int chapterId,
            @RequestParam("chapterFile") MultipartFile file
    ) {
        System.out.println(file.getOriginalFilename());

        String newFileName= fileStorageService.storeFile(file,chapterId,"books"+SEPARATOR+"chapters");
        ChapterEntity chapter=bookService.addFileToChapter(chapterId, newFileName);
        return new ResponseEntity<>(chapter,HttpStatus.ACCEPTED);
    }
    @GetMapping("page") // /Books/page?page=0&size=20&sort=
    public ResponseEntity<?> getBooksByPage(@PageableDefault Pageable pageable) {
        return new ResponseEntity<>(bookService.getBooksByPage(pageable), HttpStatus.OK);
    }
    @DeleteMapping("{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") Integer id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("id={bookId}/{chapterId}")
    public ResponseEntity<?> deleteChapter(@PathVariable("bookId") Integer bookId,
                                           @PathVariable("chapterId") Integer chapterId) {
        bookService.deleteChapterById(chapterId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("image") // books/image?imageName=....
    public ResponseEntity<?> getImage(
            @RequestParam("imageName") String name
//            HttpServletRequest servletRequest
    ) {

        Resource resource = fileStorageService.loadFile(name,"books");

        return ResponseEntity.ok()
                //   .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
