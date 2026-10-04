# GitHub Workflow (collaboration evidence)

The lecturer may inspect the repository to verify **individual contributions**. Every member must make
their own commits from their own account. Do not upload everything in one commit and do not commit
someone else's work under your name.

Replace `<owner>` with the leader's GitHub username (`Hasna-2001`).

## 1. Leader: set up (Hasna)
1. Create the repository `CIT300-Data-Structure-Graph-Analyzer` on GitHub (empty, add no files yet).
2. Settings -> Collaborators: add Sheeraz, Halidha and Hathiqu.
3. Settings -> Branches: protect `main` and require a pull request before merging.
4. Create four Issues, e.g. "Array and Searching (Sheeraz)", "Stack and Queue (Halidha)",
   "Linked List and validation (Hathiqu)", "Graph, Performance and Integration (Hasna)".
5. Push the foundation:
```
git init
git branch -M main
git add .gitignore README.md run.sh run.bat docs/ src/util/ConsoleUI.java
git commit -m "Add project foundation: README, run scripts, docs and ConsoleUI"
git remote add origin https://github.com/<owner>/CIT300-Data-Structure-Graph-Analyzer.git
git push -u origin main
```
(Temporarily allow the first push to `main`, then enable protection.)

## 2. Every member: clone and create your branch
```
git clone https://github.com/<owner>/CIT300-Data-Structure-Graph-Analyzer.git
cd CIT300-Data-Structure-Graph-Analyzer
git checkout -b feature/<your-branch>
```
Branch names: `feature/array-searching`, `feature/stack-queue`, `feature/linked-list-validation`,
`feature/graph-integration`.

## 3. Commit your own files in small, meaningful steps
Copy only **your** files from the project folder (see `docs/TEAM_TASKS.md`) and commit them gradually, for example:

Sheeraz:
```
git add src/ds/DynamicArray.java
git commit -m "Add DynamicArray with insert, delete and auto-resize"
git add src/search/
git commit -m "Add linear and binary search with step counting"
git add src/menu/ArrayMenu.java src/menu/SearchMenu.java
git commit -m "Add Array and Searching menus with comparison"
git push -u origin feature/array-searching
```
Halidha:
```
git add src/ds/EmptyStructureException.java src/ds/ArrayStack.java
git commit -m "Add ArrayStack with overflow and underflow handling"
git add src/ds/CircularQueue.java
git commit -m "Add circular array queue"
git add src/menu/StackMenu.java src/menu/QueueMenu.java
git commit -m "Add Stack and Queue menus"
git push -u origin feature/stack-queue
```
Hathiqu:
```
git add src/ds/SinglyLinkedList.java
git commit -m "Add singly linked list with insert, delete, search and reverse"
git add src/util/InputValidator.java src/util/ResultLog.java
git commit -m "Add input validation and results log"
git add src/menu/LinkedListMenu.java
git commit -m "Add Linked List menu"
git push -u origin feature/linked-list-validation
```
Hasna:
```
git add src/graph/
git commit -m "Add Graph with adjacency list, BFS, DFS and shortest path"
git add src/menu/GraphMenu.java
git commit -m "Add Graph menu"
git add src/analysis/ src/menu/PerformanceMenu.java
git commit -m "Add performance analyzer and menu"
git add src/Main.java src/test/
git commit -m "Integrate all modules in Main and add self-tests"
git push -u origin feature/graph-integration
```
Tip: the best evidence is real development - commit while you build, test and fix things, not only
once at the end. If you improve or fix something, commit that too.

## 4. Pull requests
Open a pull request into `main` on GitHub for your branch (title e.g. "Add Array and Searching module",
link the issue with `Closes #1`). The leader reviews (leave at least one review comment) and merges.

Merge order (because of dependencies):
1. Foundation (leader) - `ConsoleUI`
2. `feature/array-searching` - provides `SearchResult`
3. `feature/stack-queue` - provides `EmptyStructureException`
4. `feature/linked-list-validation` - needs `SearchResult`, `ConsoleUI`
5. `feature/graph-integration` - needs everything (Main wires all menus)

After each merge everyone runs `git checkout main && git pull`.
Before merging the last PR, run `./run.sh` and `java -cp out test.TestRunner` on the combined code.

## 5. Final repository check
- README shows the correct names and IDs
- Every member appears in the commit history
- Branches and merged pull requests are visible
- `./run.sh` works from a fresh clone
