package bootstrap.service;

import bootstrap.domain.Book;
import bootstrap.mapper.BookMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    private static final AtomicLong  AL = new AtomicLong();


    @Transactional
    public void create(String bookrecno,int count){

        for(int i = 0 ;i<count;i++){
            Book book = new Book();
            book.setBarcode(System.currentTimeMillis()+""+AL.getAndIncrement());
            book.setBookrecno(bookrecno);
            book.setState("1");
            bookMapper.insertSelective(book);
        }

    }

    @Transactional
    public void order(String bookrecno) {


        List<Book> list = bookMapper.query(bookrecno);

        int i = 0;
        if(list != null && list.size()>0){

            for(Book b : list){
                Example example = new Example(Book.class);
                example.createCriteria().andEqualTo("id",b.getId()).andEqualTo("version",b.getVersion());
                b.setState("0");
                b.setVersion(b.getVersion()+1);
                i = bookMapper.updateByExampleSelective(b, example);
                if(i == 1) return;
            }

        }
        throw  new RuntimeException("无可卖书籍了!");
    }
}
