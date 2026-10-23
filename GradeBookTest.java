import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for GradeBook.
 * Tests addScore, toString, getScoreSize, sum, minimum, and finalScore.
 */
public class GradeBookTest {

    // Test fixtures: shared objects used in multiple tests
    private GradeBook g1;
    private GradeBook g2;

    /**
     * setUp runs BEFORE each @Test method.
     * Creates two fresh GradeBook objects (capacity 5) with scores.
     */
    @BeforeEach
    public void setUp() {
        // g1 holds two scores
        g1 = new GradeBook(5);
        g1.addScore(50.0);
        g1.addScore(75.0);

        // g2 holds three scores
        g2 = new GradeBook(5);
        g2.addScore(90.0);
        g2.addScore(85.0);
        g2.addScore(70.0);
    }

    /**
     * tearDown runs AFTER each @Test method.
     * Sets the fixtures to null so each test starts clean.
     */
    @AfterEach
    public void tearDown() {
        g1 = null;
        g2 = null;
    }

    /**
     * Test addScore and toString.
     * Verifies the scores are stored in the order added and that
     * toString lists each score followed by a space.
     */
    @Test
    public void testAddScoreAndToString() {
        // toString should show the scores in the order they were added
        assertEquals("50.0 75.0 ", g1.toString());
        assertEquals("90.0 85.0 70.0 ", g2.toString());

        // addScore returns true while there is room, and the new score is appended
        assertTrue(g1.addScore(100.0));
        assertEquals("50.0 75.0 100.0 ", g1.toString());

        // fill g2 to capacity (5), then addScore should return false
        assertTrue(g2.addScore(60.0));
        assertTrue(g2.addScore(95.0));
        assertFalse(g2.addScore(80.0));
        assertEquals("90.0 85.0 70.0 60.0 95.0 ", g2.toString());

        // an empty GradeBook returns an empty string
        GradeBook empty = new GradeBook(5);
        assertEquals("", empty.toString());
    }

    /**
     * Test getScoreSize.
     * Verifies getScoreSize returns the number of scores actually added.
     */
    @Test
    public void testGetScoreSize() {
        // sizes match the number of scores added in setUp
        assertEquals(2, g1.getScoreSize());
        assertEquals(3, g2.getScoreSize());

        // size increases by one after adding a score
        g1.addScore(100.0);
        assertEquals(3, g1.getScoreSize());

        // size does not change when the GradeBook is full
        g2.addScore(60.0);
        g2.addScore(95.0);
        g2.addScore(80.0); // rejected, capacity is 5
        assertEquals(5, g2.getScoreSize());

        // a new GradeBook has no scores
        GradeBook empty = new GradeBook(5);
        assertEquals(0, empty.getScoreSize());
    }

    /**
     * Test sum.
     * Verifies sum() returns the correct total of all scores.
     */
    @Test
    public void testSum() {
        // 50 + 75 = 125
        assertEquals(125.0, g1.sum(), 0.0001);
        // 90 + 85 + 70 = 245
        assertEquals(245.0, g2.sum(), 0.0001);

        // an empty GradeBook sums to 0
        GradeBook empty = new GradeBook(5);
        assertEquals(0.0, empty.sum(), 0.0001);
    }

    /**
     * Test minimum.
     * Verifies minimum() returns the smallest score, and 0 when empty.
     */
    @Test
    public void testMinimum() {
        // smallest score in each GradeBook
        assertEquals(50.0, g1.minimum(), 0.0001);
        assertEquals(70.0, g2.minimum(), 0.0001);

        // edge case: an empty GradeBook returns 0
        GradeBook empty = new GradeBook(5);
        assertEquals(0.0, empty.minimum(), 0.0001);
    }

    /**
     * Test finalScore.
     * Verifies the lowest score is dropped when there are 2+ scores,
     * a single score is returned as-is, and an empty GradeBook returns 0.
     */
    @Test
    public void testFinalScore() {
        // 2+ scores: drop the lowest (125 - 50 = 75, 245 - 70 = 175)
        assertEquals(75.0, g1.finalScore(), 0.0001);
        assertEquals(175.0, g2.finalScore(), 0.0001);

        // edge case: no scores returns 0
        GradeBook empty = new GradeBook(5);
        assertEquals(0.0, empty.finalScore(), 0.0001);

        // edge case: exactly one score returns that score
        GradeBook single = new GradeBook(5);
        single.addScore(100.0);
        assertEquals(100.0, single.finalScore(), 0.0001);
    }
}
