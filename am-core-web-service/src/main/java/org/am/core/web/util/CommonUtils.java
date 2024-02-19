package org.am.core.web.util;

public final class CommonUtils {
    private CommonUtils() {}

    public static String getFullName(String firstName, String lastName, String secondLastName) {
        StringBuilder fullName = new StringBuilder();
        fullName.append(firstName)
                .append(" ")
                .append(lastName);
        if (secondLastName != null && !secondLastName.isEmpty()) {
            fullName.append(" ")
                    .append(secondLastName);
        }
        return fullName.toString();
    }
}
