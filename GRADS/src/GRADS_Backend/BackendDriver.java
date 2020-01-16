package GRADS_Backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BackendDriver {

    public static String SPACER = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    public static void main(String args[]) {

        // here is some example code on how our library can be used
        User user = new User();
        User.loadBackend(user);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your networkId: ");
        String userId = scanner.nextLine();

        try {
            user.setUser(userId);
        } catch (Exception e) {
            System.out.println("There was an error while setting the user!");
            e.printStackTrace();
            return;
        }

        // testing the generation of a normal progress summary
        System.out.println("Generating a progress summary for ["+userId+"]");
        try {
            ProgressSummary summary = User.generateProgressSummary(user);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(summary);
            System.out.println("\n\n"+json);
        } catch (Exception e) {
            System.out.println("There was an exception while generating the progress summary: " + e.toString());
            e.printStackTrace();
        }


        System.out.println("Generating a prospective progress summary for ["+userId+"]");
        try {
            ArrayList<CourseTaken> prospectiveCourses = loadProspectiveCourses("./res/prospective_classes/CIS_PROSPECTIVE_CLASSES.json");
            ProgressSummary summary = User.generateProgressSummary(user, prospectiveCourses);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(summary);
            System.out.println(SPACER+json);
        } catch (Exception e) {
            System.out.println("There was an exception while generating the prospective progress summary: " + e.toString());
            e.printStackTrace();
        }

        System.out.println("exiting backend!");
    }

    // reads classes from a file to create a prospective progress report
    public static ArrayList<CourseTaken> loadProspectiveCourses(String filename){
        ArrayList<CourseTaken> prospectiveCourses = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String json = "";
            while (reader.ready()) {
                json += reader.readLine();
            }
            reader.close();
            CourseTaken[] prospectiveCourseList = new Gson().fromJson(json, CourseTaken[].class); // couldnt figure out how to gson
            for (CourseTaken course : prospectiveCourseList) {
                prospectiveCourses.add(course);
            }
            return prospectiveCourses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
