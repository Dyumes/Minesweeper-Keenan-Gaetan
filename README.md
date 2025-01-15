## Table of content
* Description
* How to play
* Hellsweeper mode
* Screenshots
* Code

---

## Description
<p align="center">
  <img src="https://files.catbox.moe/im4hbd.png" alt="Title ISC" style="max-width:100%;">
</p>

**Minesweeper - ISC Edition** is a new take on the classic game : **Minesweeper**. **Minesweeper - ISC Edition** is a single-player logic-based computer game played on a rectangular board. The goal is to locate a predetermined number of randomly-placed mines by clicking on safe squares while avoiding the squares with mines. The player must use their wits and deduction to find the squares with mines and signal them using flags. **Minesweeper - ISC Edition** takes it's visual inspiration from the ISC branch of the HEI of Sion (~~which is by far the best branch~~).

---

## Hellsweeper
***"Attention Helldivers! The enemies of Managed Democracy have planted mines inside of our territory. It is your duty to bring Freedom and Peace once more inside of our soil. For Super Earth!"***

<p align="center">
<img src="https://github.com/user-attachments/assets/603ff396-9b47-43c6-a7a2-2ca1f7697ff6" alt="helldiver salute" />
</p>

**Hellsweeper** is an alternative mode available for **Minesweeper - ISC Edition**. Bringing a brand new visual style, **Hellsweeper** puts you in the boots of an elite Helldiver tasked with the perilous mission of clearing minefields to secure humanity's survival in a hostile galaxy. **Hellsweeper** will take you on a democratic and perilous adventure as you locate and secure the mines hidden by Super Earth's enemies. But fear not! Your brave commander is here to bring you valuable moral support during your fight! Good luck, and may the light of Liberty guide you.

<p align="center">
<img src="https://github.com/user-attachments/assets/fbbfe9d9-e442-4016-86cf-23c974b719a1" alt="helldiver salute" />
</p>

---

## Screenshots

---

## How to play

Once you have selected one of the three difficulty available, the game will generate a board with hidden squares. Your goal is to find and flag all the hidden mines without clicking on one. **`Left-click`** on a square reveals either a number or a blank space. Numbers tell you how many mines are in the eight squares surrounding the one you just clicked. To mark a mine, **`Right-click`** to place a flag. Use the numbers to figure out where the mines are and clear all the safe squares. If you clear every safe square and flag all the mines correctly, you win! Avoid guessing and use logic to solve the puzzle.

If you find yourself stuck or wishing to change the current setting, you can press **`R`** on your keyboard to generate a new game! Pressing **`M`** will return you to the menu, while pressing **`Escape`** will close the game.

---

## Code

### 1. Initialization and configuration

* We use global variables troughout the code
  * Defines the grid size (`rows`, `cols`), cell size (`sizeCell`), and offsets (`offsetX`, `offsetY`) to center the grid on the screen
  * Initializes a `FunGraphics` instance for graphical display
  * Sets up game state variables such as `nbrFlag` (number of flags), `isMenuOpen`, and `isFirstClick`
* The `Cell` class represents each grid cell using the following :
  * `isMine` : whether the cell contains a mine
  * `isRevealed` : whether the cell has been revealed
  * `nbrMine` : the number of adjacent mines
  * `isFlagged` : whether a flag is placed on the cell
* We use the method `reveal` to reveal a cell
* We use the method `toggleFlag` to toggle a flag on and off

### 2. Grid management

* Grid initalization
  * The grid is a 2D array of `Cell` objects, initialized with default values
* Mine placement (`spawnMines`):
  * Randomly places a specified number of mines on the grid, avoiding already occupied or revealed cells
* Counting Adjacent Mines (`countMines`):
  * Calculates and updates the number of adjacent mines for each cell that is not a mine

### 3. User interaction

* Revealing Cells (`revealAdjacent`):
  * Reveals a cell and, if it has no adjacent mines, recursively reveals its neighboring cells
* Clearing Mines Around the First Click (`clearFirstClickArea`):
  * Removes mines around the first clicked cell to avoid an immediate loss
* Click Handling:
  * Manages left-clicks (reveal cells) and right-clicks (place or remove flags)
  * Includes win and loss condition checks
* Keyboard Input Handling:
  * Allows access to the menu, restarting the game, or exiting the application

### 4. Rendering

* Drawing the Grid (`drawGrid`):
  * Draws the grid and cells, using different colors to represent states (revealed, unrevealed, flagged, etc.)
  * Displays mines, flags, and numbers indicating adjacent mines
* Displaying Win/Loss Messages:
  * Shows a central message ("WIN" or "LOST") with temporary visual effects

### 5. Game loop

* Menu and Starting a New Game (`startNewGame`):
  * Resets the grid and associated variables for a fresh game
* Main Loop:
  * Continuously renders the graphical output while the menu is closed
