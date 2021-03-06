/*
 * Twitter Korean Text - Scala library to process Korean text
 *
 * Copyright 2014 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.twitter.penguin.korean.TwitterKoreanProcessor
import com.twitter.penguin.korean.phrase_extractor.KoreanPhraseExtractor.KoreanPhrase
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer

object ScalaTwitterKoreanTextExample {
  def main(args: Array[String]) {
    // Tokenize into List<String>
    val parsed: Seq[String] = TwitterKoreanProcessor
        .tokenizeToStrings("한국어를 처리하는 예시입니닼ㅋㅋㅋㅋㅋ")
    println(parsed)
    // List(한국어, 를, 처리, 하다, 예시, 이다, ㅋㅋ)

    // Tokenize with Part-of-Speech information
    val parsedPos: Seq[KoreanTokenizer.KoreanToken] =
      TwitterKoreanProcessor.tokenize("한국어를 처리하는 예시입니닼ㅋㅋㅋㅋㅋ")
    println(parsedPos)
    // List(한국어(Noun: 0, 3), 를(Josa: 3, 1), 처리(Noun: 5, 2), 하다(Verb: 7, 2), 예시(Noun: 10, 2), 이다(Adjective: 12, 3), ㅋㅋ(KoreanParticle: 15, 2))

    // Tokenize without stemming
    val parsedPosNoStemming: Seq[KoreanTokenizer.KoreanToken] =
      TwitterKoreanProcessor
          .tokenize("한국어를 처리하는 예시입니닼ㅋㅋㅋㅋㅋ", normalizization = true, stemming = false)
    println(parsedPosNoStemming)
    // List(한국어(Noun: 0, 3), 를(Josa: 3, 1), 처리(Noun: 5, 2), 하는(Verb: 7, 2), 예시(Noun: 10, 2), 입니(Adjective: 12, 2), 다(Eomi: 14, 1), ㅋㅋ(KoreanParticle: 15, 2))

    // Tokenize without normalization and stemming
    val parsedPosParsingOnly: Seq[KoreanTokenizer.KoreanToken] = TwitterKoreanProcessor
      .tokenize("한국어를 처리하는 예시입니닼ㅋㅋㅋㅋㅋ", normalizization = false, stemming = false)
    println(parsedPosParsingOnly)
    // List(한국어(Noun: 0, 3), 를(Josa: 3, 1), 처리(Noun: 5, 2), 하는(Verb: 7, 2), 예시(Noun: 10, 2), 입니(Adjective: 12, 2), 닼*(Noun: 14, 1), ㅋㅋㅋㅋㅋ(KoreanParticle: 15, 5))

    // Phrase extraction
    val phrases: Seq[KoreanPhrase] = TwitterKoreanProcessor
        .extractPhrases("한국어를 처리하는 예시입니닼ㅋㅋㅋㅋㅋ 시발")
    println(phrases)
    // List(한국어(Noun: 0, 3), 처리(Noun: 5, 2), 처리하는 예시(Noun: 5, 7), 예시(Noun: 10, 2), 시발(Noun: 18, 2))

    // Phrase extraction with the spam filter enabled
    val phrasesSpamFilitered: Seq[KoreanPhrase] = TwitterKoreanProcessor
        .extractPhrases("한국어를 처리하는 예시입니닼ㅋㅋㅋㅋㅋ 시발", filterSpam = true)
    println(phrasesSpamFilitered)
    // List(한국어(Noun: 0, 3), 처리(Noun: 5, 2), 처리하는 예시(Noun: 5, 7), 예시(Noun: 10, 2))
  }
}
