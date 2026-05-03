package kfupm.clinic.model;

public class VisitLogEntry {
    private String patientId;
    private String doctor;
    private String note;
    private String timestamp;

    public VisitLogEntry(String patientId, String doctor, String note, String timestamp) {
        this.patientId = patientId;
        this.doctor = doctor;
        this.note = note;
        this.timestamp = timestamp;
    }

    public String getPatientId() { return patientId; }
    public String getDoctor() { return doctor; }
    public String getNote() { return note; }
    public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "[" + timestamp + "] Patient " + patientId + " seen by " + doctor + " - Note: " + note;
    }
}