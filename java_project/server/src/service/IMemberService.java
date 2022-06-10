package service;

import java.util.ArrayList;

import vo.Member;

// MemberDAO 활용한 Member 관련 기능
public interface IMemberService {

	// 로그인 프로세스
	Member login(Member member);

	// 아이디로만 조회(중복검색, 사람 찾기);
	Member find(Member member);

	int join(Member member);

	int update(Member member);

	int delete(Member member);

	ArrayList<Member> frdList(Member member);
}
