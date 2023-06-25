package admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import admin.dao.AdminCsDao;
import admin.dao.AdminManagerDao;
import admin.dao.AdminMemDao;
import admin.dao.AdminPointDao;
import admin.dao.AdminQnaDao;
import admin.dao.AdminReviewDao;
import dto.Cs;
import dto.Delivery;
import dto.Manager;
import dto.Mem;
import dto.Point;
import dto.Qna;
import dto.ReviewView;

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
	@Autowired
	private AdminPointDao pointDao;
	@Autowired
	private AdminReviewDao reviewDao;

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

	@Transactional
	public boolean regPoint(Point point) {
		boolean b1 = pointDao.regPoint(point);
		boolean b2 = memDao.pointChg(point);
		return b1&&b2;
	}

	public int getPointCnt(String query) {
		return pointDao.getPointCnt(query);
	}

	public List<Point> getPointList(Integer pageNum, String query) {
		return pointDao.getPointList(pageNum, query);
	}

	public Point getPoint(Integer point_number) {
		return pointDao.getPoint(point_number);
	}

	@Transactional
	public boolean pointChg(Point point, Integer cur_point) {
		Mem mem = memDao.getMemId(point.getMem_id());
		boolean b1 = false;
		boolean b2 = false;
		boolean b3 = false;
		if(mem.getMem_point() > cur_point) {
			Point newPoint = new Point();
			newPoint.setMem_id(point.getMem_id());
			newPoint.setPoint_value(cur_point*-1);
			b1 = memDao.pointChg(newPoint);
			b2 = memDao.pointChg(point);
			b3 = pointDao.pointChg(point);
			return b1&&b2&&b3;
		}else {
			b1 = memDao.setPointZero(0, point.getMem_id());
			int diff = mem.getMem_point() - cur_point + point.getPoint_value();
			if(diff>0) {
				b2 = memDao.pointChg(point);
				b3 = pointDao.pointChg(point);
				
				return b1&&b2&&b3;
			}else {
				b3 = pointDao.pointChg(point);
				
				return b1&&b3;
			}
		}
		
	}

	public boolean pointDel(Integer point_number) {
		return pointDao.pointDel(point_number);
	}

	public int reviewCnt(String f, String query, String sd, String ed, String review_state) {
		return reviewDao.reviewCnt(f, query, sd, ed, review_state);
	}

	public List<ReviewView> getReviewList(Integer pageNum, String f, String query, String sd, String ed,
			String review_state) {
		return reviewDao.getReviewList(pageNum, f, query, sd, ed, review_state);
	}


}
