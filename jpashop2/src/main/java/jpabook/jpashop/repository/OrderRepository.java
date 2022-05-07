package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final EntityManager em;

	public void save(Order order) {
		em.persist(order);
	}

	public Order findOne(Long id) {
		return em.find(Order.class, id);
	}

	public List<Order> findAllByString(OrderSearch orderSearch) {
//language=JPAQL
		String jpql = "select o From Order o join o.member m";
		boolean isFirstCondition = true;
//주문 상태 검색
		if (orderSearch.getOrderStatus() != null) {
			if (isFirstCondition) {
				jpql += " where";
				isFirstCondition = false;
			} else {
				jpql += " and";
			}
			jpql += " o.status = :status";
		}
//회원 이름 검색
		if (StringUtils.hasText(orderSearch.getMemberName())) {
			if (isFirstCondition) {
				jpql += " where";
				isFirstCondition = false;
			} else {
				jpql += " and";
			}
			jpql += " m.name like :name";
		}
		TypedQuery<Order> query = em.createQuery(jpql, Order.class)
									.setMaxResults(1000); //최대 1000건
		if (orderSearch.getOrderStatus() != null) {
			query = query.setParameter("status", orderSearch.getOrderStatus());
		}
		if (StringUtils.hasText(orderSearch.getMemberName())) {
			query = query.setParameter("name", orderSearch.getMemberName());
		}
		return query.getResultList();
	}

	// V4에 비해 쿼리 성능은 쪼끔 떨어지지만 재사용성이 좋다
	public List<Order> findAllWithMemberDelivery() {
		return em.createQuery("select o from Order o" +
							  " join fetch o.member m" +
							  " join fetch o.delivery d", Order.class)
				 .getResultList();
	}

	// Repository는 가급적이면 순수 엔티티를 조회하는데만 사용하자. 성능최적화를 위해 DTO 조회가 필요할 경우 따로 Repository를 만든다.
//	public List<OrderSimpleQueryDto> findOrderDtos() {
//		return em.createQuery(
//					 "select new jpabook.jpashop.repository.order.simpleQuery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
//					 " from Order o" +
//					 " join o.member m" +
//					 " join o.delivery d", OrderSimpleQueryDto.class)
//				 .getResultList();
//	}
}
