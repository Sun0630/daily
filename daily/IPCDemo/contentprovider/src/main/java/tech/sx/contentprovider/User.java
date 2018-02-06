package tech.sx.contentprovider;

/**
 * @Author Administrator
 * @Date 2018/2/6 0006 上午 10:01
 * @Description
 */

public class User {
    public int userId;
    public String userName;
    public boolean isMale;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", isMale=" + isMale +
                '}';
    }
}
