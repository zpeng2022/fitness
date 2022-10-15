package com.yiie.common.service;


import com.yiie.entity.GymComments;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;

import java.util.List;

public interface GymCommentService {
    PageVO<GymComments> pageInfo(GymCommentPageReqVO vo);
    void addGymComments(GymCommentAddReqVO vo);
    void updateGymCommentsInfo(GymCommentUpdateReqVO vo);
    void deletedGymComments(List<String> userIds);

    void readGymComments(GymComments gymComments);

    GymComments getByCommentId(String commentId);

    void readGymCommentsById(String commentId);
}
