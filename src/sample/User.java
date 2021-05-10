package sample;

import java.time.LocalDate;
import java.util.Objects;


public class User {

    private String name;
    private String username;
    private String password;
    private String email;
    private LocalDate DoB;
    private double height;
    private double weight;


    public User(String uname, String pwd) {
        this.username = uname;
        this.password = pwd;
    }

    public User(String uname, String email, double height, double weight) {
        this.username = uname;
        this.email = email;
        this.height = height;
        this.weight = weight;
    }

    public User(String name, String uname, String email, double height, double weight, LocalDate DoB) {
        this.name = name;
        this.username = uname;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.DoB = DoB;

    }

    public String getName() {
        return name;
    }

    public LocalDate getDoB() {
        return DoB;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public double getHeight() {
        return height;
    }

    public String getEmail() {
        return email;
    }

    public double getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDoB(LocalDate doB) {
        this.DoB = doB;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return "name" + name + "username : " + username + " email : " + email + " height : " + height + " weight : " + weight;
    }


    public double getBMI() {
        double bmi = weight / (height * height);
        double bmiRounded = Math.round(bmi * 100.0) / 100.0;
        return bmiRounded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Double.compare(user.height, height) == 0 &&
                Double.compare(user.weight, weight) == 0 &&
                Objects.equals(name, user.name) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(DoB, user.DoB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, username, password, email, DoB, height, weight);
    }
}