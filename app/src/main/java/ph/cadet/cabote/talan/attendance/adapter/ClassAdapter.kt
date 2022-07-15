package ph.cadet.cabote.talan.attendance.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.cadet.cabote.talan.attendance.databinding.ClassViewBinding
import ph.cadet.cabote.talan.attendance.databinding.CourseViewBinding
import ph.cadet.cabote.talan.attendance.model.Classes
import ph.cadet.cabote.talan.attendance.model.Course

class ClassAdapter (
    private val context: Context,
    private var classes: ArrayList<Classes>
) : RecyclerView.Adapter<ClassAdapter.ClassViewHolder>(){
    var onItemClick : ((Classes) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val classBinding = ClassViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ClassViewHolder(classBinding)
    }

    inner class ClassViewHolder(private val classBinding: ClassViewBinding) :
        RecyclerView.ViewHolder(classBinding.root) {
        fun bindClasses(data: Classes) {

            classBinding.textViewClassBlock.text = data.classBlock
            classBinding.textViewClassDate.text = data.date
            //classBinding.textViewTotal.text = data.totalAttendees.toString()

            itemView.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.bindClasses(classes[position])
    }

    override fun getItemCount() = classes.size
}