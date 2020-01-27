package com.cleancoder.args;

/**
 * <h1>ArgsMain</h1>
 * <p>
 * The ArgsMain class provides an interface between
 * the command line and the Args class which is used
 * to parse the command line arguments
 * </p>
 * @author      Shovan Swain
 * @version     1.0
 * @param args  String array of command line arguments
 */
public class ArgsMain {
public static void main(String[] args) {
        try {
                // Declare Schema for arguments
                Args arg = new Args("h,f,s*,n#,a##,p[*],d&", args);

                executeApplication(arg);

        }
        catch (ArgsException e) {
                System.out.printf("Argument error: %s\n", e.errorMessage());
        }
}

private static void executeApplication(Args arg) {
        // Checks which arguments have been provided and prints accordingly
        if (arg.has('h')) {
                System.out.printf("Hi, Welcome to a JavaArgs modification by Shovan Swain\n");
                System.out.printf("Usage:\n");
                System.out.printf("\t-f For Boolean variables\n");
                System.out.printf("\t-n For Integer variables\n");
                System.out.printf("\t-a For Double variables\n");
                System.out.printf("\t-p For Multiple Strings\n");
                System.out.printf("\t-d For Maps\n");
                System.out.printf("\t-s For String\n");
                System.out.printf("\nSample Command 'java -cp build/jar/args.jar com.cleancoder.args.ArgsMain -f -n 2'\n");
        }
        if (arg.has('f')) {
                System.out.printf("Boolean is %s\n", arg.getBoolean('f'));
        }
        if (arg.has('n')) {
                System.out.printf("Integer is %d\n", arg.getInt('n'));
        }
        if (arg.has('s')) {
                System.out.printf("String is %s\n", arg.getString('s'));
        }
        if (arg.has('a')) {
                System.out.printf("Double is %f\n", arg.getDouble('a'));
        }
        if (arg.has('p')) {
                System.out.println("String array elements are : ");

                String[] stringArrayArg = arg.getStringArray('p');

                for (int i = 0; i < stringArrayArg.length; i++) {
                        System.out.printf("\t%s\n", stringArrayArg[i]);
                }

        }
        if (arg.has('d')) {
                System.out.println("Map is " + arg.getMap('d').toString());
        }
}
}
