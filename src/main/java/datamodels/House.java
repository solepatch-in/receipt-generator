package datamodels;

public class House {
    private static String tenantName;
    private static String address;
    private static String panNumber;
    private static String landlordName;

    public static String getTenantName() {
        return tenantName;
    }

    public static void setTenantName(String tenantName) {
        House.tenantName = tenantName;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        House.address = address;
    }

    public static String getPanNumber() {
        return panNumber;
    }

    public static void setPanNumber(String panNumber) {
        House.panNumber = panNumber;
    }

    public static String getLandlordName() {
        return landlordName;
    }

    public static void setLandlordName(String landlordName) {
        House.landlordName = landlordName;
    }
}
