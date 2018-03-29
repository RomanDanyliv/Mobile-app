package com.example.neyro.test2;

import java.util.ArrayList;

/**
 * Created by Neyro on 25.03.2018.
 */

public class FuncManager {
    public static final FuncManager INSTANCE = new FuncManager();
    public static ArrayList<Func> AllFunctions = new ArrayList<Func>();
    public static Func FindById(int id)
    {
        for (Func item : AllFunctions)
        {
            if (item.ID==id)
                return item;
        }
        return null;
    }
}
