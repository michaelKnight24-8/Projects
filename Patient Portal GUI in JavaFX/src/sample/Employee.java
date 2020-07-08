package sample;

public class Employee extends Person {

    public String position, college, pastExperience, password;
    public Employee(String firstName, String lastName, String middleInitial, String email, String number,
                   String DOB, String emergencyNumber, String address, String sex, String college, String position,
                    String pastExperience, String emergencyRelation) {
        super(firstName,lastName,middleInitial,email,number,DOB, emergencyNumber,address,sex,emergencyRelation);

        this.pastExperience = pastExperience;
        this.college = college;
        this.position = position;
        //default password, which will be changed later
        this.password = firstName + " " + lastName;
    }

    public void setPassword(String password) { this.password = password; }

    public String getPosition() { return position; }

    public String getCollege() { return college; }

    public String getPastExperience() { return pastExperience; }

    public String getPassword() { return password; }
}
