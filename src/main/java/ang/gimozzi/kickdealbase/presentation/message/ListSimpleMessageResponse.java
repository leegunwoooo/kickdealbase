package ang.gimozzi.kickdealbase.presentation.message;

import ang.gimozzi.kickdealbase.domain.message.Message;

import java.util.List;

public record ListSimpleMessageResponse(
        List<SimpleMessageResponse> messages
) {
    public static ListSimpleMessageResponse from(List<Message> messages) {
        return new ListSimpleMessageResponse(messages
                .stream()
                .map(SimpleMessageResponse::from)
                .toList());
    }
}
