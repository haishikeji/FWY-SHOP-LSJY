package cn.px.zly.module.msg.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.zly.module.msg.dao.MessageDAO;
import cn.px.zly.module.msg.entity.Message;
import cn.px.zly.module.msg.ienum.MessageActionEnum;
import cn.px.zly.module.msg.ienum.MessageSrcTypeEnum;
import cn.px.zly.module.msg.ienum.MessageStatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息通知数据库操作
 *
 * @author 品讯科技
 * 2020-11-24 下午4:26
 */
@Repository
public class MessageDAOImpl extends BaseDAOImpl<MessageDAOImpl.MessageMapper, Message> implements MessageDAO {

    @Override
    public List<Message> listMessage(MessageActionEnum action, long userId, long lastMessageId) {
        LambdaQueryWrapper<Message> queryWrapper = Wrappers.<Message>lambdaQuery()
                .eq(Message::getAction, action)
                .eq(Message::getUserId, userId);
        if(lastMessageId > 0) {
            queryWrapper.lt(Message::getMessageId, lastMessageId);
        }
        queryWrapper.orderByDesc(Message::getMessageId);
        queryWrapper.last("LIMIT 10");
        return super.list(queryWrapper);
    }

    @Override
    public List<Message> listMessageBySrc(MessageSrcTypeEnum srcType, long userId, long lastMessageId) {
        LambdaQueryWrapper<Message> queryWrapper = Wrappers.<Message>lambdaQuery()
                .eq(Message::getSrcType, srcType)
                .eq(Message::getUserId, userId);
        if(lastMessageId > 0) {
            queryWrapper.lt(Message::getMessageId, lastMessageId);
        }
        queryWrapper.orderByDesc(Message::getMessageId);
        queryWrapper.last("LIMIT 10");
        return super.list(queryWrapper);
    }

    @Override
    public List<Message> queryBySrcIdAndAction(MessageSrcTypeEnum srcType, Long srcId,
                                         MessageActionEnum action, Long userId,
                                         Long senderId) {
        QueryWrapper<Message> qw = new QueryWrapper<>();
        qw.lambda().eq(Message::getSrcType,srcType)
                .eq(Message::getSrcId,srcId)
                .eq(Message::getAction,action)
                .eq(Message::getUserId,userId)
                .eq(Message::getSenderId,senderId);
        return list(qw);
    }

    @Override
    public void readMsg(long messageId, MessageActionEnum type, long userId) {
        LambdaUpdateWrapper<Message> uw = Wrappers.<Message>lambdaUpdate();
        uw.set(Message::getStatus, MessageStatusEnum.READ)
                .eq(Message::getStatus, MessageStatusEnum.UNREAD)
                .eq(Message::getAction,type)
                .eq(Message::getUserId,userId)
                .le(Message::getMessageId,messageId);
        this.update(uw);
    }

    @Override
    public void readSysMsg(long messageId, long userId) {
        LambdaUpdateWrapper<Message> uw = Wrappers.<Message>lambdaUpdate();
        uw.set(Message::getStatus, MessageStatusEnum.READ)
                .eq(Message::getStatus, MessageStatusEnum.UNREAD)
                .eq(Message::getSrcType,MessageSrcTypeEnum.System)
                .eq(Message::getUserId,userId)
                .le(Message::getMessageId,messageId);
        this.update(uw);
    }

    @Override
    public int queryUnreadMsgCount(Long userId, MessageActionEnum msgAction, MessageSrcTypeEnum msgSrc) {
        LambdaQueryWrapper<Message> qw = Wrappers.<Message>lambdaQuery();
        qw.eq(Message::getUserId,userId)
            .eq(Message::getStatus, MessageStatusEnum.UNREAD);
        if(msgAction != null){
            qw.eq(Message::getAction, msgAction);
        }
        if(msgSrc != null){
            qw.eq(Message::getSrcType,msgSrc);
        }
        return this.count(qw);
    }

    @Mapper
    @Component
    interface MessageMapper extends BaseMapper<Message> {
    }
}
