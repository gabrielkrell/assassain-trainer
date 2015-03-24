package assassinemailer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Gabriel
 */
public class AssassinEmailer {

    private ArrayList<String[]> emails; // {name, address}
    private String userEmail;
    private String userPassword;
    private String messageText =  "Hi <recipient>!  You've been entered into a"
            + " game of Assassin!  Your target is <target>.  The game will begin"
            + " at <time> on <date>.";
    private String time;
    private String date;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        This project should:
            Take an email/pass to send with (maybe initially gmail)
            Take a list of emails and names to include in the game
            Decide who's targeting who
            Send everyone on the list an email telling them that they're playing,
                who their target is, and the rules of the game.
        */
        System.out.println("Command-line operation is not yet supported!  Sorry!");
        System.out.println("Right now, this method doubles as a tester.");
        System.out.println("----------------------------------------------------");
        
        ArrayList<String[]> al = new ArrayList<>();
        al.add(new String[] {"adam@gmail.com", "Adam"});
        al.add(new String[] {"bob@cps.edu", "Bob"});
        al.add(new String[] {"cathy@yahoo.com","Cathy"});
        al.add(new String[] {"delilah@att.net","Delilah"});
        
        AssassinEmailer ae = new AssassinEmailer(al);
        ae.setDate("DATE");
        ae.setTime("TIME");
        for (String s : ae.previewMessages()) {
            System.out.println(s);
            System.out.println();
        }
    }
    
    /** Create a new AssassinEmailer class.  Make sure to set the email, and
     * password before using.  You can also run AssassinEmailer(ArrayList<String[]> emails)
     * 
     */
    public AssassinEmailer() {
        emails = new ArrayList<>();
    }
    
    /** Create a new AssassiniEmailer() and set the email list to <code>emails</code>.
     *
     * @param emails
     */
    public AssassinEmailer(ArrayList<String[]> emails) {
        this.emails = emails;
    }
    
    public void setUserEmail( String uname) {
        userEmail = uname;
    }
    
    public void setUserPass ( String pword ) {
        userPassword = pword;
    }
    
    public String getMessage() {
        return messageText;
    }
    
    public void setMessage( String msg ) {
        messageText = msg;
    }
    
    public String getTime() {
        return time;
    }
    
    public void setTime( String t) {
        time = t;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String d) {
        date = d;
    }
    
    public ArrayList<String> previewMessages() {
        ArrayList<String> out = new ArrayList<>();
        for (String[][] mailPair : generatePairs()) {
            out.add(processMessage(mailPair[0][1], mailPair[1][1], time, date));
        }
        return out;
    }
    
    private ArrayList<String[][]> generatePairs() {
        // shuffle the email addresses and pair them so that there is only one loop.
        // (each address leads to only one other and is lead to by only one other)
        
        ArrayList<String[]> newpairs = (ArrayList<String[]>) emails.clone();
        Collections.shuffle(newpairs); // preserve original email order for editing later
        
        ArrayList<String[][]> output = new ArrayList<>();
        for (int i=0; i<newpairs.size()-1; i++) {
            output.add(new String[][]{newpairs.get(i),newpairs.get(i+1)});
            System.out.println(Arrays.deepToString(output.get(output.size()-1)));
        }
        output.add(new String[][]{newpairs.get(newpairs.size()-1),newpairs.get(0)});
        return output;
    }
    
    private String processMessage(String recipient, String target, String time, String date) {
        StringBuilder out = new StringBuilder(messageText);
        replaceAll(out,"<recipient>",recipient);
        replaceAll(out,"<target>",target);
        replaceAll(out,"<time>",time);
        replaceAll(out,"<date>",date);
        return out.toString();
    }
    
    /** Replace all instances of <code>find</code> with <code>replace</code> in
     * the specified StringBuilder.
     * 
     * @param input StringBuilder to modify
     * @param find String to remove occurrences of
     * @param replace String to replace <code>find</code>s with
     */
    private void replaceAll (StringBuilder input, String find, String replace) {
//        StringBuilder sb = new StringBuilder(input);
        StringBuilder sb = input;
        while (sb.indexOf(find)>0) {
            sb = sb.replace(sb.indexOf(find), sb.indexOf(find)+find.length(), replace);
//            System.out.println(sb);
//            input = input.substring(0,input.indexOf(find)-1)+input.substring(input.indexOf(find)+find.length()+2);
        }
        
    }
    
    
}
