fun main() {
	fun part1(input: List<String>) {
		var lines = List(50) { '.' } // initialize the lines
		var grid = List(6) { lines.toMutableList() } // thank Ash for this heck ;-)
		grid.toMutableList()

		input.forEach { it -> // iterate the input
			var line = it.trim(); // remove potential spaces around the string
			val words = line.split(" ") // make a list of instructions per line
			if (words.first() == "rect") { // get the first operation
				var (rangeY, rangeX) = words[1].split("x") // destructing dimension
				for (y in 0 until rangeY.toInt() ) {
					for (x in 0 until rangeX.toInt() ) {
						grid[x][y] = '#' // add lit pixels
					}
				}
			} else if (words.first() == "rotate") {
				var (directinon, ln) = words[2].split("=") // destruct direction X or Y and line could be horizontal or vertical
				val line = ln.toInt() // parse into Int
				var by = words[4].toInt() // number of pixels we have to shift the line
				if (directinon == "x") { // column
					var column = List(6) { grid[it][line] } // save a column to change
					for (x in 0 until 6) {
						grid[x][line] = column[(x - by + 6) % 6] // iterate through the saved array and shift by the modulo
					}
				} else if (directinon == "y") { // row
					var row = List(50) { grid[line][it] }
					for (y in 0 until 50) {
					println((y - by + 50) % 50)
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
