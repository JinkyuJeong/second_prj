package admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin.dao.AdminCsDao;
import admin.dao.AdminManagerDao;
import admin.dao.AdminMemDao;
import admin.dao.AdminQnaDao;
import dto.Cs;
import dto.Delivery;
import dto.Manager;
import dto.Mem;
import dto.Qna;

@Service
public class AdminManageService {
	
	@Autowired
	private AdminQnaDao qnaDao;
	@Autowired
	private AdminCsDao csDao;
	@Autowired
	private AdminManagerDao managerDao;
	@Autowired
	private AdminMemDao memDao;

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

	public int managerCnt(String f, String query) {
		return managerDao.managerCnt(f, query);
	}

	public List<Manager> getManagerList(Integer pageNum, String f, String query) {
		return managerDao.getManagerList(pageNum, f, query);
	}

	public Manager getManager(String manager_id) {
		return managerDao.getManager(manager_id);
	}
	
	public Manager getManager2(String manager_name) {
		return managerDao.getManager2(manager_name);
	}

	public Manager managerLogin(String manager_id, String manager_pass) {
		return managerDao.managerLogin(manager_id, manager_pass);
	}

	public boolean managerChg(Manager manager) {
		return managerDao.managerChg(manager);
	}

	public boolean managerDel(Integer manager_number) {
		return managerDao.managerDel(manager_number);
	}

	public int memCnt(String f, String query) {
		return memDao.memCnt(f, query);
	}

	public List<Mem> getMemList(Integer pageNum, String f, String query) {
		return memDao.getMemList(pageNum, f, query);
	}

	public Mem getMem(Integer mem_number) {
		return memDao.getMem(mem_number);
	}

	public List<Delivery> getDelList(String mem_id) {
		return memDao.getDelList(mem_id);
	}

	public boolean memDel(Integer mem_number) {
		return memDao.memDel(mem_number);
	}

	public int csCnt(String sd, String ed, String query, String cs_state) {
		return csDao.csCnt(sd, ed, query, cs_state);
	}

	public List<Cs> getCsList(Integer pageNum, String sd, String ed, String query, String cs_state) {
		return csDao.getCsList(pageNum, sd, ed, query, cs_state);
	}

	public Cs getCs(Integer cs_number) {
		return csDao.getCs(cs_number);
	}

	public boolean csReply(Cs cs) {
		return csDao.csReply(cs);
	}

	public boolean csDel(Integer cs_number) {
		return csDao.csDel(cs_number);
	}


}
