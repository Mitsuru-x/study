package model; // モデル層

// カートの1行（商品1件＋数量）を表すモデル
public class CartItem {
    // フィールド
    private int productId; // 商品ID
    private String name;   // 商品名
    private int price;     // 単価
    private int qty;       // 数量

    // コンストラクタ
    public CartItem(int productId, String name, int price, int qty) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    // ゲッター
    public int getProductId() { return productId; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getQty() { return qty; }

    // 数量を1つ増やす（カートで同じ商品を追加したときに使う）
    public void addOne() {
        this.qty++;
    }

    // 数量を任意に変更（必要に応じて）
    public void setQty(int qty) {
        this.qty = qty;
    }

    // 小計を返す（単価 × 数量）
    public int getSubtotal() {
        return price * qty;
    }
}
