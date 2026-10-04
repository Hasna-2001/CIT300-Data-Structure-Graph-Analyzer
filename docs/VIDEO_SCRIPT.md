# Demonstration Video Script (target 13:00, must be under 15:00)

Rules from the brief: ONE merged video, every member's FACE clearly visible throughout their own section
(use a webcam overlay or split screen while screen-sharing), each member must show code, run the program,
and explain the concept, complexity and integration.

Record each section separately, then merge. Suggested order and timing:

| # | Who | Time | Content |
|---|-----|------|---------|
| 1 | Hasna (leader) | 0:00 - 1:00 | Introduce group and project, show main menu and README team table |
| 2 | Sheeraz | 1:00 - 3:45 | Array + Searching |
| 3 | Halidha | 3:45 - 6:30 | Stack + Queue |
| 4 | Hathiqu | 6:30 - 9:00 | Linked List + input validation + Display All Results |
| 5 | Hasna | 9:00 - 12:30 | Graph, Performance comparison, integration, tests, GitHub |
| 6 | Hasna | 12:30 - 13:00 | Closing |

Every personal section follows: **Face visible -> Introduction (name, ID, responsibility) -> Code ->
Demo -> Explain data structure/algorithm -> Complexity/performance -> Integration.**

## Leader opening (1:00)
"Hello, I am MS Faathima Hasna, student ID 23da2-1093, group leader. Our project is the Data Structure and
Graph Performance Analyzer for CIT300. It is one integrated Java console application with Array, Stack,
Queue, Linked List, Searching, Graph and a Performance comparison. Each of us will present our own part.
My parts are the Graph, the Performance comparison and the main menu integration." Show the main menu and
the README team table.

## Sheeraz (2:45)
1. Intro: "I am MF Sheeraz Gulzum, 23da2-0589. I implemented the Array and Searching components."
2. Code: `DynamicArray` (resize doubling in `ensureCapacity`, shifting loop in `insertAt`), then `SearchAlgorithms`.
3. Demo, Menu 1: insert 40, 10, 30, 20; insert at index 1; delete by index and by value; invalid index (shows error); search; display; sort.
4. Demo, Menu 5: option 4 load 100,000 sorted values; option 3 compare - search `199998` (last element): linear 100000 steps vs binary 17 steps. Then try an unsorted array with binary search to show the "sort now?" prompt.
5. Explain: shifting makes insert/delete O(n); linear O(n); binary halves the range so O(log n) but needs sorted data; `low + (high-low)/2` avoids overflow.
6. Integration: SearchMenu uses the same array built in the Array menu; results appear in main menu option 8.

## Halidha (2:45)
1. Intro: "I am R Halidha Nashath, 23da2-0535. I implemented the Stack and Queue."
2. Code: `ArrayStack` (`top` index, push/pop), `CircularQueue` (`(front + size) % capacity`), `EmptyStructureException`.
3. Demo, Menu 2: pop on empty stack (error message); push 8 values, 9th shows overflow; peek; display; balanced brackets `{[a+b]*(c-d)}` then `{[a+b)}`.
4. Demo, Menu 3: dequeue on empty queue (error); enqueue 8, overflow; dequeue 3, enqueue 2 more, Status shows front/rear wrapping around.
5. Explain: LIFO vs FIFO, why circular (no shifting, reuses freed cells), all operations O(1).
6. Integration: both menus plug into Main; exceptions are caught in the menu so the program never crashes.

## Hathiqu (2:30)
1. Intro: "I am M Hathiqu Ahamath, 23da2-0526. I implemented the Linked List, input validation and the results log."
2. Code: `SinglyLinkedList` (inner `Node`, head/tail, `unlink`, `reverse`), `InputValidator`, `ResultLog`.
3. Demo, Menu 4: delete on empty list (error); insert head/tail/position; delete head, middle, tail; search; reverse; invalid position.
4. Show validation: type letters and an out-of-range menu number anywhere in the program.
5. Explain: insert at head O(1), search/position operations O(n); tail pointer makes tail insert O(1); array front insert shifts n elements, list only re-points head.
6. Integration: linked list search returns the same `SearchResult` as the array; main menu option 8 shows all logged results.

## Hasna - Graph, Performance, Integration (3:30)
1. Code: `Graph` adjacency list (`Map<String, List<String>>`), `bfs` (queue) and `dfs` (explicit stack), `shortestPath`.
2. Demo, Menu 6: option 11 load campus graph; option 3 adjacency list; option 4 matrix; BFS from Gate vs DFS from Gate (different order); option 7 find Hostel; option 8 shortest path; add a bad edge to show validation.
3. Explain: adjacency list is O(V+E) memory, BFS level by level (fewest edges), DFS goes deep; both O(V+E).
4. Demo, Menu 7 -> option 1: run the benchmark. Explain: linear 100,000 steps vs binary 17; array front insert 100,000 shifts vs linked list 1; BFS and DFS full traversals match in steps but targeted search differs. Steps are exact, time varies by machine.
5. Show `Main.java` wiring, run `java -cp out test.TestRunner` (42 passed).
6. Show GitHub: branches, commits per member, merged pull requests, README.
7. Closing: "Thank you. This completes our integrated Data Structure and Graph Performance Analyzer."

## Recording tips
- Keep your face visible the entire time (webcam box in a corner); good lighting; read the ID clearly.
- Increase the console/IDE font size; do not scroll too fast.
- Export each part as MP4, merge (CapCut, DaVinci Resolve, Clipchamp or Windows Photos), check total < 15:00.
