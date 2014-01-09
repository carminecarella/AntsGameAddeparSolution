AntsGameAddeparSolution
=======================

Ants Game Addepar code challenge - my solution

The code challenge description can be found on https://addepar.com/challenge/

Solution Description

An ant doesn’t know anything about surrounding environment, in order to move in it the ant has to discover “the world” and build its environment representation.

Board modeling

To reach this goal I modeled the environment in MyAnt class as an object of Board class.
The board can be represented as a collection of Square with several attributes:

•	Coordinates x and y

•	Amount of food

•	Parent square, that is the square in which the ant was located before moving in this square

Behavior/Role Ant modeling

According to the project’s requirements, when an ant has collected food, it has to go to the anthill to drop it off, otherwise it can walk on the board finding out for the food. To model this behavior I have used the Enum Role with 2 value as FOOD_BEARER and SCOUT.

Ant activities

The ant has to do 3 things:

•	Explore the surrounding environment

•	Move towards next square (Path-finding algorithm:  the A* algorithm)

•	Communicate with other ants

Each point is described below

Explore the surrounding environment

The board is made up of:

•	Anthill square: the starting square for the ant

•	Current square

•	Two sets of unvisited square: they are food or plain square that the ant hasn’t visited yet. This set is a subset of the below map

•	A map of discovered squares, that it has seen (explore) walking on the board. It contains both visited squares and not visited ones.

A map has a pair of K,V where 
K is the key as the string contains coordinates x and y: x=-1 y=5 -> key is “-15”   
V is the value as Square object related to the key/coordinates

Each square has coordinates x and y calculated from the anthill square, example :
The direction NORTH, EAST, SOUTH, WEST is calculated by the relative position from the current square, example: if the ant is on the square c with coordinates x = 0 and y = 2, the surrounding squares of c will be

•	NORTH:  x, y+1 
•	EAST: x+1, y
•	SOUTH: x, y-1
•	WEST: x-1, y

Move towards next square

•	Ant Role: FOOD BEARER. It has to go to the anthill to drop the food off. After dropping it off, the ant can start scouting the food again. The path towards anthill is created with the A* algorithm.

•	Ant Role: SCOUT. The ant hasn't collected food. It walks on board following a path until it reaches a food square, gathers food and changes its role becoming FOOD BEARER.

•	Using the unvisited square sets, the algorithm selects the closest unvisited square giving priority to food square.

Communicate with other ants

Object serialization: the BoardSerializable class contains all the squares discovered for an ant to send (and receive) thorough serialization
