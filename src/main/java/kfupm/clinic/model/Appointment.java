package kfupm.clinic.model;

public class Appointment {
    private String appointmentId;
    private String patientId;
    private String date;
    private String time;
    private String doctor;

    public Appointment(String appointmentId, String patientId, String date, String time, String doctor) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.date = date;
        this.time = time;
        this.doctor = doctor;
    }

    public String getAppointmentId() { return appointmentId; }
    public String getPatientId() { return patientId; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getDoctor() { return doctor; }
    
    public AppointmentKey getKey() {
        return new AppointmentKey(date, time);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", doctor='" + doctor + '\'' +
                '}';
    }
}