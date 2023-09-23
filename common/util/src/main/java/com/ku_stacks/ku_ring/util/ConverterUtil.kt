package com.ku_stacks.ku_ring.util

import timber.log.Timber

fun splitSubjectAndTag(subject: String): Pair<String, List<String>> {
    if (subject.isEmpty()) {
        Timber.e("subject split failed (empty), so empty contents are returned.")
        return Pair("", emptyList())
    }

    val tagList = mutableListOf<String>()
    var startIdx = 0

    if (subject.first() == '[') {
        for (currentIdx in 1 until subject.length) {
            if (subject[currentIdx] == ']') {
                tagList.add(subject.substring(startIdx + 1, currentIdx))
                startIdx = currentIdx + 1
                if (currentIdx + 1 == subject.length || subject[currentIdx + 1] != '[') {
                    break
                }
            }
        }
    }
    return if (tagList.size == 0) {
        Pair(subject, emptyList())
    } else {
        Pair(subject.substring(startIdx).trim(), tagList)
    }
}

fun concatSubjectAndTag(subject: String, tags: List<String>): String {
    val tagStrings = tags.map { "[$it]" }
    return tagStrings.joinToString() + subject
}