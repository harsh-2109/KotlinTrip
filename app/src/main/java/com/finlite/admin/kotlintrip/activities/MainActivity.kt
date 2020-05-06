package com.finlite.admin.kotlintrip.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.SpannedString
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.finlite.admin.kotlintrip.ApplicationLoader
import com.finlite.admin.kotlintrip.R
import com.finlite.admin.kotlintrip.adapters.ProjectAdapter
import com.finlite.admin.kotlintrip.database.DBHelper
import com.finlite.admin.kotlintrip.interfaces.OnItemClickListener
import com.finlite.admin.kotlintrip.models.ProjectModel
import com.finlite.admin.kotlintrip.utils.EqualSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import java.util.*


class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var projectModels: List<ProjectModel>
    private lateinit var projectAdapter: ProjectAdapter

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        init()

        /*val client = OkHttpClient()
        val request = Request.Builder()
                .url("http://ec2-18-223-143-101.us-east-2.compute.amazonaws.com/packageDetail")
                .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = println(response.body()?.string())
        })
        val result = URL("http://ec2-18-223-143-101.us-east-2.compute.amazonaws.com/packageDetail").readText()
        Log.e("response",result);*/

//        btnShareText.setOnClickListener {
            val format = "%d\tX\n"
            val totalFormat = "%5s  %2d = %10d\n"
            val stringBuilder = StringBuilder()
            stringBuilder.append(String.format(format, 2000, 5, 10000))
            stringBuilder.append(String.format(format, 500, 22, 11000))
            stringBuilder.append(String.format(format, 200, 20, 4000))
            stringBuilder.append(String.format(format, 100, 55, 5500))
            stringBuilder.append(String.format(format, 20, 5, 100))
            stringBuilder.append("==============================================\n")
            stringBuilder.append(String.format(totalFormat, "Total", 107, 30600))

//            tvText.setText(spannableStringBuilder.toString())
//            var intent = Intent(Intent.ACTION_SEND)
//            intent.setType("text/html");
//            intent.putExtra(android.content.Intent.EXTRA_TEXT, stringBuilder.toString());
//            startActivity(intent)
//        }


//        var str = "<html><body>A dressy take on classic gingham in a soft, textured weave of stripes that resembles twill.  Take a closer look at this one.<ul><li>Trim, tailored fit for a bespoke feel</li><li>Medium spread collar, one-button mitered barrel cuffs</li><li>Applied placket with genuine mother-of-pearl buttons</li><li>;Split back yoke, rear side pleats</li><li>Made in the U.S.A. of 100% imported cotton.</li></ul></body></html>"
//        tvText.setText(Html.fromHtml(""))


    }

    private fun init() {
        dbHelper = DBHelper(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(EqualSpacingItemDecoration(10))
        projectModels = ArrayList()
    }

    override fun onResume() {
        super.onResume()
        setList()
    }

    fun setList() {
        projectModels = dbHelper.getAllProjects()

        projectAdapter = ProjectAdapter(this, projectModels, this)
        recyclerView.adapter = projectAdapter
    }

    override fun onClick(position: Int) {
        ApplicationLoader.instance.setProjectId(projectModels.get(position).id)
        startActivity(Intent(this@MainActivity, ProjectDetailsActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_trip -> {
                startActivity(Intent(this@MainActivity, AddProjectActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*lateinit var editText: EditText
    lateinit var btnAddProject: Button
    lateinit var alertDialog: AlertDialog

    private fun addProjectDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_project, null)
        builder.setView(dialogView)

        editText = dialogView.findViewById(R.id.etText) as EditText
        editText.setHint(R.string.str_enter_project_name)

        btnAddProject = dialogView.findViewById(R.id.btnAddProject) as Button

        alertDialog = builder.create()

        btnAddProject.setOnClickListener {
            if (editText.text.toString().isEmpty()) {
                Toast.makeText(this@MainActivity, "Enter Project Name", Toast.LENGTH_SHORT).show()
            } else {
                alertDialog.dismiss()
                startActivity(Intent(this@MainActivity, ProjectDetailsActivity::class.java))
            }
        }

        alertDialog.show()
    }*/
}
