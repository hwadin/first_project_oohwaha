package service;

import java.util.ArrayList;

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
	public Member update(Member member) {
		System.out.println("memberService.update() 진입");
		Member m = dao.update(member);
		if (m != null) {
			return m;
		}
		return null;
	}

	@Override
	public int delete(Member member) {
		System.out.println("memberService.delete() 진입");
		int result = dao.delete(member);
		return result;
	}

	@Override
	public ArrayList<Member> frdList(Member member) {
		System.out.println("memberService.frdList() 진입");
		ArrayList<Member> list = dao.frdList(member);
		return list;
	}

	@Override
	public ArrayList<Member> findId(Member member) {
		System.out.println("memberService.findId() 진입");
		ArrayList<Member> list = dao.findId(member);
		return list;
	}

	@Override
	public int frdAdd(ArrayList<Member> frdAddList) {
		System.out.println("memberService.frdAdd() 진입");
		Member member = frdAddList.get(0);
		Member frnd = dao.findById(frdAddList.get(1).getId());
		int result = dao.frdAdd(member.getNo(), frnd.getNo());
		return result;
	}

}
