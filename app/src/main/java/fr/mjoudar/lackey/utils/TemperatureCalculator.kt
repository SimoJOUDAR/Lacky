package fr.mjoudar.lackey.utils

class TemperatureCalculator {

    companion object {
        const val STEP_INF = 4.7619047619
        const val STEP_SUP = 4.761904762
        const val MIN = 7

        fun fromPercentToTemperature(data: Int) : Double {
            val s = (data/ STEP_INF) + MIN
            return s - s%0.5
        }

        fun fromTemperatureToPercent(data: Double) : Int{
            val s = (data - MIN) * STEP_SUP
            return s.toInt()
        }
    }
}