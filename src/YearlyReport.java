import java.util.ArrayList;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class YearlyReport {

    TreeMap<Integer, ArrayList<Double>> yearlyReport = new TreeMap<>();
    static String pathFolderWithYears = ("resources");
    static String formatYearlyFiles = "y.\\d{4}.csv";
    static String fileYearName;
    Integer nameMonth;
    double quantity;
    String is_expense;
    int Year;
    File reportsOfYear;


    void scanningYearlyReport() {

        reportsOfYear = new File(pathFolderWithYears);
        try {
            File[] filesYearlyReports = reportsOfYear.listFiles((dir, name) -> name.matches(formatYearlyFiles));

            if (filesYearlyReports != null && filesYearlyReports.length == 1) {

                fileYearName = filesYearlyReports[0].getName();
                Year = Integer.parseInt(fileYearName.replaceFirst("y.", "").replaceFirst(".csv", ""));
                BufferedReader scannerYearlyReport = new BufferedReader(new FileReader(filesYearlyReports[0]));

                ArrayList<Double> incomeAndExpense = new ArrayList<>(2);
                incomeAndExpense.add(0.0);
                incomeAndExpense.add(0.0);

                String lineOfYear;

                int total = 1;

                while ((lineOfYear = scannerYearlyReport.readLine()) != null) {

                    String[] massiveLinesOfYear = lineOfYear.split(",");
                    ArrayList<String> arrayLinesOfYear = new ArrayList<>();

                    for (int i = 0; i < massiveLinesOfYear.length; i++) {
                        arrayLinesOfYear.add(i, massiveLinesOfYear[i]);
                    }
                    if (arrayLinesOfYear.get(0).matches("\\d\\d")) {

                        nameMonth = Integer.parseInt(arrayLinesOfYear.get(0));
                        quantity = Double.parseDouble(arrayLinesOfYear.get(1));
                        is_expense = arrayLinesOfYear.get(2);

                        if (is_expense.equalsIgnoreCase("false")) {

                            incomeAndExpense.set(0, quantity);

                        } else if (is_expense.equalsIgnoreCase("true")) {

                            incomeAndExpense.set(1, quantity);
                        }
                        if (total % 2 == 0) {

                            yearlyReport.put(nameMonth, incomeAndExpense);
                            incomeAndExpense = new ArrayList<>(2);
                            incomeAndExpense.add(0.0);
                            incomeAndExpense.add(0.0);
                        }
                        total++;
                    }
                }
            } else {
                errorOfYear();
            }
        } catch (IOException e) {
            errorOfYear();
        }
        System.out.println("Считывание годового отчета успешно завершено.");
    }

    void infoYearlyReport() {

        ArrayList<Double> infoIncomeAndExpense;

        if (yearlyReport.size() != 0) {

            System.out.println(Year + " год");

            double sumIncome = 0;
            double sumExpenses = 0;

            for (Integer keyYearlyReportMap : yearlyReport.keySet()) {

                infoIncomeAndExpense = yearlyReport.get(keyYearlyReportMap);

                double profitMonthlyReport = infoIncomeAndExpense.get(0) - infoIncomeAndExpense.get(1);
                sumIncome += infoIncomeAndExpense.get(0);
                sumExpenses += infoIncomeAndExpense.get(1);

                System.out.println(" Прибыль за " + CheckingAmounts.namesOfMonths(keyYearlyReportMap) + ": " + profitMonthlyReport + ";");
            }

            double averageIncome = sumIncome / yearlyReport.size();
            double averageExpense = sumExpenses / yearlyReport.size();

            System.out.println(" Средние доходы в месяц за год: " + averageIncome + ";");
            System.out.println(" Средние расходы в месяц за год: " + averageExpense + ";");

        } else {
            System.out.println("Перед выводом информации об отчёте за год необходимо считать годовой отчёт.");
        }
    }

    void errorOfYear() {

        System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
    }
}