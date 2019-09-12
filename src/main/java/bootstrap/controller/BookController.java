package bootstrap.controller;

import bootstrap.domain.ResponseData;
import bootstrap.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/create")
    public ResponseData addBook(String bookrecno,int count){
        try{
            bookService.create(bookrecno,count);
        }catch (Exception e){
            log.error("生成新书异常",e);
            return ResponseData.fail("生成新书异常");
        }
        return ResponseData.ok();
    }

    /**
     * 下单
     * @param bookrecno
     * @return
     */
    @GetMapping("/order")
    public ResponseData order(String bookrecno){

        try{
            bookService.order(bookrecno);
        }catch (Exception e){
            log.error("下单失败",e);
            return ResponseData.fail("下单失败，可能无馆藏了");
        }
        return ResponseData.ok();

    }


}
