import java.util.ArrayList;
import java.util.TreeMap;

public class CheckingAmounts {

    static void sumOfReports(TreeMap<Integer, TreeMap<String, ArrayList<Double>>> monthlyReport, TreeMap<Integer, ArrayList<Double>> yearlyReport) {

        TreeMap<Integer, ArrayList<Double>> checkSumMonthlyReports = new TreeMap<>();

        int monthDifferFrom;

        if ((monthlyReport.size() != 0) && (yearlyReport.size() != 0)) {

            for (Integer keyMonthlyReport : monthlyReport.keySet()) {

                TreeMap<String, ArrayList<Double>> monthlyReportTree = monthlyReport.get(keyMonthlyReport);

                double monthlySumIncome = 0;
                double monthlySumExpenses = 0;

                for (String keyMonthlyReports : monthlyReportTree.keySet()) {

                    ArrayList<Double> IncomeAndExpense = monthlyReportTree.get(keyMonthlyReports);

                    monthlySumIncome += IncomeAndExpense.get(0);
                    monthlySumExpenses += IncomeAndExpense.get(1);

                }
                ArrayList<Double> MonthlySum = new ArrayList<>(2);

                MonthlySum.add(0.0);
                MonthlySum.add(0.0);

                MonthlySum.set(0, monthlySumIncome);
                MonthlySum.set(1, monthlySumExpenses);

                checkSumMonthlyReports.put(keyMonthlyReport, MonthlySum);
            }

            int revisedReports = 0;

            for (Integer keyMonthlyReport : checkSumMonthlyReports.keySet()) {

                boolean revised = false;

                ArrayList<Double> arrayMonthlySums = checkSumMonthlyReports.get(keyMonthlyReport);

                for (Integer keyYearlyReportMap : yearlyReport.keySet()) {

                    ArrayList<Double> arrayYearlySums = yearlyReport.get(keyYearlyReportMap);

                    if ((keyMonthlyReport.equals(keyYearlyReportMap)) && (arrayMonthlySums.get(0).equals(arrayYearlySums.get(0))) &&
                            (arrayMonthlySums.get(1).equals(arrayYearlySums.get(1)))) {

                        revised = true;

                        revisedReports += 1;

                    }
                }
                monthDifferFrom = keyMonthlyReport;

                if (!revised) {

                    System.out.println("Отчет за " + namesOfMonths(monthDifferFrom) + " не соответствует отчету за год.");

                } else if (revisedReports == checkSumMonthlyReports.size()) {

                    System.out.println("Сверка отчётов успешно завершена, ошибки отсутствуют.");

                }
            }
        } else {
            System.out.println("Перед сверкой отчётов необходимо считать:");
            System.out.println("1 - все месячные отчёты;");
            System.out.println("2 - годовой отчёт.");
        }
    }

    public static String namesOfMonths(Integer month) {

        String nameOfMonths = null;

        switch (month) {
            case 1: nameOfMonths = "Январь";
                break;
            case 2: nameOfMonths = "Февраль";
                break;
            case 3: nameOfMonths = "Март";
                break;
        }
        return nameOfMonths;
    }
}
