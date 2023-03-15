package fr.mjoudar.lackey

import fr.mjoudar.lackey.utils.TemperatureCalculator
import org.junit.Assert
import org.junit.Test

class TemperatureConversionUnitTest {

    @Test
    fun fromPercentToTemperature_isCorrect() {
        var input = 100
        var result = TemperatureCalculator.fromPercentToTemperature(input)
        Assert.assertTrue(28.0 == result)

        input = 0
        result = TemperatureCalculator.fromPercentToTemperature(input)
        Assert.assertTrue(7.0 == result)

    }

    @Test
    fun fromTemperatureToPercent_isCorrect() {
        var input = 28.toDouble()
        var result = TemperatureCalculator.fromTemperatureToPercent(input)
        Assert.assertEquals(100, result)

        input = 7.toDouble()
        result = TemperatureCalculator.fromTemperatureToPercent(input)
        Assert.assertEquals(0, result)
    }

    @Test
    fun temperatureCalculator_isCorrect() {
        val input = 28.toDouble()
        val result = TemperatureCalculator.fromPercentToTemperature(TemperatureCalculator.fromTemperatureToPercent(input))
        Assert.assertTrue(input == result)
    }

}