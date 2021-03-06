/*
 * The MIT License
 *
 * Copyright 2015 Gabriel Krell.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package assassinemailer;

import java.io.Serializable;

/**
 *
 * @author Gabriel Krell
 */
public class EmailPair implements Serializable {
    private final String email;
    private final String name;
    EmailPair ( String name, String email) {
        this.email = email;
        this.name = name;
    }
    EmailPair( String input) {
        // form is [ Name <email@address> ].  Tried to be tolerant with spaces
        // (liberal trim()s ).
        input = input.trim();
        String nameHalf = input.substring(0,input.indexOf("<")-1);
        String emailHalf = input.substring(input.indexOf("<")).trim();
        name = nameHalf.trim();
        email = emailHalf.substring(emailHalf.indexOf("<")+1,emailHalf.indexOf(">")-1).trim();
    }
    @Override
    public String toString() {
        return name+" <" + email + ">";
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getName() {
        return name;
    }
    
}
