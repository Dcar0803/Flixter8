package com.example.flixter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PersonAdapter(
    private var persons: List<Person>,
    private val listener: OnPersonItemClickListener
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    inner class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.person_name)
        private val biographyTextView: TextView = itemView.findViewById(R.id.person_biography)
        private val profileImageView: ImageView = itemView.findViewById(R.id.person_profile)

        fun bind(person: Person) {
            nameTextView.text = person.name
            biographyTextView.text = person.biography

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500/${person.profilePath}")
                .into(profileImageView)

            itemView.setOnClickListener {
                listener.onPersonItemClick(person)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(persons[position])
    }

    override fun getItemCount(): Int = persons.size

    fun setData(newPersons: List<Person>) {
        persons = newPersons
        notifyDataSetChanged()
    }
}
