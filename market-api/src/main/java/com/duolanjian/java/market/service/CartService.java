package com.duolanjian.java.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duolanjian.java.market.bean.Cart;
import com.duolanjian.java.market.mapper.CartMapper;

@Transactional(rollbackFor = Exception.class)
@Service
public class CartService {

	@Autowired
	private CartMapper cartMapper;

	public List<Cart> selectAll(Long id) {
		return cartMapper.selectAll(id);
	}
	
	public Cart selectOne(Long id){
		return cartMapper.selectOne(id);
	}

	public void update(Cart cart) {
		cartMapper.update(cart);
	}

	public void insert(Cart cart) {
		cartMapper.insert(cart);
	}

	public void delete(Long id) {
		cartMapper.delete(id);
	}

	public List<Long> selectShopId(Long id) {
		return cartMapper.selectShopId(id);
	}

}
