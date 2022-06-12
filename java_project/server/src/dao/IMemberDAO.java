package dao;

import java.util.ArrayList;

import vo.Member;

// DBHelper 사용해서 DB 접근해서 CRUD(Create, Read, Update, Delete)기능
public interface IMemberDAO {

	// 쓰기
	int save(Member member);

	// 검색
	Member findById(String id);

	Member findByIdAndPw(String id, String pw);

	int update(Member member);

	int delete(Member member);

	ArrayList<Member> frdList(Member member);

	ArrayList<Member> findId(Member member);

}
