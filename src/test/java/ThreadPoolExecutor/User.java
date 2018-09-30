package ThreadPoolExecutor;

/**
 * @Author pengyd
 * @Date 2018/9/30 15:45
 * @function:
 */
public class User {


    private String pname;

    private String pphone;

    private String pdocument_no;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPphone() {
        return pphone;
    }

    public void setPphone(String pphone) {
        this.pphone = pphone;
    }

    public String getPdocument_no() {
        return pdocument_no;
    }

    public void setPdocument_no(String pdocument_no) {
        this.pdocument_no = pdocument_no;
    }

    @Override
    public String toString() {
        return "User{" +
                "pname='" + pname + '\'' +
                ", pphone='" + pphone + '\'' +
                ", pdocument_no='" + pdocument_no + '\'' +
                '}';
    }
}
