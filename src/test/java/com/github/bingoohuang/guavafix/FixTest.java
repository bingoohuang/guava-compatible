package com.github.bingoohuang.guavafix;

import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.FuturesFix;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import lombok.SneakyThrows;
import org.junit.Test;

/**
 * @author bingoohuang [bingoohuang@gmail.com] Created on 2017/2/7.
 */
public class FixTest {
    @Test
    public void test() {
        ClassPool pool = ClassPool.getDefault();

        fixFutures(pool, "target/classes");
        fixTypeToken(pool, "target/classes");
    }

    @SneakyThrows
    private static void fixTypeToken(ClassPool pool, String directoryName) {
        CtClass dest = pool.get(TypeToken.class.getName());
        pool.importPackage(TypeToken.class.getName());
        CtMethod method = CtMethod.make(
                "public final boolean isAssignableFrom(TypeToken type) {" +
                        "        return type.isSubtypeOf(this.getType());" +
                        "    }", dest);
        dest.addMethod(method);
        dest.writeFile(directoryName);
    }

    @SneakyThrows
    private static void fixFutures(ClassPool pool, String directoryName) {
        CtClass src = pool.get(FuturesFix.class.getName());
        CtClass dest = pool.get(Futures.class.getName());
        copyMethods(src, dest, "withFallback");
        copyMethods(src, dest, "transform");
        dest.writeFile(directoryName);
    }

    @SneakyThrows
    private static void copyMethods(CtClass src, CtClass dest, String methodName) {
        CtMethod[] withFallbacks = src.getDeclaredMethods(methodName);
        for (CtMethod ctMethod : withFallbacks) {
            CtMethod m = CtNewMethod.copy(ctMethod, dest, null);
            dest.addMethod(m);
        }
    }
}
