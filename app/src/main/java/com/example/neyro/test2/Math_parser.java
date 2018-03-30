package com.example.neyro.test2;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Math_parser {

    private static String[] Functions_array = {"ABS", "SQRT", "SQR", "EBP", "LN", "ARCSIN", "ARCCOS", "SIN", "COS", "ARCTAN", "COTAN", "TAN", "LG"};
    private static char[] Operations_array = {'^', '/', '*', '+', '-'};
    public static int Degrees_or_radian = 0;
    public static int Accuracy=1;
    public static double Step=1.0;

  /*  public static void main(String[] args) {
        System.out.println("Print 'exit' to exit program");
        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Введiть вираз");
            String expresssion = scan.next();
            if (expresssion.equals("exit"))
                System.exit(0);
            if (expresssion.equals("set_deg")) {
                System.out.println("Градуси");
                Degrees_or_radian = 0;
                continue;
            }
            if (expresssion.equals("set_rad")) {
                System.out.println("Радіани");
                Degrees_or_radian = 1;
                continue;
            }
            try {
                if (expresssion.toUpperCase().indexOf("X") < 0)
                    System.out.println(Evaluate(expresssion, 0));

                else {
                    System.out.println("Введiть значення Х");
                    Double value = scan.nextDouble();
                    System.out.println(Evaluate(expresssion, value));
                }
            } catch (Exception ex) {
//                На дебаг........
//                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
            System.out.println();
        }
    }
    */

    static double Evaluate(String f, double X) throws Exception {
        String str;
        int i = 0, p;
        f = Format_string(f);
        str = Insert_X(f.toUpperCase(), X);
        str = Insert_constants(str);
        str = Change_separator(str);
        str = Evaluate_functions(str, Functions_array, Operations_array);
        str = Open_brackets(str, Operations_array);
        while (true) {
            str = Evaluate_operation(str, Operations_array[i]);
            p = new String(str).indexOf(Operations_array[i]);
            if (p <= 0) {
                if (i == Operations_array.length - 1 || p == 0)
                    break;
                else i++;
            }

        }
        return Double.parseDouble(str);
    }

    static String Insert_constants(String str) {
        int pi_pos, e_pos;

        pi_pos = str.indexOf("PI");
        while (pi_pos >= 0) {
            str = new StringBuilder(str).replace(pi_pos, pi_pos + 2, "3.14159").toString();
            pi_pos = str.indexOf("PI");
        }
        e_pos = str.indexOf("E");
        while (e_pos >= 0) {
            str = new StringBuilder(str).replace(e_pos, e_pos + 1, "2.718281").toString();
            e_pos = str.indexOf("E");
        }
        return str;
    }

    static String Change_separator(String str) {
        int krap;
        krap = str.indexOf(",");
        while (krap >= 0) {
            str = new StringBuilder(str).replace(krap, krap + 1, ".").toString();
            krap = str.indexOf(",");
        }
        return str;
    }


    static String Format_string(String str) {
        int probil;
        probil = str.indexOf(" ");
        while (probil >= 0) {
            str = new StringBuilder(str).replace(probil, probil + 1, "").toString();
            probil = str.indexOf(" ");
        }
        return str;
    }

    static String Remove_symbols(String str) {
        int Str_lenght = str.length() - 1;
        for (int i = 0; i < Str_lenght - 1; i++) {
            if (i == 0) {
                if (str.toCharArray()[0] == '+' || str.toCharArray()[0] == '+')
                    str = new StringBuilder(str).replace(0, 1, "").toString();
            }
            if (str.toCharArray()[i] == '+' && str.toCharArray()[i + 1] == '-' ||
                    str.toCharArray()[i] == '-' && str.toCharArray()[i + 1] == '+')
                str = new StringBuilder(str).replace(i, i + 2, "-").toString();
            if (str.toCharArray()[i] == '+' && str.toCharArray()[i + 1] == '+' ||
                    str.toCharArray()[i] == '-' && str.toCharArray()[i + 1] == '-')
                str = new StringBuilder(str).replace(i, i + 2, "+").toString();
        }
        return str;
    }

    static String Evaluate_operation(String str, char action) throws Exception {
        str = Remove_symbols(str);
        int Str_lenght = str.length() - 1;
        if (str.indexOf("E") > -1) {
            throw new TooMuchNumbers();
        }
        for (int i = Str_lenght; i >= 0; i--) {
            int Symbol_pos = Integer.MIN_VALUE;
            int Value_one = Integer.MIN_VALUE;
            int Value_two = Integer.MIN_VALUE;
            Double First_value = Double.MIN_VALUE;
            Double Second_value = Double.MIN_VALUE;
            Double Third_value = Double.MIN_VALUE;
            String Result = "";
            if (str.toCharArray()[i] == action &&
                    i > 0) {
                Symbol_pos = i;
                Value_one = Symbol_pos + 1;
                Second_value = Double.parseDouble(str.substring(Value_one, str.length()));
                for (int j = Symbol_pos - 1; j >= 0; j--) {
                    String All_operations = new String(Operations_array);
                    String Operations = new String(new char[]{'+', '-'}).toString();
                    if (All_operations.contains("" + str.toCharArray()[j])) {
                        if (Operations.contains("" + str.toCharArray()[j]))
                            Value_two = j;
                        else Value_two = j + 1;
                        break;
                    }
                }
                if (Value_two == Integer.MIN_VALUE)
                    Value_two = 0;
                First_value = Double.parseDouble(str.substring(Value_two, Symbol_pos));
                switch (action) {
                    case '^':
                        try {
                            if (Math.abs(First_value) == 0)
                                Result = Float.toString(0);
                            if (Math.abs(Second_value) == 0)
                                Result = Float.toString(1);
                            if ((First_value > 0) && (Second_value > 0)) {
                                Third_value = Math.pow(First_value, Second_value);
                                Result = Double.toString(Third_value);
                            }
                            if ((First_value < 0) && (Second_value < 0)) {
                                Third_value = Math.pow(Math.abs(First_value), Second_value);
                                Result = '-' + Double.toString(Third_value);
                            }
                            if (((First_value > 0) && (Second_value < 0)) || ((First_value < 0) && (Second_value > 0))) {
                                Third_value = Math.pow(First_value, Second_value);
                                if (((Second_value.intValue()) % 2 > 0) || (Third_value < 0))
                                    Result = '-' + Double.toString(Third_value);
                                else
                                    Result = Double.toString(Third_value);
                            }
                        } catch (Exception e) {
                            System.out.print("ERROR");
                            System.exit(0);
                        }
                        break;
                    case '*':
                        Third_value = First_value * Second_value;
                        Result = Double.toString(Third_value);
                        break;
                    case '/':
                        if (Second_value != 0) {
                            Third_value = First_value / Second_value;
                            Result = Double.toString(Third_value);
                        } else {
                            System.exit(0);
                        }
                        break;
                    case '+':

                        Third_value = First_value + Second_value;
                        Result = Double.toString(Third_value);
                        break;
                    case '-':

                        Third_value = First_value - Second_value;
                        Result = Double.toString(Third_value);
                        break;
                }
                if (Third_value >= 0) Result = "+" + Result;
                str = new StringBuilder(str).replace(Value_two, str.length(), Result).toString();
                Str_lenght = str.length() - 1;
                if (i >= Str_lenght) break;
            }
        }
        return str;
    }

    static String Open_bracket(String str, char[] operation) throws Exception {
        int i, j, pos1 = 0, pos2 = 0, count, k, Brackets = 2;
        String str1;
        for (i = str.length() - 1; i > 1; i--)
            if (str.toCharArray()[i] == ')')
                pos1 = i;
        count = 0;
        for (j = pos1; j >= 1; j--) {
            if (str.toCharArray()[j] == '(') {
                pos2 = j;
                break;
            }
            count = count + 1;
        }
        if (pos2 == 0 && pos1 == str.length() - 1) {
            str1 = new StringBuilder(str).substring(1, str.length() - 1);
            pos2 = 0;
            Brackets = 2;
        } else
            str1 = new StringBuilder(str).substring(pos2 + 1, pos1);
        String operators = new String(Operations_array);
        int Replace_length = str1.length();
        String Operations = new String(Operations_array);
        for (k = 0; k < operation.length - 1; k++)
            str1 = Evaluate_operation(str1, operation[k]);
        str = new StringBuilder(str).replace(pos2, pos2 + Replace_length + Brackets, str1).toString();
        str = Remove_symbols(str);
        for (int c = 1; c < str.length(); c++) {
            if (Operations.contains(String.valueOf(str.toCharArray()[c]))) {
                if (str.toCharArray()[0] != '(' && str.toCharArray()[str.length() - 1] != ')')
                    str = Open_brackets("(" + str + ")", Operations_array);
                else str = Open_brackets(str, Operations_array);
                break;
            }
        }
        return str;
    }

    static String Open_brackets(String str, char[] operation) throws Exception {
        int i = 0;
        while (true) {
            if (i >= str.length() - 1) break;
            if (new String(new char[]{')', '('}).contains(Character.toString(str.toCharArray()[i]))) {
                str = Open_bracket(str, operation);
                i = 1;
            }
            i = i + 1;
        }
        return str;
    }

    static String Evaluate_functions(String str, String[] func, char[] operation) throws Exception {
        int position0, position1, position2, i, count, p, k, z1, z2, j;
        String str1, temp_fl, temp_mn;
        double value = 0;
        int fl_pos, hh;
        for (k = 0; k < func.length - 1; k++) {
            position0 = new StringBuilder(str).indexOf(func[k]);
            if (position0 >= 0) {
                count = 0;
                position1 = 0;
                z1 = 0;
                position2 = 0;
                z2 = 0;
                for (i = position0 + func[k].length(); i < str.length(); i++) {
                    if (str.toCharArray()[i] == '(') {
                        if (position1 == 0) position1 = i;
                        z1 = z1 + 1;
                        count = count + 1;
                    }
                    if (str.toCharArray()[i] == ')') {
                        position2 = i;
                        z2 = z2 + 1;
                        if (z2 == z1) break;
                    }
                }
                str1 = new StringBuilder(str).substring(position1, position2 - position1 + position1 + 1);

                for (j = 0; j < func.length - 1; j++) {
                    p = new StringBuilder(str).indexOf(func[j]);
                    if (p >= 0)
                        str1 = Evaluate_functions(str1, func, operation);
                }
                str1 = Open_brackets(str1, operation);
                float Returned_value = Float.parseFloat(str1);

                if (Degrees_or_radian == 0 && (k>4 && k<12)) {
                    Returned_value = (float) Math.toRadians(Returned_value);
                }
                try {
                    switch (k) {
                        case 0:
                            value = Math.abs(Returned_value);
                            break;
                        case 1:
                            value = Math.sqrt(Returned_value);
                            break;
                        case 2:
                            value = Math.pow(Returned_value, 2);
                            break;
                        case 3:
                            value = Math.exp(Returned_value);
                            break;
                        case 4:
                            value = Math.log(Returned_value);
                            break;
                        case 5:
                            value = Math.asin(Returned_value);
                            break;
                        case 6:
                            value = Math.acos(Returned_value);
                            break;
                        case 7:
                            value = Math.sin(Returned_value);
                            break;
                        case 8:
                            value = Math.cos(Returned_value);
                            break;
                        case 9:
                            value = Math.tan(Returned_value);
                            break;
                        case 10:
                            if (Returned_value != 0)
                                value = Math.atan(Returned_value);
                            break;
                        case 11:
                            if (Returned_value != 0)
                                value = Math.tan(Returned_value);
                            break;
                        case 12:
                            value = Math.log10(Returned_value);
                            break;
                    }
                } catch (Exception e) {
                }
//                if (Degrees_or_radian==0)
//                {
//                    value=Math.toDegrees(value);
//                }
                for (i = 1; i < String.valueOf(value).length() - 1; i++) {
                    fl_pos = 0;
                    fl_pos = new StringBuilder(String.valueOf(value)).indexOf("E-");
                    if (fl_pos > 0) {
                        temp_fl = String.valueOf(value);
                        temp_mn = (new StringBuilder(temp_fl).substring(fl_pos + 2, temp_fl.length()));
                        temp_fl = new StringBuilder(temp_fl).delete(fl_pos, temp_fl.length()).toString();
                        for (hh = 1; hh < (int) (Float.parseFloat(temp_mn)); hh++)
                            value = value / 10;
                    }
                }
                value = Float.parseFloat(String.valueOf(value));
                if (position0 != 0) {
                    if (str.toCharArray()[position0 - 1] == '-' && value < 0)
                        str = new StringBuilder(str).replace(position0 - 1, position2 + 1, "+" + String.valueOf(Math.abs(value))).toString();
                    else if (str.toCharArray()[position0 - 1] == '-' && value > 0)
                        str = new StringBuilder(str).replace(position0 - 1, position2 + 1, "-" + String.valueOf(value)).toString();
                    else
                        str = new StringBuilder(str).replace(position0, position2 + 1, String.valueOf(value)).toString();
                } else str = new StringBuilder(str).replace(position0, position2 + 1, String.valueOf(value)).toString();
            }
        }
        for (k = 0; k < func.length; k++) {
            p = new StringBuilder(str).indexOf(func[k]);
            if (p >= 0)
                str = Evaluate_functions(str, func, operation);
        }
        return str;
    }


    static String Insert_X(String str, double value) throws Exception {
        int X;
        while ((X = str.indexOf("X")) > -1)
            str = new StringBuilder(str).replace(X, X + 1, String.valueOf(value)).toString();
        return str;
    }

    public static byte[] toByteArray(Object obj) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return bytes;
    }

    public static Func toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        Func obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = (Func) ois.readObject();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
        return obj;
    }

    public static String toString(byte[] bytes) {
        return new String(bytes);
    }
}
