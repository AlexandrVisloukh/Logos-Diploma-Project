package visloukh.libra.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import visloukh.libra.Service.BookService;
import visloukh.libra.Service.CategoryService;

@RestController
@RequestMapping("")
public class HomeController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return new ResponseEntity<>(
                categoryService.getAllCategory(), HttpStatus.OK
        );
    }
}
