package bootstrap.mapper;

import bootstrap.domain.Book;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BookMapper extends Mapper<Book> {

    public List<Book> query(String bookrecno);
}