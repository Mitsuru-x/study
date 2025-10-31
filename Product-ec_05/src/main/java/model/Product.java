package model;

import java.math.BigDecimal;

public class Product {
	private int id;
	private String name;
	private BigDecimal price;
	public Product() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public Product(int id,String name,BigDecimal price) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.id=id;
		this.name=name;
		this.price=price;
	}
	
	// [独自] 商品ID取得
    public int getId() { return id; }
    // [独自] 商品ID設定
    public void setId(int id) { this.id = id; }

    // [独自] 商品名取得
    public String getName() { return name; }
    // [独自] 商品名設定
    public void setName(String name) { this.name = name; }

    // [独自] 価格取得
    public BigDecimal getPrice() { return price; }
    // [独自] 価格設定
    public void setPrice(BigDecimal price) { this.price = price; }
}
