# bouceka-aoc-1

[Advent of Code 2016 - Day 8: Two-Factor Authentication]:https://adventofcode.com/2016/day/8

## Overview of problem

My second AOC task is the [day 8]:[https://adventofcode.com/2016/day/8] from 2016. I try to display a specific
word on a 50x6px display.
The display shows two types of characters `#` and `.`. I try to move the `#` in the way it displays a word.
Like so:

```
.#..#.#
#.#....
.#.....
```

At the front desk is cheatsheet how to find out the password.
> - `rect AxB` turns on all of the pixels in a rectangle at the top-left of the screen which is A wide and B tall.
> - `rotate row y=A by B` shifts all of the pixels in row A (0 is the top row) right by B pixels. Pixels that would fall
	off the right end appear at the left end of the row.
> - `rotate column x=A by B` shifts all of the pixels in column A (0 is the left column) down by B pixels. Pixels that
	would fall off the bottom appear at the top of the column.

The document might look like this.

```
rect 9x1
rotate column x=7 by 1
rotate row y=1 by 3
```

The instructions tell us to shift the symbols in a way it creates a word.

## Solution

The solution for the first and second part is very similar, so I combined them into one section.

Firstly, I created the needed grid based on the provided dimensions of 50x6. Every cell contains `.`. I used the idiomatic approach to initialize a
nested list.

```kotlin
val lines = List(50) { '.' }
val grid = List(6) { lines }
```

Secondly, I iterate through the instruction text file line by line. I prepare the instruction check if there are no extra
spaces that might mess up the split function.

```kotlin
val line = it.trim()
val words = line.split(" ")
```

Then I take the first word from the instruction array and decide if the program will to turn on the light in
provided dimensions or shift the symbols. I used Kotlin's native feature `.first()`, which takes the first element of the
list.

The first instruction with adding `#` was pretty simple. I only needed to iterate the nested array by provided
dimensions.
However, I tried to use another idiomatic syntax like `var (rangeY, rangeX) = words[1].split("x")` like destructing a
list, and `for (y in 0 until rangeY.toInt() )` looping in a specific range. I am not sure whether this is the most
elegant solution.

```kotlin
if (words.first() == "rect") {
	val (rangeY, rangeX) = words[1].split("x")  // destructing dimension
	for (y in 0 until rangeY.toInt()) {
		for (x in 0 until rangeX.toInt()) {
			grid[x][y] = '#'
		}
	}
}
```

The second part was significantly more challenging. It started similarly by destructing the direction of shifting and the
number of the rows/columns. Again, the first part used `.last()` for destructing the last instruction of the line
for variable `by`.
The most crucial part was to figure out how to shift in a particular direction. For this part, I felt a bit hopeless
and had to google how to shift the whole array and persist its length. Looking at it now, I see it was pretty simple. We
store the column or row I want to shift and loop its length. I re-assign the lists' values with the old list by
taking its index, deducting how many points I want to shift it, adding the length and module the
length   `grid[x][line] = previous[(x - by + 6) % 6]`. It shifts `[#,#,.,.,.,.,.,.]` by 4 to `[.,.,.,.,#,#,.,.]`. In
this way, we shift the whole `#` in a way we need.

Lastly, we need to know how many pixels are lit. We simply iterate through the grid and check which cells have the value `#`
.

```kotlin
var litPixels = 0
grid.forEach { line -> line.forEach { it -> if (it == '#') litPixels += 1 } }
```

## Reflection

The project took me two afternoons (~9 hours). Even though the code is shorter than the previous Advent of Code, I have
to admit it was significantly more demanding. I tried to apply the idiomatic approach from the start, which would make
my refactoring easier.

There are some parts I was not sure if I used Kotlin correctly, like on line
4 `val grid = List(6) { lines.toMutableList() }.toMutableList()`. When I tried to make `lines` mutable on the same line
with initialization, it didn't work then. Despite this little issue it still made my code easier than looping the array
by adding the `#`.
Now, I understand that this kind of code challenge tests my knowledge of strings. Without operations like `.split()`
, `.trim()`, `.toInt()`, it would be tough to solve these tasks.


---

Welcome to the Advent of Code[^aoc] Kotlin project created by [bouceka][github] using
the [Advent of Code Kotlin Template][template] delivered by JetBrains.

In this repository, bouceka is about to provide solutions for the puzzles using [Kotlin][kotlin] language.

If you're stuck with Kotlin-specific questions or anything related to this template, check out the following resources:

- [Kotlin docs][docs]
- [Kotlin Slack][slack]
- Template [issue tracker][issues]

[^aoc]:
[Advent of Code][aoc] – An annual event of Christmas-oriented programming challenges started December 2015.
Every year since then, beginning on the first day of December, a programming puzzle is published every day for
twenty-five days.
You can solve the puzzle and provide an answer using the language of your choice.

[aoc]: https://adventofcode.com

[docs]: https://kotlinlang.org/docs/home.html

[github]: https://github.com/bouceka

[issues]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template/issues

[kotlin]: https://kotlinlang.org

[slack]: https://surveys.jetbrains.com/s3/kotlin-slack-sign-up

[template]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template
