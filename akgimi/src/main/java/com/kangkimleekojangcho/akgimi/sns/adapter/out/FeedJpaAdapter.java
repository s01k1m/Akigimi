package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.sns.application.port.CommandFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.QueryFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.response.BriefFeedInfo;
import com.kangkimleekojangcho.akgimi.sns.application.response.BriefReceiptInfo;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FeedJpaAdapter implements CommandFeedDbPort, QueryFeedDbPort {

    private final FeedJpaRepository feedJpaRepository;
    private final FeedQuerydslRepository feedQuerydslRepository;

    @Override
    public Feed save(Feed feed) {
        return feedJpaRepository.save(feed);
    }

    @Override
    public Optional<Feed> findById(Long feedId) {
        return feedJpaRepository.findById(feedId);
    }

    @Override
    public List<BriefFeedInfo> findByUser_IdAndLastFeedIdAndNumberOfFeed
            (
                    Long userId, Long lastFeedId, Integer numberOfFeed
            ) {
        return feedQuerydslRepository.findByUser_IdAndLastFeedIdAndNumberOfFeed(userId, lastFeedId, numberOfFeed);
    }

    @Override
    public List<BriefReceiptInfo> findReceiptByUser_IdAndLastReceiptIdAndNumberOfReceipt(Long userId,  Long receiptOwnerId, Long lastReceiptId, Integer numberOfReceipt) {
        return feedQuerydslRepository.findReceiptByUser_IdAndLastReceiptIdAndNumberOfReceipt(userId, receiptOwnerId, lastReceiptId, numberOfReceipt);
    }

    @Override
    public Feed findReferenceById(Long feedId) {
        return feedJpaRepository.getReferenceById(feedId);
    }
}
