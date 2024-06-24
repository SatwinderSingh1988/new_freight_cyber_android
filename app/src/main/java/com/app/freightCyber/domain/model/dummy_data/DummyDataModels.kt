package com.app.freightCyber.domain.model.dummy_data


data class AuthenticationData(
    val image: Int,
    val authType: String,
    val authTypeText: String,
    var isSelect: Boolean = false
)

data class ComplianceData(
    val image: Int,
    val authType: String,
    val authTypeText: String,
    var isSelect: Boolean = false
)
data class MapSearchData(
    val image: Int,
    val text: String,
)

data class ReportTransfer(
    val image: Int,
    val header: String,
    val date: String,
    var isSelect: Boolean = false
)

data class ReportData(
    val image: Int,
    val reportText: String
)

data class InspectionCheckList(
    val levels : String,
    var visibility : Boolean = false
)
data class InnerInspectionCheckList(
    val levels : String,
    val subText : String,
    val checkBoxOneText : String,
    val checkBoxTwoText : String?,
    var isChecked : Int = 0
)

data class SettingData(
    val image: Int,
    val text: String,
    var isSelect: Boolean
)

data class CreateProfileData(
    val image: Int,
    val text: String,
)

data class LicenceType(
    val image: Int,
    val licenseType: String,
    var checked: Boolean
)
data class JockeyDetails(
    val text: String
)
data class DSEModuleData(
    val image: Int,
    val textHeader: String,
    val textSubHeader: String,
    val textApproxTime: String,
)

data class EmpData(
    val text: String
)

data class VehicleInspection(
    var value : Boolean ,
    var text : String
)

data class TermsData(
    var value : String ,
)

data class DeclarationList(
    val text : String
)

data class WorkDiarySolo(
    val image: Int,
    val text: String,
)

data class DriveArrangement(
    val image: Int,
    val type: String,
    val typeDesc : String
)

data class LoggingOff(
    val text : String
)

data class AddDiarySwitch(
    val text : String ,
    val image : Int
)

data class SettingsData(
    val image : Int ,
    val text : String
)

data class EventLog(
    val status : String
)









