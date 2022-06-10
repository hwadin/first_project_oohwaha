package service;

import dao.IMemberDAO;
import dao.MemberDAO;
import vo.Member;

public class MemberService implements IMemberService {
	private static IMemberDAO dao;

	public MemberService() {
		dao = new MemberDAO();
	}

	@Override
	public Member login(Member member) {
		System.out.println("memberService.login() 진입");
		Member m = dao.findByIdAndPw(member.getId(), member.getPw());
		if (m != null) {
			return m;
		}
		return null;
	}

	@Override
	public Member find(Member member) {
		System.out.println("memberService.find() 진입");
		Member m = dao.findById(member.getId());
		if (m != null) {
			return m;
		}
		return null;
	}

	@Override
	public int join(Member member) {
		System.out.println("memberService.join() 진입");
		int result = dao.save(member);
		return result;
	}

	@Override
	public int update(Member member) {
		System.out.println("memberService.update() 진입");
		int result = dao.update(member);
		return result;
	}

	@Override
	public int delete(Member member) {
		System.out.println("memberService.delete() 진입");
		int result = dao.delete(member);
		return result;
	}

}
