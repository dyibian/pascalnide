/*
 *  Copyright (c) 2017 Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duy.pascal.interpreter;

import com.duy.pascal.ui.utils.DLog;

import junit.framework.TestCase;

import java.io.File;

import static com.duy.pascal.Interpreter.checkSyntax;
import static com.duy.pascal.Interpreter.runProgram;

/**
 * Created by Duy on 29-May-17.
 */

public abstract class BaseTestCase extends TestCase {

    protected String userDir;

    public abstract String getDirTest();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DLog.ANDROID = false;

        userDir = System.getProperty("user.dir");
        System.out.println("current dir = " + userDir);
        userDir += File.separator + "test_pascal" + File.separator + getDirTest() + File.separator;
        if (!(new File(userDir).exists())) {
            userDir = getDirTest() + File.separator;
        }
    }

    /**
     * Compile and run a pascal file
     *
     * @param fileName - name of file
     */
    protected void run(String fileName)  {
        try {
            run(fileName, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void run(String fileName, boolean checkExpcetion) throws Exception {
        long time = System.currentTimeMillis();
        try {
            runProgram(userDir + fileName);
            assertTrue(true);
            System.out.printf("Time execution: %dms%n", System.currentTimeMillis() - time);
        } catch (Throwable e) {
            if (checkExpcetion) {
                System.out.printf("Time execution: %dms%n", System.currentTimeMillis() - time);
                e.printStackTrace();
                fail(e.getMessage());
            } else {
                throw e;
            }
        }
    }

    protected boolean parse(String fileName) {
        File file1 = new File(fileName);
        if (!file1.exists()) {
            fileName = userDir + fileName;
        }
        return checkSyntax(fileName);
    }

    /**
     * generate a pascal file
     *
     * @param file - dir of program file
     * @param in   - dir of input file for read input if need
     */
    protected void run(String file, String in) {
        try {
            runProgram(userDir + file);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void runAll() {
        boolean success = true;
        File parent = new File(userDir);
        for (File file : parent.listFiles()) {
            try {
                if (file.getName().endsWith(".pas")
                        && !file.getName().equalsIgnoreCase("readkey.pas")) {
                    run(file.getName());
                    System.out.println("complete test " + file.getName());
                }
            } catch (Exception e) {
                success = false;
            }
        }
        assertTrue("state ", success);
    }

    public void parseAll() {
        boolean success = true;
        File parent = new File(userDir);
        for (File file : parent.listFiles()) {
            if (file.getName().endsWith(".pas")) {
                success = (checkSyntax(file.getPath()));
            }
        }
        assertTrue(success);
    }
}
