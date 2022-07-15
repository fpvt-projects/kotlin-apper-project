package ph.cadet.cabote.talan.attendance.model

import android.os.Parcel
import android.os.Parcelable

class Classes (var classBlock: String = "", var date: String = "", var totalAttendees : Int = 0) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(classBlock)
        parcel.writeString(date)
        parcel.writeInt(totalAttendees)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Classes> {
        override fun createFromParcel(parcel: Parcel): Classes {
            return Classes(parcel)
        }

        override fun newArray(size: Int): Array<Classes?> {
            return arrayOfNulls(size)
        }
    }
}