# KFUPM Clinic System — Two-Phase Work Division

## Team Size

Two members

## Project Goal

Build a command-line clinic management system using custom implementations of the required data structures and algorithms.

The system includes:

- Patient management
- Scheduled appointments
- Walk-in patients
- Urgent patients
- Visit logs
- Undo functionality
- Log search using Naive and KMP string matching

---

# Phase 1 — Build the Core Structures

## Goal

Finish all required data structures and string matching algorithms before connecting the full clinic system logic.

## Member 1 Responsibilities

Member 1 focuses on patient storage and appointment storage.

### Files / Components

- `ds/HashTable.java`
- `ds/AVLTree.java`

### Main Work

- Implement the hash table.
- Implement collision handling.
- Implement resizing for the hash table.
- Implement the AVL tree.
- Support appointment ordering by date and time.
- Prepare the basic storage needed for patients and appointments.
- Test the hash table independently.
- Test the AVL tree independently.

### Phase 1 Output for Member 1

By the end of Phase 1, Member 1 should have:

- Patient lookup structure ready.
- Appointment storage structure ready.
- Hash table tested.
- AVL tree tested.
- Meaningful GitHub commits on related branches.

---

## Member 2 Responsibilities

Member 2 focuses on patient flow, logs, undo, urgent cases, and string matching.

### Files / Components

- `ds/LinkedQueue.java`
- `ds/LinkedStack.java`
- `ds/SinglyLinkedList.java`
- `ds/MaxHeap.java`
- `matching/NaiveMatcher.java`
- `matching/KMPMatcher.java`

### Main Work

- Implement the linked queue for walk-in patients.
- Implement the linked stack for undo history.
- Implement the singly linked list for visit logs.
- Implement the max heap for urgent patients.
- Implement Naive string matching.
- Implement KMP string matching.
- Test each structure independently.
- Test both string matching algorithms.

### Phase 1 Output for Member 2

By the end of Phase 1, Member 2 should have:

- Walk-in queue ready.
- Undo stack ready.
- Visit log list ready.
- Urgent patient heap ready.
- Naive and KMP search ready.
- Meaningful GitHub commits on related branches.

---

# Phase 2 — Connect Features and Finalize

## Goal

Connect all data structures inside `ClinicServiceImpl.java`, test the full system, prepare the report, and get ready for the demo.

---

## Member 1 Responsibilities

Member 1 owns the patient and appointment commands.

### Patient Commands

- `ADD_PATIENT`
- `FIND_PATIENT`
- `DELETE_PATIENT`

### Appointment Commands

- `ADD_APPT`
- `CANCEL_APPT`
- `FIND_APPT`
- `VIEW_DAY`
- `VIEW_RANGE`

### Main Work

- Connect the hash table to patient commands.
- Connect the AVL tree to appointment scheduling.
- Connect appointment lookup using the appointment hash table.
- Handle appointment cancellation.
- Support viewing appointments by day.
- Support viewing appointments by time range.
- Test patient-related cases.
- Test appointment-related cases.

### Phase 2 Output for Member 1

By the end of Phase 2, Member 1 should have:

- Patient commands working.
- Appointment commands working.
- Appointment search and cancellation working.
- Day and range views working.
- Patient and appointment test cases completed.

---

## Member 2 Responsibilities

Member 2 owns walk-ins, urgent patients, serving, logs, search, and undo.

### Walk-in Commands

- `ADD_WALKIN`
- `VIEW_WALKINS`

### Urgent Patient Commands

- `ADD_URGENT`
- `PEEK_URGENT`
- `VIEW_URGENTS`

### Serving, Search, and Undo Commands

- `SERVE_NEXT`
- `SEARCH_LOG_NAIVE`
- `SEARCH_LOG_KMP`
- `UNDO`

### Main Work

- Connect the linked queue to walk-in commands.
- Connect the max heap to urgent patient commands.
- Connect the linked list to visit logs.
- Connect the stack to undo functionality.
- Connect Naive and KMP matching to log search.
- Implement the serving order:
  1. Urgent patients
  2. Walk-in patients
  3. Earliest scheduled appointment
- Test serving behavior.
- Test log search.
- Test undo behavior.

### Phase 2 Output for Member 2

By the end of Phase 2, Member 2 should have:

- Walk-in commands working.
- Urgent patient commands working.
- Serving logic working.
- Visit log search working.
- Undo functionality working.
- Serving, search, and undo test cases completed.

---

# Shared Responsibilities

Both members should work together on:

- GitHub setup
- Private repository creation
- Feature branches
- Integration testing
- Fixing bugs
- Checking forbidden library usage
- Final PDF report
- Demo preparation
- Q&A preparation

---

# Suggested GitHub Branches

## Member 1 Branches

- `feature/hash-table`
- `feature/avl-tree`
- `feature/patients`
- `feature/appointments`

## Member 2 Branches

- `feature/queue-stack-list`
- `feature/heap`
- `feature/string-matching`
- `feature/serving-undo-log`

---

# Recommended Work Order

## Member 1

1. Hash Table
2. AVL Tree
3. Patient commands
4. Appointment commands
5. Appointment testing

## Member 2

1. Queue, Stack, and Singly Linked List
2. Max Heap
3. Naive and KMP string matching
4. Walk-in and urgent commands
5. Serving, logs, and undo

---

# Final Ownership Summary

| Area | Owner |
|---|---|
| Patients | Member 1 |
| Appointments | Member 1 |
| Hash Table | Member 1 |
| AVL Tree | Member 1 |
| Walk-ins | Member 2 |
| Urgent Patients | Member 2 |
| Max Heap | Member 2 |
| Queue | Member 2 |
| Stack | Member 2 |
| Linked List | Member 2 |
| Visit Logs | Member 2 |
| String Matching | Member 2 |
| Undo | Member 2 |
| Report | Both |
| Demo | Both |
| Final Testing | Both |

---

# Notes

- Do not use forbidden ready-made Java data structures.
- Make meaningful commits throughout the project.
- Both members should understand all parts of the project before the demo.
- Keep the work simple, organized, and focused on finishing as soon as possible.





