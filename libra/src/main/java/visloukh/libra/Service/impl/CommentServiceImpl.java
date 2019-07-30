package visloukh.libra.Service.impl;

import org.hibernate.annotations.Polymorphism;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import visloukh.libra.Entity.CommentEntity;
import visloukh.libra.Repository.CommentRepository;
import visloukh.libra.Service.BookService;
import visloukh.libra.Service.CommentService;
import visloukh.libra.Service.UserService;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @Override
    public boolean commentExist(int id) {
        return commentRepository.existsById(id);
    }

    @Override
    public boolean createComment(CommentEntity commentEntity, Integer userId, Integer bookId, Integer parentId) {
        commentEntity.setParent(parentId);
        return createComment(commentEntity, userId, bookId);
    }

    @Override
        public boolean createComment(CommentEntity commentEntity,Integer userId, Integer bookId) {
            if(userService.existUser(userId)){
                if (bookService.existBook(bookId)){
                    commentEntity.setUser(userService.findUserById(userId));
                    commentEntity.setBook(bookService.findBookById(bookId));
                    commentEntity.setStatus("created");
                    commentEntity.setCreateDate(new Date());
                    commentRepository.save(commentEntity);
                    System.out.println("Saved to base: "+ commentEntity.toString());
                    return true;

                }else {
                    System.out.println("book with this id= " + bookId + " is no exist");
                    return false;
                }
            }else {
                System.out.println("user with this id= " + userId + " is no exist");
                return false;
            }
    }

        @Override
        public CommentEntity getCommentById (Integer id){
            return null;
        }
    @Override
    public List<CommentEntity> getAllComment() {
        return null;
    }
}
