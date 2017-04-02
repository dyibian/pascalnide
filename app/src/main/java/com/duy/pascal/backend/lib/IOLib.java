package com.duy.pascal.backend.lib;

import com.duy.pascal.backend.core.PascalCompiler;
import com.duy.pascal.backend.exceptions.InputStreamNotFoundException;
import com.duy.pascal.frontend.activities.ExecuteActivity;
import com.js.interpreter.runtime.VariableBoxer;
import com.js.interpreter.runtime.exception.RuntimePascalException;

import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

public class IOLib implements PascalLibrary {

    public static final String TAG = IOLib.class.getSimpleName();

    PrintStream stdout;
    Scanner stdin;
    ExecuteActivity activity;

    /**
     * constructor call by {@link ClassLoader} in {@link PascalCompiler}
     *
     * @param activity
     */
    public IOLib(ExecuteActivity activity) {
        this.activity = activity;
        if (!PascalCompiler.android) {
            this.stdout = System.out;
            this.stdin = new Scanner(System.in);
        }
    }

//    /**
//     * default constructor
//     */
//    public IOLib() {
//        if (!PascalCompiler.android) {
//            this.stdout = System.out;
//            this.stdin = new Scanner(System.in);
//        }
//    }


    @Override
    public boolean instantiate(Map<String, Object> arguments) {
        if (!PascalCompiler.android) {
            this.stdout = (PrintStream) arguments.get("stdout");
            this.stdin = (Scanner) arguments.get("stdin");
            if (stdout == null) {
                stdout = System.out;
            }
            if (stdin == null) {
                stdin = new Scanner(System.in);
            }
        }
        return true;
    }

    /**
     * Writeln procedure
     *
     * @param values - list variable
     */
    public void writeln(Object... values) {
        write(values);
        if (stdout != null) stdout.println();
        if (activity != null) activity.getConsoleView().commitChar('\n');
    }


    /**
     * write procedure
     *
     * @param values - list variable
     */
    public void write(Object... values) {
        StringBuilder result = new StringBuilder();
        for (Object o : values) {
            result.append(o);
        }
        if (stdout != null) stdout.print(result);
        if (activity != null) activity.getConsoleView().commitString(result.toString());
    }

    /**
     * readln procedure
     */
    public void readln(VariableBoxer<Object> s) throws RuntimePascalException, NumberFormatException, InputStreamNotFoundException {
        if (activity == null) {
            throw new InputStreamNotFoundException("Can not find InputStream");
        }
//        System.out.println("readln: 1" + s.get().getClass());
        String inp = "";
        if (s.get() instanceof StringBuilder) {
            //read string
            activity.startInput();
            while (activity.isInputting()) {
                sleep();
            }
            inp = activity.getInput();
            s.set(new StringBuilder(inp));
        } else if (s.get() instanceof Double) {
            //read real
            inp = inp.replaceAll("\\s+", "");
            while (inp.trim().isEmpty()) {
                activity.startInput();
                while (activity.isInputting()) {
                    sleep();
                }
                inp = activity.getInput();
            }
            s.set(Double.parseDouble(inp));
        } else if (s.get() instanceof Integer) {
            while (inp.trim().isEmpty()) {
                activity.startInput();
                while (activity.isInputting()) {
                    sleep();
                }
                inp = activity.getInput();
            }
            s.set(Integer.parseInt(inp));
        } else if (s.get() instanceof Long) {
            while (inp.trim().isEmpty()) {
                activity.startInput();
                while (activity.isInputting()) {
                    sleep();
                }
                inp = activity.getInput();
            }
            s.set(Long.parseLong(inp));
        } else if (s.get() instanceof Character) {
            while (inp.trim().length() < 1) {
                activity.startInput();
                while (activity.isInputting()) {
                    sleep();
                }
                inp = activity.getInput();
                s.set(inp.charAt(0));
            }
        }
    }

    private void sleep() {
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * read procedure
     */
    public void read(VariableBoxer<Object> s) throws RuntimePascalException, NumberFormatException {
        if (activity == null) {
            throw new RuntimeException("Can not define ExecuteActivity");
        }
        if (s == null) return;
        String inp = "";

        //read string
        if (s.get() instanceof StringBuilder) {
            //read string
            activity.startInput();
            while (activity.isInputting()) {
                sleep();
            }
            inp = activity.getInput();
            s.set(new StringBuilder(inp));
        }
        //read double
        else if (s.get() instanceof Double) {
            //read real
            inp = inp.replaceAll("\\s+", "");
            while (inp.trim().isEmpty()) {
                activity.startInput();
                while (activity.isInputting()) {
                    sleep();
                }
                inp = activity.getInput();
            }
            s.set(Double.parseDouble(inp));
        }
        //read integer
        else if (s.get() instanceof Integer) {
            inp = inp.replaceAll("\\s+", "");
            while (inp.trim().isEmpty()) {
                activity.startInput();
                while (activity.isInputting()) {
                    sleep();
                }
                inp = activity.getInput();
            }
            s.set(Integer.parseInt(inp));
        }
        //read longint
        else if (s.get() instanceof Long) {
            while (inp.trim().isEmpty()) {
                activity.startInput();
                while (activity.isInputting()) {
                    sleep();
                }
                inp = activity.getInput();
            }
            s.set(Long.parseLong(inp));
        }
        //read char
        else if (s.get() instanceof Character) {
            while (inp.trim().length() < 1) {
                activity.startInput();
                while (activity.isInputting()) {
                    sleep();
                }
                inp = activity.getInput();
                s.set(inp.charAt(0));
            }
        }
    }


    /**
     * readln procedure
     */
    public void waitEnter() {
        if (activity != null) {
            activity.startInput();
            while (activity.isInputting()) {
                sleep();
            }
        }
    }

//    public void readln() {
//        if (activity != null) {
//            activity.startInput();
//            while (activity.isInputting()) {
//                sleep();
//            }
//        }
//    }
//
//
//    public void readln(VariableBoxer<Object> variableBoxer, VariableBoxer variableBoxer2) {
//        if (activity != null) {
//            activity.startInput();
//            while (activity.isInputting()) {
//                sleep();
//            }
//        }
//    }
//
//    public void readln(VariableBoxer<Object> variableBoxer, VariableBoxer variableBoxer2, VariableBoxer variableBoxer3) {
//        if (activity != null) {
//            activity.startInput();
//            while (activity.isInputting()) {
//                sleep();
//            }
//        }
//    }


    public void printf(String format, Object... args) {
        if (stdout != null) stdout.printf(format, args);
    }

}
