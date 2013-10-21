package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {
  import FunSets._

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

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "SingletonSet(1) was expected to contain 1")
      assert(!contains(s1, 2), "SingletonSet(1) was expected to not contain 2")
      assert(contains(s2, 2), "SingletonSet(2) was expected to contain 2")
      assert(!contains(s2, 3), "SingletonSet(2) was expected to not contain 3")
      assert(contains(s3, 3), "SingletonSet(3) was expected to contain 3")
      assert(!contains(s3, 1), "SingletonSet(3) was expected to not contain 1")
      
    }
  }

  test("union") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union of s1 and s2 was expected to contain 1")
      assert(contains(s, 2), "Union of s1 and s2 was expected to contain 2")
      assert(!contains(s, 3), "Union of s1 and s2 was not expected to contain 3")
    }
  }
  
  test("intersect") {
    new TestSets {
      val s = intersect(s1, s2)
      assert(!contains(s, 1), "Intersect of s1 and s2 was not expected to contain 1")
      assert(!contains(s, 2), "Intersect of s1 and s2 was not expected to contain 2")
      assert(!contains(s, 3), "Intersect of s1 and s2 was not expected to contain 3")
    }
  }
  
  test("diff") {
    new TestSets {
      val s = union(s1, s2)
      val setDiff = diff(s, s2)
      assert(contains(setDiff, 1), "{s1, s2} - {s2} was expected to contain 1")
      assert(!contains(setDiff, 2), "{s1, s2} - {s2} was not expected to contain 2")
      assert(!contains(setDiff, 3), "{s1, s2} - {s2} was not expected to contain 3")
    }
  }
  
  test("filter") {
    new TestSets {
      val s = union(s1, s2)
      val filterSet = filter(s, x => x == 2)
      assert(!contains(filterSet, 1), "{s1, s2} filtered by {x == 2} was expected to not contain 1")
      assert(contains(filterSet, 2), "{s1, s2} filtered by {x == 2} was expected to contain 2")
      assert(!contains(filterSet, 3), "{s1, s2} filtered by {x == 2} was expected to not contain 3")
    }
  }
  
  test("forall") {
    new TestSets {
      val s = union(s1, s2)
      assert(forall(s, x => true), "predicate {Inf.} was expected to hold for {s1, s2}")
      assert(forall(s, x => x == 1 || x == 2), "predicate {1, 2} was expected to hold for {s1, s2}")
      assert(!forall(s, x => x == 1), "predicate {that excepts the value 1} was not expected to hold for {s1, s2}")
    }
  }
  
  test("exists") {
    new TestSets {
      val s = union(s1, s2)
      assert(exists(s, x => true), "predicate {Inf.} was expected to hold for {s1, s2}")
      assert(exists(s, x => x == 3 || x == 2), "predicate {3, 2} was expected to hold for {s1, s2}")
      assert(!exists(s, x => x == 5), "predicate {that excepts the value 5} was not expected to hold for {s1, s2}")
    }
  }
  
  test("map") {
    new TestSets {
      val s = union(s1, s2)
      val mappedSet = map(s, x => 3 * x)
      assert(contains(mappedSet, 3), "predicate {s1, s2} transformed by 3 * x was expected to contain 3")
      assert(contains(mappedSet, 6), "predicate {s1, s2} transformed by 3 * x was expected to contain 6")
      assert(!contains(mappedSet, 9), "predicate {s1, s2} transformed by 3 * x was expected to contain 9")
    }
  }
}
