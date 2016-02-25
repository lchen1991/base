package com.base.app.test;

import android.test.AndroidTestCase;

public class TestMath extends AndroidTestCase {    
        
    static final String LOG_TAG = "MathTest";    
        
    @Override    
    protected void setUp() throws Exception {    
    }    
        
    public void testAdd() {    
    	System.out.println("this is test!");
    }    
        
    public void testDec() {    
    }    
    
    @Override    
    protected void tearDown() throws Exception {    
        super.tearDown();    
    }    
    
    @Override    
    public void testAndroidTestCaseSetupProperly() {    
        super.testAndroidTestCaseSetupProperly();    
    }    
    
}    