The 2 design patterns I chose to implement were the Observer patterns and the Strategy pattern.
For the Observer pattern I created a class called appController which basically became my new main instead of importData, a Selection class, a SelectionListener interface, and a SelectoionPub which is the listeners.
For the Strategy pattern I created a MaxStrategy class, averageStrategy class, and a statStrategy interface. 
The Observer handles when my panels should update and the Strategy patterns handle how the stats are computed. By implementing these two I believe the program became easier to test and the code can be added onto without having to do much work on the existing code. 
