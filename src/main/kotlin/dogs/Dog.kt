package dogs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dog(
    @SerialName("id") val id: Int,
    @SerialName("dog_breed") val dogBreed: String,
    @SerialName("dog_name") val dogName: String,
    @SerialName("weight") val weight: Double
)