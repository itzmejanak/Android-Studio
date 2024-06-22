# Multi-tasking

Performing multiple tasks at a single time.
- **Example**: Running VLC, Word, and a browser all at the same time on a CPU.

## Uses of Multi-tasking
- Increases the performance of the CPU.

## How to Achieve Multi-tasking
Multi-tasking can be achieved in two ways:
1. **Multiprocessing (Process-based)**
2. **Multithreading (Thread-based)**

### Multiprocessing (Process-based)
- When a system is connected to multiple CPUs/processors to complete tasks.
- Best suited for system level/OS level tasks.

### Multithreading (Thread-based)
- Executing multiple threads at a single time for a single process/task/job.
- **Example** in VLC:
  - Music, video, progress bar, and timing are executing at the same time in different threads.

## Difference between Process and Thread

| Process | Thread |
| ------- | ------ |
| A program which is in executing process | It is the sub-part of the process (small task) |
| Heavyweight | Lightweight |
| Takes more time for context switching | Takes less time for context switching |
| Communication takes more time | Communication takes less time |
| Each process has a different address space | Threads share the same address space |
| Processes are independent | Threads are dependent on each other |
| Processes don't require synchronization | Threads may require synchronization |
| Higher resource consumption | Lower resource consumption |
| Requires more time for creation | Requires less time for creation |
| Termination takes more time | Termination takes less time |
