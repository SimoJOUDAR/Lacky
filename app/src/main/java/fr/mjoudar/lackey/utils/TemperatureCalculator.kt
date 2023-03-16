package fr.mjoudar.lackey.utils

/***********************************************************************************************
 * TemperatureCalculator class - to perform basic temperature/percentage conversions
 ***********************************************************************************************/
class TemperatureCalculator {

    companion object {
        const val STEP_INF = 4.7619047619
        const val STEP_SUP = 4.761904762
        const val MIN = 7

        // Convert a percentage of a range from 0 to 100 to temperature of a range from 7 to 28
        fun fromPercentToTemperature(data: Int) : Double {
            val s = (data/ STEP_INF) + MIN
            return s - s%0.5
        }

        // Convert a temperature of a range from 7 to 28 to a percentage of a range from 0 to 100
        fun fromTemperatureToPercent(data: Double) : Int{
            val s = (data - MIN) * STEP_SUP
            return s.toInt()
        }
    }
}