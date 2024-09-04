package com.volodya.coordinates

import kotlin.math.*

fun doubleToString(value: Double, precision: Int) : String {
    return String.format("%.${precision}f", value)
}

interface Coordinates {
    fun getName() : String
    fun getFields() : Array<Char>
    fun toDecart2d() : Pair<Boolean, Coordinates>
    fun toPolar() : Pair<Boolean, Coordinates>
    fun toDecart3d() : Pair<Boolean, Coordinates>
    fun toCylindrical() : Pair<Boolean, Coordinates>
    fun toSpherical() : Pair<Boolean, Coordinates>
    fun getCoordinates(precision: Int) : String
}

const val DECART2D_NAME = "Двумерные декартовы координаты"
const val POLAR_NAME = "Полярные координаты"
const val DECART3D_NAME = "Трехмерные декартовы координаты"
const val CYLINDRICAL_NAME = "Цилинричнские координаты"
const val SPHERICAL_NAME = "Сферические координаты"

val DECART2D_FIELDS = arrayOf('x', 'y')
val POLAR_FIELDS = arrayOf('ρ', 'φ')
val DECART3D_FIELDS = arrayOf('x', 'y', 'z')
val CYLINDRICAL_FIELDS = arrayOf('ρ', 'φ', 'z')
val SPHERICAL_FIELDS = arrayOf('r', 'θ', 'φ')

class Decart2d(private var x: Double, private var y: Double) : Coordinates {
    override fun getName() : String {
        return DECART2D_NAME
    }
    override fun getFields(): Array<Char> {
        return DECART2D_FIELDS
    }

    fun getX() : Double {
        return x
    }
    fun getY() : Double {
        return x
    }
    fun setCoordinates(x: Double, y: Double) {
        this.x = x
        this.y = y
    }

    override fun toDecart2d(): Pair<Boolean, Decart2d> {
        return Pair(false, this)
    }
    override fun toPolar(): Pair<Boolean, PolarCoordinates> {
        val rho = sqrt(x * x + y * y)
        val phi = atan2(y, x)
        return Pair(false, PolarCoordinates(rho, phi))
    }
    override fun toDecart3d(): Pair<Boolean, Decart2d> {
        return Pair(true, this)
    }
    override fun toCylindrical(): Pair<Boolean, Decart2d> {
        return Pair(true, this)
    }
    override fun toSpherical(): Pair<Boolean, Decart2d> {
        return Pair(true, this)
    }
    override fun getCoordinates(precision: Int) : String {
        return (
                "x: ${doubleToString(x, precision)};\n" +
                "y: ${doubleToString(y, precision)}."
        )
    }
}

class PolarCoordinates(private var rho : Double, private var phi : Double) : Coordinates {
    override fun getName() : String {
        return POLAR_NAME
    }
    override fun getFields(): Array<Char> {
        return POLAR_FIELDS
    }

    fun getRho() : Double {
        return rho
    }
    fun getPhi() : Double {
        return phi
    }
    fun setCoordinates(rho: Double, phi: Double) {
        this.rho = rho
        this.phi = phi
    }

    override fun toDecart2d(): Pair<Boolean, Decart2d> {
        val x = rho * cos(phi)
        val y = rho * sin(phi)
        return Pair(false, Decart2d(x, y))
    }
    override fun toPolar(): Pair<Boolean, PolarCoordinates> {
        return Pair(false, this)
    }
    override fun toDecart3d(): Pair<Boolean, PolarCoordinates> {
        return Pair(true, this)
    }
    override fun toCylindrical(): Pair<Boolean, PolarCoordinates> {
        return Pair(true, this)
    }
    override fun toSpherical(): Pair<Boolean, PolarCoordinates> {
        return Pair(true, this)
    }
    override fun getCoordinates(precision: Int) : String {
        return (
                "ρ: ${doubleToString(rho, precision)};\n" +
                "φ: ${doubleToString(phi, precision)}rad."
        )
    }
}

class Decart3d(private var x: Double, private var y: Double, private var z: Double) : Coordinates {
    override fun getName() : String {
        return DECART3D_NAME
    }
    override fun getFields(): Array<Char> {
        return DECART3D_FIELDS
    }

    fun getX() : Double {
        return x
    }
    fun getY() : Double {
        return x
    }
    fun getZ() : Double {
        return x
    }
    fun setCoordinates(x: Double, y: Double, z: Double) {
        this.x = x
        this.y = y
        this.z = z
    }

