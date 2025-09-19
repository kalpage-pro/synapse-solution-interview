package lottery.results;

import java.sql.*;
import lottery.results.entity.LotteryResult;

public class DBUtil {
    private static final String URL = "jdbc:mysql://13.234.242.165:3306/a1";
    private static final String USER = "iDevice";
    private static final String PASSWORD = " iDevice_123456"; // Set your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void saveLotteryResult(LotteryResult result) throws SQLException {
        String sql = "INSERT INTO lottery_results (lottery_name, result_number, draw_date, scraped_date, source_url, additional_info) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, result.getLotteryName());
            stmt.setString(2, result.getResultNumber());
            stmt.setObject(3, result.getDrawDate());
            stmt.setObject(4, result.getScrapedDate());
            stmt.setString(5, result.getSourceUrl());
            stmt.setString(6, result.getAdditionalInfo());
            stmt.executeUpdate();
        }
    }

    public static LotteryResult getLotteryResultById(long id) throws SQLException {
        String sql = "SELECT * FROM lottery_results WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LotteryResult result = new LotteryResult();
                    result.setId(rs.getLong("id"));
                    result.setLotteryName(rs.getString("lottery_name"));
                    result.setResultNumber(rs.getString("result_number"));
                    result.setDrawDate(rs.getTimestamp("draw_date") != null ? rs.getTimestamp("draw_date").toLocalDateTime() : null);
                    result.setScrapedDate(rs.getTimestamp("scraped_date").toLocalDateTime());
                    result.setSourceUrl(rs.getString("source_url"));
                    result.setAdditionalInfo(rs.getString("additional_info"));
                    return result;
                }
            }
        }
        return null;
    }
}
