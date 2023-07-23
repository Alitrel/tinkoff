package ru.alitryel.oldgsite.api.controller

import org.springframework.web.bind.annotation.*
import ru.alitryel.oldgsite.Greeting
import ru.alitryel.oldgsite.StaffData
import java.sql.DriverManager
import java.util.concurrent.atomic.AtomicLong

@RestController("/api")
class BackendController() {

    val counter = AtomicLong()

    val func = getStuffData().split("/")

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
        Greeting(counter.incrementAndGet(), "Hello, $name")

    fun getStuffData(): String {
        try {
            Class.forName("org.postgresql.Driver")
            val connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/shoptinka", "postgres", "123")
            println("Подключение успешно!")
            val sql = "SELECT * FROM " + '"'+ "Goods" + '"'
            val statement = connection.createStatement()
            val rs = statement.executeQuery(sql)
            while (rs.next()){
                val id = rs.getString("product_id")
                val name = rs.getString("name")
                val desc = rs.getString("desc")
                val price = rs.getString("price")
                val img = rs.getString("image")
                val category = rs.getString("category")
                val selled = rs.getString("selled")
                return "$id/$name/$desc/$price/$img/$category/$selled"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
    @GetMapping("/catalog")
    fun catalog(@RequestParam(value = "id", defaultValue = "1") id: Long) =
        StaffData(id, func[1], func[2], func[3].toFloat(), 0, func[5], func[6].toBoolean())

    @GetMapping("/test")
    fun getCatalog() {
        try {
            Class.forName("org.postgresql.Driver")
            val connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/shoptinka", "postgres", "123")
            println("Подключение успешно!")
            val sql = "SELECT count(*) FROM " + '"'+ "Goods" + '"'
            val statement = connection.createStatement()
            val rs = statement.executeQuery(sql)
            while (rs.next()){
                val number = rs.getString(1)
                println(number)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}