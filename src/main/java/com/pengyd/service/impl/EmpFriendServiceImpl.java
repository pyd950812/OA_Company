package com.pengyd.service.impl;

import com.pengyd.bean.EmpFriend;
import com.pengyd.dao.EmpFriendMapper;
import com.pengyd.service.EmpFriendService;
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
public class EmpFriendServiceImpl implements EmpFriendService {

	private Logger logger = Logger.getLogger(this.getClass().getName()); 
	@Resource
	private EmpFriendMapper empFriendMapper;
	
	/**
	 * 将 EmpFriend 插入到数据库中
	 */
	public ReturnData insert(EmpFriend empFriend) {
		// TODO Auto-generated method stub
		ReturnData rd = new ReturnData();
		try {
			empFriendMapper.insert(empFriend);
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
	 * 将 EmpFriend 中的参数 删除数据库中的数据
	 */
	public ReturnData delete(EmpFriend empFriend) {
		// TODO Auto-generated method stub
		ReturnData rd = new ReturnData();
		try {
			empFriendMapper.delete(empFriend);
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
	 * 将 EmpFriend 中的参数 批量删除数据库中的数据
	 */
    public ReturnData deleteBatch(String[] ids) {
		ReturnData rd = new ReturnData();
		try {
		    empFriendMapper.deleteBatch(ids);
		    rd.setCode("OK");
		    rd.setMsg("数据删除成功 ");
		} catch (Exception e) {
			logger.error(e.getMessage());
		    rd.setCode("ERROR"); rd.setMsg(e.getMessage());
		}
		return rd;
    }

	/**
	 * 依据 EmpFriend 中的主键修改数据库中的数据
	 */
	public ReturnData update(EmpFriend empFriend) {
		// TODO Auto-generated method stub
		ReturnData rd = new ReturnData();
		try {
			empFriendMapper.update(empFriend);
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
	 * 执行 EmpFriend 的分页查询
	 */
	public JqGridJsonBean select(String page, String rows, String order_by, EmpFriend empFriend) {
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
			
			//查询EmpFriend总数据量
			int count = empFriendMapper.selectCount(empFriend);
			//根据查询条件查询总页数
			int pages = (count%Integer.parseInt(rows))==0?(count/_rows):((count/_rows)+1);
			List<EmpFriend> data = empFriendMapper.selectData(empFriend,_rows,(_page-1)*_rows,order_by);
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
	 * 执行 EmpFriend 的分页查询 - 关联查询
	 */
	public JqGridJsonBean selectRelationData(String page,String rows,String order_by, EmpFriend empFriend) {
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
			
			//查询EmpFriend总数据量
			int count = empFriendMapper.selectRelationCount(empFriend);
			//根据查询条件查询总页数
			int pages = (count%Integer.parseInt(rows))==0?(count/_rows):((count/_rows)+1);
			List<Map<String, Object>> data = empFriendMapper.selectRelationData(empFriend,_rows,(_page-1)*_rows,order_by);
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
	 * 执行 EmpFriend 的查询不分页
	 */
	public ReturnData selectByParam(String order_by, EmpFriend empFriend) {
		// TODO Auto-generated method stub
		ReturnData rd = new ReturnData();
		try {
			//没有order_by 默认主键排序
			if(order_by!=null && !"".equals(order_by)){
				//不变
			}else{
    			order_by =  "id";
			} 

			List<EmpFriend> data = empFriendMapper.selectByParam(empFriend,order_by);
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