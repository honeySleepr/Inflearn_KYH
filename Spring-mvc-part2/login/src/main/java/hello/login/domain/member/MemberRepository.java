package hello.login.domain.member;

import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class MemberRepository {

	private static Map<Long, Member> store = new HashMap<>();
	private static long sequence = 0L;

	public Member save(Member member) {
		member.setId(++sequence);
		log.info("save : member = {}", member);
		store.put(member.getId(), member);
		return member;
	}

	public Member findById(Long id) {
		return store.get(id);
	}

	public List<Member> findAll() {
		return new ArrayList<>(store.values());
	}

	public Optional<Member> findByLoginId(String loginId) {
		return findAll().stream()
			.filter(member -> member.getLoginId().equals(loginId))
			.findFirst();
	}

	public void clearStore() {
		store.clear();
	}
}