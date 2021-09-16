package org.swdc.dsl;

import org.swdc.swt.beans.ObservableValue;


public class RxObservableTest {

    public static void main(String[] args) {

        ObservableValue<String> s1 = new ObservableValue<>();
        ObservableValue<String> s2 = new ObservableValue<>();
        ObservableValue<String> s3 = new ObservableValue<>();

        s1.bind(s2);
        s3.bindBidirect(s2);

        s2.set("Test");
        System.err.println(s3.get());
        s3.set("Test Data");
        System.err.println(s1.get());


    }

}
