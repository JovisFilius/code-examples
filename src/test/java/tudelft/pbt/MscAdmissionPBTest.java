package tudelft.pbt;

import tudelft.domain.MScAdmission;

import net.jqwik.api.*;
import net.jqwik.api.constraints.Negative;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.DoubleRange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MscAdmissionPBTest {
        
        private final MScAdmission admission = new MScAdmission();

        @Property
        void actNegativeTest(@ForAll @Negative int act){
                assertThatThrownBy(() -> {
                        admission.admit(act, 0);
                })
                .isInstanceOf(AssertionError.class);
        }

        @Property
        void actTooLargeTest(@ForAll @IntRange(min = 37) int act){
                assertThatThrownBy(() -> {
                        admission.admit(act, 0);
                })
                .isInstanceOf(AssertionError.class);
        }

        @Property
        void gpaNegativeTest(@ForAll @Negative double gpa){
                assertThatThrownBy(() -> {
                        admission.admit(0, gpa);
                })
                .isInstanceOf(AssertionError.class);
        }

        @Property
        void gpaTooLargeTest(@ForAll @DoubleRange(min = 4.0, minIncluded = false) double gpa){
                assertThatThrownBy(() -> {
                        admission.admit(0, gpa);
                })
                .isInstanceOf(AssertionError.class);                
        }

        @Property
        void actAndGPAInsufficientTest(@ForAll("insufficientResults") AdmissionTestInput input){
                assertThat(!admission.admit(input.act, input.gpa));
        }
        @Provide
        Arbitrary<AdmissionTestInput> insufficientResults(){
                Arbitrary<Integer> act = Arbitraries.integers().greaterOrEqual(0)
                        .filter(k -> k <= 36);
                Arbitrary<Double> gpa = Arbitraries.doubles().greaterOrEqual(0.0)
                        .filter(k -> k <= 4.0);
                
                return Combinators.combine(act, gpa).as(AdmissionTestInput::new)
                        .filter(k -> (k.act / 10.0 + k.gpa) < 7.1);
        }

        @Property
        void actAndGPASufficientTest(@ForAll("sufficientResults") AdmissionTestInput input){
                assertThat(admission.admit(input.act, input.gpa));
        }
        @Provide
        Arbitrary<AdmissionTestInput> sufficientResults(){
                Arbitrary<Integer> act = Arbitraries.integers().greaterOrEqual(31)
                        .filter(k -> k <= 36);
                Arbitrary<Double> gpa = Arbitraries.doubles().greaterOrEqual(3.5)
                        .filter(k -> k <= 4.0);

                return Combinators.combine(act, gpa).as(AdmissionTestInput::new)
                        .filter(k -> (k.act / 10.0 + k.gpa) >= 7.1);
        }

        static class AdmissionTestInput{

                int act;
                double gpa;

                public AdmissionTestInput(int act, double gpa){
                        this.act = act;
                        this.gpa = gpa;
                }
        }
}
