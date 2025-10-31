package dao; // DAO層

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.CartItem;

/** 注文の作成（orders + order_items へのINSERT と在庫更新を1トランザクションで） */
public class OrderDAO {

    /**
     * @return 生成された注文ID
     * @throws SQLException 在庫不足/DBエラー時（ロールバック済みで再送出）
     */
    public int create(int userId, List<CartItem> items) throws SQLException {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("カートが空です。");
        }

        final String INSERT_ORDER =
                "INSERT INTO orders(user_id, total) VALUES(?, ?)";
        final String INSERT_ITEM =
                "INSERT INTO order_items(order_id, product_id, qty, price) VALUES(?, ?, ?, ?)";
        final String DEC_STOCK =
                "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";

        // 合計金額の算出
        final int total = items.stream().mapToInt(i -> i.getPrice() * i.getQty()).sum();

        Connection con = null;
        try {
            con = DB.get();
            con.setAutoCommit(false); // --- TX開始 ---

            // 注文ヘッダINSERT（自動採番ID取得）
            int orderId;
            try (PreparedStatement psOrder =
                         con.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
                psOrder.setInt(1, userId);
                psOrder.setInt(2, total);
                psOrder.executeUpdate();

                try (ResultSet keys = psOrder.getGeneratedKeys()) {
                    if (!keys.next()) throw new SQLException("注文IDの取得に失敗しました。");
                    orderId = keys.getInt(1);
                }
            }

            // 明細INSERT + 在庫更新をバッチで
            try (PreparedStatement psItem = con.prepareStatement(INSERT_ITEM);
                 PreparedStatement psStock = con.prepareStatement(DEC_STOCK)) {

                for (CartItem it : items) {
                    // 明細
                    psItem.setInt(1, orderId);
                    psItem.setInt(2, it.getProductId());
                    psItem.setInt(3, it.getQty());
                    psItem.setInt(4, it.getPrice());
                    psItem.addBatch();

                    // 在庫
                    psStock.setInt(1, it.getQty());        // 減らす数量
                    psStock.setInt(2, it.getProductId());  // 商品ID
                    psStock.setInt(3, it.getQty());        // 条件: 在庫>=数量
                    psStock.addBatch();
                }

                psItem.executeBatch();

                int[] stockRes = psStock.executeBatch();
                for (int r : stockRes) {
                    // 0行更新 or 失敗 なら在庫不足（SUCCESS_NO_INFOはOK）
                    if (r == 0 || r == Statement.EXECUTE_FAILED) {
                        con.rollback();
                        throw new SQLException("在庫不足です。いずれかの商品が在庫切れです。");
                    }
                }
            }

            con.commit(); // --- TX確定 ---
            return orderId;

        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ignore) {}
            }
            throw e; // 呼び元へ再送
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ignore) {}
            }
        }
    }
}
