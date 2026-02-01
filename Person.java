package model;

public abstract class Person {
    private String name;
    private String personID;
    private String password;

    public Person(String name, String personID, String password) {
        this.name = name;
        this.personID = personID;
        this.password = password;
    }

    public boolean login(String inputPassword) {
        return password.equals(inputPassword);
    }

    public void changePassword(String oldPass, String newPass) {
        if (password.equals(oldPass)) {
            password = newPass;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPersonID() {
        return personID;
    }

    public String getPassword() {
        return password;
    }
}