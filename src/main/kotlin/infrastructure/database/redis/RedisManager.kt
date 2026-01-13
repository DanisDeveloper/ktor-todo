@file:OptIn(ExperimentalLettuceCoroutinesApi::class)

package infrastructure.database.redis

import danis.galimullin.infrastructure.database.redis.RedisConfig
import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.RedisClient
import io.lettuce.core.api.coroutines


object RedisManager {
    private val client = RedisClient.create(RedisConfig.url)
    val connection = client.connect()
    val commands = connection.coroutines()
}