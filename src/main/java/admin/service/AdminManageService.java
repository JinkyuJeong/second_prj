package admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin.dao.AdminManagerDao;
import admin.dao.AdminQnaDao;
import dto.Manager;
import dto.Qna;

@Service
public class AdminManageService {
	
	@Autowired
	private AdminQnaDao qnaDao;
	@Autowired
	private AdminManagerDao managerDao;

	public boolean regQna(Qna qna) {
		return qnaDao.regQna(qna);
	}

	public int getQnaCnt(String type) {
		return qnaDao.getQnaCnt(type);
	}

	public List<Qna> getQnaList(Integer pageNum, String type) {
		return qnaDao.getQnaList(pageNum, type);
	}

	public Qna getQna(Integer qna_number) {
		return qnaDao.getQna(qna_number);
	}

	public void addQnaHits(Integer qna_number) {
		qnaDao.addQnaHits(qna_number);
	}

	public void qnaChg(Qna qna) {
		qnaDao.qnaChg(qna);
	}

	public void qnaDel(Integer qna_number) {
		qnaDao.qnaDel(qna_number);
	}

	public boolean managerReg(Manager manager) {
		return managerDao.managerReg(manager);
	}

	public int managerCnt(String query) {
		return managerDao.managerCnt(query);
	}

	public List<Manager> getManagerList(Integer pageNum, String query) {
		return managerDao.getManagerList(pageNum, query);
	}

	public Manager getManager(String manager_id) {
		return managerDao.getManager(manager_id);
	}

	public Manager managerLogin(String manager_id, String manager_pass) {
		return managerDao.managerLogin(manager_id, manager_pass);
	}

}
