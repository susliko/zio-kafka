package zio.kafka.serde

import org.apache.kafka.common.header.internals.RecordHeaders
import zio.test.Assertion._
import zio.test._

object SerializerSpec extends DefaultRunnableSpec {
  override def spec = suite("Serializer")(
    suite("asOption")(
      test("serialize None values to null") {
        assertM(stringSerializer.asOption.serialize("topic1", new RecordHeaders, None))(isNull)
      },
      test("serialize Some values") {
        check(Gen.string) { string =>
          assertM(
            stringSerializer.asOption.serialize("topic1", new RecordHeaders, Some(string)).map(new String(_, "UTF-8"))
          )(
            equalTo(string)
          )
        }
      }
    )
  )
  private lazy val stringSerializer: Serializer[Any, String] = Serde.string
}
