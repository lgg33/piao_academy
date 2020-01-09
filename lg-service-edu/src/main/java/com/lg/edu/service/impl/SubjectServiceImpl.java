package com.lg.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.common.exception.PiaoException;
import com.lg.common.response.ResultCode;
import com.lg.common.utils.ExcelImportHSSFUtil;
import com.lg.edu.entity.Subject;
import com.lg.edu.entity.dto.SubjectDtoFirst;
import com.lg.edu.entity.dto.SubjectDtoTwo;
import com.lg.edu.mapper.SubjectMapper;
import com.lg.edu.service.SubjectService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lg
 * @since 2020-01-06
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public List<String> batchImport(MultipartFile file) {
        ArrayList<String> list = new ArrayList<>();
        try {
            ExcelImportHSSFUtil importHSSFUtil = new ExcelImportHSSFUtil(file.getInputStream());
            HSSFSheet sheet = importHSSFUtil.getSheet();
            int rows = sheet.getPhysicalNumberOfRows();
            if (rows <= 1) {
                list.add("请填写数据");
                return list;
            }
            for (int i = 1; i < rows; i++) {
                HSSFRow row = sheet.getRow(i);
                if (row != null) {
                    String levelOneValue = "";
                    HSSFCell levelOne = row.getCell(0);
                    if (levelOne != null) {
                        levelOneValue = importHSSFUtil.getCellValue(levelOne, levelOne.getCellType());
                        if (StringUtils.isEmpty(levelOneValue)) {
                            list.add("第" + i + "行一级分类为空");
                            continue;
                        }
                    }
                    Subject subject = this.getByTitle(levelOneValue);
                    Subject subjectLevelOne;
                    String parentId;
                    if(subject == null){//创建一级分类
                        subjectLevelOne = new Subject();
                        subjectLevelOne.setTitle(levelOneValue);
                        subjectLevelOne.setSort(0);
                        baseMapper.insert(subjectLevelOne);//添加
                        parentId = subjectLevelOne.getId();
                    }else{
                        parentId = subject.getId();
                    }
                    //二级分类名称
                    String levelTwoValue = "";
                    Cell levelTwoCell = row.getCell(1);
                    if(levelTwoCell == null){
                        list.add("第" + i + "行二级分类为空");
                        continue;
                    }
                    levelTwoValue = importHSSFUtil.getCellValue(levelTwoCell, levelTwoCell.getCellType());
                    Subject subjectSub = this.getSubByTitle(levelTwoValue, parentId);
                    Subject subjectLevelTwo;
                    if(subjectSub == null){//创建二级分类
                        subjectLevelTwo = new Subject();
                        subjectLevelTwo.setTitle(levelTwoValue);
                        subjectLevelTwo.setParentId(parentId);
                        subjectLevelTwo.setSort(0);
                        baseMapper.insert(subjectLevelTwo);//添加
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PiaoException(ResultCode.EXCEL_DATA_ERROR);
        }
        return list;
    }
    /**
     * 根据分类名称查询这个一级分类中否存在
     * @param title
     * @return
     */
    private Subject getByTitle(String title) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
//        queryWrapper.eq("parent_id", "0");
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 根据分类名称和父id查询这个二级分类中否存在
     * @param title
     * @return
     */
    private Subject getSubByTitle(String title, String parentId) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", parentId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SubjectDtoFirst> nestedList() {
        ArrayList<SubjectDtoFirst> list = new ArrayList<>();
        //获取一级分类列表
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        queryWrapper.orderByAsc("sort", "id");
        List<Subject> subjects = baseMapper.selectList(queryWrapper);

        //获取二级分类数据记录
        QueryWrapper<Subject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.ne("parent_id", 0);
        queryWrapper2.orderByAsc("sort", "id");
        List<Subject> subSubjects = baseMapper.selectList(queryWrapper2);
        for (Subject subject : subjects) {
            SubjectDtoFirst first = new SubjectDtoFirst();
            BeanUtils.copyProperties(subject, first);
            list.add(first);

            ArrayList<SubjectDtoTwo> twoArrayList = new ArrayList<>();
            for (Subject subject2 : subSubjects) {
                if (subject.getId().equals(subject2.getParentId())) {
                    SubjectDtoTwo second = new SubjectDtoTwo();
                    BeanUtils.copyProperties(subject2, second);
                    twoArrayList.add(second);
                }
            }
            first.setChildren(twoArrayList);
        }
        return list;
    }

    @Override
    public boolean deleteById(String id) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        List<Subject> subjects = baseMapper.selectList(queryWrapper);
        if (subjects.size() == 0) {
            int i = baseMapper.deleteById(id);
            return i > 0;
        }
        return false;
    }

    //添加一级分类
    @Override
    public boolean saveFirst(Subject subject) {
        subject.setParentId("0");
        Subject byTitle = this.getByTitle(subject.getTitle());
        if (byTitle == null) {
            return this.save(subject);
        }
        return false;
    }

    //添加二级分类
    @Override
    public boolean saveSecond(Subject subject) {
        Subject subByTitle = this.getSubByTitle(subject.getTitle(), subject.getParentId());
        if (subByTitle == null) {
            return this.save(subject);
        }
        return false;
    }
}
