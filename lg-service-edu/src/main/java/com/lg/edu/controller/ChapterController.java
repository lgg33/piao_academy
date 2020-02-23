package com.lg.edu.controller;


import com.lg.common.response.ResponseResult;
import com.lg.edu.entity.Chapter;
import com.lg.edu.entity.dto.ChapterDto;
import com.lg.edu.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lg
 * @since 2020-01-13
 */
@Api(tags = "课程章节管理")
@CrossOrigin
@RestController
@RequestMapping("/edu/chapter")
public class ChapterController {

    @Autowired
    ChapterService chapterService;

    @ApiOperation("获取嵌套章节数据")
    @GetMapping("/nested/{id}")
    public ResponseResult getNestedChapter(@PathVariable("id") String id) {
        List<ChapterDto> chapterDtoList = chapterService.chapterDroList(id);
        return ResponseResult.success().data("items", chapterDtoList);
    }

    @ApiOperation("增加章节")
    @PostMapping
    public ResponseResult addChapter(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return ResponseResult.success();
    }

    @ApiOperation("根据Id查询章节")
    @GetMapping("/{id}")
    public ResponseResult getById(@PathVariable("id") String id) {
        Chapter chapter = chapterService.getById(id);
        return ResponseResult.success().data("item", chapter);
    }

    @ApiOperation("根据Id修改章节")
    @PutMapping()
    public ResponseResult updateById(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return ResponseResult.success();
    }

    @ApiOperation("根据Id删除章节")
    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable("id") String id) {
        boolean result = chapterService.deleteChapterById(id);
        if (result) {
            return ResponseResult.success();
        }
        return ResponseResult.error().message("删除失败");
    }
}

