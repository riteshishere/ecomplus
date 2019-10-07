package gaurav.e_complus.Model;

public class Users {
    private String Name, Pass, Phone;

    public Users() {
    }

    public Users(String name, String pass, String phone) {
        Name = name;
        Pass = pass;
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
