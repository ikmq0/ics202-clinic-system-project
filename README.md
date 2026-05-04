# KFUPM Clinic System

A command-line clinic management system built using custom data structures from scratch (Hash Tables, AVL Trees, Heaps, Linked Queues, Linked Stacks, Singly Linked Lists) and String Matching algorithms (Naive, KMP).

## Team Setup & Division

**Team Size:** 2 Members

### Member 1: Patients & Appointments
* **Core Structures:** `HashTable`, `AVLTree`
* **Features:** 
  * Add, find, and delete patients.
  * Schedule, cancel, and find appointments.
  * View appointments by day and time range (sorted via AVL Tree).

### Member 2: Patient Flow, Logs & Search
* **Core Structures:** `LinkedQueue`, `LinkedStack`, `SinglyLinkedList`, `MaxHeap`
* **Algorithms:** Naive Search, KMP Search
* **Features:**
  * Manage Walk-ins (FIFO) and Urgent Patients (Severity Priority).
  * Serve patients based on priority: Urgent -> Walk-in -> Scheduled.
  * Track and search Visit Logs using string matching.
  * Undo system actions (Stack).

---

## Implementation Path (5 Stages)

Follow these stages to safely build and commit the project to GitHub:

* **Stage 1 & 2: Infrastructure & Models Setup**
  * Copy starter templates (`api`, `model`, `parser`, `ClinicSystem.java`).
  * *Commit Idea:* `chore: setup project infrastructure and models`

* **Stage 3: Connect Data Structures**
  * Link `ds/` and `matching/` tools into `ClinicServiceImpl.java`.
  * *Commit Idea:* `feat: initialize dataset mappings`

* **Stage 4: Member 1 Logic (Patients/Appts)**
  * Implement logic for `ADD_PATIENT`, `ADD_APPT`, `VIEW_DAY`, etc.
  * *Commit Idea:* `feat: implement patient and appointment logic`

* **Stage 5: Member 2 Logic (Serving/Logs/Undo)**
  * Implement logic for `SERVE_NEXT`, `ADD_URGENT`, `UNDO`, `SEARCH_LOG_KMP`, etc.
  * *Commit Idea:* `feat: implement serving, search, and undo logic`

---

## Shared Responsibilities
* Private GitHub Repository Management.
* Integration Testing & Bug Fixing.
* Final PDF Report & Lab Demo Preparation.
