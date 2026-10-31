package pages;

public enum MyInfo {
    PERSONAL_DETAILS("Personal Details"),
    CONTACT_DETAILS("Contact Details"),
    EMERGENCY_CONTACTS("Emergency Contacts"),
    DEPENDENTS("Dependents"),
    IMMIGRATION("Immigration"),
    JOB("Job"),
    SALARY("Salary"),
    REPORT_TO("Report-to"),
    QUALIFICATIONS("Qualifications"),
    MEMBERSHIPS("Memberships");

    private final String displayName;

    MyInfo(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
