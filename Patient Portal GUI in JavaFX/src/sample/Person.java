package sample;

//base class for the employee and patient to inherit from
public class Person {
   public String firstName, lastName, middleInitial, email, number, DOB, emergencyNumber, address,
           sex, emergencyRelation;

    public Person(String firstName, String lastName, String middleInitial, String email, String number, String DOB,
                  String emergencyNumber, String address, String sex, String emergencyRelation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInitial = middleInitial;
        this.email = email;
        this.number = number;
        this.DOB = DOB;
        this.emergencyNumber = emergencyNumber;
        this.address = address;
        this.sex = sex;
        this.emergencyRelation = emergencyRelation;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getMiddleInitial() { return middleInitial; }

    public String getEmail() { return email; }

    public String getNumber() { return number; }

    public String getDOB() { return DOB; }

    public String getEmergencyNumber() { return emergencyNumber; }

    public String getAddress() { return address; }

    public String getSex() { return sex; }

    public String getEmergencyRelation() { return emergencyRelation; }
}
