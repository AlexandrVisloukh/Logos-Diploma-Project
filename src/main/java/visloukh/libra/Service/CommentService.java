package visloukh.libra.Service;

import visloukh.libra.Entity.BookEntity;
import visloukh.libra.Entity.CommentEntity;
import visloukh.libra.Entity.UserEntity;

import java.util.List;

public interface CommentService {

        boolean commentExist (int id);

        boolean createComment (CommentEntity commentEntity, Integer userId, Integer bookId);
        boolean createComment (CommentEntity commentEntity, Integer userId, Integer bookId, Integer parentId);

        CommentEntity getCommentById(Integer id);

        List<CommentEntity> getAllComment();


        }
