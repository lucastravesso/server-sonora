package br.com.spring.ecommerce.util;

import java.util.List;

import br.com.spring.ecommerce.dto.CartProductsDTO;

public class FormatPrice {
	
	private static double totalPrice = 0.00;
	
	public static Double getTotalPrice(List<CartProductsDTO> cartProducts) {

		totalPrice = 0.00;
		cartProducts.forEach(c -> {
		
			totalPrice = totalPrice + c.getPrice();

		});
		String formatPrice = String.format("%.2f", totalPrice);
		return Double.parseDouble(formatPrice.replace(',', '.'));
	}
	
	public static Double getPrice(Double price) {

		String formatPrice = String.format("%.2f", price);
		return Double.parseDouble(formatPrice.replace(',', '.'));
	}
}