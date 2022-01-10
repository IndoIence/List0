import java.io._




object Task2
{




  def main(args: Array[String]): Unit = {
    val books = List[(String, String)](
      ("Lorem Ipsum", "src/main/scala/Books/testFile.txt")
    )
    val loader = new LoadBooks()
    val Lorem = loader.load(books)

    loader.printMostFrequent(4, "Lorem Ipsum")

    menu




    def loadString(): Unit ={
      println("Input title: ")
      val userName    = scala.io.StdIn.readLine()
      println("Input text")
      val userString  = scala.io.StdIn.readLine()
      loader.loadString(userName,userString)
    }
    def loadFromFile: Unit = {
      println("Input file title: ")
      val userName    = scala.io.StdIn.readLine()
      println("Input file location")
      val userString    = scala.io.StdIn.readLine()
      loader.load(List((userName, userString)))
    }

    def printToFile: Unit = {
      println("Show how many words: ")
      val numberOfWords = scala.io.StdIn.readInt()
      println("Input text name: ")
      val fileName   = scala.io.StdIn.readLine()
      val myFile =  new File("src/main/scala/Wordcloud.csv")
      val myFilePrinter = new PrintWriter(myFile)
      println(loader.printMostFrequent(numberOfWords, fileName).toString())
      // this took me way too long to find how to do
      loader.printMostFrequent(numberOfWords, fileName).map({ case (key, values) => key + " " + values.toString() + '\n'}).foreach(myFilePrinter.write)

      myFilePrinter.close()
    }



    def menu: Unit = {
      println("1 - Input a string: ")
      println("2 - Input from a file: ")
      println("3 - Print out most frequent words: ")
      println("4 - Print out most frequent words to file: ")
      println("Other - Close")
      val userInput   = scala.io.StdIn.readInt()
      userInput.match{
        case 1 => loadString()
        case 2 => loadFromFile
        case 3 => {
          println("Show how many words: ")
          val numberOfWords = scala.io.StdIn.readInt()
          println("Name of the file: ")
          val fileName = scala.io.StdIn.readLine()
          val words = loader.printMostFrequent(numberOfWords, fileName)
          println(words)
        }
        case 4 => printToFile
        case _ => return

      }
      menu



    }


  }



}
