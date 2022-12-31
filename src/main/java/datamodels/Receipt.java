package datamodels;

import java.util.Calendar;
import java.util.List;

public class Receipt {
    private static String rentAmount;
    private Calendar fromDate;
    private Calendar toDate;
    private List<Rent> monthlyRent;

    public static String getRentAmount() {
        return rentAmount;
    }

    public static void setRentAmount(String rentAmount) {
        Receipt.rentAmount = rentAmount;
    }

    public Calendar getFromDate() {
        return fromDate;
    }

    public void setFromDate(Calendar fromDate) {
        this.fromDate = fromDate;
    }

    public Calendar getToDate() {
        return toDate;
    }

    public void setToDate(Calendar toDate) {
        this.toDate = toDate;
    }

    public static class Rent {
        private static String rentAmount;
        private static String month;

        public static String getRentAmount() {
            return rentAmount;
        }

        public static void setRentAmount(String rentAmount) {
            Rent.rentAmount = rentAmount;
        }

        public static String getMonth() {
            return month;
        }

        public static void setMonth(String month) {
            Rent.month = month;
        }

    }
}