    override fun toDecart2d(): Pair<Boolean, Decart3d> {
        return Pair(true, this)
    }
    override fun toPolar(): Pair<Boolean, Decart3d> {
        return Pair(true, this)
    }
    override fun toDecart3d(): Pair<Boolean, Decart3d> {
        return Pair(false, this)
    }
    override fun toCylindrical(): Pair<Boolean, CylindricalCoordinates> {
        val rho = sqrt(x * x + y * y)
        val phi = atan2(y, x)
        return Pair(false, CylindricalCoordinates(rho, phi, z))
    }
    override fun toSpherical(): Pair<Boolean, SphericalCoordinates> {
        val r = sqrt(x * x + y * y + z * z)
        val theta = atan(sqrt(x * x + y * y) / z)
        val phi = atan2(y, x)
        return Pair(false, SphericalCoordinates(r, theta, phi))
    }
    override fun getCoordinates(precision: Int) : String {
        return (
                "x: ${doubleToString(x, precision)};\n" +
                "y: ${doubleToString(y, precision)};\n" +
                "z: ${doubleToString(z, precision)}."
        )
    }
}

class CylindricalCoordinates(
    private var rho: Double, private var phi: Double, private var z: Double
) : Coordinates {
    override fun getName() : String {
        return CYLINDRICAL_NAME
    }
    override fun getFields(): Array<Char> {
        return CYLINDRICAL_FIELDS
    }

    fun getRho() : Double {
        return rho
    }
    fun getPhi() : Double {
        return phi
    }
    fun getZ() : Double {
        return z
    }
    fun setCoordinates(rho: Double, phi: Double, z: Double) {
        this.rho = rho
        this.phi = phi
        this.z = z
    }

    override fun toDecart2d(): Pair<Boolean, CylindricalCoordinates> {
        return Pair(true, this)
    }
    override fun toPolar(): Pair<Boolean, CylindricalCoordinates> {
        return Pair(true, this)
    }
    override fun toDecart3d(): Pair<Boolean, Decart3d> {
        val x = rho * cos(phi)
        val y = rho * sin(phi)
        return Pair(false, Decart3d(x, y, z))
    }
    override fun toCylindrical(): Pair<Boolean, Coordinates> {
        return Pair(false, this)
    }
    override fun toSpherical(): Pair<Boolean, SphericalCoordinates> {
        val r = sqrt(rho * rho + z * z)
        val theta = atan2(rho, z)
        return Pair(false, SphericalCoordinates(r, theta, phi))
    }
    override fun getCoordinates(precision: Int): String {
        return (
                "ρ: ${doubleToString(rho, precision)};\n" +
                "φ: ${doubleToString(phi, precision)}rad;\n" +
                "z: ${doubleToString(z, precision)}."
        )
    }

}

class SphericalCoordinates(
    private var r: Double, private var theta: Double, private var phi: Double
) : Coordinates {
    override fun getName() : String {
        return SPHERICAL_NAME
    }
    override fun getFields(): Array<Char> {
        return SPHERICAL_FIELDS
    }

    fun getRadius() : Double {
        return r
    }
    fun getTheta() : Double {
        return theta
    }
    fun getPhi() : Double {
        return phi
    }

    override fun toDecart2d(): Pair<Boolean, SphericalCoordinates> {
        return Pair(true, this)
    }
    override fun toPolar(): Pair<Boolean, SphericalCoordinates> {
        return Pair(true, this)
    }
    override fun toDecart3d(): Pair<Boolean, Decart3d> {
        val x = r * sin(theta) * cos(phi)
        val y = r * sin(theta) * sin(phi)
        val z = r * cos(theta)
        return Pair(false, Decart3d(x, y, z))
    }
    override fun toCylindrical(): Pair<Boolean, CylindricalCoordinates> {
        val rho = r * sin(theta)
        val z = r * cos(theta)
        return Pair(false, CylindricalCoordinates(rho, phi, z))
    }
    override fun toSpherical(): Pair<Boolean, SphericalCoordinates> {
        return Pair(false, this)
    }
    override fun getCoordinates(precision: Int): String {
        return (
                "r: ${doubleToString(r, precision)};\n" +
                "θ: ${doubleToString(theta, precision)}rad;\n" +
                "φ: ${doubleToString(phi, precision)}rad."
        )
    }
}