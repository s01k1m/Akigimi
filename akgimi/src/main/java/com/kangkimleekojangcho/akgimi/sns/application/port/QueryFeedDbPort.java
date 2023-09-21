package com.kangkimleekojangcho.akgimi.sns.application.port;

import com.kangkimleekojangcho.akgimi.sns.application.response.BriefFeedInfo;
import com.kangkimleekojangcho.akgimi.sns.application.response.BriefReceiptInfo;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;

import java.util.List;
import java.util.Optional;

public interface QueryFeedDbPort {

    Optional<Feed> findById(Long feedId);

    List<BriefFeedInfo> findByUser_IdAndLastFeedIdAndNumberOfFeed(Long userId, Long aLong, Integer integer);

    List<BriefReceiptInfo> findReceiptByUser_IdAndLastReceiptIdAndNumberOfReceipt(
                    Long userId,
                    Long lastReceiptId,
                    Integer numberOfReceipt
    );
}
