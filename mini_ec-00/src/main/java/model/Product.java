package model; // モデル層パッケージ

// 商品1件を表すモデル（JavaBean）
public class Product {
    // フィールド（プライベート変数）
    private int id;       // 商品ID
    private String name;  // 商品名
    private int price;    // 価格
    private int stock;    // 在庫

    // コンストラクタ（全フィールド用）
    public Product(int id, String name, int price, int stock) {
        this.id = id;         // 引数idをフィールドに代入
        this.name = name;     // 引数nameをフィールドに代入
        this.price = price;   // 引数priceをフィールドに代入
        this.stock = stock;   // 引数stockをフィールドに代入
    }

    // コンストラクタ（新規追加時などid未定用）
    public Product(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // ゲッター
    public int getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getStock() { return stock; }

    // セッター（更新時に使うかもしれない）
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(int price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
}
