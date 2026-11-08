package basePackage.support;

import basePackage.base.BaseTest;
import pages.AddEmployeePage;
import pages.PimPage;

import java.net.URI;
import java.util.UUID;

/** One generated employee per invocation; only this identity may be cleaned up. */
public final class EmployeeFixture extends BaseTest implements AutoCloseable {
    private final String employeeId = "ci" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    private final String firstName;
    private final String lastName = "Run" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    private boolean creationAttempted;
    private boolean savedSuccessfully;

    public EmployeeFixture() {
        this("");
    }

    public EmployeeFixture(String firstName) {
        this.firstName = firstName.isBlank() ? "Automation" : firstName;
    }

    public void create(PimPage pimPage) {
        AddEmployeePage addEmployee = pimPage.clickAddEmployeeLink();
        addEmployee.enterEmployeeId(employeeId);
        creationAttempted = true;
        addEmployee.addEmployee(firstName, "", lastName);
        // Capture success before the toast disappears, then wait for the saved profile.
        savedSuccessfully = addEmployee.isSuccessMessageVisible();
        addEmployee.getSavedEmployeeNumber();
    }

    public String firstName() {
        return firstName;
    }

    public String fullName() {
        return firstName + " " + lastName;
    }

    public String email() {
        return employeeId + "@example.com";
    }

    public boolean savedSuccessfully() {
        return savedSuccessfully;
    }

    public PimPage openEmployeeList() {
        URI origin = URI.create(config.loginUrl());
        wd.get(origin.resolve("/web/index.php/pim/viewEmployeeList").toString());
        return new PimPage();
    }

    public boolean delete() {
        return openEmployeeList().deleteCreatedEmployee(employeeId, firstName, lastName);
    }

    @Override
    public void close() {
        if (creationAttempted && wd != null) {
            delete();
        }
    }
}
