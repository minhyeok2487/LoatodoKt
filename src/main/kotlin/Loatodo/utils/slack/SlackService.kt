package Loatodo.utils.slack

import com.slack.api.Slack
import com.slack.api.webhook.WebhookResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class SlackService {

    val log = LoggerFactory.getLogger(this.javaClass)!!

    @Value("\${slack.url.error}")
    private val errorUrl: String? = null

    fun sendSlackMessage(message: String) {
        val instance = Slack.getInstance()
        val payload = "{\"text\":\"${message}\"}"
        try {
            val response: WebhookResponse = instance.send(errorUrl, payload)
        } catch (ex: Exception) {
            log.error("slack 메시지 발송 중 문제가 발생했습니다. ${ex.message}");
        }
    }

}