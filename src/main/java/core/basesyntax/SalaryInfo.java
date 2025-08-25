package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalaryInfo {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int DATE_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int HOURS_INDEX = 2;
    private static final int RATE_INDEX = 3;

    public String getSalaryInfo(String[] names, String[] data,
                                String dateFrom, String dateTo) {
        LocalDate from = LocalDate.parse(dateFrom, FORMATTER);
        LocalDate to = LocalDate.parse(dateTo, FORMATTER);

        int[] salaries = new int[names.length];

        for (int i = 0; i < names.length; i++) {
            String employee = names[i];

            for (String record : data) {
                String[] parts = record.split(" ");
                if (parts.length == 4) {
                    try {
                        LocalDate workDate = LocalDate.parse(parts[DATE_INDEX], FORMATTER);
                        String recordName = parts[NAME_INDEX];
                        int hours = Integer.parseInt(parts[HOURS_INDEX]);
                        int rate = Integer.parseInt(parts[RATE_INDEX]);

                        if (recordName.equals(employee)
                                && (workDate.isEqual(from) || workDate.isAfter(from))
                                && (workDate.isEqual(to) || workDate.isBefore(to))) {
                            salaries[i] += hours * rate;
                        }
                    } catch (Exception e) {
                        System.err.println("Invalid record skipped: " + record);
                    }
                }
            }
        }

        StringBuilder report = new StringBuilder();
        String lineSep = System.lineSeparator();
        report.append("Report for period ")
                .append(dateFrom)
                .append(" - ")
                .append(dateTo)
                .append(lineSep);

        for (int i = 0; i < names.length; i++) {
            report.append(names[i])
                    .append(" - ")
                    .append(salaries[i])
                    .append(lineSep);
        }

        return report.toString().trim();
    }
}
