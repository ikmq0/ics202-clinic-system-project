package kfupm.clinic.model;

public class UrgentPatient implements Comparable<UrgentPatient> {
    private String patientId;
    private int severity; // Higher is more severe
    private long arrivalTime;

    public UrgentPatient(String patientId, int severity, long arrivalTime) {
        this.patientId = patientId;
        this.severity = severity;
        this.arrivalTime = arrivalTime;
    }

    public String getPatientId() { return patientId; }
    public int getSeverity() { return severity; }
    public long getArrivalTime() { return arrivalTime; }

    @Override
    public int compareTo(UrgentPatient o) {
        // Higher severity comes first
        int severityCmp = Integer.compare(this.severity, o.severity);
        if (severityCmp != 0) {
            return severityCmp; // MaxHeap uses standard > for max, so natural order is fine, heap handles it.
        }
        // If severity is same, earlier arrival (smaller time) should be prioritized.
        // Wait, standard compareTo: smaller arrivalTime should be considered "larger" Priority in MaxHeap?
        // Actually, if we use MaxHeap, higher compareTo result goes to the top.
        // To make smaller arrivalTime go to top, it should be inversely compared.
        return Long.compare(o.arrivalTime, this.arrivalTime); 
    }

    @Override
    public String toString() {
        return "UrgentPatient{id='" + patientId + "', severity=" + severity + "}";
    }
}