package visloukh.libra.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import visloukh.libra.Service.BookService;
import visloukh.libra.Service.CategoryService;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<?> getAllCategoryes() {
        return new ResponseEntity<>(
                categoryService.getAllCategory(), HttpStatus.OK
        );
    }
    @GetMapping("/id{categoryId}")
    public ResponseEntity<?> findBookByCategoryId(@PathVariable("categoryId") Integer id) {
        return new ResponseEntity<>(bookService.findAllBooksByCategoryId(id), HttpStatus.OK);
    }
    @GetMapping("{categoryName}")
    public ResponseEntity<?> findBooksByCategoryName(@PathVariable("categoryName") String name) {
        return new ResponseEntity<>(bookService.findAllBooksByCategoryName(name), HttpStatus.OK);
    }


}
