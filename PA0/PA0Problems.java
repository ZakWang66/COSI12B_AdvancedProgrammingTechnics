import java.util.*;

public class PA0Problems {

    // Finds how many times a given character occurs within a string
    // Returns 0 if the character does not appear
    public static int charCount(String str, char c) {
        // Implement me!
        int count = 0;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == c){
                count++;
            }
        }
    	return count;
    }

    // Performs the mathematical operation "a [op] b" and returns the result
    // op will be one of four values: "ADD", "SUB", "MUL", "DIV"
    public static double calculator(int a, int b, String op) {
        // Implement me!
        double result = 0;
        switch(op){
            case "ADD": result = (double)a + (double)b; break;
            case "SUB": result = (double)a - (double)b; break;
            case "MUL": result = (double)a * (double)b; break;
            case "DIV": result = (double)a / (double)b; break;
            default: System.out.println("Unknown op");
        }
    	return result;
    }

    // Returns an array of length [len], in which every value is [val]
    public static int[] arrayBuilder(int len, int val) {
        // Implement me!
        int[] arr = new int[len];
        for(int i = 0; i < arr.length; i++){
            arr[i] = val;
        }
    	return arr;
    }

    // Returns a string consisting of a "triangle" that builds the given string, one letter at a time,
    // and then descends one letter at a time
    // Example - messageTriangle("pito") returns:
    // p
    // pi
    // pit
    // pito
    // pit
    // pi
    // p
    public static String messageTriangle(String str) {
        // Implement me!
        if(str.length() == 1){
            return str;
        }
        String result = "";
        for(int i = 1; i <= str.length(); i++){
            result += str.substring(0,i) + '\n';
        }
        for(int i = str.length() - 1; i >= 2; i--){
            result += str.substring(0,i) + '\n';
        }
        result += str.substring(0,1);
        return result;
    }

    // !!! DO NOT MODIFY ANY CODE BELOW THIS LINE !!!

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String input = "";
        boolean keepGoing = true;
        while(keepGoing) {
            System.out.println("Methods: charCount | calculator | arrayBuilder | messageTriangle");
            System.out.print("Name of method to run (or \"exit\"): ");
            input = console.nextLine();
            switch (input) {
                case "charCount": testCharCount(console); break;
                case "calculator": testCalculator(console); break;
                case "arrayBuilder": testArrayBuilder(console); break;
                case "messageTriangle": testMessageTriangle(console); break;
            }
            if(input.equals("exit")) keepGoing = false;
        }
    }

    private static void testCharCount(Scanner console) {
        System.out.println("Testing: int charCount(String str, char c)");
        System.out.print("\tstr: ");
        String str = console.nextLine();
        System.out.print("\tc: ");
        char c = console.nextLine().charAt(0);
        System.out.println("charCount(\"" + str + "\", " + c + ") = " + charCount(str, c));
    }

    private static void testCalculator(Scanner console) {
        System.out.println("Testing: double calculator(int a, int b, String op)");
        System.out.print("\ta: ");
        int a = console.nextInt();
        System.out.print("\tb: ");
        int b = console.nextInt();
        console.nextLine();
        System.out.print("\top (can be ADD, SUB, MUL, or DIV): ");
        String op = console.nextLine();
        System.out.println("calculator(" + a + ", " + b + ", \"" + op + "\") = " + calculator(a, b, op));
    }

    private static void testArrayBuilder(Scanner console) {
        System.out.println("Testing: int[] arrayBuilder(int len, int val)");
        System.out.print("\tlen: ");
        int len = console.nextInt();
        System.out.print("\tval: ");
        int val = console.nextInt();
        System.out.println("arrayBuilder(" + len + ", " + val + ") = " + Arrays.toString(arrayBuilder(len, val)));
    }

    private static void testMessageTriangle(Scanner console) {
        System.out.println("Testing: String messageTriangle(String str)");
        System.out.print("\tstr: ");
        String str = console.nextLine();
        System.out.println("messageTriangle(\"" + str + "\"):\n" + messageTriangle(str));
    }

}
