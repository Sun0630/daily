package tech.sx.contentprovider;

/**
 * @Author Administrator
 * @Date 2018/2/6 0006 上午 9:57
 * @Description
 */

public class Book {
    public int bookId;
    public String bookName;

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
