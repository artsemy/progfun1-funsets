package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */
  test("singleton set one contains one") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets:
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
      assert(!contains(s1, 2), "Singleton")
  }

  test("union contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  test("intersect") {
    new TestSets:
      val s = intersect(s1, s2)
      val p1 = intersect(s2, s2)
      assert(!contains(s, 1), "intersect 1")
      assert(!contains(s, 2), "intersect 2")
      assert(!contains(s, 3), "intersect 3")
      assert(contains(p1, 2), "intersect 4")
  }

  test("diff") {
    new TestSets:
      val s = diff(s1, s2)
      assert(contains(s, 1), "diff 1")
      assert(!contains(s, 2), "diff 2")
      assert(!contains(s, 3), "diff 3")
  }

  test("filter") {
    new TestSets:
      val s = union(union(s1, s2), s3)
      assert(contains(filter(s, s1), 1), "filter 1")
      assert(!contains(filter(s, s1), 2), "filter 2")
  }

  test("forall") {
    new TestSets:
      val s = union(union(s1, s2), s3)
      assert(forall(s1, s1), "forall 1")
      assert(!forall(s, s1), "forall 2")
  }

  test("exists") {
    new TestSets:
      val s = union(s1, s2)
      assert(exists(s, s1), "exists 1")
      assert(!exists(s, s3), "exists 2")
  }

  test("map") {
    new TestSets:
      val s = union(s1, s2)
      val m = map(s, x => x + 1)
      assert(!contains(m, 1) , "map 1")
      assert(contains(m, 2) , "map 2")
      assert(contains(m, 3) , "map 3")
      assert(!contains(m, 4) , "map 4")
  }



  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
