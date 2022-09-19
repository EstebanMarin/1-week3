package funsets

import scala.compiletime.ops.int

/** This class is a test suite for the methods in object FunSets.
  *
  * To run this test suite, start "sbt" then run the "test" command.
  */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /** When writing tests, one would often like to re-use certain values for
    * multiple tests. For instance, we would like to create an Int-set and have
    * multiple test about it.
    *
    * Instead of copy-pasting the code for creating the set into every test, we
    * can store it in the test class using a val:
    *
    * val s1 = singletonSet(1)
    *
    * However, what happens if the method "singletonSet" has a bug and crashes?
    * Then the test methods are not even executed, because creating an instance
    * of the test class fails!
    *
    * Therefore, we put the shared values into a separate trait (traits are like
    * abstract classes), and create an instance inside each test method.
    */

  trait TestSets:
    val s1: FunSet = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val emptySet: FunSet = x => false
    val a: FunSet = x => x >= 3 && x <= 7
    val b: FunSet = x => x >= 5 && x <= 7
    val c: FunSet = x => x >= 8 && x <= 10

  test("union contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  test("union contains all elements of each set plus empty") {
    new TestSets:
      val s = union(s1, s2)
      val sTest = union(s2, emptySet)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  test("testing union empty set") {
    new TestSets:
      val s = union(s1, emptySet)
      assert(contains(s, 1), "Union 1")
      assert(!contains(s, 2), "Union 3")
  }

  test("testing union empty set empty set") {
    new TestSets:
      val s = union(emptySet, emptySet)
      assert(!contains(s, 1), "Union 1")
      assert(!contains(s, 2), "Union 3")
  }

  test("interset contains all elements of each set") {
    new TestSets:
      val s = intersect(a, b)
      assert(!contains(s, 3), "Union 1")
      assert(contains(s, 6), "Union 2")
  }

  test("diff") {
    new TestSets:
      val s = diff(a, b)
      assert(contains(s, 3), "Union 1")
      assert(!contains(s, 6), "Union 2")
  }
  
  test("filter") {
    new TestSets:
      val s = filter(b, a)
      assert(!contains(s, 3), "Union 1")
      assert(contains(s, 6), "Union 2")
  }
  
  test("forall => true case") {
    new TestSets:
      val s = forall(b, a)
      assert(s == true, "True Case")
  }

  test("forall => false case") {
    new TestSets:
      val s = forall(a, c)
      assert(s == false, "False Case")
  }

  test("forall => false case") {
    new TestSets:
      val s = forall(a, c)
      assert(s == false, "False Case")
  }

  test("exists => true case") {
    new TestSets:
      val s = exists(a, b)
      assert(s == true, "False Case")
  }

  test("map empty Set") {
    new TestSets:
    // val s1: FunSet = singletonSet(1)
    // val s2 = singletonSet(2)
    // val s3 = singletonSet(3)
    // val a: FunSet = x => x >= 3 && x <= 7
    // val b: FunSet = x => x >= 5 && x <= 7
    // val c: FunSet = x => x >= 8 && x <= 10
      val s = map(emptySet, x => x + 1)
      assert(contains(s, 2) == false, "True Case")
  }



  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
