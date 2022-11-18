import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();
        printMenu();
        int userInput = scanner.nextInt();
        while (userInput != 0) {

            if (userInput == 1) {
                monthlyReport.scanningMonthlyReports();
            } else if (userInput == 2) {
                yearlyReport.scanningYearlyReport();
            } else if (userInput == 3) {
                CheckingAmounts.sumOfReports(monthlyReport.monthlyReportTree, yearlyReport.yearlyReport);
            } else if (userInput == 4) {
                monthlyReport.showMonthlyReports();
            } else if (userInput == 5) {
                yearlyReport.infoYearlyReport();
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
            System.out.println(); //это просто что бы в консоли был отступ:)
            printMenu();
            userInput = scanner.nextInt();
        }
        System.out.println("Программа завершена");
    }

    public static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Выйти из приложения");
    }
}
