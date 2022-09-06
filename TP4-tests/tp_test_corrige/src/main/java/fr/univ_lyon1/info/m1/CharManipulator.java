package fr.univ_lyon1.info.m1;

public class CharManipulator implements ICharManipulator {

    public String invertOrder(String string) {
	StringBuffer out = new StringBuffer();
	for (int i = 0; i < string.length(); i++) {
	    out.append(string.charAt(string.length() - i - 1));
	}
	return out.toString();
    }

    public String removePattern(String input, String pattern) {
	if (pattern == "") {
	    return input; // infinite loop in mvn test without this.
	}
	String res = input;
	String oldres = "";
	while (res != oldres) {
	    oldres = res;
	    res = res.replace(pattern, "");
	}
	return res;
    }
    
    public String invertCase(String string) {
	throw new UnsupportedOperationException("invertCase"); // TODO
    }

}
