package id.mustofa.pagingretrofit.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.e.vencenttask.data.model.SearchResults.IndustryDetail

import kotlinx.android.synthetic.main.row_layout_for_industry.view.*

class IndustryDataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

  fun setMovie(data: IndustryDetail?) {
   // val pos = adapterPosition + 1
    itemView.apply {
      var title = data?.stockId ?: "Loading..."
      var name = data?.stockName?:"Loading.."
      var qty = data?.stockQty?:"Loading.."
      var price = data?.stockPrice?:"Loading.."
      var total = data?.stockTotalPrice?:"Loading.."

      price = "₹ $price"
      total = "₹ $total"
      idValue.text = title
      stockNameValue.text = name
      stockQtyValue.text = qty
      priceValue.text = price
      totalValue.text = total

    }
  }
}