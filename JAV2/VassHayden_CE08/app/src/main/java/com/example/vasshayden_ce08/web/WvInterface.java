package com.example.vasshayden_ce08.web;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.example.vasshayden_ce08.MainActivity;
import com.example.vasshayden_ce08.fragments.MainListFragment;
import com.example.vasshayden_ce08.objects.Student;
import com.example.vasshayden_ce08.utils.FileUtils;

import java.util.ArrayList;


public class WvInterface {

    private final Context mContext;
    private final Student student;

    private static ArrayList<Student> allStudents = new ArrayList<>();
    private static final String TAG = "today";
    static final String FILE_SCHEME = "file:///";
    private static final String FILE_AUHTORITY = "data/user/com.example.vasshayden_ce08/";
    static final String FILE_PATH = "files/";
    private static final String FILE_FULL = FILE_SCHEME + FILE_AUHTORITY + FILE_PATH;

    public WvInterface(Context mContext, Student student) {
        this.mContext = mContext;
        this.student = student;
    }

    @JavascriptInterface
    public String getStudent() {
        String htmlSyntax = "<p></p>";
        htmlSyntax += "<p></p>";
        htmlSyntax += "<p></p>";
        htmlSyntax += "<h5>Name:<h5>";
        htmlSyntax += "<p></p>";
        htmlSyntax += student.getName();
        htmlSyntax += "<h5>Student Number:<h5>";
        htmlSyntax += "<p></p>";
        htmlSyntax += student.getStudentNumber();
        htmlSyntax += "<h5>Home Town:<h5>";
        htmlSyntax += "<p></p>";
        htmlSyntax += student.getHomeTown();
        htmlSyntax += "<p></p>";

        return htmlSyntax;
    }
    @JavascriptInterface
    public String showAllStudents(){
        // get all saved students so you can find name of other seralized object
        allStudents = FileUtils.LoadSerializable(MainActivity.STUDENT_FILENAME, mContext);
        StringBuilder htmlSyntax = new StringBuilder("<p></p>");
        htmlSyntax.append("<h2>Students:<h2>");
         htmlSyntax.append("<ul>");
        for (Student s : allStudents) {
            htmlSyntax.append("<li>");
            String dynamicLink = "<a href=\"" + FILE_FULL + s.getName() +
                    s.getStudentNumber() + s.getHomeTown() +
                    "\"> " + s.getName() + "</a>";
            htmlSyntax.append(dynamicLink);
            htmlSyntax.append("</li>");
        }

        // Close unordered list
        htmlSyntax.append("</ul>");

        return htmlSyntax.toString();
    }

    @JavascriptInterface
    public void saveStudent(String name, String studentNumber, String homeTown){
        Student s = new Student(name, Integer.valueOf(studentNumber), homeTown);
        allStudents.add(s);
        FileUtils.SaveSerializable(MainActivity.STUDENT_FILENAME, mContext, allStudents);
        sendBroadcast();
    }

    @JavascriptInterface
    public void deleteStudent(){
        Log.i(TAG, "deleteStudent: ");
        allStudents = FileUtils.LoadSerializable(MainActivity.STUDENT_FILENAME, mContext);
        String check = student.getName() + student.getStudentNumber() + student.getHomeTown();
        for (Student s : allStudents) {
            //checks saved student agains saved array
            String sCheck = s.getName() + s.getStudentNumber() + s.getHomeTown();
            if(check.equals(sCheck)){
                Log.i(TAG, "deleteStudent: removing");
                allStudents.remove(s);
                break;
            }
        }
        //saves the new array to present back in the list fragment
        FileUtils.SaveSerializable(MainActivity.STUDENT_FILENAME, mContext, allStudents);
        sendBroadcast();
    }

    private void sendBroadcast() {
        Log.i(TAG, "sendBroadcast: ");
        Intent i = new Intent(MainListFragment.BROADCAST_ACTION);
        mContext.sendBroadcast(i);
    }
}
