package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
	private int id;
	private String name;
	private BigDecimal price;
	private LocalDateTime createdAt;
	public Product() {}
	
	public Product(int id, String name, BigDecimal price, LocalDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.createdAt = createdAt;
	}
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public BigDecimal getPrice() { return price; }
	public void setPrice(BigDecimal price) { this.price = price; }
	public LocalDateTime getCreatedAt() { return createdAt; }
	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt;
	}
}
