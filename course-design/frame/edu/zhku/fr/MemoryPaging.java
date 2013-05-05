package edu.zhku.fr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.zhku.fr.dao.PageBean;
import edu.zhku.fr.domain.User;

/**
 * 内存分页工具类
 * 
 * @author XJQ
 * @date 2013-5-1
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class MemoryPaging extends Object{
    protected MemoryPaging() {
    }

    /**
     * 内存中进行分页查询
     * @param currentPage
     * @param pageSize
     * @param recordList
     * @return
     */
    public static final PageBean paging(int currentPage, int pageSize, Collection recordList) {
        List list = new ArrayList();
        currentPage = getCurrentPage(currentPage);
        pageSize = getPageSize(pageSize);
        int index = 1;
        int begIndex = (currentPage - 1) * pageSize + 1;
        int endIndex = (begIndex + pageSize) - 1;
        endIndex = endIndex > recordList.size() ? recordList.size() : endIndex; 
        for(Object obj : recordList) {
            if(index >= begIndex && index <= endIndex) {
                list.add(obj);
            }
            ++ index;
        }
        return new PageBean(currentPage, pageSize, getRecordCount(recordList), list);
    }
    
    public static void main(String[] args) {
        List<User> users = new ArrayList<User>();
        for(int i=1; i<11; ++i) {
            User u = new User();
            u.setId((long) i); 
            u.setName("name_" + i);
            users.add(u);
        }
        
        PageBean pb = paging(2, 8, users);
        System.out.println(pb.getRecordList());
    }
    
    /**
     * 分页
     * @param currentPage
     * @param pageSize
     * @param recordList
     * @param sorter
     * @return
     */
    public static final PageBean paging(int currentPage, int pageSize, Collection recordList, Comparator sorter) {
        List list = new ArrayList(recordList);
        Collections.sort(list, sorter);
        return paging(currentPage, pageSize, list);
    }
    
    private static int getRecordCount(Collection<?> recordList) {
        return recordList == null ? 0 : recordList.size();
    }

    public static final int getCurrentPage(int currentPage) {
        return currentPage <= 0 ? 1 : currentPage;
    }
    
    public static final int getPageSize(int pageSize) {
        return pageSize <=0 //
                ? getDefaultPageSize()//
                : pageSize;
    }

    private static int getDefaultPageSize() {
        return ConfigCenter.getConfig(Key.DEFAULT_PAGE_SIZE, int.class);
    }
}