package ph.cadet.cabote.talan.attendance.model

import android.os.Parcel
import android.os.Parcelable

class Attendance(
    var firstName: String = "",
    var lastName: String = "",
    var studentEmail: String = "",
    var studentNumber: String = "",
    var date: String = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(studentEmail)
        parcel.writeString(studentNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Attendance> {
        override fun createFromParcel(parcel: Parcel): Attendance {
            return Attendance(parcel)
        }

        override fun newArray(size: Int): Array<Attendance?> {
            return arrayOfNulls(size)
        }
    }
}