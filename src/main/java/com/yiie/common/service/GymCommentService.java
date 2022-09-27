package com.yiie.common.service;


import com.yiie.entity.GymComments;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;

import java.util.List;

public interface GymCommentService {
    PageVO<GymComments> h5PageInfo(GymCommentPageReqVO vo);
    PageVO<GymComments> pageInfo(GymCommentPageReqVO vo);
    void addGymComments(GymCommentAddReqVO vo);
    void updateGymCommentsInfo(GymCommentUpdateReqVO vo);
    void deletedGymComments(List<String> userIds);
}
