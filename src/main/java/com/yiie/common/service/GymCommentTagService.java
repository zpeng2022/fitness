package com.yiie.common.service;

import com.yiie.entity.Gym;
import com.yiie.entity.GymCustomTags;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;

import java.util.List;

public interface GymCommentTagService {
    PageVO<GymCustomTags> pageInfo(GymCommentTagPageReqVO vo);
    void addGymCustomTags(GymCommentTagAddReqVO vo);
    void updateGymCustomTagsInfo(GymCommentTagUpdateReqVO vo);
    void deletedGymCustomTags(List<String> userIds);
}
