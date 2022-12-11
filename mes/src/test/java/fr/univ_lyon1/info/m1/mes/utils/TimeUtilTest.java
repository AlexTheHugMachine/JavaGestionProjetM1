package fr.univ_lyon1.info.m1.mes.utils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TimeUtilTest {
  @Test
  void getDateReturnTheExpectedFormattedTime() {
    String dateWithoutSeparator = TimeUtil.getDate("yyyyMMddHHmm");
    String dateWithSeprator = TimeUtil.getDate("yyyy/MM/dd HH:mm");
    String dateWithHyphen = TimeUtil.getDate("yyyy-MM-dd HH:mm");
    String pattern =
"^(20[0-9]{2})(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])(0[0-9]|1[0-9]|2[0-3])(0[0-9]|[1-5][0-9])$";
    assertAll(
        () -> assertTrue(
            dateWithoutSeparator.matches(pattern)),
        () -> assertTrue(dateWithSeprator.matches("^\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}$")),
        () -> assertTrue(dateWithHyphen.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$")));
  }

  @Test
  void getDateThrowExceptionWhenPatternEmptyOrNull() {
    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> {
          TimeUtil.getDate("");
        }),
        () -> assertThrows(NullPointerException.class, () -> {
          TimeUtil.getDate(null);
        }));
  }
}
