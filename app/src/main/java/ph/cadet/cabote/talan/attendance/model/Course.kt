package ph.cadet.cabote.talan.attendance.model

import android.os.Parcel
import android.os.Parcelable

class Course (var courseID: String = "", var courseName: String = "", var courseStartTime: String = "", var courseEndTime: String = "", var courseClass: String = ""): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(courseID)
        parcel.writeString(courseName)
        parcel.writeString(courseStartTime)
        parcel.writeString(courseEndTime)
        parcel.writeString(courseClass)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Course> {
        override fun createFromParcel(parcel: Parcel): Course {
            return Course(parcel)
        }

        override fun newArray(size: Int): Array<Course?> {
            return arrayOfNulls(size)
        }
    }
}