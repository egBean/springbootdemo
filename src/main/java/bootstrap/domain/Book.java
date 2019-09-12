package bootstrap.domain;

import java.io.Serializable;
import javax.persistence.*;

public class Book implements Serializable {
    @Id
    private Long id;

    private String barcode;

    private String bookrecno;

    private String state;

    private Integer version;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * @param barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * @return bookrecno
     */
    public String getBookrecno() {
        return bookrecno;
    }

    /**
     * @param bookrecno
     */
    public void setBookrecno(String bookrecno) {
        this.bookrecno = bookrecno;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}