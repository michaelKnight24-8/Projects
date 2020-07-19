package sample;

public class Appointment {
    public String date, patientName, nurse, doctor, drugsPrescribed,
        additionalRemarks, reasonForAppointment, time, room;

    public Appointment(String date, String patientName, String nurse, String doctor,
                       String drugsPrescribed, String additionalRemarks, String reasonForAppointment,
                       String time, String room) {
        this.patientName = patientName;
        this.nurse = nurse;
        this.doctor = doctor;
        this.drugsPrescribed = drugsPrescribed;
        this.additionalRemarks = additionalRemarks;
        this.reasonForAppointment = reasonForAppointment;
        this.room = room;
        this.time = time;
        this.date = date;
    }

    public String getRoom() { return room; }

    public String getTime() { return time; }

    public String getDate() {
        return date;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getNurse() {
        return nurse;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getDrugsPrescribed() {
        return drugsPrescribed;
    }

    public String getAdditionalRemarks() {
        return additionalRemarks;
    }

    public String getReasonForAppointment() {
        return reasonForAppointment;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "date='" + date + '\'' +
                ", patientName='" + patientName + '\'' +
                ", nurse='" + nurse + '\'' +
                ", doctor='" + doctor + '\'' +
                ", drugsPrescribed='" + drugsPrescribed + '\'' +
                ", additionalRemarks='" + additionalRemarks + '\'' +
                ", reasonForAppointment='" + reasonForAppointment + '\'' +
                ", time='" + time + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}
