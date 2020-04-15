package com.swensun.potato

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.Test
import java.io.IOError

class Get {

    @Test
    fun test() {
       runBlocking {
            flow {
                (1..5).forEach {
                    delay(800)
                    emit(it)
                }
            }   .catch {
                println(it.message)
            }
                .onEach {
                println("each $it")
            }.onStart { println("Start") }
                .onCompletion { println("completion") }

                .toList()
        }

    }


}