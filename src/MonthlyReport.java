import java.util.ArrayList;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MonthlyReport {

    TreeMap<Integer, TreeMap<String, ArrayList<Double>>> monthlyReportTree = new TreeMap<>();
    static String pathFolderWithMonths = ("resources");
    static String formatMonthlyFiles = "m.\\d{6}.csv";
    String item_name;
    String is_expense;
    double quantity;
    double sum_of_one;
    File monthsOfYear;


    void scanningMonthlyReports() {

        monthsOfYear = new File(pathFolderWithMonths);

        try {
            File[] filesMonthlyReports = monthsOfYear.listFiles((dir, name) -> name.matches(formatMonthlyFiles));

            if (filesMonthlyReports != null && filesMonthlyReports.length != 0 && filesMonthlyReports.length < 13) {

                for (File filesMonthly : filesMonthlyReports) {

                    Integer numberOfMonth = Integer.parseInt(filesMonthly.getName().replaceFirst("m.\\d{4}", "").replaceFirst(".csv", ""));

                    TreeMap<String, ArrayList<Double>> monthlyReports = new TreeMap<>();
                    BufferedReader scannerMonthlyReport = new BufferedReader(new FileReader(filesMonthly));

                    String lineOfMonth;

                    while ((lineOfMonth = scannerMonthlyReport.readLine()) != null) {

                        String[] massiveLinesOfMonth = lineOfMonth.split(",");
                        ArrayList<String> arrayLinesOfMonth = new ArrayList<>();

                        for (int i = 0; i < massiveLinesOfMonth.length; i++) {

                            arrayLinesOfMonth.add(i, massiveLinesOfMonth[i]);
                        }
                        item_name = arrayLinesOfMonth.get(0);
                        is_expense = arrayLinesOfMonth.get(1);
                        ArrayList<Double> incomeAndExpense = new ArrayList<>(2);
                        incomeAndExpense.add(0.0);
                        incomeAndExpense.add(0.0);

                        if (is_expense.equalsIgnoreCase("false")) {

                            quantity = Double.parseDouble(arrayLinesOfMonth.get(2));
                            sum_of_one = Double.parseDouble(arrayLinesOfMonth.get(3));
                            incomeAndExpense.set(0, quantity * sum_of_one);

                        } else if (is_expense.equalsIgnoreCase("true")) {

                            quantity = Double.parseDouble(arrayLinesOfMonth.get(2));
                            sum_of_one = Double.parseDouble(arrayLinesOfMonth.get(3));
                            incomeAndExpense.set(1, quantity * sum_of_one);
                        }
                        monthlyReports.put(item_name, incomeAndExpense);
                        monthlyReportTree.put(numberOfMonth, monthlyReports);
                    }
                }
            } else {

                errorOfMonth();
            }
        } catch (IOException e) {
            errorOfMonth();
        }
        System.out.println("Считывание отчётов по месяцам успешно завершено.");
    }

    void showMonthlyReports() {

        if (monthlyReportTree.size() != 0) {

            for (Integer keyMonthlyReport : monthlyReportTree.keySet()) {

                String maxIncome = null;
                String maxExpanse = null;
                double maxIncomeMonthly = 0;
                double maxExpanseMonthly = 0;

                TreeMap<String, ArrayList<Double>> monthlyReport = monthlyReportTree.get(keyMonthlyReport);

                System.out.println(CheckingAmounts.namesOfMonths(keyMonthlyReport));

                for (String itemNames : monthlyReport.keySet()) {

                    ArrayList<Double> arrayProfitAndExpense = monthlyReport.get(itemNames);

                    if (arrayProfitAndExpense.get(0) > maxIncomeMonthly) {

                        maxIncomeMonthly = arrayProfitAndExpense.get(0);
                        maxIncome = itemNames;

                    } else if (arrayProfitAndExpense.get(1) > maxExpanseMonthly) {

                        maxExpanseMonthly = arrayProfitAndExpense.get(1);
                        maxExpanse = itemNames;
                    }
                }

                System.out.println(" Самый прибыльный товар: " + maxIncome + " на сумму: " + maxIncomeMonthly + "");
                System.out.println(" Самая большая трата: " + maxExpanse + " на сумму: " + maxExpanseMonthly + "");
            }
        } else {
            System.out.println("Перед выводом информации об отчётах по месяцам необходимо считать все месячные отчёты.");
        }
    }

    void errorOfMonth() {

        System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
    }
}