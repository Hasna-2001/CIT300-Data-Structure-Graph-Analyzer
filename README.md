# Data Structure & Graph Performance Analyzer

**Module:** CIT300 Data Structures and Algorithms - Graded Practical Assignment 2 (Week 12)
**Institution:** Sri Lanka Technological Campus (SLTC)
**Language:** Java (console application, no external libraries)

## Project Description
A single integrated, menu-driven Java console application that demonstrates the practical use of
data structures and algorithms: **Array, Stack, Queue, Linked List, Searching (Linear and Binary),
Graph (BFS and DFS)** and a **Performance / Complexity analyzer** that counts steps and measures
execution time so the results can be explained with Big-O notation.

## Team Members

**Group Leader**

Student Name: MS Faathima Hasna
Student ID: 23da2-1093
Assigned Responsibility: Graph Component, Performance Comparison, Main Menu Integration, Testing, GitHub/Repository Management
Individual Contribution:
- Implemented the Graph class (adjacency list, undirected/directed support)
- Implemented vertex and edge operations (add/remove vertex, add/remove edge, validation of unknown vertices, self-loops and duplicates)
- Implemented BFS traversal (queue) and iterative DFS traversal (stack), each with optional early-stop target search
- Implemented shortest path (BFS), connected components, adjacency list / adjacency matrix display and a random connected graph generator
- Implemented TraversalResult (visit order, steps, peak frontier size, time)
- Implemented PerformanceAnalyzer and Performance menu (automated benchmark, analysis of user data, complexity cheat sheet)
- Implemented Main (integration of all modules), ConsoleUI and the TestRunner self-tests
- Created and managed the GitHub repository, issues, reviews and merges

**Member 2**

Student Name: MF Sheeraz Gulzum
Student ID: 23da2-0589
Assigned Responsibility: Array and Searching Implementation
Individual Contribution:
- Implemented DynamicArray (auto-resizing int array)
- Implemented insert at end, insert at index, delete by index, delete by value, sort, random/sequential fill and display
- Implemented SearchAlgorithms: Linear Search and Binary Search with step counting and timing
- Implemented SearchResult and the Array and Searching menus (including Linear vs Binary comparison)
- Tested array and searching functionality and integrated it with the main application

**Member 3**

Student Name: R Halidha Nashath
Student ID: 23da2-0535
Assigned Responsibility: Stack and Queue Implementation
Individual Contribution:
- Implemented ArrayStack (push, pop, peek, display, overflow and underflow handling)
- Implemented CircularQueue (enqueue, dequeue, peek/front, display, circular wrap-around, overflow and underflow handling)
- Implemented EmptyStructureException for empty-structure errors
- Implemented the Stack menu (including the balanced-brackets stack application) and the Queue menu
- Tested stack and queue functionality and integrated it with the main application

**Member 4**

Student Name: M Hathiqu Ahamath
Student ID: 23da2-0526
Assigned Responsibility: Linked List Implementation, Input Validation and Results Log
Individual Contribution:
- Implemented SinglyLinkedList (insert at head/tail/position, delete by value/position, search, reverse, display)
- Implemented InputValidator (safe integer, label, yes/no and menu-range input)
- Implemented ResultLog used by "Display All Results"
- Implemented the Linked List menu
- Tested linked list and input validation, and integrated them with the main application

## Technologies Used
- Java 8 or newer (developed and tested on Java 21), console application
- Git and GitHub (branches, commits, pull requests, issues)
- Standard Java library only (java.util)

## Main System Features
| Menu | What it does |
|------|--------------|
| 1. Array | Insert (end/index), delete (index/value), search, display, sort, random fill, clear |
| 2. Stack | Push, pop, peek, display, status, balanced-brackets checker; handles overflow and empty-stack pop |
| 3. Queue | Enqueue, dequeue, peek/front, display, status (circular queue); handles overflow and empty-queue dequeue |
| 4. Linked List | Insert head/tail/position, delete by value/position, search, display, reverse |
| 5. Searching | Linear search, binary search (offers to sort first), side-by-side comparison, sorted sample datasets up to 1,000,000 |
| 6. Graph | Add vertex/edge, display adjacency list and matrix, BFS, DFS, vertex search (BFS vs DFS), shortest path, remove edge/vertex, sample campus graph, statistics |
| 7. Performance | Automated benchmark (n = 100 to 100,000), analysis of your own array and graph, complexity cheat sheet |
| 8. Display All Results | Shows every search, traversal and performance result recorded in the session |
| 9. Exit | Quits the program |

All inputs are validated (non-numbers, out-of-range choices, empty input, unknown vertices, duplicates,
invalid indexes) and empty data structures are handled with clear messages.

## Project Structure
```
src/
  Main.java                  integration (Hasna)
  ds/                        DynamicArray (Sheeraz) | ArrayStack, CircularQueue, EmptyStructureException (Halidha) | SinglyLinkedList (Hathiqu)
  search/                    SearchAlgorithms, SearchResult (Sheeraz)
  graph/                     Graph, TraversalResult (Hasna)
  analysis/                  PerformanceAnalyzer (Hasna)
  util/                      InputValidator, ResultLog (Hathiqu) | ConsoleUI (Hasna)
  menu/                      ArrayMenu, SearchMenu (Sheeraz) | StackMenu, QueueMenu (Halidha) | LinkedListMenu (Hathiqu) | GraphMenu, PerformanceMenu (Hasna)
  test/TestRunner.java       42 automated self-tests (Hasna)
docs/                        team tasks, GitHub workflow, video script, viva Q&A, submission checklist
```

## How to Run
**Requirement:** Java JDK 8 or newer (`java -version` and `javac -version` must both work).

Windows: double-click `run.bat` (or run it in Command Prompt).
Linux / macOS: `chmod +x run.sh && ./run.sh`

Manual commands:
```
javac -d out -sourcepath src src/Main.java src/test/TestRunner.java
java -cp out Main
```
Run the self-tests: `java -cp out test.TestRunner`
Run the prebuilt jar: `java -jar DataStructureGraphAnalyzer.jar`

## Complexity Summary
| Operation | Complexity |
|-----------|-----------|
| Array access / append | O(1) / O(1) amortised |
| Array insert/delete at index | O(n) |
| Stack push/pop/peek, Queue enqueue/dequeue/peek | O(1) |
| Linked list insert head/tail | O(1) |
| Linked list search / insert-delete by position | O(n) |
| Linear search | O(n) |
| Binary search (sorted data) | O(log n) |
| BFS / DFS (adjacency list) | O(V + E) |

## Collaboration
Development followed a branch-per-member workflow with pull requests reviewed by the group leader.
See `docs/GITHUB_WORKFLOW.md` and the repository's commit, branch and pull-request history.

Repository: `https://github.com/Hasna-2001/CIT300-Data-Structure-Graph-Analyzer`
