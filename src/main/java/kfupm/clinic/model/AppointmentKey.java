package kfupm.clinic.model;

public class AppointmentKey implements Comparable<AppointmentKey> {
    private String date;
    private String time;

    public AppointmentKey(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public String getDate() { return date; }
    public String getTime() { return time; }

    @Override
    public int compareTo(AppointmentKey o) {
        int dateCmp = this.date.compareTo(o.date);
        if (dateCmp != 0) {
            return dateCmp;
        }
        return this.time.compareTo(o.time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentKey that = (AppointmentKey) o;
        return date.equals(that.date) && time.equals(that.time);
    }

    @Override
    public String toString() {
        return date + " " + time;
    }
}