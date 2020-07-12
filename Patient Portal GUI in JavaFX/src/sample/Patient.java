package sample;

public class Patient extends Person {
    public String height, weight;

    public Patient(String firstName, String lastName, String middleInitial, String email, String number,
                   String DOB, String emergencyNumber, String address, String height, String weight, String sex,
                   String emergencyRelation) {
        super(firstName,lastName,middleInitial,email,number,DOB, emergencyNumber,address,sex,emergencyRelation);

        this.height = height;
        this.weight = weight;
    }

    public String getHeight() { return height; }

    public String getWeight() { return weight; }


}
