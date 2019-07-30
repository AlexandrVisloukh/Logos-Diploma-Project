//package visloukh.libra.Service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import visloukh.libra.Domain.ChapterDTO;
//import visloukh.libra.Entity.ChapterEntity;
//import visloukh.libra.Repository.BookRepository;
//import visloukh.libra.Repository.ChapterRepository;
//import visloukh.libra.Service.BookService;
//import visloukh.libra.Service.ChapterService;
//import visloukh.libra.Utils.ObjectMapperUtils;
//import visloukh.libra.exceptions.NotFoundException;
//
//import java.util.Date;
//
//
//@Service
//public class ChapterServiceImpl  implements ChapterService {
//    @Autowired
//    private ChapterRepository chapterRepository;
//    @Autowired
//    private BookService bookService;
//    @Autowired
//    private ObjectMapperUtils modelMapper;
//
//    @Override
//    public ChapterEntity findChapterById(int id) {
//        return chapterRepository.findById(id).orElseThrow(()-> new NotFoundException("chapter with this id ["+id+"] is not exist"));
//    }
//
//    @Override
//    public void deleteChapterById(int id) {
//        ChapterEntity chapter =chapterRepository.findById(id).orElseThrow(()-> new NotFoundException("chapter with this id ["+id+"] is not exist"));
//        chapter.setRedactDate(new Date());
//        chapter.setStatus("deleted");
//        chapterRepository.save(chapter);
//        System.out.println("chapter with id "+ id+ "successful deleted");
//
//    }
//    @Override
//    public ChapterEntity updateChapter(ChapterDTO chapterToUpdate, int chapterId){
//        ChapterEntity chapter = chapterRepository.findById(chapterId).orElseThrow(()-> new NotFoundException("chapter with this id ["+chapterId+"] is not exist"));
//        chapter.setChapterName(chapterToUpdate.getChapterName());
//        chapter.setDescription(chapterToUpdate.getDescription());
//        chapter.setStatus("updated");
//        chapter.setRedactDate(new Date());
//        return chapterRepository.save(chapter);
//    }
//
//}
