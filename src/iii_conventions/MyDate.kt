package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (year - other.year != 0) {
            return year - other.year
        }

        if (month - other.month != 0) {
            return month - other.month
        }

        return dayOfMonth - other.dayOfMonth
    }

    operator fun rangeTo(other: MyDate): DateRange {
        return DateRange(this, other)
    }

    infix operator fun plus(timeIntervals: RepeatedTimeInterval): MyDate {
        return addTimeIntervals(timeIntervals.timeInterval, timeIntervals.n)
    }

    infix operator fun plus(timeInterval: TimeInterval): MyDate {
        return addTimeIntervals(timeInterval, 1)
    }
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    infix operator fun times(i: Int): RepeatedTimeInterval {
        return RepeatedTimeInterval(this, i)
    }
}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val n: Int = 1)

class DateRange(val start: MyDate, val endInclusive: MyDate): Iterable<MyDate> {
    operator fun contains(date: MyDate): Boolean {
        return date > start && date <= endInclusive
    }

    override operator fun iterator(): Iterator<MyDate> {
        return object: Iterator<MyDate> {
            var currentDate: MyDate = start

            override fun next(): MyDate {
                val next = currentDate.copy()
                currentDate = currentDate.nextDay()
                return next
            }

            override fun hasNext(): Boolean {
                return currentDate <= endInclusive
            }
        }
    }
}
