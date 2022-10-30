fun main() {
	fun part1(input: List<String>) {
		val lines = List(50) { '.' } // initialize the lines
		val grid = List(6) { lines.toMutableList() }.toMutableList() // thank Ash for this heck ;-)

		input.forEach { it -> // iterate the input
			val line = it.trim() // remove potential spaces around the string
			val words = line.split(" ") // make a list of instructions per line
			if (words.first() == "rect") { // get the first operation
				val (rangeY, rangeX) = words[1].split("x") // destructing dimension
				for (y in 0 until rangeY.toInt() ) {
					for (x in 0 until rangeX.toInt() ) {
						grid[x][y] = '#' // add lit pixels
					}
				}
			} else if (words.first() == "rotate") {
				val (direction, ln) = words[2].split("=") // destruct direction X or Y and line could be horizontal or vertical
				val line = ln.toInt() // parse into Int
				val by = words.last().toInt() // number of pixels we have to shift the line
				if (direction == "x") { // column
					val column = List(6) { grid[it][line] } // save a column to change
					for (x in 0 until 6) {
						grid[x][line] = column[(x - by + 6) % 6] // iterate through the saved array and shift by the modulo
					}
				} else if (direction == "y") { // row
					val row = List(50) { grid[line][it] }
					for (y in 0 until 50) {
						grid[line][y] = row[(y - by + 50) % 50]
					}
				}

			}
		}
		var litPixels = 0
		grid.forEach { line -> line.forEach { it -> if (it == '#') litPixels += 1 } }
		grid.forEach { line -> line.forEach { it -> print(it)}; println("") }
		println("Number of lit pixels $litPixels")
	}

	val input = readInput("Day08")
	part1(input)
}
