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

package com.twitter.penguin.korean.stemmer

import com.twitter.penguin.korean.TestBase
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer.KoreanToken
import com.twitter.penguin.korean.util.KoreanPos._

class KoreanStemmerTest extends TestBase {

  val sampleText1 = "새로운 스테밍을 추가했었다."
  val sampleStems1 = Seq(
    KoreanToken("새롭다", Adjective, 0, 3),
    KoreanToken("스테밍", Noun, 4, 3),
    KoreanToken("을", Josa, 7,1),
    KoreanToken("추가", Noun, 9,2),
    KoreanToken("하다", Verb, 11,3),
    KoreanToken(".", Punctuation, 14,1)
  )

  val sampleText2 = "그런 사람 없습니다.."
  val sampleStems2 = Seq(KoreanToken("그렇다", Adjective, 0, 2),
    KoreanToken("사람", Noun, 3, 2),
    KoreanToken("없다", Adjective, 6, 4),
    KoreanToken("..", Punctuation, 10, 2)
  )

  val sampleText3 = "라고만"
  val sampleStems3 = Seq(KoreanToken("라고만", Eomi, 0,3))

  test("stemPredicates should stem predicates from Korean tokens") {
    assert(
      KoreanStemmer.stemPredicates(
        KoreanTokenizer.tokenize(sampleText1)
      ) === sampleStems1
    )

    assert(
      KoreanStemmer.stemPredicates(
        KoreanTokenizer.tokenize(sampleText2)
      ) === sampleStems2
    )

    assert(
      KoreanStemmer.stemPredicates(
        KoreanTokenizer.tokenize(sampleText3)
      ) === sampleStems3
    )
  }

  test("stem should transform the original text along with the tokens") {
    assert(
      KoreanStemmer.stem(sampleText1)
          === (new StringBuilder).append("새롭다 스테밍을 추가하다.")
    )
    assert(
      KoreanStemmer.stem(sampleText2)
          === (new StringBuilder).append("그렇다 사람 없다..")
    )
  }

  test("stem should stem adjectives correctly") {
    val words = Map(
      "예뻐도" -> "예쁘다",
      "예뻐서" -> "예쁘다",
      "예뻤다" -> "예쁘다",
      "예뻤었겠지" -> "예쁘다",
      "예뻤지" -> "예쁘다",
      "예쁘겠지" -> "예쁘다",
      "예쁘긴" -> "예쁘다",
      "예쁘지만" -> "예쁘다",
      "예쁜 것 같다" -> "예쁘다 것 같다",
      "예쁜건 아니잖아" -> "예쁘다 아니다",
      "예쁠 수 있을까" -> "예쁘다 수 있다",
      "예쁠" -> "예쁘다",
      "예쁠수있을까" -> "예쁘다",
      "예쁜" -> "예쁘다"
    )
    words.foreach {
      case (word, stem) =>
        val stemmed = KoreanStemmer.stem(word)
        assert(stem === stemmed.toString)
    }
  }

  test("stem should stem verbs correctly") {
    val words = Map(
      "먹어도" -> "먹다",
      "먹어서" -> "먹다",
      "먹었다" -> "먹다",
      "먹었었겠지" -> "먹다",
      "먹었지" -> "먹다",
      "먹겠지" -> "먹다",
      "먹긴" -> "먹다",
      "먹지만" -> "먹다",
      "먹은 것 같다" -> "먹다 것 같다",
      "먹은건 아니잖아" -> "먹다 아니다",
      "먹을 수 있을까" -> "먹다 수 있다",
      "먹을" -> "먹다",
      "먹을수있을까" -> "먹다",
      "먹은" -> "먹다"
    )
    words.foreach {
      case (word, stem) =>
        val stemmed = KoreanStemmer.stem(word)
        assert(stem === stemmed.toString)
    }
  }
}