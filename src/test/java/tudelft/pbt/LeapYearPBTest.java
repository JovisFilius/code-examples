package assignments.pbt;

import assignments.domain.LeapYear;

import net.jqwik.api.*;
import net.jqwik.api.arbitraries.IntegerArbitrary;

import static org.assertj.core.api.Assertions.assertThat;

public class LeapYearPBTest {
        
        private final LeapYear ly = new LeapYear();

        // Centurial leapyear (divisible by 4 and 100)
        @Property
         void centurialLeapYearTest(@ForAll("centurialLeapYears") int year){
                 assertThat(ly.isLeapYear(year));
         }

        // Centurial non-leapyear (divisible by 100, not by 4)
        @Property
        void centurialNonLeapYearTest(@ForAll("centurialNonLeapYears") int year){
                assertThat(!ly.isLeapYear(year));
        }

        // Non-centurial leapyear (divisible by 4, not by 100)
        @Property
        void nonCenturialLeapYearTest(@ForAll("nonCenturialLeapYears") int year){
                assertThat(ly.isLeapYear(year));
        }

        // Non-centurial non-leapyear (not divisible by 4 or 100)
        @Property
        void nonCenturialNonLeapyearTest(@ForAll("nonCenturialNonLeapyears") int year){
                assertThat(!ly.isLeapYear(year));
        }

        @Provide
        Arbitrary<Integer> centurialLeapYears(){
                IntegerArbitrary year = Arbitraries.integers().greaterOrEqual(0);
                return year.filter(k -> k % 400 == 0);
                // return getArbitraryYear().filter(k -> k / 400 == 0);
        }

        @Provide
        Arbitrary<Integer> centurialNonLeapYears(){
                IntegerArbitrary year = Arbitraries.integers().greaterOrEqual(0);
                return year.filter(k -> k % 100 == 0 && k % 400 != 0);
        }

        @Provide
        Arbitrary<Integer> nonCenturialLeapYears(){
                IntegerArbitrary year = Arbitraries.integers().greaterOrEqual(0);
                return year.filter(k -> k % 4 == 0 && k % 100 != 0);
        }

        @Provide
        Arbitrary<Integer> nonCenturialNonLeapyears(){
                IntegerArbitrary year = Arbitraries.integers().greaterOrEqual(0);
                return year.filter(k -> k % 4 != 0 && k % 100 != 0);
        }
}
