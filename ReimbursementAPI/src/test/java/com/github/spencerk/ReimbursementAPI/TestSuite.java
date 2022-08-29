package com.github.spencerk.ReimbursementAPI;

import com.github.spencerk.ReimbursementAPI.entity.EmployeeTest;
import com.github.spencerk.ReimbursementAPI.entity.ReimbursementTicketTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SelectPackages(
        "com.github.spencerk.ReimbursementAPI.entity"
)
@SuiteDisplayName("Reimbursement API Functional Tests")
@Suite
public class TestSuite {
}
