package fi.henrimakela.fishwidget.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fi.henrimakela.fishwidget.R
import fi.henrimakela.fishwidget.view.ListItemData

class ForecastAdapter(private var items: List<ListItemData> = emptyList()) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.data_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ForecastAdapter.ViewHolder, position: Int) {
        holder.image.setImageDrawable(
            ContextCompat.getDrawable(
                holder.itemView.context,
                items[position].icon
            )
        )

        holder.title.text = items[position].title
        holder.text.text = items[position].text

    }

   fun setData(data: List<ListItemData>){
       this.items = data
       notifyDataSetChanged()
   }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.image)
        var title: TextView = view.findViewById(R.id.title)
        var text: TextView = view.findViewById(R.id.text)
    }

}