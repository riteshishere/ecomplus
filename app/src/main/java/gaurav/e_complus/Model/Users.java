package gaurav.e_complus.Model;

public class Users {
    private String DOB, Name, Pass, Phone, UserId;

    public Users() {
    }

//    public Users(String DOB, String name, String pass, String phone, String userId) {
//        this.DOB = DOB;
//        this.Name = name;
//        this.Pass = pass;
//        this.Phone = phone;
//        this.UserId = userId;
//    }

    public String getDOB() { return DOB; }
    public void setDOB(String DOB) { this.DOB = DOB; }
    public String getName() { return Name; }
    public void setName(String name) { Name = name; }
    public String getPass() { return Pass; }
    public void setPass(String pass) { Pass = pass; }
    public String getPhone() { return Phone; }
    public void setPhone(String phone) { Phone = phone; }
    public String getUserId() { return UserId; }
    public void setUserId(String userId) { UserId = userId; }
}
