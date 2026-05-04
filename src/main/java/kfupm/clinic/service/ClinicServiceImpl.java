package kfupm.clinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import kfupm.clinic.api.Result;
import kfupm.clinic.ds.*;
import kfupm.clinic.matching.*;
import kfupm.clinic.model.*;

/**
 * Students implement the system logic here.
 */
public class ClinicServiceImpl implements ClinicService {

    // Hash tables
    private final HashTable<String, Patient> patientsById = new HashTable<>();
    private final HashTable<String, Appointment> apptsById = new HashTable<>();

    // Appointment schedule index (AVL)
    private final AVLTree<AppointmentKey, Appointment> apptsByTime = new AVLTree<>();

    // Walk-ins and urgent
    private final LinkedQueue<Patient> walkIns = new LinkedQueue<>();
    private final MaxHeap<UrgentPatient> urgentHeap = new MaxHeap<>((a, b) -> {
        // Higher severity first; tie-break earlier arrival first.
        if (a.severity() != b.severity()) return Integer.compare(a.severity(), b.severity());
        return Long.compare(b.arrivalEpochMillis(), a.arrivalEpochMillis());
    });

    // Undo + log
    private final LinkedStack<Action> undo = new LinkedStack<>();
    private final SinglyLinkedList<VisitLogEntry> log = new SinglyLinkedList<>();

    private final StringMatcher naive = new NaiveMatcher();
    private final StringMatcher kmp = new KMPMatcher();

    private int nextApptId = 1;

    @Override
    public Result<Void> addPatient(String id, String name, String phone) {
        if (patientsById.get(id) != null) {
            return Result.fail("Patient already exists: " + id);
        }
        Patient p = new Patient(id, name, phone);
        patientsById.put(id, p);
        undo.push(new Action(ActionType.ADD_PATIENT, id));
        return Result.ok(null, "Patient added successfully: " + id);
    }

    @Override
    public Result<Patient> findPatient(String id) {
        Patient p = patientsById.get(id);
        if (p == null) {
            return Result.fail("Patient not found: " + id);
        }
        return Result.ok(p, "Patient found: " + id);
    }

    @Override
    public Result<Void> deletePatient(String id) {
        Patient p = patientsById.get(id);
        if (p == null) {
            return Result.fail("Patient not found: " + id);
        }
        patientsById.remove(id);
        undo.push(new Action(ActionType.DELETE_PATIENT, p));
        return Result.ok(null, "Patient deleted successfully: " + id);
    }

    @Override
    public Result<String> addAppointment(String patientId, LocalDate date, LocalTime time, String doctor) {
        Patient p = patientsById.get(patientId);
        if (p == null) {
            return Result.fail("Patient not found: " + patientId);
        }
        String apptId = newAppointmentId();
        Appointment appt = new Appointment(apptId, patientId, p.name(), p.phone(), date, time, doctor);
        AppointmentKey key = new AppointmentKey(date, time, apptId);
        
        apptsById.put(apptId, appt);
        apptsByTime.insert(key, appt);
        
        undo.push(new Action(ActionType.ADD_APPT, apptId));
        return Result.ok(apptId, "Appointment created successfully: " + apptId);
    }

    @Override
    public Result<Void> cancelAppointment(String appointmentId) {
        Appointment appt = apptsById.get(appointmentId);
        if (appt == null) {
            return Result.fail("Appointment not found: " + appointmentId);
        }
        AppointmentKey key = new AppointmentKey(appt.date(), appt.time(), appt.appointmentId());
        
        apptsById.remove(appointmentId);
        apptsByTime.delete(key);
        
        undo.push(new Action(ActionType.CANCEL_APPT, appt));
        return Result.ok(null, "Appointment cancelled successfully: " + appointmentId);
    }

    @Override
    public Result<Appointment> findAppointment(String appointmentId) {
        Appointment appt = apptsById.get(appointmentId);
        if (appt == null) {
            return Result.fail("Appointment not found: " + appointmentId);
        }
        return Result.ok(appt, "Appointment found: " + appointmentId);
    }

