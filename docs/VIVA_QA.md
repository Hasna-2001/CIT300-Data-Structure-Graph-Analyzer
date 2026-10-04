# Viva / Explanation Cheat Sheet

Every member must be able to explain their own work. Read your section and make sure you can answer in your own words.

## Sheeraz - Array and Searching
- **Why does insert at an index cost O(n)?** Every element after the index must be shifted one place right (see the `for` loop in `insertAt`).
- **How does DynamicArray grow?** When `size == data.length`, `ensureCapacity` copies into an array twice as big, so appends are amortised O(1).
- **Why must binary search use sorted data?** It discards half of the range by comparing with the middle element; that only works if the order is known.
- **Why `low + (high - low) / 2`?** It avoids integer overflow of `low + high` for very large arrays.
- **Worst case for each search?** Linear: target is last or missing, n steps. Binary: about log2(n) steps (17 for 100,000).
- **What do "steps" mean here?** Number of comparisons performed.

## Halidha - Stack and Queue
- **LIFO vs FIFO?** Stack removes the most recently added item; queue removes the oldest.
- **What is stack overflow / underflow?** Push on a full stack / pop on an empty stack. Overflow returns false; underflow throws `EmptyStructureException`, which the menu catches.
- **Why a circular queue?** In a normal array queue, dequeued cells at the front are wasted or everything must be shifted. Using `% capacity` reuses cells so enqueue/dequeue stay O(1).
- **How is the rear index computed?** `(front + size - 1) % capacity`.
- **Real uses?** Stack: undo, bracket matching, function calls. Queue: printing, scheduling, BFS.
- **How does the bracket checker work?** Push each opener; each closer must match the popped opener; stack must be empty at the end.

## Hathiqu - Linked List, Validation, Results
- **Array vs linked list?** Array: O(1) access, O(n) insert/delete in the middle or front. Linked list: O(1) insert at head, O(n) access/search.
- **Why keep a tail pointer?** So insert at tail is O(1) instead of walking the whole list.
- **How does `reverse()` work?** Walk the list and flip each node's `next` to the previous node, then swap head and tail. O(n), O(1) extra memory.
- **How is deleting the tail handled?** `unlink` updates `tail` to the previous node.
- **Why validate input?** To stop crashes (`NumberFormatException`), invalid indexes and empty-structure errors; `InputValidator` re-prompts until the input is valid.
- **What does ResultLog do?** Stores every result so menu option 8 can display them all.

## Hasna - Graph, Performance, Integration
- **Why an adjacency list?** Memory O(V+E); traversal touches only real edges. A matrix needs O(V^2) memory.
- **BFS vs DFS?** BFS uses a queue and explores level by level (finds the fewest-edge path). DFS uses a stack and goes deep first. Both are O(V+E).
- **Why do BFS and DFS show the same step count on a full traversal?** Both visit every reachable vertex once and look at every adjacency entry once. They differ in visit order and frontier size; with a target they differ in steps.
- **Why is DFS iterative?** An explicit stack avoids a Java StackOverflowError on very deep graphs (100,000 vertices in the benchmark).
- **What is the shortest path method?** BFS with a parent map; the first time BFS reaches a vertex is by the fewest edges.
- **Why steps and time?** Steps are exact and repeatable and show Big-O growth; time depends on the machine/JVM, so each timing is the best of 3 runs.
- **How is everything integrated?** `Main` creates one shared array and graph and passes them to the menus (Array, Searching and Performance share the array; Graph and Performance share the graph).
