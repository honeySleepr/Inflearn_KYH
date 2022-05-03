package jpabook.jpashop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Delivery {

	@Id @GeneratedValue
	private Long id;

	private String city;
	private String street;
	private String zipcode;
	private DeliveryStatus status;

	// Delivery(FK)-Order 1대1 양방향으로 하고 싶은 경우!
	@OneToOne(mappedBy = "delivery")
	private Order order;
}