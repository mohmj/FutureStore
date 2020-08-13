package com.example.futurestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurestore.Adapters.AccountAdapter
import com.example.futurestore.Models.AccountInformaion
import com.example.futurestore.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_account_mine.*
import kotlinx.android.synthetic.main.activity_accounts.*

class AccountMineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_mine)

        getAccounts()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.accounts_add_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id=item.itemId
        when(id){
            R.id.accounts_add_account ->{
                startActivity(Intent(this,AccountsNewActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun getAccounts(){
        var adapter=GroupAdapter<ViewHolder>()
        var uid=Firebase.auth.uid
        accounts_mine_activity_recycler_view.layoutManager=StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        Firebase.database.getReference("users/$uid/${Database().buy_and_sale_accounts}").addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var account=it.getValue(AccountInformaion::class.java)
                    if(account != null){
                        adapter.add(AccountAdapter(account))
                    }
                }
                accounts_mine_activity_recycler_view.adapter=adapter
                adapter.setOnItemClickListener { item, view ->
                    var intent=Intent(view.context,AccountsUpdateActivity::class.java)
                    var intenty=item as AccountAdapter
                    intent.putExtra(Database().update_account_intent,intenty.item)
                    startActivity(intent)
                }
            }

        })
    }

    override fun onResume() {
        getAccounts()
        super.onResume()
    }
}