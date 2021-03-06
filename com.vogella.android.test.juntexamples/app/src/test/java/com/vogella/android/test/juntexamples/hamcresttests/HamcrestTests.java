package com.vogella.android.test.juntexamples.hamcresttests;

import com.vogella.android.test.juntexamples.DataService;
import com.vogella.android.test.juntexamples.model.TolkienCharacter;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.vogella.android.test.juntexamples.model.Race.HOBBIT;
import static com.vogella.android.test.juntexamples.model.Race.ORC;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.not;


/**
 * Created by vogella on 19.06.16.
 */

public class HamcrestTests {
    @Test
    public void firstHamcrestMatcherForLists( ) {
        List<Integer> list = Arrays.asList(5, 2, 4);
        // test has list has size of 3
        // contains the elements 2, 4, 5 a any order
        // That every item is greater than 1
        // ------------ START EDITING HERE ----------------------
        assertThat(list, hasSize(3));
        assertThat(list, containsInAnyOrder(2, 4,5));
        assertThat(list,everyItem(greaterThan(1)));
        // ------------ STOP EDITING HERE  ----------------------
    }
    @Test
    public void usingEitherOrAndIsOneOr( ) {
        // ------------ START EDITING HERE ----------------------
        int result = 2;
        assertThat(result, either(is(1)).or(is(2)));
        assertThat(result, isOneOf(1, 2, 3));
        // ------------ STOP EDITING HERE  ----------------------
    }


    @Test
    public void validateTolkeinCharactorsInitializationWorks() {
        TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
        //age is 33
        //name is "Frodo"
        //name is not "Frodon"
        assertThat(frodo.getName(),is("Frodo"));
        assertThat(frodo.age,is(33));
        assertThat(frodo.getName(), is(not("Frodon")));
    }

    @Test
    public void ensureThatFellowsAreNotOrcs() {
        List<TolkienCharacter> fellowship = new DataService().getFellowship();
        // ensure that not any of the fellows is a orc

        // first option
        for (TolkienCharacter t: fellowship) {
            assertThat(t.getRace(), not(is(ORC)));
        }
        // second option
        Matcher<TolkienCharacter> isNotOrc = Matchers.<TolkienCharacter>hasProperty("race", is(not(ORC)));
        assertThat(fellowship, everyItem(isNotOrc));
    }

    @Test
    public void fellowShipOfTheRingShouldContainer7() {
        assertThat("Gandalf", length(is(7)));
    }

    public static  Matcher<String> length(Matcher<? super Integer> matcher) {
        return new FeatureMatcher<String, Integer>(matcher, "a String of length that", "length") {
            @Override
            protected Integer featureValueOf(String actual) {
                return actual.length();
            }
        };
    }

}
