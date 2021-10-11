package org.swdc.swt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ApplicationIOUtil {

    public static String readStreamAsString(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
