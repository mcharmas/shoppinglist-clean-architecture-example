package pl.charmas.shoppinglist.base;


import java.util.Arrays;

import dagger.ObjectGraph;

public class TestActivityModules {
    private static Object[] additionalTestModules;

    public static void setAdditionalTestModules(Object... modules) {
        TestActivityModules.additionalTestModules = modules;
    }

    public static ObjectGraph plusTestModules(ObjectGraph graph, Object... modules) {
        if (additionalTestModules == null) {
            if (modules != null) {
                return graph.plus(modules);
            }
            return graph;
        }
        Object[] testModules = additionalTestModules;
        additionalTestModules = null;
        if (modules == null) {
            return graph.plus(testModules);
        }

        return graph.plus(concat(modules, testModules));
    }

    private static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