    @Override
    public List<Appointment> viewDay(LocalDate date) {
        List<Appointment> allAppts = apptsByTime.toList();
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : allAppts) {
            if (a.date().equals(date)) {
                result.add(a);
            }
        }
        return result;
    }

    @Override
    public List<Appointment> viewRange(LocalDate date, LocalTime start, LocalTime end) {
        List<Appointment> allAppts = apptsByTime.toList();
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : allAppts) {
            if (a.date().equals(date)) {
                // Must be >= start AND <= end
                if (!a.time().isBefore(start) && !a.time().isAfter(end)) {
                    result.add(a);
                }
            }
        }
        return result;
    }

    @Override
    public Result<Void> addWalkIn(String patientId) {
        Patient p = patientsById.get(patientId);
        if (p == null) {
            return Result.fail("Patient not found: " + patientId);
        }
        walkIns.enqueue(p);
        undo.push(new Action(ActionType.ADD_WALKIN, p));
        return Result.ok(null, "Walk-in added successfully: " + patientId);
    }

    @Override
    public List<Patient> viewWalkIns() {
        return walkIns.toList();
    }

    @Override
    public Result<Void> addUrgent(String patientId, int severity) {
        Patient p = patientsById.get(patientId);
        if (p == null) {
            return Result.fail("Patient not found: " + patientId);
        }
        UrgentPatient up = new UrgentPatient(p, severity, System.currentTimeMillis());
        urgentHeap.insert(up);
        undo.push(new Action(ActionType.ADD_URGENT, up));
        return Result.ok(null, "Urgent patient added successfully: " + patientId);
    }

    @Override
    public Result<UrgentPatient> peekUrgent() {
        UrgentPatient up = urgentHeap.peek();
        if (up == null) return Result.fail("No urgent patients.");
        return Result.ok(up, "Most urgent patient.");
    }

    @Override
    public List<UrgentPatient> viewUrgentsSnapshot() {
        return urgentHeap.toListSnapshot();
    }

    @Override
    public Result<VisitLogEntry> serveNext(String doctor, String note) {
        Patient servedPatient = null;
        Object source = null;
        String type = "";
        
        UrgentPatient up = urgentHeap.peek();
        if (up != null) {
            servedPatient = up.patient();
            source = urgentHeap.extractMax();
            type = "URGENT";
        } else if (!walkIns.isEmpty()) {
            servedPatient = walkIns.dequeue();
            source = servedPatient;
            type = "WALKIN";
        } else {
            List<Appointment> allAppts = apptsByTime.toList();
            if (allAppts.isEmpty()) {
                return Result.fail("No patients left to serve.");
            }
            Appointment earliest = allAppts.get(0);
            servedPatient = patientsById.get(earliest.patientId());
            AppointmentKey key = new AppointmentKey(earliest.date(), earliest.time(), earliest.appointmentId());
            apptsByTime.delete(key);
            apptsById.remove(earliest.appointmentId());
            source = earliest;
            type = "APPOINTMENT";
        }
        
        VisitLogEntry vlog = new VisitLogEntry(System.currentTimeMillis(), servedPatient.id(), servedPatient.name(), type, doctor, note);
        log.add(vlog);
        
        Object[] payload = new Object[] { vlog, source };
        undo.push(new Action(ActionType.SERVE, payload));
        
        return Result.ok(vlog, "Served " + type + " patient: " + servedPatient.name());
    }

    @Override
    public List<VisitLogEntry> printLog() {
        return log.toList();
    }

    @Override
    public List<VisitLogEntry> searchLogNaive(String pattern) {
        List<VisitLogEntry> all = log.toList();
        List<VisitLogEntry> matches = new ArrayList<>();
        String pat = pattern.toLowerCase();
        for (VisitLogEntry v : all) {
            if (v.notes() != null && naive.contains(v.notes().toLowerCase(), pat)) {
                matches.add(v);
            }
        }
        return matches;
    }

    @Override
    public List<VisitLogEntry> searchLogKmp(String pattern) {
        List<VisitLogEntry> all = log.toList();
        List<VisitLogEntry> matches = new ArrayList<>();
        String pat = pattern.toLowerCase();
        for (VisitLogEntry v : all) {
            if (v.notes() != null && kmp.contains(v.notes().toLowerCase(), pat)) {
                matches.add(v);
            }
        }
        return matches;
    }

    @Override
    public Result<Action> undo() {
        if (undo.isEmpty()) return Result.fail("UNDO history is empty.");
        
        Action action = undo.pop();
        switch (action.type()) {
            case ADD_PATIENT:
                String idToRemove = (String) action.payload();
                patientsById.remove(idToRemove);
                break;
            case DELETE_PATIENT:
                Patient p = (Patient) action.payload();
                patientsById.put(p.id(), p);
                break;
            case ADD_APPT:
                String aId = (String) action.payload();
                Appointment a = apptsById.get(aId);
                if (a != null) {
                    AppointmentKey aKey = new AppointmentKey(a.date(), a.time(), a.appointmentId());
                    apptsById.remove(aId);
                    apptsByTime.delete(aKey);
                }
                break;
            case CANCEL_APPT:
                Appointment ca = (Appointment) action.payload();
                AppointmentKey caKey = new AppointmentKey(ca.date(), ca.time(), ca.appointmentId());
                apptsById.put(ca.appointmentId(), ca);
                apptsByTime.insert(caKey, ca);
                break;
            case ADD_WALKIN:
                Patient targetWalkin = (Patient) action.payload();
                List<Patient> tempW = walkIns.toList();
                tempW.remove(tempW.lastIndexOf(targetWalkin));
                while (!walkIns.isEmpty()) walkIns.dequeue();
                for (Patient wp : tempW) walkIns.enqueue(wp);
                break;
            case ADD_URGENT:
                UrgentPatient targetUrgent = (UrgentPatient) action.payload();
                List<UrgentPatient> tempU = urgentHeap.toListSnapshot();
                tempU.remove(targetUrgent);
                while (!urgentHeap.isEmpty()) urgentHeap.extractMax();
                for (UrgentPatient upt : tempU) urgentHeap.insert(upt);
                break;
            case SERVE:
                Object[] payload = (Object[]) action.payload();
                VisitLogEntry vlog = (VisitLogEntry) payload[0];
                Object src = payload[1];
                
                log.remove(vlog);
                
                if (src instanceof UrgentPatient) {
                    urgentHeap.insert((UrgentPatient) src);
                } else if (src instanceof Patient) {
                    List<Patient> curWalkIns = walkIns.toList();
                    while (!walkIns.isEmpty()) walkIns.dequeue();
                    walkIns.enqueue((Patient) src);
                    for (Patient wp : curWalkIns) walkIns.enqueue(wp);
                } else if (src instanceof Appointment) {
                    Appointment reAppt = (Appointment) src;
                    apptsById.put(reAppt.appointmentId(), reAppt);
                    apptsByTime.insert(new AppointmentKey(reAppt.date(), reAppt.time(), reAppt.appointmentId()), reAppt);
                }
                break;
        }
        return Result.ok(action, "Undo successful: " + action.type());
    }

    private String newAppointmentId() {
        return "A" + (nextApptId++);
    }
}
