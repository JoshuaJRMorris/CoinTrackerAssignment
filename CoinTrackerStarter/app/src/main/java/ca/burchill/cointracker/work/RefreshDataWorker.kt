package ca.burchill.cointracker.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ca.burchill.cointracker.database.getDatabase
import ca.burchill.cointracker.repository.CoinsRepository
import com.bumptech.glide.load.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
CoroutineWorker(appContext, params){
    companion object{
        const val WORK = "ca.burchill.cointracker.work.RefreshDataWorker"
    }

    override  suspend fun  doWork(): Result{
        val db = getDatabase(applicationContext)
        val repo = CoinsRepository(db)
        try{
            repo.refreshCoins()
        }catch(e: HttpException){
            return Result.retry()
        }
        return  Result.success()
    }

}