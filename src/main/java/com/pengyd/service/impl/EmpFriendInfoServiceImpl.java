package com.pengyd.service.impl;

import com.pengyd.bean.EmpFriendInfo;
import com.pengyd.dao.EmpFriendInfoMapper;
import com.pengyd.service.EmpFriendInfoService;
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
public class EmpFriendInfoServiceImpl implements EmpFriendInfoService {

	private Logger logger = Logger.getLogger(this.getClass().getName()); 
	@Resource
	private EmpFriendInfoMapper empFriendInfoMapper;
	
	/**
	 * 将 EmpFriendInfo 插入到数据库中
	 */
	public ReturnData insert(EmpFriendInfo empFriendInfo) {
		// TODO Auto-generated method stub
		ReturnData rd = new ReturnData();
		try {
			empFriendInfoMapper.insert(empFriendInfo);
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
	 * 将 EmpFriendInfo 中的参数 删除数据库中的数据
	 */
	public ReturnData delete(EmpFriendInfo empFriendInfo) {
		// TODO Auto-generated method stub
		ReturnData rd = new ReturnData();
		try {
			empFriendInfoMapper.delete(empFriendInfo);
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
	 * 将 EmpFriendInfo 中的参数 批量删除数据库中的数据
	 */
    public ReturnData deleteBatch(String[] ids) {
		ReturnData rd = new ReturnData();
		try {
		    empFriendInfoMapper.deleteBatch(ids);
		    rd.setCode("OK");
		    rd.setMsg("数据删除成功 ");
		} catch (Exception e) {
			logger.error(e.getMessage());
		    rd.setCode("ERROR"); rd.setMsg(e.getMessage());
		}
		return rd;
    }

	/**
	 * 依据 EmpFriendInfo 中的主键修改数据库中的数据
	 */
	public ReturnData update(EmpFriendInfo empFriendInfo) {
		// TODO Auto-generated method stub
		ReturnData rd = new ReturnData();
		try {
			empFriendInfoMapper.update(empFriendInfo);
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
	 * 执行 EmpFriendInfo 的分页查询
	 */
	public JqGridJsonBean select(String page, String rows, String order_by, EmpFriendInfo empFriendInfo) {
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
			
			//查询EmpFriendInfo总数据量
			int count = empFriendInfoMapper.selectCount(empFriendInfo);
			//根据查询条件查询总页数
			int pages = (count%Integer.parseInt(rows))==0?(count/_rows):((count/_rows)+1);
			List<EmpFriendInfo> data = empFriendInfoMapper.selectData(empFriendInfo,_rows,(_page-1)*_rows,order_by);
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
	 * 执行 EmpFriendInfo 的分页查询 - 关联查询
	 */
	public JqGridJsonBean selectRelationData(String page,String rows,String order_by, EmpFriendInfo empFriendInfo) {
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
			
			//查询EmpFriendInfo总数据量
			int count = empFriendInfoMapper.selectRelationCount(empFriendInfo);
			//根据查询条件查询总页数
			int pages = (count%Integer.parseInt(rows))==0?(count/_rows):((count/_rows)+1);
			List<Map<String, Object>> data = empFriendInfoMapper.selectRelationData(empFriendInfo,_rows,(_page-1)*_rows,order_by);
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
	 * 执行 EmpFriendInfo 的查询不分页
	 */
	public ReturnData selectByParam(String order_by, EmpFriendInfo empFriendInfo) {
		// TODO Auto-generated method stub
		ReturnData rd = new ReturnData();
		try {
			//没有order_by 默认主键排序
			if(order_by!=null && !"".equals(order_by)){
				//不变
			}else{
    			order_by =  "id";
			} 

			List<EmpFriendInfo> data = empFriendInfoMapper.selectByParam(empFriendInfo,order_by);
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