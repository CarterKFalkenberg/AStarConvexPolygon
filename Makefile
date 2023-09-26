JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = Vector2D.java \
					Polygon.java \
					Environment.java \
					Search.java \
					GreedySearch.java \
					UniformCostSearch.java \
					AStarSearch.java \
					Main.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	$(RM) astar.js uniformcost.js greedy.js
	
run:
	java Main
