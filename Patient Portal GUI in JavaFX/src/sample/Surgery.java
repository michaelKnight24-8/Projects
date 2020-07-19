package sample;

public class Surgery {
    public int OR;
    public String surgeon, anes, surgeryType, patient, time, RN, scrub, patientRoom, date, results;

    public Surgery(int OR, String surgeon, String anes, String surgeryType, String patient, String time, String RN,
                   String scrub, String patientRoom, String date, String results) {
        this.OR = OR;
        this.surgeon = surgeon;
        this.anes = anes;
        this.surgeryType = surgeryType;
        this.patient = patient;
        this.time = time;
        this.RN = RN;
        this.scrub = scrub;
        this.patientRoom = patientRoom;
        this.date = date;
        this.results = results;
    }

    public String getDate() { return date; }

    public int getOR() { return OR; }

    public String getSurgeon() { return surgeon; }

    public String getAnes() {
        return anes;
    }

    public String getSurgeryType() {
        return surgeryType;
    }

    public String getPatient() {
        return patient;
    }

    public String getTime() {
        return time;
    }

    public String getRN() {
        return RN;
    }

    public String getResults() { return results; }

    public String getScrub() {
        return scrub;
    }

    public String getPatientRoom() { return patientRoom; }

    @Override
    public String toString() {
        return "Surgery{" +
                "OR=" + OR +
                ", surgeon='" + surgeon + '\'' +
                ", anes='" + anes + '\'' +
                ", surgeryType='" + surgeryType + '\'' +
                ", patient='" + patient + '\'' +
                ", time='" + time + '\'' +
                ", RN='" + RN + '\'' +
                ", scrub='" + scrub + '\'' +
                ", patientRoom='" + patientRoom + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
