package com.pengyd.service.impl;

import com.pengyd.bean.AttdApproveList;
import com.pengyd.dao.AttdApproveListMapper;
import com.pengyd.service.AttdApproveListService;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @Author pengyd
 * @Date 2018/3/22 17:00
 * @function:
 */
@Service
public class AttdApproveListServiceImpl implements AttdApproveListService {

	private Logger logger = Logger.getLogger(this.getClass().getName()); 
	@Resource
	private AttdApproveListMapper attdApproveListMapper;
	
	/**
	 * 将 AttdApproveList 插入到数据库中
	 */
	public ReturnData insert(AttdApproveList attdApproveList) {
		// TODO Auto-generated method stub
		ReturnData rd = new ReturnData();
		try {
			attdApproveListMapper.insert(attdApproveList);
			rd.setCode("OK");
			rd.setMsg("数据插入成功 ");
		}catch(Exception e){
			logger.error(e.getMessage());
			//操作异常,返回错误信息
			rd.setCode("ERROR");rd.setMsg(e.getMessage());
		}
		
		return rd;
	}
	
	/**
	 * 将 AttdApproveList 中的参数 删除数据库中的数据
	 */
	public ReturnData delete(AttdApproveList attdApproveList) {
		// TODO Auto-generated method stub
		ReturnData rd = new ReturnData();
		try {
			attdApproveListMapper.delete(attdApproveList);
			rd.setCode("OK");
			rd.setMsg("数据删除成功 ");
		}catch(Exception e){
			logger.error(e.getMessage());
			//操作异常,返回错误信息
			rd.setCode("ERROR");rd.setMsg(e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 将 AttdApproveList 中的参数 批量删除数据库中的数据
	 */
    public ReturnData deleteBatch(String[] ids) {
		ReturnData rd = new ReturnData();
		try {
		    attdApproveListMapper.deleteBatch(ids);
		    rd.setCode("OK");
		    rd.setMsg("数据删除成功 ");
		} catch (Exception e) {
			logger.error(e.getMessage());
		    rd.setCode("ERROR"); rd.setMsg(e.getMessage());
		}
		return rd;
    }

	/**
	 * 依据 AttdApproveList 中的主键修改数据库中的数据
	 */
	public ReturnData update(AttdApproveList attdApproveList) {
		// TODO Auto-generated method stub
		ReturnData rd = new ReturnData();
		try {
			attdApproveListMapper.update(attdApproveList);
			rd.setCode("OK");
			rd.setMsg("数据删除成功 ");
		}catch(Exception e){
			logger.error(e.getMessage());
			//操作异常,返回错误信息
			rd.setCode("ERROR");rd.setMsg(e.getMessage());
		}
		return rd;
	}
	
	/**
	 * 执行 AttdApproveList 的分页查询
	 */
	public JqGridJsonBean select(String page, String rows, String order_by, AttdApproveList attdApproveList) {
		// TODO Auto-generated method stub
		JqGridJsonBean jgjb = new JqGridJsonBean();
		try {
			int _page = Integer.parseInt(page);
			int _rows = Integer.parseInt(rows);
			//没有order_by 默认主键排序
			if(order_by!=null && !"".equals(order_by)){
				//不变
			}else{
    			order_by =  "id";
			} 
			
			//查询AttdApproveList总数据量
			int count = attdApproveListMapper.selectCount(attdApproveList);
			//根据查询条件查询总页数
			int pages = (count%Integer.parseInt(rows))==0?(count/_rows):((count/_rows)+1);
			List<AttdApproveList> data = attdApproveListMapper.selectData(attdApproveList,_rows,(_page-1)*_rows,order_by);
			jgjb.setPage(_page);// 第几页
			jgjb.setRecords(count);// 总数据量
			jgjb.setTotal(pages);// 总页数
			jgjb.setRoot(data);// 查询数据信息
		}catch(Exception e){
			logger.error(e.getMessage());
			//操作异常,返回错误信息
		}
		return jgjb;
	}
	
	/**
	 * 执行 AttdApproveList 的分页查询 - 关联查询
	 */
	public JqGridJsonBean selectRelationData(String page,String rows,String order_by, AttdApproveList attdApproveList) {
		// TODO Auto-generated method stub
		JqGridJsonBean jgjb = new JqGridJsonBean();
		try {
			int _page = Integer.parseInt(page);
			int _rows = Integer.parseInt(rows);
			//没有order_by 默认主键排序
			if(order_by!=null && !"".equals(order_by)){
				//不变
			}else{
    			order_by =  "id";
			} 
			
			//查询AttdApproveList总数据量
			int count = attdApproveListMapper.selectRelationCount(attdApproveList);
			//根据查询条件查询总页数
			int pages = (count%Integer.parseInt(rows))==0?(count/_rows):((count/_rows)+1);
			List<Map<String, Object>> data = attdApproveListMapper.selectRelationData(attdApproveList,_rows,(_page-1)*_rows,order_by);
			jgjb.setPage(_page);// 第几页
			jgjb.setRecords(count);// 总数据量
			jgjb.setTotal(pages);// 总页数
			jgjb.setRoot(data);// 查询数据信息
		}catch(Exception e){
			logger.error(e.getMessage());
			//操作异常,返回错误信息
		}
		return jgjb;
	}
	
	/**
	 * 执行 AttdApproveList 的查询不分页
	 */
	public ReturnData selectByParam(String order_by, AttdApproveList attdApproveList) {
		// TODO Auto-generated method stub
		ReturnData rd = new ReturnData();
		try {
			//没有order_by 默认主键排序
			if(order_by!=null && !"".equals(order_by)){
				//不变
			}else{
    			order_by =  "id";
			} 

			List<AttdApproveList> data = attdApproveListMapper.selectByParam(attdApproveList,order_by);
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("data", data);
			rd.setCode("OK");
			rd.setData(dataMap);
		}catch(Exception e){
			logger.error(e.getMessage());
			//操作异常,返回错误信息
			rd.setCode("ERROR");rd.setMsg(e.getMessage());
		}
		return rd;
	}
}