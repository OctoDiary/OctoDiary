package org.bxkr.octodiary.data.model.api.mes.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Children(
    @SerialName("age")
    val age: Int,
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("class_level_id")
    val classLevelId: Int,
    @SerialName("class_name")
    val className: String,
    @SerialName("class_unit_id")
    val classUnitId: Int,
    @SerialName("contingent_guid")
    val contingentGuid: String,
//    @SerialName("email")
//    val email: Any?,
    @SerialName("enrollment_date")
    val enrollmentDate: String,
    @SerialName("first_name")
    val firstName: String,
//    @SerialName("groups")
//    val groups: List<Any>,
    @SerialName("id")
    val id: Int,
    @SerialName("is_legal_representative")
    val isLegalRepresentative: Boolean,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("middle_name")
    val middleName: String,
//    @SerialName("parallel_curriculum_id")
//    val parallelCurriculumId: Any,
//    @SerialName("phone")
//    val phone: Any,
//    @SerialName("profession_specialty_code")
//    val professionSpecialtyCode: Any,
//    @SerialName("profession_specialty_name")
//    val professionSpecialtyName: Any,
    @SerialName("representatives")
    val representatives: List<Representative>,
    @SerialName("school")
    val school: School,
    @SerialName("sections")
    val sections: List<Section>,
    @SerialName("service_type_id")
    val serviceTypeId: Int,
    @SerialName("sex")
    val sex: String,
    @SerialName("snils")
    val snils: String,
    @SerialName("sudir_account_exists")
    val sudirAccountExists: Boolean,
//    @SerialName("sudir_login")
//    val sudirLogin: Any,
//    @SerialName("type")
//    val type: Any,
    @SerialName("user_id")
    val userId: Int
){
    val fullName
        get() = "$lastName $firstName${middleName?.let { " $it" } ?: ""}"
}