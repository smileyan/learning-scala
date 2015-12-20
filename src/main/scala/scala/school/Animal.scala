package scala.school
      
class Animal { val sound = "rustle" }

class Bird extends Animal { override val sound = "call" }

class Chicken extends Bird { override val sound = "cluck"}
