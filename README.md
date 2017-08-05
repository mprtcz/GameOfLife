# GameOfLife
An implementation of the Game of Life (https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)

For some reason, when the game is run with parallel stream, it is not behaving in the predictable manner.
The returned new states maps are not equal to the maps that are returned when created with simple foreach loop,
as it is shown in Game tests. 
