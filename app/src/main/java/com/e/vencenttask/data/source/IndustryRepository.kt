package com.e.vencenttask.data.source

import com.e.vencenttask.data.model.SearchResults.IndustryDetail
import com.e.vencenttask.data.Result



interface IndustryRepository {

   fun getindustryData(): Result<IndustryDetail>

}