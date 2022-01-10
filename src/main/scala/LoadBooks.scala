import scala.collection.immutable.ListMap
import scala.collection.mutable.ListBuffer
import scala.io.Source

class LoadBooks(){
  val stopwords = Source.fromFile("src/main/scala/Books/stop_words_english.txt", "utf-8").getLines().toList
  val booksList = ListBuffer[(String, Map[String, Int], List[String])]()

  def load(books: List[(String,String)]): List[(String, Map[String, Int], List[String])] = {



    books.foreach(book => {
      val words = loadText(book._2)
      //val counts = words.foldLeft(Map.empty[String, Int]){ (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))      }
      val unsortedCount = words.groupMapReduce(identity)(_ => 1)(_+_)
      val counts = ListMap(unsortedCount.toSeq.sortWith(_._2 > _._2):_*)
      val result = (book._1, counts, words.toList)
      booksList += result
    })
    booksList.toList
  }
  def loadText(filename: String)={
    val text = Source.fromFile(filename).getLines().mkString(" ")
    val textNoStop = cleanText(text)
    textNoStop
  }

  def cleanText(t: String) = {
    t.toLowerCase()
      .replaceAll("""[\p{Punct}&&[^.]]""","")
      .split("\\W+")
      .filter(!stopwords.contains(_))
      .toList
  }
  def loadString(title: String, text: String) = {

    val words = cleanText(text)
    val unsortedCount = words.groupMapReduce(identity)(_ => 1)(_+_)
    val counts = ListMap(unsortedCount.toSeq.sortWith(_._2 > _._2):_*)
    val result = (title, counts, words.toList)
    booksList += result
  }

  def printMostFrequent(howMany: Int, title: String): Map[String,Int] = {
    val listOfNames  = booksList.map(_._1)
    if (listOfNames.contains(title)){
      val bookInd = listOfNames.indexOf(title)
      val book = booksList(bookInd)
      println(book._2.take(howMany))
       return book._2.take(howMany)
    }else{
      println("No book with this name")
      Map()
    }

  }

}

