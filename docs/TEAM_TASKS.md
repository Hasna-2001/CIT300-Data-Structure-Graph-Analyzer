# Team Task Assignment - CIT300 Assignment 2

Group Leader: **MS Faathima Hasna (23da2-1093)**

| Member | ID | Responsibility | Files owned | Branch |
|--------|----|----------------|-------------|--------|
| MS Faathima Hasna (Leader) | 23da2-1093 | Graph, Performance comparison, Main integration, testing, GitHub management | `graph/Graph.java`, `graph/TraversalResult.java`, `analysis/PerformanceAnalyzer.java`, `menu/GraphMenu.java`, `menu/PerformanceMenu.java`, `util/ConsoleUI.java`, `Main.java`, `test/TestRunner.java`, README, docs | `feature/graph-integration` (+ `setup/foundation`) |
| MF Sheeraz Gulzum | 23da2-0589 | Array and Searching | `ds/DynamicArray.java`, `search/SearchAlgorithms.java`, `search/SearchResult.java`, `menu/ArrayMenu.java`, `menu/SearchMenu.java` | `feature/array-searching` |
| R Halidha Nashath | 23da2-0535 | Stack and Queue | `ds/ArrayStack.java`, `ds/CircularQueue.java`, `ds/EmptyStructureException.java`, `menu/StackMenu.java`, `menu/QueueMenu.java` | `feature/stack-queue` |
| M Hathiqu Ahamath | 23da2-0526 | Linked List, input validation, results log | `ds/SinglyLinkedList.java`, `util/InputValidator.java`, `util/ResultLog.java`, `menu/LinkedListMenu.java` | `feature/linked-list-validation` |

Why the leader owns the heaviest and most visible parts: the graph component is mandatory and the most
conceptual (BFS/DFS), and the leader also integrates everything, runs the performance comparison and
verifies the whole system. This shows ownership of the project as a whole.

## Detailed tasks

### Hasna (Leader)
1. Create the GitHub repository, add all members as collaborators, protect `main` (pull request required).
2. Create 4 GitHub issues (one per member) and a project board (To do / In progress / Done).
3. Commit the foundation first (`.gitignore`, README, `util/ConsoleUI.java`) so everyone can pull it.
4. Implement and explain the Graph (adjacency list, BFS, DFS, shortest path, generator).
5. Implement PerformanceAnalyzer + menus; Implement Main; write/run TestRunner.
6. Review and merge every member's pull request; fix integration conflicts; final run of `./run.sh`.
7. Collect names/IDs, finalise README, merge and edit the demo video, submit through the LMS.

### Sheeraz
1. Implement DynamicArray operations (insert end/index, delete index/value, sort, fill, display).
2. Implement Linear Search and Binary Search with step counting; handle unsorted data for binary search.
3. Build ArrayMenu and SearchMenu; test with empty arrays, invalid indexes, duplicates, missing values.
4. Be ready to explain: array resizing, shifting cost, why binary search needs sorted data, O(n) vs O(log n).

### Halidha
1. Implement ArrayStack (push/pop/peek/display) with overflow and underflow handling.
2. Implement CircularQueue (enqueue/dequeue/peek/display) with wrap-around.
3. Build StackMenu (with balanced-brackets application) and QueueMenu; test empty pop/dequeue and full push/enqueue.
4. Be ready to explain: LIFO vs FIFO, why the queue is circular, why every operation is O(1).

### Hathiqu
1. Implement SinglyLinkedList (head/tail/position insert, delete, search, reverse, display).
2. Implement InputValidator and ResultLog; build LinkedListMenu.
3. Test: empty list delete, invalid position, delete head/tail, tail pointer after delete and reverse.
4. Be ready to explain: nodes and pointers, why head insert is O(1) while array front insert is O(n), reversing pointers.

## Suggested timeline
| Step | Who | What |
|------|-----|------|
| 1 | Hasna | Foundation PR merged; everyone clones and creates their branch |
| 2 | Sheeraz, Halidha, Hathiqu | Develop on own branches, small commits, open pull requests |
| 3 | Hasna | Review, merge PRs in the order in GITHUB_WORKFLOW.md |
| 4 | Hasna | Graph + performance + Main PR, run TestRunner, full manual test |
| 5 | All | Record own video section (face visible), send to Hasna |
| 6 | Hasna | Merge video (< 15 min), final checklist, submit on the LMS |
