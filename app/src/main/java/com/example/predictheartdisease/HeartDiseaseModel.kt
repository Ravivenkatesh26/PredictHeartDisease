package com.example.predictheartdisease

import android.content.Context
import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import java.nio.FloatBuffer

class HeartDiseaseModel(context: Context, modelFileName: String) {

    private val env: OrtEnvironment = OrtEnvironment.getEnvironment()
    private val session: OrtSession

    private val expectedFeatureCount = 8

    init {
        // Load ONNX model bytes from assets and create a session once
        val modelBytes = context.assets.open(modelFileName).use { it.readBytes() }
        session = env.createSession(modelBytes)
    }

    fun predict(features: FloatArray): Float {
        if (features.size != expectedFeatureCount) {
            throw IllegalArgumentException("Expected $expectedFeatureCount features, got ${features.size}")
        }

        val shape = longArrayOf(1L, features.size.toLong())
        val fb: FloatBuffer = FloatBuffer.wrap(features)
        val inputName = session.inputNames.iterator().next()

        OnnxTensor.createTensor(env, fb, shape).use { tensor ->
            session.run(mapOf(inputName to tensor)).use { results ->
                val raw = results[0].value

                return when (raw) {
                    is Array<*> -> {
                        val first = raw.getOrNull(0)
                        when (first) {
                            is FloatArray -> {
                                if (first.size > 1) first[1] else first[0]
                            }
                            is DoubleArray -> {
                                if (first.size > 1) first[1].toFloat() else first[0].toFloat()
                            }
                            is LongArray -> {
                                val label = first.getOrNull(0) ?: 0L
                                label.toFloat()
                            }
                            is IntArray -> {
                                val label = first.getOrNull(0) ?: 0
                                label.toFloat()
                            }
                            is BooleanArray -> {
                                val b = first.getOrNull(0) ?: false
                                if (first.size > 1) { // if boolean vector (unlikely), convert index 1
                                    (if (first[1]) 1f else 0f)
                                } else (if (b) 1f else 0f)
                            }
                            else -> {
                                when (first) {
                                    is Array<*> -> {
                                        // e.g. Array<Float> inside Array<*>
                                        val maybeNum = first.getOrNull(0)
                                        (maybeNum as? Number)?.toFloat() ?: throw IllegalStateException("Unexpected nested array inner type: ${maybeNum?.javaClass}")
                                    }
                                    is Number -> (first as Number).toFloat()
                                    else -> throw IllegalStateException("Unexpected inner output type: ${first?.javaClass}")
                                }
                            }
                        }
                    }
                    is FloatArray -> {
                        if (raw.size > 1) raw[1] else raw[0]
                    }
                    is DoubleArray -> {
                        if (raw.size > 1) raw[1].toFloat() else raw[0].toFloat()
                    }
                    is LongArray -> {
                        // e.g. predicted labels as long[]
                        (raw.getOrNull(0) ?: 0L).toFloat()
                    }
                    is IntArray -> {
                        (raw.getOrNull(0) ?: 0).toFloat()
                    }
                    is BooleanArray -> {
                        if (raw.size > 1) (if (raw[1]) 1f else 0f) else (if (raw[0]) 1f else 0f)
                    }
                    is Number -> raw.toFloat()
                    else -> throw IllegalStateException("Unexpected model output type: ${raw?.javaClass}")
                }
            }
        }
    }
    fun close() {
        session.close()
        env.close()
    }
}
