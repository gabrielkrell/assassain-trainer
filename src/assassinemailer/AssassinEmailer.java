package assassinemailer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author Gabriel
 */
public class AssassinEmailer {

    private ArrayList<EmailPair> emails; // {name, address}
    private String userEmail;
    private String userPassword;
    private String messageText =  "Hi <recipient>!  You've been entered into a"
            + " game of Assassin!  Your target is <target>.  The game will begin"
            + " at <time> on <date>.";
    private String time;
    private String date;
    private String title = "Assassin Game";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        This project should:
            Take an email/pass to send with (maybe initially gmail)
            Take a list of emails and names to include in the game
            Decide who's targeting who
            send everyone on the list an email telling them that they're playing,
                who their target is, and the rules of the game.
        */
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(
"Enter your gmail username (no @gmail.com).  Other providers are not supported."
        + " Sorry!");
        try {
            AssassinEmailer ae = new AssassinEmailer();
            
            System.out.print("Username: ");
            ae.setUserEmail(br.readLine());

            System.out.print("Password: ");
            ae.setUserPass(br.readLine());
            
            //<editor-fold defaultstate="collapsed" desc="clear output, but don't scroll up!">
            
            for (int x =0; x<35; x++) {
                System.out.println();
            }
            System.out.println("Username: "+ae.getUserEmail());
            System.out.println("Password: ");
            //</editor-fold>
        
            System.out.print("Time of event (String): ");
            ae.setTime(br.readLine());
            
            System.out.print("Date of event (String): ");
            ae.setDate(br.readLine());
            
            System.out.println("Email/name pairs: (enter \"done\" to exit)");
            System.out.println();
            ArrayList<EmailPair> emails = new ArrayList<>();
            
            String[] inputPair = new String[]{"",""};
            
            while (
                    !("done".equals(inputPair[0].toLowerCase()) || 
                    "done".equals(inputPair[1].toLowerCase()))
                  ) 
            {
                if (!inputPair[0].isEmpty() && !inputPair[1].isEmpty()) { // skip the first, blank thing as well as
                    emails.add(new EmailPair(inputPair[0],inputPair[1])); // ones that cause a break ("done")
                }
                
                System.out.print("Email "+(emails.size()+1) + ": ");
                inputPair[0] = br.readLine();
                if (inputPair[0].toLowerCase().equals("done")) {
                    break;
                }
                System.out.print("Name "+(emails.size()+1) +": ");
                inputPair[1] = br.readLine();
                System.out.println();
            }
            
            for (EmailPair t : emails) {
                System.out.println(t);
            }
            ae.setEmails(emails);
            
            
            System.out.println("Default: message:");
            System.out.println(ae.getMessage());
            System.out.println();
            System.out.println("Type your message, or enter \"default\" for the default message.");
            String input = br.readLine();
            if ( !input.toLowerCase().equals("default")) {
                ae.setMessage(input);
            }
            
            
            String confirm = "";
            while (!("send".equals(confirm)||"quit".equals(confirm))) {
                System.out.println(
                        "Type \"send\" to send emails to " + emails.size() + " participants, or \"quit\" to cancel.");
                confirm = br.readLine();
            }
            
            if (confirm.equals("quit")) { System.exit(0); }
            
            for (EmailPair thing : ae.emails) {
                System.out.println(thing);
            }
            ae.sendMessages();

        } catch (IOException ex) {
            System.out.println("Couldn't read input!  Exiting.");
            Logger.getLogger(AssassinEmailer.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        
        
        
    }
    
    public static void test() {
        
        System.out.println("Command-line operation is not yet supported!  Sorry!");
        System.out.println("Right now, this method doubles as a tester.");
        System.out.println("----------------------------------------------------");
        
        ArrayList<EmailPair> al = new ArrayList<>();
        al.add(new EmailPair("gabrielkrell+adam@gmail.com", "Adam"));
        al.add(new EmailPair("gabrielkrell+bob@gmail.com", "Bob"));
        al.add(new EmailPair("gabrielkrell+cathy@gmail.com","Cathy"));
        al.add(new EmailPair("gabrielkrell+delilah@gmail.com","Delilah"));
        
        
        AssassinEmailer ae = new AssassinEmailer(al);
        ae.setDate("DATE");
        ae.setTime("TIME");
        // INITIALIZE PWORD AND EMAIL
        ae.sendMessages();
//        for (String s : ae.previewMessages()) {
//            System.out.println(s);
//            System.out.println();
//        }
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
    public AssassinEmailer(ArrayList<EmailPair> emails) {
        setEmails(emails);
    }
    
    public void setEmails(ArrayList<EmailPair> emails) {
        this.emails = emails;
    }
    
    public void setUserEmail( String uname) {
        userEmail = uname;
    }
    
    public String getUserEmail( ) {
        return userEmail;
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
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public ArrayList<String> previewMessages() {
        ArrayList<String> out = new ArrayList<>();
        for (EmailPair[] mailPair : generatePairs()) {
            out.add(processMessage(mailPair[0].getName(), mailPair[1].getName(), time, date));
        }
        return out;
    }
    
    public void sendMessages() {
        for (EmailPair[] mailPair : generatePairs()) {
            try {
                GmailSender.send(
                        userEmail, userPassword,  // login creds
                        mailPair[0].getEmail(), title, // TO:, title
                        processMessage(mailPair[0].getName(), mailPair[1].getName(), time, date));
                // message: recipient, target, time, date
                /*
                username -
                GMail username password -
                GMail password
                recipientEmail - TO recipient
                title - title of the message
                message - message to be sent
                */
            } catch (MessagingException ex) {
                Logger.getLogger(AssassinEmailer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private ArrayList<EmailPair[]> generatePairs() {
        // shuffle the email addresses and pair them so that there is only one loop.
        // (each address leads to only one other and is lead to by only one other)
        
        ArrayList<EmailPair> newpairs = (ArrayList<EmailPair>) emails.clone();
        Collections.shuffle(newpairs); // preserve original email order for editing later
        
        ArrayList<EmailPair[]> output = new ArrayList<>();
        for (int i=0; i<newpairs.size()-1; i++) {
            output.add(new EmailPair[]{newpairs.get(i),newpairs.get(i+1)});
//            System.out.println(Arrays.deepToString(output.get(output.size()-1)));
        }
        output.add(new EmailPair[]{newpairs.get(newpairs.size()-1),newpairs.get(0)});
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
    
    public String assembledMessageForm() {
        StringBuilder out = new StringBuilder(messageText);
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
        if (find.equals(replace)) {
            return;
        }
//        StringBuilder sb = new StringBuilder(input);
        StringBuilder sb = input;
        while (sb.indexOf(find)>0) {
            sb = sb.replace(sb.indexOf(find), sb.indexOf(find)+find.length(), replace);
//            System.out.println(sb);
//            input = input.substring(0,input.indexOf(find)-1)+input.substring(input.indexOf(find)+find.length()+2);
        }
        
    }
    
    public Boolean ready() {
        
        
        System.out.println(userEmail+messageText+emails);
        System.out.println(!userPassword.isEmpty());
         return null!=userEmail
             &&  null!=userPassword
             &&  null!=messageText
             && !emails.isEmpty();
    }
    
    
    
    
}
